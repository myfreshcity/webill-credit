package com.webill.core.model;

/** 
 * @ClassName: HzNotify 
 * @Description: 慧择回调--基本参数
 * @author: WangLongFei
 * @date: 2017年11月30日 下午2:52:56  
 */
public class HzNotify{
	/**
	 * @fieldName: notifyType
	 * @fieldType: int
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午4:05:08 
	 * @Description: 通知类型 出单：3
	 */
	private int notifyType;
	
	/**
	 * @fieldName: sign
	 * @fieldType: String
	 * @author: WangLongFei
	 * @date: 2017年11月30日 下午4:06:14 
	 * @Description: 签名，MD5（key+data）
	 */
	private String sign;
	
	private Object data;

	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
	
}
