package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.ProductCategoryRel;
import com.webill.core.service.IProductCategoryRelService;
import com.webill.core.service.mapper.ProductCategoryRelMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * ProductCategoryRel 服务层接口实现类
 *
 */
@Service
public class ProductCategoryRelServiceImpl extends SuperServiceImpl<ProductCategoryRelMapper, ProductCategoryRel> implements IProductCategoryRelService {

	@Override
	public ProductCategoryRel getProdCatRelByProdId(Integer prodId) {
		ProductCategoryRel pcr = baseMapper.getProdCatRelByProdId(prodId);
		return pcr;
	}

}