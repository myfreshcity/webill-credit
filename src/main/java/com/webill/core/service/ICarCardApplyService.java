package com.webill.core.service;

import com.webill.core.model.CarCardApply;
import com.webill.framework.service.ISuperService;

/**   
 * @ClassName: ICarCardApplyService   
 * @Description: CarCardApply 数据服务层接口  
 * @author: WangLongFei  
 * @date: 2017年9月15日 上午10:08:38      
 */
public interface ICarCardApplyService extends ISuperService<CarCardApply> {
	
	CarCardApply save(CarCardApply ccr);
}
