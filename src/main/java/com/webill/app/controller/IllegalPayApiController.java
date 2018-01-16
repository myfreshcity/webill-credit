package com.webill.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.util.SignUtils;
import com.webill.app.util.BigDecimalUtil;
import com.webill.app.util.OrderUtils;
import com.webill.app.util.WeixinPayUtil;
import com.webill.app.util.XMLUtil;
import com.webill.core.Constant;
import com.webill.core.ResponseInfo;
import com.webill.core.model.IllegalDetail;
import com.webill.core.model.IllegalOrder;
import com.webill.core.model.UserContact;
import com.webill.core.model.UserCoupon;
import com.webill.core.service.IIllegalDetailService;
import com.webill.core.service.IIllegalOrderService;
import com.webill.core.service.IUserContactService;
import com.webill.core.service.IUserCouponService;
import com.webill.core.service.IUserService;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by david on 16/10/28.
 */
@Api(value = "违章支付API")
@Controller
@RequestMapping(value="/api/illegalPay")
public class IllegalPayApiController extends BaseController {
	@Autowired
	IUserService userService;

	@Autowired
	IIllegalOrderService illegalOrderService;

	@Autowired
	IIllegalDetailService illegalDetailService;
	
	@Autowired
	IUserContactService userContactService;
	
	@Autowired
	IUserCouponService userCouponService;

    @Autowired
    private WxPayConfig payConfig;
    @Autowired
    private WxPayService payService;

