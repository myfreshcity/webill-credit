package com.webill.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.webill.app.util.StringUtil;
import com.webill.core.Constant;
import com.webill.core.model.RedisKeyDto;
import com.webill.core.model.User;
import com.webill.core.service.IUserService;
import com.webill.core.service.RedisService;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "userAPI", description = "用户API", produces = MediaType.APPLICATION_JSON_VALUE)
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
     * @Title: getUserByid 
     * @Description: 根据用户ID获取用户信息
     * @author ZhangYadong
     * @date 2018年2月5日 下午2:57:17
     * @param id
     * @return
     * @return JsonResult
     */
    @RequestMapping(value = "/getUserById/{id}", method = { RequestMethod.GET }, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult getUserById(@PathVariable Integer id) {
    	User user = userService.selectById(id);
    	return renderSuccess(user);
	}
    
    /** 
	 * @Title: userRegister 
	 * @Description: 用户注册
	 * @author ZhangYadong
	 * @date 2018年1月17日 上午10:06:43
	 * @param jsonStr
	 * @return
	 * @return JsonResult
	 */
	@ApiOperation(value = "用户注册")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "注册成功！"),
			@ApiResponse(code = 300, message = "验证码已失效，请重新发送！"), @ApiResponse(code = 305, message = "该手机号已注册,请直接登录！"),
			@ApiResponse(code = 400, message = "验证码错误，请重试！"), @ApiResponse(code = 500, message = "注册失败！")
	})
    @RequestMapping(value = "/register", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult userRegister(@RequestBody String jsonStr) {
		User user = JSONObject.parseObject(jsonStr, User.class);
		
		// 获取手机号相关联的用户信息
		User userCheck = userService.checkMobileIsExist(user.getMobileNo());
		if(userCheck==null){
			// 获取系统发送的验证码
			RedisKeyDto redisWhere = new RedisKeyDto();
			redisWhere.setKeys(user.getMobileNo());
			RedisKeyDto redisKeyDto =  redisService.redisGet(redisWhere);
			if(redisKeyDto!=null){
				String verifyCode = redisKeyDto.getValues();
				if(user.getInCode().equals(verifyCode)){
					User resultUser = userService.saveRegister(user);
					if(resultUser!=null){
						return renderSuccess("注册成功！", "200", resultUser);
					}else{
						return renderError("注册失败！", "500");
					}
				}else{
					// 验证码错误
					return renderError("验证码错误,请重试！", "400");
				}
			}else{
				// 验证码已失效
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
	@ApiResponses(value = { @ApiResponse(code = 200, message = "登录成功！"),
			@ApiResponse(code = 300, message = "验证码错误，请重试！"), @ApiResponse(code = 303, message = "手机号不能为空！"),
			@ApiResponse(code = 400, message = "该手机号已关联微信号！"), @ApiResponse(code = 500, message = "登录失败！")
	})
    @RequestMapping(value = "/login", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult userLogin(@RequestBody String jsonStr) {
		User user = JSONObject.parseObject(jsonStr, User.class);
		if(user!=null){
			if(StringUtil.isNotEmpty(user.getMobileNo())){
				// 获取库中用户信息
				User mobUser = userService.checkMobileIsExist(user.getMobileNo());
				if(mobUser!= null){
					if("quick".equals(user.getCheckFlag())){
						// 验证码快捷登录
						Integer result = userService.userLogin(user);
						
						if(Constant.LOGIN_SUCCESS.intValue()==result.intValue()){
			            	String token = jwtUtil.generToken(user.getMobileNo(),"mmh","mobileNo");
			            	mobUser.setJwtToken(token);
							return renderSuccess("登录成功！", "200", mobUser);
						}else if(Constant.LOGIN_VERIFY_CODE_ERROR.intValue()==result.intValue()){
							return renderError("验证码错误，请重试！", "300");
						}else if(Constant.LOGIN_VERIFY_CODE_INVALID.intValue()==result.intValue()){
							return renderError("验证码已失效，请重新发送验证码！", "305");
						}else{
							return renderError("绑定手机号失败，请重试！", "500");
						}
					}else if("pwd".equals(user.getCheckFlag())){
						// 通过密码登录
						Integer result = userService.userLoginByPwd(user);
						
						if(Constant.LOGIN_SUCCESS.intValue()==result.intValue()){
							User dbUser = userService.checkMobileIsExist(user.getMobileNo());
							String token = jwtUtil.generToken(user.getMobileNo(),"mmh","mobileNo");
							dbUser.setJwtToken(token);
							return renderSuccess("登录成功！", "200", dbUser);
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
			return renderError("登录失败，请重试！", "500");
		}
    }
	
	@ApiOperation(value = "四要素认证")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "修改身份证成功！"),
			@ApiResponse(code = 500, message = "修改身份证失败！") })
	@RequestMapping(value = "/fourElementAuth", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult fourElementAuth(@RequestBody User user) throws Exception {
		// TODO 四要素认证
		String apiAddress = "http://v.apistore.cn/api/bank/v4?" + "key=" + constPro.FOUR_ELEMENT_AUTH_KEY
				+ "&bankcard=" + user.getBankCardNo()+ "&cardNo=" + user.getIdNo() 
				+ "&realName="	+ user.getRealName() + "&Mobile=" + user.getBankMobileNo() + "&information=1";
		String response = new RestTemplate().getForObject(apiAddress, String.class);
		JSONObject resObj = JSONObject.parseObject(response);
		// 返回状态码
		String errorCode = resObj.getString("error_code");
		
		if("0".equals(errorCode)){
			// TODO 认证成功，把相关数据插入数据库
			User userAuth = new User();
			userAuth.setId(user.getId());
			userAuth.setBankMobileNo(user.getBankMobileNo());
			userAuth.setBankCardNo(user.getBankCardNo());
			userAuth.setIdNo(user.getIdNo());
			userAuth.setRealName(user.getRealName());
			// 是否实名认证：0-否 1-是
			userAuth.setIsVerified(1);
			userService.updateSelectiveById(userAuth);
			User retUser = userService.selectById(user.getId());
			return renderSuccess(retUser);
		} else{
			// TODO 认证失败，把错误信息返回
			// 返回信息说明
			String reason = resObj.getString("reason");
			return renderError(reason, errorCode);
		}
	}
	
	/**
	 * @Title: updatePassword
	 * @Description: 修改密码,成功：返回用户信息
	 * @author: WangLongFei
	 * @date: 2018年2月1日 上午10:50:39
	 * @param jsonStr
	 * @return
	 * @return: JsonResult
	 */
	@ApiOperation(value = "修改密码,成功：返回用户信息")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "重置登录密码成功！"),
		@ApiResponse(code = 500, message = "修改密码失败，请重试！"), @ApiResponse(code = 400, message = "密码不能为空！"),
		@ApiResponse(code = 405, message = "验证码错误，请重试！"), @ApiResponse(code = 406, message = "验证码已失效，请重新发送验证码！"),
		@ApiResponse(code = 305, message = "该手机号还未注册，请先注册！"), @ApiResponse(code = 300, message = "手机号不能为空！") })
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
	public JsonResult updatePassword(@RequestBody String jsonStr) {
		User user = JSONObject.parseObject(jsonStr, User.class);
		boolean f = false;
		if (StringUtil.isNotEmpty(user.getMobileNo())) {
			// 获取库中用户信息
			User dbUser = userService.checkMobileIsExist(user.getMobileNo());
			// 判断用户是否存在
			if (dbUser != null) {
				// 获取系统发送的验证码
				RedisKeyDto redisWhere = new RedisKeyDto();
				redisWhere.setKeys(user.getMobileNo());
				RedisKeyDto redisKeyDto = redisService.redisGet(redisWhere);
				if (redisKeyDto != null) {
					String verifyCode = redisKeyDto.getValues();
					if (user.getInCode().equals(verifyCode)) {
						// 验证码正确
						user.setId(dbUser.getId());
						if (StringUtil.isNotEmpty(user.getPassword())) {
							// 修改密码
							f = userService.updateSelectiveById(user);
							if (f) {
								return renderSuccess("重置登录密码成功！", "200", dbUser);
							} else {
								return renderError("修改密码失败，请重试！", "500");
							}
						} else {
							return renderError("密码不能为空！", "400");
						}
					} else {
						return renderError("验证码错误，请重试！", "405");
					}
				} else {
					return renderError("验证码已失效，请重新发送验证码！", "406");
				}
			} else {
				return renderError("该手机号还未注册，请先注册！", "305");
			}
		} else {
			return renderError("手机号不能为空！", "300");
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
	@ApiResponses(value = { @ApiResponse(code = 200, message = "密码正确！"), @ApiResponse(code = 500, message = "密码错误！") })
	@RequestMapping(value = "/checkPwd", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult checkPwd(@ApiParam(value = "修改手机号时，验证密码") @RequestBody User user) throws Exception {
		User dbUser = userService.selectById(user.getId());
		if(user.getPassword().equals(dbUser.getPassword())){
			return renderSuccess("密码正确！", "200");
		}else{
			return renderError("密码错误！", "500");
		}
	}
	
	/**  
	 * @Title: checkToken  
	 * @Description: 检验token
	 * @author: ZhangYadong
	 * @date: 2018年3月22日
	 * @param jsonStr
	 * @return Object
	 */ 
	@RequestMapping(value = "/checkToken", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object checkToken(@RequestBody String jsonStr) {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		String token = jo.getString("token");
		JSONObject resObj = new JSONObject();

		if (StringUtil.isEmpty(token)) {
			resObj.put("token", token);
			resObj.put("result", 0);
			resObj.put("message", "缺少token，无法验证");
			return resObj;
		}
		
		Claims cm = jwtUtil.verifyToken(token);
		if (cm != null) {
			token = jwtUtil.updateTokenBase64Code(cm);
			resObj.put("token", token);
			resObj.put("result", 2);
			resObj.put("message", "生成新的token");
			return resObj;
		} else {
			resObj.put("token", token);
			resObj.put("result", 1);
			resObj.put("message", "token失效");
			return resObj;
		}
	}

	
}
