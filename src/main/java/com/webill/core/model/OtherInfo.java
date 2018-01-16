package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 其它信息表
 *
 */
@TableName("other_info")
@JsonInclude(Include.NON_EMPTY)
public class OtherInfo implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** id */
	@TableId(type = IdType.AUTO)
	@JSONField(serialize = false)
	private Integer id;

	/** 旅行团号 */
	private String travelNo;

	/** 出行目的，境外旅游险必填（1:旅游、2:商务、3:探亲、4:留学、5:务工、6:其他） */
	private String tripPurpose;

	/** 出行目的地，境外旅游险必填，多个目的地使用中文顿号“、”分隔 */
	private String destination;

	/** 签证办理城市名称，申根签证保险必填 */
	private String visaCity;

	/** 航班号，航意险必填 */
	private String fltNo;

	/** 起飞日期，航意险必填，格式：yyyy-mm-dd */
	@JSONField (format="yyyy-MM-dd")
	private Date fltDate;

	/** 航班出发城市名称 */
	private String flightFromCity;

	/** 航班到达城市名称 */
	private String flightToCity;

	/** 财产所在地，地区编码使用“-”拼接，具体地址拼接在最后，如：440000-440300-440305-深圳动漫园 */
	private String propertyAddress;

	/** 财产所在地邮编 */
	private String propertyPost;

	/** 保单密码 */
	private String policyPassword;

	/** 健康告知id */
	private Integer healthAnswerId;

	/** 续保银行-银行名称（1:工商银行、2:建设银行、3:储蓄银行、4:农业银行、5:民生银行、6:招商银行7:兴业银行、8:中国银行、9:中信银行、10:交通银行、11:平安银行、12:光大银行、） */
	private String renewalBank;

	/** 续保银行-持卡人（须为投保人姓名） */
	private String renewalCardholder;

	/** 续保银行-银行账户 */
	private String renewalAccount;

	/** 续保银行-银行地址 */
	private String renewalBankAddr;

	/** 续期缴费银行-银行名称（1:工商银行、2:建设银行、3:储蓄银行、4:农业银行、5:民生银行、6:招商银行、7:兴业银行、8:中国银行、9:中信银行、10:交通银行、11:平安银行、12:光大银行、） */
	private String renewalPayBank;

	/** 续期缴费银行-持卡人（须为投保人姓名） */
	private String renewalPayCardholder;

	/** 续期缴费银行-银行账户 */
	private String renewalPayAccount;

	/** 续期缴费银行-银行地址 */
	private String renewalPayBankAddr;

	/** 续期缴费银行-开户支行 */
	private String renewalPayBranch;

	/** 代扣缴费银行-银行名称 */
	private String withholdBank;

	/** 代扣缴费银行-持卡人（须为投保人姓名） */
	private String withholdCardholder;

	/** 代扣缴费银行-银行账户 */
	private String withholdAccount;

	/** 宠物主人 1：本人 */
	private String petOwner;

	/** 养犬许可证 */
	private String dogLicense;

	/** 犬类免疫证号码 */
	private String dogImmunelicense;

	/** 宠物犬种类 */
	private String dogSpecies;

	/** 紧急联系人 */
	private String urgencyContact;

	/** 紧急联系人手机号 */
	private String urgencyContactMobile;

	/** 备用日期1，格式：yyyy-mm-dd */
	@JSONField (format="yyyy-MM-dd")
	private Date date1;

	/** 备用日期2，格式：yyyy-mm-dd */
	@JSONField (format="yyyy-MM-dd")
	private Date date2;

	/** 备用字段1 */
	private String text1;

	/** 备用字段2 */
	private String text2;

	/** 备用字段3 */
	private String text3;

	/**  */
	private Date createdTime;


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTravelNo() {
		return this.travelNo;
	}

	public void setTravelNo(String travelNo) {
		this.travelNo = travelNo;
	}

	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getVisaCity() {
		return this.visaCity;
	}

	public void setVisaCity(String visaCity) {
		this.visaCity = visaCity;
	}

	public String getFltNo() {
		return this.fltNo;
	}

	public void setFltNo(String fltNo) {
		this.fltNo = fltNo;
	}

	public Date getFltDate() {
		return this.fltDate;
	}

	public void setFltDate(Date fltDate) {
		this.fltDate = fltDate;
	}

	public String getFlightFromCity() {
		return this.flightFromCity;
	}

	public void setFlightFromCity(String flightFromCity) {
		this.flightFromCity = flightFromCity;
	}

	public String getFlightToCity() {
		return this.flightToCity;
	}

	public void setFlightToCity(String flightToCity) {
		this.flightToCity = flightToCity;
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

	public String getPolicyPassword() {
		return this.policyPassword;
	}

	public void setPolicyPassword(String policyPassword) {
		this.policyPassword = policyPassword;
	}

	public Integer getHealthAnswerId() {
		return healthAnswerId;
	}

	public void setHealthAnswerId(Integer healthAnswerId) {
		this.healthAnswerId = healthAnswerId;
	}

	public String getRenewalBank() {
		return renewalBank;
	}

	public void setRenewalBank(String renewalBank) {
		this.renewalBank = renewalBank;
	}

	public void setTripPurpose(String tripPurpose) {
		this.tripPurpose = tripPurpose;
	}

	public String getRenewalCardholder() {
		return this.renewalCardholder;
	}

	public void setRenewalCardholder(String renewalCardholder) {
		this.renewalCardholder = renewalCardholder;
	}

	public String getRenewalAccount() {
		return this.renewalAccount;
	}

	public void setRenewalAccount(String renewalAccount) {
		this.renewalAccount = renewalAccount;
	}

	public String getRenewalBankAddr() {
		return this.renewalBankAddr;
	}

	public void setRenewalBankAddr(String renewalBankAddr) {
		this.renewalBankAddr = renewalBankAddr;
	}


	public String getRenewalPayBank() {
		return renewalPayBank;
	}

	public void setRenewalPayBank(String renewalPayBank) {
		this.renewalPayBank = renewalPayBank;
	}

	public String getTripPurpose() {
		return tripPurpose;
	}

	public void setPetOwner(String petOwner) {
		this.petOwner = petOwner;
	}

	public String getPetOwner() {
		return petOwner;
	}

	public String getRenewalPayCardholder() {
		return this.renewalPayCardholder;
	}

	public void setRenewalPayCardholder(String renewalPayCardholder) {
		this.renewalPayCardholder = renewalPayCardholder;
	}

	public String getRenewalPayAccount() {
		return this.renewalPayAccount;
	}

	public void setRenewalPayAccount(String renewalPayAccount) {
		this.renewalPayAccount = renewalPayAccount;
	}

	public String getRenewalPayBankAddr() {
		return this.renewalPayBankAddr;
	}

	public void setRenewalPayBankAddr(String renewalPayBankAddr) {
		this.renewalPayBankAddr = renewalPayBankAddr;
	}

	public String getRenewalPayBranch() {
		return this.renewalPayBranch;
	}

	public void setRenewalPayBranch(String renewalPayBranch) {
		this.renewalPayBranch = renewalPayBranch;
	}


	public String getWithholdBank() {
		return withholdBank;
	}

	public void setWithholdBank(String withholdBank) {
		this.withholdBank = withholdBank;
	}

	public String getWithholdCardholder() {
		return this.withholdCardholder;
	}

	public void setWithholdCardholder(String withholdCardholder) {
		this.withholdCardholder = withholdCardholder;
	}

	public String getWithholdAccount() {
		return this.withholdAccount;
	}

	public void setWithholdAccount(String withholdAccount) {
		this.withholdAccount = withholdAccount;
	}

	public String getDogLicense() {
		return this.dogLicense;
	}

	public void setDogLicense(String dogLicense) {
		this.dogLicense = dogLicense;
	}

	public String getDogImmunelicense() {
		return this.dogImmunelicense;
	}

	public void setDogImmunelicense(String dogImmunelicense) {
		this.dogImmunelicense = dogImmunelicense;
	}

	public String getDogSpecies() {
		return this.dogSpecies;
	}

	public void setDogSpecies(String dogSpecies) {
		this.dogSpecies = dogSpecies;
	}

	public String getUrgencyContact() {
		return this.urgencyContact;
	}

	public void setUrgencyContact(String urgencyContact) {
		this.urgencyContact = urgencyContact;
	}

	public String getUrgencyContactMobile() {
		return this.urgencyContactMobile;
	}

	public void setUrgencyContactMobile(String urgencyContactMobile) {
		this.urgencyContactMobile = urgencyContactMobile;
	}

	public Date getDate1() {
		return this.date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return this.date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public String getText1() {
		return this.text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return this.text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getText3() {
		return this.text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
