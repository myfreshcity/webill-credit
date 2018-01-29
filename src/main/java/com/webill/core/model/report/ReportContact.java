package com.webill.core.model.report;
/** 
 * @ClassName: Report_Contact 
 * @Description: 报告联系人
 * @author ZhangYadong
 * @date 2018年1月24日 下午6:10:10 
 */
public class ReportContact {
	/**
	 * 聚信立-对应application_check.(app_point="contact",check_points.contact_name)，联系人姓名
	 */
	private String contact_name;
	/**
	 * 聚信立-对应application_check.(app_point="contact",check_points.check_xiaohao)，临时小号检查
	 */
	private String check_xiaohao;
	/**
	 * 聚信立-对应application_check.(app_point="contact",check_points.relationship)，联系人关系
	 */
	private String relationship;
	/**
	 * 聚信立-对应application_check.(app_point="contact",check_points.check_mobile)，联系时长手机号
	 */
	private String mobile_no;
	/**
	 * 聚信立-对应application_check.(app_point="contact",check_points.key_value)，联系天数
	 */
	private String call_day;
	/**
	 * 聚信立-对应application_check.(app_point="contact",check_points.check_mobile)，联系时长排名
	 */
	private String call_time_rank;
	/**
	 * 聚信立-对应application_check.(app_point="contact",check_points.check_mobile)，联系次数
	 */
	private String call_cnt;
	/**
	 * 聚信立-对应application_check.(app_point="contact",check_points.check_mobile)，联系时长
	 */
	private String call_len;
	
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getCheck_xiaohao() {
		return check_xiaohao;
	}
	public void setCheck_xiaohao(String check_xiaohao) {
		this.check_xiaohao = check_xiaohao;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getCall_day() {
		return call_day;
	}
	public void setCall_day(String call_day) {
		this.call_day = call_day;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getCall_time_rank() {
		return call_time_rank;
	}
	public void setCall_time_rank(String call_time_rank) {
		this.call_time_rank = call_time_rank;
	}
	public String getCall_cnt() {
		return call_cnt;
	}
	public void setCall_cnt(String call_cnt) {
		this.call_cnt = call_cnt;
	}
	public String getCall_len() {
		return call_len;
	}
	public void setCall_len(String call_len) {
		this.call_len = call_len;
	}
	
}
