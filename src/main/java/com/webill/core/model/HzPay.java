package com.webill.core.model;

/** 
 * @ClassName: HzPay 
 * @Description: 慧择支付所需参数
 * @author: WangLongFei
 * @date: 2017年11月30日 下午2:52:56  
 */
public class HzPay{
	/**
	 * @fieldName: id
	 * @fieldType: String
	 * @author: WangLongFei
	 * @date: 2017年12月26日 上午11:19:16 
	 * @Description: 订单id
	 */
	private String id;
	/**
	 * @fieldName: transNo
	 * @fieldType: String
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午2:55:07 
	 * @Description: 交易流水号
	 */
	private String transNo;
	
	/**
	 * @fieldName: partnerId
	 * @fieldType: String
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午2:55:14 
	 * @Description: 开发者身份标识
	 */
	private String partnerId;
	
	/**
	 * @fieldName: insureNums
	 * @fieldType: String
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午2:55:50 
	 * @Description: 投保单号，多个使用“,”分隔
	 */
	private String insureNums;
	
	/**
	 * @fieldName: money
	 * @fieldType: int
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午2:56:07 
	 * @Description: 订单支付总金额（单位：分）
	 */
	private int money;
	
	/**
	 * @fieldName: gateway
	 * @fieldType: int
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午2:56:31 
	 * @Description: 支付类型 1：支付宝 3：银联 21:微信
	 */
	private int gateway;
	
	/**
	 * @fieldName: clientType
	 * @fieldType: int
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午2:58:05 
	 * @Description: 客户端类型1：PC，2：H5
	 */
	private int clientType;
	
	/**
	 * @fieldName: productUrl
	 * @fieldType: String
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午2:58:17 
	 * @Description: 产品详情跳转地址（支付宝PC端可用）
	 */
	private String productUrl;
	
	/**
	 * @fieldName: callBackUrl
	 * @fieldType: String
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午2:58:59 
	 * @Description: 支付成功回调（跳转）地址 在微信中，如果中途退出，
	 *            		回调地址会拼接result=cancel返回 如果支付成功，
	 * 					回调地址会拼接result=ok 可通过该值确认跳转到支付成功/失败界面
	 */
	private String callBackUrl;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	public String getInsureNums() {
		return insureNums;
	}
	public void setInsureNum(String insureNums) {
		this.insureNums = insureNums;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getGateway() {
		return gateway;
	}
	public void setGateway(int gateway) {
		this.gateway = gateway;
	}
	public int getClientType() {
		return clientType;
	}
	public void setClientType(int clientType) {
		this.clientType = clientType;
	}
	public String getCallBackUrl() {
		return callBackUrl;
	}
	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}
}
