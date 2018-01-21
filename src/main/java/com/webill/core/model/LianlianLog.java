package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName("t_lianlian_log")
public class LianlianLog implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	private Integer id;

	/** 交易流水号 */
	private String transNo;

	/** 交易类型：1-支付 2-其他 */
	private Integer transType;

	/** 请求响应标识：1-请求 2-响应 */
	private Integer msgType;

	/** 订单号 */
	private String msgKey;

	/** 请求数据 */
	private String reqData;

	/** 连连响应数据 */
	private String resData;

	/** 状态： -1-逻辑删除 0-正常数据 */
	private Integer tStatus;

	/** 更新时间 */
	private Date updatedTime;

	/** 创建时间 */
	private Date createdTime;


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTransNo() {
		return this.transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public Integer getTransType() {
		return this.transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}

	public Integer getMsgType() {
		return this.msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getMsgKey() {
		return this.msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public String getReqData() {
		return this.reqData;
	}

	public void setReqData(String reqData) {
		this.reqData = reqData;
	}

	public String getResData() {
		return this.resData;
	}

	public void setResData(String resData) {
		this.resData = resData;
	}

	public Integer getTStatus() {
		return this.tStatus;
	}

	public void setTStatus(Integer tStatus) {
		this.tStatus = tStatus;
	}

	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
