package com.webill.core.model;

/** 
 * @ClassName: Gene
 * @Description: 承保所需参数
 * @author: WangLongFei
 * @date: 2017年12月1日 下午4:27:51  
 */
public class Gene{
	private String protectItemId;
	
	private String key;
	
	private String value;
	
	private int sort;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getProtectItemId() {
		return protectItemId;
	}
	public void setProtectItemId(String protectItemId) {
		this.protectItemId = protectItemId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}

}
