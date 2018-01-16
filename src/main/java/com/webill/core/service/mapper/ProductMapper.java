package com.webill.core.service.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.springframework.data.repository.query.Param;

import com.webill.core.model.Product;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * Product 表数据库控制层接口
 *
 */
public interface ProductMapper extends AutoMapper<Product> {

	/** 
	 * @Title: getList 
	 * @Description: 根据条件获取订单列表
	 * @author: WangLongFei
	 * @date: 2017年12月18日 下午2:26:09 
	 * @param p
	 * @return
	 * @return: List<Product>
	 */
	List<Product> getList(Pagination page,Product p);

	/** 
	 * @Title: getNumByStatus 
	 * @Description: 根据状态获取各分组数量
	 * @author: WangLongFei
	 * @date: 2017年12月19日 下午5:10:50 
	 * @return
	 * @return: Map<String,Object>
	 */
	Map<String, Object> getNumByStatus();
   /**
	* @Title: getProdList 
	* @Description: 根据父分类的ID,获取产品类别
	* @author ZhangYadong
	* @date 2017年11月30日 下午4:00:20
	* @param id
	* @return
	* @return List<Product>
	*/
	public List<Product> getProdList(@Param(value = "id") String id);
	/** 
	 * @Title: getRecommendProdList 
	 * @Description: 获取为你推荐产品列表
	 * @author ZhangYadong
	 * @date 2018年1月3日 上午9:40:37
	 * @return
	 * @return List<Product>
	 */
	public List<Product> getRecommendProdList();
	/** 
	* @Title: getListByCatMap 
	* @Description: 根据产品分类条件获取保险产品列表
	* @author ZhangYadong
	* @date 2017年12月4日 下午1:29:50
	* @param map
	* @return
	* @return List<Product>
	*/
	public List<Product> getListByCatMap(Map<String,Object> map);
	/** 
	* @Title: getListByLabelMap 
	* @Description: 根据标签条件获取保险产品列表
	* @author ZhangYadong
	* @date 2017年12月5日 下午2:04:39
	* @param map
	* @return
	* @return List<Product>
	*/
	public List<Product> getListByLabelMap(Map<String,Object> map);
	/** 
	* @Title: getPlanListByCaseCode 
	* @Description: 根据方案代码获取产品相关计划的产品列表
	* @author ZhangYadong
	* @date 2017年12月5日 下午3:09:39
	* @param caseCode
	* @return
	* @return List<Product>
	*/
	List<Product> getPlanListByCaseCode(String caseCode);
	
	/** 
	 * @Title: getProdById 
	 * @Description: 根据ID获取产品信息
	 * @author ZhangYadong
	 * @date 2017年12月28日 上午10:20:54
	 * @param id
	 * @return
	 * @return Product
	 */
	public Product getProdById(@Param("id") Integer id);
	/** 
	 * @Title: getProdByCaseCode 
	 * @Description: 根据方案代码获取产品信息
	 * @author ZhangYadong
	 * @date 2017年12月28日 上午11:12:06
	 * @param caseCode
	 * @return
	 * @return Product
	 */
	public Product getProdByCaseCode(@Param("caseCode") String caseCode);
	
}