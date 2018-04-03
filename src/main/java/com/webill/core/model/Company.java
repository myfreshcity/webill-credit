package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 企业表
 *
 */
@TableName("t_company")
public class Company implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 公司名称 */
	private String comName;

	/** 公司地址省份城市区县 */
	private String comAddr;

	/** 公司地址码 */
	private String comAddrCode;

	/** 公司地址详情 */
	private String comAddrDetail;

	/** 法人姓名 */
	private String legalName;

	/** 联系人姓名 */
	private String contactName;

	/** 联系人手机号 */
	private String contactMobile;

	/** 社会统一信息代码 */
	private String creditCode;

	/** 营业执照图片URL地址 */
	private String busiLicenseUrl;

	/** 申请状态：1-待审核 2-审核通过 3-审核拒绝 */
	private Integer status;

	/** 审核人 */
	private Integer operatorId;

	/** 审核时间 */
	private Date reviewTime;

	/** 审核备注 */
	private String remark;

	/** 更新时间 */
	private Date updatedTime;

	/** 创建时间 */
	private Date createdTime;
	
	/* 审核时间 String*/
	@TableField(exist = false)
	private String reviewTimeStr;
	
	/* 关联的用户ID*/
	@TableField(exist = false)
	private Integer userId;
	
	/* 用户登录手机号*/
	@TableField(exist = false)
	private String userMobileNo;
	
	/* 用户登录手机号*/
	@TableField(exist = false)
	private String operatorName;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComName() {
		return this.comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getComAddr() {
		return this.comAddr;
	}

	public void setComAddr(String comAddr) {
		this.comAddr = comAddr;
	}

	public String getComAddrCode() {
		return this.comAddrCode;
	}

	public void setComAddrCode(String comAddrCode) {
		this.comAddrCode = comAddrCode;
	}

	public String getComAddrDetail() {
		return this.comAddrDetail;
	}

	public void setComAddrDetail(String comAddrDetail) {
		this.comAddrDetail = comAddrDetail;
	}

	public String getLegalName() {
		return this.legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return this.contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getCreditCode() {
		return this.creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public String getBusiLicenseUrl() {
		return this.busiLicenseUrl;
	}

	public void setBusiLicenseUrl(String busiLicenseUrl) {
		this.busiLicenseUrl = busiLicenseUrl;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Date getReviewTime() {
		return this.reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getReviewTimeStr() {
		return reviewTimeStr;
	}

	public void setReviewTimeStr(String reviewTimeStr) {
		this.reviewTimeStr = reviewTimeStr;
	}

	public String getUserMobileNo() {
		return userMobileNo;
	}

	public void setUserMobileNo(String userMobileNo) {
		this.userMobileNo = userMobileNo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
}
