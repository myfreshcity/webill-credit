package com.webill.core.model.lianlianEntity;

import java.io.Serializable;

/** 
* @ClassName: WeChatPayBean 
* @Description: 连连微信支付-提交实体类
* @author ZhangYadong
* @date 2017年11月6日 下午4:27:27 
*/
public class WeChatPayBean implements Serializable{
	private static final long serialVersionUID  = 1L;
	
	private  String  no_order; 			// 商家订单号
	private  String  oid_partner;		// 商户号
	private  String  oid_biz; 			// 业务标识码
	private  String  biz_partner; 		// 业务类型
	private  String  sharing_data; 		// 分账信息
	private  String  money_order; 		// 订单金额
	private  String  user_id; 			// 用户ID
	private  String  wallet_user_id; 	// 钱包用户ID
	private  String  user_login; 		
	private  String  name_goods; 		
	private  String  pay_type; 			
	private  String  dt_order; 			
	private  String  notify_url; 		
	private  String  info_order; 		
	private  String  risk_item; 		
	private  String  openid; 			
	private  String  appid; 			
	private  String  day_billvalid;		
	private  String  sign_type;			
	private  String  sign; 				
	
	public String getNo_order() {
		return no_order;
	}
	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}
	public String getOid_partner() {
		return oid_partner;
	}
	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}
	public String getOid_biz() {
		return oid_biz;
	}
	public void setOid_biz(String oid_biz) {
		this.oid_biz = oid_biz;
	}
	public String getBiz_partner() {
		return biz_partner;
	}
	public void setBiz_partner(String biz_partner) {
		this.biz_partner = biz_partner;
	}
	public String getSharing_data() {
		return sharing_data;
	}
	public void setSharing_data(String sharing_data) {
		this.sharing_data = sharing_data;
	}
	public String getMoney_order() {
		return money_order;
	}
	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getWallet_user_id() {
		return wallet_user_id;
	}
	public void setWallet_user_id(String wallet_user_id) {
		this.wallet_user_id = wallet_user_id;
	}
	public String getUser_login() {
		return user_login;
	}
	public void setUser_login(String user_login) {
		this.user_login = user_login;
	}
	public String getName_goods() {
		return name_goods;
	}
	public void setName_goods(String name_goods) {
		this.name_goods = name_goods;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getDt_order() {
		return dt_order;
	}
	public void setDt_order(String dt_order) {
		this.dt_order = dt_order;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getInfo_order() {
		return info_order;
	}
	public void setInfo_order(String info_order) {
		this.info_order = info_order;
	}
	public String getRisk_item() {
		return risk_item;
	}
	public void setRisk_item(String risk_item) {
		this.risk_item = risk_item;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getDay_billvalid() {
		return day_billvalid;
	}
	public void setDay_billvalid(String day_billvalid) {
		this.day_billvalid = day_billvalid;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
