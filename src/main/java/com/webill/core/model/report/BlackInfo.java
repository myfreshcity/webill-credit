package com.webill.core.model.report;
/** 
 * @ClassName: Black_Info 
 * @Description: 用户黑名单信息（聚信立高级版）
 * @author ZhangYadong
 * @date 2018年1月25日 上午10:40:33 
 */
public class BlackInfo {
	/**
	 * 聚信立-对应user_info_check.check_black_info.phone_gray_score，用户号码联系黑中介分数
	 */
	private  Integer  phone_gray_score;
	/**
	 * 聚信立-对应user_info_check.check_black_info.contacts_class1_blacklist_cnt，直接联系人中黑名单人数
	 */
	private  Integer  contacts_class1_blacklist_cnt;
	/**
	 * 聚信立-对应user_info_check.check_black_info.contacts_class2_blacklist_cnt，间接联系人中黑名单人数
	 */
	private  Integer  contacts_class2_blacklist_cnt;
	/**
	 * 聚信立-对应user_info_check.check_black_info.contacts_class1_cnt，直接联系人人数
	 */
	private  Integer  contacts_class1_cnt;
	/**
	 * 聚信立-对应user_info_check.check_black_info.contacts_router_cnt，引起间接黑名单人数
	 */
	private  Integer  contacts_router_cnt;
	/**
	 * 聚信立-对应user_info_check.check_black_info.contacts_router_ratio，直接联系人中引起间接黑名单占比
	 */
	private  Integer  contacts_router_ratio; 
	
	public Integer getPhone_gray_score() {
		return phone_gray_score;
	}
	public void setPhone_gray_score(Integer phone_gray_score) {
		this.phone_gray_score = phone_gray_score;
	}
	public Integer getContacts_class1_blacklist_cnt() {
		return contacts_class1_blacklist_cnt;
	}
	public void setContacts_class1_blacklist_cnt(Integer contacts_class1_blacklist_cnt) {
		this.contacts_class1_blacklist_cnt = contacts_class1_blacklist_cnt;
	}
	public Integer getContacts_class2_blacklist_cnt() {
		return contacts_class2_blacklist_cnt;
	}
	public void setContacts_class2_blacklist_cnt(Integer contacts_class2_blacklist_cnt) {
		this.contacts_class2_blacklist_cnt = contacts_class2_blacklist_cnt;
	}
	public Integer getContacts_class1_cnt() {
		return contacts_class1_cnt;
	}
	public void setContacts_class1_cnt(Integer contacts_class1_cnt) {
		this.contacts_class1_cnt = contacts_class1_cnt;
	}
	public Integer getContacts_router_cnt() {
		return contacts_router_cnt;
	}
	public void setContacts_router_cnt(Integer contacts_router_cnt) {
		this.contacts_router_cnt = contacts_router_cnt;
	}
	public Integer getContacts_router_ratio() {
		return contacts_router_ratio;
	}
	public void setContacts_router_ratio(Integer contacts_router_ratio) {
		this.contacts_router_ratio = contacts_router_ratio;
	}
}
