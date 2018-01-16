package com.webill.core.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.webill.core.model.Label;
import com.webill.core.service.ILabelService;
import com.webill.core.service.mapper.LabelMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * Label 服务层接口实现类
 *
 */
@Service
public class LabelServiceImpl extends SuperServiceImpl<LabelMapper, Label> implements ILabelService {

	@Override
	public List<Label> getNavLabel() {
		List<Label> nls = baseMapper.getNavLabel();
		return nls;
	}

	@Override
	public List<Label> labelListByGroupId(Integer id) {
		List<Label> labels = baseMapper.labelListByGroupId(id);
		return labels;
	}

}