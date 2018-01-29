package com.webill.core.service.impl;


import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.webill.app.util.HttpUtils;
import com.webill.app.util.MD5Util;
import com.webill.app.util.StringUtil;
import com.webill.core.model.dianhuabang.DianHuaBangErrorCode;
import com.webill.core.model.dianhuabang.LoginReq;
import com.webill.core.model.dianhuabang.UserContactInfo;
import com.webill.core.model.dianhuabang.UserInfo;
import com.webill.core.service.IDianHuaBangService;

public class DianHuaBangServiceImpl implements IDianHuaBangService{
	
	private static String token = "10003e002fe4a7e42953238c58559c2c";//c7e79aba139911ef7a7aee903607f6e8
//	private Long expires;//token 到期时间
	private static String appId = "5307bf14809605d38a3c1229e7d05044";
	private static String appSecret = "1bef32031cbb7478bc2f7dbc10e6c391d1043d040068d077f1f74c0f4968a388";
	private static String sid = "SIDa918cb57bee143e8adb824c6ec3353c5";
	private static String tid = "TID15172095388162997897";
	private static String tel = "17521308009";
	private static String userName = "刘跃";
	private static String idCard = "130630199203136516";
	private static String smsCode = "033713";
	private static String newPwd = "149446";
	private static String[] telList = {"", "", ""};
	
	 public static void main(String args[]) throws Exception
    {
//		  getToken();
//		  getSid(token, tel);
//        LoginReq loginReq = new LoginReq();
//        loginReq.setSid(sid);
//        loginReq.setFullName(userName);
//        loginReq.setIdCard(idCard);
//        loginReq.setPinPwd(newPwd);
//        loginReq.setTel(tel);
//        loginReq.setSmsCode("771097");
//        login(token, loginReq);
//        loginSec(token, sid, "097516");
//        getReport(token, sid);
		 
//		 forgotPwd(token, tel, userName, idCard);
//		 forgotPwdSec(token, tid, smsCode);
//		 forgotPwdContact(tid, telList);
//		 forgotPwdLogin(tid, smsCode);
//		 setServicePwd(tid, newPwd);
    }

	@Override
	public void queryReport() {
		
	}
	
