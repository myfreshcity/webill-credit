package com.webill.core.model.report;
/** 
 * @ClassName: Cuishou 
 * @Description: 催收信息数据节点|疑似催收信息数据节点（聚信立高级版）
 * @author ZhangYadong
 * @date 2018年1月29日 上午10:04:53 
 */
public class Cuishou {
	/**
	 * 电话邦-对应cuishou_risk_detection.yisicuishou.nums_tel，号码个数
	 */
	private Integer nums_tel;
	private Integer nums_tel_degree;
	/**
	 * 电话邦-对应cuishou_risk_detection.yisicuishou.call_times，通话次数
	 */
	private String call_times;
	/**
	 * 电话邦-对应cuishou_risk_detection.yisicuishou.call_in_times，被叫次数
	 */
	private Integer call_in_times;
	private Integer call_in_times_degree;
	/**
	 * 电话邦-对应cuishou_risk_detection.yisicuishou.call_in_length，被叫时长(秒)
	 */
	private Integer call_in_length;
	private Integer call_in_length_degree;
	/**
	 * 电话邦-对应cuishou_risk_detection.yisicuishou.call_in_less_15，被叫时长 15s 以下的次数
	 */
	private Integer call_in_less_15;
	private Integer call_in_less_15_degree;
	/**
	 * 电话邦-对应cuishou_risk_detection.yisicuishou.most_times_by_tel，被同一号码呼叫的最多次数
	 */
	private Integer most_times_by_tel;
	private Integer most_times_by_tel_degree;
	/**
	 * 电话邦-对应cuishou_risk_detection.yisicuishou.up_2_times_by_tel，被同一号码呼叫 2 次以上的号码个数
	 */
	private Integer up_2_times_by_tel;
	private Integer up_2_times_by_tel_degree;
	/**
	 * 电话邦-对应cuishou_risk_detection.yisicuishou.call_out_times，主叫次数
	 */
	private Integer call_out_times;
	private Integer call_out_times_degree;
	/**
	 * 电话邦-对应cuishou_risk_detection.yisicuishou.call_out_length，主叫时长(秒)
	 */
	private Integer call_out_length;
	private Integer call_out_length_degree;
	/**
	 * 电话邦-对应cuishou_risk_detection.yisicuishou.7day_times，近 7 天被催收呼叫次数
	 */
	private Integer day7_times;
	private Integer day7_times_degree;
	/**
	 * 电话邦-对应cuishou_risk_detection.yisicuishou.30day_times，近 7-30 天被催收呼叫次数
	 */
	private Integer day30_times;
	private Integer day30_times_degree;
	/**
	 * 电话邦-对应cuishou_risk_detection.yisicuishou.60day_times，近 30-60 天被催收呼叫次数
	 */
	private Integer day60_times;
	private Integer day60_times_degree;
	/**
	 * 电话邦-对应cuishou_risk_detection.yisicuishou.90day_times，近 60-90 天被催收呼叫次数
	 */
	private Integer day90_times;
	private Integer day90_times_degree;
	/**
	 * 电话邦-对应cuishou_risk_detection.yisicuishou.120day_times，近 90-120 天被催收呼叫次数
	 */
	private Integer day120_times;
	private Integer day120_times_degree;
	
	
	public Integer getNums_tel() {
		return nums_tel;
	}
	public void setNums_tel(Integer nums_tel) {
		this.nums_tel = nums_tel;
	}
	public String getCall_times() {
		return call_times;
	}
	public void setCall_times(String call_times) {
		this.call_times = call_times;
	}
	public Integer getCall_in_times() {
		return call_in_times;
	}
	public void setCall_in_times(Integer call_in_times) {
		this.call_in_times = call_in_times;
	}
	public Integer getCall_in_length() {
		return call_in_length;
	}
	public void setCall_in_length(Integer call_in_length) {
		this.call_in_length = call_in_length;
	}
	public Integer getCall_in_less_15() {
		return call_in_less_15;
	}
	public void setCall_in_less_15(Integer call_in_less_15) {
		this.call_in_less_15 = call_in_less_15;
	}
	public Integer getMost_times_by_tel() {
		return most_times_by_tel;
	}
	public void setMost_times_by_tel(Integer most_times_by_tel) {
		this.most_times_by_tel = most_times_by_tel;
	}
	public Integer getUp_2_times_by_tel() {
		return up_2_times_by_tel;
	}
	public void setUp_2_times_by_tel(Integer up_2_times_by_tel) {
		this.up_2_times_by_tel = up_2_times_by_tel;
	}
	public Integer getCall_out_times() {
		return call_out_times;
	}
	public void setCall_out_times(Integer call_out_times) {
		this.call_out_times = call_out_times;
	}
	public Integer getCall_out_length() {
		return call_out_length;
	}
	public void setCall_out_length(Integer call_out_length) {
		this.call_out_length = call_out_length;
	}
	public Integer getDay7_times() {
		return day7_times;
	}
	public void setDay7_times(Integer day7_times) {
		this.day7_times = day7_times;
	}
	public Integer getDay30_times() {
		return day30_times;
	}
	public void setDay30_times(Integer day30_times) {
		this.day30_times = day30_times;
	}
	public Integer getDay60_times() {
		return day60_times;
	}
	public void setDay60_times(Integer day60_times) {
		this.day60_times = day60_times;
	}
	public Integer getDay90_times() {
		return day90_times;
	}
	public void setDay90_times(Integer day90_times) {
		this.day90_times = day90_times;
	}
	public Integer getDay120_times() {
		return day120_times;
	}
	public void setDay120_times(Integer day120_times) {
		this.day120_times = day120_times;
	}
	public Integer getNums_tel_degree() {
		return nums_tel_degree;
	}
	public void setNums_tel_degree(Integer nums_tel_degree) {
		this.nums_tel_degree = nums_tel_degree;
	}
	public Integer getCall_in_times_degree() {
		return call_in_times_degree;
	}
	public void setCall_in_times_degree(Integer call_in_times_degree) {
		this.call_in_times_degree = call_in_times_degree;
	}
	public Integer getCall_in_length_degree() {
		return call_in_length_degree;
	}
	public void setCall_in_length_degree(Integer call_in_length_degree) {
		this.call_in_length_degree = call_in_length_degree;
	}
	public Integer getCall_in_less_15_degree() {
		return call_in_less_15_degree;
	}
	public void setCall_in_less_15_degree(Integer call_in_less_15_degree) {
		this.call_in_less_15_degree = call_in_less_15_degree;
	}
	public Integer getMost_times_by_tel_degree() {
		return most_times_by_tel_degree;
	}
	public void setMost_times_by_tel_degree(Integer most_times_by_tel_degree) {
		this.most_times_by_tel_degree = most_times_by_tel_degree;
	}
	public Integer getUp_2_times_by_tel_degree() {
		return up_2_times_by_tel_degree;
	}
	public void setUp_2_times_by_tel_degree(Integer up_2_times_by_tel_degree) {
		this.up_2_times_by_tel_degree = up_2_times_by_tel_degree;
	}
	public Integer getCall_out_times_degree() {
		return call_out_times_degree;
	}
	public void setCall_out_times_degree(Integer call_out_times_degree) {
		this.call_out_times_degree = call_out_times_degree;
	}
	public Integer getCall_out_length_degree() {
		return call_out_length_degree;
	}
	public void setCall_out_length_degree(Integer call_out_length_degree) {
		this.call_out_length_degree = call_out_length_degree;
	}
	public Integer getDay7_times_degree() {
		return day7_times_degree;
	}
	public void setDay7_times_degree(Integer day7_times_degree) {
		this.day7_times_degree = day7_times_degree;
	}
	public Integer getDay30_times_degree() {
		return day30_times_degree;
	}
	public void setDay30_times_degree(Integer day30_times_degree) {
		this.day30_times_degree = day30_times_degree;
	}
	public Integer getDay60_times_degree() {
		return day60_times_degree;
	}
	public void setDay60_times_degree(Integer day60_times_degree) {
		this.day60_times_degree = day60_times_degree;
	}
	public Integer getDay90_times_degree() {
		return day90_times_degree;
	}
	public void setDay90_times_degree(Integer day90_times_degree) {
		this.day90_times_degree = day90_times_degree;
	}
	public Integer getDay120_times_degree() {
		return day120_times_degree;
	}
	public void setDay120_times_degree(Integer day120_times_degree) {
		this.day120_times_degree = day120_times_degree;
	}
}
