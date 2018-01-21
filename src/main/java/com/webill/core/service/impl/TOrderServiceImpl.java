package com.webill.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.SystemProperty;
import com.webill.app.util.ApplicInsurRelation;
import com.webill.app.util.AreaUtil;
import com.webill.app.util.BankUtil;
import com.webill.app.util.CardType;
import com.webill.app.util.CombineBeansUtil;
import com.webill.app.util.DateStyle;
import com.webill.app.util.DateUtil;
import com.webill.app.util.HttpUtils;
import com.webill.app.util.MD5Util;
import com.webill.app.util.MarryStateUtil;
import com.webill.app.util.StringUtil;
import com.webill.app.util.TransNoUtil;
import com.webill.core.Constant;
import com.webill.core.ResponseInfo;
import com.webill.core.model.HzNotify;
import com.webill.core.model.HzResult;
import com.webill.core.model.InsureReq;
import com.webill.core.model.NotifyData;
import com.webill.core.model.OrderApplicant;
import com.webill.core.model.OrderBeneficiary;
import com.webill.core.model.OrderInsurant;
import com.webill.core.model.OrderLog;
import com.webill.core.model.OtherInfo;
import com.webill.core.model.Policy;
import com.webill.core.model.PriceArgs;
import com.webill.core.model.Product;
import com.webill.core.model.ProductProtectItem;
import com.webill.core.model.TOrder;
import com.webill.core.model.User;
import com.webill.core.service.IOrderApplicantService;
import com.webill.core.service.IOrderBeneficiaryService;
import com.webill.core.service.IOrderInsurantService;
import com.webill.core.service.IOtherInfoService;
import com.webill.core.service.IProductProtectItemService;
import com.webill.core.service.IProductService;
import com.webill.core.service.ITOrderService;
import com.webill.core.service.IUserService;
import com.webill.core.service.mapper.TOrderMapper;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.service.impl.SuperServiceImpl;
import com.webill.framework.service.mapper.EntityWrapper;

import reactor.core.Reactor;
import reactor.event.Event;

/**
 *
 * TOrder 服务层接口实现类
 *
 */
@Service
public class TOrderServiceImpl extends SuperServiceImpl<TOrderMapper, TOrder> implements ITOrderService {

	@Autowired
    private IProductService productService;
	
	@Autowired
	private IOtherInfoService otherInfoService;
	
	@Autowired
    private IOrderBeneficiaryService orderBeneficiaryService;
	
	@Autowired
	private IOrderInsurantService orderInsurantService;
	
	@Autowired
	private IOrderApplicantService orderApplicantService;
	
    @Autowired
    private IProductProtectItemService productProtectItemService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private SystemProperty constPro;
	@Autowired
	private AreaUtil areaUtil;
	
    @Autowired
    @Qualifier("rootReactor")
    private Reactor r;
	
