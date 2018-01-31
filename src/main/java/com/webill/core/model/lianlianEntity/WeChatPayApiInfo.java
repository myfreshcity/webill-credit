package com.webill.core.model.lianlianEntity;

import java.io.Serializable;

/** 
* @ClassName: WeChatPayApiInfo 
* @Description: 
* @author ZhangYadong
* @date 2017年11月7日 下午8:18:07 
*/
public class WeChatPayApiInfo implements Serializable{
	private static final long serialVersionUID  = 1L;
	
	private  String  oid_order;
	private  String  no_order;
	private  String  user_id;
	private  String  money_order;
	private  String  oid_partner;
	private  String  dimension_url;
	private  String  pay_status;
	private  String  pay_type;
	private  String  sign_type;
	private  String  sign;
	private  String  ret_code;
	private  String  ret_msg;
	private  String  name_goods;
	
	public String getOid_order() {
		return oid_order;
	}
	public void setOid_order(String oid_order) {
		this.oid_order = oid_order;
	}
	public String getNo_order() {
		return no_order;
	}
	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getMoney_order() {
		return money_order;
	}
	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}
	public String getOid_partner() {
		return oid_partner;
	}
	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}
	public String getDimension_url() {
		return dimension_url;
	}
	public void setDimension_url(String dimension_url) {
		this.dimension_url = dimension_url;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
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
	public String getRet_code() {
		return ret_code;
	}
	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}
	public String getRet_msg() {
		return ret_msg;
	}
	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}
	public String getName_goods() {
		return name_goods;
	}
	public void setName_goods(String name_goods) {
		this.name_goods = name_goods;
	}
}
