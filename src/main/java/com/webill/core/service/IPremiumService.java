package com.webill.core.service;

import java.util.List;
import java.util.Map;

import com.webill.core.model.Premium;
import com.webill.framework.service.ISuperService;

/**
 *
 * Premium 数据服务层接口
 *
 */
public interface IPremiumService extends ISuperService<Premium> {
	/**   
	 * @Title: selectPremiumListByMap   
	 * @Description: 根据map查询保险列表 ,排出掉已选过的险种
	 * @author: WangLongFei  
	 * @date: 2017年7月3日 下午4:02:29   
	 * @param map
	 * @return  
	 * @return: int  
	 */
	List<Premium> selectPremiumListByMap(Map<String,Object> map);

}