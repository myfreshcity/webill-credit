package com.webill.core.model;

import java.util.List;

/** 
 * @ClassName: HealthStateReq 
 * @Description: 
 * @author ZhangYadong
 * @date 2017年12月22日 下午4:19:12 
 */
public class HealthStateReq {
	private String transNo; //交易流水号，生成规则参考名词解释
	private int partnerId; //开发者身份标识，获取方式参考名词解释
	private String caseCode; //方案代码，获取方式参考名词解释
	private List<GeneParam> genes; //当前试算因子（所有试算因子）
	private HealthyQa qaAnswer; //健康告知答案信息
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public int getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public List<GeneParam> getGenes() {
		return genes;
	}
	public void setGenes(List<GeneParam> genes) {
		this.genes = genes;
	}
	public HealthyQa getQaAnswer() {
		return qaAnswer;
	}
	public void setQaAnswer(HealthyQa qaAnswer) {
		this.qaAnswer = qaAnswer;
	}
	
}
