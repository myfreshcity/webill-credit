package com.webill.core.service.mapper;

import java.util.List;
import java.util.Map;

import com.webill.core.model.Coupon;
import com.webill.framework.service.mapper.AutoMapper;

public interface CouponMapper extends AutoMapper<Coupon> {
	
	/**   
	 * @Title: getList   
	 * @Description: 获取优惠券列表————后台  
	 * @author: WangLongFei  
	 * @date: 2017年7月31日 下午5:35:22   
	 * @param c
	 * @return  
	 * @return: List<Coupon>  
	 */
	List<Coupon> getList(Coupon c);
	
	/**   
	 * @Title: getWaitingCouponList   
	 * @Description: 定时任务——获取待发放的优惠券  
	 * @author: WangLongFei  
	 * @date: 2017年8月3日 上午10:54:21   
	 * @param c
	 * @return  
	 * @return: List<Coupon>  
	 */
	List<Coupon> getWaitingCouponList(Coupon c);
	
	/**   
	 * @Title: getSendCouponList   
	 * @Description: 根据事件获取可以给用户发放的优惠券列表    
	 * @author: WangLongFei  
	 * @date: 2017年8月4日 下午6:32:32   
	 * @param map
	 * @return  
	 * @return: List<Coupon>  
	 */
	List<Coupon> getSendCouponList(Map<String,Object> map);
}