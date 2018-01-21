package com.webill.core.model.juxinli;

import java.io.Serializable;

/** 
 * @ClassName: JXLReqMsgTpl 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:53:21 
 */
public class JXLReqMsgTpl implements Serializable {
	private static final long serialVersionUID = -6396659070766746853L;
	private String type;	
	private String method;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}	
}
