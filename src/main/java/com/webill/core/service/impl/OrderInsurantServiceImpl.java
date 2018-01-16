package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.OrderInsurant;
import com.webill.core.service.IOrderInsurantService;
import com.webill.core.service.mapper.OrderInsurantMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * OrderInsurant 服务层接口实现类
 *
 */
@Service
public class OrderInsurantServiceImpl extends SuperServiceImpl<OrderInsurantMapper, OrderInsurant> implements IOrderInsurantService {


}