package com.webill.app.controller;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.Constant;
import com.webill.core.model.PromoRule;
import com.webill.core.service.IPromoRuleService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;
import com.webill.framework.service.mapper.EntityWrapper;

/**   
 * @ClassName: OrderController   
 * @Description: 违章订单处理类  
 * @author: WangLongFei  
 * @date: 2017年5月31日 下午4:09:35      
 */
@Controller
@RequestMapping("/promoRule")
public class PromoRuleController extends BaseController {
    private static Log logger = LogFactory.getLog(PromoRuleController.class);

    
    @Autowired
    private IPromoRuleService promoRuleService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "system/promoRule/index";
    }

    /**   
     * @Title: list   
     * @Description: 优惠券规则列表  
     * @author: WangLongFei  
     * @date: 2017年7月28日 上午11:15:58   
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/list", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String list() {
    	Page page = this.getPage(Integer.MAX_VALUE);
        EntityWrapper<PromoRule> ew = new EntityWrapper<PromoRule>();
        ew.orderBy("created_time desc");
        page = promoRuleService.selectPage(page, ew);
        List<PromoRule> prlist = page.getRecords();
        for(PromoRule pr:prlist){
        	if(pr.getUseScope().intValue()==1){
        		pr.setUseScopeStr("代缴");
        	}else if(pr.getUseScope().intValue()==2){
        		pr.setUseScopeStr("投保");
        	}else{
        		pr.setUseScopeStr("无限制");
        	}
        	pr.setAmtLimitStr(pr.getAmtLimit()+"元");
        	
        	if(pr.getSaleResult().intValue()==1){
        		pr.setSaleResultStr("折扣");
        		BigDecimal   b   =   new   BigDecimal(pr.getSaleAmt().doubleValue()*10);  
        		double   prd   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).doubleValue();  
        		String saleAmtStr =String.valueOf(prd).replaceAll(".0$", "");
        		pr.setSaleAmtStr(saleAmtStr+"折");
        	}else if(pr.getSaleResult().intValue()==2){
        		pr.setSaleResultStr("兑换礼包");
        		pr.setSaleAmtStr(pr.getSaleAmt()+"元");
        	}else{
        		pr.setSaleResultStr("抵用");
        		pr.setSaleAmtStr(pr.getSaleAmt()+"元");
        	}
        	
        	
        	if(pr.getUserLevel().intValue()==0){
        		pr.setUserLevelStr("普通用户");
        	}else{
        		pr.setUserLevelStr("铜牌");
        	}
        	
        	if(pr.getTransferType().intValue()==1){
        		pr.setTransferTypeStr("可以");
        	}else if(pr.getTransferType().intValue()==2){
        		pr.setTransferTypeStr("只可");
        	}else{
        		pr.setTransferTypeStr("不可");
        	}
        	
        	if(pr.getActivityType().intValue()==1){
        		pr.setActivityTypeStr("可以");
        	}else if(pr.getActivityType().intValue()==2){
        		pr.setActivityTypeStr("只可");
        	}else{
        		pr.setActivityTypeStr("不可");
        	}
        	
        	if(pr.getStatus().intValue()==1){
        		pr.setStatusStr("启用");
        	}else if(pr.getStatus().intValue()==2){
        		pr.setStatusStr("禁用");
        	}else{
        		pr.setStatusStr("新建");
        	}
        }
       return JSONUtil.toJSONString(page);
    }
    
    /**   
     * @Title: saveNewPromoRule   
     * @Description: 生成新优惠券规则----后台    
     * @author: WangLongFei  
     * @date: 2017年7月28日 上午11:21:11   
     * @param pr
     * @return  
     * @return: Object  
     */
    @RequestMapping(value = "/add", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult add(PromoRule pr){
    	pr.setUseScope(pr.getUseRange());
    	pr.setUserLevel(Constant.PROMO_RULER_LEAVEL_COMMON);
    	boolean f = promoRuleService.insertSelective(pr);
    	if(f){
    		return renderSuccess("新建规则成功！", "200");
    	}else{
    		return renderError("新建规则失败！", "500");
    	}
    }
    
    /**   
     * @Title: doRuleAble   
     * @Description: 优惠券规则启用禁用操作（启用:1;禁用：2;）  
     * @author: WangLongFei  
     * @date: 2017年7月28日 下午12:00:46   
     * @param ruleId
     * @param flag
     * @return  
     * @return: Object  
     */
    @RequestMapping(value = "/doRuleAble", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object doRuleAble(PromoRule pr){
    	PromoRule r = new PromoRule();
    	r.setId(pr.getId());
    	boolean f = false;
    	if(Constant.PROMO_RULER_STATUS_DISABLED.toString().equals(pr.getFlag())){
    		r.setStatus(Constant.PROMO_RULER_STATUS_DISABLED);
    		f = promoRuleService.updateSelectiveById(r);
        	if(f){
        		return renderSuccess("禁用成功！","200");
        	}else{
        		return renderError("禁用失败", "500");
        	}
    	}else{
    		r.setStatus(Constant.PROMO_RULER_STATUS_ENABLE);
    		f = promoRuleService.updateSelectiveById(r);
        	if(f){
        		return renderSuccess("启用成功！","200");
        	}else{
        		return renderError("启用失败", "500");
        	}
    	}
    	
    }
}
