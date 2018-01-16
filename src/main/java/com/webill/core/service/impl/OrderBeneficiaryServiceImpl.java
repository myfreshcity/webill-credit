package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.OrderBeneficiary;
import com.webill.core.service.IOrderBeneficiaryService;
import com.webill.core.service.mapper.OrderBeneficiaryMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * OrderBeneficiary 服务层接口实现类
 *
 */
@Service
public class OrderBeneficiaryServiceImpl extends SuperServiceImpl<OrderBeneficiaryMapper, OrderBeneficiary> implements IOrderBeneficiaryService {


}