package com.webill.core.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.webill.app.SystemProperty;
import com.webill.app.util.DateUtil;
import com.webill.app.util.HttpUtils;
import com.webill.app.util.MD5Util;
import com.webill.app.util.PageUtil;
import com.webill.app.util.StringUtil;
import com.webill.app.util.TransNoUtil;
import com.webill.core.model.Customer;
import com.webill.core.model.RedisKeyDto;
import com.webill.core.model.User;
import com.webill.core.model.dianhuabang.DHBCallsRecord;
import com.webill.core.model.dianhuabang.DHBGetLoginReq;
import com.webill.core.model.dianhuabang.DHBGetLoginResp;
import com.webill.core.model.dianhuabang.DHBLoginReq;
import com.webill.core.model.dianhuabang.DHBLoginResp;
import com.webill.core.model.dianhuabang.DianHuaBangErrorCode;
import com.webill.core.model.juxinli.Report;
import com.webill.core.model.report.FinancialCallInfo;
import com.webill.core.service.ICustomerService;
import com.webill.core.service.IDianHuaBangService;
import com.webill.core.service.IJuxinliService;
import com.webill.core.service.IReportMongoDBService;
import com.webill.core.service.ITongDunService;
import com.webill.core.service.IUserService;
import com.webill.core.service.RedisService;
import com.webill.framework.common.JSONUtil;

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
	@Autowired
	private IJuxinliService juxinliService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private ITongDunService tongDunService;
	@Autowired
	private IUserService userService;
	
 	/**
	 * 【电话邦】==>获取登录方式（获取会话标识sid）
	 */
	@Override
	@Transactional
	public Object dhbGetSid(DHBGetLoginReq dhbReq, Integer cusId, Integer temReportType) {
		DHBGetLoginResp dhbResp = null;
		
		try {
			//TODO 【电话邦】请求
			logger.info("【电话邦】获取登录方式-请求参数-request："+dhbReq.toJsonString());
			String dhbResJson = HttpUtils.httpPostJsonRequest(constPro.DHB_REQ_URL+"/calls/flow?token="+this.getDhbToken(), dhbReq.toJsonString());
			logger.info("【电话邦】获取登录方式-响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbResp = DHBGetLoginResp.fromJsonString(dhbResJson);
			} else {
				throw new RuntimeException("获取登录方式sid失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (dhbResp.getStatus() == 0) {
			// 写mongoDB
			Report report = new Report();
			String reportKey = TransNoUtil.getReportKey();
			logger.info("reportKey:"+reportKey);
			report.setReportKey(reportKey);
			report.setSid(dhbResp.getSid());
			report.setCusId(cusId.toString());
			report.setDhbStatus(-1); //准备采集
			report.setApplyDate(Calendar.getInstance().getTime()); //请求时间
			
			report.setIdCard(dhbReq.getUserIdcard());
			report.setMobile(dhbReq.getTel());
			report.setName(dhbReq.getUserName());
			if (temReportType == 0) { //临时信息报告类型：0-基础 1-标准
				report.setStatus(-1); //准备采集
			}
			reportMongoDBService.save(report);
			
			// 修改报告为准备采集状态
			Customer cust = new Customer();
			cust.setId(cusId);
			cust.setLatestDhbRepStatus(-1); //准备采集
			cust.setLatestReportStatus(-1); //准备采集

			customerService.updateSelectiveById(cust);
		}
		JSONObject jo = new JSONObject();
		jo.put("dhbGetLogin", dhbResp);
		return jo;
	}
 
	/**
	 * 【电话邦】==>登录授权（采集信息）
	 */
	@Override
	@Transactional
	public Object dhbCollect(DHBLoginReq dhbReq, Customer cus) {
		Customer cust = new Customer();
		cust.setId(cus.getId());
		// 服务密码更新入库
		cust.setServicePwd(dhbReq.getPinPwd());
		// 临时报告类型更新入库（可能未采集成功）
		cust.setTemReportType(cus.getTemReportType());
		customerService.updateSelectiveById(cust);
		
		// 提交数据采集请求
		DHBLoginResp dhbResp = null;
		try {
			Customer dhbCus = customerService.selectById(cus.getId());
			dhbReq.setIdCard(dhbCus.getIdNo());
			dhbReq.setFullName(dhbCus.getRealName());
			//TODO 请求【电话邦】采集接口
			logger.info("【电话邦】登录授权-请求参数-request："+dhbReq.toJsonString());
			logger.info("【电话邦】登录授权地址："+constPro.DHB_REQ_URL+"/calls/login?token="+this.getDhbToken());
			String dhbResJson = HttpUtils.httpPostJsonRequest(constPro.DHB_REQ_URL+"/calls/login?token="+this.getDhbToken(), dhbReq.toJsonString());
			logger.info("【电话邦】登录授权-响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbResp = DHBLoginResp.fromJsonString(dhbResJson);
			} else {
				throw new RuntimeException("登录授权请求失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Report mgReport = reportMongoDBService.selectReportBySid(dhbReq.getSid());
		Customer tdCus = customerService.selectById(cus.getId());
		if ("done".equals(dhbResp.getAction())) {	
			// 修改报告采集状态
			Customer custo = new Customer();
			custo.setId(cus.getId());
			custo.setLatestDhbRepStatus(0); //采集中
			if (cus.getTemReportType() == 0) {//临时信息报告类型：0-基础 1-标准
				custo.setLatestReportStatus(0); //采集中
			}
			customerService.updateSelectiveById(custo);
			
			// 更新mongoDB信息
			Report report = new Report();
			report.setSid(dhbReq.getSid());
			report.setApplyDate(Calendar.getInstance().getTime());
			report.setReportType(cus.getTemReportType()); //信息报告类型：0-基础 1-标准 
			report.setDhbStatus(0); //采集中
			if (cus.getTemReportType() == 0) {//临时信息报告类型：0-基础 1-标准
				report.setStatus(0); //采集中
			}
			reportMongoDBService.updateReportBySid(report);
			
			// 采集【同盾】
			try {
				logger.info("【电话邦】登录授权成功，开始采集【同盾】数据，reportKey="+mgReport.getReportKey()+",客户ID为"+tdCus.getId());
				tongDunService.saveSubmitQuery(mgReport.getReportKey(), tdCus.getId().toString());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else if (dhbResp.getStatus() == 3100){ // 查询失败 
			/*Report mgdbReport = reportMongoDBService.selectReportBySid(dhbReq.getSid());
			// 获取【聚信立】准备采集数据
			JXLSubmitFormReq jxlReq = customerService.getJXLSubmitFormReq(cus.getId());
			//TODO 【聚信立】请求
			JSONObject jo = (JSONObject)juxinliService.submitForm(cus.getId(), mgdbReport.getReportKey());
			JSONObject jxlsfObj = jo.getJSONObject("jxlSubmitForm");*/
		}
		

		JSONObject jo = new JSONObject();
		jo.put("dhbCollect", dhbResp);
		jo.put("reportKey", mgReport.getReportKey());
		return jo;
	}
	
	/**
	 * 【电话邦】==>二次登录授权验证（采集信息）
	 */
	public Object dhbCollectSec(DHBLoginReq dhbReq, Customer cus) {
		// 提交数据采集请求
		DHBLoginResp dhbResp = null;
		
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("sid", dhbReq.getSid());
		if (StringUtil.isNotEmpty(dhbReq.getSmsCode())) {
			reqMap.put("sms_code", dhbReq.getSmsCode());
		}
		if (StringUtil.isNotEmpty(dhbReq.getCaptchaCode())) {
			reqMap.put("captcha_code", dhbReq.getCaptchaCode());
		}
		try {
			//TODO 请求【电话邦】采集接口
			logger.info("【电话邦】-二次登录授权验证-请求参数-request："+reqMap);
			String dhbResJson = HttpUtils.httpPostRequest(constPro.DHB_REQ_URL+"/calls/verify?token="+this.getDhbToken(), reqMap);
			logger.info("【电话邦】-二次登录授权验证-响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbResp = DHBLoginResp.fromJsonString(dhbResJson);
			} else {
				throw new RuntimeException("二次登录授权验证请求失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Report mgReport = reportMongoDBService.selectReportBySid(dhbReq.getSid());
		Customer tdCus = customerService.selectById(cus.getId());
		if ("done".equals(dhbResp.getAction())) {	
			// 修改报告采集状态
			Customer custo = new Customer();
			custo.setId(cus.getId());
			custo.setLatestDhbRepStatus(0); //采集中
			if (cus.getTemReportType() == 0) {//临时信息报告类型：0-基础 1-标准
				custo.setLatestReportStatus(0); //采集中
			}
			customerService.updateSelectiveById(custo);
			
			// 更新mongoDB信息
			Report report = new Report();
			report.setSid(dhbReq.getSid());
			report.setApplyDate(Calendar.getInstance().getTime());
			report.setReportType(cus.getTemReportType()); //信息报告类型：0-基础 1-标准 
			report.setDhbStatus(0); //采集中
			if (cus.getTemReportType() == 0) {//临时信息报告类型：0-基础 1-标准
				report.setStatus(0); //采集中
			}
			reportMongoDBService.updateReportBySid(report);
			
			// 采集【同盾】
			try {
				logger.info("【电话邦】二次登录授权成功，开始采集【同盾】数据，reportKey="+mgReport.getReportKey()+",客户ID为"+tdCus.getId());
				tongDunService.saveSubmitQuery(mgReport.getReportKey(), tdCus.getId().toString());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else if (dhbResp.getStatus() == 3100){ // 查询失败 
			/*Report mgdbReport = reportMongoDBService.selectReportBySid(dhbReq.getSid());
			// 获取【聚信立】准备采集数据
			JXLSubmitFormReq jxlReq = customerService.getJXLSubmitFormReq(cus.getId());
			//TODO 【聚信立】请求
			JSONObject jo = (JSONObject)juxinliService.submitForm(cus.getId(), mgdbReport.getReportKey());
			JSONObject jxlsfObj = jo.getJSONObject("jxlSubmitForm");*/
		}
		

		JSONObject jo = new JSONObject();
		jo.put("dhbCollect", dhbResp);
		jo.put("reportKey", mgReport.getReportKey());
		return jo;
	}
	
	/**
	 * 【电话邦】==>刷新图形验证码
	 */
	@Override
	public Object refreshGraphic(String sid) {
		JSONObject dhbRespObj = new JSONObject();
		String url = constPro.DHB_REQ_URL+"/calls/verify/captcha?token=" + this.getDhbToken() + "&sid=" + sid;
		try {
			logger.info("【电话邦】-刷新图形验证码-请求参数-request："+sid);
			String dhbResJson = HttpUtils.httpGetRequest(url);
			logger.info("【电话邦】-刷新图形验证码 -响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbRespObj = JSONObject.parseObject(dhbResJson);
			} else {
				throw new RuntimeException("刷新图形验证码请求失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return dhbRespObj;
	}
	
	/**
	 * 【电话邦】==>拉取详单报告
	 */
	@Override
	@Transactional
	public String updateDhbReport(String sid) {
		try {
			String url = constPro.DHB_REQ_URL+"/calls/report?token=" + this.getDhbToken() + "&sid=" + sid;
			String resJson = HttpUtils.httpGetRequest(url);
			if(resJson != null) {
				logger.info("【电话邦】报告信息==>"+ resJson);
				JSONObject json = JSONObject.parseObject(resJson);
				if (json.getIntValue("status") == 0) {
					if (json.containsKey("data")) {
						// 获取报告数据
						String reportData = json.getJSONObject("data").toString();
						// 根据sid获取mongodb报告
						Report mdbReport = reportMongoDBService.selectReportBySid(sid);
						Customer cus = customerService.selectById(Integer.parseInt(mdbReport.getCusId()));
						// 获取报告创建时间
						String createdAt = json.getJSONObject("data").getString("created_at");
						
						//TODO 1：将报告更新时间更新到数据库中
						if (createdAt != null) {
							try {
								if (createdAt.contains(".")) {
									createdAt = createdAt.substring(0, createdAt.indexOf("."));
								}
								cus.setId(Integer.parseInt(mdbReport.getCusId()));
								String timeStampToDat = DateUtil.timeStampToDat(Long.parseLong(createdAt));
								cus.setLatestReportTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStampToDat));
								customerService.updateSelectiveById(cus);
								logger.info("【电话邦】报告更新时间入库成功");
							} catch (Exception e) {
								logger.info("获取报告时间异常");
							}
						}
						
						//TODO 2：【电话邦】原始数据到mongoDB
						Report report = new Report();
						report.setSid(sid);
						// 原始【电话邦】数据
						report.setDhbOrgReport(reportData);
						reportMongoDBService.updateReportBySid(report);
						return reportData;
					} else {
						throw new RuntimeException("获取运营商原始数据请求返回报文中没有data节点！");
					}
				} else {
					throw new Exception("获取运营商原始数据请求【电话邦】失败！");
				}
			} else {
				throw new RuntimeException("获取详单请求【电话邦】失败");
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 【电话邦】==>拉取原始详单列表
	 */
	@Override
	@Transactional
	public String updateDhbOrgCallsRecord(String sid) {
		try {
			String url = constPro.DHB_REQ_URL+"/calls/record?token=" + this.getDhbToken() + "&sid=" + sid;
			String resJson = HttpUtils.httpGetRequest(url);
			if(resJson != null) {
				logger.info("【电话邦】原始详单==>"+ resJson);
				JSONObject json = JSONObject.parseObject(resJson);
				if (json.getIntValue("status") == 0) {
					if (json.containsKey("data")) {
						// 获取报告数据
						String callsRecordData = json.getJSONObject("data").toString();
						
						//TODO 【电话邦】原始详单到mongoDB
						Report report = new Report();
						report.setSid(sid);
						// 原始【电话邦】数据
						report.setDhbOrgCallsRecord(callsRecordData);
						reportMongoDBService.updateReportBySid(report);
						return callsRecordData;
					} else {
						throw new RuntimeException("获取原始详单没有data节点！");
					}
				} else {
					throw new Exception("获取原始详单失败！");
				}
			} else {
				throw new RuntimeException("获取原始详单失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 【电话邦】==>获取token，用于之后的接口访问,由于token存在过期时间2个小时，所以后面判断返回code为4000的时候重新获取
	 */
	@Override
	public String getDhbToken() {
		String dhbToken = null;
		// 从redis查询【电话邦】访问token
		RedisKeyDto rk = new RedisKeyDto();
		rk.setKeys(constPro.DHB_ACC_TOKNE_KEY);
		RedisKeyDto rv = redisService.redisGet(rk);
		
		if (rv != null) {
			logger.info("【电话邦】-接口请求token："+rv.getValues());
			return rv.getValues();
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
				dhbToken = data.getString("token");
				logger.info("【电话邦】-接口请求token："+dhbToken+",账户："+constPro.DHB_ACCOUNT+",密码："+constPro.DHB_SECRET);
				// 【电话邦】访问token入redis
				RedisKeyDto rkd = new RedisKeyDto();
				rkd.setKeys(constPro.DHB_ACC_TOKNE_KEY);
				rkd.setValues(dhbToken);
				String expire = constPro.DHB_ACC_TOKNE_EXPIRE;
				redisService.addRedisData(rkd, Integer.parseInt(expire));
			} else {
				throw new RuntimeException("获取token失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dhbToken;
	}
	
	/**
	 * 【电话邦】==>忘记服务密码
	 */
	@Override
	public Object forgetPwd(String tel, String userName, String idCard) {
		JSONObject dhbRespObj = new JSONObject();
		
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tel", tel);
		reqMap.put("user_name", userName);
		reqMap.put("user_id", idCard);
		
		try {
			logger.info("【电话邦】-忘记服务密码-请求参数-request："+reqMap);
			String dhbResJson = HttpUtils.httpPostRequest(constPro.DHB_REQ_URL+"/forget/pwd?token="+this.getDhbToken(), reqMap);
			logger.info("【电话邦】-忘记服务密码-响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbRespObj = JSONObject.parseObject(dhbResJson);
				//tid：修改密码所需的id号(必返回，类似sid)
				//need_new_pwd：是否需要新服务密码：  1为需要    0为不需要
				//sms_duration：是否需要在登录时输入短信验证码: 不为空即为需要
				//login_sms_duration：登录短信验证码有效时长，如果有值，则需要继续进一步验证，需调用找回密码登录校验接口；如果为空，不需要调用
			} else {
				throw new RuntimeException("忘记服务密码请求失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return dhbRespObj;
	}
		
	/**
	 * 【电话邦】==>设置新的服务密码（need_new_pwd不为0时调用）
	 */
	@Override
	public Object setServicePwd(String tid, String newPwd) {
		JSONObject dhbRespObj = new JSONObject();
		
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tid", tid);
		reqMap.put("new_pwd", newPwd);
		
		try {
			logger.info("【电话邦】-设置新的服务密码-请求参数-request："+reqMap);
			String dhbResJson = HttpUtils.httpPostRequest(constPro.DHB_REQ_URL+"/forget/pwd?token="+this.getDhbToken(), reqMap);
			logger.info("【电话邦】-设置新的服务密码-响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbRespObj = JSONObject.parseObject(dhbResJson);
			} else {
				throw new RuntimeException("设置新的服务密码请求失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return dhbRespObj;
	}
	
	/**
	 * 【电话邦】==>忘记服务密码短信校验（重置密码，并返回新密码:返回2条信息，1条为短信验证码，1条为重置的新密码）==》可能会递归
	 */
	@Override
	public Object forgotPwdSms(String tid, String smsCode) {
		JSONObject dhbRespObj = new JSONObject();
		
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tid", tid);
		reqMap.put("sms_code", smsCode);
		
		try {
			logger.info("【电话邦】-忘记服务密码短信校验-请求参数-request："+reqMap);
			String dhbResJson = HttpUtils.httpPostRequest(constPro.DHB_REQ_URL+"/forget/pwd?token="+this.getDhbToken(), reqMap);
			logger.info("【电话邦】-忘记服务密码短信校验-响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbRespObj = JSONObject.parseObject(dhbResJson);
			} else {
				throw new RuntimeException("忘记服务密码短信校验请求失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return dhbRespObj;
	}
	
	/**
	 * 【电话邦】==>忘记密码登录校验（login_sms_duration不为null时调用）
	 */
	@Override
	public Object forgotPwdLogin(String tid, String loginCode) {
		JSONObject dhbRespObj = new JSONObject();
		
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tid", tid);
		reqMap.put("login_code", loginCode);
		
		try {
			logger.info("【电话邦】-忘记密码登录校验-请求参数-request："+reqMap);
			String dhbResJson = HttpUtils.httpPostRequest(constPro.DHB_REQ_URL+"/forget/pwd?token="+this.getDhbToken(), reqMap);
			logger.info("【电话邦】-忘记密码登录校验-响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbRespObj = JSONObject.parseObject(dhbResJson);
			} else {
				throw new RuntimeException("忘记密码登录校验请求失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return dhbRespObj;
	}
	
	/**
	 * 【电话邦】==>忘记密码联系人通话记录校验
	 */
	@Override
	public Object forgotPwdContact(String tid, String[] telList) {
		JSONObject dhbRespObj = new JSONObject();
		
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tid", tid);
		reqMap.put("tel_list ", telList);//联系人手机号（need_contacts 为 1 时调用）
		
		try {
			logger.info("【电话邦】-忘记密码联系人通话记录校验-请求参数-request："+reqMap);
			String dhbResJson = HttpUtils.httpPostRequest(constPro.DHB_REQ_URL+"/forget/pwd?token="+this.getDhbToken(), reqMap);
			logger.info("【电话邦】-忘记密码联系人通话记录校验-响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbRespObj = JSONObject.parseObject(dhbResJson);
			} else {
				throw new RuntimeException("忘记密码联系人通话记录校验请求失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return dhbRespObj;
	}
	
	/**
	 * 每2分钟更新【聚信立】和【电话邦】报告（基础版、标准版）
	 */
	@Override
	@Transactional
	public void updateJxlDhbReport(){
		//TODO 标准版
		List<Report> stanReportList = reportMongoDBService.selectStanReportByStatus();
		if (stanReportList != null && stanReportList.size() > 0) {
			for (Report report : stanReportList) {
				logger.info("开始合并-【电话邦】-【聚信立】-【同盾】报告，需要合并的报告数为"+stanReportList.size());
				// 同盾数据
				JSONObject tdObj = JSON.parseObject(report.getTdReport());
				// 原始详单数据
				JSONObject callsRecordObj = JSON.parseObject(report.getDhbOrgCallsRecord());
				
				String dhbReportJson = null;
				String jxlReportJson = null;
				try {
					// 解析【电话邦】数据
					dhbReportJson = juxinliService.parseDHBReportData(report.getDhbOrgReport(), Integer.parseInt(report.getCusId()));
					// 解析【聚信立】数据
					jxlReportJson = juxinliService.parseJXLReportData(report.getJxlOrgReport(), Integer.parseInt(report.getCusId()));
					
					if (dhbReportJson != null && jxlReportJson != null) {
						JSONObject dhbObj = JSON.parseObject(dhbReportJson);
						JSONObject jxlObj = JSON.parseObject(jxlReportJson);
						report.setDhbReport(dhbReportJson);
						report.setJxlReport(jxlReportJson);
						//TODO 1：合并【聚信立】、【电话邦】、【同盾】报告
						// 更新mongoDB报告状态
						report.setToken(report.getToken());
						report.setStatus(1); //采集成功
						
						JSONObject jo = new JSONObject();
						jo.put("cus_basic_info", jxlObj.getJSONObject("cus_basic_info"));
						jo.put("report_contact", dhbObj.getJSONArray("report_contact"));
						jo.put("black_info", jxlObj.getJSONObject("black_info"));
						// 合并【聚信立】金融信息和【电话邦】标准版信息
						List<FinancialCallInfo> jxlFciList = JSONUtil.toObjectList(jxlObj.getString("financial_call_info"), FinancialCallInfo.class);
						List<FinancialCallInfo> dhbFciStaList = JSONUtil.toObjectList(dhbObj.getString("financial_call_info_sta"), FinancialCallInfo.class);
						List<FinancialCallInfo> allFciOList = new ArrayList<>();
						allFciOList = jxlFciList;
						allFciOList.addAll(dhbFciStaList);
						jo.put("financial_call_info", allFciOList);
						jo.put("contact_region", dhbObj.getJSONArray("contact_region"));
						jo.put("top10_date_contact", dhbObj.getJSONArray("top10_date_contact"));
						jo.put("top10_times_contact", dhbObj.getJSONArray("top10_times_contact"));
						jo.put("all_contact", dhbObj.getJSONArray("all_contact"));
						jo.put("trip_info", jxlObj.getJSONArray("trip_info"));
						jo.put("cuishou", dhbObj.getJSONObject("cuishou"));
						jo.put("yisicuishou", dhbObj.getJSONObject("yisicuishou"));
						// 解析【同盾】征信数据
						jo.put("tongdun", tdObj);
						jo.put("callsRecord", callsRecordObj);
						report.setFinalReport(jo.toString());
						reportMongoDBService.updateReportByReportKey(report);
						
						//TODO 2：更新customer客户信息
						Customer cus = customerService.selectById(Integer.parseInt(report.getCusId()));
						if (cus.getTemReportType() == 1) { //信息报告类型：0-基础 1-标准 
							// 更新客户查询状态信息
							cus.setId(Integer.parseInt(report.getCusId()));
							cus.setRefreshTimes(cus.getRefreshTimes() + 1);
							cus.setLatestReportKey(report.getReportKey());
							cus.setLatestReportType(report.getReportType()); //信息报告类型：0-基础 1-标准
							cus.setLatestReportStatus(1); //采集成功
							cus.setLatestJxlRepStatus(1); //采集成功
							cus.setLatestDhbRepStatus(1); //采集成功
							customerService.updateSelectiveById(cus);
							
							// 更新user用户查询次数
							User user = userService.selectById(cus.getUserId());
							user.setId(cus.getUserId());
							if (cus.getTemReportType() == 0) {//信息报告类型：0-基础 1-标准
								user.setStandardTimes(user.getStandardTimes() - 1);
							}else {
								user.setAdvancedTimes(user.getAdvancedTimes() - 1);
							}
							userService.updateSelectiveById(user);
						}
					}
				} catch (Exception e) {
					logger.info("【电话邦】或【聚信立】-解析报告异常！");
					throw new RuntimeException(e);
				}
				
			}
		}
		//TODO 基础版
		List<Report> basicReportList = reportMongoDBService.selectBasicReportByStatus();
		if (basicReportList != null && basicReportList.size() > 0) {
			for (Report basicReport : basicReportList) {
				logger.info("开始合并-【电话邦】-【同盾】报告，需要合并的报告数为"+basicReportList.size());
				// 同盾数据
				JSONObject tdObj = JSON.parseObject(basicReport.getTdReport());
				// 原始详单数据
				JSONObject callsRecordObj = JSON.parseObject(basicReport.getDhbOrgCallsRecord());
				
				String dhbReportJson = null;
				try {
					dhbReportJson = juxinliService.parseDHBReportData(basicReport.getDhbOrgReport(), Integer.parseInt(basicReport.getCusId()));
					if (dhbReportJson != null) {
						JSONObject dhbObj = JSON.parseObject(dhbReportJson);
						//TODO 1：合并【电话邦】、【同盾】报告
						basicReport.setDhbReport(dhbReportJson);
						// 解析【同盾】征信数据
						dhbObj.put("tongdun", tdObj);
						dhbObj.put("callsRecord", callsRecordObj);
						basicReport.setFinalReport(dhbObj.toJSONString());
						basicReport.setStatus(1); //采集成功
						reportMongoDBService.updateReportBySid(basicReport);
						
						
						//TODO 2：更新customer客户信息
						Customer cus = customerService.selectById(Integer.parseInt(basicReport.getCusId()));
						if (basicReport.getReportType() == 0) { //信息报告类型：0-基础 1-标准
							// 更新客户查询状态信息
							cus.setId(Integer.parseInt(basicReport.getCusId()));
							cus.setRefreshTimes(cus.getRefreshTimes() + 1);
							cus.setLatestReportKey(basicReport.getReportKey());
							cus.setLatestReportType(basicReport.getReportType()); //信息报告类型：0-基础 1-标准
							cus.setLatestReportStatus(1); //采集成功
							cus.setLatestDhbRepStatus(1); //采集成功
							customerService.updateSelectiveById(cus);
							
							// 更新user用户查询次数
							User user = userService.selectById(cus.getUserId());
							user.setId(cus.getUserId());
							if (cus.getTemReportType() == 0) {//信息报告类型：0-基础 1-标准
								user.setStandardTimes(user.getStandardTimes() - 1);
							}else {
								user.setAdvancedTimes(user.getAdvancedTimes() - 1);
							}
							userService.updateSelectiveById(user);
						}
					}
				} catch (Exception e) {
					logger.info("【电话邦】-解析报告异常！");
					throw new RuntimeException(e);
				}
			}
		}
	} 
	
	/**
	 * 根据reportKey获取mongodb最终报告数据
	 */
	@Override
	public String selectMdbReport(String reportKey) {
		Report report = reportMongoDBService.selectReportByReportKey(reportKey);
		if (report == null) {
			return null;
		}
		return report.getFinalReport();
	}
	
	/**
	 * 分页获取电话详单数据
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PageUtil callsRecord(DHBCallsRecord dcr, JSONArray callLogArr) {
		PageUtil page = new PageUtil();
		
		// 将数组转换成字符串
		String callLogStr = JSONObject.toJSONString(callLogArr);
		// 将字符串转成list集合
		List<DHBCallsRecord> crList = JSONObject.parseArray(callLogStr, DHBCallsRecord.class);//把字符串转换成集合  
		
		if (StringUtil.isNotEmpty(dcr.getSortWay()) && "2".equals(dcr.getSortWay())) { // 排序方式：1-时间倒序，2-通话时长降序
			Collections.sort(crList, new Comparator<DHBCallsRecord>() {
				@Override
				public int compare(DHBCallsRecord cr1, DHBCallsRecord cr2) {
					// 通话时长
					Integer cd1 = Integer.parseInt(cr1.getCallDuration());
					Integer cd2 = Integer.parseInt(cr2.getCallDuration());
					return cd2.compareTo(cd1);
				}
			});
		}
		
		// 被叫号码
		if (StringUtil.isNotEmpty(dcr.getCallTel())) {
			crList = (List<DHBCallsRecord>) JSONPath.eval(crList, "[callTel like '%"+dcr.getCallTel()+"%']");
		}
		// 呼叫类型
		if ("1".equals(dcr.getCallMethod())) {
			dcr.setCallMethod("主叫");
		}else if ("2".equals(dcr.getCallMethod())) {
			dcr.setCallMethod("被叫");
		}
		if (StringUtil.isNotEmpty(dcr.getCallMethod())) {
			crList = (List<DHBCallsRecord>) JSONPath.eval(crList, "[callMethod like '%"+dcr.getCallMethod()+"%']");
		}
		logger.info(JSONUtil.toJSONString(crList));
		// 通话起始时间
		if (StringUtil.isNotEmpty(dcr.getTimeFrom())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long callTimeStart, callTimeEnd;
			try {
				callTimeStart = sdf.parse(dcr.getTimeFrom()).getTime()/1000;
				if (StringUtil.isNotEmpty(dcr.getTimeTo())) {
					callTimeEnd = sdf.parse(dcr.getTimeTo()).getTime()/1000;
				}else {
					callTimeEnd = System.currentTimeMillis()/1000;
				}
				crList = (List<DHBCallsRecord>) JSONPath.eval(crList, "[callTime between "+ callTimeStart +" and "+callTimeEnd+"]");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		//刚开始的页面为第一页
		if (StringUtil.isEmpty(dcr.getCurrentPage())){
			page.setCurrentPage(1);
		} else {
			page.setCurrentPage(dcr.getCurrentPage());
		}
		//设置每页数据为十条
		page.setPageSize(dcr.getPageSize());
		//每页的开始数
		page.setStar((dcr.getCurrentPage() - 1) * page.getPageSize());
		//list的大小
		int count = crList.size();
		//设置总页数
		page.setTotalPage(count % dcr.getPageSize() == 0 ? count / dcr.getPageSize() : count / dcr.getPageSize() + 1);
		//设置总条数
		page.setTotalSize(count);
		// 添加索引值
		for (int i = 0; i < crList.size(); i++) {
			crList.get(i).setIndex(i+1);
			crList.get(i).setCallDuration(DateUtil.secondToMinute(Integer.parseInt(crList.get(i).getCallDuration())));
		}
		//对list进行截取
		page.setDataList(crList.subList(page.getStar(), count-page.getStar()>page.getPageSize() ? page.getStar()+page.getPageSize() : count));
		return page;
	}
	
}