	@Override
	@Transient
	public Object saveDoInsure(InsureReq ir,TOrder po) {
		boolean f = false;
		Object obj = new Object();
		ResponseInfo info = null;
		int totalNum = 0;
		//生成流水号
		String transNo = TransNoUtil.genTransNo();
		
		//投保人数据处理
		OrderApplicant cant = ir.getApplicant();
		if(cant!=null){
			//证件类型处理
			if(StringUtil.isNotEmpty(cant.getCardType())){
				int cardType =CardType.getCode(cant.getCardType()); 
				cant.setCardType(String.valueOf(cardType));
			}
//			ir.getApplicant().setJob(ir.getApplicant().getJob()!=null?ir.getApplicant().getJob():"");//职业
			//城市信息处理
			if(StringUtil.isNotEmpty(cant.getProvCityId())){
				cant.setProvCityId(areaUtil.getAreaId(cant.getProvCityId()));//城市
			}
			//性别--处理
			if(StringUtil.isNotEmpty(cant.getSex())){
				if(cant.getSex().equals("男")){
					cant.setSex("1");//性别
				}else{
					cant.setSex("0");//性别
				}
			}
			//婚姻状况--处理
			if(StringUtil.isNotEmpty(cant.getMarryState())){
				int marryState = MarryStateUtil.getCode(cant.getMarryState());
				cant.setMarryState(String.valueOf(marryState));
			}
			//是否有医保--处理
			if(StringUtil.isNotEmpty(cant.getHaveMedical())){
				if(cant.getHaveMedical().equals("有")){
					cant.setHaveMedical("true");
				}else{
					cant.setHaveMedical("false");
				}
			}
			//是否税收居民身份
			if(StringUtil.isNotEmpty(cant.getFiscalResidentIdentity())){
				String fis = cant.getFiscalResidentIdentity();
				if("仅为中国税收居民".equals(fis)){
					cant.setFiscalResidentIdentity("1");
				}else if("仅为非居民".equals(fis)){
					cant.setFiscalResidentIdentity("2");
				}else{
					cant.setFiscalResidentIdentity("3");
				}
			}
			if(StringUtil.isNotEmpty(cant.getApplicantType())){
				String applicantType = cant.getApplicantType();
				if("个人".equals(applicantType)){
					cant.setApplicantType(Constant.APPLICANT_TYPE_PERSONAL.toString());
				}else{
					cant.setApplicantType(Constant.APPLICANT_TYPE_COMPANY.toString());
				}
			}
		}
		List<OrderInsurant> insurants = null;
		List<OrderBeneficiary> beneficiaryInfos = null;
		//被保人和受益人信息处理
		if(ir.getInsurants()!=null){
			insurants = ir.getInsurants();
			String provCityId = "";
			int relationId = -1;
			for (OrderInsurant insu : insurants) {
				if(StringUtil.isNotEmpty(insu.getProvCityId())){
					provCityId = areaUtil.getAreaId(insu.getProvCityId());
					insu.setProvCityId(provCityId);
				}
				if(StringUtil.isNotEmpty(insu.getRelationId())){
					relationId = ApplicInsurRelation.getCode(insu.getRelationId());
					insu.setRelationId(String.valueOf(relationId));
				}
				//证件类型处理
				if(StringUtil.isNotEmpty(insu.getCardType())){
					int cardType =CardType.getCode(insu.getCardType()); 
					insu.setCardType(String.valueOf(cardType));
				}
				if(StringUtil.isNotEmpty(insu.getSex())){
					if("男".equals(insu.getSex())){
						insu.setSex("1");
					}else{
						insu.setSex("0");
					}
				}
				if(StringUtil.isNotEmpty(insu.getHaveMedical())){
					if("有".equals(insu.getHaveMedical())){
						insu.setHaveMedical("true");
					}else{
						insu.setHaveMedical("false");
					}
				}
				//是否税收居民身份
				if(StringUtil.isNotEmpty(insu.getFiscalResidentIdentity())){
					String fis = insu.getFiscalResidentIdentity();
					if("仅为中国税收居民".equals(fis)){
						insu.setFiscalResidentIdentity("1");
					}else if("仅为非居民".equals(fis)){
						insu.setFiscalResidentIdentity("2");
					}else{
						insu.setFiscalResidentIdentity("3");
					}
				}
				totalNum += insu.getCount();
				insu.setBuyAmount(insu.getCount());
				insu.setCardNumber(insu.getCardCode());
				insu.setOrderId(po.getId());		
				if(insu.getBeneficiaryInfos()!=null){
					beneficiaryInfos = insu.getBeneficiaryInfos();
					for (OrderBeneficiary bene : beneficiaryInfos) {
						if(StringUtil.isNotEmpty(bene.getSex())){
							if("男".equals(bene.getSex())){
								bene.setSex("1");
							}else{
								bene.setSex("0");
							}
						}
						if(StringUtil.isNotEmpty(bene.getRelationId())){
							relationId = ApplicInsurRelation.getCode(bene.getRelationId());
							bene.setRelationId(String.valueOf(relationId));
						}
						if(StringUtil.isNotEmpty(bene.getCardType())){
							bene.setCardType(String.valueOf(CardType.getCode(bene.getCardType())));
						}
					}
				}
			}
		}
		//其他信息处理
		OtherInfo other = ir.getOtherInfo();
		if(ir.getOtherInfo()!=null){
			po.setOtherInfoId(ir.getOtherInfo().getId());
			if(other.getWithholdBank()!=null){
				String WithholdBank = String.valueOf(BankUtil.getCode(other.getWithholdBank()));
				other.setWithholdBank(WithholdBank);
			}
			if(other.getRenewalBank()!=null){
				String renewalBank = String.valueOf(BankUtil.getCode(other.getRenewalBank()));
				other.setWithholdBank(renewalBank);
			}
			if(other.getRenewalPayBank()!=null){
				String renewalPayBank = String.valueOf(BankUtil.getCode(other.getRenewalPayBank()));
				other.setRenewalPayBank(renewalPayBank);
			}
		}
		//以下这块儿顺序，【不可变动】,构建下单所需数据结构
		PriceArgs pa = ir.getPriceArgs();
		String paStr = JSONUtil.toJSONString(pa).replace("\"", "\\\"");
		ir.setPriceArgs(null);
		ir.setTransNo(transNo);
		String InsureReqStr = JSONObject.toJSONString(ir).replaceAll("}$", ",");
		String priceArgsStr = "\"priceArgs\":\""+paStr+"\"}";
		InsureReqStr += priceArgsStr;
		
		String reqSign = MD5Util.MD5Encode(constPro.HUIZE_TEST_KEY + InsureReqStr, "UTF-8");
		logger.info("慧择下单：传入数据=====>"+InsureReqStr);
		String resJson = HttpUtils.httpPostJsonRequest(constPro.HUIZE_TEST_REQUEST_URL + "/api/insure"+ "?sign=" + reqSign, InsureReqStr);
		logger.info("慧择下单：返回数据=====>"+resJson);
		if(resJson!=null){
			JSONObject jo = JSONObject.parseObject(resJson);
			if(resJson.contains("respCode")){
				if(Integer.valueOf(jo.getString("respCode"))==0){
					JSONObject o = JSONObject.parseObject(jo.getString("data"));
					//计算总保费
					long payAmount = 0;
					if(ir.getInsurants().size()>0){
						for(OrderInsurant ins:ir.getInsurants()){
							payAmount += ins.getSinglePrice();
						}
					}
					po.setInOrderNo(o.getString("insureNum").toString());//投保单号
					po.setTransNo(transNo);//交易流水号
					po.setIssueStatus(Constant.NORMAL_STATUS);//出单状态
					po.setEffectiveStatus(Constant.NORMAL_STATUS);//生效状态
					po.setPayStatus(Constant.NORMAL_STATUS);//支付状态
					if(po.getAutoPay()==1){//银行代扣
						po.settStatus(Constant.ORDER_STATUS_ISSUING);//已承保
					}else{
						po.settStatus(Constant.ORDER_STATUS_WAIT_PAY);//待付款
					}
					po.setPayAmount((long) 1);
					po.setTotalNum(totalNum);
					po.setInsureTime(new Date());//投保时间
					po.setDeadline(" ");
					logger.info("慧择下单：投保单号=====>"+o.getString("insureNum").toString());
					//下单成功
					info = ResponseInfo.SUCCESS;
					logger.info("慧择下单：=====>下单成功");
					
					//保存其他信息
					if(ir.getOtherInfo()!=null){
						po.setOtherInfoId(ir.getOtherInfo().getId());
						f = otherInfoService.insertSelective(other);
						logger.debug("保存其他信息成功=====>"+JSON.toJSONString(other));
						po.setOtherInfoId(other.getId());
					}
					
					//保存订单信息
					f = this.insertSelective(po);
					obj = po;
					logger.info("创建订单:=====>"+JSON.toJSONString(po));
					
					//保存投保人
					cant.setOrderId(po.getId());
					f = orderApplicantService.insertSelective(cant);
							
					//更新用户信息
					User sourceUser = new User();
					if(cant!=null){
						sourceUser = JSONObject.parseObject(JSON.toJSONString(cant), User.class);
						User targetUser = userService.selectById(po.getUserId());
//						targetUser.setId(po.getUserId());
//						targetUser.setMobile(dbUser.getMobile());
//						targetUser.setCardCode(dbUser.getCardCode());
//						targetUser.setcName(dbUser.getcName());
						r.notify("user.update", Event.wrap(targetUser));
					}
					
					//保存订单日志
					r.notify("orderLog.operate", Event.wrap(po.getId()));
					
					//被保人和受益人信息处理
					if(ir.getInsurants()!=null){
						for (OrderInsurant insu : insurants) {
							insu.setCardNumber(insu.getCardCode());
							insu.setOrderId(po.getId());
							f = orderInsurantService.insertSelective(insu);
							logger.debug("保存被保人信息成功=====>"+JSON.toJSONString(insu));
							if(insu.getBeneficiaryInfos()!=null){
								beneficiaryInfos = insu.getBeneficiaryInfos();
								for (OrderBeneficiary bene : beneficiaryInfos) {
									bene.setInsurantId(insu.getId().toString());
									bene.setInsurantCname(insu.getcName());
									//保存受益人信息
									f = orderBeneficiaryService.insertSelective(bene);
									logger.debug("保存受益人信息成功=====>"+JSON.toJSONString(bene));
								}
							}
						}
					}
				}else{
					//下单失败
					obj = jo;
					logger.info("慧择下单失败，返回数据：=====>"+resJson);
				}
			}else{
				obj = null;
				logger.info("慧择下单失败，返回数据：=====>"+resJson);
			}
		}else{
			//下单失败
			obj = null;
			logger.info("慧择下单失败，返回数据：=====>"+resJson);
		}
		return obj;
	}

