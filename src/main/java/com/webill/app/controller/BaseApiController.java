package com.webill.app.controller;


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

import com.webill.core.model.User;
import com.webill.core.service.ITongDunService;
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
	private ITongDunService tongDunService;

    
	@RequestMapping(value = "/test", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
	public JsonResult recommendUser(@ApiParam(required = true, value = "用户") @RequestBody User user) throws Throwable {
		String reportKey = "1";
		Object obj = tongDunService.saveSubmitQuery(reportKey, user.getId().toString());
		return renderSuccess(obj);
	}
}
