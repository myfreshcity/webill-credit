package com.webill.core.model;

import java.util.List;

/** 
 * @ClassName: HealthyQa 
 * @Description: 
 * @author ZhangYadong
 * @date 2017年12月22日 下午4:20:33 
 */
public class HealthyQaHelp {
	private String healthyId; //健康告知题目id，对应HealthStatementResp中healthyId
	
	private List<HealthyQaModuleHelp> healthyQaModules; //告知模块信息列表
	
	public String getHealthyId() {
		return healthyId;
	}
	public void setHealthyId(String healthyId) {
		this.healthyId = healthyId;
	}
	public List<HealthyQaModuleHelp> getHealthyQaModules() {
		return healthyQaModules;
	}
	public void setHealthyQaModules(List<HealthyQaModuleHelp> healthyQaModules) {
		this.healthyQaModules = healthyQaModules;
	}
}
