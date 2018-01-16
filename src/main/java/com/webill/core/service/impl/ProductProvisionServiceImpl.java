package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.ProductProvision;
import com.webill.core.service.IProductProvisionService;
import com.webill.core.service.mapper.ProductProvisionMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * ProductProvision 服务层接口实现类
 *
 */
@Service
public class ProductProvisionServiceImpl extends SuperServiceImpl<ProductProvisionMapper, ProductProvision> implements IProductProvisionService {


}