package com.webill.core.service.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.webill.core.model.IllegalOrder;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * IllegalOrder 表数据库控制层接口
 *
 */
public interface IllegalOrderMapper extends AutoMapper<IllegalOrder> {

	/**   
	 * @Title: getIllegalOrderList   
	 * @Description: 根据status获取违章订单列表    
	 * @author: WangLongFei  
	 * @date: 2017年5月24日 下午1:48:12   
	 * @param page 
	 * 			 翻页对象，可以作为 xml 参数直接使用，传递参数 Page 即自动分页
	 * @param status
	 * 			状态
	 * @return  
	 * @return: List<IllegalOrder>  
	 */
	List<IllegalOrder> getIllegalOrderList(Pagination page, IllegalOrder iorder);
	
	/**   
	 * @Title: getOnlyIllegalOrderByMap   
	 * @Description: 根据条件获取违章订单  
	 * @author: WangLongFei  
	 * @date: 2017年6月30日 上午10:30:26   
	 * @param map
	 * @return  
	 * @return: List<IllegalOrder>  
	 */
	List<IllegalOrder> getOnlyIllegalOrderByMap(Map<String,Object> map);
	
	/**   
	 * @Title: getIllegalOrderInfo   
	 * @Description: 获取违章订单信息  
	 * @author: WangLongFei  
	 * @date: 2017年6月15日 下午3:58:14   
	 * @param map
	 * @return  
	 * @return: IllegalOrder  
	 */
	IllegalOrder getIllegalOrderInfo(Map<String,Object> map);
	
	/**   
	 * @Title: getWaitPushIllegalPay   
	 * @Description: 【模板推送】---违章缴费完成
	 * @author: WangLongFei  
	 * @date: 2017年9月25日 上午11:30:35   
	 * @param orderId
	 * @return  
	 * @return: IllegalOrder  
	 */
	IllegalOrder getWaitPushIllegalPay(String orderId);
}