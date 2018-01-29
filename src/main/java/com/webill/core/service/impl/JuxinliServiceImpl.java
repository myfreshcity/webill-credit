package com.webill.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.webill.app.SystemProperty;
import com.webill.app.util.HttpUtils;
import com.webill.app.util.ReportParseUtil;
import com.webill.app.util.StringUtil;
import com.webill.core.model.Customer;
import com.webill.core.model.RedisKeyDto;
import com.webill.core.model.User;
import com.webill.core.model.juxinli.JXLCollectReq;
import com.webill.core.model.juxinli.JXLResetPasswordReq;
import com.webill.core.model.juxinli.JXLResetPasswordResp;
import com.webill.core.model.juxinli.JXLResp;
import com.webill.core.model.juxinli.JXLSubmitFormReq;
import com.webill.core.model.juxinli.JXLSubmitFormResp;
import com.webill.core.model.juxinli.Report;
import com.webill.core.model.report.ContactRegion;
import com.webill.core.model.report.Cuishou;
import com.webill.core.model.report.CusBasicInfo;
import com.webill.core.model.report.FinancialCallInfo;
import com.webill.core.model.report.ReportContact;
import com.webill.core.model.report.TopContact;
import com.webill.core.service.ICustomerService;
import com.webill.core.service.IJuxinliService;
import com.webill.core.service.IReportMongoDBService;
import com.webill.core.service.IUserService;
import com.webill.core.service.RedisService;
import com.webill.framework.common.JSONUtil;

/**
 * @ClassName: JuxinliServiceImpl
 * @Description:
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:17:27
 */
@Service
public class JuxinliServiceImpl implements IJuxinliService {
	private static Log logger = LogFactory.getLog(ProductServiceImpl.class);
	
	public static final String REPORT_LOCK = "__JUXINLI__REPORT__LOG__";
	public static final int max_count = 10;
	public static final long interval = 1000*30; //30s
	private String accessToken = null;
	
	@Autowired
    private SystemProperty constPro;
	@Autowired
	private IReportMongoDBService reportMongoDBService;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private IUserService userService;
	