	/*
	 * 获取token，用于之后的接口访问,由于token存在过期时间，所以后面判断返回code为4000的时候重新获取
	 * */
	public String getToken() {
		String url = "https://crs-api.dianhua.cn/token";//获取token地址
		
		Long timeStamp = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("appid", appId);
		reqMap.put("sign", MD5Util.MD5Encode(appId + appSecret + String.valueOf(timeStamp / 1000), "UTF-8"));
		reqMap.put("time", String.valueOf(timeStamp / 1000));
		try {
			String respStr = HttpUtils.httpGetRequest(url, reqMap);
			if(respStr != null) {
				JSONObject json = JSONObject.parseObject(respStr);
				int code = json.getIntValue("status");
				if(code != 0) {
					return DianHuaBangErrorCode.getMessage(code);
				}
				JSONObject data = (JSONObject) json.get("data");
				token = data.getString("token");
			} else {
				throw new RuntimeException("获取token失败");
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return token;
	}


	/*
	 * 获取会话标识sid 
	 * */
	public String getSid(String token, String tel) {
		String url = "https://crs-api.dianhua.cn/calls/flow?token=" + token;
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tel", tel);
//		reqMap.put("user_info", new UserInfo());//可传参数
//		reqMap.put("user_contact", new ArrayList<UserContactInfo>());//可传参数
		//TODO  根据前端提交数据，可以选择提交user_info对象和user_contact对象
		try {
			String respStr = HttpUtils.httpPostRequest(url, reqMap);
			if(respStr != null) {
				JSONObject json = JSONObject.parseObject(respStr);
				int code = json.getIntValue("status");
				if(code != 0) {
					return DianHuaBangErrorCode.getMessage(code);
				}
				JSONObject data = (JSONObject) json.get("data");
				String sid = data.getString("sid");//查询详单所需的id号
				int fullName = data.getIntValue("need_full_name");//是否需要在登录时候提交用户真实姓名：  1为需要    0为不需要
				int idCard = data.getIntValue("need_id_card");//是否需要在登录时候提交用户身份证号：  1为需要    0为不需要
				int pinPwd = data.getIntValue("need_pin_pwd");//是否需要在登录时候提交用户手机号服务密码：  1为需要    0为不需要
				String sms = data.getString("sms_duration");//是否需要在登录时输入短信验证码: 不为空即为需要
				String img = data.getString("captcha_image");//是否需要在登录时输入图形验证码 base64 码: 不为空即为需要
				return data.toString();
				
			} else {
				throw new RuntimeException("获取sid失败");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 登录
	 * */
	public String login(String token, LoginReq loginReq) {
		String url = "https://crs-api.dianhua.cn/calls/login?token=" + token;
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("sid", loginReq.getSid());//必要字段
//		reqMap.put("tel", loginReq.getTel());
		reqMap.put("tel", loginReq.getTel());//必要字段
		if(!StringUtil.isEmpty(loginReq.getFullName())) 
			reqMap.put("full_name", loginReq.getFullName());
		if(!StringUtil.isEmpty(loginReq.getIdCard())) 
			reqMap.put("id_card", loginReq.getIdCard());
		if(!StringUtil.isEmpty(loginReq.getPinPwd())) 
			reqMap.put("pin_pwd", loginReq.getPinPwd());
		if(!StringUtil.isEmpty(loginReq.getSmsCode())) 
			reqMap.put("sms_code", loginReq.getSmsCode());
		if(!StringUtil.isEmpty(loginReq.getCaptchaCode())) 
			reqMap.put("captcha_code", loginReq.getCaptchaCode());
		
		try {
			String respStr = HttpUtils.httpPostRequest(url, reqMap);
			if(respStr != null) {
				JSONObject json = JSONObject.parseObject(respStr);
				int code = json.getIntValue("status");
				if(code != 0) {
					return DianHuaBangErrorCode.getMessage(code);
				}
				JSONObject data = (JSONObject) json.get("data");
				String sms = data.getString("sms_duration");//是否需要在登录时输入短信验证码: 不为空即为需要
				String img = data.getString("captcha_image");//是否需要在登录时输入图形验证码 base64 码: 不为空即为需要
				String notes = data.getString("verify_notes");//短信提示(只针对于吉林电信 sms_duration 不为空): 不为空即为需要
				return data.toString();
				
			} else {
				throw new RuntimeException("登录请求失败");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 * 登录后二次验证	
	 * */
	public String loginSec(String token, String sid, String smsCode) {
		String url = "https://crs-api.dianhua.cn/calls/verify?token=" + token;
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("sid", sid);
		reqMap.put("sms_code", smsCode);
		
		try {
			String respStr = HttpUtils.httpPostRequest(url, reqMap);
			if(respStr != null) {
				JSONObject json = JSONObject.parseObject(respStr);
				int code = json.getIntValue("status");
				if(code != 0) {
					return DianHuaBangErrorCode.getMessage(code);
				}
				JSONObject data = (JSONObject) json.get("data");
				return data.toJSONString();
			} else {
				throw new RuntimeException("登录二次验证失败");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 拉取详单报告
	 * */
	public String getReport(String token, String sid) throws Exception {
		String url = "https://crs-api.dianhua.cn/calls/report?token=" + token + "&sid=" + sid;
			String respStr = HttpUtils.httpGetRequest(url);
			if(respStr != null) {
				JSONObject json = JSONObject.parseObject(respStr);
				int code = json.getIntValue("status");
				if(code != 0) {
					return DianHuaBangErrorCode.getMessage(code);
				}
				JSONObject data = (JSONObject) json.get("data");
				
				return data.toString();
			} else {
				throw new RuntimeException("获取详单失败");
			}
		
	}


	/*
	 * 忘记服务密码
	 * */
	public String forgotPwd(String token, String tel, String userName, String idCard) {
		String url = "https://crs-api.dianhua.cn/forget/pwd?token=" + token;
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tel", tel);
		reqMap.put("user_name", userName);
		reqMap.put("user_id", idCard);
		
		try {
			String respStr = HttpUtils.httpPostRequest(url, reqMap);
			if(respStr != null) {
				JSONObject json = JSONObject.parseObject(respStr);
				int code = json.getIntValue("status");
				if(code != 0) {
					return DianHuaBangErrorCode.getMessage(code);
				}
				JSONObject data = (JSONObject) json.get("data");
				String tid = data.getString("tid");//查询详单所需的id号
				int newPwd = data.getIntValue("need_new_pwd");//是否需要新服务密码：  1为需要    0为不需要
				int contacts = data.getIntValue("need_contacts");//是否需要联系人手机号：  1为需要    0为不需要
				String loginSms = data.getString("login_sms_duration");//登录短信验证码有效时长，如果有值，则需要继续进一步验证，需调用找回密码登录校验接口；如果为空，不需要调用
				String sms = data.getString("sms_duration");//是否需要在登录时输入短信验证码: 不为空即为需要
				String content = data.getString("content");//说明
				return data.toString();
			} else {
				throw new RuntimeException("请求失败");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * 忘记服务密码短信校验
	 * */
	public String forgotPwdSec(String token, String tid, String smsCode) {
		
		String url = "https://crs-api.dianhua.cn/forget/pwd?token=" + token;
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tid", tid);
		reqMap.put("sms_code", smsCode);
		
		try {
			String respStr = HttpUtils.httpPostRequest(url, reqMap);
			if(respStr != null) {
				JSONObject json = JSONObject.parseObject(respStr);
				int code = json.getIntValue("status");
				if(code != 0) {
					return DianHuaBangErrorCode.getMessage(code);
				}
				JSONObject data = (JSONObject) json.get("data");
				return data.toJSONString();
			} else {
				throw new RuntimeException("请求失败");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 * 忘记密码联系人通话记录校验
	 * */
	public String forgotPwdContact(String tid, String[] telList) {
		String url = "https://crs-api.dianhua.cn/forget/pwd?token=" + token;
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tid", tid);
		reqMap.put("tel_list ", telList);//联系人手机号（need_contacts 为 1 时调用）
		
		try {
			String respStr = HttpUtils.httpPostRequest(url, reqMap);
			if(respStr != null) {
				JSONObject json = JSONObject.parseObject(respStr);
				int code = json.getIntValue("status");
				if(code != 0) {
					return DianHuaBangErrorCode.getMessage(code);
				}
				JSONObject data = (JSONObject) json.get("data");
				return data.toJSONString();
			} else {
				throw new RuntimeException("获取token请求电话帮失败");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 * 设置新的服务密码
	 * */
	public String setServicePwd(String tid, String newPwd) {
		String url = "https://crs-api.dianhua.cn/forget/pwd?token=" + token;
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tid", tid);
		reqMap.put("new_pwd", newPwd);
		
		try {
			String respStr = HttpUtils.httpPostRequest(url, reqMap);
			if(respStr != null) {
				JSONObject json = JSONObject.parseObject(respStr);
				int code = json.getIntValue("status");
				if(code != 0) {
					return DianHuaBangErrorCode.getMessage(code);
				}
				JSONObject data = (JSONObject) json.get("data");
				return data.toJSONString();
			} else {
				throw new RuntimeException("获取token请求电话帮失败");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}


	/*
	 * 忘记密码登录校验
	 * */
	public String forgotPwdLogin(String tid, String loginCode) {
		String url = "https://crs-api.dianhua.cn/forget/pwd?token=" + token;
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tid", tid);
		reqMap.put("login_code", loginCode);
		
		try {
			String respStr = HttpUtils.httpPostRequest(url, reqMap);
			if(respStr != null) {
				JSONObject json = JSONObject.parseObject(respStr);
				int code = json.getIntValue("status");
				if(code != 0) {
					return DianHuaBangErrorCode.getMessage(code);
				}
				JSONObject data = (JSONObject) json.get("data");
				return data.toJSONString();
				
			} else {
				throw new RuntimeException("获取token请求电话帮失败");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
