package com.webill.core.model;
/** 
* @ClassName: GeneParam 
* @Description: 
* @author ZhangYadong
* @date 2017年12月7日 下午2:58:09 
*/
public class GeneParam {
	private String key; //属性key
	private String protectItemId; //保障项目id
	private String value; //试算因子值
	private int sort; //展示顺序
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
