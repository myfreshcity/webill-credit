package com.webill.core.model;

import java.util.List;

/** 
* @ClassName: SelectJson 
* @Description: 
* @author ZhangYadong
* @date 2017年12月12日 下午5:04:16 
*/
public class SelectJson {
	private String key;
	private String value;
	private List<SelectJson> data;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<SelectJson> getData() {
		return data;
	}
	public void setData(List<SelectJson> data) {
		this.data = data;
	}
}
