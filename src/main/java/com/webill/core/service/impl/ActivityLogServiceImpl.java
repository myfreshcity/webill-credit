package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.ActivityLog;
import com.webill.core.service.IActivityLogService;
import com.webill.core.service.mapper.ActivityLogMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * ActivityLog 服务层接口实现类
 *
 */
@Service
public class ActivityLogServiceImpl extends SuperServiceImpl<ActivityLogMapper, ActivityLog> implements IActivityLogService {


}