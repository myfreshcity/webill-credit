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
import com.webill.app.util.TransNoUtil;
import com.webill.core.model.Customer;
import com.webill.core.model.RedisKeyDto;
import com.webill.core.model.User;
import com.webill.core.model.dianhuabang.DHBGetLoginReq;
import com.webill.core.model.dianhuabang.DHBGetLoginResp;
import com.webill.core.model.dianhuabang.DHBLoginReq;
import com.webill.core.model.dianhuabang.DHBLoginResp;
import com.webill.core.model.dianhuabang.DianHuaBangErrorCode;
import com.webill.core.model.juxinli.JXLCollectReq;
import com.webill.core.model.juxinli.JXLResp;
import com.webill.core.model.juxinli.JXLSubmitFormReq;
import com.webill.core.model.juxinli.JXLSubmitFormResp;
import com.webill.core.model.juxinli.Report;
import com.webill.core.model.report.FinancialCallInfo;
import com.webill.core.service.ICustomerService;
import com.webill.core.service.IJuxinliService;
import com.webill.core.service.IJxlDhbService;
import com.webill.core.service.IReportMongoDBService;
import com.webill.core.service.IUserService;
import com.webill.core.service.RedisService;
import com.webill.framework.common.JSONUtil;

/** 
 * @ClassName: JxlDhbServcieImpl 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月31日 下午7:00:18 
 */
@Service
public class JxlDhbServcieImpl implements IJxlDhbService {
	private static Log logger = LogFactory.getLog(JxlDhbServcieImpl.class);
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
	private IUserService userService;
	
