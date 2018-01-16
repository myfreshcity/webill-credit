package com.webill.core.service;

import java.util.List;
import java.util.Map;

import com.webill.core.model.UserCoupon;
import com.webill.framework.service.ISuperService;

/**
 *
 * UserCoupon 数据服务层接口
 *
 */
public interface IUserCouponService extends ISuperService<UserCoupon> {

	/**   
	 * @Title: getUsableCouponList   
	 * @Description: 获取可用优惠券列表
	 * @author: WangLongFei  
	 * @date: 2017年7月27日 上午11:11:32   
	 * @param orderNo
	 * @return  
	 * @return: List<UserCoupon>  
	 */
	List<UserCoupon> getUsableCouponList(String orderNo);
	
	/**   
	 * @Title: getCouponListByOrderNo   
	 * @Description: 获取优惠券列表（根据map条件区分可用，和不可用）  
	 * @author: WangLongFei  
	 * @date: 2017年7月27日 下午2:58:58   
	 * @param map
	 * @return  
	 * @return: List<UserCoupon>  
	 */
	List<UserCoupon> getCouponListBy(Map<String,Object> map);
	
	/**   
	 * @Title: getUserCouponList   
	 * @Description: 获取用户所有优惠券列表  
	 * @author: WangLongFei  
	 * @date: 2017年8月1日 上午10:52:58   
	 * @param map
	 * @return  
	 * @return: List<UserCoupon>  
	 */
	List<UserCoupon> getUserCouponList(Map<String,Object> map);
	
	/**   
	 * @Title: getPeoPleNum   
	 * @Description: 根据优惠券id,获取优惠券发放人数  
	 * @author: WangLongFei  
	 * @date: 2017年8月1日 下午12:53:37   
	 * @param map
	 * @return  
	 * @return: Integer  
	 */
	Integer getPeoPleNum(Map<String,Object> map );
	
	/**   
	 * @Title: addUserCouponByEvent   
	 * @Description: 根据返回事项发放优惠券  
	 * @author: WangLongFei  
	 * @date: 2017年8月4日 下午6:40:13   
	 * @param map
	 * @return  
	 * @return: boolean  
	 */
	boolean addUserCouponByEvent(Map<String, Object> map);
}