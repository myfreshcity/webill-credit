package com.webill.core.model;
/** 
* @ClassName: PlanProduct 
* @Description: 
* @author ZhangYadong
* @date 2017年12月5日 下午4:33:10 
*/
public class PlanProduct {
	private String defaultPlan; //默认计划
	private String caseCode; //方案代码
	private String planName; //计划名称
	public String getDefaultPlan() {
		return defaultPlan;
	}
	public void setDefaultPlan(String defaultPlan) {
		this.defaultPlan = defaultPlan;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
}
