package com.webill.core.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.webill.app.SystemProperty;
import com.webill.app.util.HttpUtils;
import com.webill.app.util.StringUtil;
import com.webill.core.model.juxinli.JXLCollectReq;
import com.webill.core.model.juxinli.JXLResp;
import com.webill.core.model.juxinli.JXLSubmitFormReq;
import com.webill.core.model.juxinli.JXLSubmitFormResp;
import com.webill.core.model.juxinli.Report;
import com.webill.core.service.IJuxinliService;
import com.webill.core.service.IReportMongoDBService;

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
	public static final long interval = 1000*30;
	private static final String CACHE_NAME = "juxinliCache";
	private String accessToken = null;
	
	@Autowired
    private SystemProperty constPro;
	@Autowired
	private IReportMongoDBService reportMongoDBService;
	
	/**
	 * 聚信立提交申请表单获取回执信息
	 */
	@Override
	public JXLSubmitFormResp submitForm(JXLSubmitFormReq req) {
		JXLSubmitFormResp resp = null;

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
			// 写数据库
			Report report = new Report();
			// report.setApplyDate(Calendar.getInstance().getTime());
			report.setApplySn(req.getApplySn());
			report.setToken(resp.getToken());
			report.setIdCard(req.getIdCardNum());
			report.setMobile(req.getMobileNo());
			report.setName(req.getName());
			report.setApplyDate(Calendar.getInstance().getTime());
			report.setStatus(-1); // 准备抓取
			reportMongoDBService.save(report);
		}
		return resp;
	}
	
	/**
	 * 聚信立提交数据采集请求,根据返回的processCode，可能会请求多次
	 */
	@Override
	public JXLResp collect(JXLCollectReq req) {
		JXLResp resp = null;
		
		JSONObject jo = new JSONObject();
		jo.put("token", "f711c4f6b383499d971c8543890e421c");
		jo.put("account", "15121193141");
		jo.put("password", "895623");
		jo.put("website", "chinamobilesh");
		String json = jo.toJSONString();
		try {
			String resJson = HttpUtils.httpPostJsonRequest(constPro.JXL_REQ_URL+"/orgApi/rest/v2/messages/collect/req", json);
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
				Report report = new Report();
				report.setToken(req.getToken());
				report.setApplyDate(Calendar.getInstance().getTime());
				report.setStatus(0);
				reportMongoDBService.update(report);
//				juxinliReportDao.updateByToken(report);
				
				// 起一个线程在2分钟后通知获取状态
				new Thread() {
					public void run() {
						try {
							logger.debug("+++++++++++++++++++++++++++++++等待122秒后去唤醒状态更新线程");
							sleep(2 * 61 * 1000);
						} catch (Throwable e) {
							logger.error("等待被打断", e);
						}
						logger.debug("+++++++++++++++++++++++++++++++开始唤醒状态更新线程");
						synchronized (REPORT_LOCK) {
							REPORT_LOCK.notifyAll();
						}
						logger.debug("+++++++++++++++++++++++++++++++唤醒状态更新线程结束");
					}
				}.start();
			}
		}

		return resp;
	}
	
	/**
	 * 获取access_token，如果为空，则发送请求到聚信立获取永久有效期的access_token并保存在accessToken变量中，
	 * 下次使用不需要再次请求
	 */
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
	 * 通过token获取报告数据
	 * 申请表单提交时获取的token
	 */
	public String getReport(String token,String applySn, String name, String idCard, String mobile) {
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
//			report = juxinliReportDao.selectByToken(token);
		} /*else if(applySn!=null){
			Report param = new Report();
			param.setApplySn(applySn);
//			report = juxinliReportDao.selectNearestByApplySn(param);
		}*/
		
		/*if(report == null && (!StringUtil.isEmpty(name) && !StringUtil.isEmpty(idCard) && !StringUtil.isEmpty(mobile))){
			Report param = new Report();
			param.setName(name);
			param.setMobile(mobile);
			param.setIdCard(idCard);
			report = juxinliReportDao.selectNearestByCust(param);
		}*/

		if (report == null) {
			throw new RuntimeException("尚未提交申请表单");
		}
		if (!StringUtil.isEmpty(report.getFinalReport())) {
			return report.getFinalReport();
		}
		if (report.getStatus() != 1) {
			throw new RuntimeException("报告尚未准备好");
		}
		if(report.getFinalReport()!=null) {
			return report.getFinalReport();
		}
		
		token = report.getToken();

		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("client_secret", constPro.JXL_SECRET);
		reqMap.put("access_token", this.getAccessToken());
		reqMap.put("token", token);
		
		try {
			String resJson = HttpUtils.httpGetRequest(constPro.JXL_REQ_URL+"/api/access_report_data_by_token", reqMap);
			if (resJson != null) {
				JSONObject json = JSONObject.parseObject(resJson);
				if (json.getBoolean("success")) {
					if (json.containsKey("report_data")) {
						String reportData = json.getJSONObject("report_data").toString();
						// 将获取到的数据更新到数据库中
						report = new Report();
						report.setToken(token);
						report.setFinalReport(reportData);
						reportMongoDBService.updateReportByToke(report);
//						juxinliReportDao.updateByToken(report);
						return reportData;
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
	
	/*@Async
	public void getReportAsync(String token) {
		//同步报告到本地
		JSONObject reqObject = new JSONObject();
		reqObject.put("token", token);
		try{
			String result = getReport(token,null, null, null, null);
			JSONObject json = JSONObject.parseObject(result);
			Report report = juxinliReportDao.selectByToken(token);
			if(report.getApplySn().startsWith("APP")){
				JSONArray jsonArray = json.getJSONArray("application_check");
				JSONArray contacts = new JSONArray();
				for (int index = 0; index < jsonArray.size(); index++) {
					JSONObject appJson = jsonArray.getJSONObject(index);
					if(appJson.getString("app_point")!=null&&"contact".equals(appJson.getString("app_point"))){
						contacts.add(appJson.getJSONObject("check_points"));
					}
				}
				
				reqObject.put("contacts", contacts);
			}
		}catch(Exception e){
			logger.error("聚信立报告获取异常：",e);
		}
		
		RestTemplate retTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
		HttpEntity formEntity = new HttpEntity(reqObject.toString(),headers);
		retTemplate.postForObject(appResultNotifyUrl, formEntity, String.class);
		
	} */
	
	/**
	 * 通过token获取运营商原始数据
	 * 申请表单提交时获取的token
	 */
	public String getMobileRawData(String token) {

		// 尝试从数据库中获取，如果数据库中有，则直接返回
//		Report report = juxinliReportDao.selectByToken(token);
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
//						juxinliReportDao.updateByToken(report);
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
	
	/**
	 * 获取报告状态并更新数据库
	 */
	public void updateReportStatus(Report report) {
		// 必须是抓取中的记录才需要更新状态
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
							report = new Report();
							report.setToken(token);
							report.setStatus(1);
							/*juxinliReportDao.updateByToken(report);
							SpringUtils.getBean(AsyncProcessor.class).excute(this.getClass(), "getReportAsync", token);
							CacheManager manager = SpringUtils.getBean(CacheManager.class);
							Cache cache = manager.getCache(CACHE_NAME);
							cache.remove(report.getToken());*/
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
	 * 提交重置密码请求
	 *//*
	public JXLResetPasswordResp resetPassword(JXLResetPasswordReq map) {
		JXLResetPasswordResp rsp = null;
		JSONObject parameter = JSONObject.fromObject(map);
		String rspStr = "{}";
		try {
			System.out.println("request:\t" + parameter.toString());
			HttpResp httpRes = http.doPostJsonString(", parameter.toString(), "utf-8");
			HttpUtils.httpPostRequest(constPro.JXL_REQ_URL+"/orgApi/rest/v2/messages/reset/req", params);

			if (httpRes != null && httpRes.getStatusCode() == 200) {
				rspStr = httpRes.getText("utf-8");
				JSONObject json = JSONObject.fromObject(rspStr);
				rsp = (JXLResetPasswordResp) JSONObject.toBean(json, JXLResetPasswordResp.class);
				System.out.println(rspStr);
			} else {
				throw new RuntimeException("提交申请表单请求聚信立失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return rsp;
	}

	public JuxinliResetPasswordResp resetPasswordResp(JuxinliResetPasswordReq map) {
		JuxinliResetPasswordResp rsp = null;
		JSONObject parameter = new JSONObject();
		parameter.put("token", map.getToken());
		parameter.put("account", map.getAccount());
		parameter.put("password", map.getPassword());
		parameter.put("captcha", map.getCaptcha());
		parameter.put("type", map.getType());
		parameter.put("websit", map.getWebsite());
		try {
			HttpResp httpRes = http.doPostJsonString(resetPasswordRespUrl, parameter.toString(), "utf-8");
			if (httpRes != null && httpRes.getStatusCode() == 200) {
				String rspStr = "{}";
				rspStr = httpRes.getText("utf-8");
				JSONObject json = JSONObject.fromObject(rspStr);
				rsp = (JuxinliResetPasswordResp) JSONObject.toBean(json, JuxinliResetPasswordResp.class);
				System.out.println(rspStr);
			} else {
				throw new RuntimeException(
						"提交申请表单请求聚信立失败！失败代码：" + ((httpRes == null) ? "响应为空" : httpRes.getStatusCode()));
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return rsp;
	}

	public Map<String,Object> validReportList(String name,String idCard){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Searchable search = new Searchable();
		search.addSearchParam("name_eq", name);
		search.addSearchParam("idcard_eq", idCard);
		search.addSearchParam("status_eq", "1");
		//排除报告数据，数据内容太大，影响性能
		List<JuxinliReport> reportList = juxinliReportDao.selectListIgnoreData(search, new PageBounds());
		dataMap.put("success", true);
		dataMap.put("reports", reportList);
		return dataMap;
	}
	
	*//**
	 * 启动守护线程，定期更新报告状态
	 *//*
	@PostConstruct
	public void daemon() {

		// 守护线程1，定期更新报告状态
		new Thread() {
			public void run() {
				while (true) {
					logger.debug("+++++++++++++++++++++++++++++++开始获取数据，准备更新报告状态");
					List<JuxinliReport> reportList = null;
					try {
						reportList = juxinliReportDao.selectTop100();
					} catch (Throwable e) {
						logger.error("查询数据库失败入,5分钟后重试", e);
						try {
							sleep(5 * 60 * 1000);
						} catch (Throwable e1) {
							logger.error("5分钟等待过程中出现问题", e);
						}
						continue;
					}
					logger.debug("+++++++++++++++++++++++++++++++成功访问数据库获取到数据");
					// 如果没有数据，则等待1个小时后再去尝试
					// 如果1小时内有新数据进来，会唤醒此处的线程，不用等到1个小时之后
					if (CollectionUtils.isEmpty(reportList)) {
						logger.debug("+++++++++++++++++++++++++++++++获取到的数据为空，准备等待");
						try {
							synchronized (REPORT_LOCK) {
								REPORT_LOCK.wait(60 * 60 * 1000);
							}
							logger.debug("+++++++++++++++++++++++++++++++等待时间1小时或中途被唤醒");
						} catch (InterruptedException e) {
							logger.error("停止被打断", e);
						}
						continue;
					}

					logger.debug("+++++++++++++++++++++++++++++++待处理报告数：{}", reportList.size());
					for (JuxinliReport report : reportList) {
						CacheManager manager = SpringUtils.getBean(CacheManager.class);
						Cache cache = manager.getCache(CACHE_NAME);
						Element ele = cache.get(report.getToken());
						if(ele!=null){
							JSONObject jsonObj = (JSONObject) ele.getObjectValue();
							long dt = (long) jsonObj.get("nextTryTime");
							Date now = new Date();
							if(((int)jsonObj.get("tryCount"))>max_count){
								JuxinliReport jr = new JuxinliReport();
								jr.setFinalReport("获取聚信力报告超时，重试次数："+jsonObj.get("tryCount")+"结束时间："+new SimpleDateFormat("yyyyMMddHHmmss").format(now));
								jr.setToken(report.getToken());
								jr.setStatus(2);
								juxinliReportDao.updateByToken(jr);
								cache.remove(report.getToken());
							}else if(dt>now.getTime()){
								continue;
							}else{
								int count = (int) jsonObj.get("tryCount");
								jsonObj.put("tryCount", count+1);
								jsonObj.put("nextTryTime", dt+interval);
							}
						}else{
							Date curDate = new Date();
							JSONObject jsonObj = new JSONObject();
							jsonObj.put("tryCount", 1);
							jsonObj.put("nextTryTime", curDate.getTime()+interval);
							cache.put(new Element(report.getToken(), jsonObj));
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
		new Thread() {
			public void run() {
				juxinliReportDao.deleteExpire();
				try {
					sleep(60 * 60 * 1000);
				} catch (Exception e) {
					logger.error("停止被打断", e);
				}
			}
		}.start();
	}*/
}
