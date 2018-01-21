package com.webill.core.service.mapper;

import java.util.List;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.webill.core.model.Customer;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * Customer 表数据库控制层接口
 *
 */
public interface CustomerMapper extends AutoMapper<Customer> {
	
	/** 
	 * @Title: getCusList 
	 * @Description: 根据条件获取客户列表
	 * @author ZhangYadong
	 * @date 2018年1月17日 下午3:06:16
	 * @param page
	 * @param cus
	 * @return
	 * @return List<Customer>
	 */
	List<Customer> getCusList(Pagination page, Customer cus);

}