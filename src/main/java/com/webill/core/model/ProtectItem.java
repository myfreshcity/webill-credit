package com.webill.core.model;

import java.util.List;

/** 
* @ClassName: ProtectItem 
* @Description: 保障项目
* @author ZhangYadong
* @date 2017年12月4日 下午3:41:55 
*/
public class ProtectItem {
	private int protectItemId; //保障项目id
	private String defaultValue; //默认显示值
	private String name; //保障项目名
	private String description; //描述信息
	private int sort; //展示顺序
	private String relateCoverage; //保额关联试算因子（如果此属性有值表示当前保障项目的保额根据试算因子的取值自动变动，试算时不会动态返回当前试算因子的值）
	
	public int getProtectItemId() {
		return protectItemId;
	}
	public void setProtectItemId(int protectItemId) {
		this.protectItemId = protectItemId;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getRelateCoverage() {
		return relateCoverage;
	}
	public void setRelateCoverage(String relateCoverage) {
		this.relateCoverage = relateCoverage;
	}
}
