package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.Illegal;
import com.webill.core.service.IIllegalService;
import com.webill.core.service.mapper.IllegalMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * Illegal 服务层接口实现类
 *
 */
@Service
public class IllegalServiceImpl extends SuperServiceImpl<IllegalMapper, Illegal> implements IIllegalService {


}