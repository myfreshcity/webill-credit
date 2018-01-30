package com.webill.core.model.juxinli;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName: JXLCollectReq
 * @Description:
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:46:38
 */
public class JXLCollectReq {

	private String token;

	private String account;

	private String password;

	private String captcha;

	private String queryPwd;

	private String type;

	private String website;
	
	/** 客户主键ID */
	@JSONField(serialize = false)
	private Integer cusId;
	
	/** 临时信息报告类型：0-基础 1-标准 */
	@JSONField(serialize = false)
	private Integer temReportType;

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

	public String getQueryPwd() {
		return queryPwd;
	}

	public void setQueryPwd(String queryPwd) {
		this.queryPwd = queryPwd;
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

	public Integer getCusId() {
		return cusId;
	}

	public void setCusId(Integer cusId) {
		this.cusId = cusId;
	}

	public Integer getTemReportType() {
		return temReportType;
	}

	public void setTemReportType(Integer temReportType) {
		this.temReportType = temReportType;
	}

/*	public String toJsonString() {
		return JSONObject.fromObject(this).toString();

	}
*/
}
