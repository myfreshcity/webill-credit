package com.webill.core.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.model.Customer;
import com.webill.core.model.dianhuabang.DHBGetLoginReq;
import com.webill.core.model.juxinli.JXLSubmitFormReq;
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

	/**
	 * @Title: getCusByBasicInfo
	 * @Description: 根据客户基本信息获取客户详情
	 * @author ZhangYadong
	 * @date 2018年1月21日 下午2:45:21
	 * @param cus
	 * @return
	 * @return Customer
	 */
	Customer getCusByBasicInfo(Customer cus);

	/**
	 * @Title: updateCus
	 * @Description: 完善联系信息
	 * @author ZhangYadong
	 * @date 2018年1月21日 下午4:20:49
	 * @param cus
	 * @return
	 * @return boolean
	 */
	boolean updateCus(Customer cus);

	/**
	 * @Title: getCusByUserIdCusId
	 * @Description: 根据用户ID客户ID获取客户信息
	 * @author ZhangYadong
	 * @date 2018年1月21日 下午4:35:11
	 * @param cus
	 * @return
	 * @return Customer
	 */
	Customer getCusByUserIdCusId(Customer cus);

	/**
	 * @Title: cusToJXLSubmitFormReq
	 * @Description: 客户信息转聚信立表单提交数据
	 * @author ZhangYadong
	 * @date 2018年1月23日 下午5:10:30
	 * @param cus
	 * @return
	 * @return JXLSubmitFormReq
	 */
	JXLSubmitFormReq cusToJXLSubmitFormReq(Customer cus);

	/**
	 * @Title: addSelectTimes
	 * @Description: 返回客户信息中添加用户查询次数信息
	 * @author ZhangYadong
	 * @date 2018年1月26日 下午3:42:52
	 * @param cus
	 * @return
	 * @return Customer
	 */
	Customer addSelectTimes(Customer cus);

	/**
	 * @Title: cusToDHBGetLoginReq
	 * @Description: 客户信息转电话邦获取登录方式数据
	 * @author ZhangYadong
	 * @date 2018年1月31日 下午7:26:49
	 * @param cus
	 * @return
	 * @return DHBGetLoginReq
	 */
	DHBGetLoginReq cusToDHBGetLoginReq(Customer cus);

	/** 
	 * @Title: getJXLSubmitFormReq 
	 * @Description: 获取聚信立表单提交数据
	 * @author ZhangYadong
	 * @date 2018年2月6日 下午3:37:18
	 * @param cusId
	 * @return
	 * @return JXLSubmitFormReq
	 */
	JXLSubmitFormReq getJXLSubmitFormReq(Integer cusId);
}