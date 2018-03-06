package com.webill.core.service;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.model.TradeLog;
import com.webill.core.model.lianlianEntity.CertPayWebReq;
import com.webill.framework.service.ISuperService;

/**
 *
 * TradeLog 数据服务层接口
 *
 */
public interface ITradeLogService extends ISuperService<TradeLog> {

	/** 
	 * @Title: certPayRequest 
	 * @Description: 标准支付：认证支付请求数据
	 * @author ZhangYadong
	 * @date 2018年1月30日 下午7:50:54
	 * @param req
	 * @param userId
	 * @param infoGoodsId
	 * @return
	 * @return CertPayWebReq
	 */
	CertPayWebReq certPayRequest(HttpServletRequest req, Integer userId, Integer infoGoodsId);
	
	/** 
	* @Title: emailOrderPayParam 
	* @Description: 订单支付发送邮件
	* @author ZhangYadong
	* @date 2017年11月28日 下午1:33:21
	* @param dt_order
	* @param money_order
	* @param orderNo
	* @return void
	*/
	void emailOrderPayParam(String orderNo, String orderDt, String orderMoney);

	/** 
	 * @Title: getTradeLogList 
	 * @Description: 获取用户购买记录
	 * @author ZhangYadong
	 * @date 2018年1月31日 下午5:13:40
	 * @param page
	 * @param tl
	 * @return
	 * @return Page<TradeLog>
	 */
	Page<TradeLog> getTradeLogList(Page<TradeLog> page, TradeLog tl);
	
	/** 
	 * @Title: updateTradeStatus 
	 * @Description: 更新交易流水状态
	 * @author ZhangYadong
	 * @date 2018年1月31日 下午5:13:40
	 * @param transNo
	 * @param reqStr
	 * @return
	 */
	boolean updateTradeStatus(String transNo, String reqStr);

	/** 
	 * @Title: addUserTimes 
	 * @Description: 添加用户次数
	 * @author ZhangYadong
	 * @date 2018年1月31日 下午5:13:40
	 * @param transNo
	 * @return
	 */
	boolean addUserTimes(String transNo);
}