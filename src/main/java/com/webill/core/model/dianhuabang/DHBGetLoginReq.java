package com.webill.core.model.dianhuabang;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/** 
 * @ClassName: DHBGetLoginReq 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月30日 上午11:33:00 
 */
public class DHBGetLoginReq {
	/**
	 * 用户手机号码
	 */
	private String tel;
	/**
	 * 用户唯一标识（调用方传入，可用于区分）
	 */
	private String uid;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 身份证号码
	 */
	private String userIdcard;
	/**
	 * 省份
	 */
	private String userProvince;
	/**
	 * 城市
	 */
	private String userCity;
	/**
	 * 详细地址
	 */
	private String userAddress;
	/**
	 * 紧急联系人信息
	 */
	private List<DHBUserContact> contacts = new ArrayList<DHBUserContact>();
	
	/** 
	 * 转换成电话邦获取登录方式，请求时所需json格式
	 */
	public String toJsonString(){
		JSONObject reqJson = new JSONObject();
		
		reqJson.put("tel", tel);
		if (uid != null) {
			reqJson.put("uid", uid);
		}

		// 处理user_info
		JSONObject userInfoObj = new JSONObject();
		if (userName != null) {
			userInfoObj.put("user_name", userName);
		}
		if (userIdcard != null) {
			userInfoObj.put("user_idcard", userIdcard);
		}
		if (userProvince != null) {
			userInfoObj.put("user_province", userProvince);
		}
		if (userCity != null) {
			userInfoObj.put("user_city", userCity);
		}
		if (userAddress != null) {
			userInfoObj.put("user_address", userAddress);
		}
		reqJson.put("user_info", userInfoObj);
		
		// 处理user_contact
		JSONArray userContactArr = new JSONArray();
		for(DHBUserContact contactl : contacts){
			JSONObject contactObj = new JSONObject();
			if (contactl.getContactTel() != null) {
				contactObj.put("contact_tel",contactl.getContactTel());
			}
			if (contactl.getContactName() != null) {
				contactObj.put("contact_name",contactl.getContactName());
			}
			if (contactl.getContactRelationShip() != null) {
				contactObj.put("contact_relationship",contactl.getContactRelationShip());
			}
			if (contactl.getContactPrionity() != null) {
				contactObj.put("contact_priority",contactl.getContactPrionity());
			}
			userContactArr.add(contactObj);
		}
		reqJson.put("user_contact", userContactArr);
		
		return reqJson.toString();
	}
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserIdcard() {
		return userIdcard;
	}
	public void setUserIdcard(String userIdcard) {
		this.userIdcard = userIdcard;
	}
	public String getUserProvince() {
		return userProvince;
	}
	public void setUserProvince(String userProvince) {
		this.userProvince = userProvince;
	}
	public String getUserCity() {
		return userCity;
	}
	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public List<DHBUserContact> getContacts() {
		return contacts;
	}
	public void setContacts(List<DHBUserContact> contacts) {
		this.contacts = contacts;
	}
}
