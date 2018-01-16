package com.webill.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.model.ProductLog;
import com.webill.core.service.IProductLogService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.controller.BaseController;

/** 
* @ClassName: ProductLogController 
* @Description: 
* @author ZhangYadong
* @date 2017年12月12日 下午4:45:59 
*/
@Controller
@RequestMapping("/productLog")
public class ProductLogController extends BaseController{
	@Autowired
    private IProductLogService productLogService;
	
    @RequestMapping(value = "/list", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String productList(ProductLog pl) {
       Page page = this.getPage(Integer.MAX_VALUE);
       page =   productLogService.getList(page, pl);
       return JSONUtil.toJSONString(page);
    }
    
    @RequestMapping(value = "/productLogList/{id}", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String prodLogDetail(@PathVariable Integer id, Model model) {
    	List<ProductLog> pls = productLogService.getListByProdId(id);
    	model.addAttribute("productLogList", pls);
    	return "system/product/product-log-list";
    }
}
