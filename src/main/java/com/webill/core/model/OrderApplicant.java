package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 投保人表
 *
 */
@TableName("order_applicant")
@JsonInclude(Include.NON_EMPTY)
public class OrderApplicant implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** id */
	@TableId(type = IdType.AUTO)
	@JSONField(serialize = false)
	@JsonIgnore
	private Integer id;

	/** 订单id*/
	private Integer orderId;
	
	/** 必填  中文名 */
	private String cName;

	/** 拼音或英文名，境外旅游险必填 */
	private String eName;

	/** 必填  证件类型，证件类型：1：身份证、2:护照、3：出生证、4：驾照、5：港澳通行证、6：军官证、7：台胞证、8：警官证、9：港澳台回乡证10：组织机构代码证、11：营业执照、12：税务登记号、13：三证合一、14：统一信用代码证、99：其他、 */
	private String cardType;

	/** 必填  证件号 */
	private String cardCode;

	/** 必填  性别 0：女 1：男 */
	private String sex;

	/** 必填  出生日期 格式：yyyy-MM-dd */
	@JSONField(format = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date birthday;

	/** 必填  手机号码 */
	private String mobile;

	/** 必填  邮箱 */
	private String email;

	/** 职业信息，职业id使用“-”拼接如：101414-101415-101416 */
	private String job;
	
	@JSONField(serialize = false)
	private String jobText;

	/** 国籍 */
	private String country;

	/** 居住省市，地区编码使用“-”拼接如：320000-320100-320104 */
	private String provCityId;

	/** 联系地址 */
	private String contactAddress;

	/** 联系地址邮编 */
	private String contactPost;

	/** 投保人类型 0：个人（默认） 1：公司 */
	private String applicantType;

	/** 证件有效期，格式yyyy-MM-dd */
	@JSONField(format = "yyyy-MM-dd")
	private Date cardPeriod;

	/** 婚姻状态 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他 */
	private String marryState;

	/** 年收入 */
	private String yearlyIncome;

	/** 工作单位地址（办公地址） */
	private String officeAddress;

	/**   工作单位电话（办公电话） */
	private String tel;

	/** 工作单位名称 */
	private String workCompanyName;

	/** 工作单位邮箱 */
	private String workEmail;

	/** 办公地址邮编 */
	private String officePost;

	/** 是否有医保 0：否 1：是 */
	private String haveMedical;

	/** 身高 */
	private String height;

	/** 体重 */
	private String weight;

	/** 税收居民身份1：仅为中国税收居民2：仅为非居民3：既是中国税收居民又是其他国家（地区）税收居民 */
	private String fiscalResidentIdentity;

	/** 投保人与房屋关系0：房主1：房主直系亲属2：租户 */
	private String relatedPersonHouse;

	/**  */
	private Date createdTime;


	public String getJobText() {
		return jobText;
	}

	public void setJobText(String jobText) {
		this.jobText = jobText;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCName() {
		return this.cName;
	}

	public void setCName(String cName) {
		this.cName = cName;
	}

	public String getEName() {
		return this.eName;
	}

	public void setEName(String eName) {
		this.eName = eName;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardCode() {
		return this.cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvCityId() {
		return this.provCityId;
	}

	public void setProvCityId(String provCityId) {
		this.provCityId = provCityId;
	}

	public String getContactAddress() {
		return this.contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactPost() {
		return this.contactPost;
	}

	public void setContactPost(String contactPost) {
		this.contactPost = contactPost;
	}

	public String getApplicantType() {
		return this.applicantType;
	}

	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}

	public Date getCardPeriod() {
		return this.cardPeriod;
	}

	public void setCardPeriod(Date cardPeriod) {
		this.cardPeriod = cardPeriod;
	}

	public String getMarryState() {
		return this.marryState;
	}

	public void setMarryState(String marryState) {
		this.marryState = marryState;
	}

	public String getYearlyIncome() {
		return this.yearlyIncome;
	}

	public void setYearlyIncome(String yearlyIncome) {
		this.yearlyIncome = yearlyIncome;
	}

	public String getOfficeAddress() {
		return this.officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getWorkCompanyName() {
		return this.workCompanyName;
	}

	public void setWorkCompanyName(String workCompanyName) {
		this.workCompanyName = workCompanyName;
	}

	public String getWorkEmail() {
		return this.workEmail;
	}

	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}

	public String getOfficePost() {
		return this.officePost;
	}

	public void setOfficePost(String officePost) {
		this.officePost = officePost;
	}

	public String getHaveMedical() {
		return this.haveMedical;
	}

	public void setHaveMedical(String haveMedical) {
		this.haveMedical = haveMedical;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getFiscalResidentIdentity() {
		return this.fiscalResidentIdentity;
	}

	public void setFiscalResidentIdentity(String fiscalResidentIdentity) {
		this.fiscalResidentIdentity = fiscalResidentIdentity;
	}

	public String getRelatedPersonHouse() {
		return this.relatedPersonHouse;
	}

	public void setRelatedPersonHouse(String relatedPersonHouse) {
		this.relatedPersonHouse = relatedPersonHouse;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

}
