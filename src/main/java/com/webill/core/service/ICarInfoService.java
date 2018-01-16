package com.webill.core.service;

import java.util.List;
import java.util.Map;

import com.webill.core.model.CarInfo;
import com.webill.core.model.CarUserRel;
import com.webill.framework.common.JsonResult;
import com.webill.framework.service.ISuperService;

/**
 *
 * CarInfo 数据服务层接口
 *
 */
public interface ICarInfoService extends ISuperService<CarInfo> {
	/**   
	 * @Title: checkCar   
	 * @Description: 验证车牌号是否存在  
	 * @author: WangLongFei  
	 * @date: 2017年7月8日 下午5:31:19   
	 * @param licenseNo
	 * @return  
	 * @return: CarInfo  
	 */
	CarInfo checkCar(String licenseNo);
	
	/**   
	 * @Title: addCarByOcr   
	 * @Description: OCR扫描添加车辆  
	 * @author: WangLongFei  
	 * @date: 2017年7月8日 下午6:00:21   
	 * @param ci
	 * @param cur
	 * @return  
	 * @return: boolean  
	 */
	boolean addCarByOcr(CarInfo ci,CarUserRel cur);
	
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
	
	/**   
	 * @Title: addCarToUser   
	 * @Description: 给用户添加车辆  
	 * @author: WangLongFei  
	 * @date: 2017年9月6日 上午9:56:21   
	 * @param ci
	 * @return  
	 * @return: boolean  
	 */
	boolean addCarToUser(CarInfo ci);
	
	/**   
	 * @Title: addCarInfoOnly   
	 * @Description: 添加车辆
	 * @author: WangLongFei  
	 * @date: 2017年10月23日 下午2:13:17   
	 * @param ci
	 * @return  
	 * @return: CarInfo  
	 */
	CarInfo addCarInfoOnly(CarInfo ci);
	
}