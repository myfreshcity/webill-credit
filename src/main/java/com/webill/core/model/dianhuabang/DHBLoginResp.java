package com.webill.core.model.dianhuabang;

import com.alibaba.fastjson.JSONObject;

/** 
 * @ClassName: DHBLoginResp 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月31日 下午7:50:20 
 */
public class DHBLoginResp {
	private String captchaImage; //base64 图片文件,如果需要继续验证，有值，否则为空值
	private String smsDuration; //短信验证码有效时长，如果需要继续验证，有值，否则为空值
	private String verifyNotes; //短信提示（只针对于吉林电信 sms_duration 不为空）
	private Integer status; // 请求状态码 0 为正常 非 0 为错误
	private String action; // 爬虫状态 processing 进行中 done 完成 reset 错误
	private String msg; // 状态描述
	
	public static DHBLoginResp fromJsonString(String jsonString){
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		
		DHBLoginResp resp = new DHBLoginResp();
		
		if(jsonObject.containsKey("status")) {
			resp.setStatus(jsonObject.getIntValue("status"));
		}
		if(jsonObject.containsKey("action")) {
			resp.setAction(jsonObject.getString("action"));
		}
		if(jsonObject.containsKey("msg")){
			resp.setMsg(jsonObject.getString("msg"));
		}
		
		if(jsonObject.containsKey("data")){
			JSONObject data = jsonObject.getJSONObject("data");
			resp.setCaptchaImage(data.getString("captcha_image"));
			resp.setSmsDuration(data.getString("sms_duration"));
			resp.setVerifyNotes(data.getString("verify_notes"));
		}
		
		return resp;
	}

	public String getCaptchaImage() {
		return captchaImage;
	}

	public void setCaptchaImage(String captchaImage) {
		this.captchaImage = captchaImage;
	}

	public String getSmsDuration() {
		return smsDuration;
	}

	public void setSmsDuration(String smsDuration) {
		this.smsDuration = smsDuration;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
