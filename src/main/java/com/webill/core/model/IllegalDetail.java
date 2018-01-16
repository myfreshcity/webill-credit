package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 违章记录子表
 *
 */
@TableName("illegal_detail")
public class IllegalDetail implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 违章主表编号 */
	private Integer illegalId;

	/** 发生时间 */
	private Date occurTime;

	/** 地点 */
	private String address;

	/** 违章内容 */
	private String content;

	/** 违章代码 */
	private String legalnum;

	/** 违章ID */
	private String illid;

	/** 罚款数 */
	private Integer price;
	
	/** 服务费 */
	private Integer serverFee;

	/** 扣分数 */
	private Integer score;

	/** 状态。-1:删除，0-正常 */
	private Integer status;

	/** 代缴订单表编号，对应illegal_order的ID */
	private Integer orderId;

	/**  */
	private Date createdTime;


	
	public Integer getServerFee() {
		return serverFee;
	}

	public void setServerFee(Integer serverFee) {
		this.serverFee = serverFee;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIllegalId() {
		return this.illegalId;
	}

	public void setIllegalId(Integer illegalId) {
		this.illegalId = illegalId;
	}

	public Date getOccurTime() {
		return this.occurTime;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLegalnum() {
		return this.legalnum;
	}

	public void setLegalnum(String legalnum) {
		this.legalnum = legalnum;
	}

	public String getIllid() {
		return illid;
	}

	public void setIllid(String illid) {
		this.illid = illid;
	}

	public Integer getPrice() {
		return this.price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
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

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
