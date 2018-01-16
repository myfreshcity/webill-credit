package com.webill.app.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.webill.app.util.ApplicInsurRelation;
import com.webill.app.util.AreaUtil;
import com.webill.app.util.CardType;
import com.webill.app.util.StringUtil;
import com.webill.app.util.TransNoUtil;
import com.webill.core.Constant;
import com.webill.core.ResponseInfo;
import com.webill.core.model.Category;
import com.webill.core.model.HzNotify;
import com.webill.core.model.HzPay;
import com.webill.core.model.InsureReq;
import com.webill.core.model.OrderApplicant;
import com.webill.core.model.OrderBeneficiary;
import com.webill.core.model.OrderEnsureProject;
import com.webill.core.model.OrderInsurant;
import com.webill.core.model.OrderLog;
import com.webill.core.model.Product;
import com.webill.core.model.ProductProtectItem;
import com.webill.core.model.TOrder;
import com.webill.core.model.User;
import com.webill.core.service.IOrderApplicantService;
import com.webill.core.service.IOrderBeneficiaryService;
import com.webill.core.service.IOrderEnsureProjectService;
import com.webill.core.service.IOrderInsurantService;
import com.webill.core.service.IOrderLogService;
import com.webill.core.service.IProductProtectItemService;
import com.webill.core.service.IProductService;
import com.webill.core.service.ITOrderService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/** 
* @ClassName: OrderApiController 
* @Description: 
* @author ZhangYadong
* @date 2017年11月16日 下午6:22:46 
*/
@Api(value = "订单相关API")
@Controller
@RequestMapping("/api/tOrder")
public class TOrderApiController extends BaseController{
	@Autowired
	private ITOrderService tOrderService;
	
	@Autowired
	private IOrderInsurantService orderInsurantService;
	
	@Autowired
	private IOrderBeneficiaryService orderBeneficiaryService;
	
    @Autowired
    private IProductService productService;
    
    @Autowired
    private IProductProtectItemService productProtectItemService;
    
    @Autowired
    private IOrderApplicantService orderApplicantService;
    
