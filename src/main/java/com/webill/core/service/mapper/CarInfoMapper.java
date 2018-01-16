package com.webill.core.service.mapper;

import java.util.List;
import java.util.Map;

import com.webill.core.model.CarInfo;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * CarInfo 表数据库控制层接口
 *
 */
public interface CarInfoMapper extends AutoMapper<CarInfo> {


	/**   
	 * @Title: getCarInfoListByTime   
	 * @Description: 获取一个月内投保即将到期的车辆信息  
	 * @author: WangLongFei  
	 * @date: 2017年6月8日 下午2:08:24   
	 * @return  
	 * @return: List<CarInfo>  
	 */
	List<CarInfo> getCarInfoListByTime();
	
	/**   
	 * @Title: getDuringCarList   
	 * @Description: 根据保期天数之内的车辆列表  
	 * @author: WangLongFei  
	 * @date: 2017年8月2日 下午3:32:45   
	 * @param map
	 * @return  
	 * @return: List<CarInfo>  
	 */
	List<CarInfo> getDuringCarList(Map<String,Object> map);
	
	
	/**   
	 * @Title: getCarListByUserId   
	 * @Description: 根据用户id获取车辆列表  
	 * @author: WangLongFei  
	 * @date: 2017年8月28日 上午10:25:06   
	 * @param userId
	 * @return  
	 * @return: List<CarInfo>  
	 */
	List<CarInfo> getCarListByUserId(String userId);
	
	/**   
	 * @Title: getCarInfoByUser   
	 * @Description: 获取用户下某辆车的详细信息  
	 * @author: WangLongFei  
	 * @date: 2017年9月5日 下午3:02:45   
	 * @param map
	 * @return  
	 * @return: CarInfo  
	 */
	CarInfo getCarInfoByUser(Map<String,Object> map);
}