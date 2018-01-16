package com.webill.core.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.webill.core.model.ProductLog;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * ProductLog 表数据库控制层接口
 *
 */
public interface ProductLogMapper extends AutoMapper<ProductLog> {
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
	List<ProductLog> getList(Pagination page,ProductLog p);
	/** 
	 * @Title: getListByProdId 
	 * @Description: 根据商品Id获取日志列表
	 * @author ZhangYadong
	 * @date 2017年12月25日 下午3:34:04
	 * @param prodId
	 * @return
	 * @return List<ProductLog>
	 */
	public List<ProductLog> getListByProdId(@Param(value = "prodId") Integer prodId);
}