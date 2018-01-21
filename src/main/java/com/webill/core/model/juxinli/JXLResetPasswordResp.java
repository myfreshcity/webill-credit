package com.webill.core.model.juxinli;

import java.io.Serializable;

/**
 * @ClassName: JXLResetPasswordResp
 * @Description:
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:56:31
 */
public class JXLResetPasswordResp implements Serializable {
	private static final long serialVersionUID = 4331519609443311479L;
	private boolean success; // 请求是否成功
	private JXLResetPasswordRespData data; // 重置密码服务启动成功
	private String message; //

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public JXLResetPasswordRespData getData() {
		return data;
	}

	public void setData(JXLResetPasswordRespData data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
