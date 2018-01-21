package com.webill.core.model.juxinli;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @ClassName: Report
 * @Description: 本类用来展示MongoDB报告实体类映射的使用
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:51:09
 */
@Document(collection = "coll_report")
public class Report implements Serializable {

	private static final long serialVersionUID = 1L;
	// 主键使用此注解
	@Id
	private String id;

	// 字段使用此注解
	@Field
	private String token;

	@Field
	private String name;

	@Field
	private String idCard;

	@Field
	private String mobile;

	@Field
	private String finalReport;

	@Field
	private String mobileRaw;

	@Field
	private String ebusinessRaw;

	@Field
	private Date applyDate;

	@Field
	private Integer status;

	@Field
	private Integer code;

	@Field
	private String applySn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFinalReport() {
		return finalReport;
	}

	public void setFinalReport(String finalReport) {
		this.finalReport = finalReport;
	}

	public String getMobileRaw() {
		return mobileRaw;
	}

	public void setMobileRaw(String mobileRaw) {
		this.mobileRaw = mobileRaw;
	}

	public String getEbusinessRaw() {
		return ebusinessRaw;
	}

	public void setEbusinessRaw(String ebusinessRaw) {
		this.ebusinessRaw = ebusinessRaw;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getApplySn() {
		return applySn;
	}

	public void setApplySn(String applySn) {
		this.applySn = applySn;
	}

}
