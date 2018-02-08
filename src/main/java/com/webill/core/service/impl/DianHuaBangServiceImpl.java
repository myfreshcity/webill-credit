package com.webill.core.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.webill.app.SystemProperty;
import com.webill.app.util.DateUtil;
import com.webill.app.util.HttpUtils;
import com.webill.app.util.MD5Util;
import com.webill.app.util.StringUtil;
import com.webill.app.util.TransNoUtil;
import com.webill.core.model.Customer;
import com.webill.core.model.RedisKeyDto;
import com.webill.core.model.User;
import com.webill.core.model.dianhuabang.DHBGetLoginReq;
import com.webill.core.model.dianhuabang.DHBGetLoginResp;
import com.webill.core.model.dianhuabang.DHBLoginReq;
import com.webill.core.model.dianhuabang.DHBLoginResp;
import com.webill.core.model.dianhuabang.DianHuaBangErrorCode;
import com.webill.core.model.juxinli.JXLSubmitFormReq;
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
	 * 电话邦==>获取登录方式（获取会话标识sid）
	 */
	@Override
	@Transactional
	public Object dhbGetSid(DHBGetLoginReq dhbReq, Integer cusId) {
		DHBGetLoginResp dhbResp = null;
		
		try {
			//TODO 电话邦请求
			logger.info("电话邦获取登录方式请求参数-request："+dhbReq.toJsonString());
			String dhbResJson = HttpUtils.httpPostJsonRequest(constPro.DHB_REQ_URL+"/calls/flow?token="+this.getDhbToken(), dhbReq.toJsonString());
			logger.info("电话邦获取登录方式响应数据-response："+dhbResJson);
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
			
//			report.setToken(jxlResp.getToken());
//			report.setJxlStatus(-1); //准备采集
//			report.setStatus(-1); //准备采集
			reportMongoDBService.save(report);
			
			// 修改报告为准备采集状态
			Customer cust = new Customer();
			cust.setId(cusId);
			cust.setLatestDhbRepStatus(-1); //准备采集
			//cus.setLatestReportStatus(-1); //准备采集
			customerService.updateSelectiveById(cust);
		}
		JSONObject jo = new JSONObject();
		jo.put("dhbGetLogin", dhbResp);
		return jo;
	}
 
	/**
	 * 聚信立==>提交数据采集请求,根据返回的processCode，可能会请求多次
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
			//TODO 请求电话邦采集接口
			logger.info("电话邦登录请求参数-request："+dhbReq.toJsonString());
			String dhbResJson = HttpUtils.httpPostJsonRequest(constPro.DHB_REQ_URL+"/calls/login?token="+this.getDhbToken(), dhbReq.toJsonString());
			logger.info("电话邦登录响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbResp = DHBLoginResp.fromJsonString(dhbResJson);
			} else {
				throw new RuntimeException("登录请求失败");
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
			//custo.setLatestReportStatus(0); //采集中
			customerService.updateSelectiveById(custo);
			
			// 更新mongoDB信息
			Report report = new Report();
			report.setSid(dhbReq.getSid());
			report.setApplyDate(Calendar.getInstance().getTime());
			report.setReportType(cus.getTemReportType()); //信息报告类型：0-基础 1-标准 
			report.setDhbStatus(0); //采集中
			//report.setStatus(0); //采集中
			reportMongoDBService.updateReportBySid(report);
			
			// 采集同盾
			try {
				tongDunService.saveSubmitQuery(mgReport.getReportKey(), tdCus.getUserId().toString());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}else if (dhbResp.getStatus() == 3100){ // 查询失败 
			Report mgdbReport = reportMongoDBService.selectReportBySid(dhbReq.getSid());
			// 获取聚信立准备采集数据
			JXLSubmitFormReq jxlReq = customerService.getJXLSubmitFormReq(cus.getId());
			//TODO 聚信立请求
			JSONObject jo = (JSONObject)juxinliService.submitForm(cus.getId(), mgdbReport.getReportKey());
			JSONObject jxlsfObj = jo.getJSONObject("jxlSubmitForm");
		}
		

		JSONObject jo = new JSONObject();
		//jo.put("jxlCollect", jxlResp);
		jo.put("dhbCollect", dhbResp);
		jo.put("reportKey", mgReport.getReportKey());
		return jo;
	}
	
	/**
	 * 二次登录验证	
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
			//TODO 请求电话邦采集接口
			logger.info("电话邦-二次登录验证-请求参数-request："+reqMap);
			String dhbResJson = HttpUtils.httpPostRequest(constPro.DHB_REQ_URL+"/calls/verify?token="+this.getDhbToken(), reqMap);
			logger.info("电话邦-二次登录验证-响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbResp = DHBLoginResp.fromJsonString(dhbResJson);
			} else {
				throw new RuntimeException("二次登录验证请求失败");
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
			//custo.setLatestReportStatus(0); //采集中
			customerService.updateSelectiveById(custo);
			
			// 更新mongoDB信息
			Report report = new Report();
			report.setSid(dhbReq.getSid());
			report.setApplyDate(Calendar.getInstance().getTime());
			report.setReportType(cus.getTemReportType()); //信息报告类型：0-基础 1-标准 
			report.setDhbStatus(0); //采集中
			//report.setStatus(0); //采集中
			reportMongoDBService.updateReportBySid(report);
			
			// 采集同盾
			try {
				tongDunService.saveSubmitQuery(mgReport.getReportKey(), tdCus.getUserId().toString());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}else if (dhbResp.getStatus() == 3100){ // 查询失败 
			Report mgdbReport = reportMongoDBService.selectReportBySid(dhbReq.getSid());
			// 获取聚信立准备采集数据
			JXLSubmitFormReq jxlReq = customerService.getJXLSubmitFormReq(cus.getId());
			//TODO 聚信立请求
			JSONObject jo = (JSONObject)juxinliService.submitForm(cus.getId(), mgdbReport.getReportKey());
			JSONObject jxlsfObj = jo.getJSONObject("jxlSubmitForm");
		}
		

		JSONObject jo = new JSONObject();
		//jo.put("jxlCollect", jxlResp);
		jo.put("dhbCollect", dhbResp);
		jo.put("reportKey", mgReport.getReportKey());
		return jo;
	}
	
	/**
	 * 刷新图形验证码
	 */
	@Override
	public Object refreshGraphic(String sid) {
		JSONObject dhbRespObj = new JSONObject();
		
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("sid", sid);
		try {
			logger.info("电话邦-刷新图形验证码-请求参数-request："+reqMap);
			String dhbResJson = HttpUtils.httpGetRequest(constPro.DHB_REQ_URL+"/calls/verify/captcha?token="+this.getDhbToken(), reqMap);
			logger.info("电话邦-刷新图形验证码 -响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbRespObj = JSONObject.parseObject(dhbResJson);
			} else {
				throw new RuntimeException("刷新图形验证码请求失败");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return dhbRespObj;
	}
	
	/**
	 * 电话邦==>拉取详单报告
	 */
	@Override
	@Transactional
	public String updateDhbReport(String sid) {
		try {
			String url = constPro.DHB_REQ_URL+"/calls/report?token=" + this.getDhbToken() + "&sid=" + sid;
			String resJson = HttpUtils.httpGetRequest(url);
			if(resJson != null) {
				logger.info("电话邦报告信息==>"+ resJson);
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
							} catch (Exception e) {
								logger.info("获取报告时间异常");
							}
						}
						
						//TODO 2：处理电话邦数据到mongoDB
						Report report = new Report();
						report.setSid(sid);
						// 原始电话邦数据
						report.setDhbOrgReport(reportData);
						// 解析电话邦数据
						String dhbReportJson = juxinliService.parseDHBReportData(reportData, cus.getId());
						report.setDhbReport(dhbReportJson);
						if (cus.getTemReportType() == 0) { //信息报告类型：0-基础 1-标准
							report.setFinalReport(dhbReportJson);
						}
						reportMongoDBService.updateReportBySid(report);
						return dhbReportJson;
					} else {
						throw new RuntimeException("获取运营商原始数据请求返回报文中没有data节点！");
					}
				} else {
					throw new Exception("获取运营商原始数据请求电话邦失败！");
				}
			} else {
				throw new RuntimeException("获取详单请求电话邦失败");
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 电话邦==>获取token，用于之后的接口访问,由于token存在过期时间，所以后面判断返回code为4000的时候重新获取
	 */
	@Override
	public String getDhbToken() {
		String dhbToken = null;
		// 从redis查询电话邦访问token
		RedisKeyDto rk = new RedisKeyDto();
		rk.setKeys(constPro.DHB_ACC_TOKNE_KEY);
		RedisKeyDto rv = redisService.redisGet(rk);
		
		if (rv != null) {
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
				// 电话邦访问token入redis
				RedisKeyDto rkd = new RedisKeyDto();
				rkd.setKeys(constPro.DHB_ACC_TOKNE_KEY);
				rkd.setValues(dhbToken);
				String expire = constPro.DHB_ACC_TOKNE_EXPIRE;
				redisService.addRedisData(rkd, Integer.parseInt(expire));
			} else {
				throw new RuntimeException("获取token失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dhbToken;
	}
	
	/**
	 * 电话邦-忘记服务密码
	 */
	@Override
	public Object forgetPwd(String tel, String userName, String idCard) {
		JSONObject dhbRespObj = new JSONObject();
		
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tel", tel);
		reqMap.put("user_name", userName);
		reqMap.put("user_id", idCard);
		
		try {
			logger.info("电话邦-忘记服务密码-请求参数-request："+reqMap);
			String dhbResJson = HttpUtils.httpPostRequest(constPro.DHB_REQ_URL+"/forget/pwd?token="+this.getDhbToken(), reqMap);
			logger.info("电话邦-忘记服务密码-响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbRespObj = JSONObject.parseObject(dhbResJson);
				//tid：修改密码所需的id号(必返回，类似sid)
				//need_new_pwd：是否需要新服务密码：  1为需要    0为不需要
				//sms_duration：是否需要在登录时输入短信验证码: 不为空即为需要
				//login_sms_duration：登录短信验证码有效时长，如果有值，则需要继续进一步验证，需调用找回密码登录校验接口；如果为空，不需要调用
			} else {
				throw new RuntimeException("忘记服务密码请求失败");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return dhbRespObj;
	}
		
	/**
	 * 电话邦-设置新的服务密码（need_new_pwd不为0时调用）
	 */
	@Override
	public Object setServicePwd(String tid, String newPwd) {
		JSONObject dhbRespObj = new JSONObject();
		
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tid", tid);
		reqMap.put("new_pwd", newPwd);
		
		try {
			logger.info("电话邦-设置新的服务密码-请求参数-request："+reqMap);
			String dhbResJson = HttpUtils.httpPostRequest(constPro.DHB_REQ_URL+"/forget/pwd?token="+this.getDhbToken(), reqMap);
			logger.info("电话邦-设置新的服务密码-响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbRespObj = JSONObject.parseObject(dhbResJson);
			} else {
				throw new RuntimeException("设置新的服务密码请求失败");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return dhbRespObj;
	}
	
	/**
	 * 忘记服务密码短信校验（重置密码，并返回新密码:返回2条信息，1条为短信验证码，1条为重置的新密码）==》可能会递归
	 */
	@Override
	public Object forgotPwdSms(String tid, String smsCode) {
		JSONObject dhbRespObj = new JSONObject();
		
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tid", tid);
		reqMap.put("sms_code", smsCode);
		
		try {
			logger.info("电话邦-忘记服务密码短信校验-请求参数-request："+reqMap);
			String dhbResJson = HttpUtils.httpPostRequest(constPro.DHB_REQ_URL+"/forget/pwd?token="+this.getDhbToken(), reqMap);
			logger.info("电话邦-忘记服务密码短信校验-响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbRespObj = JSONObject.parseObject(dhbResJson);
			} else {
				throw new RuntimeException("忘记服务密码短信校验请求失败");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return dhbRespObj;
	}
	
	/**
	 * 忘记密码登录校验（login_sms_duration不为null时调用）
	 */
	@Override
	public Object forgotPwdLogin(String tid, String loginCode) {
		JSONObject dhbRespObj = new JSONObject();
		
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tid", tid);
		reqMap.put("login_code", loginCode);
		
		try {
			logger.info("电话邦-忘记密码登录校验-请求参数-request："+reqMap);
			String dhbResJson = HttpUtils.httpPostRequest(constPro.DHB_REQ_URL+"/forget/pwd?token="+this.getDhbToken(), reqMap);
			logger.info("电话邦-忘记密码登录校验-响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbRespObj = JSONObject.parseObject(dhbResJson);
			} else {
				throw new RuntimeException("忘记密码登录校验请求失败");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return dhbRespObj;
	}
	
	/**
	 * 忘记密码联系人通话记录校验
	 */
	@Override
	public Object forgotPwdContact(String tid, String[] telList) {
		JSONObject dhbRespObj = new JSONObject();
		
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("tid", tid);
		reqMap.put("tel_list ", telList);//联系人手机号（need_contacts 为 1 时调用）
		
		try {
			logger.info("电话邦-忘记密码联系人通话记录校验-请求参数-request："+reqMap);
			String dhbResJson = HttpUtils.httpPostRequest(constPro.DHB_REQ_URL+"/forget/pwd?token="+this.getDhbToken(), reqMap);
			logger.info("电话邦-忘记密码联系人通话记录校验-响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbRespObj = JSONObject.parseObject(dhbResJson);
			} else {
				throw new RuntimeException("忘记密码联系人通话记录校验请求失败");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return dhbRespObj;
	}
	
	@Override
	@Transactional
	public void updateJxlDhbReport(){
		List<Report> reportList = reportMongoDBService.selectReportByStatus();
		for (Report report : reportList) {
			//TODO 合并聚信立和电话邦报告
			JSONObject jxlObj = JSON.parseObject(report.getJxlReport());
			JSONObject dhbObj = JSON.parseObject(report.getDhbReport());
			JSONObject tdObj = JSON.parseObject(report.getTdReport());
			if (jxlObj != null && dhbObj != null) {
				// 更新客户信息
				Customer cus = customerService.selectById(report.getCusId());
				cus.setId(cus.getId());
				cus.setRefreshTimes(cus.getRefreshTimes() + 1);
				cus.setLatestReportKey(report.getReportKey());
				cus.setLatestReportType(cus.getTemReportType()); //信息报告类型：0-基础 1-标准
				cus.setLatestReportStatus(1); //采集成功
				customerService.updateSelectiveById(cus);
				
				// 更新mongoDB报告状态
				report.setToken(report.getToken());
				report.setStatus(1); //采集成功
				
				JSONObject jo = new JSONObject();
				jo.put("cus_basic_info", jxlObj.getJSONObject("cus_basic_info"));
				jo.put("report_contact", jxlObj.getJSONArray("report_contact"));
				jo.put("black_info", jxlObj.getJSONObject("black_info"));
				// 合并聚信立金融信息和电话邦标准版信息
				List<FinancialCallInfo> jxlFciList = JSONUtil.toObjectList(jxlObj.getString("financial_call_info"), FinancialCallInfo.class);
				List<FinancialCallInfo> dhbFciStaList = JSONUtil.toObjectList(dhbObj.getString("financial_call_info_sta"), FinancialCallInfo.class);
				List<FinancialCallInfo> allFciOList = new ArrayList<>();
				allFciOList = jxlFciList;
				allFciOList.addAll(dhbFciStaList);
				jo.put("financial_call_info", allFciOList);
				jo.put("contact_region", jxlObj.getJSONArray("contact_region"));
				jo.put("top10_date_contact", jxlObj.getJSONArray("top10_date_contact"));
				jo.put("top10_times_contact", jxlObj.getJSONArray("top10_times_contact"));
				jo.put("all_contact", jxlObj.getJSONArray("all_contact"));
				jo.put("trip_info", jxlObj.getJSONArray("trip_info"));
				jo.put("cuishou", dhbObj.getJSONObject("cuishou"));
				jo.put("yisicuishou", dhbObj.getJSONObject("yisicuishou"));
				// 解析同盾征信数据
				jo.put("tongdun", tdObj);
				report.setFinalReport(jo.toString());
				reportMongoDBService.updateReportByToken(report);
				
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
	} 
	
	/**
	 * 根据reportKey获取mongodb最终报告数据
	 */
	@Override
	public String selectMdbReport(String reportKey) {
		Report report = reportMongoDBService.selectReportByReportKey(reportKey);
		return report.getFinalReport();
	}
}
