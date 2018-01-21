package com.webill.core.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.model.Customer;
import com.webill.core.service.ICustomerService;
import com.webill.core.service.mapper.CustomerMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * Customer 服务层接口实现类
 *
 */
@Service
public class CustomerServiceImpl extends SuperServiceImpl<CustomerMapper, Customer> implements ICustomerService {

	@Override
	public Page<Customer> getCusList(Page<Customer> page, Customer cus){
		List<Customer> cusList = baseMapper.getCusList(page, cus);
		page.setRecords(cusList);
		return page;
	}
}