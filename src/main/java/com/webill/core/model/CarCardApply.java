package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 建行汽车卡申请记录表
 *
 */
@TableName("car_card_apply")
public class CarCardApply implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	
	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	
	/** 用户id */
	private Integer userId;
	
	/** 联系人id即user_contact表id */
	private Integer ucId;
	
	/** 车辆id对应car_info表id */
	private Integer carId;

	/** 申请人名字 */
	private String applicantName;
	
	/** 申请人手机号 */
	private String applicantMobile;
	
	/** 申请人地址 */
	private String applicantAddress;
	
	/** 车牌号 */
	private String licenseNo;

	/**  */
	private Date createdTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUcId() {
		return ucId;
	}

	public void setUcId(Integer ucId) {
		this.ucId = ucId;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicantMobile() {
		return applicantMobile;
	}

	public void setApplicantMobile(String applicantMobile) {
		this.applicantMobile = applicantMobile;
	}

	public String getApplicantAddress() {
		return applicantAddress;
	}

	public void setApplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}


}
