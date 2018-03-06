package com.webill.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.util.LianLianUtil;
import com.webill.core.model.TradeLog;
import com.webill.core.model.lianlianEntity.CertPayWebReq;
import com.webill.core.model.lianlianEntity.CertPayWebSyncResp;
import com.webill.core.model.lianlianEntity.ReturnBean;
import com.webill.core.service.ITradeLogService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/** 
 * @ClassName: TradeLogController 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月30日 下午6:16:52 
 */
@Controller
@RequestMapping(value = "/api/trade")
public class TradeLogController extends BaseController{
	@Autowired
	private ITradeLogService tradeLogService;
	
	/** 
	* @Title: certPay 
	* @Description: 认证支付生成报文数据
	* @author ZhangYadong
	* @date 2017年11月1日 下午8:57:15
	* @param jsonStr (姓名、身份证号必传)
	* @return
	* @throws Exception
	* @return Object
	*/
	@ApiOperation(value = "连连-认证支付")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取认证支付报文成功！")})
	@RequestMapping(value = "/certPay", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult certPay(@ApiParam(required = true, value="用户-userId，信息套餐-infoGoodsId") @RequestBody String jsonStr) {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		int userId = jo.getIntValue("userId");
		int infoGoodsId = jo.getIntValue("infoGoodsId");
		
		CertPayWebReq webReq = tradeLogService.certPayRequest(request, userId, infoGoodsId);
		logger.info("连连-认证支付请求数据："+JSONUtil.toJSONString(webReq));
		return renderSuccess(webReq);
	}

	/** 
	 * @Title: urlReturn 
	 * @Description: 认证支付-同步通知返回到前端
	 * @author ZhangYadong
	 * @date 2018年1月31日 下午1:52:24
	 * @param cpwsr
	 * @return
	 * @throws Exception
	 * @return String
	 */
	@ApiOperation(value = "认证支付-同步通知返回到前端")
	@RequestMapping(value = "/urlReturn", method = { RequestMethod.POST, RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	public String urlReturn(CertPayWebSyncResp cpwsr) throws Exception {
		logger.info("同步通知数据："+JSONUtil.toJSONString(cpwsr));
		if ("SUCCESS".equals(cpwsr.getResult_pay())) {
			return "redirect:" + constPro.LIANLIANPAY_WEB_URL + "/#/personal/account";
		}else {
			return "redirect:" + constPro.LIANLIANPAY_WEB_URL + "/#/personal/buyData";
		}
	}
	
	/** 
	* @Title: notifyUrl 
	* @Description: 认证支付-异步通知
	* @author ZhangYadong
	* @date 2017年11月14日 下午6:14:35
	* @param request
	* @param response
	* @param model
	* @return void
	*/
	@Transactional
	@RequestMapping(value = "/notifyUrl", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	public void notifyUrl(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
        	response.setCharacterEncoding("UTF-8");
            String reqStr = LianLianUtil.readReqStr(request);
            if (LianLianUtil.isnull(reqStr)) {
            	ReturnBean rb = new ReturnBean("9999", "交易失败");
            	response.getWriter().write(JSON.toJSONString(rb));
            	response.getWriter().flush();
                return;
            }
            logger.info("接收支付异步通知数据：【" + reqStr + "】");
            JSONObject reqObj = JSON.parseObject(reqStr);
            try {
            	if (!LianLianUtil.checkSignRSA(reqObj, constPro.LIANLIANPAY_PUBLIC_KEY)) {
            		logger.info("支付异步通知验签失败");
            		ReturnBean rb = new ReturnBean("9999", "交易失败");
            		response.getWriter().write(JSON.toJSONString(rb));
            		response.getWriter().flush();
                    return;
                }
            } catch (Exception e) {
            	logger.info("异步通知报文解析异常：" + e);
            	ReturnBean rb = new ReturnBean("9999", "交易失败");
        		response.getWriter().write(JSON.toJSONString(rb));
        		response.getWriter().flush();
                return;
            }
            
            String result_pay = reqObj.getString("result_pay"); //支付结果
            if("SUCCESS".equals(result_pay)){ //支付成功
            	String transNo = reqObj.getString("no_order"); 	//支付订单号（交易流水号）
            	String orderDt = reqObj.getString("dt_order");		  	//商户订单时间
            	String orderMoney = reqObj.getString("money_order"); 	//交易金额
            	
            	TradeLog tl = new TradeLog();
            	tl.setTransNo(transNo);
        		TradeLog tlog = tradeLogService.selectOne(tl);
        		
        		if (tlog.getIsAddTimes() == 0) { // 是否增加次数：0-未增加 1-增加成功
        			// 更新交易流水状态
        			boolean f1 = tradeLogService.updateTradeStatus(transNo, reqStr);
        			// 添加用户次数
        			boolean f2 = tradeLogService.addUserTimes(transNo);
        			
        			if (f1 && f2) {
        				ReturnBean rb = new ReturnBean("0000", "交易成功");
        				response.getWriter().write(JSON.toJSONString(rb));
        				response.getWriter().flush();
        				// 发送邮件
        				tradeLogService.emailOrderPayParam(transNo, orderDt, orderMoney);
        				logger.info("支付异步通知数据接收处理成功");
        			}else {
        				logger.info("修改订单状态失败");
        				ReturnBean rb = new ReturnBean("9999", "交易失败");
        				response.getWriter().write(JSON.toJSONString(rb));
        				response.getWriter().flush();
        				return;
        			}
				}
            } else {
            	logger.info("异步通知返回支付结果失败");
            	ReturnBean rb = new ReturnBean("9999", "交易失败");
                response.getWriter().write(JSON.toJSONString(rb));
        		response.getWriter().flush();
                return;
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings(value = {"unchecked", "rawtypes"})
	@ApiOperation(value = "获取用户购买记录")
	@RequestMapping(value = "/list", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String tradeLogList(HttpServletRequest request, TradeLog tl) {
		Page page = this.getPage(request, Integer.MAX_VALUE);
        page = tradeLogService.getTradeLogList(page, tl);
        return JSONUtil.toJSONString(page);
    }
}
