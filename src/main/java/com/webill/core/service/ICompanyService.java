package com.webill.core.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.model.Company;
import com.webill.framework.service.ISuperService;

/**
 *
 * Company 数据服务层接口
 *
 */
public interface ICompanyService extends ISuperService<Company> {

	/**  
	 * @Title: addComInfo  
	 * @Description: 添加企业信息
	 * @author: ZhangYadong
	 * @date: 2018年3月21日
	 * @param com
	 * @return boolean
	 */ 
	boolean addComInfo(Company com);

	/**  
	 * @Title: getComList  
	 * @Description: 根据条件获取企业信息列表
	 * @author: ZhangYadong
	 * @date: 2018年3月22日
	 * @param page
	 * @param com
	 * @return Page<Company>
	 */ 
	Page<Company> getComList(Page<Company> page, Company com);

}