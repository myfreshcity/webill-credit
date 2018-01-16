package com.webill.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.util.SignUtils;
import com.webill.app.util.OrderUtils;
import com.webill.app.util.WeixinPayUtil;
import com.webill.app.util.XMLUtil;
import com.webill.core.Constant;
import com.webill.core.ResponseInfo;
import com.webill.core.model.IllegalDetail;
import com.webill.core.model.IllegalOrder;
import com.webill.core.model.PremiumOrder;
import com.webill.core.model.UserContact;
import com.webill.core.service.IIllegalDetailService;
import com.webill.core.service.IIllegalOrderService;
import com.webill.core.service.IPremiumDetailService;
import com.webill.core.service.IPremiumOrderService;
import com.webill.core.service.IUserContactService;
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
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@Api(value = "投保支付API")
@Controller
@RequestMapping(value="/api/premiumPay")
public class PremiumPayApiController extends BaseController {
	@Autowired
	IUserService userService;

	@Autowired
	IPremiumDetailService premiumDetailService;

	@Autowired
	IPremiumOrderService premiumOrderService;
	
	@Autowired
	IUserContactService userContactService;

    @Autowired
    private WxPayConfig payConfig;
    @Autowired
    private WxPayService payService;

	@Autowired
	private RedisOperationsSessionRepository sessionRepository;


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
	public Object toPay(@ApiParam(required = true, value = "返回订单ID，用户openId，") @RequestBody String jsonStr) throws Exception {
		Object result = null;
		JSONObject jo = JSONObject.parseObject(jsonStr);
		String orderId = jo.get("orderId").toString();
		String openId = jo.get("openId").toString();
		
        PremiumOrder order = premiumOrderService.selectById(orderId);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("license_no", order.getLicenseNo());
        List<PremiumOrder> orderlist = premiumOrderService.selectByMap(map);
        boolean l = true;
        for(PremiumOrder po:orderlist){
        	if(po.getStatus().intValue()==Constant.PREMIUM_ORDER_STATUS_PAYSUCCESS.intValue()){
//        		判断是否存在已经支付成功的订单，l:true能支付，l:false,不能支付。
        		l = false;
        		break;
        	}
        }
        if(l){
    		//根据优惠券计算实付费用
    		double totalCount = order.getPrmValue().doubleValue();
    		
    		//向微信请求数据准备
    		
    		String body = "慢慢花-在线投保";// 商品描述
    		String spbill_create_ip = request.getRemoteAddr();//订单生成的机器 IP
    		Integer total_fee = (int) (totalCount * 100);//总金额
    		total_fee = 1; //测试，设置为1分钱
    		String notify_url = constPro.BASE_WEIXIN_URL + "/api/premiumPay/notifyUrl";
    		
    		
    		WxPayUnifiedOrderRequest prepayInfo = new WxPayUnifiedOrderRequest();
    		prepayInfo.setOpenid(openId);
    		prepayInfo.setOutTradeNo(order.getOrderNo());// 商户订单号
    		prepayInfo.setTotalFee(total_fee);
    		prepayInfo.setBody(body);
    		prepayInfo.setTradeType("JSAPI");
    		prepayInfo.setSpbillCreateIp(spbill_create_ip);
    		prepayInfo.setNotifyURL(notify_url);
    		
    		
    		try {
    			this.logger
    			.debug("PartnerKey is :" + this.payConfig.getMchKey());
    			Map<String, String> payInfo = this.payService.getPayInfo(prepayInfo);
    			result = renderSuccess(payInfo);
    		} catch (WxErrorException e) {
    			logger.error("", e);
    			result = renderError(e.getError().toString());
    		}
        }else{
        	result = renderError("该车辆已投保，请勿重复投保！", "505");
        }
        return result;
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
	@ApiResponses(value = {@ApiResponse(code = 200, message = "支付成功！"),@ApiResponse(code = 500, message = "支付失败！"),@ApiResponse(code = 505, message = "该订单信息有误，请重新下单！")})
	@RequestMapping(value = "/success", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult toWXPaySuccess(@ApiParam(required = true, value = "订单编号") @RequestBody String orderNo,Model model)
			throws IOException {
		JsonResult resultstr = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("order_no", orderNo);
		//订单信息
		List<PremiumOrder> orderList = premiumOrderService.selectByMap(map);
		PremiumOrder order = null;
		if(orderList.size()>0){
			order = new PremiumOrder();
			order = orderList.get(0);
			if(order.getStatus().intValue()==Constant.PREMIUM_ORDER_STATUS_PAYWAITTING){
				try {
					Map resultMap = WeixinPayUtil.checkWxOrderPay(orderNo);
					String return_code = (String) resultMap.get("return_code");
					String result_code = (String) resultMap.get("result_code");
					if ("SUCCESS".equals(return_code)) {
						if ("SUCCESS".equals(result_code)) {
							model.addAttribute("orderId", orderNo);
							model.addAttribute("payResult", "1");
							ResponseInfo result = premiumOrderService.savePaySuccess(orderNo);
							if(result.equals(ResponseInfo.SUCCESS)){
								Map<String,Object> maps = new HashMap<String,Object>();
								maps.put("trade_no", orderNo);
								//订单信息
								List<PremiumOrder> olist = premiumOrderService.selectByMap(maps);
								order = olist.get(0);
								resultstr = renderSuccess("支付成功！", "200", order);
							}
						} else {
							String err_code = (String) resultMap.get("err_code");
							String err_code_des = (String) resultMap.get("err_code_des");
							model.addAttribute("err_code", err_code);
							model.addAttribute("err_code_des", err_code_des);
							model.addAttribute("payResult", "0");
							resultstr=renderError("支付失败！", "500");
						}
					} else {
						model.addAttribute("payResult", "0");
						model.addAttribute("err_code_des", "通信错误");
						resultstr=renderError("支付失败！", "500");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(order.getStatus().intValue()==Constant.PREMIUM_ORDER_STATUS_PAYSUCCESS.intValue()){
				resultstr = renderSuccess("支付成功！", "200", order);
			}
		}else{
			resultstr=renderError("该订单信息有误，请重新下单！", "500");
		}
		return resultstr;
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
	@Transactional
	@RequestMapping(value = "/notifyUrl", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
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
                        PremiumOrder qOrder = new PremiumOrder();
                        qOrder.setOrderNo(tradeNo);
                        PremiumOrder iord = premiumOrderService.selectOne(qOrder);
                        iord.setStatus(1);
                        iord.setId(iord.getId());
                        logger.info("更新订单状态为1，缴费中：");
                        premiumOrderService.updateSelectiveById(iord);
                        response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[ok]]></return_msg></xml>");
                        ResponseInfo info =  premiumOrderService.savePaySuccess(iord.getId().toString());
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
