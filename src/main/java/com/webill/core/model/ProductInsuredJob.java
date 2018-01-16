package com.webill.core.model;

import java.util.List;

/** 
* @ClassName: ProductInsuredJob 
* @Description: 
* @author ZhangYadong
* @date 2017年12月7日 下午8:29:27 
*/
public class ProductInsuredJob {
	private long id; //职业id（用户承保接口）
	private String level; //级别
	private String name; //职业名称
	private long parentId; //上一级职业id
	private String isInsure; //是否支持投保
	
	private List<ProductInsuredJob> jobs;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getIsInsure() {
		return isInsure;
	}
	public void setIsInsure(String isInsure) {
		this.isInsure = isInsure;
	}
	public List<ProductInsuredJob> getJobs() {
		return jobs;
	}
	public void setJobs(List<ProductInsuredJob> jobs) {
		this.jobs = jobs;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
}
