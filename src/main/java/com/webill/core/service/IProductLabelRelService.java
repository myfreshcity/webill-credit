package com.webill.core.service;

import java.util.List;

import com.webill.core.model.ProductLabelRel;
import com.webill.framework.service.ISuperService;

/**
 *
 * ProductLabelRel 数据服务层接口
 *
 */
public interface IProductLabelRelService extends ISuperService<ProductLabelRel> {

	/** 
	 * @Title: getProdLabRelByProdId 
	 * @Description: 根据产品ID获取产品标签实体
	 * @author ZhangYadong
	 * @date 2017年12月28日 下午6:10:37
	 * @param prodId
	 * @return
	 * @return ProductLabelRel
	 */
	ProductLabelRel getProdLabRelByProdId(Integer prodId);

	/** 
	 * @Title: addProdToRecomLabel 
	 * @Description: 添加商品到为你推荐
	 * @author ZhangYadong
	 * @date 2018年1月2日 下午6:27:18
	 * @param prodId
	 * @return
	 * @return boolean
	 */
	boolean addProdToRecomLabel(Integer prodId);

	/** 
	 * @Title: addProdToNavLabel 
	 * @Description: 添加商品到导航标签
	 * @author ZhangYadong
	 * @date 2018年1月2日 下午6:30:51
	 * @param prodId
	 * @param labelId
	 * @return
	 * @return boolean
	 */
	boolean addProdToNavLabel(Integer prodId, Integer labelId);

	/** 
	 * @Title: addProdToBusinessLabel 
	 * @Description: 添加商品到业务标签
	 * @author ZhangYadong
	 * @date 2018年1月2日 下午6:37:03
	 * @param prodId
	 * @param labelArray
	 * @return
	 * @return boolean
	 */
	boolean addProdToBusinessLabel(Integer prodId, String labelArray);
	/** 
	 * @Title: isRecommendByProdId 
	 * @Description: 根据产品ID判断是否是推荐商品
	 * @author ZhangYadong
	 * @date 2018年1月3日 上午11:14:18
	 * @param prodId
	 * @return
	 * @return ProductLabelRel
	 */
	ProductLabelRel isRecommendByProdId(Integer prodId);
	/** 
	 * @Title: getBusinessLabelByProdId 
	 * @Description: 根据产品ID获取业务标签列表
	 * @author ZhangYadong
	 * @date 2018年1月3日 下午4:13:25
	 * @param prodId
	 * @return
	 * @return List<ProductLabelRel>
	 */
	List<ProductLabelRel> getBusinessLabelByProdId(Integer prodId);
	/** 
	 * @Title: delBusinessLabelByProdId 
	 * @Description: 通过商品ID删除所对应的业务标签
	 * @author ZhangYadong
	 * @date 2018年1月4日 上午10:08:20
	 * @param prodId
	 * @return
	 * @return boolean
	 */
	boolean delBusinessLabelByProdId(Integer prodId);
}