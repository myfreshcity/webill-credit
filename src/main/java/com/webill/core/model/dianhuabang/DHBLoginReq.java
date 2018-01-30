package com.webill.core.model.dianhuabang;

public class DHBLoginReq {
	private String sid;//会话唯一标识(必填)
	private String tel;//要查询的手机号	(必填)
	private String pinPwd;//手机号服务密码
	private String fullName;//客户姓名
	private String idCard;//客户身份证号
	private String smsCode;//手机验证码
	private String captchaCode;//图形验证码
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPinPwd() {
		return pinPwd;
	}
	public void setPinPwd(String pinPwd) {
		this.pinPwd = pinPwd;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getCaptchaCode() {
		return captchaCode;
	}
	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}
}
