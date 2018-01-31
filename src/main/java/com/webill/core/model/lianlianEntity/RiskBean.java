package com.webill.core.model.lianlianEntity;
/** 
* @ClassName: RiskBean 
* @Description: 风控参数实体类
* @author ZhangYadong
* @date 2017年10月31日 上午10:50:11 
*/
public class RiskBean {
	private String frms_ware_category; //商品类目
	private String user_info_mercht_userno; //用户在商户中的标识
	private String user_info_dt_register; //用户注册时间
	private String user_info_bind_phone; //用户手机号
	
	public String getFrms_ware_category() {
		return frms_ware_category;
	}
	public void setFrms_ware_category(String frms_ware_category) {
		this.frms_ware_category = frms_ware_category;
	}
	public String getUser_info_mercht_userno() {
		return user_info_mercht_userno;
	}
	public void setUser_info_mercht_userno(String user_info_mercht_userno) {
		this.user_info_mercht_userno = user_info_mercht_userno;
	}
	public String getUser_info_dt_register() {
		return user_info_dt_register;
	}
	public void setUser_info_dt_register(String user_info_dt_register) {
		this.user_info_dt_register = user_info_dt_register;
	}
	public String getUser_info_bind_phone() {
		return user_info_bind_phone;
	}
	public void setUser_info_bind_phone(String user_info_bind_phone) {
		this.user_info_bind_phone = user_info_bind_phone;
	}
}
