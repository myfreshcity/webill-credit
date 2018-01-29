package com.webill.core.model;

import java.util.ArrayList;
import java.util.List;

/** 
 * @ClassName: AreaChildren 
 * @Description: 微账房-省市区三级联动实体类
 * @author ZhangYadong
 * @date 2018年1月26日 下午3:12:52 
 */
public class AreaChildren {
	private String value;
	
	private String label;
	
	private AreaChildren area;
	
	private List<AreaChildren> children = new ArrayList<AreaChildren>();

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public AreaChildren getArea() {
		return area;
	}

	public void setArea(AreaChildren area) {
		this.area = area;
	}

	public List<AreaChildren> getChildren() {
		return children;
	}

	public void setChildren(List<AreaChildren> children) {
		this.children = children;
	}
}
