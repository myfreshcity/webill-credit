/**
 * 
 */
package com.webill.core.model;

/**
 * @ClassName: RecResult
 * @Description: 接收sumbit接口返回VO
 * @author: WangLongFei
 * @date: 2018年2月6日 下午1:55:42
 */
public class RecResult {
	private String success;
	private String reason_code;
	private String reason_desc;
	private String report_id;
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getReason_code() {
		return reason_code;
	}
	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}
	public String getReason_desc() {
		return reason_desc;
	}
	public void setReason_desc(String reason_desc) {
		this.reason_desc = reason_desc;
	}
	public String getReport_id() {
		return report_id;
	}
	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}
	
	
}
