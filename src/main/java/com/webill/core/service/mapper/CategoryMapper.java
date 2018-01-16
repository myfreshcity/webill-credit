package com.webill.core.service.mapper;

import java.util.List;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.webill.core.model.Category;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * Category 表数据库控制层接口
 *
 */
public interface CategoryMapper extends AutoMapper<Category> {

	/** 
	* @Title: getFirstCatList 
	* @Description: 获取一级分类列表
	* @author ZhangYadong
	* @date 2017年12月5日 下午8:20:04
	* @return
	* @return List<Category>
	*/
	public List<Category> getFirstCatList();
	/** 
	* @Title: getFirstCatList 
	* @Description: 根据一级分类ID获取二级分类列表
	* @author ZhangYadong
	* @date 2017年12月5日 下午8:20:04
	* @return
	* @return List<Category>
	*/
	public List<Category> getSecondCatList(Integer id);
	
	/** 
	* @Title: getCatIdByName 
	* @Description: 根据分类名称获取分类ID
	* @author ZhangYadong
	* @date 2017年12月12日 下午5:58:32
	* @param cateName
	* @return
	* @return Integer
	*/
	public Integer getCatIdByName(String cateName);
	
	/** 
	* @Title: getCategoryList 
	* @Description: 根据条件获取商品分类列表 
	* @author ZhangYadong
	* @date 2017年12月13日 下午3:10:49
	* @param page
	* @param cate
	* @return
	* @return List<Category>
	*/
	List<Category> getCategoryList(Pagination page,Category cate);
}