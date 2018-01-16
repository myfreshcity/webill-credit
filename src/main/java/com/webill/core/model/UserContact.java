package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 用户联络方式表
 *
 */
@TableName("user_contact")
public class UserContact implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 用户id */
	private Integer userId;

	/** 联系人手机号 */
	private String mobile;

	private String contactName;
	/** 邮箱 */
	private String email;

	/** 是否默认地址：0、否；1、是； */
	private Integer isDefault;

	/** 省份 */
	private String province;

	/** 城市 */
	private String city;
	
	/** 区域 */
	private String area;
	
	/** 状态 */
	private Integer tStatus;
	

	/** 地址 */
	private String address;

	/**  */
	private Date createdTime;

	@TableField(exist = false)
	private String inCode;
	
	@TableField(exist = false)
	private int isSuccess;
	
	@TableField(exist = false)
	private String msg;
	
	@TableField(exist = false)
	private String weixinNick;
	
	@TableField(exist = false)
	private String carId;
	
	@TableField(exist = false)
	private String licenseNo;
	
	@TableField(exist = false)
	private String[] cityArray;
	
	
	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getWeixinNick() {
		return weixinNick;
	}

	public void setWeixinNick(String weixinNick) {
		this.weixinNick = weixinNick;
	}

	public String[] getCityArray() {
		return cityArray;
	}
	
	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public void setCityArray(String[] cityArray) {
		this.cityArray = cityArray;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public int getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Integer gettStatus() {
		return tStatus;
	}

	public void settStatus(Integer tStatus) {
		this.tStatus = tStatus;
	}

	public String getInCode() {
		return inCode;
	}

	public void setInCode(String inCode) {
		this.inCode = inCode;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
