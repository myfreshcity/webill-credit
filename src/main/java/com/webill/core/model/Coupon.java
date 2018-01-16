package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 优惠券表
 *
 */
@TableName("coupon")
public class Coupon implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 优惠券名称 */
	private String cpName;

	/** 优惠券描述 */
	private String cpDesc;
	
	/** 发放对象：全部，车牌号，车险到期用户*/
	private String sendTarget;

	/** 优惠券规则编号，对应promo_rule表ID */
	private Integer ruleId;

	/** 计划投放数量 */
	private Integer planAmt;

	/** 领取数量 */
	private Integer obtainAmt;

	/** 使用数量 */
	private Integer useAmt;

	/** 发放方式：系统发放-0；手动发放-1；*/
	private Integer sendWay;
	
	/** 领用方式：直接发放卡包-0；点击领用-1；*/
	private Integer receiveWay;
	
	/** 投放开始时间 */
	private Date sendStartTime;

	/** 投放结束时间 */
	private Date sendEndTime;
	
	/** 有效期天数（和cp_end_time二选一） */
	private Integer cpValidDay;
	
	/** 优惠券有效截至时间 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private Date cpEndTime;
	/** 状态：新建-100；提交审核-200；审核失败-300；审核通过未发放-400；发放中-500；停止发放-600；发放结束-700；作废-800 */
	private Integer status;

	/**  */
	private Date createdTime;

	@TableField(exist = false)
	private Integer sendScope;
	
	@TableField(exist = false)
	private String sendWayStr;
	
	@TableField(exist = false)
	private String sendRange;
	
	@TableField(exist = false)
	private String sendObjStr;
	
	@TableField(exist = false)
	private String validTimeStr;
	
	@TableField(exist = false)
	private String ruleName;
	
	@TableField(exist = false)
	private String useScopeStr;
	
	@TableField(exist = false)
	private String saleAmtStr;
	
	@TableField(exist = false)
	private String amtLimitStr;
	
	@TableField(exist = false)
	private Integer sendPeopleNum;
	
	@TableField(exist = false)
	private String statusStr;
	
	@TableField(exist = false)
	private String sendStartTimeStr;
	
	@TableField(exist = false)
	private String sendEndTimeStr;
	
	@TableField(exist = false)
	private Integer saleResult;
	
	
	public Integer getSaleResult() {
		return saleResult;
	}

	public void setSaleResult(Integer saleResult) {
		this.saleResult = saleResult;
	}

	public String getSendStartTimeStr() {
		return sendStartTimeStr;
	}

	public void setSendStartTimeStr(String sendStartTimeStr) {
		this.sendStartTimeStr = sendStartTimeStr;
	}

	public String getSendEndTimeStr() {
		return sendEndTimeStr;
	}

	public void setSendEndTimeStr(String sendEndTimeStr) {
		this.sendEndTimeStr = sendEndTimeStr;
	}

	public String getSendObjStr() {
		return sendObjStr;
	}

	public void setSendObjStr(String sendObjStr) {
		this.sendObjStr = sendObjStr;
	}

	public String getSendRange() {
		return sendRange;
	}

	public void setSendRange(String sendRange) {
		this.sendRange = sendRange;
	}


	public String getSendTarget() {
		return sendTarget;
	}

	public void setSendTarget(String sendTarget) {
		this.sendTarget = sendTarget;
	}

	public Integer getReceiveWay() {
		return receiveWay;
	}

	public void setReceiveWay(Integer receiveWay) {
		this.receiveWay = receiveWay;
	}

	public String getAmtLimitStr() {
		return amtLimitStr;
	}

	public void setAmtLimitStr(String amtLimitStr) {
		this.amtLimitStr = amtLimitStr;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public Integer getSendScope() {
		return sendScope;
	}

	public void setSendScope(Integer sendScope) {
		this.sendScope = sendScope;
	}

	public String getSendWayStr() {
		return sendWayStr;
	}

	public void setSendWayStr(String sendWayStr) {
		this.sendWayStr = sendWayStr;
	}

	public String getValidTimeStr() {
		return validTimeStr;
	}

	public void setValidTimeStr(String validTimeStr) {
		this.validTimeStr = validTimeStr;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getUseScopeStr() {
		return useScopeStr;
	}

	public void setUseScopeStr(String useScopeStr) {
		this.useScopeStr = useScopeStr;
	}

	public String getSaleAmtStr() {
		return saleAmtStr;
	}

	public void setSaleAmtStr(String saleAmtStr) {
		this.saleAmtStr = saleAmtStr;
	}


	public Integer getSendPeopleNum() {
		return sendPeopleNum;
	}

	public void setSendPeopleNum(Integer sendPeopleNum) {
		this.sendPeopleNum = sendPeopleNum;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getSendWay() {
		return sendWay;
	}

	public void setSendWay(Integer sendWay) {
		this.sendWay = sendWay;
	}

	public Date getSendStartTime() {
		return sendStartTime;
	}

	public void setSendStartTime(Date sendStartTime) {
		this.sendStartTime = sendStartTime;
	}

	public Date getSendEndTime() {
		return sendEndTime;
	}

	public void setSendEndTime(Date sendEndTime) {
		this.sendEndTime = sendEndTime;
	}

	public Integer getCpValidDay() {
		return cpValidDay;
	}

	public void setCpValidDay(Integer cpValidDay) {
		this.cpValidDay = cpValidDay;
	}

	public Date getCpEndTime() {
		return cpEndTime;
	}

	public void setCpEndTime(Date cpEndTime) {
		this.cpEndTime = cpEndTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCpName() {
		return this.cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getCpDesc() {
		return this.cpDesc;
	}

	public void setCpDesc(String cpDesc) {
		this.cpDesc = cpDesc;
	}

	public Integer getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getPlanAmt() {
		return this.planAmt;
	}

	public void setPlanAmt(Integer planAmt) {
		this.planAmt = planAmt;
	}

	public Integer getObtainAmt() {
		return this.obtainAmt;
	}

	public void setObtainAmt(Integer obtainAmt) {
		this.obtainAmt = obtainAmt;
	}

	public Integer getUseAmt() {
		return this.useAmt;
	}

	public void setUseAmt(Integer useAmt) {
		this.useAmt = useAmt;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
