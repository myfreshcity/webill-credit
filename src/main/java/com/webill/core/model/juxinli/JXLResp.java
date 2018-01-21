package com.webill.core.model.juxinli;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: JXLResp
 * @Description:
 * @author ZhangYadong
 * @date 2018年1月18日 下午1:12:38
 */
public class JXLResp {

	private boolean success = false;

	private String type = null;

	private String content = null;

	private int processCode = 0;

	private boolean finish = false;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getProcessCode() {
		return processCode;
	}

	public void setProcessCode(int processCode) {
		this.processCode = processCode;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public static JXLResp fromJsonString(String jsonString) {
		JXLResp resp = new JXLResp();

		JSONObject json = JSONObject.parseObject(jsonString);
		resp.setSuccess(json.getBoolean("success"));

		if (json.containsKey("data")) {
			JSONObject data = json.getJSONObject("data");
			resp.setType(data.getString("type"));
			resp.setContent(data.getString("content"));
			resp.setProcessCode(data.getIntValue("process_code"));
			resp.setFinish(data.getBoolean("finish"));
		}
		return resp;
	}

}
