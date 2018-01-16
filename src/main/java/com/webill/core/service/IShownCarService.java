package com.webill.core.service;

import java.util.List;

import com.webill.core.model.CarInfo;
import com.webill.core.model.ShownCar;
import com.webill.framework.service.ISuperService;

public interface IShownCarService extends ISuperService<ShownCar>{

	/**   
	 * @Title: getList   
	 * @Description: 根据唯一id获取所有展示车辆信息  
	 * @author: WangLongFei  
	 * @date: 2017年9月8日 下午3:28:22   
	 * @param id
	 * @return  
	 * @return: List<ShownCar>  
	 */
	List<CarInfo> getList(String id);
}
