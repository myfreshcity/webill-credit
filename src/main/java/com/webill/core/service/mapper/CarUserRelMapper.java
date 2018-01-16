package com.webill.core.service.mapper;

import java.util.List;
import java.util.Map;

import com.webill.core.model.CarUserRel;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * CarUserRel 表数据库控制层接口
 *
 */
public interface CarUserRelMapper extends AutoMapper<CarUserRel> {

	/**   
	 * @Title: getCarListBy   
	 * @Description: 获取车辆列表  
	 * @author: WangLongFei  
	 * @date: 2017年7月6日 下午7:05:46   
	 * @param map
	 * @return  
	 * @return: List<CarUserRel>  
	 */
	List<CarUserRel> getCarListBy(Map<String,Object> map);
	
	/**   
	 * @Title: getCarUserRelByMap   
	 * @Description: 根据map获取车辆关系信息  
	 * @author: WangLongFei  
	 * @date: 2017年9月14日 下午1:21:10   
	 * @param map
	 * @return  
	 * @return: CarUserRel  
	 */
	CarUserRel getCarUserRelByMap(Map<String,Object> map);
}