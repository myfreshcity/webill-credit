package com.webill.core.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.ResponseInfo;
import com.webill.core.model.HzNotify;
import com.webill.core.model.InsureReq;
import com.webill.core.model.NotifyData;
import com.webill.core.model.PremiumOrder;
import com.webill.core.model.TOrder;
import com.webill.framework.common.HzJsonResult;
import com.webill.framework.service.ISuperService;

/**
 *
 * TOrder 数据服务层接口
 *
 */
public interface ITOrderService extends ISuperService<TOrder> {

	/** 
	 * @Title: saveDoInsure 
	 * @Description: 下单即承保
	 * @author: WangLongFei
	 * @date: 2017年12月1日 下午5:18:37 
	 * @param ir
	 * @param po
	 * @return
	 * @return: ResponseInfo
	 */
	Object saveDoInsure(InsureReq ir,TOrder po); 

	/** 
	 * @Title: onlinePay 
	 * @Description: 支付
	 * @author: WangLongFei
	 * @date: 2017年11月30日 上午10:44:44 
	 * @param map
	 * @return
	 * @return: String
	 */
	String onlinePay(Map<String,Object> map);
	
	/** 
	 * @Title: updatePayNotify 
	 * @Description: 支付回调
	 * @author: WangLongFei
	 * @date: 2017年11月30日 上午10:44:44 
	 * @param map
	 * @return
	 * @return: ResponseInfo
	 */
	ResponseInfo updateByNotify(HzNotify hn) throws IOException;
	
	/** 
	 * @Title: downloadUrl 
	 * @Description: 下载保单
	 * @author: WangLongFei
	 * @date: 2017年11月30日 上午11:22:21 
	 * @param map
	 * @return
	 * @return: String
	 */
	String downloadUrl(Map<String,Object> map);
	
	/** 
	 * @Title: orderDetail 
	 * @Description: 保单查询
	 * @author: WangLongFei
	 * @date: 2017年11月30日 上午11:23:40 
	 * @param map
	 * @return
	 * @return: String
	 */
	String orderDetail(Map<String,Object> map);
	
	/** 
	 * @Title: surrenderPolicy 
	 * @Description: 退保接口
	 * @author: WangLongFei
	 * @date: 2017年11月30日 上午11:23:40 
	 * @param map
	 * @return
	 * @return: String
	 */
	String surrenderPolicy(Map<String,Object> map);
	
	/** 
	 * @Title: getData 
	 * @Description: 请求慧择并返回信息
	 * @author: WangLongFei
	 * @date: 2017年12月6日 下午6:07:34 
	 * @param map
	 * @param reqUrl
	 * @return
	 * @return: String
	 */
	String getData(Map<String, Object> map, String reqUrl);
	
	/** 
	 * @Title: getList 
	 * @Description: 根据条件获取订单列表
	 * @author: WangLongFei
	 * @date: 2017年12月10日 下午4:56:05 
	 * @param page
	 * @param po
	 * @return
	 * @return: Page<TOrder>
	 */
	Page<TOrder> getList(Page<TOrder> page, TOrder po);
	
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
	
	/** 
	 * @Title: getOrderList 
	 * @Description: 用户id
	 * @author: WangLongFei
	 * @date: 2018年1月4日 上午11:53:25 
	 * @param userId
	 * @return
	 * @return: Object
	 */
	Object getOrderList(String userId);
	
	/** 
	 * @Title: getOrderDetail 
	 * @Description: 获取用户订单详情
	 * @author: WangLongFei
	 * @date: 2018年1月4日 上午11:58:05 
	 * @param id
	 * @return
	 * @return: TOrder
	 */
	TOrder getOrderDetail(String id);
}