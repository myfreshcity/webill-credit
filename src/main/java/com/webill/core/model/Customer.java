package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
	
	/** 家庭地址省份城市区县 */
	private String homeAddr;

	/** 家庭地址码 */
	private String homeAddrCode;

	/** 家庭地址详情 */
	private String homeAddrDetail;

	/** 家庭电话 */
	private String homeTel;

	/** 工作地址省份城市区县 */
	private String workAddr;

	/** 工作地址码 */
	private String workAddrCode;

	/** 工作地址详情 */
	private String workAddrDetail;

	/** 工作电话 */
	private String workTel;
	
	/** 手机服务密码 */
	private String servicePwd;
	
	/** 报告获取次数 */
	private Integer refreshTimes;

	/** 最近信息报告编号 */
	private String latestReportKey;

	/** 最近信息报告类型：0-基础 1-标准 */
	private Integer latestReportType;
	
	/** 临时信息报告类型：0-基础 1-标准 */
	private Integer temReportType;

	/** 最近报告获取时间 */
	private Date latestReportTime;

	/** 最近聚信立报告状态：-1-准备采集 0-采集中 1-采集成功 2-采集失败 */
	private Integer latestJxlRepStatus;

	/** 最近电话邦报告状态：-1-准备采集 0-采集中 1-采集成功 2-采集失败 */
	private Integer latestDhbRepStatus;
	
	/** 最近报告状态：-1-准备采集 0-采集中 1-采集成功 2-采集失败 */
	private Integer latestReportStatus;

	/** 状态： -1-逻辑删除 0-正常数据 */
	private Integer tStatus;

	/** 更新时间 */
	private Date updatedTime;

	/** 创建时间 */
	private Date createdTime;

	/* 最近报告获取时间-年月日*/
	@TableField(exist = false)
	private String latestReportTimeStr;
	
	/* 排序方式： 1-时间倒序排序 2-查询次数高低排序*/
	@TableField(exist = false)
	private Integer sortWay;
	
	/* 客户信息标准版可用次数*/
	@TableField(exist = false)
	private Integer standardTimes;
	
	/* 客户信息高级版可用次数*/
	@TableField(exist = false)
	private Integer advancedTimes;
	
	/* 联系人集合*/
	@TableField(exist = false)
	private List<CusContact> contacts;
	
	/* 地址三级联动字符串*/
	@TableField(exist = false)
	private String areaJson;
	
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
		return homeAddr;
	}

	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

	public String getHomeAddrCode() {
		return homeAddrCode;
	}

	public void setHomeAddrCode(String homeAddrCode) {
		this.homeAddrCode = homeAddrCode;
	}

	public String getHomeAddrDetail() {
		return homeAddrDetail;
	}

	public void setHomeAddrDetail(String homeAddrDetail) {
		this.homeAddrDetail = homeAddrDetail;
	}

	public String getHomeTel() {
		return this.homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public String getWorkAddr() {
		return workAddr;
	}

	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
	}

	public String getWorkAddrCode() {
		return workAddrCode;
	}

	public void setWorkAddrCode(String workAddrCode) {
		this.workAddrCode = workAddrCode;
	}

	public String getWorkAddrDetail() {
		return workAddrDetail;
	}

	public void setWorkAddrDetail(String workAddrDetail) {
		this.workAddrDetail = workAddrDetail;
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
	
	public Integer getTemReportType() {
		return temReportType;
	}

	public void setTemReportType(Integer temReportType) {
		this.temReportType = temReportType;
	}

	public Date getLatestReportTime() {
		return this.latestReportTime;
	}

	public void setLatestReportTime(Date latestReportTime) {
		this.latestReportTime = latestReportTime;
	}

	public Integer getLatestReportStatus() {
		return latestReportStatus;
	}

	public void setLatestReportStatus(Integer latestReportStatus) {
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

	public List<CusContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<CusContact> contacts) {
		this.contacts = contacts;
	}

	public Integer getStandardTimes() {
		return standardTimes;
	}

	public void setStandardTimes(Integer standardTimes) {
		this.standardTimes = standardTimes;
	}

	public Integer getAdvancedTimes() {
		return advancedTimes;
	}

	public void setAdvancedTimes(Integer advancedTimes) {
		this.advancedTimes = advancedTimes;
	}

	public Integer getSortWay() {
		return sortWay;
	}

	public void setSortWay(Integer sortWay) {
		this.sortWay = sortWay;
	}

	public String getServicePwd() {
		return servicePwd;
	}

	public void setServicePwd(String servicePwd) {
		this.servicePwd = servicePwd;
	}

	public String getAreaJson() {
		return areaJson;
	}

	public void setAreaJson(String areaJson) {
		this.areaJson = areaJson;
	}

	public String getLatestReportTimeStr() {
		return latestReportTimeStr;
	}

	public void setLatestReportTimeStr(String latestReportTimeStr) {
		this.latestReportTimeStr = latestReportTimeStr;
	}

	public Integer getLatestJxlRepStatus() {
		return latestJxlRepStatus;
	}

	public void setLatestJxlRepStatus(Integer latestJxlRepStatus) {
		this.latestJxlRepStatus = latestJxlRepStatus;
	}

	public Integer getLatestDhbRepStatus() {
		return latestDhbRepStatus;
	}

	public void setLatestDhbRepStatus(Integer latestDhbRepStatus) {
		this.latestDhbRepStatus = latestDhbRepStatus;
	}
	
}
