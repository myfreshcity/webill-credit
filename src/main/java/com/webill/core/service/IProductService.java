package com.webill.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.model.PlanProduct;
import com.webill.core.model.Product;
import com.webill.core.model.ProductDetail;
import com.webill.core.model.ProductInsuredJob;
import com.webill.core.model.TrialResp;
import com.webill.framework.service.ISuperService;

/**
 *
 * Product 数据服务层接口
 *
 */
public interface IProductService extends ISuperService<Product> {
	
	/** 
	* @Title: getProdList 
	* @Description: 根据父分类的ID,获取产品列表
	* @author ZhangYadong
	* @date 2017年11月30日 下午4:00:20
	* @param id
	* @return
	* @return List<Product>
	*/
	public List<Product> getProdList(@Param(value = "id") String id);
	/** 
	* @Title: getListByCatMap 
	* @Description: 根据产品分类条件获取保险产品列表
	* @author ZhangYadong
	* @date 2017年12月4日 下午1:30:14
	* @param map
	* @return
	* @return List<Product>
	*/
	public List<Product> getListByCatMap(Map<String,Object> map);
	/** 
	 * @Title: getListByLabelMap 
	 * @Description: 根据标签条件获取保险产品列表
	 * @author ZhangYadong
	 * @date 2017年12月4日 下午1:30:14
	 * @param map
	 * @return
	 * @return List<Product>
	 */
	public List<Product> getListByLabelMap(Map<String,Object> map);
	/** 
	* @Title: saveProduct 
	* @Description: 添加保险商品
	* @author ZhangYadong
	* @date 2017年11月16日 上午10:19:22
	* @return
	* @return String
	*/
	boolean saveProduct(Product prod);
	/** 
	* @Title: getThirdProductList 
	* @Description: 获取保险产品列表
	* @author ZhangYadong
	* @date 2017年11月16日 下午1:50:55
	* @return
	* @return String
	*/
	String getThirdProductList();
	/** 
	* @Title: getProductDetail 
	* @Description: 根据caseCode方案代码获取产品详情
	* @author ZhangYadong
	* @date 2017年11月16日 上午10:19:19
	* @param caseCode
	* @return
	* @return String
	*/
	String getProductDetail(String caseCode);
	/** 
	* @Title: getDefaultTrial 
	* @Description: 根据caseCode方案代码获取产品默认试算
	* @author ZhangYadong
	* @date 2017年11月16日 上午10:19:14
	* @param caseCode
	* @return
	* @return String
	*/
	String getDefaultTrial(String caseCode);
	/** 
	* @Title: getBankAddress 
	* @Description: 获取银行地址
	* @author ZhangYadong
	* @date 2017年11月16日 下午5:02:23
	* @param caseCode
	* @return
	* @return String
	*/
	String bankAddress(String caseCode, String areaCode);
	/** 
	* @Title: propertyAddress 
	* @Description: 获取财产地址信息
	* @author ZhangYadong
	* @date 2017年12月6日 下午7:23:46
	* @param caseCode
	* @param areaCode
	* @return
	* @return String
	*/
	String propertyAddress(String caseCode, String areaCode);
	/** 
	* @Title: productInsuredArea 
	* @Description: 获取产品可投保省市地区
	* @author ZhangYadong
	* @date 2017年12月6日 下午7:23:52
	* @param caseCode
	* @param areaCode
	* @return
	* @return String
	*/
	String productInsuredArea(String caseCode, String areaCode);
	/** 
	* @Title: productInsuredJob 
	* @Description: 获取产品职业信息
	* @author ZhangYadong
	* @date 2017年12月6日 下午7:23:57
	* @param caseCode
	* @return
	* @return String
	*/
	String productInsuredJob(String caseCode);
	/** 
	* @Title: orderTrial 
	* @Description: 产品试算
	* @author ZhangYadong
	* @date 2017年11月16日 上午10:19:07
	* @param caseCode
	* @param startDate
	* @param newRestrictGenes
	* @param oldRestrictGene
	* @return
	* @return String
	*/
	String orderTrial(String caseCode, String startDate, String newRestrictGenes, String oldRestrictGene);
	/** 
	* @Title: getHealthStatement 
	* @Description: 获取健康告知
	* @author ZhangYadong
	* @date 2017年11月16日 上午10:19:03
	* @param caseCode
	* @param genes
	* @return
	* @return String
	*/
	String getHealthStatement(String caseCode, String genes);
	/** 
	* @Title: submitHealthState 
	* @Description: 提交健康告知
	* @author ZhangYadong
	* @date 2017年11月16日 上午10:18:58
	* @param caseCode
	* @param genes
	* @param qaAnswer
	* @return
	* @return String
	*/
	String submitHealthState(String caseCode, String genes, String qaAnswer);
	/** 
	* @Title: getProductInsuredAttr 
	* @Description: 获取产品投保属性
	* @author ZhangYadong
	* @date 2017年11月16日 下午6:12:18
	* @param caseCode
	* @return
	* @return String
	*/
	String getProductInsuredAttr(String caseCode);

	/** 
	 * @Title: getData 
	 * @Description: 惠泽数据请求处理
	 * @author: WangLongFei
	 * @date: 2017年11月30日 上午9:54:23 
	 * @param map
	 * @param reqUrl
	 * @return
	 * @return: String
	 */
	public String getData(Map<String, Object> map, String reqUrl);
	
	/** 
	 * @Title: getList 
	 * @Description: 根据条件获取订单列表
	 * @author: WangLongFei
	 * @date: 2017年12月18日 下午2:29:36 
	 * @param page
	 * @param p
	 * @return
	 * @return: Page<Product>
	 */
	Page<Product> getList(Page<Product> page,Product p);
	
	Map<String,Object> getNumByStatus();
	/** 
	* @Title: retProdDetail 
	* @Description: 根据Json数据返回产品详情
	* @author ZhangYadong
	* @date 2017年12月5日 上午9:53:13
	* @param json
	* @return
	* @return ProductDetail
	*/
	ProductDetail retProdDetail(String json);
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
	* @Title: planProduct 
	* @Description: 
	* @author ZhangYadong
	* @date 2017年12月6日 下午7:24:10
	* @param caseCode
	* @return
	* @return List<PlanProduct>
	*/
	List<PlanProduct> planProduct(String caseCode);
	/** 
	* @Title: addCityName 
	* @Description: 添加省市名称
	* @author ZhangYadong
	* @date 2017年12月7日 下午2:19:57
	* @param json
	* @return
	* @return String
	*/
	TrialResp addCityName(String json);
	List<Product> tpProdListMain();
	List<Product> tpProdList();
	List<ProductInsuredJob> retJobs(String json);
	Product parseProdDetail(String caseCode, String fristCategory, String secondCategory, String companyName, String planName);
	/** 
	 * @Title: getProdById 
	 * @Description: 根据ID获取产品信息
	 * @author ZhangYadong
	 * @date 2017年12月28日 上午10:20:54
	 * @param id
	 * @return
	 * @return Product
	 */
	public Product getProdById(Integer id);
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
	/** 
	 * @Title: getRecommendProdList 
	 * @Description: 获取为你推荐产品列表 
	 * @author ZhangYadong
	 * @date 2018年1月3日 上午9:51:23
	 * @return
	 * @return List<Product>
	 */
	List<Product> getRecommendProdList();
	/** 
	 * @Title: updateProduct 
	 * @Description: 修改商品信息
	 * @author ZhangYadong
	 * @date 2018年1月3日 下午5:36:22
	 * @param prod
	 * @return
	 * @return boolean
	 */
	boolean updateProduct(Product prod);
}