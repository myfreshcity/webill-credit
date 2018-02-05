package com.webill.core.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webill.app.util.HttpClientGetUtil;
import com.webill.app.util.HttpClientPostUtil;
import com.webill.app.util.ObjectUtil;
import com.webill.app.util.PropertiesFileUtil;
import com.webill.core.model.AddressDetectTab;
import com.webill.core.model.CourtDetailsTab;
import com.webill.core.model.CreditRtnResultTab;
import com.webill.core.model.ItemDetailTab;
import com.webill.core.model.OverdueDetailsTab;
import com.webill.core.model.RecResult;
import com.webill.core.model.RiskItemsTab;
import com.webill.core.service.ICreditService;

/**
 * @author zhangjia
 * @createDate 2016年12月5日 下午2:04:01
 * @className CrrditService
 * @classDescribe 同盾征信处理
 */
@Service
public class CreditServiceImpl implements ICreditService {
	private Logger log = LoggerFactory.getLogger(CreditServiceImpl.class);

	// @Autowired
	// IGenericDao genericDao;
	// @Autowired
	// HibernateTemplate heHibernateTemplate;

	private String msg = " ";
	private String reportId = "";
	private String finalDecision = "";

	@Override
	public Map<String, String> saveCreditInfo(Map<String, String> map, Long userId, Long acOrderId) throws Throwable {
		Properties properties = null;
		try {
			properties = PropertiesFileUtil.loadProperties(WorkbenchContext.getResourcePath() + "/credit.properties",
					PropertiesFileUtil.BY_PROPERTIES);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, String> maps = new HashMap<String, String>();
		map.put("partner_code", properties.getProperty("partnerCode"));
		map.put("partner_key", properties.getProperty("partnerKey"));
		map.put("app_name", properties.getProperty("appName"));
		CreditRtnResultTab creditRtnResultTab = null;
		String resultSub = HttpClientPostUtil.doPost(properties.getProperty("subUrl"), map);// 调用同盾贷前submit审核接口
		Thread.sleep(3000);
		System.out.println(resultSub);
		RecResult recResult = JSONObject.parseObject(resultSub, RecResult.class);
		System.out.println(recResult.getSuccess());
		System.out.println(recResult.getReport_id());
		reportId = recResult.getReport_id();
		creditRtnResultTab = new CreditRtnResultTab();
		creditRtnResultTab.setReportId(recResult.getReport_id());
		creditRtnResultTab.setRealName(map.get("name"));
		creditRtnResultTab.setCreSn(map.get("id_number"));
		creditRtnResultTab.setMobileNumber(map.get("mobile"));
		creditRtnResultTab.setAcOrderId(acOrderId);
		creditRtnResultTab.setRequestTime(new Date());
		creditRtnResultTab.setUserId(userId);
		// genericDao.save(creditRtnResultTab);
		if (recResult.getSuccess().equals("true")) {
			System.out.println("请求成功");
			msg = recResult.getSuccess();
			// 入库
			// genericDao.save(creditRtnResultTab);
			// 用reportid请求query接口入库
			String reportId = recResult.getReport_id();
			String param = "partner_code" + "=" + properties.getProperty("partnerCode") + "&partner_key" + "="
					+ properties.getProperty("partnerKey") + "&app_name" + "=" + properties.getProperty("appName")
					+ "&report_id" + "=" + reportId;
			String resultQry = HttpClientGetUtil.sendGet(properties.getProperty("qryUrl"), param);// 调用同盾贷前query报告接口
			System.out.println(resultQry);
			RecResult recResultQry = JSONObject.parseObject(resultQry, RecResult.class);
			JSONObject jsonObject = JSONObject.parseObject(resultQry);

			if (recResultQry.getSuccess().equals("true")) {
				JSONArray risk_items = jsonObject.getJSONArray("risk_items");
				System.out.println(jsonObject.getString("final_decision"));
				if (jsonObject.getString("final_decision").equals("Accept")) {
					creditRtnResultTab.setFinalDecision("0");
					finalDecision = "0";// Accept,通过
				} else if (jsonObject.getString("final_decision").equals("Reject")) {
					creditRtnResultTab.setFinalDecision("1");
					finalDecision = "1";// Reject,拒绝
				} else {
					creditRtnResultTab.setFinalDecision("2");
					finalDecision = "2";// Review,审核
				}
				creditRtnResultTab.setFinalScore(jsonObject.getInteger("final_score"));
				// genericDao.save(creditRtnResultTab);

				// 风险项
				for (int i = 0; i < risk_items.size(); i++) {
					RiskItemsTab itemsTab = new RiskItemsTab();
					JSONObject riskObj = risk_items.getJSONObject(i);
					itemsTab.setItemId(riskObj.getInteger("item_id"));
					itemsTab.setReportId(reportId);
					itemsTab.setGroups(riskObj.getString("group"));
					itemsTab.setItemName(riskObj.getString("item_name"));
					if (riskObj.getString("risk_level").equals("high")) {
						itemsTab.setRiskLevel("0");
					} else if (riskObj.getString("risk_level").equals("low")) {
						itemsTab.setRiskLevel("2");
					} else {
						itemsTab.setRiskLevel("1");
					}
					// genericDao.save(itemsTab);
					// 获取检查项
					JSONObject item_detail = riskObj.getJSONObject("item_detail");
					if (!ObjectUtil.isNullOrEmpty(item_detail)) {
						ItemDetailTab detailTab = new ItemDetailTab();
						detailTab.setDiscreditTimes(item_detail.getInteger("discredit_times"));
						detailTab.setFraudType(item_detail.getString("fraud_type"));
						detailTab.setItemId(itemsTab.getItemId());
						detailTab.setPlatformCount(item_detail.getInteger("platform_count"));
						detailTab.setType(item_detail.getString("type"));
						// genericDao.save(detailTab);
						// 法院
						JSONArray court_details = item_detail.getJSONArray("court_details");
						if (!ObjectUtil.isNullOrEmpty(court_details)) {
							for (int j = 0; j < court_details.size(); j++) {
								JSONObject courtObj = court_details.getJSONObject(j);
								CourtDetailsTab courtDetailsTab = new CourtDetailsTab();
								courtDetailsTab.setAge(courtObj.getString("age"));
								courtDetailsTab.setCaseNumber(courtObj.getString("case_number"));
								courtDetailsTab.setCourtName(courtObj.getString("court_name"));
								courtDetailsTab.setDiscreditDetail(courtObj.getString("discredit_detail"));
								courtDetailsTab.setDuty(courtObj.getString("duty"));
								courtDetailsTab.setExecutionBase(courtObj.getString("execution_base"));
								courtDetailsTab.setExecutionDepartment(courtObj.getString("execution_department"));
								courtDetailsTab.setExecutionNumber(courtObj.getString("execution_number"));
								courtDetailsTab.setExecutionStatus(courtObj.getString("execution_status"));
								courtDetailsTab.setFilingTime(courtObj.getString("filing_time"));
								if (courtObj.getString("fraud_type").equals("法院失信")) {
									courtDetailsTab.setFraudType("0");
								} else if (courtObj.getString("fraud_type").equals("法院执行")) {
									courtDetailsTab.setFraudType("1");
								} else {
									courtDetailsTab.setFraudType("2");
								}
								courtDetailsTab.setGender(courtObj.getString("gender"));
								courtDetailsTab.setItemId(itemsTab.getItemId());
								courtDetailsTab.setName(courtObj.getString("name"));
								courtDetailsTab.setProvince(courtObj.getString("province"));
								courtDetailsTab.setSituation(courtObj.getString("situation"));
								// genericDao.save(courtDetailsTab);
							}
						}
						// 逾期
						JSONArray overdue_details = item_detail.getJSONArray("overdue_details");
						if (!ObjectUtil.isNullOrEmpty(overdue_details)) {
							for (int k = 0; k < overdue_details.size(); k++) {
								JSONObject overdueObj = overdue_details.getJSONObject(k);
								OverdueDetailsTab overdueDetailsTab = new OverdueDetailsTab();
								overdueDetailsTab.setItemId(itemsTab.getItemId());
								overdueDetailsTab.setOverdueAmount(overdueObj.getBigDecimal("overdue_amount"));
								overdueDetailsTab.setOverdueCount(overdueObj.getInteger("overdue_count"));
								overdueDetailsTab.setOverdueDay(overdueObj.getInteger("overdue_count"));
								// genericDao.save(overdueDetailsTab);
							}
						}
					}
				}
				// 地址详情
				JSONObject address_detect = jsonObject.getJSONObject("address_detect");
				if (!address_detect.isEmpty()) {
					AddressDetectTab addressDetectTab = new AddressDetectTab();
					addressDetectTab.setReportId(reportId);
					addressDetectTab.setBankCardAddress(address_detect.getString("bank_card_address"));
					addressDetectTab.setCellAddress(address_detect.getString("cell_address"));
					addressDetectTab.setIdCardAddress(address_detect.getString("id_card_address"));
					addressDetectTab.setMobileAddress(address_detect.getString("mobile_address"));
					addressDetectTab.setWifiAddress(address_detect.getString("wifi_address"));
					addressDetectTab.setTrueIpAddress(address_detect.getString("true_ip_address"));
					// genericDao.save(addressDetectTab);
				}
			} else {
				msg = "错误码：" + jsonObject.getString("reason_code") + " " + "错误信息："
						+ jsonObject.getString("reason_desc");
				// 更新错误信息
				creditRtnResultTab.setReportId(reportId);
				creditRtnResultTab.setReasonDesc(jsonObject.getString("reason_desc"));
				// genericDao.save(creditRtnResultTab);
			}
			System.out.println("----------------------");
		} else {
			msg = "错误码：" + recResult.getReason_code() + " " + "错误信息：" + recResult.getReason_desc();
			// 入库
			creditRtnResultTab.setReportId(recResult.getReport_id());
			creditRtnResultTab.setRealName(map.get("name"));
			creditRtnResultTab.setCreSn(map.get("id_number"));
			creditRtnResultTab.setMobileNumber(map.get("mobile"));
			creditRtnResultTab.setReasonDesc(recResult.getReason_desc());
			// genericDao.save(creditRtnResultTab);
		}
		maps.put("msg", msg);
		maps.put("finalDecision", finalDecision);
		if (!ObjectUtil.isNullOrEmpty(reportId)) {
			maps.put("reportId", reportId);// 贷前申请风险报告编号
		}
		return maps;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, String> qryAndSaveCreditInfo(Map<String, String> map, Long userId, Long acOrderId)
			throws Throwable {
		Properties properties = PropertiesFileUtil.loadProperties(
				WorkbenchContext.getResourcePath() + "/credit.properties", PropertiesFileUtil.BY_PROPERTIES);
		Map<String, String> maps = new HashMap<String, String>();
		map.put("partner_code", properties.getProperty("partnerCode"));
		map.put("partner_key", properties.getProperty("partnerKey"));
		map.put("app_name", properties.getProperty("appName"));

		String resultSub = HttpClientPostUtil.doPost(properties.getProperty("subUrl"), map);// 调用同盾贷前submit审核接口
		log.info(resultSub);
		RecResult recResult = JSONObject.parseObject(resultSub, RecResult.class);
		// 获取报告成功
		if (Boolean.valueOf(recResult.getSuccess())) {
			CreditRtnResultTab creditRtnResultTab = null;
			String reportId = recResult.getReport_id();
			creditRtnResultTab = new CreditRtnResultTab();
			creditRtnResultTab.setReportId(recResult.getReport_id());
			creditRtnResultTab.setRealName(map.get("name"));
			creditRtnResultTab.setCreSn(map.get("id_number"));
			creditRtnResultTab.setMobileNumber(map.get("mobile"));
			creditRtnResultTab.setAcOrderId(acOrderId);
			creditRtnResultTab.setRequestTime(new Date());
			creditRtnResultTab.setUserId(userId);
			creditRtnResultTab.setStatus("D");// 报告获取中
			// heHibernateTemplate.save(creditRtnResultTab);
			// maps.put("id", creditRtnResultTab.getId().toString());
			maps.put("id", "1");
			maps.put("reportId", reportId);// 贷前申请风险报告编号
//			heHibernateTemplate.getSessionFactory().getCurrentSession().close();
		} else {// 获取失败成功
			maps.put("msg", String.format("错误码：%s 错误信息：%s", recResult.getReason_code(), recResult.getReason_desc()));
		}
//		maps.put("finalDecision", finalDecision);
		
		this.qryAndUpdateReportRst(Long.valueOf(maps.get("id")), recResult.getReport_id(), 2, 3000L);
		return maps;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void qryAndUpdateReportRst(Long id, String reportId, Integer retry, Long interval) throws Throwable {
		retry = retry!=null&&retry>0?retry:1;
		interval = interval!=null&&interval>0?interval:5000L;
		for(int i=0;i<retry;i++){
			Properties properties = null;
			try {
				properties = PropertiesFileUtil.loadProperties(
						WorkbenchContext.getResourcePath() + "/credit.properties", PropertiesFileUtil.BY_PROPERTIES);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String param = "partner_code" + "=" + properties.getProperty("partnerCode") + "&partner_key" + "="
					+ properties.getProperty("partnerKey") + "&app_name" + "=" + properties.getProperty("appName")
					+ "&report_id" + "=" + reportId;
			String resultQry = HttpClientGetUtil.sendGet(properties.getProperty("qryUrl"), param);// 调用同盾贷前query报告接口
			log.info(resultQry);
			JSONObject jsonObject = JSONObject.parseObject(resultQry);
			if (Boolean.valueOf((boolean) jsonObject.get("success"))) {
				
				// CreditRtnResultTab creditRtnResultTab = (CreditRtnResultTab)
				// heHibernateTemplate.getSessionFactory().getCurrentSession().get(CreditRtnResultTab.class,
				// id);
				CreditRtnResultTab creditRtnResultTab = new CreditRtnResultTab();
				creditRtnResultTab.setId(id);
				creditRtnResultTab.setReportId(reportId);
				creditRtnResultTab.setStatus("S");
				if (jsonObject.getString("final_decision").equals("Accept")) {
					creditRtnResultTab.setFinalDecision("0");
					finalDecision = "0";// Accept,通过
				} else if (jsonObject.getString("final_decision").equals("Reject")) {
					creditRtnResultTab.setFinalDecision("1");
					finalDecision = "1";// Reject,拒绝
				} else {
					creditRtnResultTab.setFinalDecision("2");
					finalDecision = "2";// Review,审核
				}
//				creditRtnResultTab.setFinalDecision(jsonObject.getString("final_decision"));
				creditRtnResultTab.setFinalScore(jsonObject.getInteger("final_score"));
				creditRtnResultTab.setFinalReport(resultQry);
				// heHibernateTemplate.update(creditRtnResultTab);
			}else if("204".equals(jsonObject.getString("reason_code"))){
				Thread.sleep(interval);
			}else{
				// CreditRtnResultTab creditRtnResultTab = (CreditRtnResultTab)
				// heHibernateTemplate.getSessionFactory().getCurrentSession().get(CreditRtnResultTab.class,
				// id);
				CreditRtnResultTab creditRtnResultTab = new CreditRtnResultTab();
				creditRtnResultTab.setFinalReport(resultQry);
				creditRtnResultTab.setId(id);
				creditRtnResultTab.setStatus("F");
				creditRtnResultTab.setStatusDesc(jsonObject.getString("reason_desc"));
				// heHibernateTemplate.update(creditRtnResultTab);
			}
		}
		
		CreditRtnResultTab creditRtnResultTab = new CreditRtnResultTab();
		creditRtnResultTab.setId(id);
		creditRtnResultTab.setStatus("F");
		creditRtnResultTab.setStatusDesc("数据获取超时");
	}
	
	@Override
	public CreditRtnResultTab qryReportRstLocal(String applicantIdNbr,String reportId,String flag){
		CreditRtnResultTab creditRtnResultTab = null;
		if(flag.equals("crdit1")||flag.equals("court1")||flag.equals("overdue1")||flag.equals("itemInfo1")){
			// creditRtnResultTab = (CreditRtnResultTab) genericDao.findOne("
			// from CreditRtnResultTab a where a.reportId = ?", reportId);
		}else{
			// creditRtnResultTab = (CreditRtnResultTab) genericDao.findOne("
			// from CreditRtnResultTab a where a.creSn= ? order by a.id
			// desc",applicantIdNbr);
		}
		if(ObjectUtil.isNullOrEmpty(creditRtnResultTab)){
			log.info("暂无详细数据");
		}
		return creditRtnResultTab;
	}

	@Override
	public JSONArray checkIdNbrAgreement(Map<String, String> map) {
		// Properties properties = PropertiesFileUtil.loadProperties(
		// WorkbenchContext.getResourcePath() + "/credit.properties",
		// PropertiesFileUtil.BY_PROPERTIES);
		JSONArray json = new JSONArray();
		// JSONObject jo = new JSONObject();
		//
		// /** 白名单 */
		// AcIdCard acIdCard = (AcIdCard) genericDao.findOne("from AcIdCard
		// where cardSn = ? and realName = ?",
		// map.get("id_number"), map.get("name"));
		// if (acIdCard != null) {
		// jo.put("0", "0");
		// json.add(jo);
		// } else {
		// map.put("partner_code", properties.getProperty("partnerCode"));
		// map.put("partner_key", properties.getProperty("partnerKey"));
		// // 调用实名验证接口
		// String resultMsg =
		// HttpClientPostUtil.doPost(properties.getProperty("chkUrl"), map);
		// ChkIdNbrVo recResult = JSONObject.parseObject(resultMsg,
		// ChkIdNbrVo.class);
		// if (recResult.getSuccess().equals("true")) {
		// msg = String.valueOf(recResult.getResult());
		// if (msg.equals("0")) {
		// jo.put("0", "0");
		//
		// /** 添加白名单 */
		// AcIdCard card = new AcIdCard();
		// card.setCardSn(map.get("id_number"));
		// card.setRealName(map.get("name"));
		// genericDao.getHibernateTemplate().save(card);
		// } else {
		// jo.put("0", "1");
		// }
		// } else {
		// jo.put("0", "1");
		// }
		// json.add(jo);
		// }
		return json;
	}

	public static void main(String[] args) throws Throwable {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "李小雨");// 用户姓名
		map.put("id_number", "430725199110015753");// 用户身份证
		map.put("mobile", "13601658182");// 用户手机号
		Map rspMap = new CreditServiceImpl().qryAndSaveCreditInfo(map, (long) 5, 5000L);
		
		String str = "{'success':true,'risk_items':[{'risk_level':'high','item_id':294542,'item_name':'身份证格式校验错误','group':'个人基本信息核查'},{'risk_level':'medium','item_detail':{'frequency_detail_list':[{'detail':'7天内手机号申请次数>=3：5'}],'type':'frequency_detail'},'item_id':294652,'item_name':'7天内设备或身份证或手机号申请次数过多','group':'客户行为检测'},{'risk_level':'medium','item_detail':{'frequency_detail_list':[{'detail':'1个月内身份证申请次数>=5：5'}],'type':'frequency_detail'},'item_id':294654,'item_name':'1个月内设备或身份证或手机号申请次数过多','group':'客户行为检测'}],'report_id':'ER2016122315323518637549','apply_time':1482478356000,'final_decision':'Reject','final_score':100,'application_id':'1612231532355467E9623C4C99755C9C','report_time':1482478356000,'address_detect':{'mobile_address':'上海市','id_card_address':'湖南省零陵地区零陵县'}}";
		JSONObject jsonObject = JSONObject.parseObject(str);
		JSONArray array = jsonObject.getJSONArray("risk_items");
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject2 = array.getJSONObject(i);
			System.out.println(i + ":" + jsonObject2);
			JSONObject item_detail = jsonObject2.getJSONObject("item_detail");
			if (item_detail != null) {
				JSONArray frequency_detail_list = item_detail.getJSONArray("frequency_detail_list");
				if (frequency_detail_list != null) {
					for (int b = 0; b < frequency_detail_list.size(); b++) {
						System.out.println(b + ":" + frequency_detail_list.getJSONObject(b));
					}
				}
			}
		}
	}

	private void qryReportRst(long l, String string, int i, long m) {
		// TODO Auto-generated method stub  
		
	}
}
