package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 受益人信息表 
 *
 */
@TableName("order_beneficiary")
public class OrderBeneficiary implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** id */
	@TableId(type = IdType.AUTO)
	@JSONField(serialize = false)
	private Integer id;

	/** id对应order_insurant表的主键id */
	private String insurantId;

	/** 所属被保险人姓名  */
	private String insurantCname;

	/** 中文名 */
	private String cName;

	/** 拼音/英文名 */
	private String eName;

	/** 证件名称 */
	private String cardType;
	
	/** 证件号码 */
	@JSONField(serialize=false) 
	private String cardNumber;

	/** 性别 0：女 1：男 */
	private String sex;
	
	/** 出生日期 格式：yyyy-MM-dd */
	@JSONField (format="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date birthday;

	/** 受益序列  */
	private Integer serial;

	/** 受益比例  */
	private Integer proportion;

	/** 受益人与被保险人关系说明  */
	private String relation;

	/** 更新时间  */
	private Date updatedTime;

	/**  */
	private Date createdTime;

	/** 证件名称 */
	@TableField(exist = false)
	private String cardName;
	
	@TableField(exist = false)
	private String cardCode;
	
	@TableField(exist = false)
	private String relationId;
	
	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInsurantId() {
		return this.insurantId;
	}

	public void setInsurantId(String insurantId) {
		this.insurantId = insurantId;
	}

	public String getInsurantCname() {
		return this.insurantCname;
	}

	public void setInsurantCname(String insurantCname) {
		this.insurantCname = insurantCname;
	}

	public String getCName() {
		return this.cName;
	}

	public void setCName(String cName) {
		this.cName = cName;
	}

	public String getEName() {
		return this.eName;
	}

	public void setEName(String eName) {
		this.eName = eName;
	}

	public String getCardName() {
		return this.cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSerial() {
		return this.serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public Integer getProportion() {
		return this.proportion;
	}

	public void setProportion(Integer proportion) {
		this.proportion = proportion;
	}

	public String getRelation() {
		return this.relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
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
