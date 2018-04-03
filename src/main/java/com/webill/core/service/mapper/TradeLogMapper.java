package com.webill.core.service.mapper;

import java.util.List;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.webill.core.model.TradeLog;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * TradeLog 表数据库控制层接口
 *
 */
public interface TradeLogMapper extends AutoMapper<TradeLog> {

	/** 
	 * @Title: getTradeLogList 
	 * @Description: 获取用户购买记录
	 * @author ZhangYadong
	 * @date 2018年1月31日 下午5:00:25
	 * @param page
	 * @param tl
	 * @return
	 * @return List<TradeLog>
	 */
	List<TradeLog> getTradeLogList(Pagination page, TradeLog tl);
	
	/**  
	 * @Title: getRechargeList  
	 * @Description: 获取用户充值查询次数记录
	 * @author: ZhangYadong
	 * @date: 2018年3月22日
	 * @param page
	 * @param tl
	 * @return List<TradeLog>
	 */ 
	List<TradeLog> getRechargeList(Pagination page, TradeLog tl);
	
}