package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.Company;
import com.webill.core.service.ICompanyService;
import com.webill.core.service.mapper.CompanyMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * Company 服务层接口实现类
 *
 */
@Service
public class CompanyServiceImpl extends SuperServiceImpl<CompanyMapper, Company> implements ICompanyService {


}