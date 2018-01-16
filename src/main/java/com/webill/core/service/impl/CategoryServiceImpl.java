package com.webill.core.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.util.StringUtil;
import com.webill.core.model.Category;
import com.webill.core.service.ICategoryService;
import com.webill.core.service.mapper.CategoryMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * Category 服务层接口实现类
 *
 */
@Service
public class CategoryServiceImpl extends SuperServiceImpl<CategoryMapper, Category> implements ICategoryService {

	@Override
	public List<Category> getFirstCatList() {
		List<Category> list = baseMapper.getFirstCatList();
		return list;
	}
	
	@Override
	public List<Category> getSecondCatList(Integer id) {
		List<Category> list = baseMapper.getSecondCatList(id);
		return list;
	}
	
	@Override
	public List<Category> getChildCatList(Integer id) {
		List<Category> list = baseMapper.getSecondCatList(id);
		return list;
	}

	@Override
	public Integer getCatIdByName(String cateName) {
		return null;
	}
	
	@Override
	public Page<Category> getCategoryList(Page<Category> page, Category cate) {
		List<Category> cateList = baseMapper.getCategoryList(page, cate);
		/*for(PremiumOrder p:polist){
			p.setOrderStatus(this.getStatusStr(p.getStatus()));
		}*/
		page.setRecords(cateList);
		return page;
	}

	@Override
	public boolean addCat(Category cat) {
		if (cat.getParentId() != null) {
			cat.setLevel(2); //二级分类
		}else {
			cat.setParentId(0); //一级分类父分类Id为0
			cat.setLevel(1); //一级分类
		}
		if ("on".equals(cat.getTurnSwitch())) {
			cat.setIsDisplay(0); //是否显示：0-显示，1-不显示
		}else {
			cat.setIsDisplay(1);
		}
		boolean f = this.insertSelective(cat);
		return f;
	}
	
}