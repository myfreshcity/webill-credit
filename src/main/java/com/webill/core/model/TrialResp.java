package com.webill.core.model;

import java.util.List;

/** 
* @ClassName: TrialResp 
* @Description: 
* @author ZhangYadong
* @date 2017年12月7日 下午2:54:31 
*/
public class TrialResp {
	private String transNo; //交易流水号
	private int partnerId; //开发者身份标识
	private String caseCode; //方案代码
	private long price; //产品支付单价（单位：分）
	private long originalPrice; //产品原价（单位：分）
	private List<RestrictGene> restrictGenes; //试算因子
	private List<ProtectItem> protectItems; //保障项目
	private TrailPremiumParam priceArgs; //试算结果，用于承保接口
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
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public long getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(long originalPrice) {
		this.originalPrice = originalPrice;
	}
	public List<RestrictGene> getRestrictGenes() {
		return restrictGenes;
	}
	public void setRestrictGenes(List<RestrictGene> restrictGenes) {
		this.restrictGenes = restrictGenes;
	}
	public List<ProtectItem> getProtectItems() {
		return protectItems;
	}
	public void setProtectItems(List<ProtectItem> protectItems) {
		this.protectItems = protectItems;
	}
	public TrailPremiumParam getPriceArgs() {
		return priceArgs;
	}
	public void setPriceArgs(TrailPremiumParam priceArgs) {
		this.priceArgs = priceArgs;
	}
}
