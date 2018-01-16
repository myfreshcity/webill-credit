package com.webill.core.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.model.Product;
import com.webill.core.model.ProductLog;
import com.webill.core.service.IProductLogService;
import com.webill.core.service.mapper.ProductLogMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * ProductLog 服务层接口实现类
 *
 */
@Service
public class ProductLogServiceImpl extends SuperServiceImpl<ProductLogMapper, ProductLog> implements IProductLogService {

	@Override
	public Page<ProductLog> getList(Page<ProductLog> page, ProductLog pl) {
		List<ProductLog> pList = baseMapper.getList(page,pl);
		page.setRecords(pList);
		return page;
	}

	@Override
	public List<ProductLog> getListByProdId(Integer prodId) {
		List<ProductLog> list = baseMapper.getListByProdId(prodId);
		return list;
	}

}