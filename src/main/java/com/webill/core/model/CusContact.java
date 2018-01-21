package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 联系人表
 *
 */
@TableName("t_cus_contact")
public class CusContact implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 联系人姓名 */
	private String name;

	/** 手机号 */
	private String mobileNo;

	/** 联系人类型（"0":配偶，"1":父母，"2":兄弟姐妹,"3":子女,"4":同事,"5": 同学,"6": 朋友）字符串类型非int */
	private Integer contactType;

	/** 客户的身份证号码，冗余设计，方便查询 */
	private String cusIdNo;

	/** 客户表对应的主键ID */
	private Integer cusId;

	/** 状态： -1-逻辑删除 0-正常数据 */
	private Integer tStatus;

	/** 更新时间 */
	private Date updatedTime;

	/** 创建时间 */
	private Date createdTime;


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Integer getContactType() {
		return this.contactType;
	}

	public void setContactType(Integer contactType) {
		this.contactType = contactType;
	}

	public String getCusIdNo() {
		return this.cusIdNo;
	}

	public void setCusIdNo(String cusIdNo) {
		this.cusIdNo = cusIdNo;
	}

	public Integer getCusId() {
		return this.cusId;
	}

	public void setCusId(Integer cusId) {
		this.cusId = cusId;
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
