package com.webill.app.controller;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.SystemProperty;
import com.webill.app.util.DateUtil;
import com.webill.app.util.EnumString;
import com.webill.app.util.PinYinUtil;
import com.webill.app.util.PremiumOrderStatus;
import com.webill.app.util.StringUtil;
import com.webill.core.Constant;
import com.webill.core.model.CarInfo;
import com.webill.core.model.IllegalDetail;
import com.webill.core.model.IllegalOrder;
import com.webill.core.model.Premium;
import com.webill.core.model.PremiumDetail;
import com.webill.core.model.PremiumOrder;
import com.webill.core.model.UserContact;
import com.webill.core.service.ICarInfoService;
import com.webill.core.service.IPremiumDetailService;
import com.webill.core.service.IPremiumOrderService;
import com.webill.core.service.IPremiumService;
import com.webill.core.service.IUserContactService;
import com.webill.core.service.impl.PremiumDetailServiceImpl;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;
import com.webill.framework.service.mapper.EntityWrapper;

/**   
 * @ClassName: OrderController   
 * @Description: 违章订单处理类  
 * @author: WangLongFei  
 * @date: 2017年5月31日 下午4:09:35      
 */
@Controller
@RequestMapping("/premiumOrder")
public class PremiumOrderController extends BaseController {
    private static Log logger = LogFactory.getLog(PremiumOrderController.class);

    @Autowired
    private IUserContactService userContactService;
    
    @Autowired
    private IPremiumOrderService premiumOrderService;
    
    @Autowired
    private IPremiumDetailService premiumDetailService;
    
    @Autowired
    private IPremiumService premiumService;
    
    @Autowired
    private ICarInfoService carInfoService;
    
