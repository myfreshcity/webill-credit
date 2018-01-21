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

	/** 交易类型：0-用户报告 1-会员 */
	private Integer payType;

	/** 交易方向：0-购买 1-消费 */
	private Integer payDirec;

	/** 支付方式：0-其他 1-支付宝 2-银联支付 */
	private Integer payWay;

	/** 交易金额／数量 */
	private Integer amount;

	/** 信息关键索引 */
	private String msgKey;

	/** 备注 */
	private String remark;

	/** 状态： -1-逻辑删除 0-正常数据 */
	private Integer tStatus;

	/** 创建时间 */
	private Date createdTime;


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

}
