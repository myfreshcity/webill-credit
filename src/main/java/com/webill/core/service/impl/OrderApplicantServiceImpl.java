package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.OrderApplicant;
import com.webill.core.service.IOrderApplicantService;
import com.webill.core.service.mapper.OrderApplicantMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * OrderApplicant 服务层接口实现类
 *
 */
@Service
public class OrderApplicantServiceImpl extends SuperServiceImpl<OrderApplicantMapper, OrderApplicant> implements IOrderApplicantService {


}