	/**
	 * @Title: toHandle
	 * @Description: 保存违章订单详情
	 * @author: WangLongFei
	 * @date: May 17, 2017 11:33:21 AM
	 * @param request
	 * @param response
	 * @param totalFee
	 * @param serviceFee
	 * @return
	 * @return: ModelAndView
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 */
	@ApiOperation(value = "保存违章订单详情")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "保存成功！"),@ApiResponse(code = 500, message = "保存失败！")})
	@RequestMapping(value = "/toHandle", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult toHandle(@ApiParam(required = true, value = "违章订单") @RequestBody IllegalOrder order)
			throws UnsupportedEncodingException, ParseException {
		List<IllegalDetail> details = order.getDetailList();
		
    	String token = request.getHeader("token");
    	String userId = jwtUtil.getUserId(token);
    	
		order.setCount(details.size());
		order.setCreatedTime(new Date());
		order.setStatus(0);
		//总费用
		Integer totalMoney = 0;
		Integer totalCount = 0;
		String detailIds = ""; 
		Integer totalFee = Integer.valueOf(constPro.SERVER_FEE)*(details.size());
		Integer discountMoney = Integer.valueOf(constPro.DISCOUNT_MONEY)*(details.size());
		for(IllegalDetail d:details){
			totalMoney += d.getPrice();
			detailIds += d.getId()+",";
		}
		//获取订单关联的违章id
		detailIds = detailIds.replaceAll(",$", "");
		order.setDetailIds(detailIds);		
		totalCount =totalMoney+totalFee-discountMoney;
		order.setTotalFee(totalFee);
		order.setTotalMoney(totalMoney);
		order.setTotalCount(totalCount);
		order.setUserId(Integer.valueOf(userId));
		
//		order.setTradeNo(tradeNo);
		order.setDiscountMoney(discountMoney);
		
		//获取默认联系人
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("user_id", userId);
		m.put("is_default", Constant.USER_CONTACT_IS_DEFAULT);
		List<UserContact> uclist = userContactService.selectByMap(m);
		//将联系人关联到订单
		if(uclist.size()==1){
			order.setContactId(uclist.get(0).getId());
		}
		logger.info("更新多笔违章信息的订单号和插入订单到数据库：");
		ResponseInfo result = illegalDetailService.saveIIllegalDetails(details,order);
		if(result.toString().equals("SUCCESS")){
			logger.info("返回jsonstr：" + order.getId());
			return renderSuccess("保存成功！", "200", order.getId());
		}else{
			return renderError("保存失败！", "500"); 
		}
	}

	/**
	 * @Title: toPay
	 * @Description: 支付并返回参数
	 * @author: WangLongFei
	 * @date: May 17, 2017 10:16:17 AM
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @return: String
	 * @throws Exception 
	 */
	@ApiOperation(value = "支付并返回参数 ")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "办理成功！"),@ApiResponse(code = 500, message = "办理失败！")})
	@RequestMapping(value = "/toPay", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult toPay(@ApiParam(required = true, value = "返回订单ID，用户openId，联系人id，优惠券id") @RequestBody String jsonStr
			) throws Exception {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		Integer couponId = (Integer) jo.get("couponId");
		String contactId = jo.get("contactId").toString();
		String openId = jo.get("openId").toString();
		String orderId = jo.get("orderId").toString();
        IllegalOrder order = illegalOrderService.selectById(orderId);
        String[] detailIds = order.getDetailIds().split(",");
        IllegalDetail detail = null;
        boolean f = false;
        for(String did:detailIds){
        	detail = new IllegalDetail();
        	detail = illegalDetailService.selectById(did);
        	if(detail.getStatus()==1&&detail.getOrderId()!=null&&!"".equals(detail.getOrderId())){//已有人支付成功
        		f = false;
        		IllegalOrder entity = new IllegalOrder();
        		entity.setStatus(-1);
        		illegalOrderService.updateSelective(entity, order);
        		break;
        	}else{
        		f = true;
        	}
        }
        if(f){
        	//根据优惠券计算实付费用
        	// TODO: 2017/6/28
        	int totalCount = order.getTotalCount();
        	UserCoupon uc = new UserCoupon();
        	Map<String,Object> map = new HashMap<String,Object>();
        	map.put("couponId", couponId);
        	List<UserCoupon> uclist = userCouponService.getUserCouponList(map);
        	if(uclist.size()>0){
        		int oldFee = order.getTotalFee()-order.getDiscountMoney();
        		
        		BigDecimal oldFeeB = new BigDecimal(oldFee);
        		if(oldFeeB.compareTo(uclist.get(0).getSaleAmt())==0||oldFeeB.compareTo(uclist.get(0).getSaleAmt())==-1){
        			order.setTotalCount(order.getTotalCount()-oldFee);
        		}else{
        			order.setTotalCount((int)BigDecimalUtil.add((double)oldFee, -(uclist.get(0).getSaleAmt().doubleValue())));
        		}
        	}
        	totalCount = order.getTotalCount();
//        	if(couponId!=null){
//        		if(couponId.intValue()!=0){
//        			order.setCouponId(couponId);
//        		}
//        	}
        	
        	//更新联系人ID
        	// TODO: 2017/6/28  设置实付费用
        	order.setUpdateTime(new Date());
        	order.setContactId(Integer.valueOf(contactId));
        	illegalOrderService.updateSelectiveById(order);
        	
        	//向微信请求数据准备
        	
        	String body = "慢慢花-交通违章代缴";// 商品描述
        	String spbill_create_ip = request.getRemoteAddr();//订单生成的机器 IP
        	Integer total_fee = totalCount * 100;//总金额
        	total_fee = 1; //测试，设置为1分钱
        	String notify_url = constPro.BASE_WEIXIN_URL + "/api/illegalPay/weixinReceive";
        	
        	
        	WxPayUnifiedOrderRequest prepayInfo = new WxPayUnifiedOrderRequest();
        	prepayInfo.setOpenid(openId);
        	prepayInfo.setOutTradeNo(order.getTradeNo());// 商户订单号
        	prepayInfo.setTotalFee(total_fee);
        	prepayInfo.setBody(body);
        	prepayInfo.setTradeType("JSAPI");
        	prepayInfo.setSpbillCreateIp(spbill_create_ip);
        	prepayInfo.setNotifyURL(notify_url);
        	
        	
        	try {
        		this.logger
        		.debug("PartnerKey is :" + this.payConfig.getMchKey());
        		Map<String, String> payInfo = this.payService.getPayInfo(prepayInfo);
        		return renderSuccess(payInfo);
        	} catch (WxErrorException e) {
        		logger.error("", e);
        		return renderError(e.getError().toString());
        	}
        }else{
        	return renderError();
        }
	}
	
	/**
	 * @Title: toWXPaySuccess
	 * @Description: 页面js返回支付成功后，查询微信后台是否支付成功，然后跳转结果页面
	 * @author: WangLongFei
	 * @date: May 17, 2017 1:20:35 PM
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 * @return: String
	 */
	@ApiOperation(value = "跳转微信支付成功页面")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "办理成功！"),@ApiResponse(code = 500, message = "办理失败！")})
	@RequestMapping(value = "/success", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult toWXPaySuccess(@ApiParam(required = true, value = "订单编号") @RequestBody String tradeNo,Model model)
			throws IOException {
		String resultstr = "";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("trade_no", tradeNo);
		//订单信息
		List<IllegalOrder> orderList = illegalOrderService.selectByMap(map);
		IllegalOrder order = null;
		if(orderList.size()>0){
			order = new IllegalOrder();
			order = orderList.get(0);
		}
		if(order.getStatus().intValue()==Constant.NORMAL_STATUS){
			try {
				Map resultMap = WeixinPayUtil.checkWxOrderPay(tradeNo);
				String return_code = (String) resultMap.get("return_code");
				String result_code = (String) resultMap.get("result_code");
				if ("SUCCESS".equals(return_code)) {
					if ("SUCCESS".equals(result_code)) {
						model.addAttribute("orderId", tradeNo);
						model.addAttribute("payResult", "1");
						ResponseInfo result = illegalOrderService.savePaySuccess(tradeNo);
						if(result.equals(ResponseInfo.SUCCESS)){
							Map<String,Object> maps = new HashMap<String,Object>();
							maps.put("trade_no", tradeNo);
							//订单信息
							List<IllegalOrder> olist = illegalOrderService.selectByMap(maps);
							if(olist.size()>0){
								order = olist.get(0);
								resultstr = JSONObject.toJSONString(order);
							}
						}
					} else {
						String err_code = (String) resultMap.get("err_code");
						String err_code_des = (String) resultMap.get("err_code_des");
						model.addAttribute("err_code", err_code);
						model.addAttribute("err_code_des", err_code_des);
						model.addAttribute("payResult", "0");
						resultstr = JSONObject.toJSONString(order);
					}
				} else {
					model.addAttribute("payResult", "0");
					model.addAttribute("err_code_des", "通信错误");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(order.getStatus()==1){
			resultstr = JSONObject.toJSONString(order);
		}
		return renderSuccess("同步成功！", "200", resultstr);
	}
	
	/**
	 * @Title: weixinReceive
	 * @Description: 微信异步回调，不会跳转页面
	 * @author: WangLongFei
	 * @date: May 17, 2017 1:26:18 PM
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @return: String
	 */
    @ApiOperation(value = "同步微信支付状态")
    @ApiResponses(value = {})
	@Transactional
	@RequestMapping(value = "/syncWeiXinPayResult", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	public void weixinReceive(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            synchronized (this) {
                Map<String, String> kvm = XMLUtil.parseRequestXmlToMap(request);
                if (SignUtils.checkSign(kvm, this.payConfig.getMchKey())) {
                    if (kvm.get("result_code").equals("SUCCESS")) {
                        //TODO(user) 微信服务器通知此回调接口支付成功后，通知给业务系统做处理。幂等操作
                        String tradeNo = kvm.get("out_trade_no");
                        logger.info("out_trade_no: " + tradeNo + " pay SUCCESS!");
                        // 支付成功更新订单状态为-----缴费中
                        IllegalOrder qOrder = new IllegalOrder();
                        qOrder.setTradeNo(tradeNo);
                        IllegalOrder iord = illegalOrderService.selectOne(qOrder);
                        iord.setStatus(1);
                        iord.setId(iord.getId());
                        iord.setUpdateTime(new Date());
                        logger.info("更新订单状态为1，缴费中：");
                        illegalOrderService.updateById(iord);
                        //设置优惠券过期
                        response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[ok]]></return_msg></xml>");
                        ResponseInfo info =  illegalOrderService.savePaySuccess(iord.getId().toString());
                        if(info.equals(ResponseInfo.SUCCESS)){
                        	response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[ok]]></return_msg></xml>");
                        }
                    } else {
                        this.logger.error("out_trade_no: "
                                + kvm.get("out_trade_no") + " result_code is FAIL");
                        response.getWriter().write(
                                "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[result_code is FAIL]]></return_msg></xml>");
                    }
                } else {
                    response.getWriter().write(
                            "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[check signature FAIL]]></return_msg></xml>");
                    this.logger.error("out_trade_no: " + kvm.get("out_trade_no")
                            + " check signature FAIL");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

	}

}
