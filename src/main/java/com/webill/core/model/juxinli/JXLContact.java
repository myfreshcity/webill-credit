package com.webill.core.model.juxinli;
/** 
 * @ClassName: ContactJxl 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:36:16 
 */
public class JXLContact {
	
	/**
	 * 联系人电话，对应contacts.contact_tel
	 */
	private String contactTel = null;
	
	/**
	 * 联系人电话，对应contacts.contact_tel
	 */
	private String contactName = null;
	
	/**
	 * 联系人电话，对应contacts.contact_tel
	 */
	private String contactType = null;
	
	private String typeText = null;

	
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

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getTypeText() {
		return typeText;
	}

	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}


}