    @Autowired
    protected SystemProperty constPro;
    

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "system/premiumOrder/index";
    }

    /**   
     * @Title: orderList   
     * @Description: 后台订单列表      
     * @author: WangLongFei  
     * @date: 2017年7月19日 上午9:26:41   
     * @param po
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/list", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String orderList(PremiumOrder po) {
       logger.info("logging test....");
       Page page = this.getPage(Integer.MAX_VALUE);
       page =   premiumOrderService.getPremiumOrderList(page, po);
       return JSONUtil.toJSONString(page);
    }
    
    /**   
     * @Title: selectPremiumOrderDetail   
     * @Description: 获取订单信息---------------后台  
     * @author: WangLongFei  
     * @date: 2017年7月11日 下午5:59:48   
     * @param id	订单id
     * @param pms	保险表对象
     * @param model		
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/orderDetail/{id}", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String selectPremiumOrderDetail(@PathVariable @Param(value = "id") String id,Premium pms,Model model){
    	PremiumOrder po = new PremiumOrder();
    	//根据id获取订单
    	po = premiumOrderService.getPremiumOrderById(id);
    	if(StringUtil.isNotEmpty(po.getPayImgUrl())){
    		po.setPayImgUrl(po.getPayImgUrl());
    	}
    	
    	//根据userId获取联系人
    	Map<String,Object> ucmap = new HashMap<String,Object>();
    	ucmap.put("userId", po.getUserId());
    	List<UserContact> uclist = userContactService.getUserContact(ucmap);
    	po.setUclist(uclist);
    	
    	//获取订单详情
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("prmOrderId", id);
    	List<PremiumDetail> pdlist = premiumDetailService.selectOrderBy(map);
    	//基础险
    	List<PremiumDetail> jcpdlist = new ArrayList<PremiumDetail>();
    	//附加险
    	List<PremiumDetail> fjpdlist = new ArrayList<PremiumDetail>();
    	//交强险
    	List<PremiumDetail> jqpdlist = new ArrayList<PremiumDetail>();
    	Premium pm = new Premium();
    	for(PremiumDetail pd:pdlist){
    		pm = premiumService.selectById(pd.getPrmId());
    		if(pm.getPrmType()==1){
    			jcpdlist.add(pd);
    		}else if(pm.getPrmType()==2){
    			fjpdlist.add(pd);
    		}else if(pm.getPrmType()==3){
    			jqpdlist.add(pd);
    		}
    		pd.setPremiumType(pm.getAmount());
    		pd.setDetailType(pm.getPrmType());
    	}
    	po.setPdlist(pdlist);
    	po.setJcpdlist(jcpdlist);
    	po.setFjpdlist(fjpdlist);
    	po.setJqpdlist(jqpdlist);
    	po.setId(Integer.valueOf(id));
    	
    	model.addAttribute("po", po);
    	
    	//保险列表
    	Map<String,Object> mapby = new HashMap<String,Object>();
    	mapby.put("orderId", id);
    	mapby.put("prmName", pms.getPrmName());
    	mapby.put("prmType", pms.getPrmType());
    	List<Premium> plist = premiumService.selectPremiumListByMap(mapby);
    	for(Premium p:plist){
    		if(p.getPrmType()==1){
    			p.setPrmTypeStr("基础险");
    		}else if(p.getPrmType()==2){
    			p.setPrmTypeStr("附加险");
    		}else if(p.getPrmType()==3){
    			p.setPrmTypeStr("交强险");
    		}
    	}
    	model.addAttribute("plist", plist);
    	return "system/premiumOrder/detail-list";
    }
    /**   
     * @Title: confirmPremiumOrder   
     * @Description: 保存方案（订单详情保存,出单成功）
     * @author: WangLongFei  
     * @date: 2017年7月18日 下午4:16:19   
     * @param po
     * @return
     * @throws UnsupportedEncodingException  
     * @return: Object  
     */
    @RequestMapping(value = "/confirmPremiumOrder", method = { RequestMethod.POST })
    @ResponseBody
    public JsonResult confirmPremiumOrder(PremiumOrder po) throws UnsupportedEncodingException{
    	boolean f = premiumOrderService.saveBackPremiumOrder(po);
    	if(f){
    		return renderSuccess("保存订单成功！", "200");
    	}else{
    		return renderError("保存订单失败！", "500");
    	}
    }
    /**   
     * @Title: addPriceToPremium   
     * @Description: 给方案添加价格
     * @author: WangLongFei  
     * @date: 2017年7月18日 下午4:16:19   
     * @param po
     * @return
     * @throws UnsupportedEncodingException  
     * @return: Object  
     */
    @RequestMapping(value = "/addPriceToPremium", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object addPriceToPremium(PremiumOrder po) throws UnsupportedEncodingException{
    	if(po.getDiscount()!=null&&!"".equals(po.getDiscount())){
    		if(Double.valueOf(po.getDiscount())<=100&&Double.valueOf(po.getDiscount())>=0){
    			boolean f = premiumOrderService.addPriceToPremium(po);
    			if(f){
    				return renderSuccess("操作成功！", "200",po);
    			}else{
    				return renderError("操作失败！", "500",po);
    			}
    		}else{
    			return renderError("折扣比例应在0-100之间！", "505");
    		}
    	}else{
    		return renderError("折扣比例不能为空！", "500");
    	}
    }
    /**   
     * @Title: addPremium   
     * @Description: 加载添加保险列表窗口  
     * @author: WangLongFei  
     * @date: 2017年7月4日 下午5:08:53   
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/addPremium", method = RequestMethod.GET)
    public String addPremium() {
        return "system/premiumOrder/premiumList";
    }
    
    /**   
     * @Title: selectPremiumListBy   
     * @Description: 异步加载保险列表    
     * @author: WangLongFei  
     * @date: 2017年7月19日 上午9:30:44   
     * @param pm	保险对象
     * @param orderId	订单id
     * @param model
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/premiumList", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String selectPremiumListBy(Premium pm,String orderId,Model model){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("orderId", orderId);
    	if(pm.getPrmName()!=null && !"".equals(pm.getPrmName())){
    		map.put("prmName", pm.getPrmName());
    	}
    	if(pm.getPrmType()!=null && !"".equals(pm.getPrmType())){
    		map.put("prmType", pm.getPrmType());
    	}
    	List<Premium> plist = premiumService.selectPremiumListByMap(map);
    	for(Premium p:plist){
    		if(p.getPrmType()==1){
    			p.setPrmTypeStr("基础险");
    		}else if(p.getPrmType()==2){
    			p.setPrmTypeStr("附加险");
    		}else if(p.getPrmType()==3){
    			p.setPrmTypeStr("交强险");
    		}
    	}
    	return JSONUtil.toJSONString(plist);
    }
    
    /**   
     * @Title: addPremiumToOrder   
     * @Description: 给投保订单添加保险方案    
     * @author: WangLongFei  
     * @date: 2017年7月19日 上午9:31:27   
     * @param orderId	订单id
     * @param premiumId		保险对象id
     * @param model
     * @return  
     * @return: Object  
     */
    @RequestMapping(value = "/addPremiumToOrder", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object addPremiumToOrder(String orderId,String premiumId,Model model){
    	boolean f = premiumOrderService.addPremiumToOrder(orderId, premiumId);
    	if(f){
    		return renderSuccess("添加险种成功！","200");
    	}else{
    		return renderError("添加险种失败", "500");
    	}
    }
    
    /**   
     * @Title: getCarInfo   
     * @Description: 根据车牌号获取车辆信息,新建订单-----------后台  
     * @author: WangLongFei  
     * @date: 2017年7月19日 上午9:33:30   
     * @param ci	车辆对象
     * @return  
     * @return: Object  
     */
    @RequestMapping(value = "/getCarInfo", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object getCarInfo(CarInfo ci){
    	Map<String,Object> map = new HashMap<String,Object>();
		map.put("license_no", ci.getLicenseNo());
		map.put("status", 0);
		List<CarInfo> cilist = carInfoService.selectByMap(map);
		if(cilist.size()>0){
			return cilist.get(0);
		}else{
			return renderError("没有该车辆信息！", "500");
		}
    }
    /**   
     * @Title: saveNewPremiumOrder   
     * @Description: 生成新订单----后台  
     * @author: WangLongFei  
     * @date: 2017年7月15日 下午6:08:02   
     * @param po	订单对象
     * @return  
     * @return: Object  
     */
    @RequestMapping(value = "/saveNewPremiumOrder", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object saveNewPremiumOrder(PremiumOrder po){
    	po.setStatus(Constant.PREMIUM_ORDER_STATUS_CREATED);
    	po.setCreatedTime(new Date());
    	if(po.getUserId()!=null){
    		if(po.getUserId()!=Constant.PUBLIC_ORDER_USERID){
        		po.setUserId(po.getUserId());
        	}else{
        		po.setUserId(Constant.PUBLIC_ORDER_USERID);
        	}
    	}else{
    		po.setUserId(Constant.PUBLIC_ORDER_USERID);
    	}
    	po.setBiInsurerCom(po.getCiInsurerCom());
    	po.setCity(constPro.CITY);
    	
    	//获取车辆信息
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("license_no", po.getLicenseNo());
    	map.put("status", 0);
    	List<CarInfo> ciList = carInfoService.selectByMap(map);
    	
    	po.setBiStartDate(po.getCiStartDate());
    	po.setBiEndDate(DateUtil.addYear(po.getBiStartDate(), constPro.DEFAULT_PREMIUM_YEAR_NUM));
    	po.setCiEndDate(DateUtil.addYear(po.getCiStartDate(), constPro.DEFAULT_PREMIUM_YEAR_NUM));
    	if(ciList.size()==1){
    		CarInfo ci = ciList.get(0);
    		po.setCarId(ci.getId());
    		boolean f = premiumOrderService.insertSelective(po);
    		if(f){
    			return renderSuccess("新建订单成功！", "200",po.getId());
    		}else{
    			return renderError("新建订单失败！", "500");
    		}
    	}else{
    		return renderError("新建订单失败！", "500");
    	}
    }
    
    /**   
     * @Title: confirmPush   
     * @Description: 确认推送，同时给用户发送推送【车险报价通知】————————后台  
     * @author: WangLongFei  
     * @date: 2017年7月17日 下午4:03:05   
     * @param orderId	订单id
     * @return  
     * @return: Object  
     */
    @RequestMapping(value = "/confirmPush", method = { RequestMethod.GET},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object confirmPush(String orderId){
    	Integer result = premiumOrderService.updateConfirmPush(orderId);
    	if(result.equals(Constant.STATUS_SUCCESS)){
    		return renderSuccess("推送成功！","200");
    	}else if(result.equals(Constant.PREMIUM_ORDER_DETAIL_HAVENO_PRICE)){
    		return renderError("方案价格不完整，请填写完整再试！", "400");
    	}else{
    		return renderError("推送失败", "500");
    	}
    }
    
    /**   
     * @Title: autoAskPrice   
     * @Description: 自动询价  
     * @author: WangLongFei  
     * @date: 2017年10月20日 下午1:39:35   
     * @param po
     * @return  
     * @return: Object  
     */
    @RequestMapping(value = "/autoAskPrice", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object autoAskPrice(PremiumOrder po){
		Map<String,Object> map = new  HashMap<String, Object>();
		map.put("prm_order_id", po.getId());
		List<PremiumDetail> pdList = premiumDetailService.selectByMap(map);
		
		boolean f = premiumOrderService.updateOrderByAskPrice(po.getId().toString(), pdList);
		if(f){
			return renderSuccess("自动询价成功！","200");
		}else{
			return renderError("自动询价失败", "500");
		}
    }

    /**   
     * @Title: delPremiumDetail   
     * @Description: 删除投保方案 ——————后台
     * @author: WangLongFei  
     * @date: 2017年7月15日 上午11:15:35   
     * @param orderId  订单id
     * @param detailId	方案id
     * @return  
     * @return: Object  
     */
    @RequestMapping(value = "/delPremiumDetail", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object delPremiumDetail(PremiumDetail pd){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("id", pd.getId());
    	boolean f = premiumDetailService.deleteByMap(map);
    	if(f){
    		return renderSuccess("删除成功！","200");
    	}else{
    		return renderError("删除失败", "500");
    	}
    }
    
    /**   
     * @Title: autoCount   
     * @Description: 自动计算保费  
     * @author: WangLongFei  
     * @date: 2017年7月19日 上午9:35:30   
     * @param po	订单对象（价格处理）
     * @return
     * @throws UnsupportedEncodingException  
     * @return: Object  
     */
    @RequestMapping(value = "/autoCount", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object autoCount(PremiumOrder po) throws UnsupportedEncodingException{
    	if(po.getDiscount()!=null&&!"".equals(po.getDiscount())){
    		if(Double.valueOf(po.getDiscount())<=100&&Double.valueOf(po.getDiscount())>=0){
    			po = premiumOrderService.autoCount(po);
    			return po;
    		}else{
    			return renderError("折扣比例应在0-100之间！", "505");
    		}
    	}else{
    		return renderError("折扣比例不能为空！", "500");
    	}
    }
    
    /**   
     * @Title: uploadPayImg
     * @Description: 上传支付凭证
     * @author: WangLongFei  
     * @date: 2017年9月1日 下午2:27:46   
     * @param request
     * @param response
     * @param myFile
     * @return  
     * @return: JsonResult  
     */
    @RequestMapping(value = "/upload/payImg",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Map<String,Object> uploadPayImg(HttpServletRequest request,HttpServletResponse response,PremiumOrder po){
    	Map<String, Object> json = new HashMap<String, Object>();
        try {
            //输出文件后缀名称
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            //图片名称
            String name = df.format(new Date());

            Random r = new Random();
            for(int i = 0 ;i<3 ;i++){
                name += r.nextInt(10);
            }
            MultipartFile myfile = null;
            if(po.getPayImg()!=null){
            	myfile = po.getPayImg();
            }
            //
            String fileType = FilenameUtils.getExtension(myfile.getOriginalFilename());
            //保存图片       File位置 （全路径）   /upload/fileName.jpg
            String url = constPro.FILE_SAVE_PATH;
            //相对路径
            String path = "/"+name + "." + fileType;
            File file = new File(url);
            if(!file.exists()){
                file.mkdirs();
            }
            myfile.transferTo(new File(url+path));
            json.put("success", path);
            String pathFlag = "";
            if(po.getPayImg()!=null){
            	pathFlag = "payImg";
            }
            json.put("pathFlag", pathFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
    
    /**   
     * @Title: confirmPay   
     * @Description: 确认支付————————后台  
     * @author: WangLongFei  
     * @date: 2017年7月17日 下午4:03:05   
     * @param orderId	订单id
     * @param payImgUrl	支付凭证
     * @return  
     * @return: Object  
     */
    @RequestMapping(value = "/confirmPay", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object confirmPay(PremiumOrder po){
    	if(StringUtil.isNotEmpty(po.getPayImgUrl())){
    		boolean f = premiumOrderService.updateConfirmPay(po.getId().toString(),po.getPayImgUrl());
    		if(f){
    			return renderSuccess("确认支付成功！","200");
    		}else{
    			return renderError("确认支付失败！", "500");
    		}
    	}else{
    		return renderError("请上传支付凭证！", "505");
    	}
    }
    
    /**   
     * @Title: confirmPay   
     * @Description: 确认支付————————后台  
     * @author: WangLongFei  
     * @date: 2017年7月17日 下午4:03:05   
     * @param orderId	订单id
     * @param payImgUrl	支付凭证
     * @return  
     * @return: Object  
     */
    @RequestMapping(value = "/delPayImg", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object delPayImg(PremiumOrder po){
    	if(StringUtil.isNotEmpty(po.getPayImgUrl())){
    		boolean f = premiumOrderService.updateConfirmPay(po.getId().toString(),po.getPayImgUrl());
    		if(f){
    			return renderSuccess("确认支付成功！","200");
    		}else{
    			return renderError("确认支付失败！", "500");
    		}
    	}else{
    		return renderError("请上传支付凭证！", "505");
    	}
    }
    
}
