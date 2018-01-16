package com.webill.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.webill.app.util.MD5;
import com.webill.app.util.StringUtil;
import com.webill.core.Constant;
import com.webill.core.model.RedisKeyDto;
import com.webill.core.model.User;
import com.webill.core.service.IUserService;
import com.webill.core.service.RedisService;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by david on 16/10/9.
 */
@Api(value = "用户API")
@Controller
@RequestMapping("/api/user")
public class UserApiController extends BaseController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserService userService;
    
	@Autowired
	private RedisService redisService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    
	/** 
	 * @Title: userRegister 
	 * @Description: 用户注册
	 * @author: WangLongFei
	 * @date: 2017年11月23日 上午10:05:55 
	 * @param jsonStr
	 * @return
	 * @return: JsonResult
	 */
	@ApiOperation(value = "用户注册")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "注册成功！"),
			@ApiResponse(code = 300, message = "验证码已失效，请重新发送！"),
			@ApiResponse(code = 305, message = "该手机号已注册,请直接登录！"),
			@ApiResponse(code = 400, message = "验证码错误，请重试！"),
			@ApiResponse(code = 500, message = "注册失败！")
	})
    @RequestMapping(value = "/userRegister", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult userRegister(@RequestBody String jsonStr) {
		User user = JSONObject.parseObject(jsonStr, User.class);
		//获取手机号相关联的用户信息
		User userCheck = userService.checkMobileIsExist(user.getMobile());
		if(userCheck==null){
			//获取系统发送的验证码
			RedisKeyDto redisWhere = new RedisKeyDto();
			redisWhere.setKeys(user.getMobile());
			RedisKeyDto redisKeyDto =  redisService.redisGet(redisWhere);
			if(redisKeyDto!=null){
				String verifyCode = redisKeyDto.getValues();
				if(user.getInCode().equals(verifyCode)){
					User resultUser = userService.saveRegister(user);
					if(resultUser!=null){
						return renderSuccess("注册成功！", "200",resultUser);
					}else{
						return renderError("注册失败！", "500");
					}
				}else{
					//验证码错误
					return renderError("验证码错误,请重试！", "400");
				}
			}else{
				//验证码已失效
				return renderError("验证码已失效，请重新发送！", "300");
			}
		}else{
			return renderError("该手机号已注册,请直接登录！", "305");
		}
    }

	/**   
	 * @Title: userLogin   
	 * @Description: 用户登录  
	 * @author: WangLongFei  
	 * @date: 2017年10月26日 下午5:34:26   
	 * @param user
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "用户登录")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "登录成功！"),
			@ApiResponse(code = 300, message = "验证码错误，请重试！"),
			@ApiResponse(code = 303, message = "手机号不能为空！"),
			@ApiResponse(code = 400, message = "该手机号已关联微信号！"),
	@ApiResponse(code = 500, message = "登录失败！")
	})
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult userLogin(@RequestBody String jsonStr) {
		User user = JSONObject.parseObject(jsonStr, User.class);
		user.settStatus(Constant.NORMAL_STATUS);
		if(user!=null){
			if(StringUtil.isNotEmpty(user.getMobile())){
				//获取库中用户信息
				User mobUser = userService.checkMobileIsExist(user.getMobile());
				if(mobUser!= null){
					if("quick".equals(user.getCheckFlag())){
						user.setOpenId(user.getOpenId());;
						//验证码快捷登录
						Integer result = userService.userLogin(user);
						
						if(Constant.LOGIN_SUCCESS.intValue()==result.intValue()){
							User dbUser = userService.checkMobileIsExist(user.getMobile());
							return renderSuccess("登录成功！", "200",mobUser);
						}else if(Constant.LOGIN_VERIFY_CODE_ERROR.intValue()==result.intValue()){
							return renderError("验证码错误，请重试！", "300");
						}else if(Constant.LOGIN_VERIFY_CODE_INVALID.intValue()==result.intValue()){
							return renderError("验证码已失效，请重新发送验证码！", "305");
						}else{
							return renderError("绑定手机号失败，请重试！", "500");
						}
					}else if("pwd".equals(user.getCheckFlag())){
						mobUser.setOpenId(user.getOpenId());
						//通过密码登录
						Integer result = userService.userLoginByPwd(mobUser);
						
						if(Constant.LOGIN_SUCCESS.intValue()==result.intValue()){
							User dbUser = userService.checkMobileIsExist(user.getMobile());
							return renderSuccess("登录成功！", "200",dbUser);
						}else if(Constant.LOGIN_PWD_ERROR.intValue()==result.intValue()){
							return renderError("密码错误，请重试！", "300");
						}else{
							return renderError("该账号还未注册，请先注册！", "305");
						}
					}else{
						return renderError("登录失败，请重试！", "500");
					}
				}else{
					return renderError("该账号还未注册，请先注册！", "305");
				}
			}else{
				return renderError("手机号不能为空！", "303");
			}
		}else{
			return renderError("绑定手机号失败，请重试！", "500");
		}
    }
	
	/** 
	 * @Title: userLogout 
	 * @Description: 用户注销
	 * @author: WangLongFei
	 * @date: 2017年11月22日 下午2:46:39 
	 * @param jsonStr
	 * @return
	 * @return: JsonResult
	 */
	@ApiOperation(value = "用户注销")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "注销成功！"),@ApiResponse(code = 500, message = "注销失败，请重试！"),})
    @RequestMapping(value = "/userLogout", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult userLogout(@RequestBody String jsonStr) {
		User user = JSONObject.parseObject(jsonStr, User.class);
		if(user!=null){
			//获取当前账号信息
			User mobUser = userService.checkMobileIsExist(user.getMobile());
			if(mobUser!=null){
				boolean f = userService.userLogon(mobUser);
				if(f){
					User defaultUser = new User();
					defaultUser.setId(-1);
					return renderSuccess("注销成功！", "200",defaultUser);
				}else{
					return renderError("注销失败，请重试！", "500");
				}
			}else{
				return renderError("注销失败，请重试！", "500");
			}
			
		}else{
			return renderError("注销失败，请重试！", "500");
		}
    }
	
	/** 
	 * @Title: updateIdCard 
	 * @Description: 身份验证
	 * @author: WangLongFei
	 * @date: 2017年11月29日 下午1:42:05 
	 * @return
	 * @throws Exception
	 * @return: Object
	 */
	@ApiOperation(value = "身份验证")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "修改身份证成功！"),@ApiResponse(code = 500, message = "修改身份证失败！")})
	@RequestMapping(value = "/updateIdCard", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object updateIdCard(@ApiParam(value = "身份验证")@RequestBody User user) throws Exception {
		//原始数据
		User dbUser = userService.selectById(user.getId());
		if(StringUtil.isEmpty(dbUser.getCardCode())&&StringUtil.isEmpty(dbUser.getcName())){
			user.setCName(user.getcName());
			boolean f = userService.updateSelectiveById(user);
			User newUser = userService.selectById(user.getId());
			if(f){
				return renderSuccess("修改身份信息成功！", "200",newUser);
			}else{
				return renderError("修改身份信息失败！", "500");
			}
		}else{
			return renderError("已身份认证！", "500");
		}
		
	}
	
	/** 
	 * @Title: checkPwd 
	 * @Description: 修改手机号时，验证密码
	 * @author: WangLongFei
	 * @date: 2017年11月29日 下午1:42:05 
	 * @return
	 * @throws Exception
	 * @return: Object
	 */
	@ApiOperation(value = "修改手机号时，验证密码")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "密码正确！"),@ApiResponse(code = 500, message = "密码错误！")})
	@RequestMapping(value = "/checkPwd", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult checkPwd(@ApiParam(value = "修改手机号时，验证密码")@RequestBody User user) throws Exception {
		User dbUser = userService.selectById(user.getId());
		if(user.getPassword().equals(dbUser.getPassword())){
			return renderSuccess("密码正确！", "200");
		}else{
			return renderError("密码错误！", "500");
		}
	}
	/**
	 * @Title: updateMobile 
	 * @Description: 修改手机号  
	 * @author: WangLongFei
	 * @date: 2017年11月24日 上午10:58:43 
	 * @param jsonStr
	 * @return
	 * @return: JsonResult
	 */
	@ApiOperation(value = "修改手机号")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "验证成功！"),
			@ApiResponse(code = 305, message = "验证码已失效，请重新发送验证码！"),
			@ApiResponse(code = 500, message = "验证码错误，请重试！")
			})
    @RequestMapping(value = "/updateMobile", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult updateMobile(@RequestBody String jsonStr) {
		User user = JSONObject.parseObject(jsonStr, User.class);
		//获取系统发送的验证码
		RedisKeyDto redisWhere = new RedisKeyDto();
		redisWhere.setKeys(user.getMobile());
		RedisKeyDto redisKeyDto =  redisService.redisGet(redisWhere);
		if(redisKeyDto!=null){
			String verifyCode = redisKeyDto.getValues();
			if(user.getInCode().equals(verifyCode)){
				//验证码正确
				boolean f = userService.updateSelectiveById(user);
				if(f){
					return renderSuccess("修改手机号成功！","200");
				}else{
					return renderSuccess("修改手机号失败！","500");
				}
			}else{
				return renderError("验证码错误，请重试！","400");
			}
		}else{
			return renderError("验证码已失效，请重新发送验证码！", "305");
		}
	}
	/**
	 * @Title: checkInCode 
	 * @Description: 核对验证码  
	 * @author: WangLongFei
	 * @date: 2017年11月24日 上午10:58:43 
	 * @param jsonStr
	 * @return
	 * @return: JsonResult
	 */
	@ApiOperation(value = "核对验证码")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "验证成功！"),
			@ApiResponse(code = 305, message = "验证码已失效，请重新发送验证码！"),
			@ApiResponse(code = 500, message = "验证码错误，请重试！")
			})
    @RequestMapping(value = "/checkInCode", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult checkInCode(@RequestBody String jsonStr) {
		User user = JSONObject.parseObject(jsonStr, User.class);
		//获取系统发送的验证码
		RedisKeyDto redisWhere = new RedisKeyDto();
		redisWhere.setKeys(user.getMobile());
		RedisKeyDto redisKeyDto =  redisService.redisGet(redisWhere);
		if(redisKeyDto!=null){
			String verifyCode = redisKeyDto.getValues();
			if(user.getInCode().equals(verifyCode)){
				//验证码正确
				return renderSuccess("验证成功！","200");
			}else{
				return renderError("验证码错误，请重试！","500");
			}
		}else{
			return renderError("验证码已失效，请重新发送验证码！", "305");
		}
	}
	
	/**   
	 * @Title: updatePassword   
	 * @Description: 修改密码,成功：返回用户信息
	 * @author: WangLongFei  
	 * @date: 2017年10月26日 下午5:34:26   
	 * @param user
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "修改密码,成功：返回用户信息")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "修改成功！"),
			@ApiResponse(code = 300, message = "手机号不能为空！"),
			@ApiResponse(code = 305, message = "该账号还未注册，请先注册！"),
			@ApiResponse(code = 400, message = "密码不能为空！"),
			@ApiResponse(code = 500, message = "修改密码失败，请重试！")
			})
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult updatePassword(@RequestBody String jsonStr) {
		User user = JSONObject.parseObject(jsonStr, User.class);
		boolean f = false;
		if(StringUtil.isNotEmpty(user.getMobile())){
			//获取库中用户信息
			User dbUser = userService.checkMobileIsExist(user.getMobile());
			if(dbUser!=null){
					user.setId(dbUser.getId());
					if(StringUtil.isNotEmpty(user.getPassword())){
						//修改密码
						f = userService.updateSelectiveById(user);
						
						if(f){
							return renderSuccess("重置登录密码成功！","200",dbUser);
						}else{
							return renderError("修改密码失败，请重试！", "500");
						}
					}else{
						return renderError("密码不能为空！", "400");
					}
			}else{
				return renderError("该账号还未注册，请先注册！", "305");
			}
			
		}else{
			return renderError("手机号不能为空！", "300");
		}
    }
	
}
