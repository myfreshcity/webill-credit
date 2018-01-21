package com.webill.core.model.juxinli;

import com.alibaba.fastjson.JSONObject;

/** 
 * @ClassName: JXLSubmitFormResp 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:43:36 
 */
public class JXLSubmitFormResp {
	
	private String token;
	
	private String mobileNo;
	
	private JXLDataSource dataSource;
	
	private boolean success = false;
	
	private String message = null;
	
	private String code = null;
	
	public void parseJson(JSONObject json){
		if(json.containsKey("message")) {
			this.setMessage(json.getString("message"));
		}
		if(json.containsKey("success")) {
			this.setSuccess(json.getBoolean("success"));
		}
		if(json.containsKey("code")) {
			this.setCode(json.getString("code"));
		}
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public JXLDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(JXLDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public static JXLSubmitFormResp fromJsonString(String jsonString){
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		
		JXLSubmitFormResp resp = new JXLSubmitFormResp();
		
		resp.parseJson(jsonObject);
		
		if(jsonObject.containsKey("data")){
			JSONObject data = jsonObject.getJSONObject("data");
			resp.setToken(data.getString("token"));
			resp.setMobileNo(data.getString("cell_phone_num"));
			JSONObject dataSourceJson = data.getJSONObject("datasource");
			JXLDataSource dataSource = JXLDataSource.fromJson(dataSourceJson);
			resp.setDataSource(dataSource);
		}

		return resp;
	}
	
	
	/*public String toString(){
		return JSONObject.fromObject(this).toString();
	}*/

}
