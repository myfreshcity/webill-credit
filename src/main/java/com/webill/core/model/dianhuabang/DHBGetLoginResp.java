package com.webill.core.model.dianhuabang;

import com.alibaba.fastjson.JSONObject;

/** 
 * @ClassName: DHBGetLoginResp 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月30日 下午1:47:31 
 */
public class DHBGetLoginResp {
	private String sid; // 会话标识
	private Integer needPinPwd; // 是否需要输入服务密码：1 是；0 否
	private Integer needFullName; // 是否需要输入姓名：1 是；0 否
	private Integer needIdCard; //  登入页面是否需用户身份证号 1:是, 0:否
	private String smsDuration; // 短信验证码提示有效时间
	private String captchaImage; // 图形验证码 base64 码
	private String verifyNotes; // 短信提示（只针对于吉林电信并 sms_duration 不为空）
	private Integer status; // 
	private String action; // 
	
	public static DHBGetLoginResp fromJsonString(String jsonString){
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		
		DHBGetLoginResp resp = new DHBGetLoginResp();
		
		if(jsonObject.containsKey("status")) {
			resp.setStatus(jsonObject.getIntValue("status"));
		}
		if(jsonObject.containsKey("action")) {
			resp.setAction(jsonObject.getString("action"));
		}
		if(jsonObject.containsKey("data")){
			JSONObject data = jsonObject.getJSONObject("data");
			resp.setSid(data.getString("sid"));
			resp.setNeedPinPwd(data.getIntValue("need_pin_pwd"));
			resp.setNeedFullName(data.getIntValue("need_full_name"));
			resp.setNeedIdCard(data.getIntValue("need_id_card"));
			resp.setSmsDuration(data.getString("sms_duration"));
			resp.setCaptchaImage(data.getString("captcha_image"));
			resp.setVerifyNotes(data.getString("verify_notes"));
		}

		return resp;
	}
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public Integer getNeedPinPwd() {
		return needPinPwd;
	}
	public void setNeedPinPwd(Integer needPinPwd) {
		this.needPinPwd = needPinPwd;
	}
	public Integer getNeedFullName() {
		return needFullName;
	}
	public void setNeedFullName(Integer needFullName) {
		this.needFullName = needFullName;
	}
	public Integer getNeedIdCard() {
		return needIdCard;
	}
	public void setNeedIdCard(Integer needIdCard) {
		this.needIdCard = needIdCard;
	}
	public String getSmsDuration() {
		return smsDuration;
	}
	public void setSmsDuration(String smsDuration) {
		this.smsDuration = smsDuration;
	}
	public String getCaptchaImage() {
		return captchaImage;
	}
	public void setCaptchaImage(String captchaImage) {
		this.captchaImage = captchaImage;
	}
	public String getVerifyNotes() {
		return verifyNotes;
	}
	public void setVerifyNotes(String verifyNotes) {
		this.verifyNotes = verifyNotes;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
}
