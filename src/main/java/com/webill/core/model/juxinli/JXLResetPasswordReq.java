package com.webill.core.model.juxinli;

import java.io.Serializable;

/**
 * @ClassName: JXLResetPasswordReq
 * @Description:
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:54:29
 */
public class JXLResetPasswordReq implements Serializable {
	private static final long serialVersionUID = 7296612185521835935L;

	private String token;
	private String account;
	private String password;
	private String captcha;
	private String type;
	private String website;
	private String contact1;
	private String contact2;
	private String contact3;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getContact1() {
		return contact1;
	}

	public void setContact1(String contact1) {
		this.contact1 = contact1;
	}

	public String getContact2() {
		return contact2;
	}

	public void setContact2(String contact2) {
		this.contact2 = contact2;
	}

	public String getContact3() {
		return contact3;
	}

	public void setContact3(String contact3) {
		this.contact3 = contact3;
	}

}
