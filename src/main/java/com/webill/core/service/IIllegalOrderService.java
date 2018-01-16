package com.webill.core.service;

import java.util.List;
import java.util.Map;


import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.ResponseInfo;
import com.webill.core.model.CarInfo;
import com.webill.core.model.IllegalOrder;
import com.webill.core.model.WechatTemplateMsg;
import com.webill.framework.service.ISuperService;

/**
 *
 * IllegalOrder 数据服务层接口
 *
 */
public interface IIllegalOrderService extends ISuperService<IllegalOrder> {

	/**   
	 * @Title: getIllegalOrderList   
	 * @Description: 根据status获取违章订单列表  
	 * @author: WangLongFei  
	 * @date: 2017年5月20日 下午1:26:29   
	 * @return  
	 * @return: List<IllegalOrder>  
	 */
	Page<IllegalOrder> getIllegalOrderList(Page<IllegalOrder> page, IllegalOrder iorder);
	
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
	 * @Title: saveConfirmPay   
	 * @Description: 确认代缴：全部代缴完成才更改订单状态为---已缴费：2，其它全部为---缴费中：1  
	 * @author: WangLongFei  
	 * @date: 2017年6月19日 下午4:43:18   
	 * @param car
	 * @return  
	 * @return: ResponseInfo  
	 */
	ResponseInfo saveConfirmPay(CarInfo car);
	
	/**   
	 * @Title: getOnlyIllegalOrderByMap   
	 * @Description: 根据条件获取违章订单  
	 * @author: WangLongFei  
	 * @date: 2017年6月30日 上午10:29:07   
	 * @param map
	 * @return  
	 * @return: List<IllegalOrder>  
	 */
	List<IllegalOrder> getOnlyIllegalOrderByMap(Map<String,Object> map);
	
	/**   
	 * @Title: savePaySuccess   
	 * @Description: 用户支付成功  
	 * @author: WangLongFei  
	 * @date: 2017年6月28日 下午1:51:27   
	 * @param id
	 * @return  
	 * @return: ResponseInfo  
	 */
	ResponseInfo savePaySuccess(String id);
	
	/**   
	 * @Title: getWaitPushIllegalPay   
	 * @Description: 违章缴费完成通知  
	 * @author: WangLongFei  
	 * @date: 2017年9月25日 上午11:07:37   
	 * @param orderId
	 * @return  
	 * @return: WechatTemplateMsg  
	 */
	WechatTemplateMsg getWaitPushIllegalPay(String orderId);
}