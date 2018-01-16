package com.webill.core.model;

import java.util.ArrayList;
import java.util.List;

public class Area {
	private String areaId;
	
	private String areaName;
	
	private Area area;
	
	private List<Area> areaList = new ArrayList<Area>();
	
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public List<Area> getAearList() {
		return areaList;
	}

	public void setAearList(List<Area> areaList) {
		this.areaList = areaList;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
