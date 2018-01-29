package com.webill.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.model.Customer;
import com.webill.core.model.User;
import com.webill.core.model.juxinli.JXLCollectReq;
import com.webill.core.model.juxinli.JXLResetPasswordReq;
import com.webill.core.model.juxinli.JXLSubmitFormReq;
import com.webill.core.model.juxinli.Report;
import com.webill.core.service.ICustomerService;
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
	
	@ApiOperation(value = "完善客户联系信息")
	@RequestMapping(value = "/improveCusInfo", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	@Deprecated
	public JsonResult improveCusInfo(@RequestBody Customer cus){
		boolean f = customerService.updateCus(cus);
		if (f) {
			Customer cusDetail = customerService.getCusByUserIdCusId(cus);
			return renderSuccess(cusDetail);
		}else {
			return renderError("更新客户信息失败", "510");
		}
	}
	
	/*************************************************聚信立接口************************************************/
	
	@ApiOperation(value = "提交申请表单获取回执信息，并完善客户联系信息")
	@RequestMapping(value = "/submitForm", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object submitForm(@RequestBody Customer cus) {
		// 完善客户信息
		boolean f = customerService.updateCus(cus);
		if (f) {
			// 客户信息转聚信立表单提交数据
			JXLSubmitFormReq req = customerService.cusToJXLSubmitFormReq(cus);
			// 提交申请表单
			return juxinliService.submitForm(req, cus.getId());
		}else {
			return renderError("更新客户信息失败", "510");
		}
	}
	
	@ApiOperation(value = "提交数据采集请求")
	@RequestMapping(value = "/collect", method = { RequestMethod.POST }, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object collect(@RequestBody JXLCollectReq req) {
		return juxinliService.collect(req);
	}
	
	@ApiOperation(value = "通过token获取报告数据，申请表单提交时获取的token")
	@RequestMapping(value = "/selectReport")
	@ResponseBody
	public Object getReport(@RequestBody Report report) {
		String reportRes = juxinliService.getReport(report.getReportKey(), report.getName(), report.getIdCard(), report.getMobile());
		return renderSuccess(JSONObject.parseObject(reportRes));
	}
	
	@ApiOperation(value = "获取重置密码响应信息")
	@RequestMapping(value = "/resetPasswordResp", method = { RequestMethod.POST })
	@ResponseBody
	public Object resetPasswordResp(@RequestBody JXLResetPasswordReq req) {
		return juxinliService.resetPasswordResp(req);
	}
	
	@ApiOperation(value = "提交重置密码请求")
	@RequestMapping(value = "/resetPassword", method = { RequestMethod.POST })
	@ResponseBody
	public Object resetPasswordSubmit(@RequestBody JXLResetPasswordReq req) {
		return juxinliService.resetPassword(req);
	}

}
