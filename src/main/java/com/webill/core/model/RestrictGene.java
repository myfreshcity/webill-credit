package com.webill.core.model;

import java.util.List;
import java.util.Map;

/** 
* @ClassName: RestrictGene 
* @Description: 试算因子
* @author ZhangYadong
* @date 2017年12月4日 下午4:01:16 
*/
public class RestrictGene {
	private int	protectItemId;	//保障项目id（试算因子为保障项目时不为空）
	private String	key;	//属性key（试算因子为保障项目时为空）
	private String	name;	//试算因子名称
	private String	defaultValue;	//默认值
	private int	type;	//类型 0：下拉框 1：日历 2：日历+下拉框 3：文本输入框 4：地区 5：职业 6：密码框 7：文本 8：对话框 9：单选框 10：复选框
	private boolean	display;	//是否展示 true：是 false：否
	private int	sort;	//展示顺序
	private List<RestrictDictionary>	values;	//试算因子选项
	private List<RestrictGene>	subRestrictGenes;	//子试算因子
	private	String provinceName; //省级名称
	private String cityName; //市级名称
	private List<InsurePrice> insurPrice; //基本保额
	
	public int getProtectItemId() {
		return protectItemId;
	}
	public void setProtectItemId(int protectItemId) {
		this.protectItemId = protectItemId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public List<RestrictDictionary> getValues() {
		return values;
	}
	public void setValues(List<RestrictDictionary> values) {
		this.values = values;
	}
	public List<RestrictGene> getSubRestrictGenes() {
		return subRestrictGenes;
	}
	public void setSubRestrictGenes(List<RestrictGene> subRestrictGenes) {
		this.subRestrictGenes = subRestrictGenes;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public List<InsurePrice> getInsurPrice() {
		return insurPrice;
	}
	public void setInsurPrice(List<InsurePrice> insurPrice) {
		this.insurPrice = insurPrice;
	}
}
