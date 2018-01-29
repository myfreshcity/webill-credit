package com.webill.core.model.report;
/** 
 * @ClassName: Financial_Call_Info 
 * @Description: 金融类通话信息
 * @author ZhangYadong
 * @date 2018年1月25日 上午10:53:26 
 */
public class FinancialCallInfo {
	/**
	 * 聚信立-对应behavior_check.check_point，分析点
	 */
	private String check_point;
	/**
	 * （微账房-检查项）
	 * 聚信立-对应behavior_check.check_point_cn，分析点中文
	 * 电话邦-对应calls_sa_by_tags_financial.tags_name，金融标签
	 */
	private String check_point_cn;
	/**
	 * （微账房-结果）
	 * 聚信立-对应behavior_check.result，检查结果
	 * 电话邦-对应calls_sa_by_tags_financial.contact_last_time，最后一次联系时间
	 */
	private String result;
	/**
	 * （微账房-依据）
	 * 聚信立对应behavior_check.evidence，证据
	 * 电话邦-对应calls_sa_by_tags_financial.contact_detail，详细记录数据节点（list）
	 */
	private String evidence;
	/**
	 * 聚信立-对应behavior_check.score，标记
	 */
	private Integer score;
	
	public String getCheck_point() {
		return check_point;
	}
	public void setCheck_point(String check_point) {
		this.check_point = check_point;
	}
	public String getCheck_point_cn() {
		return check_point_cn;
	}
	public void setCheck_point_cn(String check_point_cn) {
		this.check_point_cn = check_point_cn;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getEvidence() {
		return evidence;
	}
	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
}
