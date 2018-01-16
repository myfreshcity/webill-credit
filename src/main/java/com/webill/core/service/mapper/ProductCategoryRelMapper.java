package com.webill.core.service.mapper;

import org.springframework.data.repository.query.Param;

import com.webill.core.model.ProductCategoryRel;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * ProductCategoryRel 表数据库控制层接口
 *
 */
public interface ProductCategoryRelMapper extends AutoMapper<ProductCategoryRel> {
	/** 
	 * @Title: getProdCatRelByProdId 
	 * @Description: 根据产品ID获取产品分类实体
	 * @author ZhangYadong
	 * @date 2017年12月28日 下午6:04:23
	 * @param prodId
	 * @return
	 * @return ProductCategoryRel
	 */
	public ProductCategoryRel getProdCatRelByProdId(@Param("prodId") Integer prodId);
}