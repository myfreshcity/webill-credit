package com.webill.core.service;

import com.webill.core.model.CusContact;
import com.webill.framework.service.ISuperService;

/**
 *
 * CusContact 数据服务层接口
 *
 */
public interface ICusContactService extends ISuperService<CusContact> {

	/** 
	 * @Title: delCusContact 
	 * @Description: 批量删除客户的联系人
	 * @author ZhangYadong
	 * @date 2018年1月21日 下午4:12:46
	 * @param cusId
	 * @return
	 * @return boolean
	 */
	boolean delCusContact(Integer cusId);
}