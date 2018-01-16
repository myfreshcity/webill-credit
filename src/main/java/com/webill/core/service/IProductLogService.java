package com.webill.core.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.model.ProductLog;
import com.webill.framework.service.ISuperService;

/**
 *
 * ProductLog 数据服务层接口
 *
 */
public interface IProductLogService extends ISuperService<ProductLog> {

	/** 
	 * @Title: getList 
	 * @Description: 根据条件获取商品日志列表
	 * @author ZhangYadong
	 * @date 2017年12月22日 上午11:58:56
	 * @param page
	 * @param pl
	 * @return
	 * @return Page<ProductLog>
	 */
	Page<ProductLog> getList(Page<ProductLog> page,ProductLog pl);
	
	/** 
	 * @Title: getListByProdId 
	 * @Description: 根据商品Id获取日志列表
	 * @author ZhangYadong
	 * @date 2017年12月25日 下午3:34:04
	 * @param prodId
	 * @return
	 * @return List<ProductLog>
	 */
	public List<ProductLog> getListByProdId(Integer prodId);
}