	@Override
	public String onlinePay(Map<String, Object> map) {
		logger.info("调用支付接口=====>");
		String respStr = getData(map, "/api/onlinePay");
		return respStr;
	}
	
	@Override
	public String downloadUrl(Map<String, Object> map) {
		String respStr = getData(map, "/api/downloadUrl");
		HzResult hr = JSONObject.parseObject(respStr, HzResult.class);
		JSONObject jo = JSONObject.parseObject(respStr);
		if(hr.getRespCode()==0){
			EntityWrapper<TOrder> whereEntity = new EntityWrapper<TOrder>();
			whereEntity.addFilter("in_order_no", jo.getString("insureNum"));
			
			TOrder po = new TOrder();
			po = this.selectOne(whereEntity);
			if(po!=null){
				po.setInsureDownUrl(jo.getString("fileUrl"));
				po.setUpdatedTime(new Date());
				
				boolean f = this.updateSelectiveById(po);
				
			}
			
		}
		return respStr;
		
	}

	@Override
	public String orderDetail(Map<String, Object> map) {
		String respStr = getData(map, "/api/orderDetail");
		return respStr;
	}

	@Override
	public String surrenderPolicy(Map<String, Object> map) {
		String respStr = getData(map, "/api/surrenderPolicy");
		return respStr;
	}

