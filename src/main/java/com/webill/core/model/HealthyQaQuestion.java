package com.webill.core.model;

import java.util.List;

/** 
 * @ClassName: HealthyQaQuestion 
 * @Description: 
 * @author ZhangYadong
 * @date 2017年12月22日 下午4:25:54 
 */
public class HealthyQaQuestion {
	private int questionId; //题目id，对应HealthyQuestion中questionId
	private int parentId; //上级题目id，对应HealthyQuestion中parentId
	private int questionSort; //展示顺序，对应HealthyQuestion中questionSort
	private List<HealthyQaAnswer> healthyQaAnswers; //答案信息列表
//	private List<HealthyQaQuestion> childrens; //子问题信息列表
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getQuestionSort() {
		return questionSort;
	}
	public void setQuestionSort(int questionSort) {
		this.questionSort = questionSort;
	}
	public List<HealthyQaAnswer> getHealthyQaAnswers() {
		return healthyQaAnswers;
	}
	public void setHealthyQaAnswers(List<HealthyQaAnswer> healthyQaAnswers) {
		this.healthyQaAnswers = healthyQaAnswers;
	}
}
