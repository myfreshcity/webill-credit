package com.webill.core.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.model.Coupon;
import com.webill.framework.service.ISuperService;

/**
 *
 * CouponNew 数据服务层接口
 *
 */
public interface ICouponService extends ISuperService<Coupon> {

	/**   
	 * @Title: getList   
	 * @Description: 获取优惠券列表————后台  
	 * @author: WangLongFei  
	 * @date: 2017年7月31日 下午5:35:22   
	 * @param c
	 * @return  
	 * @return: List<Coupon>  
	 */
	Page<Coupon> getList(Page<Coupon> page,Coupon c);
	
	/**   
	 * @Title: sendCoupon   
	 * @Description: 系统发放优惠券  
	 * @author: WangLongFei  
	 * @date: 2017年8月2日 下午1:37:47   
	 * @param couponId
	 * @param flag  
	 * @return: void  
	 */
	boolean addSendCoupon(Integer couponId,String sendTarget);
	
	/**   
	 * @Title: addCouponByCareFor   
	 * @Description: 关注微信公众号  
	 * @author: WangLongFei  
	 * @date: 2017年8月2日 下午6:13:12   
	 * @return  
	 * @return: boolean  
	 */
	boolean addCouponByCareFor();
	
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
	 * @date: 2017年8月4日 下午6:14:07   
	 * @param map
	 * @return  
	 * @return: List<Coupon>  
	 */
	List<Coupon> getSendCouponList(Map<String,Object> map);

}