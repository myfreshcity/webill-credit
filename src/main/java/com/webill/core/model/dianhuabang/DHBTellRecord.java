package com.webill.core.model.dianhuabang;

import java.util.List;

/** 
 * @ClassName: DHBTellRecord 
 * @Description: 通话号码列表
 * @author: ZhangYadong
 * @date: 2018年3月8日
 */
public class DHBTellRecord {
	private String formatTel; //通话号码
	private String fanchaTelloc; //号码归属地
	private String callTimes; //联系次数
	private String callLength; //通话时长（秒）
	private int callOutTimes; //主叫次数
	private int callInTimes; //被叫次数
	private List<?> tagsTinancial; //金融标签
	private String tagsLabel; //号码标记
	private int tagsLabelTimes; //号码标记次数
	private String tagsYellowPage; //黄页名称
	private String recentCallTime; //最近一次通话时间
	private String firstCallTime; //首次通话时间
	
	public String getFormatTel() {
		return formatTel;
	}
	public void setFormatTel(String formatTel) {
		this.formatTel = formatTel;
	}
	public String getFanchaTelloc() {
		return fanchaTelloc;
	}
	public void setFanchaTelloc(String fanchaTelloc) {
		this.fanchaTelloc = fanchaTelloc;
	}
	public String getCallTimes() {
		return callTimes;
	}
	public void setCallTimes(String callTimes) {
		this.callTimes = callTimes;
	}
	public String getCallLength() {
		return callLength;
	}
	public void setCallLength(String callLength) {
		this.callLength = callLength;
	}
	public int getCallOutTimes() {
		return callOutTimes;
	}
	public void setCallOutTimes(int callOutTimes) {
		this.callOutTimes = callOutTimes;
	}
	public int getCallInTimes() {
		return callInTimes;
	}
	public void setCallInTimes(int callInTimes) {
		this.callInTimes = callInTimes;
	}
	public List<?> getTagsTinancial() {
		return tagsTinancial;
	}
	public void setTagsTinancial(List<?> tagsTinancial) {
		this.tagsTinancial = tagsTinancial;
	}
	public String getTagsLabel() {
		return tagsLabel;
	}
	public void setTagsLabel(String tagsLabel) {
		this.tagsLabel = tagsLabel;
	}
	public int getTagsLabelTimes() {
		return tagsLabelTimes;
	}
	public void setTagsLabelTimes(int tagsLabelTimes) {
		this.tagsLabelTimes = tagsLabelTimes;
	}
	public String getTagsYellowPage() {
		return tagsYellowPage;
	}
	public void setTagsYellowPage(String tagsYellowPage) {
		this.tagsYellowPage = tagsYellowPage;
	}
	public String getRecentCallTime() {
		return recentCallTime;
	}
	public void setRecentCallTime(String recentCallTime) {
		this.recentCallTime = recentCallTime;
	}
	public String getFirstCallTime() {
		return firstCallTime;
	}
	public void setFirstCallTime(String firstCallTime) {
		this.firstCallTime = firstCallTime;
	}
	
}
