package com.webill.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webill.app.SystemProperty;
import com.webill.app.util.RegexUtil;
import com.webill.app.util.StringUtil;
import com.webill.core.model.Category;
import com.webill.core.model.PlanProduct;
import com.webill.core.model.Product;
import com.webill.core.model.ProductDetail;
import com.webill.core.model.ProductInsuredJob;
import com.webill.core.model.RestrictGene;
import com.webill.core.model.TrialResp;
import com.webill.core.service.ICategoryService;
import com.webill.core.service.IProductService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/** 
* @ClassName: ProductApiController 
* @Description:
* @author ZhangYadong
* @date 2017年11月15日 下午2:32:37 
*/
@Api(value = "productApi", description = "保险产品相关API", produces = MediaType.APPLICATION_JSON_VALUE )
@Controller
@RequestMapping("/api/product")
public class ProductApiController extends BaseController{
	
	@Autowired
    private IProductService productService;
	@Autowired
	private ICategoryService categoryService;
	
    @Autowired
    protected SystemProperty constPro;
	
	
	@ApiOperation(value = "获取所有保险产品列表")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取保险产品列表成功！"),@ApiResponse(code = 500, message = "获取保险产品列表失败！")})
	@RequestMapping(value = "/getAllProdList", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object getAllProdList() {
		List<Category> clist = categoryService.getFirstCatList();
		JSONArray ja = new JSONArray();
		for (Category cate : clist) {
			JSONObject obj = new JSONObject();  
			obj.put("id", cate.getId());  
			obj.put("catName", cate.getCatName());
			List<Product> prodList = productService.getProdList(cate.getId().toString());
			for (Product prod : prodList) {
				if (StringUtil.isNotEmpty(prod.getImgUrlShow())) {
					prod.setImgUrlShow(constPro.PRODUCT_FILE_ACCESS_PATH + prod.getImgUrlShow());
				}
			}
			obj.put("productList", prodList);  
			ja.add(obj);  
		}
		if(!ja.isEmpty()){
			return renderSuccess(ja);
		}else{
			return renderError("获取保险产品列表失败！", "500");
		}
	}
	
	@ApiOperation(value = "根据标签ID获取保险产品列表")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取保险产品列表成功！"),@ApiResponse(code = 500, message = "获取保险产品列表失败！")})
	@RequestMapping(value = "/prodListByLabelId/{labelId}", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object prodListByLabelId(@ApiParam(required = true, value="标签ID-labelId") @PathVariable String labelId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("labelId", labelId);
		List<Product> resJson = productService.getListByLabelMap(map);
		if(resJson != null){
			return renderSuccess(resJson);
		}else{
			return renderError("获取保险产品列表失败！", "500");
		}
	}
	
	@ApiOperation(value = "获取产品详情")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取产品详情成功！"),@ApiResponse(code = 500, message = "获取产品详情失败！")})
	@RequestMapping(value = "/productDetail/{caseCode}", method = { RequestMethod.GET},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object productDetail(@ApiParam(required = true, value="产品方案-caseCode") @PathVariable String caseCode) {
		// API获取产品详情
		String resJson = productService.getProductDetail(caseCode);
		logger.info(resJson);
		if(resJson != null){
			// 解析产品数据（前端需求）
			ProductDetail pd = productService.retProdDetail(resJson);
			// 获取计划相关的产品列表
			List<PlanProduct> plans = productService.planProduct(caseCode);
			pd.setPlanProds(plans);
			// 获取产品ID
			Product p = productService.getProdByCaseCode(caseCode);
			if (p != null) {
				pd.setProductId(p.getId());
				if (StringUtil.isNotEmpty(p.getImgUrlShow())) {
					pd.setImgUrlShow(constPro.PRODUCT_FILE_ACCESS_PATH + p.getImgUrlShow());
				}
				pd.setAutoPay(p.getAutoPay());
			}
			return renderSuccess(pd);
		}else{
			return renderError("获取产品详情失败！", "500");
		}
	}
	
	@ApiOperation(value = "获取产品默认试算因子")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取产品默认试算因子成功！"),@ApiResponse(code = 500, message = "获取产品默认试算因子失败！")})
	@RequestMapping(value = "/defaultTrial/{caseCode}", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object defaultTrial(@ApiParam(required = true, value = "caseCode-方案代码") @PathVariable String caseCode) throws Exception {
		String resJson = productService.getDefaultTrial(caseCode);
		
		if (resJson != null) {
			TrialResp tr = JSONUtil.toObject(JSONObject.parseObject(resJson).getString("data"), TrialResp.class);
			String restrictGenes = JSONObject.parseObject(JSONObject.parseObject(resJson).getString("data")).getString("restrictGenes");
			List<RestrictGene> rgs = RegexUtil.parseRestrictGenes(restrictGenes);
			tr.setRestrictGenes(rgs);
			return renderSuccess(tr);
		}else {
			return renderError("获取产品默认试算因子失败！", "500");
		}
	}
	
	@ApiOperation(value = "产品试算")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "产品试算成功！"),@ApiResponse(code = 500, message = "产品试算失败！")})
	@RequestMapping(value = "/orderTrial", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object orderTrial(@ApiParam(required = true, value = "caseCode-方案代码, startDate-起保日期, "
			+ "newRestrictGenes-所有试算因子, oldRestrictGene-原试算因子") @RequestBody String jsonStr) {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		String caseCode = jo.getString("caseCode");
		String startDate = jo.getString("startDate");
		String newRestrictGenes = jo.getString("newRestrictGenes");
		String oldRestrictGene = jo.getString("oldRestrictGene");
		
		String resJson = productService.orderTrial(caseCode, startDate, newRestrictGenes, oldRestrictGene);
		if (resJson != null) {
			JSONObject jbt = JSONObject.parseObject(resJson);
			return renderSuccess(jbt);
		}else {
			return renderError("产品试算失败！", "500");
		}
	}
	
	@ApiOperation(value = "获取健康告知")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取健康告知成功！"),@ApiResponse(code = 500, message = "获取健康告知失败！")})
	@RequestMapping(value = "/healthStatement", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object healthStatement(@ApiParam(required = true, value = "caseCode-方案代码, genes-所有试算因子") @RequestBody String jsonStr) {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		String caseCode = jo.getString("caseCode");
		String genes = jo.getString("genes");
		
		String resJson = productService.getHealthStatement(caseCode, genes);
		if (resJson != null) {
			JSONObject jbt = JSONObject.parseObject(resJson);
			return renderSuccess(jbt);
		}else {
			return renderError("获取健康告知失败！", "500");
		}
	}
	
	@ApiOperation(value = "提交健康告知")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "提交健康告知成功！"),@ApiResponse(code = 500, message = "健康告知验证不通过！")})
	@RequestMapping(value = "/submitHealthState", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object submitHealthState(@ApiParam(required = true, value = "caseCode-方案代码, genes-所有试算因子, qaAnswer-健康告知答案信息")
			@RequestBody String jsonStr) throws Exception {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		String caseCode = jo.getString("caseCode");
		String genes = jo.getString("genes");
		String qaAnswerHelp = jo.getString("qaAnswer");
		String qaAnswer = RegexUtil.parseHealthState(qaAnswerHelp);
		
		String resJson = productService.submitHealthState(caseCode, genes, qaAnswer);
		if (resJson != null) {
			JSONObject jbt = JSONObject.parseObject(resJson);
			return renderSuccess(jbt);
		}else {
			return renderError("健康告知验证不通过！", "500");
		}
	}
	
	@ApiOperation(value = "获取银行地址")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取银行地址成功！"),@ApiResponse(code = 500, message = "获取银行地址失败！")})
	@RequestMapping(value = "/bankAddress", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object bankAddress(@ApiParam(required = true, value = "caseCode-方案代码, areaCode-地区编码") @RequestBody String jsonStr) {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		String caseCode = jo.getString("caseCode");
		//地区编码（用于查询下一级地区，顶级默认空）
		String areaCode = jo.getString("areaCode");
		String resJson = productService.bankAddress(caseCode, areaCode);
		if (resJson != 	null) {
			JSONObject jbt = JSONObject.parseObject(resJson);
			return renderSuccess(jbt);
		}else {
			return renderError("获取银行地址失败！", "500");
		}
	}
	
	@ApiOperation(value = "获取财产地址信息")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取财产地址信息成功！"),@ApiResponse(code = 500, message = "获取财产地址信息失败！")})
	@RequestMapping(value = "/propertyAddress", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object propertyAddress(@ApiParam(required = true, value = "caseCode-方案代码, areaCode-地区编码") @RequestBody String jsonStr) {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		String caseCode = jo.getString("caseCode");
		//地区编码（用于查询下一级地区，顶级默认空）
		String areaCode = jo.getString("areaCode");
		String resJson = productService.propertyAddress(caseCode, areaCode);
		if (resJson != null) {
			JSONObject jbt = JSONObject.parseObject(resJson);
			return renderSuccess(jbt);
		}else {
			return renderError("获取财产地址信息失败！", "500");
		}
	}
	
	@ApiOperation(value = "获取产品可投保省市地区")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取产品可投保省市地区成功！"),@ApiResponse(code = 500, message = "获取产品可投保省市地区失败！")})
	@RequestMapping(value = "/productInsuredArea", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object productInsuredArea(@ApiParam(required = true, value="caseCode-方案代码, areaCode-地区编码") @RequestBody String jsonStr) {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		String caseCode = jo.getString("caseCode");
		String areaCode = jo.getString("areaCode");
		String resJson = productService.productInsuredArea(caseCode, areaCode);
		if (resJson != null) {
			JSONObject jbt = JSONObject.parseObject(resJson);
			return renderSuccess(jbt);
		}else {
			return renderError("获取产品可投保省市地区失败！", "500");
		}
	}
	
	@ApiOperation(value = "获取产品职业信息")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取产品职业信息成功！"),@ApiResponse(code = 500, message = "获取产品职业信息失败！")})
	@RequestMapping(value = "/productInsuredJob/{caseCode}", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object productInsuredJob(@ApiParam(required = true, value="产品方案-caseCode") @PathVariable String caseCode) {
		String resJson = productService.productInsuredJob(caseCode);
		if (resJson != null) {
			List<ProductInsuredJob> jobs = productService.retJobs(resJson);
			return renderSuccess(jobs);
		}else {
			return renderError("获取获取产品职业信息失败！", "500");
		}
	}
	
	@ApiOperation(value = "获取产品投保属性")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取产品投保属性成功！"),@ApiResponse(code = 500, message = "获取产品投保属性失败！")})
	@RequestMapping(value = "/productInsuredAttr/{caseCode}", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object productInsuredAttr(@ApiParam(required = true, value = "caseCode-方案代码") @PathVariable String caseCode) {
		String resJson = productService.getProductInsuredAttr(caseCode);
		if (resJson != null) {
			JSONObject jbt = JSONObject.parseObject(resJson);
			return renderSuccess(jbt);
		}else {
			return renderError("获取产品投保属性失败！", "500");
		}
	}

	@ApiOperation(value = "获取为你推荐产品列表")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取为你推荐产品列表成功！")})
	@RequestMapping(value = "/getRecommendProdList", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object getRecommendProdList() {
		List<Product> prodList = productService.getRecommendProdList();
		for (Product prod : prodList) {
			if (StringUtil.isNotEmpty(prod.getRecommendUrl())) {
				prod.setRecommendUrl(constPro.PRODUCT_FILE_ACCESS_PATH + prod.getRecommendUrl());
			}
		}
		return renderSuccess(prodList);
	}
	
}
