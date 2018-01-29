package com.webill.core.model.report;
/** 
 * @ClassName: Contact_Region 
 * @Description: 联系人区域汇总
 * @author ZhangYadong
 * @date 2018年1月25日 上午11:18:53 
 */
public class ContactRegion {
	/**
	 * 聚信立-对应contact_region.region_loc，地区名称
	 * 电话邦-对应calls_sa_by_region.region，区域
	 */
	private String region_loc;
	/**
	 * 聚信立-对应contact_region.region_uniq_num_cnt，号码数量
	 * 电话邦-对应calls_sa_by_region.total_format_tel，号码数量
	 */
	private String region_uniq_num_cnt;
	/**
	 * 聚信立-对应contact_region.region_call_in_cnt，电话呼入次数
	 * 电话邦-对应calls_sa_by_region.call_in_times，呼入次数
	 */
	private String region_call_in_cnt;
	/**
	 * 聚信立-对应contact_region.region_call_out_cnt，电话呼出次数
	 * 电话邦-对应calls_sa_by_region.call_out_times，呼出次数
	 */
	private String region_call_out_cnt;
	
	public String getRegion_loc() {
		return region_loc;
	}
	public void setRegion_loc(String region_loc) {
		this.region_loc = region_loc;
	}
	public String getRegion_uniq_num_cnt() {
		return region_uniq_num_cnt;
	}
	public void setRegion_uniq_num_cnt(String region_uniq_num_cnt) {
		this.region_uniq_num_cnt = region_uniq_num_cnt;
	}
	public String getRegion_call_in_cnt() {
		return region_call_in_cnt;
	}
	public void setRegion_call_in_cnt(String region_call_in_cnt) {
		this.region_call_in_cnt = region_call_in_cnt;
	}
	public String getRegion_call_out_cnt() {
		return region_call_out_cnt;
	}
	public void setRegion_call_out_cnt(String region_call_out_cnt) {
		this.region_call_out_cnt = region_call_out_cnt;
	}
}
