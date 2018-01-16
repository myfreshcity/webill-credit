package com.webill.core.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @ClassName: Applicant
 * @Description: 承保所需参数
 * @author: WangLongFei
 * @date: 2017年12月1日 下午4:27:51
 */
public class Applicant {
	private String id;
	private String cName;
	private String eName;
	private String cardCode;
	private String country;
	private String contactPost;

	 /**投保人类型 0：个人（默认） 1：公司*/
	private int applicantType;

	/**婚姻状态 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他*/
	private String marryState;

	private String yearlyIncome;

	private String officeAddress;

	private String officePost;

	private String tel;

	private String height;

	private String weight;

	/**1：身份证、2:护照、3：出生证、4：驾照、5：港澳通行证、6：军官证、7：台胞证、8：警官证、9：港澳台回乡证10：组织机构代码证、11：营业执照、12：税务登记号、13：三证合一、14：统一信用代码证、99：其他、*/
	private String cardType;

	private String sex;
	
	@JSONField(format = "yyyy-MM-dd")
	private Date birthday;

	@JSONField(format = "yyyy-MM-dd")
	private Date cardPeriod;

	private String mobile;

	private String email;

	private String provCityId;

	private String contactAddress;

	private String job;

	private String haveMedical;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getContactPost() {
		return contactPost;
	}

	public void setContactPost(String contactPost) {
		this.contactPost = contactPost;
	}

	public int getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(int applicantType) {
		this.applicantType = applicantType;
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

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
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

	public String getHaveMedical() {
		return haveMedical;
	}

	public void setHaveMedical(String haveMedical) {
		this.haveMedical = haveMedical;
	}

	public Date getCardPeriod() {
		return cardPeriod;
	}

	public void setCardPeriod(Date cardPeriod) {
		this.cardPeriod = cardPeriod;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

}
