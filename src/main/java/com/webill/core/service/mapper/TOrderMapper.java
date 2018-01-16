package com.webill.core.service.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.webill.core.model.TOrder;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * TOrder 表数据库控制层接口
 *
 */
public interface TOrderMapper extends AutoMapper<TOrder> {

	/** 
	 * @Title: getList 
	 * @Description: 根据条件获取订单列表
	 * @author: WangLongFei
	 * @date: 2017年12月10日 下午4:54:57 
	 * @param po
	 * @return
	 * @return: List<TOrder>
	 */
	List<TOrder> getList(Pagination page,TOrder po);

	/** 
	 * @Title: getNumByStatus 
	 * @Description: 根据状态分组获取数量
	 * @author: WangLongFei
	 * @date: 2017年12月15日 下午1:57:01 
	 * @return
	 * @return: Map<String,Object>
	 */
	Map<String,Object> getNumByStatus();
	
	/** 
	 * @Title: geOrderList 
	 * @Description: 用户获取订单列表
	 * @author: WangLongFei
	 * @date: 2017年12月20日 下午6:26:34 
	 * @param po
	 * @return
	 * @return: List<TOrder>
	 */
	List<TOrder> getOrderList(TOrder po);
}