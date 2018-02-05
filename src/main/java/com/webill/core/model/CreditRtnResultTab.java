/**
 * 
 */
package com.webill.core.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

//import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSONObject;
import com.webill.framework.annotations.FieldAnnotation;

/**
 * @author zhangjia
 * @createDate 2016年12月5日 上午9:52:11
 * @className CreditRtnResultTab
 * @classDescribe 征信返回结果
 */
@Entity
@Table(name = "CREDIT_RTN_RESULT_TAB", uniqueConstraints = {})
// @DynamicUpdate
public class CreditRtnResultTab {
	private Long id;
	private String reportId;
	private Long userId;
	private String realName;
	private String creSn;
	private String mobileNumber;
	private Long acOrderId;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date requestTime;
	private String reasonDesc;
	private String status; // D--提交成功，报告获取中，S--报告获取成功，F--报告获取失败
	private String statusDesc; // D--提交成功，报告获取中，S--报告获取成功，F--报告获取失败
	private Integer finalScore;
	private String finalDecision;// '0--Accept,通过；1--Reject,拒绝；2--Review,审核'；--风险结果
	private String finalReport;
	
	private Map report;
	@Transient
	public Map getReport(){
		report = JSONObject.parseObject(this.finalReport, Map.class);
		return report;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "report_id", length = 24)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	@Column(name = "user_id")
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "real_name", length = 50)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	@Column(name = "cre_sn", length = 50)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getCreSn() {
		return creSn;
	}
	public void setCreSn(String creSn) {
		this.creSn = creSn;
	}
	@Column(name = "mobile_number", length = 20)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	@Column(name = "ac_order_id")
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public Long getAcOrderId() {
		return acOrderId;
	}
	public void setAcOrderId(Long acOrderId) {
		this.acOrderId = acOrderId;
	}
	@Column(name = "request_time", length = 19)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	@Column(name = "reason_desc", length = 256)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getReasonDesc() {
		return reasonDesc;
	}
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
	@Column(name = "final_score", length = 3)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public Integer getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(Integer finalScore) {
		this.finalScore = finalScore;
	}
	@Column(name = "final_decision", length = 1)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getFinalDecision() {
		return finalDecision;
	}
	public void setFinalDecision(String finalDecision) {
		this.finalDecision = finalDecision;
	}
	@Column(name = "status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "status_Desc")
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	@Column(name = "final_Report")
	public String getFinalReport() {
		return finalReport;
	}
	public void setFinalReport(String finalReport) {
		this.finalReport = finalReport;
	}

}
