package com.webill.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

import com.webill.app.util.EnumString;
import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 保险订单信息
 *
 */
@TableName("premium_order")
public class PremiumOrder implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 用户编号,对应user的ID */
	private Integer userId;

	/** 车辆编号,对应car_info的编号 */
	private Integer carId;
	
	/**	父级订单id */
	private Integer parentId;

	/** 车牌号 */
	private String licenseNo;
	
	/** 订单编号*/
	private String orderNo;

	/** 投保单号 */
	private String preInsureNo;

	/** 保单号 */
	private String insureNo;

	/** 交易号（保险付款通知书编号） */
	private String transacNo;

	/** 行驶城市 */
	private String city;

	/** 交强险起保日期 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date ciStartDate;

	/** 商业险起保日期 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date biStartDate;
	
	/** 交强险结束日期 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date ciEndDate;
	
	/** 商业险结束日期 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date biEndDate;

	/** 交强险保费 */
	private BigDecimal ciValue;

	/** 商业险保费 */
	private BigDecimal biValue;

	/** 车船税 */
	private BigDecimal taxValue;

	/** 交强险保险人 */
	private String ciInsurerCom;

	/** 商业险保险人 */
	private String biInsurerCom;

	/** 车主姓名 */
	private String ownerName;

	/** 车主身份证号 */
	private String ownerIdNo;

	/**  */
	private String insuredName;

	/** 被保人身份证号 */
	private String insuredIdNo;

	/** 投保人姓名 */
	private String applicantName;

	/** 投保人身份证号 */
	private String applicantIdNo;

	/** 状态 -1-废弃 0-已创建 1-确认方案 2-完成个人信息 3-支付完成 */
	private Integer status;

	/** 配送地址，关联user_contact的ID */
	private Integer contactId;

	/** 上一期名义保费 */
	private BigDecimal lastValue;

	/** 本期实收保费（折扣后） */
	private BigDecimal prmValue;

	/** 最低保险人 */
	private String lowestInsurer;
	
	/** 支付凭证路径 */
	private String payImgUrl;
	
	/** 支付类型 */
	private Integer payType;
	
	/** 备注 */
	private String remark;
	
	/** 最低保费（折扣后） */
	private BigDecimal lowestValue;

	/**  */
	private Date createdTime;
	
	@TableField(exist = false)
	private String prmEndTime;
	
	@TableField(exist = false)
	private String isSuccess;
	
	@TableField(exist = false)
	private String openId;
	
	@TableField(exist = false)
	private String discount;
	
	@TableField(exist = false)
	private BigDecimal discountMoney;
	
	@TableField(exist = false)
	private String content;
	
	
	@TableField(exist = false)
	private String contentFlag;
	
	@TableField(exist = false)
	private PersonMsg personMsg;
	
	@TableField(exist = false)
	private List<PremiumDetail> pdlist = new ArrayList<PremiumDetail>();
	
	@TableField(exist = false)
	private List<PremiumDetail> jcpdlist = new ArrayList<PremiumDetail>();
	
	@TableField(exist = false)
	private List<PremiumDetail> fjpdlist = new ArrayList<PremiumDetail>();
	
	@TableField(exist = false)
	private List<PremiumDetail> jqpdlist = new ArrayList<PremiumDetail>();
	
	@TableField(exist = false)
	private List<UserContact> uclist = new ArrayList<UserContact>();
	
	@TableField(exist = false)
	private List<EnumString> eslist = new ArrayList<EnumString>();
	
	@TableField(exist = false)
	private String timeFrom;
	
	@TableField(exist = false)
	private String timeTo;

	@TableField(exist = false)
	private String orderStatus;
	
	@TableField(exist = false)
	private String carOwner;
	
	@TableField(exist = false)
	private String brandName;
	
	@TableField(exist = false)
	private String weixinNick;
	
	@TableField(exist = false)
	private String mobileNo;
	
	@TableField(exist = false)
	private String mobile;
	
	@TableField(exist = false)
	private String insurerCom;
	
	@TableField(exist = false)
	private String amountstr;
	
	@TableField(exist = false)
	private String prmValueStr;
	
	@TableField(exist = false)
	private String engineNo;
	
	@TableField(exist = false)
	private String frameNo;
	
	@TableField(exist = false)
	private MultipartFile payImg;
	
	@TableField(exist = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date enrollDate;
	
	public MultipartFile getPayImg() {
		return payImg;
	}
	

	public String getPayImgUrl() {
		return payImgUrl;
	}

	public void setPayImgUrl(String payImgUrl) {
		this.payImgUrl = payImgUrl;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Date getCiEndDate() {
		return ciEndDate;
	}

	public void setCiEndDate(Date ciEndDate) {
		this.ciEndDate = ciEndDate;
	}

	public Date getBiEndDate() {
		return biEndDate;
	}

	public void setBiEndDate(Date biEndDate) {
		this.biEndDate = biEndDate;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getFrameNo() {
		return frameNo;
	}

	public void setPayImg(MultipartFile payImg) {
		this.payImg = payImg;
	}


	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	public String getPrmEndTime() {
		return prmEndTime;
	}


	public void setPrmEndTime(String prmEndTime) {
		this.prmEndTime = prmEndTime;
	}


	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public List<EnumString> getEslist() {
		return eslist;
	}

	public void setEslist(List<EnumString> eslist) {
		this.eslist = eslist;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<UserContact> getUclist() {
		return uclist;
	}

	public void setUclist(List<UserContact> uclist) {
		this.uclist = uclist;
	}

	public String getAmountstr() {
		return amountstr;
	}

	public void setAmountstr(String amountstr) {
		this.amountstr = amountstr;
	}

	public String getPrmValueStr() {
		return prmValueStr;
	}

	public void setPrmValueStr(String prmValueStr) {
		this.prmValueStr = prmValueStr;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getInsurerCom() {
		return insurerCom;
	}

	public void setInsurerCom(String insurerCom) {
		this.insurerCom = insurerCom;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getWeixinNick() {
		return weixinNick;
	}

	public void setWeixinNick(String weixinNick) {
		this.weixinNick = weixinNick;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getCarOwner() {
		return carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public PersonMsg getPersonMsg() {
		return personMsg;
	}

	public void setPersonMsg(PersonMsg personMsg) {
		this.personMsg = personMsg;
	}

	public String getContentFlag() {
		return contentFlag;
	}

	public void setContentFlag(String contentFlag) {
		this.contentFlag = contentFlag;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<PremiumDetail> getJcpdlist() {
		return jcpdlist;
	}

	public void setJcpdlist(List<PremiumDetail> jcpdlist) {
		this.jcpdlist = jcpdlist;
	}

	public List<PremiumDetail> getFjpdlist() {
		return fjpdlist;
	}

	public void setFjpdlist(List<PremiumDetail> fjpdlist) {
		this.fjpdlist = fjpdlist;
	}

	public List<PremiumDetail> getJqpdlist() {
		return jqpdlist;
	}

	public void setJqpdlist(List<PremiumDetail> jqpdlist) {
		this.jqpdlist = jqpdlist;
	}

	public List<PremiumDetail> getPdlist() {
		return pdlist;
	}

	public void setPdlist(List<PremiumDetail> pdlist) {
		this.pdlist = pdlist;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCarId() {
		return this.carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public String getLicenseNo() {
		return this.licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getPreInsureNo() {
		return this.preInsureNo;
	}

	public void setPreInsureNo(String preInsureNo) {
		this.preInsureNo = preInsureNo;
	}

	public String getInsureNo() {
		return this.insureNo;
	}

	public void setInsureNo(String insureNo) {
		this.insureNo = insureNo;
	}

	public String getTransacNo() {
		return this.transacNo;
	}

	public void setTransacNo(String transacNo) {
		this.transacNo = transacNo;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getCiStartDate() {
		return this.ciStartDate;
	}

	public void setCiStartDate(Date ciStartDate) {
		this.ciStartDate = ciStartDate;
	}

	public Date getBiStartDate() {
		return this.biStartDate;
	}

	public void setBiStartDate(Date biStartDate) {
		this.biStartDate = biStartDate;
	}

	public BigDecimal getCiValue() {
		return this.ciValue;
	}

	public void setCiValue(BigDecimal ciValue) {
		this.ciValue = ciValue;
	}

	public BigDecimal getBiValue() {
		return this.biValue;
	}

	public void setBiValue(BigDecimal biValue) {
		this.biValue = biValue;
	}

	public BigDecimal getTaxValue() {
		return this.taxValue;
	}

	public void setTaxValue(BigDecimal taxValue) {
		this.taxValue = taxValue;
	}

	public String getCiInsurerCom() {
		return this.ciInsurerCom;
	}

	public void setCiInsurerCom(String ciInsurerCom) {
		this.ciInsurerCom = ciInsurerCom;
	}

	public String getBiInsurerCom() {
		return this.biInsurerCom;
	}

	public void setBiInsurerCom(String biInsurerCom) {
		this.biInsurerCom = biInsurerCom;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerIdNo() {
		return this.ownerIdNo;
	}

	public void setOwnerIdNo(String ownerIdNo) {
		this.ownerIdNo = ownerIdNo;
	}

	public String getInsuredName() {
		return this.insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getInsuredIdNo() {
		return this.insuredIdNo;
	}

	public void setInsuredIdNo(String insuredIdNo) {
		this.insuredIdNo = insuredIdNo;
	}

	public String getApplicantName() {
		return this.applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicantIdNo() {
		return this.applicantIdNo;
	}

	public void setApplicantIdNo(String applicantIdNo) {
		this.applicantIdNo = applicantIdNo;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getContactId() {
		return this.contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public BigDecimal getLastValue() {
		return this.lastValue;
	}

	public void setLastValue(BigDecimal lastValue) {
		this.lastValue = lastValue;
	}

	public BigDecimal getPrmValue() {
		return this.prmValue;
	}

	public void setPrmValue(BigDecimal prmValue) {
		this.prmValue = prmValue;
	}

	public String getLowestInsurer() {
		return this.lowestInsurer;
	}

	public void setLowestInsurer(String lowestInsurer) {
		this.lowestInsurer = lowestInsurer;
	}

	public BigDecimal getLowestValue() {
		return this.lowestValue;
	}

	public void setLowestValue(BigDecimal lowestValue) {
		this.lowestValue = lowestValue;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
