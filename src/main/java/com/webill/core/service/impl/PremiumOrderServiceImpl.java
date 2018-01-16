package com.webill.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.SystemProperty;
import com.webill.app.util.BaoXianUtil;
import com.webill.app.util.BigDecimalUtil;
import com.webill.app.util.DateUtil;
import com.webill.app.util.HttpUtils;
import com.webill.app.util.OrderUtils;
import com.webill.app.util.PinYinUtil;
import com.webill.app.util.PremiumOrderStatus;
import com.webill.app.util.StringUtil;
import com.webill.core.Constant;
import com.webill.core.ResponseInfo;
import com.webill.core.model.AskPrice;
import com.webill.core.model.CarInfo;
import com.webill.core.model.CarUserRel;
import com.webill.core.model.PersonMsg;
import com.webill.core.model.Premium;
import com.webill.core.model.PremiumDetail;
import com.webill.core.model.PremiumOrder;
import com.webill.core.model.TemplateMsgResult;
import com.webill.core.model.UserContact;
import com.webill.core.model.WechatTemplateMsg;
import com.webill.core.service.ICarInfoService;
import com.webill.core.service.ICarUserRelService;
import com.webill.core.service.IMessageService;
import com.webill.core.service.IPremiumDetailService;
import com.webill.core.service.IPremiumOrderService;
import com.webill.core.service.IPremiumService;
import com.webill.core.service.IUserContactService;
import com.webill.core.service.mapper.PremiumOrderMapper;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.service.impl.SuperServiceImpl;

import reactor.core.Reactor;

/**
 *
 * PremiumOrder 服务层接口实现类
 *
 */
@Service
public class PremiumOrderServiceImpl extends SuperServiceImpl<PremiumOrderMapper, PremiumOrder> implements IPremiumOrderService {

    @Autowired
    @Qualifier("rootReactor")
    private Reactor r;
    
	@Autowired
	private IPremiumDetailService premiumDetailService;
	
	@Autowired
	private IPremiumService premiumService;
	
	@Autowired
	private IUserContactService userContactService;
	
	@Autowired
	private ICarInfoService carInfoService;
	
    @Autowired
    protected SystemProperty constPro;
    
    @Autowired
    private ICarUserRelService carUserRelService;
    
    @Autowired
    private IMessageService messageService;

