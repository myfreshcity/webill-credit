package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 保险产品表
 *
 */
@TableName("product")
@JsonInclude(Include.NON_EMPTY)
public class Product implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** id */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 方案代码 */
	private String caseCode;

	/** 产品名称 */
	private String prodName;

	/** 计划名称 */
	private String planName;

	/** 保险公司名称 */
	private String companyName;

	/** 保险公司logo地址 */
	private String companyLogoUrl;

	/** 是否上架 0：否 1：是 */
	private Integer offShelf;

	/** 一级分类（齐欣云服分类） */
	private Integer fristCategory;

	/** 二类分级（齐欣云服分类） */
	private Integer secondCategory;

	/** 图片Url地址（齐欣云服原始地址） */
	private String imgUrlSrc;

	/** 图片Url地址（平台展示地址） */
	private String imgUrlShow;

	/** 默认价格 */
	private Long defaultPrice;

	/** 最低承保年龄 */
	private String minInsurDate;

	/** 最高承保年龄 */
	private String maxInsurDate;

	/** 保障期限 */
	private String insurDateLimit;

	/** 产品解读 */
	private String prodRead;

	/** 费率表地址 */
	private String premiumTable;

	/** 源产品详情地址（齐欣） */
	private String thirdPUrl;

	/** 产品简述 */
	private String description;

	/** 状态  -1：逻辑删除 0：正常数据 */
	private Integer tStatus;

	/** 更新时间 */
	private Date updatedTime;

	/** 创建时间 */
	private Date createdTime;
	
	/** 支付方式：0-手动支付 1-银行代扣  */
	private Integer autoPay;
	
	/** 为你推荐图片Url地址 */
	private String recommendUrl;
	
	@TableField(exist = false)
	private String catName; //一级分类名称
	
	@TableField(exist = false)
	private String catId; //一级分类ID
	
	@TableField(exist = false)
	private Integer secondCatId; //二级分类ID
	
	@TableField(exist = false)
	private String fristCatName; //齐欣一级类名
	
	@TableField(exist = false)
	private String secondCatName; //齐欣二级类名
	
	@TableField(exist = false)
	private Integer labelId; //标签ID
	
	@TableField(exist = false)
	private String labelName; //标签名称
	
	@TableField(exist = false)
	private String labelArray; //业务标签数组
	
	@TableField(exist = false)
	private String turnSwitch; //是否上架标识
	
	@TableField(exist = false)
	private String protectItems; //保障项目
	
	@TableField(exist = false)
	private String recommend; //为你推荐
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyLogoUrl() {
		return companyLogoUrl;
	}
	public void setCompanyLogoUrl(String companyLogoUrl) {
		this.companyLogoUrl = companyLogoUrl;
	}
	public Integer getOffShelf() {
		return offShelf;
	}
	public void setOffShelf(Integer offShelf) {
		this.offShelf = offShelf;
	}
	public Integer getFristCategory() {
		return fristCategory;
	}
	public void setFristCategory(Integer fristCategory) {
		this.fristCategory = fristCategory;
	}
	public Integer getSecondCategory() {
		return secondCategory;
	}
	public void setSecondCategory(Integer secondCategory) {
		this.secondCategory = secondCategory;
	}
	public String getImgUrlSrc() {
		return imgUrlSrc;
	}
	public void setImgUrlSrc(String imgUrlSrc) {
		this.imgUrlSrc = imgUrlSrc;
	}
	public String getImgUrlShow() {
		return imgUrlShow;
	}
	public void setImgUrlShow(String imgUrlShow) {
		this.imgUrlShow = imgUrlShow;
	}
	public Long getDefaultPrice() {
		return defaultPrice;
	}
	public void setDefaultPrice(Long defaultPrice) {
		this.defaultPrice = defaultPrice;
	}
	public String getMinInsurDate() {
		return minInsurDate;
	}
	public void setMinInsurDate(String minInsurDate) {
		this.minInsurDate = minInsurDate;
	}
	public String getMaxInsurDate() {
		return maxInsurDate;
	}
	public void setMaxInsurDate(String maxInsurDate) {
		this.maxInsurDate = maxInsurDate;
	}
	public String getInsurDateLimit() {
		return insurDateLimit;
	}
	public void setInsurDateLimit(String insurDateLimit) {
		this.insurDateLimit = insurDateLimit;
	}
	public String getProdRead() {
		return prodRead;
	}
	public void setProdRead(String prodRead) {
		this.prodRead = prodRead;
	}
	public String getPremiumTable() {
		return premiumTable;
	}
	public void setPremiumTable(String premiumTable) {
		this.premiumTable = premiumTable;
	}
	public String getThirdPUrl() {
		return thirdPUrl;
	}
	public void setThirdPUrl(String thirdPUrl) {
		this.thirdPUrl = thirdPUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer gettStatus() {
		return tStatus;
	}
	public void settStatus(Integer tStatus) {
		this.tStatus = tStatus;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public Integer getLabelId() {
		return labelId;
	}
	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	public String getTurnSwitch() {
		return turnSwitch;
	}
	public void setTurnSwitch(String turnSwitch) {
		this.turnSwitch = turnSwitch;
	}
	public String getFristCatName() {
		return fristCatName;
	}
	public void setFristCatName(String fristCatName) {
		this.fristCatName = fristCatName;
	}
	public Integer getSecondCatId() {
		return secondCatId;
	}
	public void setSecondCatId(Integer secondCatId) {
		this.secondCatId = secondCatId;
	}
	public String getSecondCatName() {
		return secondCatName;
	}
	public void setSecondCatName(String secondCatName) {
		this.secondCatName = secondCatName;
	}
	public String getLabelArray() {
		return labelArray;
	}
	public void setLabelArray(String labelArray) {
		this.labelArray = labelArray;
	}
	public Integer getAutoPay() {
		return autoPay;
	}
	public void setAutoPay(Integer autoPay) {
		this.autoPay = autoPay;
	}
	public String getProtectItems() {
		return protectItems;
	}
	public void setProtectItems(String protectItems) {
		this.protectItems = protectItems;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public String getRecommendUrl() {
		return recommendUrl;
	}
	public void setRecommendUrl(String recommendUrl) {
		this.recommendUrl = recommendUrl;
	}
}
