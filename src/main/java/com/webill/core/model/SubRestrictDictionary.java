package com.webill.core.model;
/** 
* @ClassName: SubRestrictDictionary 
* @Description: 子约束
* @author ZhangYadong
* @date 2017年12月4日 下午4:04:59 
*/
public class SubRestrictDictionary {
	private int	min;	//最小值
	private int	max;	//最大值
	private int	condition;	//执行条件（0：大于 1：大于等于 2：小于 3：小于等于 4：不等于 5：等于 6：包含 7：不包含 8：不投保 9：提示 10：关联变量）
	private String	unit;	//单位
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
	public int getCondition() {
		return condition;
	}
	public void setCondition(int condition) {
		this.condition = condition;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
