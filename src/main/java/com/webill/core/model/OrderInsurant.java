package com.webill.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 被保人信息表
 *
 */
@TableName("order_insurant")
@JsonInclude(Include.NON_EMPTY)
public class OrderInsurant implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** id */
	@TableId(type = IdType.AUTO)
	@JSONField(serialize = false)
	@JsonIgnore
	private Integer id;

	/** 订单id对应order表的主键id */
	@JSONField(serialize = false)
	private Integer orderId;

	/** 中文名 */
	private String cName;

	/** 拼音/英文名 */
	private String eName;
	
	/** 证件类型（取值参考附录1） */
	private String cardType;

	/** 证件号码 */
	@JSONField(serialize = false)
	private String cardNumber;
	
	/** 性别 0：女 1：男 */
	private String sex;
	
	/** 出生日期 格式：yyyy-MM-dd */
	@JSONField(format = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date birthday;

	/** 国籍 */
	private String country;

	/** 居住省市名称 */
	private String provCityText;

	/** 职业名称 */
	@JSONField(serialize = false)
	private String jobText;

	/** 家庭地址 */
	private String homeAddress;

	/** 家庭地址邮编 */
	private String homePost;

	/** 办公地址 */
	private String officeAddress;

	/** 办公地址邮编 */
	private String officePost;

	/** 办公电话 */
	private String tel;

	/** 常用联系地址 */
	private String contactAddress;

	/** 常用联系地址邮编 */
	private String contactPost;

	/** 手机号码 */
	private String mobile;

	/** 电子邮箱 */
	private String email;

	/** 婚姻状态 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他 */
	private String marryState;

	/** 房屋类型名称 */
	private String houseTypeName;

	/** 财产所在地 */
	private String propertyAddress;

	/** 财产所在地邮编 */
	private String propertyPost;

	/** 是否有医保 0：否 1：是 */
	private String haveMedical;

	/** 身高 */
	private String height;

	/** 体重 */
	private String weight;

	/** 年收入 */
	private String yearlyIncome;

	/** 购买份数 */
	@JSONField(serialize = false)
	private Integer buyAmount;

	/** 保险公司保单号 */
	private String policyNum;
	
	private String fiscalResidentIdentity;

	/** 支付价格（单位：分） */
	private long singlePrice;

	/** 受益人类型 0：无 1：法定 2：指定 */
	private Integer beneficiaryType;

	/** 更新时间 */
	private Date updatedTime;

	/**  */
	private Date createdTime;
	

	/** 与投保人关系（取值参考附录3） */
	private String relationId;
	
	@TableField(exist = false)
	private List<OrderBeneficiary> beneficiaryInfos=new ArrayList<OrderBeneficiary>();

	/** 购买份数 */
	@TableField(exist=false)
	private int count;
	
	/** 职业信息，职业Id使用“-”拼接 */
	private String job;
	
	/** 居住省市，地区编码使用“-”拼接 */
	@TableField(exist=false)
	private String provCityId;
	
	/** 证件有效期 */
	@JSONField(format = "yyyy-MM-dd")
	@TableField(exist=false)
	private Date cardPeriod;
	
	/** 被保人与投保人关系 */
	@TableField(exist=false)
	private String relationName;
	
	/** 证件号码 */
	@TableField(exist=false)
	private String cardCode;
	
	/** 被保人id，由开发者传递（同一单此字段不能重复，取值须在int范围内） */
	@TableField(exist = false)
	private int insurantId;
	
	/** 证件名称 */
	@TableField(exist=false)
	private String cardName;
	
	
	public String getFiscalResidentIdentity() {
		return fiscalResidentIdentity;
	}

	public void setFiscalResidentIdentity(String fiscalResidentIdentity) {
		this.fiscalResidentIdentity = fiscalResidentIdentity;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public int getInsurantId() {
		return insurantId;
	}

	public void setInsurantId(int insurantId) {
		this.insurantId = insurantId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getProvCityId() {
		return provCityId;
	}

	public void setProvCityId(String provCityId) {
		this.provCityId = provCityId;
	}

	public Date getCardPeriod() {
		return cardPeriod;
	}

	public void setCardPeriod(Date cardPeriod) {
		this.cardPeriod = cardPeriod;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getEName() {
		return this.eName;
	}

	public void setEName(String eName) {
		this.eName = eName;
	}

	public String getCardName() {
		return this.cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getSex() {
		return sex;
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

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvCityText() {
		return this.provCityText;
	}

	public void setProvCityText(String provCityText) {
		this.provCityText = provCityText;
	}

	public String getJobText() {
		return this.jobText;
	}

	public void setJobText(String jobText) {
		this.jobText = jobText;
	}

	public String getHomeAddress() {
		return this.homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getHomePost() {
		return this.homePost;
	}

	public void setHomePost(String homePost) {
		this.homePost = homePost;
	}

	public String getOfficeAddress() {
		return this.officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getOfficePost() {
		return this.officePost;
	}

	public void setOfficePost(String officePost) {
		this.officePost = officePost;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMarryState() {
		return marryState;
	}

	public void setMarryState(String marryState) {
		this.marryState = marryState;
	}

	public String getHouseTypeName() {
		return this.houseTypeName;
	}

	public void setHouseTypeName(String houseTypeName) {
		this.houseTypeName = houseTypeName;
	}

	public String getPropertyAddress() {
		return this.propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public String getPropertyPost() {
		return this.propertyPost;
	}

	public void setPropertyPost(String propertyPost) {
		this.propertyPost = propertyPost;
	}

	public String getHaveMedical() {
		return haveMedical;
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

	public String getYearlyIncome() {
		return this.yearlyIncome;
	}

	public void setYearlyIncome(String yearlyIncome) {
		this.yearlyIncome = yearlyIncome;
	}

	public Integer getBuyAmount() {
		return this.buyAmount;
	}

	public void setBuyAmount(Integer buyAmount) {
		this.buyAmount = buyAmount;
	}

	public String getPolicyNum() {
		return this.policyNum;
	}

	public void setPolicyNum(String policyNum) {
		this.policyNum = policyNum;
	}

	public long getSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(long singlePrice) {
		this.singlePrice = singlePrice;
	}

	public String getRelationName() {
		return this.relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public Integer getBeneficiaryType() {
		return this.beneficiaryType;
	}

	public void setBeneficiaryType(Integer beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
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

	public List<OrderBeneficiary> getBeneficiaryInfos() {
		return beneficiaryInfos;
	}

	public void setBeneficiaryInfos(List<OrderBeneficiary> beneficiaryInfos) {
		this.beneficiaryInfos = beneficiaryInfos;
	}

	
}
