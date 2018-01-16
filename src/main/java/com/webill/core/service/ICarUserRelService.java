package com.webill.core.service;

import com.webill.core.ResponseInfo;
import com.webill.core.model.CarUserRel;
import com.webill.framework.service.ISuperService;

import java.util.List;
import java.util.Map;

/**
 * CarUserRel 数据服务层接口
 */
public interface ICarUserRelService extends ISuperService<CarUserRel> {

    public List<CarUserRel> selectCarList(String userId);

    /**   
     * @Title: addCarUserRel   
     * @Description: 添加人车关系（包括：车辆存在和不存在的情况）  
     * @author: WangLongFei  
     * @date: 2017年10月24日 下午12:58:21   
     * @param rel
     * @return  
     * @return: String  
     */
    public String addCarUserRel(CarUserRel rel);
    
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
	
	/**   
	 * @Title: addRelOnly   
	 * @Description: 只添加人车关系  
	 * @author: WangLongFei  
	 * @date: 2017年10月24日 下午12:58:17   
	 * @param rel
	 * @return  
	 * @return: ResponseInfo  
	 */
	public ResponseInfo addRelOnly(CarUserRel rel);
}