	@Override
	public ResponseInfo updateByNotify(HzNotify hn){
		String str = hn.getData().toString();
		NotifyData data = JSONObject.parseObject(str, NotifyData.class);
		List<Policy> policys = data.getPolicys();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("in_order_no", data.getInsureNum());
		TOrder dbPo = new TOrder();
		TOrder wherePo = new TOrder();

		List<TOrder> orderList = this.selectByMap(map);
		if(orderList!=null){
			dbPo = orderList.get(0); 
			wherePo.setId(dbPo.getId());
			//更新支付方式
			if(StringUtil.isNotEmpty(data.getOnlinePaymentId())){
				wherePo.setGateWay(Integer.valueOf(data.getOnlinePaymentId()));
			}
			if(hn.getNotifyType()==2){//支付
				logger.info("回调通知类型：=====>支付");
				if(data.isState()){
					wherePo.setPayStatus(1);//已支付
					wherePo.setIssueStatus(0);//未出单
					wherePo.setEffectiveStatus(0);//未生效
					wherePo.setPayTime(DateUtil.StringToDate(data.getPayTime(), DateStyle.YYYY_MM_DD_HH_MM_SS));//成功支付时间
					wherePo.setUpdatedTime(new Date());//更新时间
					wherePo.settStatus(Constant.ORDER_STATUS_ALREADY_PAY);//已支付
					wherePo.setGateWay(Integer.valueOf(data.getOnlinePaymentId()));//支付方式
					logger.info("支付=====>成功");
				}else{
					logger.info("支付=====>失败");
				}
			}else if(hn.getNotifyType()==3){//出单
				logger.info("回调通知类型：出单");
				if(data.isState()){
					wherePo.setPayStatus(1);//扣款成功
					wherePo.setIssueStatus(0);//未出单
					wherePo.setEffectiveStatus(0);//未生效
					if(policys!=null){
						wherePo.setStartDate(policys.get(0).getStartDate());//起保日期
						wherePo.setEndDate(policys.get(0).getEndDate());//终保日期
					}
					wherePo.setOrderIssueTime(new Date());//出单时间
					wherePo.setUpdatedTime(new Date());//更新时间
					wherePo.settStatus(Constant.ORDER_STATUS_ISSUING);//出单中（已承保）
					logger.info("出单=====>成功");
				}else{
					logger.info("出单=====>失败");
				}
			}else if(hn.getNotifyType()==4){//退保
				logger.info("回调通知类型：=====>已退保");
				if(data.isState()){
					wherePo.setEffectiveStatus(2);//已退保
					wherePo.setUpdatedTime(new Date());//更新时间
					wherePo.settStatus(Constant.ORDER_STATUS_ALREADY_SURRENDER);//已退保
				}else{
					logger.info("退保=====>失败");
				}
			}else if(hn.getNotifyType()==5){//重出
				logger.info("回调通知类型：=====>已退保");
				wherePo.setEffectiveStatus(2);//已退保
				wherePo.setUpdatedTime(new Date());//更新时间
//				wherePo.settStatus(60);//已退保
			}else if(hn.getNotifyType()==9){//保单生成（已出单）
				logger.info("回调通知类型：=====>保单生成");
				if(data.isState()){
					wherePo.setInsureIssueTime(new Date());//保单生成时间
					wherePo.setUpdatedTime(new Date());//更新时间
					wherePo.settStatus(Constant.ORDER_STATUS_ALREADY_ISSUE);//保单生成(已出单)
					
					map.clear();
					map.put("order_id", dbPo.getId());
					List<OrderInsurant> insurantList = orderInsurantService.selectByMap(map);
					boolean f = false;
					for (OrderInsurant insurant : insurantList) {
						for (Policy poli : policys) {
							if(insurant.getcName().equals(poli.getApplicant())){
								insurant.setPolicyNum(poli.getPolicyNum());
								f = orderInsurantService.updateSelectiveById(insurant);
							}
							
						}
					}
					logger.info("保单生成=====>成功");
				}else{
					logger.info("保单生成=====>失败");
				}
			}else{
				logger.info("未知的通知类型："+hn.getNotifyType());
				wherePo = null;
			}
		}else{
			if(hn.getNotifyType()==2){//支付
				logger.info("回调通知类型=====>支付");
			}else if(hn.getNotifyType()==3){//出单
				logger.info("回调通知类型=====>出单");
			}else if(hn.getNotifyType()==4||hn.getNotifyType()==5){//退保或者重出
				logger.info("回调通知类=====>退保或重出，暂未处理");
			}else if(hn.getNotifyType()==9){//保单生成
				logger.info("回调通知类型=====>保单生成");
			}else{
				logger.info("未知的通知类型=====>"+hn.getNotifyType());
			}
			logger.info("未找到订单，投保单号=====>"+data.getInsureNum());
		}
		
		boolean f = false;
		
		if(dbPo!=null){
			if(dbPo.gettStatus()<wherePo.gettStatus()){
				f = this.updateSelectiveById(wherePo);
				r.notify("orderLog.operate", Event.wrap(wherePo.getId()));
				logger.info("=====>修改订单成功！");
			}else{
				logger.info("=====>无需操作订单！");
				f = true;
			}
		}
		
		if(f){
			logger.info("=====>操作成功！");
			return ResponseInfo.SUCCESS;
		}else{
			logger.info("=====>操作失败！");
			return ResponseInfo.ACTION_FAIL;
		}
	}

