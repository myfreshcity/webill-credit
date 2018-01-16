package com.webill.core.service.mapper;

import java.util.List;
import java.util.Map;

import com.webill.core.model.Premium;
import com.webill.framework.service.mapper.AutoMapper;

public interface PremiumMapper extends AutoMapper<Premium>{
	/**   
	 * @Title: selectPremiumListByMap   
	 * @Description: 根据map查询保险列表  
	 * @author: WangLongFei  
	 * @date: 2017年7月3日 下午4:02:29   
	 * @param map
	 * @return  
	 * @return: int  
	 */
	List<Premium> selectPremiumListByMap(Map<String,Object> map);
	
}