package com.webill.core.model;

import java.util.List;

/** 
 * @ClassName: HealthyQaModule 
 * @Description: 
 * @author ZhangYadong
 * @date 2017年12月22日 下午4:24:48 
 */
public class HealthyQaModule {
	private String moduleId; //模块id，对应HealthyModule中moduleId
	private List<HealthyQaQuestion> healthyQaQuestions; //题目信息列表
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public List<HealthyQaQuestion> getHealthyQaQuestions() {
		return healthyQaQuestions;
	}
	public void setHealthyQaQuestions(List<HealthyQaQuestion> healthyQaQuestions) {
		this.healthyQaQuestions = healthyQaQuestions;
	}
}
