package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.TradeLog;
import com.webill.core.service.ITradeLogService;
import com.webill.core.service.mapper.TradeLogMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * TradeLog 服务层接口实现类
 *
 */
@Service
public class TradeLogServiceImpl extends SuperServiceImpl<TradeLogMapper, TradeLog> implements ITradeLogService {


}