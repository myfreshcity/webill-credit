package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 违章代缴订单表
 *
 */
@TableName("illegal_order")
public class IllegalOrder implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 用户编号，关联user表ID */
	private Integer userId;
	
	/** 车辆编号，关联car_info表ID */
	private Integer carId;
	
	/** 联系人id，关联user_contact表ID */
	private Integer contactId;
	
	/** 违章详情id，关联illegal_detail表ID */
	private String detailIds;

	/** 违章数 */
	private Integer count;
	
	/** 车牌号 */
	private String licenseNo;

	/** 罚款总额 */
	private Integer totalMoney;
	
	/** 服务费减免额 */
	private Integer discountMoney;
	
	/** 实付金额 */
	private Integer totalCount;

	/** 手续费总额 */
	private Integer totalFee;

	/** 状态，-1:废弃，0:待缴费，1:缴费中，2:已缴费，3:缴费失败 */
	private Integer status;

	/**  */
	private Date updateTime;
	
	/**  */
	@DateTimeFormat(pattern = "yyyy-MM-dd 24HH:mm:ss") 
	private Date createdTime;
	
	/** 订单编号 */
	private String tradeNo;
	
	@TableField(exist = false)
	private String carOwner;
	
	@TableField(exist = false)
	private String frameNo;
	
	@TableField(exist = false)
	private String engineNo;
	
	@TableField(exist = false)
	private String orderStatus;
	
	/**  */
	@TableField(exist = false)
	private String starTime;
	
	@TableField(exist = false)
	private String ownerPhone;
	
	@TableField(exist = false)
	private String endTime;
	
	@TableField(exist = false)
	private String openId;
	
	@TableField(exist = false)
	private List<IllegalDetail> detailList;
	
	
	public String getDetailIds() {
		return detailIds;
	}

	public void setDetailIds(String detailIds) {
		this.detailIds = detailIds;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public List<IllegalDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<IllegalDetail> detailList) {
		this.detailList = detailList;
	}

	public Integer getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(Integer discountMoney) {
		this.discountMoney = discountMoney;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	public String getLicenseNo() {
		return licenseNo;
	}
	
	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public String getStarTime() {
		return starTime;
	}

	public void setStarTime(String starTime) {
		this.starTime = starTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getCarOwner() {
		return carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	public String getFrameNo() {
		return frameNo;
	}

	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getTotalMoney() {
		return this.totalMoney;
	}

	public void setTotalMoney(Integer totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getTotalFee() {
		return this.totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
