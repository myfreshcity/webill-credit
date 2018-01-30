package com.webill.core.model.dianhuabang;

public class DHBUserContact {
	/**
	 * 紧急联系电话
	 */
	private String contactTel; 
	/**
	 * 紧急联系人姓名
	 */
	private String contactName;
	/**
	 * 客户与紧急联系人的关系
	 */
	private String contactRelationShip;
	/**
	 * 紧急联系人优先级
	 */
	private Integer contactPrionity;
	
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactRelationShip() {
		return contactRelationShip;
	}
	public void setContactRelationShip(String contactRelationShip) {
		this.contactRelationShip = contactRelationShip;
	}
	public Integer getContactPrionity() {
		return contactPrionity;
	}
	public void setContactPrionity(Integer contactPrionity) {
		this.contactPrionity = contactPrionity;
	}
}
