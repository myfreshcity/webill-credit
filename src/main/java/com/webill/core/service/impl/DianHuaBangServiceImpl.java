package com.webill.core.service.impl;


import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.webill.app.SystemProperty;
import com.webill.app.util.HttpUtils;
import com.webill.app.util.MD5Util;
import com.webill.app.util.StringUtil;
import com.webill.core.model.dianhuabang.DianHuaBangErrorCode;
import com.webill.core.model.juxinli.JXLSubmitFormReq;
import com.webill.core.model.juxinli.Report;
import com.webill.core.model.Customer;
import com.webill.core.model.dianhuabang.DHBGetLoginReq;
import com.webill.core.model.dianhuabang.DHBGetLoginResp;
import com.webill.core.model.dianhuabang.DHBLoginReq;
import com.webill.core.service.ICustomerService;
import com.webill.core.service.IDianHuaBangService;
import com.webill.core.service.IReportMongoDBService;

/**
 * @ClassName: DianHuaBangServiceImpl
 * @Description:
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:17:27
 */
@Service
public class DianHuaBangServiceImpl implements IDianHuaBangService{
	private static Log logger = LogFactory.getLog(DianHuaBangServiceImpl.class);
	@Autowired
    private SystemProperty constPro;
	@Autowired
	private IReportMongoDBService reportMongoDBService;
	@Autowired
	private ICustomerService customerService;
	
	private static String token = null;
	
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

	/**
	 * 获取token，用于之后的接口访问,由于token存在过期时间，所以后面判断返回code为4000的时候重新获取
	 */
	@Override
	public String getToken() {
		if (token != null) {
			return token;
		}
		
		Long timeStamp = System.currentTimeMillis();
		
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("appid", constPro.DHB_ACCOUNT);
		reqMap.put("sign", MD5Util.MD5Encode(constPro.DHB_ACCOUNT + constPro.DHB_SECRET + String.valueOf(timeStamp / 1000), "UTF-8"));
		reqMap.put("time", String.valueOf(timeStamp / 1000));
		try {
			// token有效期为 7200 秒（2小时）
			String respStr = HttpUtils.httpGetRequest(constPro.DHB_REQ_URL +"/token", reqMap);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return token;
	}


	/**
	 * 获取登录方式（获取会话标识sid） 
	 */
	@Override
//	public String getSid(JXLSubmitFormReq req,String token, String tel) {
	public DHBGetLoginResp getSid(DHBGetLoginReq req, Integer cusId) {
//		String url = "https://crs-api.dianhua.cn/calls/flow?token=" + token;
//		Map<String, Object> reqMap = new HashMap<>();
//		reqMap.put("tel", tel);
//		reqMap.put("user_info", new UserInfo());//可传参数
//		reqMap.put("user_contact", new ArrayList<UserContactInfo>());//可传参数
		//根据前端提交数据，可以选择提交user_info对象和user_contact对象
		
		DHBGetLoginResp resp = null;
		try {
			String resJson = HttpUtils.httpPostJsonRequest(constPro.DHB_REQ_URL+"/calls/flow?token="+this.getToken(), req.toJsonString());
			if(resJson != null) {
				resp = DHBGetLoginResp.fromJsonString(resJson);
				
				/*JSONObject json = JSONObject.parseObject(respStr);
				int code = json.getIntValue("status");
				if(code != 0) {
					return DianHuaBangErrorCode.getMessage(code);
				}
				JSONObject data = (JSONObject) json.get("data");
				String sid = data.getString("sid");//查询详单所需的id号
//				int fullName = data.getIntValue("need_full_name");//是否需要在登录时候提交用户真实姓名：  1为需要    0为不需要
//				int idCard = data.getIntValue("need_id_card");//是否需要在登录时候提交用户身份证号：  1为需要    0为不需要
				int pinPwd = data.getIntValue("need_pin_pwd");//是否需要在登录时候提交用户手机号服务密码：  1为需要    0为不需要
				String sms = data.getString("sms_duration");//是否需要在登录时输入短信验证码: 不为空即为需要
				String img = data.getString("captcha_image");//是否需要在登录时输入图形验证码 base64 码: 不为空即为需要
				
				return data.toString();*/
			} else {
				throw new RuntimeException("获取登录方式sid失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		if (resp.getStatus() == 0) {
			// 写mongoDB
			Report report = new Report();
			report.setToken(resp.getSid());
			report.setIdCard(req.getUserIdcard());
			report.setMobile(req.getTel());
			report.setName(req.getUserName());
			report.setCusId(cusId.toString());
			report.setApplyDate(Calendar.getInstance().getTime()); //请求时间
			report.setStatus(-1); //准备采集
			reportMongoDBService.save(report);
			
			// 修改报告采集状态
			Customer cus = new Customer();
			cus.setId(cusId);
			cus.setLatestReportStatus(-1); //准备采集
			customerService.updateSelectiveById(cus);
		}
		return resp;
	}
	
	/**
	 * 登录（采集信息）
	 */
	@Override
	public String login(String token, DHBLoginReq loginReq) {
		String url = "https://crs-api.dianhua.cn/calls/login?token=" + token;
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("sid", loginReq.getSid());//必要字段
		reqMap.put("tel", loginReq.getTel());//必要字段
		if(!StringUtil.isEmpty(loginReq.getFullName())){
			reqMap.put("full_name", loginReq.getFullName());
		}
		if(!StringUtil.isEmpty(loginReq.getIdCard())){
			reqMap.put("id_card", loginReq.getIdCard());
		} 
		if(!StringUtil.isEmpty(loginReq.getPinPwd())){
			reqMap.put("pin_pwd", loginReq.getPinPwd());
		} 
		if(!StringUtil.isEmpty(loginReq.getSmsCode())){
			reqMap.put("sms_code", loginReq.getSmsCode());
		} 
		if(!StringUtil.isEmpty(loginReq.getCaptchaCode())){
			reqMap.put("captcha_code", loginReq.getCaptchaCode());
		} 
		
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 登录后二次验证
	 */
	@Override
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
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 拉取详单报告
	 */
	@Override
	public String getReport(String token, String sid) {
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


	/**
	 * 忘记服务密码
	 */
	@Override
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
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 忘记服务密码短信校验
	 */
	@Override
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
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 忘记密码联系人通话记录校验
	 */
	@Override
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
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 设置新的服务密码
	 */
	@Override
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
			e.printStackTrace();
		}
		
		return null;
	}


	/**
	 * 忘记密码登录校验
	 */
	@Override
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
			e.printStackTrace();
		}
		
		return null;
	}

}
