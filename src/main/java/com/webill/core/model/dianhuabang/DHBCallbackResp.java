package com.webill.core.model.dianhuabang;

import com.alibaba.fastjson.JSONObject;

/** 
 * @ClassName: DHBCallbackResp 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年2月1日 上午9:47:58 
 */
public class DHBCallbackResp {
	private Integer status; //报告生成状态（成功 0，失败 2）
	private String sid; //电话邦唯一标识
	private String tel; //手机号
	private String uid; //用户唯一标识
	
	public static DHBCallbackResp fromJsonString(String jsonString){
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		
		DHBCallbackResp resp = new DHBCallbackResp();
		
		if(jsonObject.containsKey("status")) {
			resp.setStatus(jsonObject.getIntValue("status"));
		}
		if(jsonObject.containsKey("sid")) {
			resp.setSid(jsonObject.getString("sid"));
		}
		if(jsonObject.containsKey("tel")){
			resp.setTel(jsonObject.getString("tel"));
		}
		if(jsonObject.containsKey("uid")){
			resp.setUid(jsonObject.getString("uid"));
		}
		return resp;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
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
}
