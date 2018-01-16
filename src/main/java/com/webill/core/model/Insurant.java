package com.webill.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName: Insurant
 * @Description: 承保所需参数
 * @author: WangLongFei
 * @date: 2017年12月1日 下午4:27:51
 */
public class Insurant {
	/* 被保人id，由开发者传递（同一单此字段不能重复，取值须在int范围内） */
	private int insurantId;

	/* 中文名 */
	private String cName;

	/* 拼音或英文名，境外旅游险必填 */
	private String eName;

	/* 证件类型（取值参考附录1） */
	private String cardType;

	/* 证件号 */
	private String cardCode;

	/* 性别 0：女 1：男 */
	private String sex;

	/* 出生日期 格式：yyyy-MM-dd */
	@JSONField(format = "yyyy-MM-dd")
	private Date birthday;

	/* 与投保人关系（取值参考附录3） */
	private String relationId;

	/* 购买份数 */
	private int count;
	
	/* 产品单价（单位：分） */
	private long singlePrice;
	
	/* 手机号码 */
	private String mobile;
	
	/* 职业信息，职业Id使用“-”拼接 */
	private String job;
	
	/* 国籍 */
	private String country;
	
	/* 居住省市，地区编码使用“-”拼接 */
	private String provCityId;
	
	/* 联系地址 */
	private String contactAddress;
	
	/* 联系地址邮编 */
	private String contactPost;
	/* 证件有效期 */
	@JSONField(format = "yyyy-MM-dd")
	private Date cardPeriod;
	
	/* 婚姻状态 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他 */
	private String marryState;
	
	/* 年收入 */
	private String yearlyIncome;
	
	/* 办公地址 */
	private String officeAddress;
	
	/* 办公地址邮编 */
	private String officePost;
	
	/* 办公电话 */
	private String tel;
	
	/* 是否有医保 0：否 1：是 */
	private String haveMedical;
	
	/* 身高 */
	private String height;
	
	/* 体重 */
	private String weight;

	private String email;

	/* 受益人列表 */
	private List<OrderBeneficiary> beneficiaryInfos = new ArrayList<OrderBeneficiary>();

	public int getInsurantId() {
		return insurantId;
	}

	public void setInsurantId(int insurantId) {
		this.insurantId = insurantId;
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

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public long getSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(long singlePrice) {
		this.singlePrice = singlePrice;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvCityId() {
		return provCityId;
	}

	public void setProvCityId(String provCityId) {
		this.provCityId = provCityId;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactPost() {
		return contactPost;
	}

	public void setContactPost(String contactPost) {
		this.contactPost = contactPost;
	}

	public Date getCardPeriod() {
		return cardPeriod;
	}

	public void setCardPeriod(Date cardPeriod) {
		this.cardPeriod = cardPeriod;
	}

	public String getMarryState() {
		return marryState;
	}

	public void setMarryState(String marryState) {
		this.marryState = marryState;
	}

	public String getYearlyIncome() {
		return yearlyIncome;
	}

	public void setYearlyIncome(String yearlyIncome) {
		this.yearlyIncome = yearlyIncome;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getOfficePost() {
		return officePost;
	}

	public void setOfficePost(String officePost) {
		this.officePost = officePost;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getHaveMedical() {
		return haveMedical;
	}

	public void setHaveMedical(String haveMedical) {
		this.haveMedical = haveMedical;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<OrderBeneficiary> getBeneficiaryInfos() {
		return beneficiaryInfos;
	}

	public void setBeneficiaryInfos(List<OrderBeneficiary> beneficiaryInfos) {
		this.beneficiaryInfos = beneficiaryInfos;
	}
}
