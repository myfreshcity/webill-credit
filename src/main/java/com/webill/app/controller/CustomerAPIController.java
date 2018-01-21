package com.webill.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.model.Customer;
import com.webill.core.model.User;
import com.webill.core.model.juxinli.JXLCollectReq;
import com.webill.core.model.juxinli.JXLSubmitFormReq;
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
		Map<String, Object> map = new HashMap<>();
		map.put("user_id", cus.getUserId());
		map.put("real_name", cus.getRealName());
		map.put("id_no", cus.getIdNo());
		map.put("mobile_no", cus.getMobileNo());
		List<Customer> cusList = customerService.selectByMap(map);
		
		if (cusList != null && cusList.size() > 0) {
			return renderSuccess(cusList.get(0));
		}else {
			boolean f = customerService.insertSelective(cus);
			if (f) {
				return renderSuccess("客户基本信息入库成功！", "210");
			}else {
				return renderError("客户基本信息入库失败！", "510");
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "客户列表")
	@RequestMapping(value = "/list", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String customerList(HttpServletRequest request, Customer cus) {
	    Page page = this.getPage(request, Integer.MAX_VALUE);
        page = customerService.getCusList(page, cus);
        return JSONUtil.toJSONString(page);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "客户列表")
	@RequestMapping(value = "/submitForm1", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public String submitForm1(HttpServletRequest request, Customer cus) {
		Page page = this.getPage(request, Integer.MAX_VALUE);
		page = customerService.getCusList(page, cus);
		return JSONUtil.toJSONString(page);
	}
	
	@ApiOperation(value = "提交申请表单获取回执信息")
	@RequestMapping(value = "/submitForm", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object submitForm(@RequestBody JXLSubmitFormReq req) {
		/*Map<String, String> code = codeService.getCodeTable("CONV_JXL");
		req.convertContact(code);*/
		return juxinliService.submitForm(req);
	}
	
	@ApiOperation(value = "提交数据采集请求")
	@RequestMapping(value = "/collect", method = { RequestMethod.POST }, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object collect(@RequestBody JXLCollectReq req) {

		return juxinliService.collect(req);

	}
	
	/*@RequestMapping(value = "/getReport")
	public ModelAndView getReport(@RequestParam(value = "applySn", required = false) String applySn,
			@RequestParam(value = "token", required = false) String token,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "idCard", required = false) String idCard,
			@RequestParam(value = "mobile", required = false) String mobile) {

		String result = juxinliService.getReport(token, applySn, name, idCard, mobile);
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");// 把JDK变成1.6

		JSONObject json = JSONObject.fromObject(result);

		JSONArray jsonArray = json.getJSONArray("application_check");
		JSONObject applicationCheck = new JSONObject();
		
		for (int index = 0; index < jsonArray.size(); index++) {

			JSONObject appJson = jsonArray.getJSONObject(index);
			String appPoint = appJson.getString("app_point");
			JSONObject checkPoints = appJson.getJSONObject("check_points");

			if (appPoint.equals("contact")) {
				JSONArray array = null;
				if (applicationCheck.containsKey(appPoint))
					array = applicationCheck.getJSONArray(appPoint);
				else {
					array = new JSONArray();
					applicationCheck.put(appPoint, array);
				}
				array.add(checkPoints);
				applicationCheck.put(appPoint, array);
			} else {
				applicationCheck.put(appPoint, checkPoints);
			}
		}

		json.put("application_check_map", applicationCheck);

		// 联系人区域排序
		JSONArray contactArray = json.getJSONArray("contact_region");

		Collections.sort(contactArray, new Comparator<JSONObject>() {

			public int compare(JSONObject o1, JSONObject o2) {

				int inTime1 = o1.getInt("region_call_in_cnt");
				int inTime2 = o2.getInt("region_call_in_cnt");
				int outTime1 = o1.getInt("region_call_out_cnt");
				int outTime2 = o2.getInt("region_call_out_cnt");

				if (outTime1 != outTime2)
					return outTime1 < outTime2 ? 1 : -1;
				else
					return inTime1 < inTime2 ? 1 : -1;
			}

		});
		// 运营商数据排序
		JSONArray spArray = json.getJSONArray("contact_list");

		System.out.println("<<<<<<<<<<<<contact_list>>>>>>>>>>>>");
		Collections.sort(spArray, new Comparator<JSONObject>() {

			public int compare(JSONObject o1, JSONObject o2) {

				int callCnt1 = o1.getInt("call_cnt");
				int callCnt2 = o2.getInt("call_cnt");

				// if(callCnt1 == callCnt2){
				// return 0;
				// }else{
				// return callCnt1 < callCnt2?1:-1;
				// }
				return callCnt1 < callCnt2 ? 1 : callCnt1 == callCnt2 ? 0 : -1;
			}

		});

		json.put("contact_region", contactArray);
		json.put("contact_list", spArray);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("antifraud/juxinli/report");

		mv.addObject("result", json);
		return mv;
	}
	
	@RequestMapping(value = "resetPassword", method = { RequestMethod.POST })
	@ResponseBody
	public Object resetPasswordSubmit(@RequestBody JXLResetPasswordReq req, Model model) {
		return juxinliService.resetPassword(req);
	}

	@RequestMapping(value = "resetPassword", method = { RequestMethod.GET })
	public String resetPassword(@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "website", required = false) String website,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "idCardNum", required = false) String idCardNum, Model model)
			throws UnsupportedEncodingException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("token", token);
		map.put("mobile", mobile);
		map.put("website", website);
		// map.put("name",new String(name.getBytes("ISO8859-1"), "UTF-8"));
		map.put("name", name);
		map.put("idCardNum", idCardNum);
		model.addAttribute("data", map);
		return viewName("resetPassword");
	}*/
	
}
