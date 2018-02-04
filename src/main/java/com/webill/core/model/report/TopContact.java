package com.webill.core.model.report;
/** 
 * @ClassName: Top_Contact
 * @Description: 长时间联系人（Top10）-联系时长|高频联系人（Top10）-联系次数 
 * @author ZhangYadong
 * @date 2018年1月25日 上午11:33:06 
 */
public class TopContact {
	/**
	 * 电话邦-长时间-对应calls_sa_by_length.format_tel，通话号码
	 * 电话邦-高频-对应calls_sa_by_times.format_tel，通话号码
	 * 聚信立-对应contact_list.phone_num，号码
	 */
	private String format_tel;
	/**
	 * 电话邦-长时间-对应calls_sa_by_length.tags_label，标记
	 * 电话邦-高频-对应calls_sa_by_times.tags_label，标记
	 * 聚信立-对应contact_list.contact_name，号码标注
	 */
	private String tags_label;
	/**
	 * 电话邦-长时间-对应calls_sa_by_length.call_times，联系次数
	 * 电话邦-高频-对应calls_sa_by_times.call_times，联系次数
	 * 聚信立-对应contact_list.call_cnt，通话次数
	 */
	private String call_times;
	/**
	 * 电话邦-长时间-对应calls_sa_by_length.call_length，通话总时长（秒）
	 * 电话邦-高频-对应calls_sa_by_times.call_length，通话总时长
	 * 聚信立-对应contact_list.call_len，通话时长（分钟）
	 */
	private String call_length;

	/**
	 * 电话邦-对应call_log_group_by_tel.fancha_telloc，号码归属地
	 * 聚信立-对应contact_list.phone_num_loc，号码归属地
	 */
	private String fancha_telloc;
	/**
	 * 电话邦-对应call_log_group_by_tel.call_out_times，主叫次数
	 * 聚信立-对应contact_list.call_out_cnt，呼出次数
	 */
	private Integer call_out_times;
	/**
	 * 电话邦-对应call_log_group_by_tel.call_in_times，被叫次数
	 * 聚信立-对应contact_list.call_in_cnt，呼入次数
	 */
	private Integer call_in_times; 
	
	public String getFormat_tel() {
		return format_tel;
	}
	public void setFormat_tel(String format_tel) {
		this.format_tel = format_tel;
	}
	public String getTags_label() {
		return tags_label;
	}
	public void setTags_label(String tags_label) {
		this.tags_label = tags_label;
	}
	public String getCall_times() {
		return call_times;
	}
	public void setCall_times(String call_times) {
		this.call_times = call_times;
	}
	public String getCall_length() {
		return call_length;
	}
	public void setCall_length(String call_length) {
		this.call_length = call_length;
	}
	public String getFancha_telloc() {
		return fancha_telloc;
	}
	public void setFancha_telloc(String fancha_telloc) {
		this.fancha_telloc = fancha_telloc;
	}
	public Integer getCall_out_times() {
		return call_out_times;
	}
	public void setCall_out_times(Integer call_out_times) {
		this.call_out_times = call_out_times;
	}
	public Integer getCall_in_times() {
		return call_in_times;
	}
	public void setCall_in_times(Integer call_in_times) {
		this.call_in_times = call_in_times;
	}
}
