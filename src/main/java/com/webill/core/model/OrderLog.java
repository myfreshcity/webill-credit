package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName("order_log")
public class OrderLog implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 订单id */
	private Integer orderId;
	/** 操作人id */
	private Integer operatorId;

	/** 订单状态：-1：已删除、0：默认状态、100：待付款、150：待发货、200：已出单、300：已完成、400：已关闭、900：已失效 */
	private Integer orderTStatus;

	/** 支付状态 0：未支付 1：已支付 2：不能支付 3：扣款中 4：扣款失败 5：扣款成功 */
	private Integer payStatus;

	/** 出单状态 0：未出单 1：已出单 2：延时出单 3：取消出单 */
	private Integer issueStatus;

	/** 备注 */
	private String remark;

	/** 操作时间 */
	private Date createdTime;


	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getOrderTStatus() {
		return this.orderTStatus;
	}

	public void setOrderTStatus(Integer orderTStatus) {
		this.orderTStatus = orderTStatus;
	}

	public Integer getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getIssueStatus() {
		return this.issueStatus;
	}

	public void setIssueStatus(Integer issueStatus) {
		this.issueStatus = issueStatus;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
