package com.webill.core.service;

public interface ITongDunService {

	/**
	 * @Title: saveSubmitQuery
	 * @Description: 提交申请信息，获取报告，并入库
	 * @author: WangLongFei
	 * @date: 2018年2月6日 上午11:12:15
	 * @param reportKey
	 * @param userId
	 * @return
	 * @return: JSONObject
	 * @throws InterruptedException
	 */
	public Object saveSubmitQuery(String reportKey, String userId) throws InterruptedException;

	/**
	 * @Title: updateQueryByReportId
	 * @Description: 根据报告id获取报告信息并入库
	 * @author: WangLongFei
	 * @date: 2018年2月6日 下午12:08:51
	 * @param reportId
	 * @param reportKey
	 * @return: void
	 */
	public void updateQueryByReportId(String reportId, String reportKey);
}
