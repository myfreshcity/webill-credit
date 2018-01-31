package com.webill.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webill.core.model.Customer;
import com.webill.core.service.IDianHuaBangService;
import com.webill.framework.common.JsonResult;

import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: DianHuaBangController 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月30日 上午10:30:18 
 */
@Controller
@RequestMapping(value = "/dhbReport")
public class DianHuaBangController {
	@Autowired
	private IDianHuaBangService dianHuaBangService;
	
	@ApiOperation(value = "完善客户联系信息")
	@RequestMapping(value = "/test", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult test(){
		String token = dianHuaBangService.getToken();
		System.out.println(token);
		return null;
	}
}
