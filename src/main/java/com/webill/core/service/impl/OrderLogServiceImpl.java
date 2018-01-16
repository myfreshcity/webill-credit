package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.OrderLog;
import com.webill.core.service.IOrderLogService;
import com.webill.core.service.mapper.OrderLogMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * OrderLog 服务层接口实现类
 *
 */
@Service
public class OrderLogServiceImpl extends SuperServiceImpl<OrderLogMapper, OrderLog> implements IOrderLogService {


}