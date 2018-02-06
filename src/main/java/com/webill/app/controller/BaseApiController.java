package com.webill.app.controller;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webill.app.util.WeixinSupport;
import com.webill.core.model.User;
import com.webill.core.service.ICreditService;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import reactor.core.Reactor;


@Api(value = "基础API")
@Controller
@RequestMapping("/api/base")
public class BaseApiController extends BaseController {

    private static Log logger = LogFactory.getLog(BaseApiController.class);

    @Autowired
    @Qualifier("rootReactor")
    private Reactor r;

    @Autowired
	private ICreditService creditService;
    
    
    @Autowired
    WeixinSupport weixinSupport;
    
	@RequestMapping(value = "/test", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
	public JsonResult recommendUser(@ApiParam(required = true, value = "用户") @RequestBody User user) throws Throwable {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "李小雨");// 用户姓名
		map.put("id_number", "430725199110015753");// 用户身份证
		map.put("mobile", "13601658182");// 用户手机号
		Map<String, String> rspMap = creditService.qryAndSaveCreditInfo(map, (long) 5, 5000L);
		return renderSuccess(rspMap);
		// new
		// CreditService().qryReportRst(0L,(String)rspMap.get("reportId"),5,5000L);
	}
}
