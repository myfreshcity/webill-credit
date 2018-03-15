package com.webill.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.model.Customer;
import com.webill.core.model.User;
import com.webill.core.model.dianhuabang.DHBGetLoginReq;
import com.webill.core.model.dianhuabang.DHBLoginReq;
import com.webill.core.model.juxinli.JXLCollectReq;
import com.webill.core.model.juxinli.JXLResetPasswordReq;
import com.webill.core.service.ICustomerService;
import com.webill.core.service.IDianHuaBangService;
import com.webill.core.service.IJuxinliService;
import com.webill.core.service.IUserService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/** 
 * @ClassName: CustomerAPIController 
 * @Description: 客户控制层
 * @author ZhangYadong
 * @date 2018年1月17日 上午10:53:11 
 */
@Controller
@RequestMapping(value = "/api/customer")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CustomerAPIController extends BaseController{
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IJuxinliService juxinliService;
	@Autowired
	private IDianHuaBangService dianHuaBangService;

	@ApiOperation(value = "添加客户基本信息")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取客户信息成功！"), @ApiResponse(code = 210, message = "客户基本信息入库成功！"),
			@ApiResponse(code = 220, message = "客户未进行实名认证！"), @ApiResponse(code = 510, message = "客户基本信息入库失败！")})
	@RequestMapping(value = "/addCusBasicInfo", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult addCusBasicInfo(@ApiParam(required = true, value = "userId-用户ID, realName-真实姓名, "
			+ "idNo-身份证号, mobileNo-手机号") @RequestBody String jsonStr){
		Customer cus = JSONUtil.toObject(jsonStr, Customer.class);
		
		User user = userService.selectById(cus.getUserId());
		if (user != null && user.getIsVerified().intValue() == 0) { //是否实名认证：0-否 1-是
			return renderSuccess("客户未进行实名认证！", "220");
		}
		Customer cusDetail = customerService.getCusByBasicInfo(cus);
		if (cusDetail != null) {
			return renderSuccess(cusDetail);
		}else {
			boolean f = customerService.insertSelective(cus);
			if (f) {
				Customer cust = customerService.addSelectTimes(cus);
				return renderSuccess("客户基本信息入库成功！", "210", cust);
			}else {
				return renderError("客户基本信息入库失败！", "510");
			}
		}
	}
	
	@ApiOperation(value = "客户列表")
	@RequestMapping(value = "/list", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String customerList(HttpServletRequest request, Customer cus) {
	    Page page = this.getPage(request, Integer.MAX_VALUE);
        page = customerService.getCusList(page, cus);
        return JSONUtil.toJSONString(page);
    }
	
	/*************************************************电话邦-聚信立接口***********************************************************/
	
	@ApiOperation(value = "通过token获取报告数据，申请表单提交时获取的token")
	@RequestMapping(value = "/selectReport")
	@ResponseBody
	public Object getReport(@RequestBody Customer cus) {
		String rpRes = dianHuaBangService.selectMdbReport(cus.getLatestReportKey());
		if (rpRes == null) {
			return renderError("reportKey不存在！", "501");
		}
		return renderSuccess(JSONObject.parseObject(rpRes));
	}
	
	@ApiOperation(value = "电话邦-提交申请表单获取回执信息，并完善客户联系信息")
	@RequestMapping(value = "/dhbSubmitForm", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object dhbSubmitForm(@RequestBody Customer cus) {
		// 完善客户信息
		boolean f = customerService.updateCus(cus);
		if (f) {
			// 判断客户报告是否处于获取中
			Customer isCus = customerService.selectById(cus.getId());
			if (isCus.getLatestReportStatus() != null && isCus.getLatestReportStatus() == 0) { //-1-准备采集 0-采集中 1-采集成功 2-采集失败
				return renderSuccess("您的报告处于查询中，请耐心等待！", "300");
			}
			// 客户信息转聚信立表单提交数据
			DHBGetLoginReq dhbReq = customerService.cusToDHBGetLoginReq(cus); 
			return dianHuaBangService.dhbGetSid(dhbReq, cus.getId(), cus.getTemReportType());
		}else {
			return renderError("更新客户信息失败", "510");
		}
	}
	
	@ApiOperation(value = "电话邦-提交数据采集请求")
	@RequestMapping(value = "/dhbCollect", method = { RequestMethod.POST }, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object dhbCollect(@RequestBody String jsonStr) {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		DHBLoginReq dhbReq = JSONUtil.toObject(jo.getString("dhbLoginReq"), DHBLoginReq.class);
		Customer cus = JSONUtil.toObject(jo.getString("customer"), Customer.class);
		return dianHuaBangService.dhbCollect(dhbReq, cus);
	}
	
	@ApiOperation(value = "电话邦-二次提交数据采集请求")
	@RequestMapping(value = "/dhbCollectSec", method = { RequestMethod.POST }, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object dhbCollectSec(@RequestBody String jsonStr) {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		DHBLoginReq dhbReq = JSONUtil.toObject(jo.getString("dhbLoginReq"), DHBLoginReq.class);
		Customer cus = JSONUtil.toObject(jo.getString("customer"), Customer.class);
		return dianHuaBangService.dhbCollectSec(dhbReq, cus);
	}
	
	@ApiOperation(value = "聚信立-提交申请表单获取回执信息，并完善客户联系信息")
	@RequestMapping(value = "/jxlSubmitForm", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object jxlSubmitForm(@RequestBody Customer cus, String reportKey) {
		// 完善客户信息
		boolean f = customerService.updateCus(cus);
		if (f) {
			return juxinliService.submitForm(cus.getId(), reportKey);
		}else {
			return renderError("更新客户信息失败", "510");
		}
	}
	
	@ApiOperation(value = "聚信立-提交数据采集请求")
	@RequestMapping(value = "/jxlCollect", method = { RequestMethod.POST }, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object jxlCollect(@RequestBody String jsonStr) {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		JXLCollectReq jxlReq = JSONUtil.toObject(jo.getString("jxlCollectReq"), JXLCollectReq.class);
		Customer cus = JSONUtil.toObject(jo.getString("customer"), Customer.class);
		String reportKey = jo.getString("reportKey");
		return juxinliService.jxlCollect(jxlReq, cus, reportKey);
	}
	
	@ApiOperation(value = "聚信立-提交重置密码请求")
	@RequestMapping(value = "/resetPassword", method = { RequestMethod.POST })
	@ResponseBody
	public Object resetPasswordSubmit(@RequestBody JXLResetPasswordReq req) {
		return juxinliService.resetPassword(req);
	}
	
	@ApiOperation(value = "电话邦-忘记密码")
	@RequestMapping(value = "/dhbforgetPwd", method = { RequestMethod.POST })
	@ResponseBody
	public Object dhbforgetPwd(@RequestBody String jsonStr) {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		String tel = jo.getString("tel");
		String userName = jo.getString("userName");
		String idCard = jo.getString("idCard");
		return dianHuaBangService.forgetPwd(tel, userName, idCard);
	}
	
	@ApiOperation(value = "电话邦-设置新的服务密码")
	@RequestMapping(value = "/dhbSetServicePwd", method = { RequestMethod.POST })
	@ResponseBody
	public Object dhbSetServicePwd(@RequestBody String jsonStr) {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		String tid = jo.getString("tid");
		String newPwd = jo.getString("newPwd");
		return dianHuaBangService.setServicePwd(tid, newPwd);
	}
	
	@ApiOperation(value = "电话邦-忘记服务密码短信校验")
	@RequestMapping(value = "/dhbForgotPwdSms", method = { RequestMethod.POST })
	@ResponseBody
	public Object dhbForgotPwdSms(@RequestBody String jsonStr) {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		String tid = jo.getString("tid");
		String smsCode = jo.getString("smsCode");
		return dianHuaBangService.forgotPwdSms(tid, smsCode);
	}
	
	@ApiOperation(value = "电话邦-忘记密码登录校验")
	@RequestMapping(value = "/dhbForgotPwdLogin", method = { RequestMethod.POST })
	@ResponseBody
	public Object dhbForgotPwdLogin(@RequestBody String jsonStr) {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		String tid = jo.getString("tid");
		String loginCode = jo.getString("loginCode");
		return dianHuaBangService.forgotPwdLogin(tid, loginCode);
	}
	
	@ApiOperation(value = "电话邦-刷新图形验证码")
	@RequestMapping(value = "/refreshGraphic/{sid}", method = { RequestMethod.GET})
	@ResponseBody
	public Object refreshGraphic(@PathVariable String sid) {
		return dianHuaBangService.refreshGraphic(sid);
	}
	
}
