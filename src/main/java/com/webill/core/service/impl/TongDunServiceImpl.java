package com.webill.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webill.app.SystemProperty;
import com.webill.app.controller.BaseApiController;
import com.webill.app.util.HttpClientGetUtil;
import com.webill.app.util.HttpClientPostUtil;
import com.webill.app.util.StringUtil;
import com.webill.core.model.CusContact;
import com.webill.core.model.Customer;
import com.webill.core.model.RecResult;
import com.webill.core.model.juxinli.Report;
import com.webill.core.service.ICusContactService;
import com.webill.core.service.ICustomerService;
import com.webill.core.service.IReportMongoDBService;
import com.webill.core.service.ITongDunService;
import com.webill.framework.common.JSONUtil;

/**
 * @author zhangjia
 * @createDate 2016年12月5日 下午2:04:01
 * @className CrrditService
 * @classDescribe 同盾征信处理
 */
@Service
public class TongDunServiceImpl implements ITongDunService {
	private static Log logger = LogFactory.getLog(BaseApiController.class);

	@Autowired
	private IReportMongoDBService reportMongoDBService;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private ICusContactService cusContactService;
	@Autowired
	protected SystemProperty constPro;

	@Override
	public Object saveSubmitQuery(String reportKey, String userId) throws InterruptedException {
		Map<String, String> map = new HashMap<String, String>();
		// 必传数据
		map.put("partner_code", constPro.TD_PARTNER_CODE);
		map.put("partner_key", constPro.TD_PARTNER_KEY);
		map.put("app_name", constPro.TD_APP_NAME);
		// 获取客户信息
		Customer cus = customerService.selectById(userId);
		// 获取客户的联系人信息
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("cus_id", userId);
		List<CusContact> ccList = cusContactService.selectByMap(columnMap);
		// 客户信息
		// map.put("name", cus.getRealName());
		// map.put("id_number", cus.getIdNo());
		// map.put("mobile", cus.getMobileNo());
		// map.put("home_address", cus.getHomeAddr());
		// map.put("company_address", cus.getWorkAddr());
		map.put("name", "曾丽洁");
		map.put("id_number", "430602199507252599");
		map.put("mobile", "13564616288");
		map.put("home_address", cus.getHomeAddr());
		map.put("company_address", cus.getWorkAddr());
		// 联系人信息
		if (ccList != null) {
			for (int i = 0; i < ccList.size(); i++) {
				int x = i + 1;
				String preKey = "contact" + x;
				map.put(preKey + "_name", ccList.get(i).getName());
				map.put(preKey + "_mobile", ccList.get(i).getMobileNo());
			}
		}
		Report report = new Report();
		logger.info("【同盾】提交申请信息，接口传入数据=====>" + JSONUtil.toJSONString(map));
		// 获取--------->【提交申请】
		String resultSub = HttpClientPostUtil.doPost(constPro.TD_SUB_URL, map);// 调用同盾贷前submit审核接口
		logger.info("【同盾】提交申请信息，接口返回数据=====>" + resultSub);
		if (StringUtil.isNotEmpty(resultSub)) {
			Thread.sleep(3000);
			RecResult recResult = JSONObject.parseObject(resultSub, RecResult.class);
			// 提交申请信息，返回结果入库
			if (Boolean.valueOf(recResult.getSuccess())) {
				logger.info("【同盾】提交申请信息=====>成功");
				// 调用成功
				report.setCusId(userId.toString());
				report.setTdStatus(0);// 采集中
				report.setSid(recResult.getReport_id());
				report.setName(map.get("name"));
				report.setIdCard(map.get("id_number"));
				report.setMobile(map.get("mobile"));
				// 入库
				reportMongoDBService.save(report);
				logger.info("【同盾】提交申请=====>返回信息保存成功");
				// 用reportid请求query接口入库
				String reportId = recResult.getReport_id();
				// 根据报告id获取报告信息
				String param = "partner_code" + "=" + constPro.TD_PARTNER_CODE + "&partner_key" + "="
						+ constPro.TD_PARTNER_KEY + "&app_name" + "=" + constPro.TD_APP_NAME
						+ "&report_id" + "=" + reportId;
				// 获取----------->【查询结果】
				String resultQry = HttpClientGetUtil.sendGet(constPro.TD_QRY_URL, param);// 调用同盾贷前query报告接口

				logger.info("【同盾】提交报告id" + reportId + "，返回的报告数据=====>" + resultQry);
				RecResult recResultQry = JSONObject.parseObject(resultQry, RecResult.class);
				JSONObject jsonObject = JSONObject.parseObject(resultQry);

				if (recResultQry.getSuccess().equals("true")) {
					JSONArray risk_items = jsonObject.getJSONArray("risk_items");
					report.setTdReport(resultQry);
					report.setTdOrgReport(resultQry);
					report.setTdStatus(1);
					report.setSid(reportId);
					report.setReportKey(reportKey);
					// 报告信息入库
					reportMongoDBService.updateReportBySid(report);
					return null;
				} else {
					String msg = "错误码：" + jsonObject.getString("reason_code") + " " + "错误信息："
							+ jsonObject.getString("reason_desc");
					logger.info(msg);
					return recResultQry;
				}
			} else {
				logger.info("【同盾】=====>提交申请失败，未获取到报告id");
				return recResult;
			}
		} else {
			logger.info("【同盾】=====>提交申请失败，未获取到报告id");
			return resultSub;
		}
	}

	@Override
	public void updateQueryByReportId(String reportId, String reportKey) {
		Report report = new Report();
		String param = "partner_code" + "=" + constPro.TD_PARTNER_CODE + "&partner_key" + "=" + constPro.TD_PARTNER_KEY
				+ "&app_name" + "=" + constPro.TD_APP_NAME + "&report_id" + "=" + reportId;
		// 获取----------->【查询结果】
		String resultQry = HttpClientGetUtil.sendGet(constPro.TD_QRY_URL, param);// 调用同盾贷前query报告接口

		logger.info("【同盾】提交报告id" + reportId + "，返回的报告数据=====>" + resultQry);
		RecResult recResultQry = JSONObject.parseObject(resultQry, RecResult.class);
		JSONObject jsonObject = JSONObject.parseObject(resultQry);

		if (recResultQry.getSuccess().equals("true")) {
			JSONArray risk_items = jsonObject.getJSONArray("risk_items");
			report.setTdReport(resultQry);
			report.setTdOrgReport(resultQry);
			report.setTdStatus(1);
			report.setSid(reportId);
			report.setReportKey(reportKey);
			// 报告信息入库
			reportMongoDBService.updateReportBySid(report);
		} else {
			String msg = "错误码：" + jsonObject.getString("reason_code") + " " + "错误信息："
					+ jsonObject.getString("reason_desc");
			logger.info(msg);
		}
	}
}
