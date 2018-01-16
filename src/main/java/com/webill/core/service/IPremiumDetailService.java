package com.webill.core.service;

import java.util.List;
import java.util.Map;

import com.webill.core.model.PremiumDetail;
import com.webill.framework.service.ISuperService;

/**
 *
 * PremiumDetail 数据服务层接口
 *
 */
public interface IPremiumDetailService extends ISuperService<PremiumDetail> {

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
	
	/**   
	 * @Title: getFullDetail   
	 * @Description: 处理投保方案（主要对不计免赔处理，一条数据转成两条）  
	 * @author: WangLongFei  
	 * @date: 2017年9月12日 上午11:15:16   
	 * @param oList
	 * @return  
	 * @return: List<PremiumDetail>  
	 */
	List<PremiumDetail> getFullDetail(List<PremiumDetail> oList);
}