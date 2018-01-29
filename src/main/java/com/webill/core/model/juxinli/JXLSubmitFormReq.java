package com.webill.core.model.juxinli;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/** 
 * @ClassName: JuxinliSubmitFormReq 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:22:50 
 */
public class JXLSubmitFormReq {
	
	/**
	 * 对应提交申请表单接口上送字段中的selected_website字段，默认赎值为空数组，在转json时，如果为null，需要转为[]
	 * 选择的数据源
	 */
	private List<Website> selectedWebsite = new ArrayList<Website>();
	
	/**
	 * 联系人列表，对应contacts
	 */
	private List<JXLContact> contacts = new ArrayList<JXLContact>();
	
	/**
	 * 对应skip_mobile字段，标识是否跳过移动运营商的采集，（ true 跳过，false 不跳过）
	 * 如果选择跳过的话，selected_website 字段就不能为空。
	 */
	private boolean skipMobile = false;
	
	/**
	 * 对应basic_info.name，客户名称
	 * 必填项
	 */
	private String name = null;
	
	/**
	 * 对应basic_info.id_card_num，身份证号码，如有字母x，使用大写形式
	 * 必填项
	 */
	private String idCardNum = null;
	
	/**
	 * 对应basic_info.cell_phone_num，手机号码
	 * 必填项
	 */
	private String mobileNo = null;
	
	/**
	 * 对应basic_info.home_addr，家庭地址
	 */
	private String homeAddress = null;
	
	/**
	 * 对应basic_info.work_addr，工作地址
	 */
	private String workAddress = null;
	
	/**
	 * 对应basic_info.home_tel，家庭电话
	 */
	private String homeTel = null;
	
	/**
	 * 对应basic_info.work_tel，工作电话
	 */
	private String workTel = null;
	
	/**
	 * 对应basic_info.cell_phone_num2，备用电话
	 */
	private String mobileNo2 = null;
	
	/**
	 * 如果需要推送报告时推送机构账号系统中的ID需要设置此字段
	 */
	private String uid = null;
	
	private String applySn = null;
	
	/**
	 * 数据源网站信息类
	 */
	private class Website{
		/**
		 * 网站名称，对应selected_website.website
		 */
		private String websiteName = null;
		/**
		 * 网站分类，对应selected_website.category
		 */
		private String category = null;
	}
	
	/**
	 * 添加联系人
	 */
	public void addContact(String contactTel,String contactName,String contactType){
		JXLContact contact = new JXLContact();
		contact.setContactTel(contactTel);
		contact.setContactName(contactName);
		contact.setContactType(contactType);
		contacts.add(contact);
	}
	
	/**
	 * 转换联系人类型
	 */
	public void convertContact(Map<String,String> code){
		for(JXLContact Contact : contacts){
			if(code.get(Contact.getContactType()) != null){
				Contact.setContactType (code.get(Contact.getContactType())); 
			}
		}
	}
	
	/** 
	 * 添加数据源网站信息
	 */
	public void addWebsite(String websiteName,String category){
		Website website = new Website();
		website.websiteName = websiteName;
		website.category = category;
		selectedWebsite.add(website);
	}

	/** 
	 * 转换成聚信立请求所需json格式
	 */
	public String toJsonString(){
		JSONObject json = new JSONObject();
		
		//处理selected_website
		JSONArray selectedWebsiteArray = new JSONArray();
		for(Website website : selectedWebsite){
			JSONObject websiteJson = new JSONObject();
			websiteJson.put("website", website.websiteName);
			websiteJson.put("category", website.category);
			selectedWebsiteArray.add(websiteJson);
		}
		json.put("selected_website", selectedWebsiteArray);
		
		//处理skipMobile
		json.put("skip_mobile", skipMobile);
		
		//处理basic_info
		JSONObject basicInfo = new JSONObject();
		basicInfo.put("name", name);
		basicInfo.put("id_card_num", idCardNum);
		basicInfo.put("cell_phone_num", mobileNo);
		if(homeAddress != null){
			basicInfo.put("home_addr", homeAddress);
		}
		if(workTel != null){
			basicInfo.put("work_tel", workTel);
		}
		if(workAddress != null){
			basicInfo.put("work_addr", workAddress);
		}
		if(homeTel != null){
			basicInfo.put("home_tel", homeTel);
		}
		if(mobileNo2 != null){
			basicInfo.put("cell_phone_num2", mobileNo2);
		}
		json.put("basic_info", basicInfo);
		
		//处理contacts
		JSONArray contactsArray = new JSONArray();
		for(JXLContact contactl : contacts){
			JSONObject contactJson = new JSONObject();
			contactJson.put("contact_tel",contactl.getContactTel());
			contactJson.put("contact_name",contactl.getContactName());
			contactJson.put("contact_type",contactl.getContactType());
			contactsArray.add(contactJson);
		}
		json.put("contacts", contactsArray);
		
		//处理uid
		if(uid != null){
			json.put("uid", uid);
		}
		return json.toString();
	}
	
	public boolean isSkipMobile() {
		return skipMobile;
	}

	public void setSkipMobile(boolean skipMobile) {
		this.skipMobile = skipMobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCardNum() {
		return idCardNum;
	}

	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getHomeTel() {
		return homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public String getWorkTel() {
		return workTel;
	}

	public void setWorkTel(String workTel) {
		this.workTel = workTel;
	}

	public String getMobileNo2() {
		return mobileNo2;
	}

	public void setMobileNo2(String mobileNo2) {
		this.mobileNo2 = mobileNo2;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getApplySn() {
		return applySn;
	}

	public void setApplySn(String applySn) {
		this.applySn = applySn;
	}

	public List<Website> getSelectedWebsite() {
		return selectedWebsite;
	}

	public void setSelectedWebsite(List<Website> selectedWebsite) {
		this.selectedWebsite = selectedWebsite;
	}

	public List<JXLContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<JXLContact> contacts) {
		this.contacts = contacts;
	}
	
}
