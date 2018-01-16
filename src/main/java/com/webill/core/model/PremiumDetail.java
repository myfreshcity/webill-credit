package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

import java.math.BigDecimal;

/**
 *
 * 保险订单商业险子表
 *
 */
@TableName("premium_detail")
public class PremiumDetail implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 保险订单编号，关联premium_order的ID */
	private Integer prmOrderId;
	
	private Integer prmId;

	/** 保费名称 */
	private String prmName;

	/** 保额 */
	private String amount;

	/** 保费 */
	private BigDecimal prmValue;

	/** 保费代码 */
	private String prmCode;

	/**  */
	private Date createdTime;
	
	
	@TableField(exist = false)
	private String content;

	@TableField(exist = false)
	private boolean selected;
	
	@TableField(exist = false)
	private String premiumType;
	
	@TableField(exist = false)
	private Integer detailType;
	
	
	public Integer getDetailType() {
		return detailType;
	}

	public void setDetailType(Integer detailType) {
		this.detailType = detailType;
	}

	public String getPremiumType() {
		return premiumType;
	}

	public void setPremiumType(String premiumType) {
		this.premiumType = premiumType;
	}

	public Integer getPrmId() {
		return prmId;
	}

	public void setPrmId(Integer prmId) {
		this.prmId = prmId;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrmOrderId() {
		return this.prmOrderId;
	}

	public void setPrmOrderId(Integer prmOrderId) {
		this.prmOrderId = prmOrderId;
	}

	public String getPrmName() {
		return this.prmName;
	}

	public void setPrmName(String prmName) {
		this.prmName = prmName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public BigDecimal getPrmValue() {
		return this.prmValue;
	}

	public void setPrmValue(BigDecimal prmValue) {
		this.prmValue = prmValue;
	}

	public String getPrmCode() {
		return this.prmCode;
	}

	public void setPrmCode(String prmCode) {
		this.prmCode = prmCode;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
