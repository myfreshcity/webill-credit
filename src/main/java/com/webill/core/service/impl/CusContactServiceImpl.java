package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.CusContact;
import com.webill.core.service.ICusContactService;
import com.webill.core.service.mapper.CusContactMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * CusContact 服务层接口实现类
 *
 */
@Service
public class CusContactServiceImpl extends SuperServiceImpl<CusContactMapper, CusContact> implements ICusContactService {


}