package com.webill.app.controller;



import java.math.BigDecimal;
import java.text.ParseException;
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
import com.webill.app.util.DateUtil;
import com.webill.core.Constant;
import com.webill.core.model.Coupon;
import com.webill.core.model.PromoRule;
import com.webill.core.service.ICouponService;
import com.webill.core.service.IPromoRuleService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;
import com.webill.framework.service.mapper.EntityWrapper;

/**   
 * @ClassName: CouponController   
 * @Description: 优惠券处理类  
 * @author: WangLongFei  
 * @date: 2017年7月28日 下午4:27:44      
 */
@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {
    private static Log logger = LogFactory.getLog(CouponController.class);
    
    @Autowired
    private ICouponService couponService;
    
    @Autowired
    private IPromoRuleService promoRuleService;
    

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "system/coupon/index";
    }

    /**   
     * @Title: list   
     * @Description: 优惠券列表  
     * @author: WangLongFei  
     * @date: 2017年7月28日 上午11:15:58   
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/list", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String list(Coupon c) {
    	Page<Coupon> page = this.getPage(Integer.MAX_VALUE);
        page = couponService.getList(page,c);
       return JSONUtil.toJSONString(page);
    }
    
    /**   
     * @Title: ruleList   
     * @Description: 规则列表  
     * @author: WangLongFei  
     * @date: 2017年8月1日 下午5:44:29   
     * @param id
     * @param pms
     * @param model
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/ruleList", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String ruleList(PromoRule r){
    	Page page = this.getPage(Integer.MAX_VALUE);
        EntityWrapper<PromoRule> ew = new EntityWrapper<PromoRule>();
        ew.addFilter("status={0}", 1);
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
        	}else if(pr.getSaleResult().intValue()==2){
        		pr.setSaleResultStr("兑换礼包");
        	}else{
        		pr.setSaleResultStr("抵用");
        	}
        	
        	if(pr.getSaleResult().intValue()==1){
        		BigDecimal   b   =   new   BigDecimal(pr.getSaleAmt().doubleValue()*10);  
        		double   prd   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).doubleValue();  
        		String saleAmtStr =String.valueOf(prd).replaceAll(".0$", "");
        		
        		pr.setSaleAmtStr(saleAmtStr+"折");
        	}else if(pr.getSaleResult().intValue()==2){
        		pr.setSaleAmtStr(pr.getSaleAmt()+"元");
        	}else{
        		pr.setSaleAmtStr(pr.getSaleAmt()+"元");
        	}
//        	pr.setSaleAmtStr(pr.getSaleAmt()+"元");
        	
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
        }
    	
    	return JSONUtil.toJSONString(prlist);
    }
    /**   
     * @Title: saveOrGet   
     * @Description: 生成新优惠券----后台    
     * @author: WangLongFei  
     * @date: 2017年7月28日 上午11:21:11   
     * @param pr
     * @return  
     * @return: Object  
     */
    @RequestMapping(value = "/saveOrGet", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult saveOrGet(Coupon cn){
    	JsonResult jr =null;
    	try {
    		cn.setObtainAmt(0);
    		if(cn.getSendWay().intValue()==1){
    			cn.setPlanAmt(0);
    		}
    		cn.setUseAmt(0);
			cn.setSendStartTime(DateUtil.datePickerToDate(cn.getSendStartTimeStr()));
			cn.setSendEndTime(DateUtil.datePickerToDate(cn.getSendEndTimeStr()));
			cn.setStatus(Constant.COUPON_STATUS_NEW);
	    	if(cn.getId()!=null){
	    		//查看优惠券
	    		Coupon c = couponService.selectById(cn.getId());
	    		jr = renderSuccess("获取优惠券成功！", "200", c);
	    	}else{
	    		//新建优惠券
	    		boolean f = couponService.insertSelective(cn);
				if(f){
					jr = renderSuccess("新建优惠券成功！", "200");
				}else{
					jr = renderError("新建优惠券失败！", "500");
				}
	    	}
		} catch (ParseException e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}
		return jr;
    	
    }
    
    /**   
     * @Title: saveSendCoupon   
     * @Description: 优惠券规则启用禁用操作（状态：新建-100；提交审核-200；审核失败-300；审核通过未发放-400；发放中-500；停止发放-600；发放结束-700；作废-800）    
     * @author: WangLongFei  
     * @date: 2017年8月2日 下午7:07:35   
     * @param cp
     * @return  
     * @return: Object  
     */
    @RequestMapping(value = "/saveSendCoupon", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult saveSendCoupon(Coupon cp){
    	Coupon oldCp = couponService.selectById(cp.getId());
    	Coupon c = new Coupon();
    	c.setId(Integer.valueOf(cp.getId()));
    	if(oldCp.getSendWay().intValue()==1){
    		//手动发放
    		c.setStatus(Constant.COUPON_STATUS_CHECK_PASS_NO_SEND);
    		c.setSendTarget(cp.getSendTarget());
    	}else{
    		c.setStatus(Constant.COUPON_STATUS_SENDING);
    	}
    	boolean f = couponService.updateSelectiveById(c);
    	if(f){
    		return renderSuccess("发放成功！","200");
    	}else{
    		return renderError("发放失败！", "500");
    	}
    }
    
    /**   
     * @Title: getCouponStatus   
     * @Description: 获取优惠券状态（状态：新建-100；提交审核-200；审核失败-300；审核通过未发放-400；发放中-500；停止发放-600；发放结束-700；作废-800）    
     * @author: WangLongFei  
     * @date: 2017年8月2日 下午7:07:35   
     * @param cp
     * @return  
     * @return: Object  
     */
    @RequestMapping(value = "/getCouponStatus", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult getCouponStatus(Coupon cp){
    	cp = couponService.selectById(cp.getId());
    	JsonResult jr = renderSuccess("获取成功！","200",JSONUtil.toJSONString(cp));
    	return jr;
    }
    /**   
     * @Title: saveStopCoupon   
     * @Description: 优惠券规则启用禁用操作（状态：新建-100；提交审核-200；审核失败-300；审核通过未发放-400；发放中-500；停止发放-600；发放结束-700；作废-800）    
     * @author: WangLongFei  
     * @date: 2017年8月2日 下午7:07:35   
     * @param couponId
     * @param flag
     * @return  
     * @return: Object  
     */
    @RequestMapping(value = "/saveStopCoupon", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult saveStopCoupon(Coupon cp){
    	Coupon c = new Coupon();
    	c.setId(Integer.valueOf(cp.getId()));
		c.setStatus(Constant.COUPON_STATUS_STOP_SEND);
    	boolean f = couponService.updateSelectiveById(c);
    	if(f){
    		return renderSuccess("停止发放成功！","200");
    	}else{
    		return renderError("停止发放失败！", "500");
    	}
    }
}
