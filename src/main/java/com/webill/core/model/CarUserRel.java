package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * 车辆用户关联表
 *
 */
@TableName("car_user_rel")
public class CarUserRel implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	@TableField(exist = false)
	private CarInfo car;

	@TableField(exist = false)
	private User user; 

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 车辆ID,关联car_info的编号 */
	private Integer carId;

	/** 用户ID,关联user的编号 */
	private Integer userId;

	/** 车牌号 */
	private String licenseNo;

	/** 车主联系电话 */
	private String mobileNo;
	
	/** 身份证正面路径 */
	private String idCardPPath;
	
	/** 身份证反面路径*/
	private String idCardNPath;
	
	/** 行驶证正面路径 */
	private String vehicleLicensePPath;
	
	/** 行驶证反面路径 */
	private String vehicleLicenseNPath ;
	
	/** 驾驶证正面路径 */
	private String driveLicensePPath;
	
	/** 驾驶证反面路径 */
	private String driveLicenseNPath;

	/** 更新时间 */
	private Date updatedTime;

	/**  */
	private Date createdTime;
	
	/** 车主姓名 */
	@ApiModelProperty(value = "车主姓名", required = false)
	@TableField(exist = false)
	private String carOwner;
	
	/** 厂牌型号 */
	@ApiModelProperty(value = "厂牌型号", required = false)
	@TableField(exist = false)
	private String brandName;
	
	/** 保险到期日期 */
	@ApiModelProperty(value = "保险到期日期", required = false)
	@TableField(exist = false)
	private String prmEndTime;
	
	/** 参考价格 */
	@ApiModelProperty(value = "参考价格", required = false)
	@TableField(exist = false)
	private String purchasePrice;
	
	/** 初登日期 */
	@ApiModelProperty(value = "初登日期", required = false)
	@TableField(exist = false)
	private String enrollDate;
	
	/** 投保公司 */
	@ApiModelProperty(value = "投保公司", required = false)
	@TableField(exist = false)
	private String insurerCom;

	
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getPrmEndTime() {
		return prmEndTime;
	}

	public void setPrmEndTime(String prmEndTime) {
		this.prmEndTime = prmEndTime;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}

	public String getInsurerCom() {
		return insurerCom;
	}

	public void setInsurerCom(String insurerCom) {
		this.insurerCom = insurerCom;
	}

	public String getIdCardPPath() {
		return idCardPPath;
	}

	public void setIdCardPPath(String idCardPPath) {
		this.idCardPPath = idCardPPath;
	}

	public String getIdCardNPath() {
		return idCardNPath;
	}

	public void setIdCardNPath(String idCardNPath) {
		this.idCardNPath = idCardNPath;
	}

	public String getVehicleLicensePPath() {
		return vehicleLicensePPath;
	}

	public void setVehicleLicensePPath(String vehicleLicensePPath) {
		this.vehicleLicensePPath = vehicleLicensePPath;
	}

	public String getVehicleLicenseNPath() {
		return vehicleLicenseNPath;
	}

	public void setVehicleLicenseNPath(String vehicleLicenseNPath) {
		this.vehicleLicenseNPath = vehicleLicenseNPath;
	}

	public String getDriveLicensePPath() {
		return driveLicensePPath;
	}

	public void setDriveLicensePPath(String driveLicensePPath) {
		this.driveLicensePPath = driveLicensePPath;
	}

	public String getDriveLicenseNPath() {
		return driveLicenseNPath;
	}

	public void setDriveLicenseNPath(String driveLicenseNPath) {
		this.driveLicenseNPath = driveLicenseNPath;
	}

	public String getCarOwner() {
		return carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCarId() {
		return this.carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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

	public CarInfo getCar() {
		return car;
	}

	public void setCar(CarInfo car) {
		this.car = car;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
}
