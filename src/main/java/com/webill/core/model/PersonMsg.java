package com.webill.core.model;

import java.util.Date;

/**
 *
 * 修改投保人信息
 *
 */
public class PersonMsg {
	// 车主名字
	private String ownerName;
	// 车主身份证号
	private String ownerIdNo;
	// 投保人名字
	private String applicantName;
	// 投保人身份证号
	private String applicantIdNo;
	// 被投保人名字
	private String insuredName;
	// 被投保人身份证号
	private String insuredIdNo;
	// 交强险开始时间
	private Date ciStartDate;
	// 商业险开始时间
	private Date biStartDate;
	
	private Integer contactId;

	
	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerIdNo() {
		return ownerIdNo;
	}

	public void setOwnerIdNo(String ownerIdNo) {
		this.ownerIdNo = ownerIdNo;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicantIdNo() {
		return applicantIdNo;
	}

	public void setApplicantIdNo(String applicantIdNo) {
		this.applicantIdNo = applicantIdNo;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getInsuredIdNo() {
		return insuredIdNo;
	}

	public void setInsuredIdNo(String insuredIdNo) {
		this.insuredIdNo = insuredIdNo;
	}

	public Date getCiStartDate() {
		return ciStartDate;
	}

	public void setCiStartDate(Date ciStartDate) {
		this.ciStartDate = ciStartDate;
	}

	public Date getBiStartDate() {
		return biStartDate;
	}

	public void setBiStartDate(Date biStartDate) {
		this.biStartDate = biStartDate;
	}

}
