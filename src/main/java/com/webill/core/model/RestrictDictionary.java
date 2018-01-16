package com.webill.core.model;
/** 
* @ClassName: RestrictDictionary 
* @Description: 试算因子选项
* @author ZhangYadong
* @date 2017年12月4日 下午4:03:15 
*/
public class RestrictDictionary {
	private	String	value;	//选项值
	private	String	type;	//类型 1：普通选项 2：最小值到最大值步长值
	private	String	name;	//选项名称
	private	int	min;	//最小值（type为2时使用）
	private	int	max;	//最大值（type为2时使用）
	private	int	step;	//步长（type为2时使用）
	private	String	unit;	//单位
	private	SubRestrictDictionary	subDictionary;	//子约束
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public SubRestrictDictionary getSubDictionary() {
		return subDictionary;
	}
	public void setSubDictionary(SubRestrictDictionary subDictionary) {
		this.subDictionary = subDictionary;
	}
}
