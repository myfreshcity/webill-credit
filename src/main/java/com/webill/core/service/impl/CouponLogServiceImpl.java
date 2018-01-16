package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.CouponLog;
import com.webill.core.service.ICouponLogService;
import com.webill.core.service.mapper.CouponLogMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * CouponLog 服务层接口实现类
 *
 */
@Service
public class CouponLogServiceImpl extends SuperServiceImpl<CouponLogMapper, CouponLog> implements ICouponLogService {


}