	@Override
	public String getData(Map<String, Object> map, String reqUrl) {
		String reqJson = JSONUtils.toJSONString(map);
		String reqSign = MD5Util.MD5Encode(constPro.HUIZE_TEST_KEY + reqJson, "UTF-8");
		String resJson = HttpUtils.httpPostJsonRequest(constPro.HUIZE_TEST_REQUEST_URL + reqUrl+ "?sign=" + reqSign, reqJson);
		if(StringUtil.isNotEmpty(resJson)){
			JSONObject jo = JSON.parseObject(resJson);
			if("0".equals(jo.getString("respCode"))){
				//请求成功
				logger.info("请求成功，url地址=====>"+constPro.HUIZE_TEST_REQUEST_URL+ reqUrl);
			}else{
				logger.info("请求失败，url地址=====>"+constPro.HUIZE_TEST_REQUEST_URL+ reqUrl);
			}
			logger.info("请求参数=====>"+reqJson);
			logger.info("返回数据=====>"+resJson);
		}
		return resJson;
	}
	
	@Override
	public Page<TOrder> getList(Page<TOrder> page, TOrder po) {
		List<TOrder> poList = baseMapper.getList(page,po);
		page.setRecords(poList);
		return page;
	}

	@Override
	public Map<String, Object> getNumByStatus() {
		return baseMapper.getNumByStatus();
	}

