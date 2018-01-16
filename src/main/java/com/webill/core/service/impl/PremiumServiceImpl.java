package com.webill.core.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.webill.core.model.Premium;
import com.webill.core.service.IPremiumService;
import com.webill.core.service.mapper.PremiumMapper;
import com.webill.framework.service.impl.SuperServiceImpl;


@Service
public class PremiumServiceImpl extends SuperServiceImpl<PremiumMapper, Premium> implements IPremiumService {

	@Override
	public List<Premium> selectPremiumListByMap(Map<String, Object> map) {
		List<Premium> pmList = baseMapper.selectPremiumListByMap(map);
		return pmList;
	}


}