	public static final String REPORT_LOCK = "__JUXINLI__REPORT__LOG__";
	public static final int max_count = 10;
	public static final long interval = 1000*30; //30s
	private String jxlToken = null;

	
	/**
	 * 串行处理3
	 * 聚信立==>提交申请表单获取回执信息
	 */
	@Override
	@Transactional
	public JXLSubmitFormResp jxlSubmitForm(JXLSubmitFormReq jxlReq, Integer cusId) {
		JXLSubmitFormResp jxlResp = null;
		
		try {
			//TODO 聚信立请求
			logger.info("聚信立提交申请表单请求参数-request："+jxlReq.toJsonString());
			String jxlResJson = HttpUtils.httpPostJsonRequest(constPro.JXL_REQ_URL+"/orgApi/rest/v3/applications/"+ constPro.JXL_ACCOUNT, jxlReq.toJsonString());
			logger.info("聚信立提交申请表单响应数据-response："+jxlResJson);
			if (jxlResJson != null) {
				jxlResp = JXLSubmitFormResp.fromJsonString(jxlResJson);
			} else {
				throw new RuntimeException("提交申请表单请求聚信立响应失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return jxlResp;
	}
	
	/**
	 * 聚信立==>提交申请表单获取回执信息
	 * 电话邦==>获取登录方式（获取会话标识sid）
	 */
	@Override
	@Transactional
	public Object submitFormAndGetSid(JXLSubmitFormReq jxlReq, DHBGetLoginReq dhbReq, Integer cusId) {
		JXLSubmitFormResp jxlResp = null;
		DHBGetLoginResp dhbResp = null;
		
		try {
			//TODO 聚信立请求
			logger.info("聚信立提交申请表单请求参数-request："+jxlReq.toJsonString());
			String jxlResJson = HttpUtils.httpPostJsonRequest(constPro.JXL_REQ_URL+"/orgApi/rest/v3/applications/"+ constPro.JXL_ACCOUNT, jxlReq.toJsonString());
			logger.info("聚信立提交申请表单响应数据-response："+jxlResJson);
			if (jxlResJson != null) {
				jxlResp = JXLSubmitFormResp.fromJsonString(jxlResJson);
			} else {
				throw new RuntimeException("提交申请表单请求聚信立响应失败！");
			}
			
			//TODO 电话邦请求
			logger.info("电话邦获取登录方式请求参数-request："+dhbReq.toJsonString());
			String dhbResJson = HttpUtils.httpPostJsonRequest(constPro.DHB_REQ_URL+"/calls/flow?token="+this.getDhbToken(), dhbReq.toJsonString());
			logger.info("电话邦获取登录方式响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				//F4000(4000, "无效的token")
				dhbResp = DHBGetLoginResp.fromJsonString(dhbResJson);
			} else {
				throw new RuntimeException("获取登录方式sid失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (jxlResp.isSuccess() && dhbResp.getStatus() == 0) {
			// 写mongoDB
			Report report = new Report();
			report.setReportKey(TransNoUtil.getReportKey());
			report.setToken(jxlResp.getToken());
			report.setSid(dhbResp.getSid());
			report.setCusId(cusId.toString());
			report.setJxlStatus(-1); //准备采集
			report.setDhbStatus(-1); //准备采集
			report.setStatus(-1); //准备采集
			report.setIdCard(jxlReq.getIdCardNum());
			report.setMobile(jxlReq.getMobileNo());
			report.setName(jxlReq.getName());
			report.setApplyDate(Calendar.getInstance().getTime()); //请求时间
			reportMongoDBService.save(report);
			
			// 修改报告为准备采集状态
			Customer cus = new Customer();
			cus.setId(cusId);
			cus.setLatestReportStatus(-1); //准备采集
			customerService.updateSelectiveById(cus);
		}
		JSONObject jo = new JSONObject();
		jo.put("jxlSubmitForm", jxlResp);
		jo.put("dhbGetLogin", dhbResp);
		return jo;
	}
	
	/**
	 * 聚信立==>提交数据采集请求,根据返回的processCode，可能会请求多次
	 */
	@Override
	@Transactional
	public Object jxlOrDhbCollect(JXLCollectReq jxlReq, DHBLoginReq dhbReq, Customer cus) {
		Customer cust = new Customer();
		cust.setId(cus.getId());
		// 服务密码更新入库
		cust.setServicePwd(jxlReq.getPassword());
		// 临时报告类型更新入库（可能未采集成功）
		cust.setTemReportType(cus.getTemReportType());
		customerService.updateSelectiveById(cust);
		
		// 提交数据采集请求
		JXLResp jxlResp = null;
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

		if (dhbResp.getStatus() == 0) {
			// 修改报告采集状态
			Customer custo = new Customer();
			custo.setId(cus.getId());
			custo.setLatestReportStatus(0); //采集中
			customerService.updateSelectiveById(custo);
			
			// 更新mongoDB信息
			Report report = new Report();
			report.setSid(dhbReq.getSid());
			report.setApplyDate(Calendar.getInstance().getTime());
			report.setReportType(cus.getTemReportType()); //信息报告类型：0-基础 1-标准 
			report.setDhbStatus(0); //采集中
			report.setStatus(0); //采集中
			reportMongoDBService.updateReportBySid(report);
			
		}else if (dhbResp.getStatus() == 3100){ // 查询失败 
			//TODO 聚信立请求
			logger.info("聚信立提交数据采集请求参数-request："+JSONUtil.toJSONString(jxlReq));
			String jxlResJson = HttpUtils.httpPostJsonRequest(constPro.JXL_REQ_URL+"/orgApi/rest/v2/messages/collect/req", JSONUtil.toJSONString(jxlReq));
			logger.info("聚信立提交数据采集响应数据-response："+jxlResJson);
			if (jxlResJson != null) {
				jxlResp = JXLResp.fromJsonString(jxlResJson);
			} else {
				throw new RuntimeException("提交数据源采集请求聚信立失败！");
			}
			
			if (jxlResp.isSuccess()) {
				//TODO 聚信立响应
				// 更新数据采集时间到数据库
				if (jxlResp.getProcessCode() == 10008) { //10008：开始采集行为数据
					// 修改报告采集状态
					Customer custo = new Customer();
					custo.setId(cus.getId());
					custo.setLatestReportStatus(0); //采集中
					customerService.updateSelectiveById(custo);
					
					// 更新mongoDB信息
					Report report = new Report();
					report.setToken(jxlReq.getToken());
					report.setApplyDate(Calendar.getInstance().getTime());
					report.setReportType(cus.getTemReportType()); //信息报告类型：0-基础 1-标准 
					report.setJxlStatus(0); //采集中
					report.setStatus(0); //采集中
					reportMongoDBService.updateReportByToken(report);
					
					// 起一个线程在2分钟后通知获取状态
					new Thread() {
						public void run() {
							try {
								logger.debug("===>等待122秒后去唤醒状态更新线程");
//							sleep(2 * 61 * 1000);
								sleep(60 * 1000);
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
					
				}else if (jxlResp.getProcessCode() == 30000) { // 错误信息：网络异常、运营商异常或当天无法下发短信验证码所导致的无法登陆【建议结束流程】
					// 电话邦处理
				}
			}
		}
		

		JSONObject jo = new JSONObject();
		jo.put("jxlCollect", jxlResp);
		jo.put("dhbCollect", dhbResp);
		return jo;
	}
	
	/**
	 * 聚信立==>提交数据采集请求,根据返回的processCode，可能会请求多次
	 */
	@Override
	@Transactional
	public Object jxlAndDhbcollect(JXLCollectReq jxlReq, DHBLoginReq dhbReq, Customer cus) {
		Customer cust = new Customer();
		cust.setId(cus.getId());
		// 服务密码更新入库
		cust.setServicePwd(jxlReq.getPassword());
		// 临时报告类型更新入库
		cust.setTemReportType(cus.getTemReportType());
		customerService.updateSelectiveById(cust);
		
		// 提交数据采集请求
		JXLResp jxlResp = null;
		DHBLoginResp dhbResp = null;
		try {
			//TODO 电话邦请求
			logger.info("电话邦登录请求参数-request："+dhbReq.toJsonString());
			String dhbResJson = HttpUtils.httpPostJsonRequest(constPro.DHB_REQ_URL+"/calls/login?token="+this.getDhbToken(), dhbReq.toJsonString());
			logger.info("电话邦登录响应数据-response："+dhbResJson);
			if(dhbResJson != null) {
				dhbResp = DHBLoginResp.fromJsonString(dhbResJson);
			} else {
				throw new RuntimeException("登录请求失败");
			}
			
			if (dhbResp.getStatus() == 0) {// 电话邦采集成功，再去采集聚信立
				//TODO 聚信立请求
				logger.info("聚信立提交数据采集请求参数-request："+JSONUtil.toJSONString(jxlReq));
				String jxlResJson = HttpUtils.httpPostJsonRequest(constPro.JXL_REQ_URL+"/orgApi/rest/v2/messages/collect/req", JSONUtil.toJSONString(jxlReq));
				logger.info("聚信立提交数据采集响应数据-response："+jxlResJson);
				if (jxlResJson != null) {
					jxlResp = JXLResp.fromJsonString(jxlResJson);
				} else {
					throw new RuntimeException("提交数据源采集请求聚信立失败！");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (jxlResp.isSuccess() && dhbResp.getStatus() == 0) {
			//TODO 聚信立响应
			// 更新数据采集时间到数据库
			if (jxlResp.getProcessCode() == 10008) { //10008：开始采集行为数据
				// 修改报告采集状态
				Customer custo = new Customer();
				custo.setId(cus.getId());
				custo.setLatestReportStatus(0); //采集中
				customerService.updateSelectiveById(custo);
				
				// 更新mongoDB信息
				Report report = new Report();
				report.setToken(jxlReq.getToken());
				report.setApplyDate(Calendar.getInstance().getTime());
				report.setReportType(cus.getTemReportType()); //信息报告类型：0-基础 1-标准 
				report.setJxlStatus(0); //采集中
				report.setDhbStatus(0); //采集中
				report.setStatus(0); //采集中
				reportMongoDBService.updateReportByToken(report);
				
				// 起一个线程在2分钟后通知获取状态
				new Thread() {
					public void run() {
						try {
							logger.debug("===>等待122秒后去唤醒状态更新线程");
//							sleep(2 * 61 * 1000);
							sleep(60 * 1000);
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
			//TODO 电话邦响应（回调获取报告数据）
		}

		JSONObject jo = new JSONObject();
		jo.put("jxlCollect", jxlResp);
		jo.put("dhbCollect", dhbResp);
		return jo;
	}
	
	/**
	 * 串行采集数据1：电话邦
	 * 聚信立==>提交数据采集请求,根据返回的processCode，可能会请求多次
	 */
//	@Override
	@Transactional
	public Object dhbcollect(DHBLoginReq dhbReq, Customer cus) {
		Customer cust = new Customer();
		cust.setId(cus.getId());
		// 服务密码更新入库
		cust.setServicePwd(dhbReq.getPinPwd());
		// 临时报告类型更新入库
		cust.setTemReportType(cus.getTemReportType());
		customerService.updateSelectiveById(cust);
		
		// 提交数据采集请求
		DHBLoginResp dhbResp = null;
		try {
			//TODO 电话邦请求
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
		
		JSONObject jo = new JSONObject();
		jo.put("dhbCollect", dhbResp);
		return jo;
	}
	
	/**
	 * 串行采集数据2：聚信立
	 * 聚信立==>提交数据采集请求,根据返回的processCode，可能会请求多次
	 */
//	@Override
	@Transactional
	public Object jxlcollect(JXLCollectReq jxlReq, Customer cus) {
		Customer cust = customerService.selectById(cus.getId());
		jxlReq.setPassword(cust.getServicePwd());
		
		// 提交数据采集请求
		JXLResp jxlResp = null;
		DHBLoginResp dhbResp = null;
		try {
			//TODO 聚信立请求
			logger.info("聚信立提交数据采集请求参数-request："+JSONUtil.toJSONString(jxlReq));
			String jxlResJson = HttpUtils.httpPostJsonRequest(constPro.JXL_REQ_URL+"/orgApi/rest/v2/messages/collect/req", JSONUtil.toJSONString(jxlReq));
			logger.info("聚信立提交数据采集响应数据-response："+jxlResJson);
			if (jxlResJson != null) {
				jxlResp = JXLResp.fromJsonString(jxlResJson);
			} else {
				throw new RuntimeException("提交数据源采集请求聚信立失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (jxlResp.isSuccess()) {
			//TODO 聚信立响应
			// 更新数据采集时间到数据库
			if (jxlResp.getProcessCode() == 10008) { //10008：开始采集行为数据
				// 修改报告采集状态
				Customer custo = new Customer();
				custo.setId(cus.getId());
				custo.setLatestReportStatus(0); //采集中
				customerService.updateSelectiveById(custo);
				
				// 更新mongoDB信息
				Report report = new Report();
				report.setToken(jxlReq.getToken());
				report.setApplyDate(Calendar.getInstance().getTime());
				report.setReportType(cus.getTemReportType()); //信息报告类型：0-基础 1-标准 
				report.setJxlStatus(0); //采集中
				report.setDhbStatus(0); //采集中
				report.setStatus(0); //采集中
				reportMongoDBService.updateReportByToken(report);
				
				// 起一个线程在2分钟后通知获取状态
				new Thread() {
					public void run() {
						try {
							logger.debug("===>等待122秒后去唤醒状态更新线程");
//							sleep(2 * 61 * 1000);
							sleep(60 * 1000);
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
		}else { // 请求超时,请重新认证(token过期)
			JXLSubmitFormReq sfReq = new JXLSubmitFormReq();
			JXLSubmitFormResp sfResp = this.jxlSubmitForm(sfReq, cus.getId());
		}

		JSONObject jo = new JSONObject();
		jo.put("jxlCollect", jxlResp);
		jo.put("dhbCollect", dhbResp);
		return jo;
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
	 * 聚信立==>根据token获取报告数据，申请表单提交时获取的token
	 */
	@Override
	@Transactional
	public String updateJxlReport(String token) {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");// 把JDK变成1.6
		logger.info("聚信立报告的token:"+token);
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("client_secret", constPro.JXL_SECRET);
		reqMap.put("access_token", this.getJxlToken());
		reqMap.put("token", token);
		
		try {
			String resJson = HttpUtils.httpGetRequest(constPro.JXL_REQ_URL+"/api/access_report_data_by_token", reqMap);
			if (resJson != null) {
				logger.info("聚信立报告信息==>"+resJson);
				JSONObject json = JSONObject.parseObject(resJson);
				if (json.getBoolean("success")) {
					if (json.containsKey("report_data")) {
						// 获取报告数据
						String reportData = json.getJSONObject("report_data").toString();
						// 获取报告更新时间
						String update_time = json.getJSONObject("report_data").getJSONObject("report").getString("update_time");
						
						Report mdbReport = reportMongoDBService.selectReportByToken(token);
						Customer cus = customerService.selectById(Integer.parseInt(mdbReport.getCusId()));
						//TODO 将报告更新时间更新到数据库中
						if (update_time != null) {
							try {
								update_time = update_time.replace("Z", " UTC");
								cus.setId(Integer.parseInt(mdbReport.getCusId()));
								cus.setLatestReportTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z").parse(update_time));
								customerService.updateSelectiveById(cus);
							} catch (Exception e) {
								logger.info("更新报告获取时间异常");
							}
						}
						
						//TODO 处理聚信立数据到mongoDB
						Report report = new Report();
						report.setToken(token);
						// 原始聚信立数据
						report.setJxlOrgReport(reportData);
						// 解析聚信立数据
						String jxlReportJson = juxinliService.parseJXLReportData(reportData, cus.getId());
						report.setJxlReport(jxlReportJson);
						if (cus.getTemReportType() == 0) { //信息报告类型：0-基础 1-标准
							report.setFinalReport(jxlReportJson);
						}
						reportMongoDBService.updateReportByToken(report);
						return jxlReportJson;
					} else {
						throw new RuntimeException("获取运营商原始数据请求返回报文中没有report_data节点！");
					}
				} else {
					throw new Exception("获取运营商原始数据请求聚信立失败！");
				}
			} else {
				throw new RuntimeException("获取报告请求聚信立失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 聚信立==>获取报告状态并更新数据库
	 */
	@Override
	@Transactional
	/*public void updateJxlReportStatus(Report report) {
		// 必须是采集中的记录才需要更新状态
		if (report.getStatus() != 0) {
			return;
		}
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("client_secret", constPro.JXL_SECRET);
		reqMap.put("access_token", this.getJxlToken());
		reqMap.put("token", report.getToken());

		try {
			String resJson = HttpUtils.httpGetRequest(constPro.JXL_REQ_URL+"/api/v2/job/access_jobs_status_by_token", reqMap);
			if (resJson != null) {
				JSONObject json = JSONObject.parseObject(resJson);

				if (json.getBoolean("success")) {
					if (json.containsKey("data")) {
						if ("完成".equals(json.getJSONObject("data").getString("status"))) {
							String token = report.getToken();
							String ujr = this.updateJxlReport(token);
							if (ujr != null) {
								//TODO 更新mongoDB报告状态：采集成功
								report = new Report();
								report.setToken(token);
								report.setJxlStatus(1); //采集成功
								reportMongoDBService.updateReportByToken(report);
								
								//TODO 更新聚信立报告数据
								// 通过token获取mongodb中的report
								Report mdbReport = reportMongoDBService.selectReportByToken(token);
								Customer cus = customerService.selectById(Integer.parseInt(mdbReport.getCusId()));
								
								//TODO 更新最新报告key编号等信息入库 
								if (cus.getTemReportType() == 0) { //信息报告类型：0-基础 1-标准 
									cus.setId(Integer.parseInt(mdbReport.getCusId()));
									cus.setRefreshTimes(cus.getRefreshTimes() + 1);
									cus.setLatestReportKey(mdbReport.getReportKey());
									cus.setLatestReportType(cus.getTemReportType()); //信息报告类型：0-基础 1-标准
//								cus.setLatestReportTime(new Date()); //报告更新时间（先设置为系统）
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
								}
								
								// 删除redis中的采集记录
								RedisKeyDto redisReq = new RedisKeyDto();
								redisReq.setKeys(constPro.REPORT_KEY + report.getToken());
								redisService.delete(redisReq);
							}
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
	*/
	/**
	 * 聚信立==>启动守护线程，定期更新报告状态
	 */
	/*@PostConstruct
	public void daemon() {
		// 守护线程1，定期更新报告状态
		new Thread() {
			public void run() {
				while (true) {
					logger.debug("===>开始获取数据，准备更新报告状态");
					List<Report> reportList = null;
					try {
						// 查询申请时间2分钟之前到现在，100条采集中的报告
						// reportList = juxinliReportDao.selectTop100();
						//获取采集中的报告
						reportList = reportMongoDBService.findByProp("jxlStatus", 0);
					} catch (Throwable e) {
						logger.error("查询数据库失败,5分钟后重试", e);
						try {
							sleep(5 * 60 * 1000);
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
								reportMongoDBService.updateReportByToken(report);
								redisService.delete(redisReq);
								
								// 修改报告采集状态
								Report mdbReport = reportMongoDBService.selectReportByToken(report.getToken());
								Customer cus = new Customer();
								cus.setId(Integer.parseInt(mdbReport.getCusId()));
								cus.setLatestReportStatus(2); //采集超时失败
								customerService.updateSelectiveById(cus);
								
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
							updateJxlReportStatus(report);
						} catch (Throwable e) {
							logger.error("更新状态失败", e);
						}
					}
				}
			}
		}.start();
	}*/
	
	/**
	 * 电话邦==>获取token，用于之后的接口访问,由于token存在过期时间，所以后面判断返回code为4000的时候重新获取
	 */
//	@Override
	public String getDhbToken() {
		String dhbToken = null;
		// 从redis查询电话邦访问token
		RedisKeyDto rk = new RedisKeyDto();
		rk.setKeys(constPro.DHB_ACC_TOKNE_KEY);
		RedisKeyDto rv = redisService.redisGet(rk);
		
		/*if (dhbToken != null) {
			return dhbToken;
		}*/
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
	 * 聚信立==>获取access_token，如果为空，则发送请求到聚信立获取永久有效期的access_token并保存在accessToken变量中，
	 * 下次使用不需要再次请求
	 */
	@Override
	public String getJxlToken() {
		if (jxlToken != null) {
			return jxlToken;
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
				jxlToken = json.getString("access_token");
			} else {
				throw new RuntimeException("获取access-token请求聚信立失败!");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return jxlToken;
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