	@Override
	public List<TOrder> getOrderList(TOrder po) {
		return baseMapper.getOrderList(po);
	}

	@Override
	public Object getOrderList(String userId) {
    	TOrder po = new TOrder();
        //全部
        po.setUserId(Integer.valueOf(userId));
        List<TOrder> allList= this.getOrderList(po);
        List<OrderInsurant> iList = null;
        Map<String,Object> map = new HashMap<String,Object>();
        for (TOrder o : allList) {
     	   map.put("order_id", o.getId());
     	   iList = orderInsurantService.selectByMap(map);
     	   o.setInsurantList(iList);
     	   o.setImgUrlShow(constPro.PRODUCT_FILE_ACCESS_PATH + o.getImgUrlShow());
        }
        
        
        //订单状态：-1：已删除、0：新建、10：待付款、20：已支付、30：出单中（已承保）、40：已出单、50：退保中、60：已退保、80：已关闭、90：已失效',
        //已完成
        po.setUserId(Integer.valueOf(userId));
        po.settStatus(Constant.ORDER_STATUS_ALREADY_PAY);//已支付
        List<TOrder> dfhList= this.getOrderList(po);
        
        po.setUserId(Integer.valueOf(userId));
        po.settStatus(Constant.ORDER_STATUS_ALREADY_ISSUE);//已出单
        List<TOrder> ycdList= this.getOrderList(po);
        po.setUserId(Integer.valueOf(userId));
        po.settStatus(Constant.ORDER_STATUS_ISSUING);//出单中
        List<TOrder> cdzList= this.getOrderList(po);
        List<TOrder> ywcList = new ArrayList<TOrder>();
        ywcList.addAll(dfhList);
        ywcList.addAll(ycdList);
        ywcList.addAll(cdzList);
        for (TOrder o : ywcList) {
     	   map.put("order_id", o.getId());
     	   iList = orderInsurantService.selectByMap(map);
     	   o.setInsurantList(iList);
     	   o.setImgUrlShow(constPro.PRODUCT_FILE_ACCESS_PATH + o.getImgUrlShow());
        }
        
        //待支付
        po.setUserId(Integer.valueOf(userId));
        po.settStatus(Constant.ORDER_STATUS_WAIT_PAY);//待支付       
        List<TOrder> dzfList= this.getOrderList(po);
        for (TOrder o : dzfList) {
     	   map.put("order_id", o.getId());
     	   iList = orderInsurantService.selectByMap(map);
     	   o.setInsurantList(iList);
     	   o.setImgUrlShow(constPro.PRODUCT_FILE_ACCESS_PATH + o.getImgUrlShow());
        }
        
        //已失效
        po.setUserId(Integer.valueOf(userId));
        po.settStatus(Constant.ORDER_STATUS_ALREADY_INVALID);//已失效       
        List<TOrder> ysxList= this.getOrderList(po);
        for (TOrder o : ysxList) {
     	   map.put("order_id", o.getId());
     	   iList = orderInsurantService.selectByMap(map);
     	   o.setInsurantList(iList);
     	   o.setImgUrlShow(constPro.PRODUCT_FILE_ACCESS_PATH + o.getImgUrlShow());
        }
        
        JSONArray ja = new JSONArray();
        
        JSONObject all = new JSONObject();
        JSONObject bzz = new JSONObject();
        JSONObject dzf = new JSONObject();
        JSONObject ysx = new JSONObject();
        all.put("name", "allList");
        all.put("list", allList);
        ja.add(all);
        
        bzz.put("name", "ywcList");
        bzz.put("list", ywcList);
        ja.add(bzz);
        
        dzf.put("name", "dzfList");
        dzf.put("list", dzfList);
        ja.add(dzf);
        
        ysx.put("name", "ysxList");
        ysx.put("list", ysxList);
        ja.add(ysx);
		return ja;
	}

