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
	@Id // 主键使用此注解
	private String id;
	
	/** 提交表单获取的token（报告key） */
	@Field // 字段使用此注解
	private String token;
	
	/** 客户ID */
	@Field
	private String cusId;

	/** 姓名 */
	@Field
	private String name;

	/** 身份证 */
	@Field
	private String idCard;

	/** 手机号 */
	@Field
	private String mobile;
	
	/** 信息报告类型：0-标准 1-高级 */
	@Field
	private Integer reportType;

	/** 聚信立报告数据 */
	@Field
	private String jxlReport;
	
	/** 电话邦报告数据 */
	@Field
	private String dhbReport;
	
	/** 最终处理报告数据 */
	@Field
	private String finalReport;

	/** 运营商原始数据 */
	@Field
	private String mobileRaw;

	/** 电商原始数据 */
	@Field
	private String ebusinessRaw;
	
	/** 请求时间 */
	@Field
	private Date applyDate;
	
	/** 报告状态：-1-准备采集 0-采集中 1-采集成功 2-采集超时 */
	@Field
	private Integer status;

	/** 响应状态码 */
	@Field
	private Integer code;

	/* 最近信息报告编号 */
	private String reportKey;

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

	public String getCusId() {
		return cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public String getJxlReport() {
		return jxlReport;
	}

	public void setJxlReport(String jxlReport) {
		this.jxlReport = jxlReport;
	}

	public String getDhbReport() {
		return dhbReport;
	}

	public void setDhbReport(String dhbReport) {
		this.dhbReport = dhbReport;
	}

	public String getReportKey() {
		return reportKey;
	}

	public void setReportKey(String reportKey) {
		this.reportKey = reportKey;
	}
	
	public String getFinalReport() {
		return finalReport;
	}

	public void setFinalReport(String finalReport) {
		this.finalReport = finalReport;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}
	
}
