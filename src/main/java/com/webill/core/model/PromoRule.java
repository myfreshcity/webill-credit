package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

import java.math.BigDecimal;


/**
 *
 * 促销业务规则表
 *
 */
@TableName("promo_rule")
public class PromoRule implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 规则名称*/
	private String ruleName;
	
	/** 使用范围 0-无限制 1-代缴 2-投保 */
	private Integer useScope;

	/** 消费金额下限 */
	private Integer amtLimit;

	/** 区域限制 */
	private String areaLimit;

	/** 保险公司限制 */
	private String insurerLimit;

	/** 每人最大领用数量 */
	private Integer maxNum;

	/** 用户领用资格 0-普通会员 1-铜牌 */
	private Integer userLevel;

	/** 转赠类型 0-不可转赠 1-可转赠 2-只可转赠 */
	private Integer transferType;
	
	/** 是否可与优惠活动同时使用： 0-不可 1-可 2-只可 */
	private Integer activityType;

	/** 优惠效果,0-抵用，1-折扣，2-兑换礼包 */
	private Integer saleResult;

	/** 面值（额度、折扣百分比） */
	private BigDecimal saleAmt;

	/**  */
	private Date createdTime;
	
	/** 状态 ————新建：0；启用：1；禁用：2； */
	private Integer status;


	@TableField(exist = false)
	private Integer useRange;

	@TableField(exist = false)
	private String useScopeStr;
	
	@TableField(exist = false)
	private String saleResultStr;
	
	@TableField(exist = false)
	private String userLevelStr;
	
	@TableField(exist = false)
	private String transferTypeStr;
	
	@TableField(exist = false)
	private String activityTypeStr;
	
	@TableField(exist = false)
	private String amtLimitStr;
	
	@TableField(exist = false)
	private String saleAmtStr;
	
	@TableField(exist = false)
	private String statusStr;
	
	@TableField(exist = false)
	private String flag;
	
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public String getUseScopeStr() {
		return useScopeStr;
	}

	public void setUseScopeStr(String useScopeStr) {
		this.useScopeStr = useScopeStr;
	}

	public String getSaleResultStr() {
		return saleResultStr;
	}

	public void setSaleResultStr(String saleResultStr) {
		this.saleResultStr = saleResultStr;
	}

	public String getUserLevelStr() {
		return userLevelStr;
	}

	public void setUserLevelStr(String userLevelStr) {
		this.userLevelStr = userLevelStr;
	}

	public String getTransferTypeStr() {
		return transferTypeStr;
	}

	public void setTransferTypeStr(String transferTypeStr) {
		this.transferTypeStr = transferTypeStr;
	}

	public String getActivityTypeStr() {
		return activityTypeStr;
	}

	public void setActivityTypeStr(String activityTypeStr) {
		this.activityTypeStr = activityTypeStr;
	}

	public String getAmtLimitStr() {
		return amtLimitStr;
	}

	public void setAmtLimitStr(String amtLimitStr) {
		this.amtLimitStr = amtLimitStr;
	}

	public String getSaleAmtStr() {
		return saleAmtStr;
	}

	public void setSaleAmtStr(String saleAmtStr) {
		this.saleAmtStr = saleAmtStr;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public Integer getUseRange() {
		return useRange;
	}

	public void setUseRange(Integer useRange) {
		this.useRange = useRange;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUseScope() {
		return this.useScope;
	}

	public void setUseScope(Integer useScope) {
		this.useScope = useScope;
	}

	public Integer getAmtLimit() {
		return this.amtLimit;
	}

	public void setAmtLimit(Integer amtLimit) {
		this.amtLimit = amtLimit;
	}

	public String getAreaLimit() {
		return this.areaLimit;
	}

	public void setAreaLimit(String areaLimit) {
		this.areaLimit = areaLimit;
	}

	public String getInsurerLimit() {
		return this.insurerLimit;
	}

	public void setInsurerLimit(String insurerLimit) {
		this.insurerLimit = insurerLimit;
	}

	public Integer getMaxNum() {
		return this.maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public Integer getUserLevel() {
		return this.userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	public Integer getTransferType() {
		return this.transferType;
	}

	public void setTransferType(Integer transferType) {
		this.transferType = transferType;
	}

	public Integer getSaleResult() {
		return this.saleResult;
	}

	public void setSaleResult(Integer saleResult) {
		this.saleResult = saleResult;
	}

	public BigDecimal getSaleAmt() {
		return this.saleAmt;
	}

	public void setSaleAmt(BigDecimal saleAmt) {
		this.saleAmt = saleAmt;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
