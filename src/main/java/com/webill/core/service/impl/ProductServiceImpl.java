package com.webill.core.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.SystemProperty;
import com.webill.app.util.CategoryEnum;
import com.webill.app.util.DateUtil;
import com.webill.app.util.HttpUtils;
import com.webill.app.util.MD5Util;
import com.webill.app.util.RegexUtil;
import com.webill.app.util.StringUtil;
import com.webill.app.util.TransNoUtil;
import com.webill.core.model.Faq;
import com.webill.core.model.PlanProduct;
import com.webill.core.model.Product;
import com.webill.core.model.ProductCategoryRel;
import com.webill.core.model.ProductDetail;
import com.webill.core.model.ProductFeature;
import com.webill.core.model.ProductInsuredJob;
import com.webill.core.model.ProductLabelRel;
import com.webill.core.model.ProductProtectItem;
import com.webill.core.model.ProductProvision;
import com.webill.core.model.ProtectItem;
import com.webill.core.model.RestrictDictionary;
import com.webill.core.model.RestrictGene;
import com.webill.core.model.TOrder;
import com.webill.core.model.TrialResp;
import com.webill.core.service.IProductCategoryRelService;
import com.webill.core.service.IProductLabelRelService;
import com.webill.core.service.IProductProtectItemService;
import com.webill.core.service.IProductService;
import com.webill.core.service.mapper.ProductMapper;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * Product 服务层接口实现类
 *
 */
@Service
public class ProductServiceImpl extends SuperServiceImpl<ProductMapper, Product> implements IProductService {
	private static Log logger = LogFactory.getLog(ProductServiceImpl.class);
	@Autowired
    protected SystemProperty constPro;
	@Autowired
	private IProductCategoryRelService productCategoryRelService;
	@Autowired
	private IProductLabelRelService productLabelRelService;
	@Autowired
	private IProductProtectItemService productProtectItemService;
	
	@Transactional
	@Override
	public boolean saveProduct(Product prod) {
		boolean f = false;
		if ("on".equals(prod.getTurnSwitch())) {
			prod.setOffShelf(0);//是否上架：0-是 1-否
		}else {
			prod.setOffShelf(1);
		}
		if (StringUtil.isEmpty(prod.getImgUrlShow())) {
			prod.setImgUrlShow(prod.getImgUrlSrc());
		}
		prod.setThirdPUrl("惠泽");
		f = this.insertSelective(prod);
		//添加保障项目
		if (f) {
			List<ProductProtectItem> ppiList = JSONUtil.toObjectList(prod.getProtectItems(), ProductProtectItem.class);
			for (ProductProtectItem ppi : ppiList) {
				ppi.setProdId(prod.getId());
				ppi.setTStatus(0);
				ppi.setCreatedTime(new Date());
			}
			productProtectItemService.insertBatch(ppiList);
		}
		//添加分类信息
		if (f) {
			if (prod.getSecondCatId() != null) {
				ProductCategoryRel rcr = new ProductCategoryRel();
				rcr.setProdId(prod.getId());
				rcr.setCatId(prod.getSecondCatId());
				f = productCategoryRelService.insertSelective(rcr);
			}
		}
		//添加标签信息
		if (f) {
			//添加商品到导航标签
			Integer labelId = prod.getLabelId();
			if (labelId != null) {
				f = productLabelRelService.addProdToNavLabel(prod.getId(), labelId);
			}
			//添加商品到为你推荐
			if ("0".equals(prod.getRecommend())) {
				f = productLabelRelService.addProdToRecomLabel(prod.getId());
			}
			//添加业务标签
			String la = prod.getLabelArray();
			if (StringUtil.isNotEmpty(la)) {
				f = productLabelRelService.addProdToBusinessLabel(prod.getId(), la);
			}
		}
		return f;
	}
	