	@Override
	public TOrder getOrderDetail(String id) {
		  TOrder po = new TOrder();
	      po =   this.selectById(id);
			if (po != null) {
				// 保障权益信息
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("prod_id", po.getProductId());
				List<ProductProtectItem> itemList = productProtectItemService.selectByMap(map);
				po.setItemList(itemList);
				// 被保人信息和受益人信息
				map.clear();
				map.put("order_id", po.getId());
				// 被保人信息
				List<OrderInsurant> insurantList = orderInsurantService.selectByMap(map);
				for (OrderInsurant insu : insurantList) {
					// 性别处理
					if (insu.getSex() != null) {
						if ("1".equals(insu.getSex())) {
							insu.setSex("男");
						} else {
							insu.setSex("女");
						}
					}
					// 证件类型处理
					if (insu.getCardType() != null) {
						String cardType = CardType.getName(Integer.valueOf(insu.getCardType()));
						insu.setCardType(cardType);
					}
					// 城市处理
					if (insu.getProvCityId() != null) {
						String provCityId = areaUtil.getAreaName(insu.getProvCityId());
						insu.setProvCityId(provCityId);
					}
					// 职业处理
					if (insu.getJob() != null) {
						String job = insu.getJob();
					}
					// 是否有医保
					if (StringUtil.isNotEmpty(insu.getHaveMedical())) {
						if ("1".equals(insu.getHaveMedical())) {
							insu.setHaveMedical("有");
						} else {
							insu.setHaveMedical("无");
						}
					}
					if(StringUtil.isNotEmpty(insu.getRelationId())){
						String relationId = ApplicInsurRelation.getName(Integer.valueOf(insu.getRelationId()));
						insu.setRelationId(relationId);
					}
					// 受益人信息
					map.clear();
					map.put("insurant_id", insu.getId());
					List<OrderBeneficiary> beneficiaryInfos = orderBeneficiaryService.selectByMap(map);
					if(beneficiaryInfos!=null){
						for (OrderBeneficiary obf : beneficiaryInfos) {
							if (StringUtil.isNotEmpty(obf.getSex())) {
								if ("1".equals(insu.getSex())) {
									obf.setSex("男");
								} else {
									obf.setSex("女");
								}
								if (insu.getCardType() != null) {
									String cardType = CardType.getName(Integer.valueOf(obf.getCardType()));
									obf.setCardType(cardType);
								}
								if(StringUtil.isNotEmpty(obf.getRelationId())){
									String relationId = ApplicInsurRelation.getName(Integer.valueOf(obf.getRelationId()));
									obf.setRelationId(relationId);
								}
							}
						}
						insu.setBeneficiaryInfos(beneficiaryInfos);
					}
				}
			  po.setInsurantList(insurantList);
			  
			  //产品信息
			  Product product = productService.selectById(po.getProductId());
			  if (StringUtil.isNotEmpty(product.getImgUrlShow())) {
				  product.setImgUrlShow(constPro.PRODUCT_FILE_ACCESS_PATH + product.getImgUrlShow());
			  }
			  po.setProduct(product);
			  
			  //投保人信息
			  OrderApplicant entity = new OrderApplicant();
			  entity.setOrderId(po.getId());
			  OrderApplicant applicant = orderApplicantService.selectOne(entity);
			  //性别处理
			  if(applicant.getSex()!=null){
				  if("1".equals(applicant.getSex())){
					  applicant.setSex("男");
				  }else{
					  applicant.setSex("女");
				  }
			  }
			  //证件类型处理
			  if(applicant.getCardType()!=null){
				  String cardType = CardType.getName(Integer.valueOf(applicant.getCardType()));
				  applicant.setCardType(cardType);
			  }
			  //城市处理
			  if(applicant.getProvCityId()!=null){
				  String provCityId = areaUtil.getAreaName(applicant.getProvCityId());
				  applicant.setProvCityId(provCityId);
			  }
			  //职业处理
			  if(applicant.getJob()!=null){
				  String job = applicant.getJob(); 
			  }
			  //是否有医保
			  if(StringUtil.isNotEmpty(applicant.getHaveMedical())){
				  if("1".equals(applicant.getHaveMedical())){
					  applicant.setHaveMedical("有");
				  }else{
					  applicant.setHaveMedical("无");
				  }
			  }
			  po.setApplicant(applicant);
			  
			  return po;
		  }else{
			  return null;
		  }
	}
	
}