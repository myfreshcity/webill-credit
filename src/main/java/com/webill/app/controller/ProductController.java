package com.webill.app.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.impl.PublicImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.util.StringUtil;
import com.webill.core.model.Label;
import com.webill.core.model.Product;
import com.webill.core.model.ProductCategoryRel;
import com.webill.core.model.ProductLabelRel;
import com.webill.core.model.ProductLog;
import com.webill.core.model.TOrder;
import com.webill.core.model.User;
import com.webill.core.service.ILabelService;
import com.webill.core.service.IProductCategoryRelService;
import com.webill.core.service.IProductLabelRelService;
import com.webill.core.service.IProductService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import reactor.core.Reactor;
import reactor.event.Event;

/** 
* @ClassName: ProductController 
* @Description: 
* @author ZhangYadong
* @date 2017年11月16日 下午4:12:56 
*/
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController{
	@Autowired
    private IProductService productService;
	@Autowired
	private IProductCategoryRelService productCategoryRelService;
	@Autowired
	private IProductLabelRelService productLabelRelService;
	@Autowired
	@Qualifier("rootReactor")
	private Reactor r;
	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "system/product/product-list";
    }
    
    @RequestMapping(value = "/prodLog", method = RequestMethod.GET)
    public String prodLog() {
    	return "system/product/product-log";
    }
    
    /** 
     * @Title: productList 
     * @Description: 后台商品列表      
     * @author: WangLongFei
     * @date: 2017年12月18日 下午1:43:02 
     * @param po
     * @return
     * @return: String
     */
    @RequestMapping(value = "/list", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String productList(Product p) {
       Page page = this.getPage(Integer.MAX_VALUE);
       page =   productService.getList(page, p);
       return JSONUtil.toJSONString(page);
    }
    
	/**
	 * @Title: getNum
	 * @Description: 获取各状态订单数量
	 * @author: WangLongFei
	 * @date: 2017年12月15日 下午2:09:44
	 * @param po
	 * @param model
	 * @return
	 * @return: JsonResult
	 */
	@RequestMapping(value = "/getNum", method = { RequestMethod.POST }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public Map<String,Object> getNum(TOrder po, Model model) {
		Map<String, Object> numMap = productService.getNumByStatus();
		return numMap;
	}

	@Autowired
	private ILabelService labelService;
	
    @RequestMapping(value = "/thirdPartList", method = RequestMethod.GET)
    public String thirdPartList() {
        return "system/product/third-part-list";
    }
    
	@RequestMapping(value = "/addProduct", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object saveProduct(Product prod) {
		boolean f = productService.saveProduct(prod);
		if(f){
			return renderSuccess("添加产品信息成功！", "200");
		}else{
			return renderError("添加产品信息失败！", "500");
		}
	}
	
	@RequestMapping(value = "/updateProduct", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object updateProduct(Product prod) {
		boolean f = productService.updateProduct(prod);
		if(f){
			return renderSuccess("修改产品信息成功！", "200");
		}else{
			return renderError("修改产品信息失败！", "500");
		}
	}
	
	@RequestMapping(value = "/tpProdList", method = { RequestMethod.GET , RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public String tpProdList() throws Exception {
		List<Product> tpProdList = productService.tpProdList();
		JSONObject ob = new JSONObject();
		ob.put("records", tpProdList);
		return JSONUtil.toJSONString(ob);	
	}
	
	@RequestMapping(value = "/prodDetail/{caseCode}/{fristCategory}/{secondCategory}/{companyName}",
			method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String thirdProdDetail(@PathVariable String caseCode, @PathVariable String fristCategory, @PathVariable String secondCategory,
    		@PathVariable String companyName, @RequestParam(required = false) String planName, Model model){
		Product prod = productService.parseProdDetail(caseCode, fristCategory, secondCategory, companyName, planName);
		logger.info(JSONUtil.toJSONString(prod));
    	model.addAttribute("prod", prod);
    	List<Label> navlabList = labelService.getNavLabel();
    	model.addAttribute("navlabList", navlabList);
    	return "system/product/add-product";
    }
	
	@RequestMapping(value = "/prodDetail/{id}", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String prodDetail(@PathVariable Integer id, Model model) {
		Product prod = productService.getProdById(id);
		if (StringUtil.isNotEmpty(prod.getImgUrlShow())) {
			prod.setImgUrlShow(constPro.PRODUCT_FILE_ACCESS_PATH + prod.getImgUrlShow());
		}
		if (StringUtil.isNotEmpty(prod.getRecommendUrl())) {
			prod.setRecommendUrl(constPro.PRODUCT_FILE_ACCESS_PATH + prod.getRecommendUrl());
		}
		ProductLabelRel plr = productLabelRelService.isRecommendByProdId(id);
		if (plr != null) {
			prod.setRecommend("0"); // 为你推荐：0-是，1-否
		}else{
			prod.setRecommend("1"); // 为你推荐：0-是，1-否
		}
		model.addAttribute("prod", prod);
		List<Label> navlabList = labelService.getNavLabel();
		model.addAttribute("navlabList", navlabList);
    	return "system/product/product-detail";
    }
	
	@RequestMapping(value = "/thirdPartylist", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object productList() throws Exception {
		String resJson = productService.getThirdProductList();
		if(resJson != null){
			JSONObject jbt = JSONObject.parseObject(resJson);
			return renderSuccess(jbt);
		}else{
			return renderError("获取保险产品列表失败！", "500");
		}
	}
	
	/** 
	 * @Title: uploadImg 
	 * @Description: 上传商品图片
	 * @author ZhangYadong
	 * @date 2018年1月2日 下午7:05:43
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @return Object
	 */
	@RequestMapping(value = "/upload/productImg", method = { RequestMethod.POST }, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody 
	public Object uploadImg(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
            //输出文件后缀名称
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            //图片名称
            String name = df.format(new Date());

            Random r = new Random();
            for(int i = 0 ;i<3 ;i++){
                name += r.nextInt(10);
            }
            String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
            //保存图片       File位置 （全路径）   /upload/fileName.jpg
            String url = constPro.PRODUCT_FILE_SAVE_PATH;
            //相对路径
            String path = "/"+name + "." + fileType;
            File f = new File(url);
            if(!f.exists()){
                f.mkdirs();
            }
            file.transferTo(new File(url+path));
            map.put("success", path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
	}
	
	@RequestMapping(value = "/prodcutByCaseCode", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object prodcutByCaseCode(String caseCode) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("case_code", caseCode);
		List<Product> plist = productService.selectByMap(map);
		if (plist != null && plist.size() > 0) {
			return renderSuccess("该产品已入库！", "210");
		}else{
			return renderSuccess("该产品没有入库！", "200");
		}
	}
	
	/** 
	 * @Title: onShelf 
	 * @Description: 商品上架 
	 * @author ZhangYadong
	 * @date 2017年12月22日 上午10:19:20
	 * @param prod
	 * @return
	 * @return JsonResult
	 */
	@RequestMapping(value = "/onShelf", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult onShelf(Product prod) {
		//是否上架 0：是 1：否
		prod.setOffShelf(0);

		User user = (User) request.getSession().getAttribute("loginUser");
		ProductLog pl = new ProductLog();
		pl.setProdId(prod.getId());
		pl.setCaseCode(prod.getCaseCode());
		pl.setPrice(prod.getDefaultPrice());
		pl.setOffShelfStatus(1);
		if (user != null) {
			pl.setOperatorId(user.getId());
		}
		r.notify("prodLog.onShelf", Event.wrap(pl));
		
		boolean f = productService.updateSelectiveById(prod);
		if (f) {
			return renderSuccess("商品已上架！", "200");
		}else {
			return renderError("修改商品上架异常！", "500");
		}
	}
	
	/** 
	 * @Title: offShelf 
	 * @Description: 商品 
	 * @author ZhangYadong
	 * @date 2017年12月22日 上午10:19:52
	 * @param prod
	 * @return
	 * @return JsonResult
	 */
	@RequestMapping(value = "/offShelf", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult offShelf(Product prod) {
		//是否上架 0：是 1：否
		prod.setOffShelf(1);
		
		User user = (User) request.getSession().getAttribute("loginUser");
		ProductLog pl = new ProductLog();
		pl.setProdId(prod.getId());
		pl.setCaseCode(prod.getCaseCode());
		pl.setPrice(prod.getDefaultPrice());
		pl.setOffShelfStatus(0);
		if (user != null) {
			pl.setOperatorId(user.getId());
		}
		r.notify("prodLog.onShelf", Event.wrap(pl));
		boolean f = productService.updateSelectiveById(prod);
		if (f) {
			return renderSuccess("商品已下架！", "200");
		}else {
			return renderError("修改商品下架异常！", "500");
		}
	}
	
	/** 
	 * @Title: deleleProd 
	 * @Description: 逻辑删除商品
	 * @author ZhangYadong
	 * @date 2017年12月26日 下午1:44:12
	 * @param p
	 * @return
	 * @return JsonResult
	 */
	@RequestMapping(value = "/deleleProd", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public JsonResult deleleProd(Product p){
		p.settStatus(-1); //-1：逻辑删除 0：正常数据
		boolean f = productService.updateSelectiveById(p);
		if (f) {
			return renderSuccess("商品删除成功！", "200");
		}else {
			return renderError("商品删除失败！", "500");
		}
	}
	
	@RequestMapping(value = "/getBusinessLabelByProdId/{prodId}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public JsonResult getBusinessLabelByProdId(@PathVariable Integer prodId){
		List<ProductLabelRel> plrList = productLabelRelService.getBusinessLabelByProdId(prodId);
		if (plrList != null && plrList.size() > 0) {
			return renderSuccess(plrList);
		}else {
			return renderError("商品删除失败！", "500");
		}
	}
}
