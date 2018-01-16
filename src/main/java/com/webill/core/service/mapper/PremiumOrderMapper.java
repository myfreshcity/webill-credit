package com.webill.core.service.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.webill.core.model.PremiumOrder;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * PremiumOrder 表数据库控制层接口
 *
 */
public interface PremiumOrderMapper extends AutoMapper<PremiumOrder> {


	/**   
	 * @Title: getOnePoByMap   
	 * @Description: 根据条件获取一个premiu  
	 * @author: WangLongFei  
	 * @date: 2017年6月6日 下午4:44:44   
	 * @param map
	 * @return  
	 * @return: PremiumOrder  
	 */
	PremiumOrder getOnePoByMap(Map<String,Object> map);
	
	/**   
	 * @Title: getPremiumOrderList   
	 * @Description: 根据条件获取投保订单列表  
	 * @author: WangLongFei  
	 * @date: 2017年6月22日 上午9:57:02   
	 * @param po
	 * 			订单对象
	 * @return  
	 * @return: List<PremiumOrder>  
	 */
	List<PremiumOrder> getPremiumOrderList(Pagination page,PremiumOrder po);
	
	/**   
	 * @Title: getPremiumOrderById   
	 * @Description: 根据id获取订单详情  
	 * @author: WangLongFei  
	 * @date: 2017年7月3日 下午6:08:41   
	 * @param id
	 * @return  
	 * @return: PremiumOrder  
	 */
	PremiumOrder getPremiumOrderById(@Param(value = "id") String id);
	
	/**   
	 * @Title: getWaitPushOrderList   
	 * @Description: 获取待推送的公有订单列表  
	 * @author: WangLongFei  
	 * @date: 2017年9月20日 下午4:38:15   
	 * @param po
	 * @return  
	 * @return: List<PremiumOrder>  
	 */
	List<PremiumOrder> getWaitPushOrderList(PremiumOrder po);
	
	/**   
	 * @Title: getWaitPushOrderListAgain   
	 * @Description: 获取待推送的公有订单列表 (再次推送已经推送过的，间隔天数)
	 * @author: WangLongFei  
	 * @date: 2017年9月20日 下午4:38:15   
	 * @param po
	 * @return  
	 * @return: List<PremiumOrder>  
	 */
	List<PremiumOrder> getWaitPushOrderListAgain(PremiumOrder po);
	
	/**   
	 * @Title: getWaitPushPrmPrice   
	 * @Description: 获取待推送车险报价  
	 * @author: WangLongFei  
	 * @date: 2017年9月22日 上午11:10:55   
	 * @param po
	 * @return  
	 * @return: List<PremiumOrder>  
	 */
	List<PremiumOrder> getWaitPushPrmPrice(PremiumOrder po);
	
	/**   
	 * @Title: getWaitPushPrmIssue   
	 * @Description: 车险出单成功通知  
	 * @author: WangLongFei  
	 * @date: 2017年9月25日 上午9:38:27   
	 * @param id
	 * @return  
	 * @return: PremiumOrder  
	 */
	PremiumOrder getWaitPushPrmIssue(String id);
}