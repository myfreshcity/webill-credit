package com.webill.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * 用户优惠券表
 *
 */
@TableName("user_coupon")
public class UserCoupon implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 关联coupon的ID */
	private Integer couponId;

	/** 关联user的ID */
	private Integer userId;

	/** 状态 0-新建 1-领用 2-使用 3-失效 */
	private Integer status;

	/** 关联premium_order的ID */
	private Integer orderId;

	/** 失效截止时间 */
	private Date endTime;

	/**  */
	private Date createdTime;
	
	/** 消费金额下限 */
	@ApiModelProperty(value = "消费金额下限", required = true)
	@TableField(exist = false)
	private Integer amtLimit;
	
	/** 使用范围 0-无限制 1-代缴 2-投保 */
	@ApiModelProperty(value = "使用范围 0-无限制 1-代缴 2-投保", required = true)
	@TableField(exist = false)
	private Integer useScope;
	
	/** 优惠券是否可用 0-否 1-是  */
	@ApiModelProperty(value = "优惠券是否可用 0-否 1-是", required = true)
	@TableField(exist = false)
	private Integer cpStatus;
	
	/** 优惠券是否可用 0-否 1-是  */
	@ApiModelProperty(value = "优惠效果,0-抵用，1-折扣，2-兑换礼包", required = true)
	@TableField(exist = false)
	private Integer saleResult;
	
	/** 面值（额度、折扣百分比） */
	@ApiModelProperty(value = "面值（额度、折扣百分比）", required = true)
	@TableField(exist = false)
	private BigDecimal saleAmt;
	
	@ApiModelProperty(value = "优惠券名称", required = true)
	@TableField(exist = false)
	private String cpName;
	
	@ApiModelProperty(value = "优惠券描述", required = true)
	@TableField(exist = false)
	private String cpDesc;
	
	@ApiModelProperty(value = "保险公司限制", required = true)
	@TableField(exist = false)
	private String insurerLimit;
	
	
	public Integer getSaleResult() {
		return saleResult;
	}

	public void setSaleResult(Integer saleResult) {
		this.saleResult = saleResult;
	}

	public String getCpDesc() {
		return cpDesc;
	}

	public void setCpDesc(String cpDesc) {
		this.cpDesc = cpDesc;
	}

	public String getInsurerLimit() {
		return insurerLimit;
	}

	public void setInsurerLimit(String insurerLimit) {
		this.insurerLimit = insurerLimit;
	}

	public Integer getAmtLimit() {
		return amtLimit;
	}

	public void setAmtLimit(Integer amtLimit) {
		this.amtLimit = amtLimit;
	}

	public Integer getUseScope() {
		return useScope;
	}

	public void setUseScope(Integer useScope) {
		this.useScope = useScope;
	}

	public Integer getCpStatus() {
		return cpStatus;
	}

	public void setCpStatus(Integer cpStatus) {
		this.cpStatus = cpStatus;
	}

	public BigDecimal getSaleAmt() {
		return saleAmt;
	}

	public void setSaleAmt(BigDecimal saleAmt) {
		this.saleAmt = saleAmt;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCouponId() {
		return this.couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
