package com.webill.core.model;
/** 
* @ClassName: EmailBean 
* @Description: 发送邮件的参数实体
* @author ZhangYadong
* @date 2017年11月27日 下午4:32:20 
*/
public class EmailBean {
	private String orderNo; 	//订单号
	private String orderDt; 	//商户付款时间
	private String orderMoney; 	//付款金额
	private String insurComp; 	//保险公司
	private String subject; 	//邮件主题
	private String ftlFile; 	//Freemaker模板文件
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderDt() {
		return orderDt;
	}
	public void setOrderDt(String orderDt) {
		this.orderDt = orderDt;
	}
	public String getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}
	public String getInsurComp() {
		return insurComp;
	}
	public void setInsurComp(String insurComp) {
		this.insurComp = insurComp;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFtlFile() {
		return ftlFile;
	}
	public void setFtlFile(String ftlFile) {
		this.ftlFile = ftlFile;
	}
}
