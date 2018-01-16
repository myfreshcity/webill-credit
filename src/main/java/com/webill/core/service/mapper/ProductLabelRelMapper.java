package com.webill.core.service.mapper;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.webill.core.model.ProductLabelRel;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * ProductLabelRel 表数据库控制层接口
 *
 */
public interface ProductLabelRelMapper extends AutoMapper<ProductLabelRel> {
	/** 
	 * @Title: getProdLabRelByProdId 
	 * @Description: 根据产品ID获取产品标签实体
	 * @author ZhangYadong
	 * @date 2017年12月28日 下午6:10:37
	 * @param prodId
	 * @return
	 * @return ProductLabelRel
	 */
	ProductLabelRel getProdLabRelByProdId(@Param("prodId") Integer prodId);
	/** 
	 * @Title: isRecommendByProdId 
	 * @Description: 根据产品ID判断是否是推荐商品
	 * @author ZhangYadong
	 * @date 2018年1月3日 上午11:11:39
	 * @param prodId
	 * @return
	 * @return ProductLabelRel
	 */
	public ProductLabelRel isRecommendByProdId(@Param("prodId") Integer prodId);
	/** 
	 * @Title: getBusinessLabelByProdId 
	 * @Description: 根据产品ID获取业务标签列表
	 * @author ZhangYadong
	 * @date 2018年1月3日 下午4:11:59
	 * @param prodId
	 * @return
	 * @return List<ProductLabelRel>
	 */
	public List<ProductLabelRel> getBusinessLabelByProdId(@Param("prodId") Integer prodId);
}