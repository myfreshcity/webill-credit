package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.io.filefilter.FalseFileFilter;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 客户表
 *
 */
@TableName("t_customer")
public class Customer implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 客户真实姓名 */
	private String realName;

	/** 身份证号码 */
	private String idNo;

	/** 手机号 */
	private String mobileNo;

	/** 关联用户ID，获取信息时写入用户ID，同企业员工共享客户信息 */
	private Integer userId;

	/** 获取信息企业ID */
	private Integer comId;

	/** 家庭地址 */
	private String homeAddr;

	/** 家庭电话 */
	private String homeTel;

	/** 工作地址 */
	private String workAddr;

	/** 工作电话 */
	private String workTel;

	/** 报告获取次数 */
	private Integer refreshTimes;

	/** 最近信息报告编号 */
	private String latestReportKey;

	/** 最近信息报告类型：0-标准 1-高级 */
	private Integer latestReportType;

	/** 最近报告获取时间 */
	private Date latestReportTime;

	/** 最近报告状态：-1-准备抓取 0-抓取中 1-抓取成功 2-抓取超时 */
	private String latestReportStatus;

	/** 状态： -1-逻辑删除 0-正常数据 */
	private Integer tStatus;

	/** 更新时间 */
	private Date updatedTime;

	/** 创建时间 */
	private Date createdTime;

	/* 排序方式：1-时间倒序排序 2-查询次数高低排序*/
	@TableField(exist = false)
	private Integer sortWay = 1; 
	
	/* 每页显示条数 */
	@TableField(exist = false)
	private int start;

	/* 总页数 */
	@TableField(exist = false)
	private int length;
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdNo() {
		return this.idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getComId() {
		return this.comId;
	}

	public void setComId(Integer comId) {
		this.comId = comId;
	}

	public String getHomeAddr() {
		return this.homeAddr;
	}

	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

	public String getHomeTel() {
		return this.homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public String getWorkAddr() {
		return this.workAddr;
	}

	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
	}

	public String getWorkTel() {
		return this.workTel;
	}

	public void setWorkTel(String workTel) {
		this.workTel = workTel;
	}

	public Integer getRefreshTimes() {
		return this.refreshTimes;
	}

	public void setRefreshTimes(Integer refreshTimes) {
		this.refreshTimes = refreshTimes;
	}

	public String getLatestReportKey() {
		return this.latestReportKey;
	}

	public void setLatestReportKey(String latestReportKey) {
		this.latestReportKey = latestReportKey;
	}

	public Integer getLatestReportType() {
		return this.latestReportType;
	}

	public void setLatestReportType(Integer latestReportType) {
		this.latestReportType = latestReportType;
	}

	public Date getLatestReportTime() {
		return this.latestReportTime;
	}

	public void setLatestReportTime(Date latestReportTime) {
		this.latestReportTime = latestReportTime;
	}

	public String getLatestReportStatus() {
		return this.latestReportStatus;
	}

	public void setLatestReportStatus(String latestReportStatus) {
		this.latestReportStatus = latestReportStatus;
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
