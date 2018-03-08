package com.webill.core.model.dianhuabang;

public class DHBCallsRecord {
	private String callCost; // 通话费
	private String callTel; // 被叫号码
	private Long callTime; // 通话起始时间
	private String callMethod; // 呼叫类型(e.g. 主叫)
	private String callFrom; // 本机通话地
	private String callType; // 通话类型(e.g. 国内电话)
	private String callDuration; // 通话时长
	private String callTo; // 对方归属地
	private String sortWay; // 排序方式：1-时间倒序，2-通话时长降序
	private String reportKey; // 报告key
	private Integer currentPage; // 当前页
	private Integer pageSize; // 每页显示多少数据
	private String timeFrom; // 通话起始时间-开始时间
	private String timeTo; // 通话起始时间-结束时间
	private Integer index; //索引
	private String tagsLabel; //号码标记
	
	public String getCallCost() {
		return callCost;
	}
	public void setCallCost(String callCost) {
		this.callCost = callCost;
	}
	public String getCallTel() {
		return callTel;
	}
	public void setCallTel(String callTel) {
		this.callTel = callTel;
	}
	public Long getCallTime() {
		return callTime;
	}
	public void setCallTime(Long callTime) {
		this.callTime = callTime;
	}
	public String getCallMethod() {
		return callMethod;
	}
	public void setCallMethod(String callMethod) {
		this.callMethod = callMethod;
	}
	public String getCallFrom() {
		return callFrom;
	}
	public void setCallFrom(String callFrom) {
		this.callFrom = callFrom;
	}
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	public String getCallDuration() {
		return callDuration;
	}
	public void setCallDuration(String callDuration) {
		this.callDuration = callDuration;
	}
	public String getCallTo() {
		return callTo;
	}
	public void setCallTo(String callTo) {
		this.callTo = callTo;
	}
	public String getSortWay() {
		return sortWay;
	}
	public void setSortWay(String sortWay) {
		this.sortWay = sortWay;
	}
	public String getReportKey() {
		return reportKey;
	}
	public void setReportKey(String reportKey) {
		this.reportKey = reportKey;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getTimeFrom() {
		return timeFrom;
	}
	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}
	public String getTimeTo() {
		return timeTo;
	}
	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getTagsLabel() {
		return tagsLabel;
	}
	public void setTagsLabel(String tagsLabel) {
		this.tagsLabel = tagsLabel;
	}
	
}
