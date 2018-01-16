package com.webill.core.service.mapper;

import com.webill.core.model.CarCardApply;
import com.webill.framework.service.mapper.AutoMapper;

public interface CarCardApplyMapper extends AutoMapper<CarCardApply>{

		
	/**   
	 * @Title: save   
	 * @Description: 保存申请记录并返回记录  
	 * @author: WangLongFei  
	 * @date: 2017年9月14日 下午4:08:50   
	 * @param ccr
	 * @return  
	 * @return: CreditCardRecord  
	 */
	CarCardApply save(CarCardApply ccr);
}
