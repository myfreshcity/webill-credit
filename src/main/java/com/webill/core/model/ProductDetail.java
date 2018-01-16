package com.webill.core.model;

import java.util.List;

/** 
* @ClassName: ProductDetail 
* @Description: 
* @author ZhangYadong
* @date 2017年11月30日 下午6:10:43 
*/
public class ProductDetail {
	private Integer productId; //产品ID
	private String productName; //产品名称
	private String planName; //计划名称
	private Long price; //默认价格
	private String mobileLogo; //保险公司logo
	private List<ProtectItem> protectItems; //保障项目
	private List<String> productRead; //产品解读（产品详情）
	private List<ProductFeature> features; //产品特色
	private List<String> fullDescription; //h5的banner图
	private List<String> tips; //投保须知
	private List<ProductProvision> masterProvisions; //主条款
	private List<ProductProvision> additionalProvisions; //附加条款
	private String premiumTable; //费率表
	private List<ProductProvision> claimTypes; //赔偿方式
	private String pictureNotify; //客户告知书
	private String samplePolicy; //保单样本
	private List<RestrictGene> restrictGenes; //试算因子
	private String insuAge; //承保年龄
	private String insuDateLimit; //保障期限
	private List<Faq> faqs; //常见问题
	private List<PlanProduct> planProds; //产品计划相关产品列表
	private String insureDeclare; //投保声明
	private String imgUrlShow; //商品显示图片
	private Integer autoPay; // 支付方式：0-手动支付 1-银行代扣 
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getMobileLogo() {
		return mobileLogo;
	}
	public void setMobileLogo(String mobileLogo) {
		this.mobileLogo = mobileLogo;
	}
	public List<ProtectItem> getProtectItems() {
		return protectItems;
	}
	public void setProtectItems(List<ProtectItem> protectItems) {
		this.protectItems = protectItems;
	}
	public List<String> getProductRead() {
		return productRead;
	}
	public void setProductRead(List<String> productRead) {
		this.productRead = productRead;
	}
	public List<ProductFeature> getFeatures() {
		return features;
	}
	public void setFeatures(List<ProductFeature> features) {
		this.features = features;
	}
	public List<String> getFullDescription() {
		return fullDescription;
	}
	public void setFullDescription(List<String> fullDescription) {
		this.fullDescription = fullDescription;
	}
	public List<String> getTips() {
		return tips;
	}
	public void setTips(List<String> tips) {
		this.tips = tips;
	}
	public List<ProductProvision> getMasterProvisions() {
		return masterProvisions;
	}
	public void setMasterProvisions(List<ProductProvision> masterProvisions) {
		this.masterProvisions = masterProvisions;
	}
	public List<ProductProvision> getAdditionalProvisions() {
		return additionalProvisions;
	}
	public void setAdditionalProvisions(List<ProductProvision> additionalProvisions) {
		this.additionalProvisions = additionalProvisions;
	}
	public String getPremiumTable() {
		return premiumTable;
	}
	public void setPremiumTable(String premiumTable) {
		this.premiumTable = premiumTable;
	}
	public List<ProductProvision> getClaimTypes() {
		return claimTypes;
	}
	public void setClaimTypes(List<ProductProvision> claimTypes) {
		this.claimTypes = claimTypes;
	}
	public String getPictureNotify() {
		return pictureNotify;
	}
	public void setPictureNotify(String pictureNotify) {
		this.pictureNotify = pictureNotify;
	}
	public String getSamplePolicy() {
		return samplePolicy;
	}
	public void setSamplePolicy(String samplePolicy) {
		this.samplePolicy = samplePolicy;
	}
	public List<RestrictGene> getRestrictGenes() {
		return restrictGenes;
	}
	public void setRestrictGenes(List<RestrictGene> restrictGenes) {
		this.restrictGenes = restrictGenes;
	}
	public String getInsuAge() {
		return insuAge;
	}
	public void setInsuAge(String insuAge) {
		this.insuAge = insuAge;
	}
	public String getInsuDateLimit() {
		return insuDateLimit;
	}
	public void setInsuDateLimit(String insuDateLimit) {
		this.insuDateLimit = insuDateLimit;
	}
	public List<Faq> getFaqs() {
		return faqs;
	}
	public void setFaqs(List<Faq> faqs) {
		this.faqs = faqs;
	}
	public List<PlanProduct> getPlanProds() {
		return planProds;
	}
	public void setPlanProds(List<PlanProduct> planProds) {
		this.planProds = planProds;
	}
	public String getInsureDeclare() {
		return insureDeclare;
	}
	public void setInsureDeclare(String insureDeclare) {
		this.insureDeclare = insureDeclare;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getImgUrlShow() {
		return imgUrlShow;
	}
	public void setImgUrlShow(String imgUrlShow) {
		this.imgUrlShow = imgUrlShow;
	}
	public Integer getAutoPay() {
		return autoPay;
	}
	public void setAutoPay(Integer autoPay) {
		this.autoPay = autoPay;
	}
}
