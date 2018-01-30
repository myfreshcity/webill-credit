package com.webill.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.webill.app.util.StringUtil;
import com.webill.core.Constant;
import com.webill.core.model.User;
import com.webill.core.service.IUserContactService;
import com.webill.core.service.IUserService;
import com.webill.core.service.IVerificationCodeService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(value = "手机验证码API")
@Controller
@RequestMapping("/api/verifyCode")
public class VerifyCodeApiController extends BaseController {

	private static Log logger = LogFactory.getLog(VerifyCodeApiController.class);

	@Autowired
	private IUserContactService userContactService;
	
	@Autowired
	private IVerificationCodeService verificationCodeService;
	
    @Autowired
    private IUserService userService;
	
	/**   
	 * @Title: sendVerifyCode   
	 * @Description: 发送验证码（默认验证手机号唯一性，checkOnly:true,不验证时：checkOnly:false）
	 * @author: WangLongFei  
	 * @date: 2017年7月24日 下午4:06:29   
	 * @param mobile
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "发送验证码（默认验证手机号唯一性）")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "发送验证码成功！"),@ApiResponse(code = 500, message = "手机号不能为空！")})
	@RequestMapping(value = "/sendVerifyCode", method = { RequestMethod.POST },produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public JsonResult sendVerifyCode(@ApiParam(required = true, value = "手机号")@RequestBody String jsonStr) {
		User user = JSONObject.parseObject(jsonStr, User.class);
		String checkFlag = user.getCheckFlag();
		if(StringUtil.isNotEmpty(user.getMobileNo())){
			User dbUser = userService.checkMobileIsExist(user.getMobileNo());
			if(dbUser!=null){
				//手机号已存在
				if(Constant.SEND_MSG_CHECK_FLAG_REGISTER.equals(checkFlag)){
					//注册
					return renderError("该账号已存在，请直接登录！", "400");
				}else if(Constant.SEND_MSG_CHECK_FLAG_UPDATEMOB.equals(checkFlag)){
					//修改手机号
					return renderError("请使用未绑定的手机号！", "402");
				}else {
					//修改密码或者快速登录
					Integer result = verificationCodeService.sendVerficationCode(user.getMobileNo());
					if(result.intValue() == Constant.STATUS_SUCCESS){
						return renderSuccess("发送验证码成功！", "200");
					}else{
						return renderError("发送验证码失败！", "300");
					}
				}
			}else{
				//手机号不存在
				if(Constant.SEND_MSG_CHECK_FLAG_REGISTER.equals(checkFlag)||Constant.SEND_MSG_CHECK_FLAG_UPDATEMOB.equals(checkFlag)){
					//注册或修改手机号
					Integer result = verificationCodeService.sendVerficationCode(user.getMobileNo());
					if(result.intValue() == Constant.STATUS_SUCCESS){
						return renderSuccess("发送验证码成功！", "200");
					}else{
						return renderError("发送验证码失败！", "300");
					}
				}else{
					//修改密码或者快速登录
					return renderError("此账户不存在！", "400");
				}
			}			
		}else{
			return renderError("手机号不能为空！", "500");
		}
	}
}
