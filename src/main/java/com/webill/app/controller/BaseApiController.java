package com.webill.app.controller;


import com.webill.app.util.WeixinSupport;
import com.webill.core.model.User;
import com.webill.core.service.IUserService;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    private IUserService userService;
    
    
    @Autowired
    WeixinSupport weixinSupport;
    
	/*@ApiOperation(value = "推荐用户")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "推荐成功!"),@ApiResponse(code = 500, message = "推荐失败！")})
    @RequestMapping(value = "/user/recommend", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult recommendUser(@ApiParam(required = true, value = "用户") @RequestBody User user){
		User ruser = userService.saveRecommend(user.getId().toString());
		if(ruser!=null){
			logger.info("推荐成功！");
			return renderSuccess("推荐成功", "200", ruser);
		}else{
			return renderError();
		}
    }*/
}
