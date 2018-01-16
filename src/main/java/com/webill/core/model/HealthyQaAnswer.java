package com.webill.core.model;
/** 
 * @ClassName: HealthyQaAnswer 
 * @Description: 
 * @author ZhangYadong
 * @date 2017年12月22日 下午4:26:46 
 */
public class HealthyQaAnswer {
	private int answerId; //答案id，对应HealthyAnswer中answerId
	private String answerValue; //答案值，对应HealthyAttributeValue中controlValue的取值
	private String keyCode; //属性key，对应HealthyAttribute中keyCode的取值
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