	/**
	 * 聚信立提交申请表单获取回执信息
	 */
	@Override
	public JXLSubmitFormResp submitForm(JXLSubmitFormReq req, Integer cusId) {
		JXLSubmitFormResp resp = null;
		logger.info("聚信立提交申请表单请求参数："+req.toJsonString());
		try {
			String resJson = HttpUtils.httpPostJsonRequest(constPro.JXL_REQ_URL+"/orgApi/rest/v3/applications/"+ constPro.JXL_ACCOUNT, req.toJsonString());
			if (resJson != null) {
				resp = JXLSubmitFormResp.fromJsonString(resJson);
			} else {
				throw new RuntimeException("提交申请表单请求聚信立响应失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (resp.isSuccess()) {
			// 写mongoDB
			Report report = new Report();
			report.setToken(resp.getToken());
			report.setIdCard(req.getIdCardNum());
			report.setMobile(req.getMobileNo());
			report.setName(req.getName());
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
	 * 聚信立提交数据采集请求,根据返回的processCode，可能会请求多次
	 */
	@Override
	public JXLResp collect(JXLCollectReq req) {
		// 服务密码和最近信息报告类型更新入库
		Customer cus = new Customer();
		cus.setId(req.getCusId());
		cus.setServicePwd(req.getPassword());
		// 临时报告类型入库
		cus.setTemReportType(req.getTemReportType());
		customerService.updateSelectiveById(cus);
		
		// 提交数据采集请求
		JXLResp resp = null;
		try {
			String resJson = HttpUtils.httpPostJsonRequest(constPro.JXL_REQ_URL+"/orgApi/rest/v2/messages/collect/req", JSONUtil.toJSONString(req));
			if (resJson != null) {
				resp = JXLResp.fromJsonString(resJson);
			} else {
				throw new RuntimeException("提交数据源采集请求聚信立失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (resp.isSuccess()) {
			// 更新数据采集时间到数据库
			if (resp.getProcessCode() == 10008) {
				// 修改报告采集状态
				Customer cust = new Customer();
				cust.setId(req.getCusId());
				cust.setLatestReportStatus(0); //采集中
				customerService.updateSelectiveById(cust);
				
				// 更新mongoDB信息
				Report report = new Report();
				report.setToken(req.getToken());
				report.setApplyDate(Calendar.getInstance().getTime());
				report.setReportType(req.getTemReportType()); //信息报告类型：0-基础 1-标准 
				report.setStatus(0); //采集中
				reportMongoDBService.updateReportByToke(report);
				
				// 起一个线程在2分钟后通知获取状态
				new Thread() {
					public void run() {
						try {
							logger.debug("===>等待122秒后去唤醒状态更新线程");
//							sleep(2 * 61 * 1000);
						} catch (Throwable e) {
							logger.error("等待被打断", e);
						}
						logger.debug("===>开始唤醒状态更新线程");
						synchronized (REPORT_LOCK) {
							REPORT_LOCK.notifyAll();
						}
						logger.debug("===>唤醒状态更新线程结束");
					}
				}.start();
			}
		}

		return resp;
	}
	
	/**
	 * 通过token获取报告数据
	 * 申请表单提交时获取的token
	 */
	@Override
	public String getReport(String token, String name, String idCard, String mobile) {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");// 把JDK变成1.6
		
		// 尝试从数据库中获取，如果数据库中有，则直接返回
		Report report = null;
		if (StringUtil.isEmpty(token)) {
			return null;
		}
		if (token != null) {
			// 通过token获取mongodb中的report
			List<Report> reports = reportMongoDBService.findByProp("token", token);
			if (reports != null && reports.size() > 0) {
				report = reports.get(0);
			}
		} 
		if (report == null) {
			throw new RuntimeException("尚未提交申请表单");
		}
		if (report.getStatus() != 1) {
			throw new RuntimeException("报告尚未准备好");
		}
		if (StringUtil.isNotEmpty(report.getFinalReport())) {
			return report.getFinalReport();
		}
		
		token = report.getToken();

		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("client_secret", constPro.JXL_SECRET);
		reqMap.put("access_token", this.getAccessToken());
		reqMap.put("token", token);
		
		try {
			String resJson = HttpUtils.httpGetRequest(constPro.JXL_REQ_URL+"/api/access_report_data_by_token", reqMap);
			logger.info(resJson);
			if (resJson != null) {
				JSONObject json = JSONObject.parseObject(resJson);
				if (json.getBoolean("success")) {
					if (json.containsKey("report_data")) {
						String reportData = json.getJSONObject("report_data").toString();
						// 获取报告更新时间
						String update_time = json.getJSONObject("report_data").getJSONObject("report").getString("update_time");
						// 将报告更新时间更新到数据库中
						List<Report> reports = reportMongoDBService.findByProp("token", token);
						if (reports != null && reports.size() > 0) {
							Customer cus = new Customer();
							cus.setId(Integer.parseInt(reports.get(0).getCusId()));
							cus.setLatestReportTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(update_time));
							customerService.updateSelectiveById(cus);
						}
						
						report = new Report();
						report.setToken(token);
						report.setJxlReport(reportData);
						// 处理聚信立数据到mongoDB
						String jxlReportJson = this.parseJXLReportData(reportData);
						report.setFinalReport(jxlReportJson);
						reportMongoDBService.updateReportByToke(report);
						return jxlReportJson;
					} else {
						throw new RuntimeException("获取运营商原始数据请求返回报文中没有report_data节点！返回报文：" + report);
					}
				} else {
					throw new Exception("获取运营商原始数据请求聚信立失败！返回报文 ：" + report);
				}
			} else {
				throw new RuntimeException("获取报告请求聚信立失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取报告状态并更新数据库
	 */
	@Override
	@Transactional
	public void updateReportStatus(Report report) {
		// 必须是采集中的记录才需要更新状态
		if (report.getStatus() != 0) {
			return;
		}
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("client_secret", constPro.JXL_SECRET);
		reqMap.put("access_token", this.getAccessToken());
		reqMap.put("token", report.getToken());

		try {
			String resJson = HttpUtils.httpGetRequest(constPro.JXL_REQ_URL+"/api/v2/job/access_jobs_status_by_token", reqMap);
			if (resJson != null) {
				JSONObject json = JSONObject.parseObject(resJson);

				if (json.getBoolean("success")) {
					if (json.containsKey("data")) {
						if ("完成".equals(json.getJSONObject("data").getString("status"))) {
							String token = report.getToken();
							// 更新mongoDB报告状态
							report = new Report();
							report.setToken(token);
							report.setStatus(1); //采集成功
							reportMongoDBService.updateReportByToke(report);
							
							// 通过token获取mongodb中的report
							Report reportToken = null;
							List<Report> reports = reportMongoDBService.findByProp("token", token);
							if (reports != null && reports.size() > 0) {
								reportToken = reports.get(0);
							}
							
							// 更新最新报告key编号等信息入库 
							String cusId = reportToken.getCusId();
							Customer cus = customerService.selectById(Integer.parseInt(cusId));
							cus.setId(Integer.parseInt(cusId));
							cus.setRefreshTimes(cus.getRefreshTimes() + 1);
							cus.setLatestReportKey(token);
							cus.setLatestReportType(cus.getTemReportType()); //信息报告类型：0-基础 1-标准
							cus.setLatestReportTime(new Date()); //报告更新时间（先设置为系统）
							cus.setLatestReportStatus(1); //采集成功
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
							
							// 删除redis中的采集记录
							RedisKeyDto redisReq = new RedisKeyDto();
							redisReq.setKeys(constPro.REPORT_KEY + report.getToken());
							redisService.delete(redisReq);
						}
					} else {
						throw new RuntimeException("获取报告状态请求响应中没有data节点！返回报文 ：" + resJson);
					}
				} else {
					throw new RuntimeException("获取运营商原始数据请求聚信立失败！返回报文 ：" + resJson);
				}
			} else {
				throw new RuntimeException("获取运营商原始数据请求聚信立失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 启动守护线程，定期更新报告状态
	 */
	@PostConstruct
	public void daemon() {
		// 守护线程1，定期更新报告状态
		new Thread() {
			public void run() {
				while (true) {
					logger.debug("===>开始获取数据，准备更新报告状态");
					List<Report> reportList = null;
					try {
						// 查询申请时间2分钟之前到现在，100条采集中的报告
//						reportList = juxinliReportDao.selectTop100();
						reportList = reportMongoDBService.findByProp("status", 0);
					} catch (Throwable e) {
						logger.error("查询数据库失败入,5分钟后重试", e);
						try {
//							sleep(5 * 60 * 1000);
						} catch (Throwable e1) {
							logger.error("5分钟等待过程中出现问题", e);
						}
						continue;
					}
					logger.debug("===>成功访问数据库获取到数据");
					// 如果没有数据，则等待1个小时后再去尝试
					// 如果1小时内有新数据进来，会唤醒此处的线程，不用等到1个小时之后
					if (CollectionUtils.isEmpty(reportList)) {
						logger.debug("===>获取到的数据为空，准备等待");
						try {
							synchronized (REPORT_LOCK) {
								REPORT_LOCK.wait(60 * 60 * 1000);
							}
							logger.debug("===>等待时间1小时或中途被唤醒");
						} catch (InterruptedException e) {
							logger.error("停止被打断", e);
						}
						continue;
					}
					logger.info("===>待处理报告数："+reportList.size());
					for (Report report : reportList) {
						RedisKeyDto redisReq = new RedisKeyDto();
						redisReq.setKeys(constPro.REPORT_KEY + report.getToken());
						RedisKeyDto redisData = redisService.redisGet(redisReq);
						
						if(redisData != null){
							String redisValue = redisData.getValues();
							JSONObject jsonObj = JSONObject.parseObject(redisValue);
							long dt = (long) jsonObj.get("nextTryTime");
							Date now = new Date();
							// 采集次数超过10次，停止采集报告
							if(((int)jsonObj.get("tryCount")) > max_count) {
								Report jr = new Report();
								jr.setFinalReport("获取聚信力报告超时，重试次数："+jsonObj.get("tryCount")+"结束时间："+new SimpleDateFormat("yyyyMMddHHmmss").format(now));
								jr.setToken(report.getToken());
								jr.setStatus(2); //采集超时失败
								reportMongoDBService.updateReportByToke(report);
								redisService.delete(redisReq);
								
								// 修改报告采集状态
								List<Report> reports = reportMongoDBService.findByProp("token", report.getToken());
								if (reports != null && reports.size() > 0) {
									Customer cus = new Customer();
									cus.setId(Integer.parseInt(reports.get(0).getCusId()));
									cus.setLatestReportStatus(2); //采集超时失败
									customerService.updateSelectiveById(cus);
								}
								
							}else if (dt > now.getTime()){ // 下一次采集时间大于30s，本次不请求采集
								continue;
							}else {
								int count = (int) jsonObj.get("tryCount");
								jsonObj.put("tryCount", count+1);
								jsonObj.put("nextTryTime", dt+interval);
								redisReq.setValues(jsonObj.toJSONString());
								redisService.addData(redisReq);
							}
						}else {
							Date curDate = new Date();
							JSONObject jsonObj = new JSONObject();
							jsonObj.put("tryCount", 1); //采集次数
							jsonObj.put("nextTryTime", curDate.getTime()+interval); //下一次采集的时间
							
							redisReq.setValues(jsonObj.toJSONString());
							redisService.addData(redisReq);
						}
						try {
							updateReportStatus(report);
						} catch (Throwable e) {
							logger.error("更新状态失败", e);
						}
					}
				}
			}
		}.start();

		// 启动守护线程，每小时清除已过期的未提交采集请求的数据
		/*new Thread() {
			public void run() {
				reportMongoDBService.deleteExpire();
				try {
					sleep(60 * 60 * 1000);
				} catch (Exception e) {
					logger.error("停止被打断", e);
				}
			}
		}.start();*/
	}
	
	/**
	 * 获取access_token，如果为空，则发送请求到聚信立获取永久有效期的access_token并保存在accessToken变量中，
	 * 下次使用不需要再次请求
	 */
	@Override
	public String getAccessToken() {
		if (accessToken != null) {
			return accessToken;
		}
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("org_name", constPro.JXL_ACCOUNT);
		reqMap.put("client_secret", constPro.JXL_SECRET);
		reqMap.put("hours", "per"); //永久性可用
		
		try {
			String resJson = HttpUtils.httpGetRequest(constPro.JXL_REQ_URL+"/api/v2/access_report_token", reqMap);
			if (resJson != null) {
				JSONObject json = JSONObject.parseObject(resJson);
				int code = json.getIntValue("code");
				if (code != 200) {
					throw new RuntimeException("获取access-token返回code:" + code);
				}
				accessToken = json.getString("access_token");
			} else {
				throw new RuntimeException("获取access-token请求聚信立失败!");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return accessToken;
	}
	
	/**
	 * 提交重置密码请求
	 */
	@Override
	public JXLResetPasswordResp resetPassword(JXLResetPasswordReq map) {
		JXLResetPasswordResp rsp = null;
		try {
			logger.info("提交重置密码请求参数，request:\t" + JSONUtil.toJSONString(map));
			String resJson = HttpUtils.httpPostJsonRequest(constPro.JXL_REQ_URL+"/orgApi/rest/v2/messages/reset/req", JSONUtil.toJSONString(map));

			if (resJson != null) {
				rsp = JSONUtil.toObject(resJson, JXLResetPasswordResp.class);
				logger.info("提交重置密码请求响应报文："+JSONUtil.toJSONString(rsp));
			} else {
				throw new RuntimeException("提交申请表单请求聚信立失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}
	
	/**
	 * 获取重置密码响应信息
	 */
	@Override
	public JXLResetPasswordResp resetPasswordResp(JXLResetPasswordReq map) {
		JXLResetPasswordResp rsp = null;
		try {
			String resJson = HttpUtils.httpPostJsonRequest(constPro.JXL_REQ_URL+"/orgApi/rest/messages/reset/resp", JSONUtil.toJSONString(map));
			if (resJson != null) {
				rsp = JSONUtil.toObject(resJson, JXLResetPasswordResp.class);
				logger.info("提交重置密码请求响应报文："+JSONUtil.toJSONString(rsp));
			} else {
				throw new RuntimeException("提交申请表单请求聚信立失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 解析聚信立报告数据
	 */
	@Override
	public String parseJXLReportData(String jxlReportJson){
		JSONObject jo = new JSONObject();
		// 聚信立-解析客户基本信息
		CusBasicInfo cusBasicInfo = ReportParseUtil.parseCusBasicInfo(jxlReportJson);
		jo.put("cus_basic_info", cusBasicInfo);
		// 聚信立-解析联系人信息
		List<ReportContact> rcList = ReportParseUtil.parseReportContact(jxlReportJson);
		jo.put("report_contact", rcList);
		// 聚信立-解析用户黑名单信息
//		BlackInfo blackInfo = ReportParseUtil.parseBlackInfo(jxlReportJson);
		// 聚信立-解析金融类通话信息
		List<FinancialCallInfo> fciList = ReportParseUtil.parseFinancialCallInfo(jxlReportJson);
		jo.put("financial_call_info", fciList);
		// 聚信立-解析联系人区域信息
		List<ContactRegion> crList = ReportParseUtil.parseContactRegion(jxlReportJson);
		jo.put("contact_region", crList);
		// 聚信立-解析长时间联系人（Top10）
		List<TopContact> dateTopConList = ReportParseUtil.parseDateTopContact(jxlReportJson);
		jo.put("top10_date_contact", dateTopConList);
		// 聚信立-解析高频联系人（Top10）
		List<TopContact> timesTopConList = ReportParseUtil.parseTimesTopContact(jxlReportJson);
		jo.put("top10_times_contact", timesTopConList);
		// 聚信立-解析出行数据
//		List<TripInfo> tripInfoList = ReportParseUtil.parseTripInfo(jxlReportJson);
		
		return jo.toJSONString();
	}
	
	/**
	 * 解析电话邦报告数据
	 */
	@Override
	public String parseDHBReportData(String dhbReportJson){
		JSONObject jo = new JSONObject();
		// 电话邦-解析金融类基础版通话信息
		List<FinancialCallInfo> fciList = ReportParseUtil.parseDHBFinBasCallInfo(dhbReportJson);
		jo.put("financial_call_info", fciList);
		// 电话邦-解析联系人区域信息
		List<ContactRegion> crList = ReportParseUtil.parseDHBContactRegion(dhbReportJson);
		jo.put("contact_region", crList);
		// 电话邦-解析长时间联系人（Top10）
		List<TopContact> dateTop10Con = ReportParseUtil.parseDHBDateTopContact(dhbReportJson);
		jo.put("top10_date_contact", dateTop10Con);
		// 电话邦-解析高频联系人（Top10）
		List<TopContact> timesTop10Con = ReportParseUtil.parseDHBTimesTopContact(dhbReportJson);
		jo.put("top10_times_contact", timesTop10Con);
		// 电话邦-解析催收信息数据节点
		Cuishou cs = ReportParseUtil.parseDHBCuishou(dhbReportJson);
		jo.put("cuishou", cs);
		// 电话邦-疑似催收信息数据节点
		Cuishou yscs = ReportParseUtil.parseDHBYisiCuishou(dhbReportJson);
		jo.put("yisicuishou", yscs);
		
		return jo.toJSONString();
	}
	
	/**
	 * 通过token获取运营商原始数据
	 * 申请表单提交时获取的token
	 */
	@Override
	public String getMobileRawData(String token) {

		// 通过token获取mongodb中的report
		Report report = null;
		List<Report> reports = reportMongoDBService.findByProp("token", token);
		if (reports != null && reports.size() > 0) {
			report = reports.get(0);
		}
		
		if (report == null) {
			throw new RuntimeException("尚未提交申请表单");
		}
		if (!StringUtil.isEmpty(report.getMobileRaw())) {
			return report.getMobileRaw();
		}

		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("client_secret", constPro.JXL_SECRET);
		reqMap.put("access_token", this.getAccessToken());
		reqMap.put("token", token);
		try {
			String resJson = HttpUtils.httpGetRequest(constPro.JXL_REQ_URL+"/api/access_raw_data_by_token", reqMap);
			if (resJson != null) {
				JSONObject json = JSONObject.parseObject(resJson);
				if (json.getBoolean("success")) {
					if (json.containsKey("raw_data")) {
						String rawData = json.getJSONObject("raw_data").toString();
						// 将获取到的数据更新到数据库中
						report = new Report();
						report.setToken(token);
						report.setMobileRaw(rawData);
						reportMongoDBService.updateReportByToke(report);
						return rawData;
					} else {
						throw new RuntimeException("获取运营商原始数据请求返回报文中没有raw_data节点！返回报文：" + resJson);
					}
				} else {
					throw new RuntimeException("获取运营商原始数据请求聚信立失败！返回报文 ：" + resJson);
				}

			} else {
				throw new RuntimeException("获取运营商原始数据请求聚信立失败!");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
