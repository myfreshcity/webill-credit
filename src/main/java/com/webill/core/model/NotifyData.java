package com.webill.core.model;

import java.util.ArrayList;
import java.util.List;

/** 
 * @ClassName: NotifyData 
 * @Description: 慧择回调--基本参数
 * @author: WangLongFei
 * @date: 2017年11月30日 下午2:52:56  
 */
public class NotifyData{
	/**
	 * @fieldName: partnerId
	 * @fieldType: int
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午4:05:08 
	 * @Description: 开发者身份标识
	 */
	private int partnerId;
	
	/**
	 * @fieldName: sign
	 * @fieldType: String
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午4:06:14 
	 * @Description: 开发者用户标识
	 */
	private String partnerUniqueKey;
	
	/**
	 * @fieldName: caseCode
	 * @fieldType: String
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午4:11:43 
	 * @Description: 方案代码，产品唯一标识
	 */
	private String caseCode;
	
	/**
	 * @fieldName: insureNum
	 * @fieldType: String
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午4:12:20 
	 * @Description: 投保单号
	 */
	private String insureNum;
	
	/**
	 * @fieldName: state
	 * @fieldType: String
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午4:12:39 
	 * @Description: 是否支付成功 true：成功 false：失败
	 */
	private boolean state;
	
	/**
	 * @fieldName: payTime
	 * @fieldType: String
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午4:12:54 
	 * @Description: 支付时间，格式：yyyy-MM-dd HH:mm:ss
	 */
	private String payTime;
	
	/**
	 * @fieldName: payTime
	 * @fieldType: long
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午4:13:12 
	 * @Description: 支付金额（单位：分）
	 */
	private long price;
	
	/**
	 * @fieldName: onlinePaymentId
	 * @fieldType: String
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午4:14:37 
	 * @Description: 在线支付网关标识(在线支付网关标识1：支付宝 3：银联 14：财付通 21:微信-11：银行代扣)
	 */
	private String onlinePaymentId;
	
	/**
	 * @fieldName: failMsg
	 * @fieldType: String
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午4:16:59 
	 * @Description: 失败原因
	 */
	private String failMsg;
	
	private List<Policy> policys = new ArrayList<Policy>();


	public List<Policy> getPolicys() {
		return policys;
	}

	public void setPolicys(List<Policy> policys) {
		this.policys = policys;
	}

	public int getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerUniqueKey() {
		return partnerUniqueKey;
	}

	public void setPartnerUniqueKey(String partnerUniqueKey) {
		this.partnerUniqueKey = partnerUniqueKey;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getInsureNum() {
		return insureNum;
	}

	public void setInsureNum(String insureNum) {
		this.insureNum = insureNum;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getOnlinePaymentId() {
		return onlinePaymentId;
	}

	public void setOnlinePaymentId(String onlinePaymentId) {
		this.onlinePaymentId = onlinePaymentId;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}

	
}
