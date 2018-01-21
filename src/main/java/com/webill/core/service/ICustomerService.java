package com.webill.core.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.model.Customer;
import com.webill.framework.service.ISuperService;

/**
 *
 * Customer 数据服务层接口
 *
 */
public interface ICustomerService extends ISuperService<Customer> {

	/** 
	 * @Title: getCusList 
	 * @Description: 根据条件获取客户列表
	 * @author ZhangYadong
	 * @date 2018年1月17日 下午3:07:46
	 * @param page
	 * @param cus
	 * @return
	 * @return Page<Customer>
	 */
	Page<Customer> getCusList(Page<Customer> page, Customer cus);

}