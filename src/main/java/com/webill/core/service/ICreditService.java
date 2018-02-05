package com.webill.core.service;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.webill.core.model.CreditRtnResultTab;

public interface ICreditService {

	public Map<String, String> qryAndSaveCreditInfo(Map<String, String> map, Long userId, Long acOrderId)
			throws Exception, Throwable;

	public void qryAndUpdateReportRst(Long id, String reportId, Integer retry, Long interval)
			throws InterruptedException, Throwable;

	public CreditRtnResultTab qryReportRstLocal(String applicantIdNbr, String reportId, String flag);

	public JSONArray checkIdNbrAgreement(Map<String, String> map);

	public Map<String, String> saveCreditInfo(Map<String, String> map, Long userId, Long acOrderId)
			throws Exception, Throwable;
}
