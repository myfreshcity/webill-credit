package com.webill.core.model;

import java.util.List;

/** 
* @ClassName: TrailPremiumParam 
* @Description: 
* @author ZhangYadong
* @date 2017年12月7日 下午2:57:15 
*/
public class TrailPremiumParam {
	private int productId; //产品id
	private int productPlanId; //产品计划id
	private List<GeneParam> genes; //默认选定试算因子
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getProductPlanId() {
		return productPlanId;
	}
	public void setProductPlanId(int productPlanId) {
		this.productPlanId = productPlanId;
	}
	public List<GeneParam> getGenes() {
		return genes;
	}
	public void setGenes(List<GeneParam> genes) {
		this.genes = genes;
	}
}