	@Override
	public List<Product> tpProdListMain() {
		String prods = this.getThirdProductList();
		logger.info("所有产品列表："+prods);
		String proData = JSON.parseObject(JSON.parseObject(prods).getString("data")).getString("products");
		List<Product> prodList = JSONUtil.toObjectList(proData, Product.class);
		List<Product> pList = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			Product prod = prodList.get(i);
			String resPdet = this.getProductDetail(prod.getCaseCode());
			logger.info("产品详情："+resPdet);
			if (resPdet == null) {
				continue;
			}
			String pdetData = JSON.parseObject(resPdet).getString("data");
			JSONObject resjp = JSONObject.parseObject(pdetData);
			Product pd = new Product();
			pd.setCaseCode(prod.getCaseCode());
			pd.setProdName(prod.getProdName());
			pd.setPlanName(prod.getPlanName());
			pd.setCompanyName(prod.getCompanyName());
			pd.setCompanyLogoUrl(resjp.getString("mobileLogo"));
			pd.setOffShelf(prod.getOffShelf());
			pd.setFristCategory(prod.getFristCategory());
			pd.setSecondCategory(prod.getSecondCategory());
			if (resjp.getString("fullDescription") != null) {
				String img = RegexUtil.matchImg(resjp.getString("fullDescription"));
				pd.setImgUrlSrc(img);
				pd.setImgUrlShow(img);
			}
			pd.setDefaultPrice(Long.parseLong(resjp.getString("price"))/100);
			pd.setPremiumTable(resjp.getString("premiumTable"));
			pd.setThirdPUrl("惠泽");
			String restrictGenes = JSONObject.parseObject(pdetData).getString("restrictGenes");
			pd.setMinInsurDate(RegexUtil.getMinInsurDate(restrictGenes));
			pd.setMaxInsurDate(RegexUtil.getMaxInsurDate(restrictGenes));
			pd.setInsurDateLimit(RegexUtil.insurantDateLimit(restrictGenes));
			pList.add(pd);
		}
		return pList;
	}
	
	@Override
	public List<Product> tpProdList() {
		String prods = this.getThirdProductList();
		logger.info("所有产品列表："+prods);
		String proData = JSON.parseObject(JSON.parseObject(prods).getString("data")).getString("products");
		List<Product> prodList = JSONUtil.toObjectList(proData, Product.class);
		for (Product prod : prodList) {
			prod.setFristCatName(CategoryEnum.getValue(prod.getFristCategory()));
			prod.setSecondCatName(CategoryEnum.getValue(prod.getSecondCategory()));
			prod.setThirdPUrl("惠泽");
		}
		return prodList;
	}
	
	@Override
	public Product parseProdDetail(String caseCode, String fristCategory, String secondCategory, String companyName, String planName) {
		
		String pdtail = this.getProductDetail(caseCode);
		logger.info("产品详情："+pdtail);
		if (StringUtil.isNotEmpty(pdtail)) {
			String pdetData = JSON.parseObject(pdtail).getString("data");
			JSONObject resjp = JSONObject.parseObject(pdetData);
			Product pd = new Product();
			pd.setCaseCode(caseCode);
			pd.setProdName(resjp.getString("productName"));
			pd.setPlanName(planName);
			pd.setCompanyName(companyName);
			pd.setCompanyLogoUrl(resjp.getString("mobileLogo"));
			pd.setFristCategory(Integer.parseInt(fristCategory));
			pd.setSecondCategory(Integer.parseInt(secondCategory));
			if (resjp.getString("fullDescription") != null) {
				List<String> imgs = RegexUtil.getImgSrc(resjp.getString("fullDescription"));
				pd.setImgUrlShow(imgs.get(0));
				pd.setImgUrlSrc(imgs.get(0));
			}
			if (resjp.getString("productRead") != null) {
				String imgs = RegexUtil.retImgSrc(resjp.getString("productRead"));
				logger.info(imgs);
				pd.setProdRead(imgs);
			}
			pd.setDefaultPrice(Long.valueOf(resjp.getString("price")));
			pd.setPremiumTable(resjp.getString("premiumTable"));
			pd.setThirdPUrl("惠泽");
			String restrictGenes = JSONObject.parseObject(pdetData).getString("restrictGenes");
			pd.setMinInsurDate(RegexUtil.insurantDate(restrictGenes));
			pd.setInsurDateLimit(RegexUtil.insurantDateLimit(restrictGenes));
			if (resjp.getString("protectItems") != null) {
				String protectItems = resjp.getString("protectItems");
				List<ProtectItem> piList = JSONUtil.toObjectList(protectItems, ProtectItem.class);
				String piStr = JSONUtil.toJSONString(piList);
				pd.setProtectItems(piStr);
			}
			return pd;
		}
		return null;
	}

	@Override
	public String getThirdProductList() {
		Map< String, Object> reqMap = new LinkedHashMap<>();
		reqMap.put("transNo", TransNoUtil.genTransNo());
		reqMap.put("partnerId", constPro.HUIZE_PARTNERID);
		String resJson = getData(reqMap, "/border/productList");
		return resJson;
	}
	
	@Override
	public String getProductDetail(String caseCode) {
		Map< String, Object> reqMap = new LinkedHashMap<>();
		reqMap.put("transNo", TransNoUtil.genTransNo());
		reqMap.put("caseCode", caseCode);
		reqMap.put("partnerId", constPro.HUIZE_PARTNERID);
		reqMap.put("platformType", 1); //平台标识 0：PC 1：H5
		String resJson = getData(reqMap, "/border/productDetail");
		logger.info(resJson);
		return resJson;
	}
	
	@Override
	public String getDefaultTrial(String caseCode) {
		Map< String, Object> reqMap = new LinkedHashMap<>();
		reqMap.put("transNo", TransNoUtil.genTransNo());
		reqMap.put("partnerId", constPro.HUIZE_PARTNERID);
		reqMap.put("caseCode", caseCode);
		String resJson = getData(reqMap, "/api/defaultTrial");
		return resJson;
	}
	
	@Override
	public String bankAddress(String caseCode, String areaCode) {
		Map< String, Object> reqMap = new LinkedHashMap<>();
		reqMap.put("transNo", TransNoUtil.genTransNo());
		reqMap.put("partnerId", constPro.HUIZE_PARTNERID);
		reqMap.put("caseCode", caseCode);
		if (StringUtil.isNotEmpty(areaCode)) {
			reqMap.put("areaCode", areaCode);
		}
		String resJson = getData(reqMap, "/border/bankAddress");
		return resJson;
	}
	@Override
	public String propertyAddress(String caseCode, String areaCode) {
		Map< String, Object> reqMap = new LinkedHashMap<>();
		reqMap.put("transNo", TransNoUtil.genTransNo());
		reqMap.put("partnerId", constPro.HUIZE_PARTNERID);
		reqMap.put("caseCode", caseCode);
		if (StringUtil.isNotEmpty(areaCode)) {
			reqMap.put("areaCode", areaCode);
		}
		String resJson = getData(reqMap, "/border/propertyAddress");
		return resJson;
	}
	@Override
	public String productInsuredArea(String caseCode, String areaCode) {
		Map< String, Object> reqMap = new LinkedHashMap<>();
		reqMap.put("transNo", TransNoUtil.genTransNo());
		reqMap.put("partnerId", constPro.HUIZE_PARTNERID);
		reqMap.put("caseCode", caseCode);
		if (StringUtil.isNotEmpty(areaCode)) {
			reqMap.put("areaCode", areaCode);
		}
		String resJson = getData(reqMap, "/border/productInsuredArea");
		return resJson;
	}
	
	@Override
	public String productInsuredJob(String caseCode) {
		Map< String, Object> reqMap = new LinkedHashMap<>();
		reqMap.put("transNo", TransNoUtil.genTransNo());
		reqMap.put("partnerId", constPro.HUIZE_PARTNERID);
		reqMap.put("caseCode", caseCode);
		String resJson = getData(reqMap, "/border/productInsuredJob");
		return resJson;
	}
	
	@Override
	public List<ProductInsuredJob> retJobs(String json) {
		String jobStr= JSONObject.parseObject(JSONObject.parseObject(json).getString("data")).getString("jobs");
		List<ProductInsuredJob> obList = JSONUtil.toObjectList(jobStr, ProductInsuredJob.class);
		
		List<ProductInsuredJob> pi1 = (List<ProductInsuredJob>) JSONPath.eval(obList, "[parentId=0]");
		List<ProductInsuredJob> rList = recursive(pi1, obList);
		logger.info("职业："+JSONUtil.toJSONString(rList));
		return rList;
	}

	private static List<ProductInsuredJob> recursive(List<ProductInsuredJob> jobs, List<ProductInsuredJob> obList){
		if (jobs != null && jobs.size() > 0) {
			for (ProductInsuredJob job : jobs) {
				List<ProductInsuredJob> piList = (List<ProductInsuredJob>)JSONPath.eval(obList, "[parentId="+job.getId()+"]");
				job.setJobs(piList);
				recursive(piList, obList);
			}
		}
		return jobs;
	}
	
	@Override
	public String orderTrial(String caseCode, String startDate, String newRestrictGenes, String oldRestrictGene) {
		JSONArray jsonArray = (JSONArray) JSON.parse(newRestrictGenes);
		Object jObject = JSON.parse(oldRestrictGene);
		Map< String, Object> reqMap = new LinkedHashMap<>();
		reqMap.put("transNo", TransNoUtil.genTransNo());
		reqMap.put("partnerId", constPro.HUIZE_PARTNERID);
		reqMap.put("caseCode", caseCode);
		if (StringUtil.isEmpty(startDate)) {
			startDate = DateUtil.getTomorDate();
		}
		reqMap.put("startDate", startDate);
		reqMap.put("newRestrictGenes", jsonArray);
		reqMap.put("oldRestrictGene", jObject);
		String resJson = getData(reqMap, "/api/orderTrial");
		return resJson;
	}
	
	@Override
	public String getHealthStatement(String caseCode, String genes) {
		JSONArray jsonArray = (JSONArray) JSON.parse(genes);
		Map< String, Object> reqMap = new LinkedHashMap<>();
		reqMap.put("transNo", TransNoUtil.genTransNo());
		reqMap.put("partnerId", constPro.HUIZE_PARTNERID);
		reqMap.put("caseCode", caseCode);
		reqMap.put("genes", jsonArray);
		String resJson = getData(reqMap, "/border/healthStatement");
		return resJson;
	}
	
	@Override
	public String submitHealthState(String caseCode, String genes, String qaAnswer) {
		JSONArray jsonArray = (JSONArray) JSON.parse(genes);
		Object jObject = JSON.parse(qaAnswer);
		Map< String, Object> reqMap = new LinkedHashMap<>();
		reqMap.put("transNo", TransNoUtil.genTransNo());
		reqMap.put("partnerId", constPro.HUIZE_PARTNERID);
		reqMap.put("caseCode", caseCode);
		reqMap.put("genes", jsonArray);
		reqMap.put("qaAnswer", jObject);
		String resJson = getData(reqMap, "/border/submitHealthState");
		return resJson;
	}
	
	@Override
	public String getProductInsuredAttr(String caseCode) {
		Map< String, Object> reqMap = new LinkedHashMap<>();
		reqMap.put("transNo", TransNoUtil.genTransNo());
		reqMap.put("partnerId", constPro.HUIZE_PARTNERID);
		reqMap.put("caseCode", caseCode);
		String resJson = getData(reqMap, "/border/productInsuredAttr");
		return resJson;
	}
	
	@Override
	public String getData(Map<String, Object> map, String reqUrl) {
		String reqJson = JSONUtils.toJSONString(map);
		String reqSign = MD5Util.MD5Encode(constPro.HUIZE_TEST_KEY + reqJson, "UTF-8");
		String resJson = HttpUtils.httpPostJsonRequest(constPro.HUIZE_TEST_REQUEST_URL + reqUrl+ "?sign=" + reqSign, reqJson);
		logger.info("请求参数：=====>"+reqJson);
		logger.info("响应结果：====>"+resJson);
		if (resJson != null && "0".equals(JSON.parseObject(resJson).getString("respCode"))) {
			return resJson;
		}
		logger.info("响应数据："+resJson);
		return null;
	}

	@Override
	public Page<Product> getList(Page<Product> page,Product p) {
		List<Product> pList = baseMapper.getList(page,p);
		for (Product prod : pList) {
			if (StringUtil.isNotEmpty(prod.getImgUrlShow())) {
				prod.setImgUrlShow(constPro.PRODUCT_FILE_ACCESS_PATH + prod.getImgUrlShow());
			}
		}
		page.setRecords(pList);
		return page;
	}

	@Override
	public Map<String, Object> getNumByStatus() {
		return baseMapper.getNumByStatus();
	}
	public List<Product> getProdList(String id) {
		List<Product> prodList = baseMapper.getProdList(id);
		return prodList;
	}

	@Override
	public List<Product> getRecommendProdList() {
		List<Product> prodList = baseMapper.getRecommendProdList();
		return prodList;
	}
	
	@Override
	public List<Product> getListByCatMap(Map<String,Object> map) {
		List<Product> prodList = baseMapper.getListByCatMap(map);
		return prodList;
	}
	
	@Override
	public List<Product> getListByLabelMap(Map<String,Object> map) {
		List<Product> prodList = baseMapper.getListByLabelMap(map);
		if(prodList!=null){
			for (Product prod : prodList) {
				if (StringUtil.isNotEmpty(prod.getImgUrlShow())) {
					prod.setImgUrlShow(constPro.PRODUCT_FILE_ACCESS_PATH + prod.getImgUrlShow());
				}
			}
		}
		return prodList;
	}
	
	@Override
	public ProductDetail retProdDetail(String json){
		JSONObject pdOb = JSON.parseObject(json);
		ProductDetail pd = new ProductDetail();
		JSONObject pdData = JSON.parseObject(pdOb.getString("data"));
		pd.setProductName(pdData.getString("productName"));
		pd.setPrice(Long.parseLong(pdData.getString("price")));
		pd.setMobileLogo(pdData.getString("mobileLogo"));

		String attachmentUrl = null; //主条款pdf链接
		if (pdData.getString("planName") != null) {
			pd.setPlanName(pdData.getString("planName"));
		}
		if (pdData.getString("protectItems") != null) {
			List<ProtectItem> piList = JSONUtil.toObjectList(pdData.getString("protectItems"), ProtectItem.class);
			//去掉保障项目重复的保障项
			for (int i = 0; i < piList.size()-1; i++) {
	            for (int j = piList.size()-1; j > i; j--) {
	                if (piList.get(j).getName().equals(piList.get(i).getName())) {
	                	piList.remove(j);
	                }
	            }
	        }
			pd.setProtectItems(piList);
		}
		if (pdData.getString("productRead") != null) {
			pd.setProductRead(RegexUtil.getImgSrc(pdData.getString("productRead")));
		}
		if (pdData.getString("features") != null) {
			String features = RegexUtil.delTextTag(pdData.getString("features"));
			pd.setFeatures(JSONUtil.toObjectList(features, ProductFeature.class));
		}
		if (pdData.getString("fullDescription") != null) {
			pd.setFullDescription(RegexUtil.getImgSrc(pdData.getString("fullDescription")));
		}
		if (pdData.getString("tips") != null) {
			pd.setTips(JSONUtil.toObjectList(pdData.getString("tips"), String.class));
		}
		if (pdData.getString("masterProvisions") != null) {
			pd.setMasterProvisions(JSONUtil.toObjectList(pdData.getString("masterProvisions"), ProductProvision.class));
			JSONArray ma = JSONArray.parseArray(pdData.getString("masterProvisions"));
			JSONObject mo = (JSONObject) ma.get(0);
			attachmentUrl = mo.getString("attachmentUrl");
		}
		//投保声明
		if (pdData.getString("insureDeclare") != null) {
			if (attachmentUrl != null) {
				String insureDeclare = RegexUtil.parseInsureDeclare(pdData.getString("insureDeclare"), attachmentUrl);
				pd.setInsureDeclare(insureDeclare);
			}
		}
		if (pdData.getString("additionalProvisions") != null) {
			pd.setAdditionalProvisions(JSONUtil.toObjectList(pdData.getString("additionalProvisions"), ProductProvision.class));
		}
		if (pdData.getString("premiumTable") != null) {
			pd.setPremiumTable(pdData.getString("premiumTable"));
		}
		if (pdData.getString("claimTypes") != null) {
			pd.setClaimTypes(JSONUtil.toObjectList(pdData.getString("claimTypes"), ProductProvision.class));
		}
		if (pdData.getString("pictureNotify") != null) {
			pd.setPictureNotify(pdData.getString("pictureNotify"));
		}
		if (pdData.getString("samplePolicy") != null) {
			pd.setSamplePolicy(pdData.getString("samplePolicy"));
		}
		String restrictGenes = pdData.getString("restrictGenes");
		if (restrictGenes != null) {
			pd.setInsuAge(RegexUtil.insurantDate(restrictGenes));
			pd.setInsuDateLimit(RegexUtil.insurantDateLimit(restrictGenes));
			List<RestrictGene> rlist = RegexUtil.parseRestrictGenes(restrictGenes);
			pd.setRestrictGenes(rlist);
		}
		if (pdData.getString("faqs") != null) {
			pd.setFaqs(JSONUtil.toObjectList(pdData.getString("faqs"), Faq.class));
		}
		return pd;
	}
	@Override
	public Product getProdByCaseCode(String caseCode){
		Product prod = baseMapper.getProdByCaseCode(caseCode);
		return prod;
	}
	
	@Override
	public List<Product> getPlanListByCaseCode(String caseCode) {
		List<Product> list = baseMapper.getPlanListByCaseCode(caseCode);
		return list;
	}

	@Override
	public List<PlanProduct> planProduct(String caseCode) {
		List<Product> pList = this.getPlanListByCaseCode(caseCode);
		List<PlanProduct> plan = new LinkedList<>();
		if (pList != null && pList.size() > 0) {
			for (int i = 0; i < pList.size(); i++) {
				PlanProduct pp = new PlanProduct();
				// 1-默认计划	0-其他计划
				pp.setDefaultPlan(pList.get(i).getCaseCode().equals(caseCode) ? "1" : "0");
				pp.setCaseCode(pList.get(i).getCaseCode());
				if (pList.size() == 1) {
					pList.get(0).setPlanName("基础版");
				}
				if (pList.size() == 2) {
					pList.get(0).setPlanName("基础版");
					pList.get(1).setPlanName("精选版");
				}
				if (pList.size() == 3) {
					pList.get(0).setPlanName("基础版");
					pList.get(1).setPlanName("精选版");
					pList.get(2).setPlanName("尊享版");
				}
				pp.setPlanName(pList.get(i).getPlanName());
				plan.add(pp);
			}
		}
		return plan;
	}

	@Override
	public TrialResp addCityName(String json) {
		TrialResp trialResp = null;
		trialResp = JSONUtil.toObject(JSONObject.parseObject(json).getString("data"), TrialResp.class);
		List<RestrictGene> reList = trialResp.getRestrictGenes();
		String cityName = null, proName = null;
		for (RestrictGene re : reList) {
			if ("city".equals(re.getKey())) {
				// 获取市级名称
				String defCityVa = re.getDefaultValue();
				List<RestrictDictionary> cvaList = re.getValues();
				for (RestrictDictionary crd : cvaList) {
					if (defCityVa.equals(crd.getValue())) {
						cityName = crd.getName();
					}
				}
				//获取省级名称
				List<RestrictGene> srList = re.getSubRestrictGenes();
				for (RestrictGene rg : srList) {
					String defProVa = rg.getDefaultValue();
					if ("province".equals(rg.getKey())) {
						List<RestrictDictionary> pvalues = rg.getValues();
						for (RestrictDictionary prd : pvalues) {
							if (defProVa.equals(prd.getValue())) {
								proName = prd.getName();
							}
						}
					}
				}
			}
			re.setProvinceName(proName);
			re.setCityName(cityName);
		}
		return trialResp;
	}
	
	@Override
	public Product getProdById(Integer id){
		Product prod = baseMapper.getProdById(id);
		return prod;
	}
	
	@Override
	public boolean updateProduct(Product prod){
		if (prod.getImgUrlShow() != null && prod.getImgUrlShow().indexOf(constPro.PRODUCT_FILE_ACCESS_PATH) != -1) {
			prod.setImgUrlShow(null);
		};
		if (prod.getRecommendUrl() != null && prod.getRecommendUrl().indexOf(constPro.PRODUCT_FILE_ACCESS_PATH) != -1) {
			prod.setRecommendUrl(null);
		};
		boolean f = this.updateSelectiveById(prod);
		
		//修改推荐商品
		ProductLabelRel r = productLabelRelService.isRecommendByProdId(prod.getId());
		// 为你推荐：0-是，1-否
		if ("0".equals(prod.getRecommend()) && r == null) {
			productLabelRelService.addProdToRecomLabel(prod.getId());
		}else if("1".equals(prod.getRecommend()) && r != null){
			productLabelRelService.deleteById(r.getId());
		}
		
		//修改产品分类
		if (prod.getSecondCatId() != null) {
			ProductCategoryRel pcr = productCategoryRelService.getProdCatRelByProdId(prod.getId());
			pcr.setCatId(prod.getSecondCatId());
			f = productCategoryRelService.updateSelectiveById(pcr);
		}
		//修改导航标签
		if (prod.getLabelId() != null) {
			ProductLabelRel plr = productLabelRelService.getProdLabRelByProdId(prod.getId());
			plr.setLabelId(prod.getLabelId());
			f = productLabelRelService.updateSelectiveById(plr);
		}
		//修改业务标签
		//1:先删除产品业务标签
		productLabelRelService.delBusinessLabelByProdId(prod.getId());
		//2:再添加产品业务标签
		String la = prod.getLabelArray();
		if (StringUtil.isNotEmpty(la)) {
			f = productLabelRelService.addProdToBusinessLabel(prod.getId(), la);
		}
		return f;
	}
}