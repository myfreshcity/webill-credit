package com.webill.core.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.model.Category;
import com.webill.framework.service.ISuperService;

/**
 *
 * Category 数据服务层接口
 *
 */
public interface ICategoryService extends ISuperService<Category> {

	/** 
	* @Title: getFirstCatList 
	* @Description: 获取一级分类列表
	* @author ZhangYadong
	* @date 2017年12月5日 下午8:19:41
	* @return
	* @return List<Category>
	*/
	public List<Category> getFirstCatList();
	/** 
	 * @Title: getFirstCatList 
	 * @Description: 根据一级分类ID获取二级分类列表
	 * @author ZhangYadong
	 * @date 2017年12月5日 下午8:19:41
	 * @return
	 * @return List<Category>
	 */
	public List<Category> getSecondCatList(Integer id);
	
	/** 
	* @Title: getChildCatList 
	* @Description: 获取子类分类列表
	* @author ZhangYadong
	* @date 2017年12月18日 下午5:36:19
	* @param id
	* @return
	* @return List<Category>
	*/
	public List<Category> getChildCatList(Integer id);
	
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
	* @date 2017年12月13日 下午3:10:37
	* @param page
	* @param cate
	* @return
	* @return Page<Category>
	*/
	Page<Category> getCategoryList(Page<Category> page, Category cate);
	/** 
	* @Title: addCat 
	* @Description: 添加分类
	* @author ZhangYadong
	* @date 2017年12月15日 下午2:45:51
	* @param cat
	* @return
	* @return boolean
	*/
	boolean addCat(Category cat);
}