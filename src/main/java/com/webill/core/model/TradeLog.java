package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 交易流水表
 *
 */
@TableName("t_trade_log")
public class TradeLog implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 用户编号，对应user表的ID */
	private Integer userId;
	
	/** 交易流水号 */
	private String transNo;

	/** 交易类型：0-用户报告 1-会员 */
	private Integer payType;

	/** 交易方向：0-购买 1-消费 */
	private Integer payDirec;

	/** 支付方式：0-其他 1-支付宝 2-银联支付 */
	private Integer payWay;
	
	/** 支付状态： 0-未支付 1-支付成功 2-支付失败 */
	private Integer payStatus;

	/** 交易金额 */
	private Long price;

	/** 数量 */
	private Integer amount;

	/** 信息关键索引 */
	private String msgKey;
	
	/** 是否增加次数：0-未增加 1-增加成功 */
	private Integer isAddTimes;

	/** 备注 */
	private String remark;

	/** 状态： -1-逻辑删除 0-正常数据 */
	private Integer tStatus;

	/** 创建时间 */
	private Date createdTime;

	/* 订单时间*/
	@TableField(exist = false)
	private String orderTimeStr;
	
	@TableField(exist = false)
	private String timeFrom;
	
	@TableField(exist = false)
	private String timeTo;
	
	/* 用户信息等级：0-基础版 1-标准版*/
	@TableField(exist = false)
	private Integer infoLevel;
	
	/* 信息次数*/
	@TableField(exist = false)
	private Integer times;
	
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

	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getPayDirec() {
		return this.payDirec;
	}

	public void setPayDirec(Integer payDirec) {
		this.payDirec = payDirec;
	}

	public Integer getPayWay() {
		return this.payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getMsgKey() {
		return this.msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getTStatus() {
		return this.tStatus;
	}

	public void setTStatus(Integer tStatus) {
		this.tStatus = tStatus;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getOrderTimeStr() {
		return orderTimeStr;
	}

	public void setOrderTimeStr(String orderTimeStr) {
		this.orderTimeStr = orderTimeStr;
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

	public Integer getInfoLevel() {
		return infoLevel;
	}

	public void setInfoLevel(Integer infoLevel) {
		this.infoLevel = infoLevel;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Integer getIsAddTimes() {
		return isAddTimes;
	}

	public void setIsAddTimes(Integer isAddTimes) {
		this.isAddTimes = isAddTimes;
	}
	
}
