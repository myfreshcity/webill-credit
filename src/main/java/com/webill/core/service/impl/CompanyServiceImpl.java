package com.webill.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.util.AreaChildrenUtil;
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
	@Autowired
	private AreaChildrenUtil areaChildrenUtil;
	
	@Override
	public boolean addComInfo(Company com){
		com.setComAddr(areaChildrenUtil.getLable(com.getComAddrCode()));
		com.setStatus(1); // 申请状态：1-待审核 2-审核通过 3-审核拒绝
		boolean f = this.insertSelective(com);
		return f;
	}
	
	@Override
	public Page<Company> getComList(Page<Company> page, Company com){
		List<Company> comList = baseMapper.getComList(page, com);
		for (Company comp : comList) {
			if (comp.getReviewTime() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				comp.setReviewTimeStr(sdf.format(comp.getReviewTime()));
			}
		}
		page.setRecords(comList);
		return page;
	}

}