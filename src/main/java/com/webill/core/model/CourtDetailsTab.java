/**
 * 
 */
package com.webill.core.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.webill.framework.annotations.FieldAnnotation;


/**
 * @author zhangjia
 * @createDate 2016年12月5日 上午10:53:41
 * @className CourtDetailsTab
 * @classDescribe 法院详情信息
 */
 @Entity
 @Table(name = "COURT_DETAILS_TAB", uniqueConstraints = {})
public class CourtDetailsTab {
	private Long id;
	private Integer itemId;
	private String fraudType;
	private String name;
	private String age;
	private String gender;
	private String province;
	private String filingTime;
	private String courtName;
	private String executionDepartment;
	private String duty;
	private String situation;
	private String discreditDetail;
	private String executionBase;
	private String caseNumber;
	private String executionNumber;
	private String executionStatus;
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
	@Column(name = "item_id", length = 4)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	@Column(name = "fraud_type", length = 1)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getFraudType() {
		return fraudType;
	}
	public void setFraudType(String fraudType) {
		this.fraudType = fraudType;
	}
	@Column(name = "name", length = 30)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "age", length = 3)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	@Column(name = "gender", length = 1)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Column(name = "province", length = 20)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	@Column(name = "filing_time", length = 11)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getFilingTime() {
		return filingTime;
	}
	public void setFilingTime(String filingTime) {
		this.filingTime = filingTime;
	}
	@Column(name = "court_name", length = 30)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getCourtName() {
		return courtName;
	}
	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}
	@Column(name = "execution_department", length = 30)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getExecutionDepartment() {
		return executionDepartment;
	}
	public void setExecutionDepartment(String executionDepartment) {
		this.executionDepartment = executionDepartment;
	}
	@Column(name = "duty", length = 128)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	@Column(name = "situation", length = 128)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	@Column(name = "discredit_detail", length = 128)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getDiscreditDetail() {
		return discreditDetail;
	}
	public void setDiscreditDetail(String discreditDetail) {
		this.discreditDetail = discreditDetail;
	}
	@Column(name = "execution_base", length = 20)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getExecutionBase() {
		return executionBase;
	}
	public void setExecutionBase(String executionBase) {
		this.executionBase = executionBase;
	}
	@Column(name = "case_number", length = 20)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getCaseNumber() {
		return caseNumber;
	}
	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}
	@Column(name = "execution_number", length = 20)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getExecutionNumber() {
		return executionNumber;
	}
	public void setExecutionNumber(String executionNumber) {
		this.executionNumber = executionNumber;
	}
	@Column(name = "execution_status", length = 10)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getExecutionStatus() {
		return executionStatus;
	}
	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
	}
}
