package com.webill.core.service;

import java.util.List;

import com.webill.core.ResponseInfo;
import com.webill.core.model.IllegalDetail;
import com.webill.core.model.IllegalOrder;
import com.webill.framework.service.ISuperService;

/**
 *
 * IllegalDetail 数据服务层接口
 *
 */
public interface IIllegalDetailService extends ISuperService<IllegalDetail> {

	/**   
	 * @Title: saveIIllegalDetails   
	 * @Description: 遍历保存多条违章记录 ,和订单  
	 * @author: WangLongFei  
	 * @date: 2017年5月19日 上午9:17:05   
	 * @param illd
	 * @param illo
	 * @return  
	 * @return: ResponseInfo  
	 */
	ResponseInfo saveIIllegalDetails(List<IllegalDetail> details, IllegalOrder order);
}