	@Transactional
	@Override
	public ResponseInfo savePoByHistoryOrDefault(String carNo,String userId,String insurerCom) {
		if(StringUtil.isNotEmpty(insurerCom)){
			constPro.INSURER_COM = getBaoXianStr(insurerCom);
		}
		ResponseInfo result = null;
		String resultstr = "";
		boolean f = false;
		
		//获取历史订单接口
		String url = constPro.MMH_NET_URL + "/Premium/getHistory" ;
		
		//获取车辆信息
		Map<String,Object> mp = new HashMap<String,Object>();
		mp.put("license_no", carNo);
		mp.put("status", 0);
		List<CarInfo> cf = carInfoService.selectByMap(mp);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("carNo", carNo);
		if(cf.size()>0){
			//获取历史投保订单
			try {
					resultstr = HttpUtils.httpGetRequest(url, map);
					if(resultstr.contains("isSuccess")){
						PremiumOrder po = JSONObject.parseObject(resultstr, PremiumOrder.class);
						//历史保单信息
						if(Constant.PREMIUM_ASK_PRICE_SUCCESS.toString().equals(po.getIsSuccess())){//获取历史信息成功
							//保存历史订单
							//设置订单为过期状态
							po.setStatus(Constant.PREMIUM_ORDER_STATUS_OVERDUE);
							f = this.insert(po);
							//保存历史投保详情
							for(PremiumDetail pd:po.getPdlist()){
								f = premiumDetailService.insert(pd);
							}
							//创建新订单
							PremiumOrder npo = new PremiumOrder();
							npo.setLicenseNo(carNo);
							npo.setCreatedTime(new Date());
							npo.setCarId(cf.get(0).getId());
							npo.setCity(constPro.CITY);
							npo.setUserId(Constant.PUBLIC_ORDER_USERID);
							npo.setCiStartDate(cf.get(0).getPrmEndTime());
							npo.setBiStartDate(cf.get(0).getPrmEndTime());
							npo.setStatus(Constant.PREMIUM_ORDER_STATUS_HAVEPRICE);
							f = this.insert(npo);
							//根据历史投保方案询价
							AskPrice pricestr = this.askPremiumPrice(po.getCiInsurerCom(), po.getLicenseNo(), po.getPdlist());
							if(pricestr.getIsSuccess()==Constant.PREMIUM_ASK_PRICE_SUCCESS){
								//询价成功
								PremiumOrder r = new PremiumOrder();
								r.setId(npo.getId());
								r.setStatus(Constant.PREMIUM_ORDER_STATUS_HAVEPRICE);
								this.updateSelectiveById(r);
							}
							//询价结果
							List<PremiumDetail> pdlist = pricestr.getDetailList();
							//创建订单详情
							PremiumDetail npd = null;
							for(PremiumDetail d:pdlist){
								npd = new PremiumDetail();
								npd.setAmount(d.getAmount());
								npd.setPrmName(d.getPrmName());
								npd.setCreatedTime(new Date());
								f = premiumDetailService.insert(npd);
								if(!f){
									result = ResponseInfo.ACTION_FAIL;
									break;
								}
							}
						}else{//获取历史信息失败,创建默认订单
							result = this.addNewPremiumOrderByDefault(carNo,userId,insurerCom);
							if(ResponseInfo.SUCCESS.equals(result)){
								f = true;
							}
						}
					}else{
						//第三方接口未开启
						logger.info("第三方接口未开启====>>"+resultstr);
						result = this.addNewPremiumOrderByDefault(carNo,userId,insurerCom);
						if(ResponseInfo.SUCCESS.equals(result)){
							f = true;
						}
					}
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		if(f){
			result = ResponseInfo.SUCCESS;
		}else{
			result = ResponseInfo.ACTION_FAIL;
		}
		return result;
	}
	

	
	@Override
	public String getPremiumOrder(String orderId,String carNo,String userId) {
		String jsonstr = "";
		PremiumOrder order = new PremiumOrder();
		if(orderId!=null){
			order = this.selectById(orderId);
		}else{
			Map<String,Object> m = new HashMap<String,Object>();
			if(carNo!=null &&!"".equals(carNo)){
				m.put("carNo", carNo);
			}
			m.put("userId", userId);
			order = this.getOnePoByMap(m);
			if(order==null){
				m.put("userId", constPro.DEFAULT_ORDER_ID);
				order = this.getOnePoByMap(m);
			}
		}
		
		if(order!=null){//有公有订单或者私有订单时
			if(order.getContactId()!=null){//私有订单时，绑定联系人信息
				UserContact uc = userContactService.selectById(order.getContactId());
				List<UserContact> list = new ArrayList<UserContact>();
				list.add(uc);
				order.setUclist(list);
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("prm_order_id", order.getId());
			List<PremiumDetail> detail = premiumDetailService.selectByMap(map);//获取私有或者公有订单的投保方案
			List<PremiumDetail> ilist = new ArrayList<PremiumDetail>();
			PremiumDetail d = null;
			Integer askFlag = Constant.PREMIUM_NO_ORDER;
			for(int i=0;i<detail.size();i++){
				d = detail.get(i);
				//----------------------------------------处理A险和A险不计免赔合二为一------------start----------------------
				if(d.getPrmCode().contains("_BJMP")){
					String s = d.getPrmCode().replaceAll("_BJMP$", "");
					for(PremiumDetail p:detail){
						if(p.getPrmCode().equals(s)){
							p.setSelected(true);
							//合算A险和A险不计免赔
							Double newValue = BigDecimalUtil.add(p.getPrmValue()==null?0:p.getPrmValue().doubleValue(), d.getPrmValue()==null?0:d.getPrmValue().doubleValue());
							p.setPrmValue(BigDecimal.valueOf(newValue).setScale(2, BigDecimal.ROUND_HALF_UP));
							break;
						}
					}
				}else{
					ilist.add(d);
				}
				//----------------------------------------处理A险和A险不计免赔合二为一------------end----------------------
				
			}
			//----------------------------------------给订单添加标识判断，是否询价成功------------start----------------------
			if(detail.size()>0){
				askFlag = this.checkDetailPrice(order.getId().toString());
			}
			//----------------------------------------给订单添加标识判断，是否询价成功------------end----------------------
			order.setBiInsurerCom(PinYinUtil.getPingYin(order.getBiInsurerCom()));
			order.setCiInsurerCom(order.getBiInsurerCom());
			jsonstr = JSONObject.toJSONString(order).replaceAll("}$", "");
			jsonstr += ",\"isSuccess\":" + askFlag;
			jsonstr += ",\"PremiumDetail\":";
			jsonstr += JSONObject.toJSONString(ilist);
			jsonstr += "}";
		}else{
			jsonstr += "{\"isSuccess\":"+Constant.PREMIUM_NO_ORDER+"}";
		}
		return jsonstr;
	}
	

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public boolean updatePremiumOrderInfo(Map<String,Object> map) {
		PremiumOrder po = null;
		Integer contentFlag = Integer.valueOf(map.get("contentFlag").toString());
		String carNo = map.get("carNo").toString();
		String city = map.get("city").toString();
		Integer userId = Integer.valueOf(map.get("userId").toString());
		Integer orderId = Integer.valueOf(map.get("orderId").toString());
		if(orderId!=null){
			po = this.selectById(orderId);
		}else{
			Map<String,Object> mpby = new HashMap<String,Object>();
			mpby.put("carNo", carNo);
			mpby.put("userId", userId);
			//根据车牌号获取订单信息
			po = this.getOnePoByMap(mpby);
		}
		PremiumOrder iorder = null;
		boolean f = false;
		if(po==null){
			String ciInsurerCom = map.get("ciInsurerCom").toString();
			iorder = new PremiumOrder();
			iorder.setCiInsurerCom(this.getBaoXianStr(ciInsurerCom));
			iorder.setBiInsurerCom(iorder.getCiInsurerCom());
			iorder.setLicenseNo(carNo);
			iorder.setStatus(Constant.PREMIUM_ORDER_STATUS_CREATED);
			iorder.setCity(city);
			iorder.setCreatedTime(new Date());
			iorder.setUserId(userId);
			//生成订单
			f = this.insert(iorder);
			orderId = iorder.getId();
		}else{
			orderId = po.getId();
		}
		if(Constant.PREMIUM_CHANGE_ORDER_DETAIL==contentFlag){
			//------↓-------------↓-------↓----------------------更改投保方案
			//新投保方案
			List<PremiumDetail> nList = new ArrayList<PremiumDetail>();
			
			nList = (List<PremiumDetail>) map.get("PremiumDetail");
			f = this.updatePremiumDetail(orderId.toString(), nList);
		}else if(Constant.PREMIUM_CHANGE_ORDER_COMPANYNAME==contentFlag){
			//----------------↓-------↓----------↓--------------更换保险公司
			String ciInsurerCom = map.get("ciInsurerCom").toString();
			f = this.updateInsurecom(ciInsurerCom, orderId.toString());
		}else if(Constant.PREMIUM_CHANGE_ORDER_INSURER==contentFlag){
			//----------------↓-------↓------------↓-------------修改投保人信息
			PersonMsg pm = (PersonMsg) map.get("personMsg");
			f = this.updatePersonMsg(orderId.toString(), pm);
		}
		return f;
	}
	
	@Override
	public AskPrice askPremiumPrice(String ciInsurerCom, String carNo, List<PremiumDetail> details) {
		String url = constPro.MMH_NET_URL + "/Premium/askPrice";
		//封装询价所需参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ciInsurerCom", ciInsurerCom);
		map.put("carNo", carNo);
		map.put("details", JSONUtil.toJSONString(details));
		String data = JSONUtil.toJSONString(map);
		map.clear();
		map.put("data", data);
		
		String result = "";
		try {
			result = HttpUtils.httpPostRequest(url, map);//询价接收询价结果
			AskPrice ap = null;
//			result = "{\"detailList\":[{\"premiumType\":\"500000\",\"prmCode\":\"DSFZRX_BJMP\",\"prmValue\":\"159.46\"},{\"premiumType\":\"true\",\"prmCode\":\"CSX\",\"prmValue\":\"1767.16\"},{\"premiumType\":\"10000\",\"prmCode\":\"CKX\",\"prmValue\":\"0.01\"},{\"premiumType\":\"50000\",\"prmCode\":\"SJX_BJMP\",\"prmValue\":\"22.09\"},{\"premiumType\":\"50000\",\"prmCode\":\"SJX\",\"prmValue\":\"147.26\"},{\"premiumType\":\"true\",\"prmCode\":\"CCS\",\"prmValue\":\"\"},{\"premiumType\":\"true\",\"prmCode\":\"JQX\",\"prmValue\":\"\"},{\"premiumType\":\"10000\",\"prmCode\":\"CKX_BJMP\",\"prmValue\":\"\"},{\"premiumType\":\"500000\",\"prmCode\":\"DSFZRX\",\"prmValue\":\"1063.10\"},{\"premiumType\":\"2000\",\"prmCode\":\"GHX_BJMP\",\"prmValue\":\"64.16\"},{\"premiumType\":\"2000\",\"prmCode\":\"GHX\",\"prmValue\":\"427.76\"},{\"premiumType\":\"true\",\"prmCode\":\"CSX_BJMP\",\"prmValue\":\"265.07\"}],\"isSuccess\":200}";
			if(result.contains("502")){
				ap = new AskPrice();
				ap.setIsSuccess(Constant.PREMIUM_ASK_PRICE_FAILE);
				ap.setDetailList(null);
				ap.setCiInsurerCom(ciInsurerCom);
				logger.info("询价失败------>>"+result);
			}else if(result.contains("404")){
				ap = new AskPrice();
				ap.setIsSuccess(Constant.PREMIUM_ASK_PRICE_FAILE);
				ap.setCiInsurerCom(ciInsurerCom);
				ap.setDetailList(null);
				logger.info("未找到询价接口------>>"+result);
			}else if(result.contains("504")){
				int maxCount = 2;
				int currentCount = 0;
				while(currentCount < maxCount){
				        result = HttpUtils.httpPostRequest(url, map);
				        if (result.contains("504")){ // 请求不成功
				                 currentCount++;
				                 Thread.sleep(10000);// 等待10s
				        }else{
				        	break;
				        }
				}
				if(result.contains("isSuccess")){
		        	ap = JSONObject.parseObject(result, AskPrice.class);//处理询价结果
		        }else{
		        	ap = new AskPrice();
		        	ap.setIsSuccess(Constant.PREMIUM_ASK_PRICE_FAILE);
		        	ap.setDetailList(null);
		        	logger.info("询价超时------>>"+result);
		        }
				ap.setCiInsurerCom(ciInsurerCom);
			}else if(result.contains("isSuccess")){
				ap = JSONObject.parseObject(result, AskPrice.class);//处理询价结果
			}else{
				ap = new AskPrice();
				ap.setIsSuccess(Constant.PREMIUM_ASK_PRICE_FAILE);
				ap.setDetailList(null);
				ap.setCiInsurerCom(ciInsurerCom);
				logger.info("询价失败------>>"+result);
			}
			return ap;
		} catch (UnsupportedEncodingException | InterruptedException e) {
			logger.error(map,e);
			return null;
		}
	}
	
	@Transactional
	@Override
	public ResponseInfo addNewPremiumOrderByDefault(String carNo,String userId,String insurerCom) {
		if(StringUtil.isNotEmpty(insurerCom)){
			constPro.INSURER_COM = getBaoXianStr(insurerCom);
		}
		ResponseInfo result = null; 
		boolean f = false;
		//-----------------------------------------------根据配置文件创建新订单
		PremiumOrder norder = new PremiumOrder();
		
		Map<String,Object> mapby = new HashMap<String,Object>();
		mapby.put("license_no", carNo);
		mapby.put("status", 0);
		List<CarInfo> cilist = carInfoService.selectByMap(mapby);
		
		//--------------------------------------------------创建公有订单
		if(cilist.size()==1){
			//有效车辆为一辆时候根据车辆信息创建公有订单
			CarInfo ci = cilist.get(0);
			norder.setCarId(ci.getId());
			norder.setUserId(Constant.PUBLIC_ORDER_USERID);
			norder.setLicenseNo(ci.getLicenseNo());//车牌号
			norder.setCity(constPro.CITY);//行驶城市
			norder.setBiStartDate(ci.getPrmEndTime());//商业险起保时间
			norder.setCiStartDate(ci.getPrmEndTime());//交强险起保时间
			norder.setBiEndDate(DateUtil.addYear(ci.getPrmEndTime(), constPro.DEFAULT_PREMIUM_YEAR_NUM));//商业险结束时间
			norder.setCiEndDate(DateUtil.addYear(ci.getPrmEndTime(), constPro.DEFAULT_PREMIUM_YEAR_NUM));//交强险结束时间
			norder.setCiInsurerCom(constPro.INSURER_COM);//交强险保险人
			norder.setBiInsurerCom(constPro.INSURER_COM);//商业险险保险人
			norder.setOwnerName(ci.getCarOwner());//车主姓名
			norder.setOwnerIdNo(ci.getOwnerNo());//车主身份证号
			norder.setInsuredName(ci.getCarOwner());//被保人姓名
			norder.setInsuredIdNo(ci.getOwnerNo());//被保人身份证号
			norder.setStatus(Constant.PREMIUM_ORDER_STATUS_CREATED);//订单状态:已创建
			norder.setPayType(Constant.PREMIUM_ORDER_PAYTYPE_DEFAULT);//默认投保方式
			norder.setCreatedTime(new Date());
			
			//存入公有订单
			f = this.insert(norder);
			
		}
		
		//根据默认订单id,获取默认投保方案
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("prm_order_id", constPro.DEFAULT_ORDER_ID);
		
		//获取源-默认投保方案
		List<PremiumDetail> nList = premiumDetailService.selectByMap(map);
		
		//获取公有订单默认方案
		List<PremiumDetail> pdnList = new ArrayList<PremiumDetail>();
		PremiumDetail pdn = null;
		String[] ignorArr = {"id","prm_order_id","created_time"};
		for (PremiumDetail n : nList) {
			pdn = new PremiumDetail();
			BeanUtils.copyProperties(n, pdn, ignorArr);
			pdn.setPrmOrderId(norder.getId());
			pdn.setCreatedTime(new Date());
			f = premiumDetailService.insertSelective(pdn);
			pdn.setCreatedTime(null);
			pdnList.add(pdn);
			if(!f){
				break;
			}
		}
		
		//根据共有订单默认方案询价并更新公有订单
		f = this.updateOrderByAskPrice(norder.getId().toString(), pdnList);
		
		//给订单添加标识判断
		Integer askFlag = this.checkDetailPrice(norder.getId().toString());
		
		if(askFlag.intValue()==Constant.PREMIUM_PRICE_ALL_SUCCESS.intValue()||askFlag.intValue()==Constant.PREMIUM_PRICE_BI_SUCCESS_CI_FAIL.intValue()||askFlag.intValue()==Constant.PREMIUM_PRICE_CI_SUCCESS_BI_FAIL.intValue()){
			if(StringUtil.isNotEmpty(userId)){
				//创建私有订单
				String childOrderId = this.saveCopyOrder(norder.getId().toString(), userId);
				if(StringUtil.isNotEmpty(childOrderId)){
					f = true;
				}else{
					f = false;
				}
			}
		}
		if(f){
			result = ResponseInfo.SUCCESS;
		}else{
			result = ResponseInfo.ACTION_FAIL;
		}
		return result;
	}

	@Override
	public ResponseInfo checkCarNo(String carNo, String username) {
		ResponseInfo result = null;
		String resultstr = "";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("username", username);
		map.put("carNo", carNo);
		
		String url = constPro.MMH_NET_URL + "/carinfo/get/" + carNo;
		try {
			resultstr = HttpUtils.httpGetRequest(url, map);
			JSONObject jo = JSON.parseObject(resultstr);
            String carOwner = jo.getString("carOwnerBC");
            if (username.equals(carOwner)) {
            	result = ResponseInfo.SUCCESS;
            } else {
            	result = ResponseInfo.ACTION_FAIL;
            }
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return ResponseInfo.ACTION_FAIL;
		}
		return result;
	}

	@Override
	public Map<String, Object> getLowestInfo(String carNo, List<PremiumDetail> details) {
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("lowestLnsurer", "太平洋保险");
		 map.put("lowestValue", 4800.28);
		return map;
	}

	@Override
	public PremiumOrder getOnePoByMap(Map<String, Object> map) {
		return baseMapper.getOnePoByMap(map);
	}

	@Override
	public Page<PremiumOrder> getPremiumOrderList(Page<PremiumOrder> page, PremiumOrder po) {
		List<PremiumOrder> polist = baseMapper.getPremiumOrderList(page, po);
		for(PremiumOrder p:polist){
			p.setOrderStatus(this.getStatusStr(p.getStatus()));
		}
		page.setRecords(polist);
		return page;
	}

	@Override
	public PremiumOrder getPremiumOrderById(@Param(value = "id") String id) {
		return baseMapper.getPremiumOrderById(id);
	}

	@Transactional
	@Override
	public String savePremiumOrder(PremiumOrder po, List<PremiumDetail> pdlist) {
		String result = "";
		boolean f = false;
		po.setCity(constPro.CITY);
		
		//************************创建私有订单****************************************start******************************************
		po.setStatus(Constant.PREMIUM_ORDER_STATUS_APPOINTMENT);
		po.setBiInsurerCom(po.getCiInsurerCom());
		Map<String,Object> map = new HashMap<String,Object>();
    	map.put("userId", po.getUserId());
    	map.put("licenseNo", po.getLicenseNo());
    	CarUserRel cur = carUserRelService.getCarUserRelByMap(map);
    	
    	//创建私有订单时，自动关联用户默认联系地址______________________________start_____________________________________
    	Map<String,Object> m = new HashMap<String,Object>();
    	map.put("user_id", po.getUserId());
    	m.put("is_default", Constant.USER_CONTACT_IS_DEFAULT);
    	List<UserContact> uclist = userContactService.selectByMap(m);
    	if(uclist.size()>0){
    		po.setContactId(uclist.get(0).getId());
    	}
    	//创建私有订单时，自动关联用户默认联系地址________________________________end_________________________________________
    	if(cur!=null){
    		po.setCarId(cur.getCarId());
    		po.setOwnerName(cur.getCarOwner());
    		f = this.insertSelective(po);
    		//生成订单号
    		String orderNo = OrderUtils.genOrderNo("premium",po.getId().toString());
    		PremiumOrder upNoWhere = new PremiumOrder(); 
    		upNoWhere.setOrderNo(orderNo);
    		upNoWhere.setId(po.getId());
    		f = this.updateSelectiveById(upNoWhere);
    		//************************创建私有订单***********************************************end**********************************************************
    		
    		//所有方案
    		Map<String,Object> mapby = new HashMap<String,Object>();
    		List<Premium> plist = premiumService.selectPremiumListByMap(mapby);
    		
    		Double biValue = 0.00;//商业险
    		BigDecimal ciValue = new BigDecimal(0.00);//交强险
    		BigDecimal taxValue = new BigDecimal(0.00);//车船税
    		
    		//方案--询价结果
    		List<PremiumDetail> nList = premiumDetailService.getFullDetail(pdlist);
    		for(PremiumDetail n:nList){
    			//给投保方案绑定订单id
    			n.setPrmOrderId(po.getId());
    			
    			n.setPrmValue(new BigDecimal(0));
    			
    			//给投保方案设置--方案id
    			for(Premium p:plist){
    				if(p.getPrmCode().equals(n.getPrmCode())&&p.getPrmName().equals(n.getPrmName())){
    					n.setPrmId(p.getId());
    				}
    			}
    			f = premiumDetailService.insertSelective(n);
    		}
    		
    		//更新订单价格
    		PremiumOrder whereEntity = new PremiumOrder();
    		whereEntity.setId(po.getId());
    		whereEntity.setCiValue(ciValue);//交强险
    		whereEntity.setUserId(po.getUserId());
    		whereEntity.setBiValue(new BigDecimal(biValue));//商业险
    		whereEntity.setTaxValue(taxValue);//车船税
    		//无报价
    		//订单价格清零
    		whereEntity.setId(po.getId());
    		//价格清零
//    		whereEntity.setBiValue(new BigDecimal(0));
//    		whereEntity.setCiValue(new BigDecimal(0));
//    		whereEntity.setTaxValue(new BigDecimal(0));
    		whereEntity.setPrmValue(new BigDecimal(0.00));
    		whereEntity.setStatus(Constant.PREMIUM_ORDER_STATUS_APPOINTMENT);
    		
    		f = this.updateSelectiveById(whereEntity);		
    		if(f){
    			result = po.getId().toString();
    		}else{
    			result = null;
    		}
    	}else{
    		result = null;
    	}
		
		return result;
	}

	@Transactional
	@Override
	public boolean addPremiumToOrder(String orderId,String premiumId) {
		boolean f = false;
		String[] ids = premiumId.split(",");
		PremiumDetail pd = new PremiumDetail();
		Premium pm = new Premium();
		for(String id:ids){
			pm = premiumService.selectById(id);
			pd.setPrmName(pm.getPrmName());
			pd.setPrmCode(pm.getPrmCode());
			pd.setPrmId(pm.getId());
			pd.setPrmOrderId(Integer.valueOf(orderId));
			pd.setPrmValue(new BigDecimal(0));
			f = premiumDetailService.insert(pd);
		}
		return f;
	}

	@SuppressWarnings("static-access")
	@Override
	public Integer isDuringPremiumTime(String licenseNo) {
		Integer result = 0;
		CarInfo ci = new CarInfo();
		Map<String,Object> map = new HashMap<String,Object>();
    	map.put("license_no", licenseNo);
    	map.put("status", 0);
    	
    	List<CarInfo> cilist = carInfoService.selectByMap(map);
    	if(cilist.size()==1){
    		 ci = cilist.get(0);
    		 Date dBf = new Date();
    		 Calendar calendar = Calendar.getInstance(); //得到日历
    		 calendar.setTime(new Date());//把当前时间赋给日历
    		 calendar.add(calendar.MONTH, 1);  //设置为后1月
    		 dBf = calendar.getTime();   //得到后1月的时间
    		 //ci.getPrmEndTime().getTime()>DateUtil.StringToDate(dBfstr).getTime()&&
    		 if(ci.getPrmEndTime()!=null){
    			 if(ci.getPrmEndTime().getTime()<dBf.getTime()){
    				 result = Constant.PREMIUM_DURING_INSURE;//在投保期内
    			 }else{
    				 result = Constant.PREMIUM_OUTOF_INSURE;//保期外
    			 }
    		 }else{
    			 result = Constant.PREMIUM_NOSURE_INSURE;//没有保险到期时间，即无法确定是否在保期内
    		 }
    	}
		return result;
	}

	@Transactional
	@Override
	public boolean saveBackPremiumOrder(PremiumOrder po) {
		boolean f = false;
		po.setBiInsurerCom(po.getCiInsurerCom());
		f = this.updateSelectiveById(po);
		if(f){
			PremiumOrder oldPo = new PremiumOrder();
			oldPo = this.selectById(po.getId());
			if(StringUtil.isNotEmpty(oldPo.getInsureNo())&&StringUtil.isNotEmpty(oldPo.getPreInsureNo())&&StringUtil.isNotEmpty(oldPo.getTransacNo())){
				//推送微信消息，出单成功
				if(oldPo.getStatus().intValue()==Constant.PREMIUM_ORDER_STATUS_PAYSUCCESS){
					//更新订单状态为【已完成】
					oldPo.setStatus(Constant.PREMIUM_ORDER_STATUS_COMPLETED);
					f = this.updateSelectiveById(oldPo);
					if(f){
						// 封装车险到期提醒模板数据
						WechatTemplateMsg wtm = new WechatTemplateMsg();
						
						//获取待推送订单对象信息
						PremiumOrder newPo = new PremiumOrder();
						newPo = this.getWaitPushPrmIssue(po.getId().toString());
						
						if (newPo!=null) {
								wtm.setTemplate_id(constPro.PRM_ISSUE_TEMPLATE_ID);
								
								wtm.setTouser(newPo.getOpenId());
								wtm.setUrl(constPro.BASE_WEIXIN_URL+"/wx/insureOrderDetail/"+newPo.getId());

								TreeMap<String, String> licenseNo = WechatTemplateMsg.item(newPo.getLicenseNo(), "#173177");
								TreeMap<String, String> insurerCom = WechatTemplateMsg.item(newPo.getInsurerCom(), "#173177");
								TreeMap<String, String> prmValue = WechatTemplateMsg.item(newPo.getPrmValue().toString(), "#173177");
								TreeMap<String, String> insureNo = WechatTemplateMsg.item(newPo.getInsureNo(), "#173177");

								TreeMap<String, TreeMap<String, String>> data = new TreeMap<String, TreeMap<String, String>>();
								data.put("licenseNo", licenseNo);
								data.put("insureNo", insureNo);
								data.put("insurerCom", insurerCom);
								data.put("prmValue", prmValue);
								wtm.setData(data);

								// 推送模板
								TemplateMsgResult tmr = messageService.sendTemplateMessage(wtm);
								if(tmr.getErrcode()==0){
									//微信推送模板成功，修改订单状态为【推送】
									logger.info("已完成订单，订单Id:"+newPo.getId()+"-----推送成功！");
								}else{
									logger.info("已完成订单，订单Id:"+newPo.getId()+"----推送失败！错误信息："+tmr.getErrcode()+"---"+tmr.getMsgid()+"--"+tmr.getErrmsg());
								}
						}
					}
				}
			}
			
		}
		
		return f;
	}

	@Transactional
	@Override
	public boolean addPriceToPremium(PremiumOrder po) {
		boolean f = false;
		String[] amountstr = po.getAmountstr().split(",");
		String[] prmValueStr = po.getPrmValueStr().split(",");
		PremiumDetail pdentity = null;
		PremiumDetail pdwhere = null;
		Integer index = null;
		String adetailId = "";
		String pdetailId = "";
		String prmValue = "";
		for(String amount:amountstr){
			pdentity = new PremiumDetail();
			pdwhere = new PremiumDetail();
			index = amount.indexOf("|");
			adetailId = amount.substring(0,index);
			pdwhere.setId(Integer.valueOf(adetailId));
			pdentity.setAmount(amount.substring(index+1, amount.length()));
			for(String pvs:prmValueStr){
				index = pvs.indexOf("|");
				pdetailId = pvs.substring(0,index);
				prmValue = pvs.substring(index+1, pvs.length());
				if(adetailId.equals(pdetailId)){
					BigDecimal bd = new BigDecimal(prmValue);
					pdentity.setPrmValue(bd);
				}
			}
			f = premiumDetailService.updateSelective(pdentity, pdwhere);
		}
		
		PremiumOrder poentity = this.autoCount(po);
		
		//核对价格全部完整时候，更新主订单价格，并更新主订单状态为【有报价】
		Integer checkFlag = this.checkDetailPrice(po.getId().toString());
		if(f&&checkFlag.intValue()==Constant.PREMIUM_PRICE_ALL_SUCCESS){
			poentity.setStatus(Constant.PREMIUM_ORDER_STATUS_HAVEPRICE);
			f = this.updateSelectiveById(poentity);
		}
		return f;
	}
	@Override
	public String getStatusStr(Integer status) {
		String result ="";
		for(PremiumOrderStatus s:PremiumOrderStatus.values()){
			if(status.equals(s.getValue())){
				result = s.getDescription();
			}
		}
		return result;
	}
	
	@Override
	public String getBaoXianStr(String pingyin) {
		String result ="";
		for(BaoXianUtil baoxian:BaoXianUtil.values()){
			if(pingyin.equals(baoxian.getPinyin())){
				result = baoxian.getChinese();
			}
		}
		return result;
	}

	@Transactional
	@Override
	public Integer updateConfirmPush(String orderId) {
		Integer result = -1;
		boolean f = false;
		Integer checkFlag = this.checkDetailPrice(orderId);
		if (checkFlag.intValue()==Constant.PREMIUM_PRICE_ALL_SUCCESS.intValue()) {//价格完整
			PremiumOrder entity = new PremiumOrder();
			entity.setId(Integer.valueOf(orderId));
			PremiumOrder oldPo = this.selectById(orderId);
			if (oldPo.getStatus().equals(Constant.PREMIUM_ORDER_STATUS_ALREADYPUSH)) {
				// 已经推送过，不可再推送
				result = Constant.STATUS_SUCCESS;
			} else {
				//获取待推送对象列表
				PremiumOrder powhere = new PremiumOrder();
				powhere.setId(Integer.valueOf(orderId));
				if (oldPo.getUserId() != 0) {
					//userId不为0，是私有订单，否则推送公有订单
					powhere.setUserId(oldPo.getUserId());
				}
				List<PremiumOrder> poList = this.getWaitPushPrmPrice(powhere);


				// 封装报价成功提醒模板数据
				WechatTemplateMsg wtm = null;
				
				if (poList.size() > 0) {
					for (PremiumOrder po : poList) {
						wtm = new WechatTemplateMsg();
						wtm.setTemplate_id(constPro.PRM_PRICE_TEMPLATE_ID);
						wtm.setTouser(po.getOpenId());
						wtm.setUrl(constPro.BASE_WEIXIN_URL+"/wx/onlineInsurance/"+po.getId());

						TreeMap<String, String> licenseNo = WechatTemplateMsg.item(po.getLicenseNo(), "#173177");
						TreeMap<String, String> insurerCom = WechatTemplateMsg.item(po.getCiInsurerCom(), "#173177");
						TreeMap<String, String> prmValue = WechatTemplateMsg.item(po.getPrmValue().toString(), "#173177");
						TreeMap<String, String> taxValue = WechatTemplateMsg.item(po.getTaxValue().toString(), "#173177");
						TreeMap<String, String> ciValue = WechatTemplateMsg.item(po.getCiValue().toString(), "#173177");
						TreeMap<String, String> biValue = WechatTemplateMsg.item(po.getBiValue().toString(), "#173177");

						TreeMap<String, TreeMap<String, String>> data = new TreeMap<String, TreeMap<String, String>>();
						data.put("licenseNo", licenseNo);
						data.put("insurerCom", insurerCom);
						data.put("prmValue", prmValue);
						data.put("taxValue", taxValue);
						data.put("ciValue", ciValue);
						data.put("biValue", biValue);
						wtm.setData(data);

						// 推送模板
						TemplateMsgResult tmr = messageService.sendTemplateMessage(wtm);
						if(tmr.getErrcode()==0){
							//微信推送模板成功，修改订单状态为【推送】
							entity.setStatus(Constant.PREMIUM_ORDER_STATUS_ALREADYPUSH);
							f = this.updateSelectiveById(entity);
							if (f) {
								result = Constant.STATUS_SUCCESS;
							} else {
								result = Constant.STATUS_FAIL;
							}
						}else{
							result = Constant.STATUS_FAIL;
						}
					}
				}
			}
		}else {//价格不完整
			result = Constant.PREMIUM_ORDER_DETAIL_HAVENO_PRICE;
		}
		
		return result;
	}
	
	@Transactional
	@Override
	public ResponseInfo savePaySuccess(String id) {
		ResponseInfo result = null;
		boolean f = false;
		// 修改订单信息到数据库
		PremiumOrder iorder = new PremiumOrder();
		//支付成功时候订单状态改为----支付成功
		iorder.setId(Integer.valueOf(id));
		iorder.setStatus(Constant.PREMIUM_ORDER_STATUS_PAYSUCCESS);
		f = this.updateSelectiveById(iorder);
		if(f){
			result = ResponseInfo.SUCCESS;
		}else{
			result = ResponseInfo.ACTION_FAIL;
		}
		return result;
	}

	@Override
	public PremiumOrder saveLookPremiumOrder(String orderId,String licenseNo, String userId) {
		boolean f = false;
		PremiumOrder ponew  = null;
		PremiumOrder po = null;
		PremiumOrder result = null;
		if(orderId!=null){
//			Ⅰ、只传orderId和userId：
//					①从订单列表进入；②从微信公众号推送连接进入
			po = this.selectById(Integer.valueOf(orderId));
			if(po!=null){
				if(po.getUserId().toString().equals(userId.toString())){//确定是私有订单
					if(po.getStatus().equals(Constant.PREMIUM_ORDER_STATUS_ALREADYPUSH)){
						f = this.updateStatus(orderId, Constant.PREMIUM_ORDER_STATUS_ALREADYSEE);
					}
				}else{//暂无当前用户的私有订单
					ponew  = new PremiumOrder();
					if(po.getStatus().equals(Constant.PREMIUM_ORDER_STATUS_ALREADYPUSH)){
						String[] ignoreProperties = {"id","status","createdTime"};
						BeanUtils.copyProperties(po, ponew, ignoreProperties);
						ponew.setStatus(Constant.PREMIUM_ORDER_STATUS_ALREADYSEE);
						ponew.setCreatedTime(new Date());
						f = this.insertSelective(ponew);
					}
					if(f){
						result = ponew;
					}else{
						result = null;
					}
				}
			}else{
				return null;
			}
		}else{
//			Ⅱ、只传licenseNo、userId:
//				点击在线投保进入。
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("license_no", licenseNo);
			map.put("user_id", userId);
			List<PremiumOrder> polist = this.selectByMap(map);
			if(polist.size()==1){
				po = polist.get(0);
				if(po!=null){
					ponew  = new PremiumOrder();
					if(po.getStatus().equals(Constant.PREMIUM_ORDER_STATUS_ALREADYPUSH)){
						f = this.updateStatus(po.getId().toString(), Constant.PREMIUM_ORDER_STATUS_ALREADYSEE);
					}
					if(f){
						result = ponew;
					}else{
						result = null;
					}
				}else{
					result = null;
				}
			}
		}
		return result;
	}

	@Override
	public PremiumOrder autoCount(PremiumOrder po) {
		String[] prmValueStr = po.getPrmValueStr().split(",");
		Integer index = null;
		String prmValue = "";
		Double biValue = 0.00;
		Double ciValue = 0.00;
		Double taxValue = 0.00;
		Double discountMoney = 0.00;
		String detailId="";
		PremiumDetail pd = null;
		for(String pvs:prmValueStr){
			index = pvs.indexOf("|");
			detailId = pvs.substring(0, index);
			prmValue = pvs.substring(index+1, pvs.length());
			pd = new PremiumDetail();
			pd = premiumDetailService.selectById(detailId);
			if(pd.getPrmName().equals("交强险")){
				ciValue+=Double.valueOf(prmValue);
			}else if(pd.getPrmName().equals("车船税")){
				taxValue+=Double.valueOf(prmValue);
			}else{
				biValue+=Double.valueOf(prmValue);
			}
		}
		BigDecimal cibd = new BigDecimal(ciValue);
		BigDecimal taxbd = new BigDecimal(taxValue);
		BigDecimal bibd = new BigDecimal(biValue);
		po.setBiValue(bibd);
		po.setCiValue(cibd);
		po.setTaxValue(taxbd);
		
		//商业险折扣的金额
		discountMoney = po.getBiValue().doubleValue()*(100-Double.valueOf(po.getDiscount()))/100;
		Double money1 = BigDecimalUtil.add(po.getBiValue().doubleValue(), po.getCiValue().doubleValue());
		Double money2 = BigDecimalUtil.add(money1, po.getTaxValue().doubleValue());
		Double realMoney = BigDecimalUtil.add(money2, -discountMoney);
		po.setDiscountMoney(new BigDecimal(discountMoney));
		po.setPrmValue(new BigDecimal(realMoney));
		return po;
	}

	@Override
	public boolean updateStatus(String orderId, Integer status) {
		PremiumOrder po = this.selectById(orderId); 
		po.setStatus(status);
		boolean f = this.updateSelectiveById(po);
		return f;
	}

	@Override
	public Integer updateOrderCanPay(String orderId) {
		PremiumOrder order = this.selectById(orderId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("prm_order_id", orderId);
		List<PremiumDetail> detail = premiumDetailService.selectByMap(map);
		PremiumDetail d = null;
		int count = 0;
		for(int i=0;i<detail.size();i++){
			d = detail.get(i);
			//----------------------------------------给订单添加标识判断，是否询价成功------------start----------------------
			if(d.getPrmValue().compareTo(new BigDecimal(0))==0){
				count++;
			}
			//----------------------------------------给订单添加标识判断，是否询价成功------------end----------------------
		}
		boolean f = false;
		PremiumOrder po = new PremiumOrder();
		po.setId(Integer.valueOf(orderId));
		//判断有无报价
		if(count>0){
			//有无报价
			po.setStatus(Constant.PREMIUM_ORDER_STATUS_APPOINTMENT);
			f = this.updateSelectiveById(po);
		}else{
			//都有报价
			if(order.getStatus().toString().equals(Constant.PREMIUM_ORDER_STATUS_FINISHINFO.toString())){
				//如果完成投保信息时，修改订单状态为待支付
				po.setStatus(Constant.PREMIUM_ORDER_STATUS_PAYWAITTING);
				f = this.updateSelectiveById(po);
			}
		}
		if(f){
			order = this.selectById(orderId);
			return order.getStatus();
		}else{
			return order.getStatus();
		}
	}


	@Transactional
	@Override
	public boolean updateOrderByAskPrice(String orderId,List<PremiumDetail> nList) {
		PremiumOrder po = new PremiumOrder();
		boolean f = false;
		po = this.selectById(orderId);
		if(po!=null){
			//询价结果
			AskPrice ask = this.askPremiumPrice(po.getCiInsurerCom(), po.getLicenseNo(), nList);
			
			//所有方案
			Map<String,Object> mapby = new HashMap<String,Object>();
			List<Premium> plist = premiumService.selectPremiumListByMap(mapby);
			
			Double biValue = 0.00;//商业险
			BigDecimal ciValue = new BigDecimal(0.00);//交强险
			BigDecimal taxValue = new BigDecimal(0.00);//车船税
			
			//方案--询价结果
			List<PremiumDetail> $nlist = ask.getDetailList();
			for(PremiumDetail n:nList){
				//给投保方案绑定订单id
				n.setPrmOrderId(po.getId());
				
				if(ask.getIsSuccess()==Constant.PREMIUM_ASK_PRICE_SUCCESS.intValue()){
					//给投保方案设置--价格
					for(PremiumDetail $n:$nlist){
						if(n.getPrmCode().equals($n.getPrmCode())&&n.getPrmName().equals($n.getPrmName())){
							//code和名字都一致时候，获取价格
							//询价成功时候设置---价格
							n.setPrmValue($n.getPrmValue()==null?new BigDecimal(0):$n.getPrmValue().setScale(2, BigDecimal.ROUND_HALF_UP));
							//给共有订单设置价格
							if("交强险".equals($n.getPrmName())&&"JQX".equals($n.getPrmCode())){
								ciValue = $n.getPrmValue()==null?new BigDecimal(0):$n.getPrmValue().setScale(2, BigDecimal.ROUND_HALF_UP);
							}else if("车船税".equals($n.getPrmName())&&"CCS".equals($n.getPrmCode())){
								taxValue = $n.getPrmValue()==null?new BigDecimal(0):$n.getPrmValue().setScale(2, BigDecimal.ROUND_HALF_UP);
							}else{
								biValue = BigDecimalUtil.add(biValue, $n.getPrmValue().doubleValue());
							}
						}
					}
				}else{
					n.setPrmValue(new BigDecimal(0.00));
				}
				//给投保方案设置--方案id
				for(Premium p:plist){
					if(p.getPrmCode().equals(n.getPrmCode())&&p.getPrmName().equals(n.getPrmName())){
						n.setPrmId(p.getId());
					}
				}
				f = premiumDetailService.insertOrUpdateSelective(n);
			}
			
			//更新订单价格
			PremiumOrder whereEntity = new PremiumOrder();
			whereEntity.setId(po.getId());
			
			whereEntity.setCiValue(ciValue.setScale(2, BigDecimal.ROUND_HALF_UP));//交强险
			whereEntity.setUserId(po.getUserId());
			whereEntity.setBiValue((new BigDecimal(biValue)).setScale(2, BigDecimal.ROUND_HALF_UP));//商业险
			whereEntity.setTaxValue(taxValue.setScale(2, BigDecimal.ROUND_HALF_UP));//车船税
			whereEntity.setBiValue(new BigDecimal(biValue*constPro.DISCOUNT_VALUE));//商业险
			
			Double prmValue = BigDecimalUtil.add(ciValue.doubleValue(), taxValue.doubleValue()) ;
			prmValue =BigDecimalUtil.add(prmValue, biValue*constPro.DISCOUNT_VALUE) ;
			
			whereEntity.setPrmValue(BigDecimal.valueOf(prmValue).setScale(2, BigDecimal.ROUND_HALF_UP));//本期实收保费
			
			f = this.updateSelectiveById(whereEntity);
//			if(Constant.PREMIUM_ASK_PRICE_SUCCESS==ask.getIsSuccess()){
			Integer checkFlag = this.checkDetailPrice(po.getId().toString());
			//判断公有私有订单
			if(po.getUserId().intValue()>0){//私有订单
				if(checkFlag.intValue()==Constant.PREMIUM_PRICE_ALL_SUCCESS){
					//有报价
					//计算本期实收保费
					//状态=3000，有投保信息变4200：完善投保信息；无投保信息状态变4100，已查看；否则状态不修改
					if(po.getStatus().intValue()==Constant.PREMIUM_ORDER_STATUS_APPOINTMENT){
						if(po.getContactId()!=null){
							//有联系人信息
							whereEntity.setStatus(Constant.PREMIUM_ORDER_STATUS_FINISHINFO);
						}else{
							whereEntity.setStatus(Constant.PREMIUM_ORDER_STATUS_ALREADYSEE);
						}
					}
				}else if(checkFlag.intValue()==Constant.PREMIUM_PRICE_BI_SUCCESS_CI_FAIL||checkFlag.intValue()==Constant.PREMIUM_PRICE_CI_SUCCESS_BI_FAIL){
					//部分无报价
					if(po.getContactId()!=null){
						//有联系人信息
						whereEntity.setStatus(Constant.PREMIUM_ORDER_STATUS_FINISHINFO);
					}else{
						whereEntity.setStatus(Constant.PREMIUM_ORDER_STATUS_ALREADYSEE);
					}
				}else{
					//无报价
					//订单价格清零
					whereEntity.setId(Integer.valueOf(orderId));
					//价格清零
					
					whereEntity.setBiValue(new BigDecimal(0));
					whereEntity.setCiValue(new BigDecimal(0));
					whereEntity.setTaxValue(new BigDecimal(0));
					whereEntity.setPrmValue(new BigDecimal(0));
					whereEntity.setStatus(Constant.PREMIUM_ORDER_STATUS_APPOINTMENT);
				}
				
			}else{//公有订单
				//
				if(checkFlag.intValue()==Constant.PREMIUM_PRICE_ALL_SUCCESS){
					//有报价
					whereEntity.setStatus(Constant.PREMIUM_ORDER_STATUS_HAVEPRICE);
				}else if(checkFlag.intValue()==Constant.PREMIUM_PRICE_BI_SUCCESS_CI_FAIL||checkFlag.intValue()==Constant.PREMIUM_PRICE_CI_SUCCESS_BI_FAIL){
					//部分无报价
					whereEntity.setStatus(Constant.PREMIUM_ORDER_STATUS_NOPRICE);
				}else{
					//全部无报价
					//订单价格清零
					whereEntity.setId(Integer.valueOf(orderId));
					//价格清零
					whereEntity.setBiValue(new BigDecimal(0));
					whereEntity.setCiValue(new BigDecimal(0));
					whereEntity.setTaxValue(new BigDecimal(0));
					whereEntity.setPrmValue(new BigDecimal(0));
					whereEntity.setStatus(Constant.PREMIUM_ORDER_STATUS_NOPRICE);
				}
				
			}
//		//获取最低投保信息
//		Map<String,Object> lowestmap = this.getLowestInfo(carNo, nlist);
//		entity.setLowestInsurer(lowestmap.get("lowestLnsurer").toString());//最低投保人
//		entity.setLowestValue(new BigDecimal(lowestmap.get("lowestValue").toString()));//最低投保费
			//更新订单信息
			f = this.updateSelectiveById(whereEntity);
		}
		
		return f;
	}



	@Override
	public boolean  updatePremiumDetail(String orderId,List<PremiumDetail> nList) {
		//------↓-------------↓-------↓----------------------更改投保方案----------↓-----------start----------↓
		boolean f = false;
		
		//订单相关投保方案
		List<PremiumDetail> opdlist = new ArrayList<PremiumDetail>();
		Map<String,Object> omap = new HashMap<String,Object>();
		omap.put("prm_order_id", orderId);
		opdlist = premiumDetailService.selectByMap(omap);
		List<Integer> ids = opdlist.stream().map(b -> b.getId()).collect(Collectors.toList());
		//删除原订单所有投保方案
		if(!opdlist.isEmpty()){
			f = premiumDetailService.deleteBatchIds(ids);
		}
		//处理后的新方案,无价格
		List<PremiumDetail> npdList = premiumDetailService.getFullDetail(nList);
		
		//根据处理后的新方案更新订单
		f = this.updateOrderByAskPrice(orderId, npdList);
//		orderStr = this.getPremiumOrder(orderId,null,po.getUserId().toString());
		//--------------↑-------------↑----------↑-----------更改投保方案----------↑-------------------end-----------↑  
		return f;
	}


	@Transactional
	@Override
	public boolean updateInsurecom(String ciInsurerCom,String orderId) {
		//----------------↓-------↓----------↓---------------更换保险公司-----↓---------↓---------end---------↓
		boolean f = false;
		//如果订单状态大于4400时，不可修改订单保险公司
		PremiumOrder dbPo = new PremiumOrder();
		dbPo = this.selectById(orderId);
		if(dbPo!=null){
			if(dbPo.getStatus().intValue()<Constant.PREMIUM_ORDER_STATUS_PAYWAITTING.intValue()){
				//修改保单中公司名字
				PremiumOrder po = new PremiumOrder();
				po.setCiInsurerCom(this.getBaoXianStr(ciInsurerCom));
				po.setBiInsurerCom(this.getBaoXianStr(ciInsurerCom));
				po.setId(Integer.valueOf(orderId));
				f = this.updateSelectiveById(po);
				
				//根据保单id获取保单相关的方案
				Map<String,Object> columnMap = new HashMap<String,Object>();
				columnMap.put("prm_order_id", orderId);
				//原投保方案
				List<PremiumDetail> pdList = premiumDetailService.selectByMap(columnMap);
				
//		//处理投保方案
//		List<PremiumDetail> nList = premiumDetailService.getFullDetail(pdList);
				
				//根据订单Id和处理后的投保方案更新订单信息
				f = this.updateOrderByAskPrice(orderId, pdList);
				
			}
		}
		
		//---------------↑---------↑--------↑----------------更换保险公司-------↑------↑---------end-------------↑  
		return f;
	}



	@Override
	public boolean updatePersonMsg(String orderId,PersonMsg pm) {
		//----------------↓-------↓------------↓-------------修改投保人信息↓-------begin-----------↓---------↓
		PremiumOrder po = new PremiumOrder();
		po.setId(Integer.valueOf(orderId));
		//完成投保信息4200
		po.setStatus(Constant.PREMIUM_ORDER_STATUS_FINISHINFO);
		po.setOwnerName(pm.getOwnerName());
		po.setOwnerIdNo(pm.getOwnerIdNo());
		po.setApplicantIdNo(pm.getApplicantIdNo());//投保人身份证号
		po.setApplicantName(pm.getApplicantName());//投保人姓名
		po.setInsuredName(pm.getInsuredName());//被保人姓名
		po.setInsuredIdNo(pm.getInsuredIdNo());//被保人身份证号
		po.setBiStartDate(pm.getBiStartDate());
		po.setCiStartDate(pm.getCiStartDate());
		po.setContactId(pm.getContactId());
		//修改
		boolean f = this.insertOrUpdateSelective(po);
		//---------------↑-------------↑--------------------修改投保人信息↑----------end---------------↑  
		return f;
	}


	@Transactional
	@Override
	public String saveCopyOrder(String orderId, String userId) {
		//获取公有订单
		PremiumOrder parentPo = new PremiumOrder();
		parentPo = this.selectById(orderId);
		
		//保期内
		//如果询价成功，则创建私有订单
		//复制一个公有订单作为私有订单
		PremiumOrder childPo = new PremiumOrder();
		String[] ignor = {"id","user_id","status","parent_id"};
		BeanUtils.copyProperties(parentPo, childPo, ignor);
		childPo.setUserId(Integer.valueOf(userId));
		childPo.setParentId(Integer.valueOf(orderId));
		childPo.setStatus(Constant.PREMIUM_ORDER_STATUS_ALREADYSEE);
		//存入私有订单
		boolean f = this.insertSelective(childPo);
		
		//私有订单存入订单编号
		PremiumOrder where = new PremiumOrder();
		String orderNo = OrderUtils.genOrderNo("premium", childPo.getId().toString());
		where.setId(childPo.getId());
		where.setOrderNo(orderNo);
		f = this.updateSelectiveById(where);
		
		Map<String,Object> nmap = new HashMap<String,Object>();
		nmap.put("prm_order_id", orderId);
		
		//有价格时候获取获取公有投保方案
		List<PremiumDetail> parentPdList = premiumDetailService.selectByMap(nmap);
		PremiumDetail childPd = null;
		for(PremiumDetail parentPd:parentPdList){
			childPd = new PremiumDetail();
			String[] ignoArr = {"id","prm_order_id","created_time"};
			BeanUtils.copyProperties(parentPd, childPd, ignoArr);
			childPd.setPrmOrderId(childPo.getId());
			childPd.setCreatedTime(new Date());
			f = premiumDetailService.insertSelective(childPd);
			if(!f){
				break;
			}
		}
		
		if(f){
			return childPo.getId().toString();
		}else{
			return null;
		}
	}


	@Transactional
	@Override
	public boolean deletePremiumOrder(String orderId) {
		//逻辑删除订单信息
		PremiumOrder po = new PremiumOrder();
		po.setId(Integer.valueOf(orderId));
		po.setStatus(Constant.PREMIUM_ORDER_STATUS_DELETED);
		boolean f = this.updateSelectiveById(po);
		return f;
	}



	@Override
	public boolean updateConfirmPay(String orderId,String payImgUrl) {
		boolean f = false;
		PremiumOrder oldPo = this.selectById(orderId);
		if(oldPo!=null){
			PremiumOrder po = new PremiumOrder();
			po.setId(Integer.valueOf(orderId));
			po.setStatus(Constant.PREMIUM_ORDER_STATUS_PAYSUCCESS);
			po.setPayType(Constant.PREMIUM_ORDER_PAYTYPE_OFFLINE);
			po.setPayImgUrl(payImgUrl);
			f = this.updateSelectiveById(po);
		}
		return f;
	}



	@Override
	public List<PremiumOrder> getWaitPushOrderList(PremiumOrder po) {
		return baseMapper.getWaitPushOrderList(po);
	}



	@Override
	public ResponseInfo addNewPremiumOrderByHistory(String orderId) {
		boolean f = false;
		ResponseInfo result = null;
		//保存历史订单
		PremiumOrder po = new PremiumOrder();
		po = this.selectById(orderId);
		
		//获取历史投保方案
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("prm_order_id", orderId);
		List<PremiumDetail> pdList = premiumDetailService.selectByMap(map);
		
		//创建新订单
		PremiumOrder npo = new PremiumOrder();
		
		//获取车辆保险到期时间,并计算起保日期
		CarInfo ci = new CarInfo();
		ci = carInfoService.selectById(po.getCarId());
		Date startTime = DateUtil.addDay(ci.getPrmEndTime(), 1);
		
		npo.setLicenseNo(po.getLicenseNo());
		npo.setCreatedTime(new Date());
		npo.setCarId(po.getCarId());
		npo.setCity(constPro.CITY);
		npo.setUserId(Constant.PUBLIC_ORDER_USERID);
		npo.setCiStartDate(startTime);
		npo.setBiStartDate(startTime);
		npo.setStatus(Constant.PREMIUM_ORDER_STATUS_HAVEPRICE);
		
		f = this.insert(npo);
		PremiumDetail newPd = null;
		String[] ignorArr = {"id","prm_order_id","prm_value","created_time"};
		//根据历史投保方案保存新的投保方案
		for (PremiumDetail pd : pdList) {
			newPd = new PremiumDetail();
			BeanUtils.copyProperties(pd, newPd, ignorArr);
			newPd.setPrmOrderId(npo.getId());
			newPd.setPrmValue(new BigDecimal(0));
			newPd.setCreatedTime(new Date());
			f = premiumDetailService.insertSelective(newPd);
			if(!f){
				result = ResponseInfo.ACTION_FAIL;
				break;
			}
		}
		
		//获取新投保方案
		Map<String,Object> columnMap = new HashMap<String,Object>();
		columnMap.put("prm_order_id", npo.getId());
		List<PremiumDetail> newPdList = premiumDetailService.selectByMap(columnMap);
		
		f = this.updateOrderByAskPrice(npo.getId().toString(), newPdList);
		if(f){
			result = ResponseInfo.SUCCESS;
		}
		return result;
	}



	@Override
	public List<PremiumOrder> getWaitPushPrmPrice(PremiumOrder po) {
		return baseMapper.getWaitPushPrmPrice(po);
	}



	public Integer checkDetailPrice(String orderId) {
		Integer checkFlag = 0;
		
		//获取要核对的订单
		PremiumOrder po = new PremiumOrder();
		po = this.selectById(orderId);
		
		if(po.getPrmValue()!=null){
			//获取订单的投保方案
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("prm_order_id", orderId);
			List<PremiumDetail> pdList = premiumDetailService.selectByMap(map);
			//交强险和车船税数量
			int ciCount = 0;
			
			//交强险和车船税价格完整数量
			int ciFlag = 0;
			
			//商业险数量
			int biCount = 0;
			
			//商业险价格完整数量
			int biFlag = 0;
			
			for (PremiumDetail pd : pdList) {
				if("JQX".equals(pd.getPrmCode())||"CCS".equals(pd.getPrmCode())){
					ciCount++;
				}else{
					biCount++;
				}
				if(pd.getPrmValue()!=null){
					if(pd.getPrmValue().compareTo(new BigDecimal(0))==1&&("JQX".equals(pd.getPrmCode())||"CCS".equals(pd.getPrmCode()))){
						//交强险和车船税报价成功,商业险报价失败：400
						ciFlag++;
					}
					if(pd.getPrmValue().compareTo(new BigDecimal(0))==1&&(!"JQX".equals(pd.getPrmCode())&&!"CCS".equals(pd.getPrmCode()))){
						biFlag++;
					}
				}
			}
			
			if(ciCount>0&&biCount>0){
				if(biFlag==biCount&&ciFlag!=ciCount){
					//商业险报价成功，交强险报价失败：300
					checkFlag = Constant.PREMIUM_PRICE_BI_SUCCESS_CI_FAIL;
				}else if(biFlag!=biCount&&ciFlag==ciCount){
					//交强险和车船税报价成功,商业险报价失败：400
					checkFlag = Constant.PREMIUM_PRICE_CI_SUCCESS_BI_FAIL;
				}else if(biFlag==biCount&&ciFlag==ciCount){
					//全部报价成功：200
					checkFlag = Constant.PREMIUM_PRICE_ALL_SUCCESS;
				}else{
					//全部报价失败：500
					checkFlag = Constant.PREMIUM_PRICE_ALL_FAIL;
				}
			}else if(ciCount>0&&biCount==0){
				//只有交强险
				if(ciFlag==ciCount){
					//全部报价成功：200
					checkFlag = Constant.PREMIUM_PRICE_ALL_SUCCESS;
				}else{
					//全部报价失败：500
					checkFlag = Constant.PREMIUM_PRICE_ALL_FAIL;
				}
			}else if(ciCount==0&&biCount>0){
				//只有商业险
				if(biFlag==biCount){
					//全部报价成功：200
					checkFlag = Constant.PREMIUM_PRICE_ALL_SUCCESS;
				}else{
					//全部报价失败：500
					checkFlag = Constant.PREMIUM_PRICE_ALL_FAIL;
				}
			}else{
				//方案为空
				checkFlag = Constant.PREMIUM_PRICE_NO_DETAIL;
			}
		}
		return checkFlag;
	}



	@Override
	public PremiumOrder getWaitPushPrmIssue(String id) {
		return baseMapper.getWaitPushPrmIssue(id);
	}



	@Override
	public List<PremiumOrder> getWaitPushOrderListAgain(PremiumOrder po) {
		return baseMapper.getWaitPushOrderListAgain(po);
	}
}