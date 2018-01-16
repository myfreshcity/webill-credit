package com.webill.core.model;

import java.util.ArrayList;
import java.util.List;

/** 
 * @ClassName: PriceArgs 
 * @Description: 承保所需参数
 * @author: WangLongFei
 * @date: 2017年12月5日 上午10:42:19  
 */
public class PriceArgs{
	private int productId;
	
	private int productPlanId;
	
	private List<Gene> genes = new ArrayList<Gene>();

	public Integer getProductId() {
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

	public List<Gene> getGenes() {
		return genes;
	}

	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}

}
