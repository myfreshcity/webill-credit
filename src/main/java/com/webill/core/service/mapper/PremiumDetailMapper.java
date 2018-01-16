package com.webill.core.service.mapper;

import java.util.List;
import java.util.Map;

import com.webill.core.model.PremiumDetail;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * PremiumDetail 表数据库控制层接口
 *
 */
public interface PremiumDetailMapper extends AutoMapper<PremiumDetail> {


	/**   
	 * @Title: selectOrderBy   
	 * @Description: 获取订单详情，并排序  
	 * @author: WangLongFei  
	 * @date: 2017年8月30日 下午2:43:30   
	 * @param map
	 * @return  
	 * @return: List<PremiumDetail>  
	 */
	List<PremiumDetail> selectOrderBy(Map<String,Object> map);
}