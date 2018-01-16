package com.webill.core.model;
/** 
 * @ClassName: HealthyQaModuleHelp 
 * @Description: 
 * @author ZhangYadong
 * @date 2017年12月22日 下午5:28:33 
 */
public class HealthyQaModuleHelp {
	private String moduleId; //模块id，对应HealthyModule中moduleId
	private int questionId; //题目id，对应HealthyQuestion中questionId
	private int parentId; //上级题目id，对应HealthyQuestion中parentId
	private int questionSort; //展示顺序，对应HealthyQuestion中questionSort
	private int answerId; //答案id，对应HealthyAnswer中answerId
	private String answerValue; //答案值，对应HealthyAttributeValue中controlValue的取值
	private String keyCode; //属性key，对应HealthyAttribute中keyCode的取值
	
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
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
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	public String getAnswerValue() {
		return answerValue;
	}
	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}
	public String getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
}
