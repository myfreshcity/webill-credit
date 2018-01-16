package com.webill.core.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 车辆基础信息表
 *
 */
@TableName("car_info")
public class CarInfo implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 车牌号 */
	@ApiModelProperty(value = "车牌号", required = true)
	private String licenseNo;

	/** 发动机号 */
	private String engineNo;

	/** 车架号 */
	private String frameNo;

	/** 车主姓名 */
	private String carOwner;
	
	/** 车主身份证号 */
	private String ownerNo;

	/** 车主联系电话 */
	private String ownerPhone;

	/** 查询码 */
	@ApiModelProperty(hidden = true)
	private String searchSequenceNo;

	/** 车辆管理人ID */
	private Integer userId;

	/** 厂牌型号 */
	private String brandName;

	/** 车型代码 */
	private String modelCode;

	/** 核定载客 */
	private Integer seatCount;
	
	/** 状态 */
	@ApiModelProperty(hidden = true)
	private Integer status;

	/** 排量／功率 */
	@ApiModelProperty(hidden = true)
	private String exhaustScale;

	/** 新车购置价 */
	@ApiModelProperty(hidden = true)
	private Integer purchasePrice;

	/** 车型类比价 */
	@ApiModelProperty(hidden = true)
	private Integer purchasePriceLb;

	/** 初次登记日期 */
	@ApiModelProperty(hidden = true)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date enrollDate;

	/** 整备质量 */
	@ApiModelProperty(hidden = true)
	private String completeKerbMass;

	/** 上一次违章查询时间 */
	@ApiModelProperty(hidden = true)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date lastIllegeQtime;

	/** 保险到期时间 */
	@ApiModelProperty(hidden = true)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date prmEndTime;

	/** 投保公司 */
	private String insurerCom;
	
	/** OCR图片路径 */
	private String imgUrl;

	/**  */
	@ApiModelProperty(hidden = true)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdTime;
	
	@TableField(exist = false)
	private String penalizeIds;
	
	@TableField(exist = false)
	private String orderId;
	
	@TableField(exist = false)
	private String curId;
	
	@TableField(exist = false)
	private String curUserId;
	
	@TableField(exist = false)
	private String curMobileNo;
	
	@TableField(exist = false)
	private int illCount;
	
	@TableField(exist = false)
	private MultipartFile idCardP;
	
	@TableField(exist = false)
	private MultipartFile idCardN;
	
	@TableField(exist = false)
	private MultipartFile vehicleLicenseP;
	
	@TableField(exist = false)
	private MultipartFile vehicleLicenseN;
	
	@TableField(exist = false)
	private MultipartFile driveLicenseP;
	
	@TableField(exist = false)
	private MultipartFile driveLicenseN;
	
	/** 身份证正面路径 */
	@TableField(exist = false)
	private String idCardPPath;
	
	/** 身份证反面路径*/
	@TableField(exist = false)
	private String idCardNPath;
	
	/** 行驶证正面路径 */
	@TableField(exist = false)
	private String vehicleLicensePPath;
	
	/** 行驶证反面路径 */
	@TableField(exist = false)
	private String vehicleLicenseNPath ;
	
	/** 驾驶证正面路径 */
	@TableField(exist = false)
	private String driveLicensePPath;
	
	/** 驾驶证反面路径 */
	@TableField(exist = false)
	private String driveLicenseNPath;
	
	
	
	public String getCurMobileNo() {
		return curMobileNo;
	}

	public void setCurMobileNo(String curMobileNo) {
		this.curMobileNo = curMobileNo;
	}

	public String getCurUserId() {
		return curUserId;
	}

	public void setCurUserId(String curUserId) {
		this.curUserId = curUserId;
	}

	public String getCurId() {
		return curId;
	}

	public void setCurId(String curId) {
		this.curId = curId;
	}

	public MultipartFile getIdCardP() {
		return idCardP;
	}

	public void setIdCardP(MultipartFile idCardP) {
		this.idCardP = idCardP;
	}

	public MultipartFile getIdCardN() {
		return idCardN;
	}

	public void setIdCardN(MultipartFile idCardN) {
		this.idCardN = idCardN;
	}

	public MultipartFile getVehicleLicenseP() {
		return vehicleLicenseP;
	}

	public void setVehicleLicenseP(MultipartFile vehicleLicenseP) {
		this.vehicleLicenseP = vehicleLicenseP;
	}

	public MultipartFile getVehicleLicenseN() {
		return vehicleLicenseN;
	}

	public void setVehicleLicenseN(MultipartFile vehicleLicenseN) {
		this.vehicleLicenseN = vehicleLicenseN;
	}

	public MultipartFile getDriveLicenseP() {
		return driveLicenseP;
	}

	public void setDriveLicenseP(MultipartFile driveLicenseP) {
		this.driveLicenseP = driveLicenseP;
	}

	public MultipartFile getDriveLicenseN() {
		return driveLicenseN;
	}

	public void setDriveLicenseN(MultipartFile driveLicenseN) {
		this.driveLicenseN = driveLicenseN;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getIllCount() {
		return illCount;
	}

	public void setIllCount(int illCount) {
		this.illCount = illCount;
	}

	public String getPenalizeIds() {
		return penalizeIds;
	}

	public void setPenalizeIds(String penalizeIds) {
		this.penalizeIds = penalizeIds;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOwnerNo() {
		return ownerNo;
	}

	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLicenseNo() {
		return this.licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getEngineNo() {
		return this.engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getFrameNo() {
		return this.frameNo;
	}

	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	public String getCarOwner() {
		return this.carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	public String getOwnerPhone() {
		return this.ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	public String getSearchSequenceNo() {
		return this.searchSequenceNo;
	}

	public void setSearchSequenceNo(String searchSequenceNo) {
		this.searchSequenceNo = searchSequenceNo;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelCode() {
		return this.modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public Integer getSeatCount() {
		return this.seatCount;
	}

	public void setSeatCount(Integer seatCount) {
		this.seatCount = seatCount;
	}

	public String getExhaustScale() {
		return this.exhaustScale;
	}

	public void setExhaustScale(String exhaustScale) {
		this.exhaustScale = exhaustScale;
	}

	public Integer getPurchasePrice() {
		return this.purchasePrice;
	}

	public void setPurchasePrice(Integer purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Integer getPurchasePriceLb() {
		return this.purchasePriceLb;
	}

	public void setPurchasePriceLb(Integer purchasePriceLb) {
		this.purchasePriceLb = purchasePriceLb;
	}

	public Date getEnrollDate() {
		return this.enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	public String getCompleteKerbMass() {
		return this.completeKerbMass;
	}

	public void setCompleteKerbMass(String completeKerbMass) {
		this.completeKerbMass = completeKerbMass;
	}

	public Date getLastIllegeQtime() {
		return this.lastIllegeQtime;
	}

	public void setLastIllegeQtime(Date lastIllegeQtime) {
		this.lastIllegeQtime = lastIllegeQtime;
	}

	public Date getPrmEndTime() {
		return this.prmEndTime;
	}

	public void setPrmEndTime(Date prmEndTime) {
		this.prmEndTime = prmEndTime;
	}

	public String getInsurerCom() {
		return this.insurerCom;
	}

	public void setInsurerCom(String insurerCom) {
		this.insurerCom = insurerCom;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
