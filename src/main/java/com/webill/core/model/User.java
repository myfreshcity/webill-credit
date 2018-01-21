package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * 用户表
 *
 */
@TableName("t_user")
public class User implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 微信ID */
	private String openId;

	/** 银行预留手机号 */
	private String bankMobileNo;

	/** 联系移动电话，直接用于登录 */
	private String mobileNo;

	/** 用户密码 */
	private String password;

	/** 身份证号码 */
	private String idNo;

	/** 银行卡号码 */
	private String bankCardNo;

	/** 真实姓名 */
	private String realName;

	/** 电子邮箱 */
	private String email;

	/** 所属企业，企业表的主键ID */
	private Integer comId;

	/** 是否为普通员工：0-否，即企业主 1-是 */
	private Integer isStaff;

	/** 是否实名认证：0-否 1-是 */
	private Integer isVerified;
	
	/** 客户信息标准版可用次数 */
	private Integer standardTimes;

	/** 客户信息高级版可用次数 */
	private Integer advancedTimes;

	/** 会员等级：0-试用会员 1-银卡会员 2-金卡会员 */
	private Integer memeberLevel;

	/** 会员开始日期 */
	private Date memberStartDate;

	/** 会员结束日期 */
	private Date memberEndDate;

	/** 状态： -1-逻辑删除 0-正常数据 */
	private Integer tStatus;

	/** 更新时间 */
	private Date updatedTime;

	/** 创建时间 */
	private Date createdTime;

	@TableField(exist = false)
	private String timeFrom;
	
	@TableField(exist = false)
	private String timeTo;
	
	@TableField(exist = false)
	private String recommendName;
	
	@ApiModelProperty(value = "用户输入的手机验证码", required = true)
	@TableField(exist = false)
	private String inCode;
	
	@TableField(exist = false)
	private String checkFlag;
	
	@TableField(exist = false)
	private String jwtToken;
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getBankMobileNo() {
		return this.bankMobileNo;
	}

	public void setBankMobileNo(String bankMobileNo) {
		this.bankMobileNo = bankMobileNo;
	}

	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdNo() {
		return this.idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getBankCardNo() {
		return this.bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getComId() {
		return this.comId;
	}

	public void setComId(Integer comId) {
		this.comId = comId;
	}

	public Integer getIsStaff() {
		return this.isStaff;
	}

	public void setIsStaff(Integer isStaff) {
		this.isStaff = isStaff;
	}
	
	public Integer getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Integer isVerified) {
		this.isVerified = isVerified;
	}

	public Integer getStandardTimes() {
		return this.standardTimes;
	}

	public void setStandardTimes(Integer standardTimes) {
		this.standardTimes = standardTimes;
	}

	public Integer getAdvancedTimes() {
		return this.advancedTimes;
	}

	public void setAdvancedTimes(Integer advancedTimes) {
		this.advancedTimes = advancedTimes;
	}

	public Integer getMemeberLevel() {
		return this.memeberLevel;
	}

	public void setMemeberLevel(Integer memeberLevel) {
		this.memeberLevel = memeberLevel;
	}

	public Date getMemberStartDate() {
		return this.memberStartDate;
	}

	public void setMemberStartDate(Date memberStartDate) {
		this.memberStartDate = memberStartDate;
	}

	public Date getMemberEndDate() {
		return this.memberEndDate;
	}

	public void setMemberEndDate(Date memberEndDate) {
		this.memberEndDate = memberEndDate;
	}

	public Integer getTStatus() {
		return this.tStatus;
	}

	public void setTStatus(Integer tStatus) {
		this.tStatus = tStatus;
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

	public String getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public String getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	public String getRecommendName() {
		return recommendName;
	}

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}

	public String getInCode() {
		return inCode;
	}

	public void setInCode(String inCode) {
		this.inCode = inCode;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
}
