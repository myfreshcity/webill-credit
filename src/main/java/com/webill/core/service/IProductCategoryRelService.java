package com.webill.core.service;

import com.webill.core.model.ProductCategoryRel;
import com.webill.framework.service.ISuperService;

/**
 *
 * ProductCategoryRel 数据服务层接口
 *
 */
public interface IProductCategoryRelService extends ISuperService<ProductCategoryRel> {

	/** 
	 * @Title: getProdCatRelByProdId 
	 * @Description: 根据产品ID获取产品分类实体
	 * @author ZhangYadong
	 * @date 2017年12月28日 下午6:04:23
	 * @param prodId
	 * @return
	 * @return ProductCategoryRel
	 */
	public ProductCategoryRel getProdCatRelByProdId(Integer prodId);
}