    @Autowired
    private IOrderLogService orderLogService;
    
	
    /** 
     * @Title: orderList 
     * @Description: 用户订单列表 
     * @author: WangLongFei
     * @date: 2018年1月4日 上午11:56:07 
     * @param userId
     * @return
     * @return: JsonResult
     */
    @RequestMapping(value = "/list/{userId}", method = {RequestMethod.GET},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult orderList(@PathVariable String userId) {
    	Object obj = tOrderService.getOrderList(userId);
    	return renderSuccess("200","获取成功！",obj);
    }
	
	/** 
	 * @Title: orderDetail 
	 * @Description: 订单详情
	 * @author: WangLongFei
	 * @date: 2018年1月4日 上午9:12:00 
	 * @param id
	 * @param model
	 * @return
	 * @return: JsonResult
	 */
   @RequestMapping(value = "/orderDetail/{id}", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
   @ResponseBody
   public JsonResult orderDetail(@PathVariable(value="id")String id,Model model) {
	  TOrder po = tOrderService.getOrderDetail(id);
	  if(po!=null){
		  return renderSuccess("200","获取成功！",po);
	  }else{
		  return renderError();
	  }
   }

	/** 
	 * @Title: isClient 
	 * @Description: 判断客户端类型
	 * @author: WangLongFei
	 * @date: 2018年1月4日 下午1:15:10 
	 * @param request
	 * @return
	 * @return: String
	 */
	public String isClient(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent").toLowerCase();
		if (userAgent == null || userAgent.indexOf("micromessenger") == -1 ? false : true) { 
			// 判断当前客户端是否为微信
			return "weixin";
		}else{
			return "pc";
		}
	}

	/** 
	 * @Title: getInsureParam 
	 * @Description: 下单
	 * @author: WangLongFei
	 * @date: 2018年1月4日 下午1:19:19 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 * @return: Object
	 */
	@ApiOperation(value = "下单")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "下单成功！"),@ApiResponse(code = 500, message = "下单失败！")})
	@RequestMapping(value = "/getInsureParam", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object getInsureParam(@ApiParam(value = "下单所需")@RequestBody String jsonStr) throws Exception {
		logger.debug("下单数据=====>"+jsonStr);
		//下单所需Model
		InsureReq ir = JSONObject.parseObject(jsonStr, InsureReq.class);

		//订单Model
		TOrder po = JSONObject.parseObject(jsonStr, TOrder.class);
		
		//下单
		Object obj = tOrderService.saveDoInsure(ir,po);
		JSONObject jo = JSONObject.parseObject(JSONUtil.toJSONString(obj));
		if(jo.containsKey("id")){
			return renderSuccess("下单成功！", "200",jo.getString("id"));
		}else{
			return renderError(jo.getString("respMsg"), "500");
		}
	}
	
	/** 
	 * @Title: onlinePay 
	 * @Description: 在线支付
	 * @author: WangLongFei
	 * @date: 2018年1月4日 下午1:19:40 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 * @return: Object
	 */
	@ApiOperation(value = "支付")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "支付成功！"),@ApiResponse(code = 500, message = "支付失败！")})
	@RequestMapping(value = "/onlinePay", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object onlinePay(@ApiParam(value ="惠泽支付所需参数")@RequestBody String jsonStr) throws Exception {
		HzPay pay = JSONObject.parseObject(jsonStr, HzPay.class);
		TOrder po = tOrderService.selectById(pay.getId());
		Product prod = productService.selectById(po.getProductId());
		if (StringUtil.isNotEmpty(prod.getImgUrlShow())) {
	      prod.setImgUrlShow(constPro.PRODUCT_FILE_ACCESS_PATH + prod.getImgUrlShow());
	    }
		po.setProduct(prod);
		Map<String,Object> reqMap = new HashMap<String,Object>();
		reqMap.put("transNo", TransNoUtil.genTransNo());
		reqMap.put("partnerId", constPro.HUIZE_PARTNERID);
		reqMap.put("insureNums", po.getInOrderNo());
		reqMap.put("gateway", pay.getGateway());
		reqMap.put("money", po.getPayAmount());
		reqMap.put("clientType", pay.getClientType());
		reqMap.put("callBackUrl",constPro.DOMAIN_STATIC_URL+"/api/tOrder/urlReturn?id="+pay.getId());

		String resultStr = tOrderService.onlinePay(reqMap);
		if(resultStr!=null){
			JSONObject jo = JSONObject.parseObject(resultStr);
			if(jo.getString("respCode").equals("0")){
				return renderSuccess("支付成功！", "200",jo);
			}else if(jo.getString("respCode").equals("50001")){
				TOrder order = new TOrder();
				order.setId(Integer.valueOf(pay.getId()));
				order.settStatus(Constant.ORDER_STATUS_ALREADY_PAY);
				boolean f = tOrderService.updateSelectiveById(order);
				return renderSuccess("该保单已支付！", "400",jo);
			}else{
				return renderError("支付失败！", "500",jo);
			}
		}else{
			return renderError("支付失败！", "500");
		}
	}
	
	/** 
	 * @Title: downloadUrl 
	 * @Description: 下载保单
	 * @author: WangLongFei
	 * @date: 2017年12月4日 下午4:26:51 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 * @return: Object
	 */
	@ApiOperation(value = "下载保单")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "下载保单成功！"),@ApiResponse(code = 500, message = "下载保单失败！")})
	@RequestMapping(value = "/downloadUrl", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object downloadUrl(@ApiParam(value ="惠泽下载保单所需参数")@RequestBody String jsonStr) throws Exception {
		HzPay pay = JSONObject.parseObject(jsonStr, HzPay.class);
		Map<String,Object> reqMap = new HashMap<String,Object>();
		reqMap.put("transNo", pay.getTransNo());
		reqMap.put("partnerId", constPro.HUIZE_PARTNERID);
		reqMap.put("insureNum", pay.getInsureNums());

		String resultStr = tOrderService.downloadUrl(reqMap);
		if(resultStr!=null){
			JSONObject jo = JSONObject.parseObject(resultStr);
			if(jo.getString("respCode").equals("0")){
				return renderSuccess("下载保单成功！", "200",resultStr);
			}else{
				return renderError("下载保单失败！", "500",resultStr);
			}
		}else{
			return renderError("下载保单失败！", "500",resultStr);
		}
	}
	
    
	/** 
	 * @Title: surrenderPolicy 
	 * @Description: 退保
	 * @author: WangLongFei
	 * @date: 2017年12月4日 下午5:56:20 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 * @return: Object
	 */
	@ApiOperation(value = "退保")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "退保成功！"),@ApiResponse(code = 500, message = "退保失败！")})
	@RequestMapping(value = "/surrenderPolicy", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object surrenderPolicy(@ApiParam(value ="惠泽退保所需参数") @RequestBody String jsonStr) throws Exception {
		HzPay pay = JSONObject.parseObject(jsonStr, HzPay.class);
		Map<String,Object> reqMap = new HashMap<String,Object>();
		reqMap.put("transNo", pay.getTransNo());
		reqMap.put("partnerId", constPro.HUIZE_PARTNERID);
		reqMap.put("insureNums", pay.getInsureNums());

		String resultStr = tOrderService.surrenderPolicy(reqMap);
		if(resultStr!=null){
			JSONObject jo = JSONObject.parseObject(resultStr);
			if(jo.getString("respCode").equals("0")){
				return renderSuccess("退保成功！", "200",resultStr);
			}else{
				return renderError("退保失败！", "500",resultStr);
			}
		}else{
			return renderError("退保失败！", "500",resultStr);
		}
	}
	
	
	/** 
	 * @Title: orderDetail 
	 * @Description: 保单查询
	 * @author: WangLongFei
	 * @date: 2017年12月4日 下午4:26:51 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 * @return: Object
	 */
	@ApiOperation(value = "保单查询")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "下单成功！"),@ApiResponse(code = 500, message = "下单失败！")})
	@RequestMapping(value = "/orderDetail", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object orderDetail(@ApiParam(value ="惠泽支付所需参数") @RequestBody String jsonStr) throws Exception {
		HzPay pay = JSONObject.parseObject(jsonStr, HzPay.class);
		Map<String,Object> reqMap = new HashMap<String,Object>();
		reqMap.put("transNo", TransNoUtil.genTransNo());
		reqMap.put("partnerId", constPro.HUIZE_PARTNERID);
		reqMap.put("insureNum", pay.getInsureNums());

		String resultStr = tOrderService.orderDetail(reqMap);
		if(resultStr!=null){
			JSONObject jo = JSONObject.parseObject(resultStr);
			if(jo.getString("respCode").equals("0")){
				return renderSuccess("保单查询成功！", "200",resultStr);
			}else{
				return renderError("保单查询失败！", "500",resultStr);
			}
		}else{
			return renderError("保单查询失败！", "500",resultStr);
		}
	}
	
	/** 
	 * @Title: hzNotify 
	 * @Description: 慧择通知回调接口
	 * @author: WangLongFei
	 * @date: 2017年11月29日 下午1:42:05 
	 * @return
	 * @throws Exception
	 * @return: Object
	 */
	@ApiOperation(value = "慧择通知回调接口")
	@RequestMapping(value = "/hzNotify", method = { RequestMethod.POST,RequestMethod.GET},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object hzNotify(@ApiParam(value ="惠泽支付所需参数")@RequestBody String jsonStr) throws Exception {
		logger.info("惠泽回调通知：=====>"+jsonStr);
		HzNotify hpn = JSONObject.parseObject(jsonStr, HzNotify.class);
		ResponseInfo result = tOrderService.updateByNotify(hpn);

		if(result.equals(ResponseInfo.SUCCESS)){
			return renderTrueResult();
		}else{
			return renderFalseResult("修改订单状态失败！");
		}
	}
	
	/**
	 * @Title: urlReturn
	 * @Description: 从支付链接跳转前端页面
	 * @author: WangLongFei
	 * @date: 2017年12月29日 下午2:03:13
	 * @param res_data
	 * @return
	 * @throws Exception
	 * @return: String
	 */
	@ApiOperation(value = "从支付链接跳转前端页面")
	@RequestMapping(value = "/urlReturn", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public String urlReturn(String res_data,HttpServletRequest request) throws Exception {
		logger.info("支付异步通知数据接收处理成功,回调URL"+request.getRequestURL().toString()+"\n回调URI"+request.getRequestURI().toString());
		String urlRedirect = null;
		String result = request.getParameter("result");
		String id = request.getParameter("id");
		if (result != null) {
				urlRedirect = urlRedirect("/?target=orderDetail&id=" + id,request);
				return "redirect:" + urlRedirect;
		}
		return "redirect:" + urlRedirect;
	}
	
	/** 
	 * @Title: urlRedirect 
	 * @Description: ☆☆☆☆☆—————————————— 如果是PC端直接跳转页面，如果是微信端，用户同意授权，获取code 如果用户同意授权，页面将跳转至
	 *               redirect_uri/?code=CODE&state=STATE。 code说明 ：
	 *               code作为换取access_token的票据，每次用户授权带上的code将不一样，
	 * @author: WangLongFei
	 * @date: 2018年1月4日 下午1:21:41 
	 * @param uri
	 * @param request
	 * @return
	 * @return: String
	 */
	private String urlRedirect(String uri, HttpServletRequest request) {
		String client = this.isClient(request);
		String newUri = null;
		if("weixin".equals(client)){
			if (constPro.IS_PRODUCT) {
				try {
					newUri = URLEncoder.encode(constPro.DOMAIN_URL + "/#" + uri, "UTF-8");
					newUri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + constPro.WEIXIN_APPID
							+ "&redirect_uri=" + newUri
							+ "&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect";
				} catch (UnsupportedEncodingException e) {
					logger.error("encode url fail:" + uri, e);
				}
			} else {
				String forwardUrl = request.getParameter("url");
				String code = request.getParameter("code");
				if(org.apache.commons.lang3.StringUtils.isBlank(forwardUrl)){
					forwardUrl = constPro.DOMAIN_URL;
				}
				if(org.apache.commons.lang3.StringUtils.isBlank(code)){
					code = "CODE";
				}
				newUri = forwardUrl + "/?code="+code+"&status=123#" + uri;
			}
		}else{
			newUri = constPro.DOMAIN_URL;
		}
		return newUri;
	}
}
