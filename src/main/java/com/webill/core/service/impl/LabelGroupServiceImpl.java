package com.webill.core.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.webill.core.model.LabelGroup;
import com.webill.core.service.ILabelGroupService;
import com.webill.core.service.mapper.LabelGroupMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * LabelGroup 服务层接口实现类
 *
 */
@Service
public class LabelGroupServiceImpl extends SuperServiceImpl<LabelGroupMapper, LabelGroup> implements ILabelGroupService {

	@Override
	public List<LabelGroup> labelGroupList() {
		List<LabelGroup> list = baseMapper.labelGroupList();
		return list;
	}
}