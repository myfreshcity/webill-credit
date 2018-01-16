package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.Activity;
import com.webill.core.service.IActivityService;
import com.webill.core.service.mapper.ActivityMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * Activity 服务层接口实现类
 *
 */
@Service
public class ActivityServiceImpl extends SuperServiceImpl<ActivityMapper, Activity> implements IActivityService {


}