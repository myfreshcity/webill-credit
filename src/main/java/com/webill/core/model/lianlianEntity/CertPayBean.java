package com.webill.core.model.lianlianEntity;

import java.io.Serializable;

/** 
* @ClassName: CertPayBean 
* @Description: 连连认证支付-提交实体类
* @author ZhangYadong
* @date 2017年11月7日 下午7:54:05 
*/
public class CertPayBean implements Serializable{
    private static final long serialVersionUID = 1L;
    // 商户提交参数
    private String            version;              // 接口版本号
    private String            oid_partner;          // 商户编号
    private String            user_id;				// 用户ID
    private String            app_request;          // 请求应用标识 1：Android 2：ios 3：WAP
    private String            sign_type;            // 签名方式
    private String            sign;                 // 签名
    private String            bg_color;             // 支付页面背景颜色
    private String 			  font_color;			// 支付页面字体颜色
    private String            busi_partner;         // 商户业务类型
    private String            no_order;             // 商户唯一订单号
    private String            dt_order;             // 商户订单时间
    private String            name_goods;           // 商品名称
    private String            info_order;           // 订单描述
    private String            money_order;          // 交易金额 单位为RMB-元
    private String            notify_url;           // 服务器异步通知地址
    private String            url_return;           // 支付结束回显url
    private String            no_agree;             // 签约协议号
    private String            valid_order;          // 订单有效时间 分钟为单位，默认为10080分钟（7天）
    private String            id_type;              // 证件类型
    private String            id_no;                // 身份证号码
    private String            acct_name;            // 姓名
    private String            shareing_data;        // 分账商户名称
    private String            risk_item; 			// 风控参数
    private String            card_no;              // 银行卡号
    
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getOid_partner() {
		return oid_partner;
	}
	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getApp_request() {
		return app_request;
	}
	public void setApp_request(String app_request) {
		this.app_request = app_request;
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
	public String getBg_color() {
		return bg_color;
	}
	public void setBg_color(String bg_color) {
		this.bg_color = bg_color;
	}
	public String getFont_color() {
		return font_color;
	}
	public void setFont_color(String font_color) {
		this.font_color = font_color;
	}
	public String getBusi_partner() {
		return busi_partner;
	}
	public void setBusi_partner(String busi_partner) {
		this.busi_partner = busi_partner;
	}
	public String getNo_order() {
		return no_order;
	}
	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}
	public String getDt_order() {
		return dt_order;
	}
	public void setDt_order(String dt_order) {
		this.dt_order = dt_order;
	}
	public String getName_goods() {
		return name_goods;
	}
	public void setName_goods(String name_goods) {
		this.name_goods = name_goods;
	}
	public String getInfo_order() {
		return info_order;
	}
	public void setInfo_order(String info_order) {
		this.info_order = info_order;
	}
	public String getMoney_order() {
		return money_order;
	}
	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getUrl_return() {
		return url_return;
	}
	public void setUrl_return(String url_return) {
		this.url_return = url_return;
	}
	public String getNo_agree() {
		return no_agree;
	}
	public void setNo_agree(String no_agree) {
		this.no_agree = no_agree;
	}
	public String getValid_order() {
		return valid_order;
	}
	public void setValid_order(String valid_order) {
		this.valid_order = valid_order;
	}
	public String getId_type() {
		return id_type;
	}
	public void setId_type(String id_type) {
		this.id_type = id_type;
	}
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	public String getAcct_name() {
		return acct_name;
	}
	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}
	public String getShareing_data() {
		return shareing_data;
	}
	public void setShareing_data(String shareing_data) {
		this.shareing_data = shareing_data;
	}
	public String getRisk_item() {
		return risk_item;
	}
	public void setRisk_item(String risk_item) {
		this.risk_item = risk_item;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
    
}
