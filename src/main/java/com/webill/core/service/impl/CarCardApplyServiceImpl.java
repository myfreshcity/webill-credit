package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.CarCardApply;
import com.webill.core.service.ICarCardApplyService;
import com.webill.core.service.mapper.CarCardApplyMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

@Service
public class CarCardApplyServiceImpl extends SuperServiceImpl<CarCardApplyMapper, CarCardApply> implements ICarCardApplyService{

	@Override
	public CarCardApply save(CarCardApply cca) {
		boolean f = this.insertSelective(cca);
		if(f){
			return cca; 
		}else{
			return null;
		}
	}

}
