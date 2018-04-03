package com.webill.core.service.mapper;

import java.util.List;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.webill.core.model.Company;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * Company 表数据库控制层接口
 *
 */
public interface CompanyMapper extends AutoMapper<Company> {

	/** 
	 * @Title: getComList 
	 * @Description: 根据条件获取企业信息列表
	 * @author ZhangYadong
	 * @date 2018年1月17日 下午3:06:16
	 * @param page
	 * @param com
	 * @return
	 * @return List<Company>
	 */
	List<Company> getComList(Pagination page, Company com);
}