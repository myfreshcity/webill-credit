package com.webill.app.controller;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.SystemProperty;
import com.webill.app.util.ApplicInsurRelation;
import com.webill.app.util.CardType;
import com.webill.app.util.DateUtil;
import com.webill.app.util.StringUtil;
import com.webill.core.Constant;
import com.webill.core.model.CarInfo;
import com.webill.core.model.OrderApplicant;
import com.webill.core.model.OrderInsurant;
import com.webill.core.model.OrderLog;
import com.webill.core.model.Premium;
import com.webill.core.model.PremiumDetail;
import com.webill.core.model.PremiumOrder;
import com.webill.core.model.Product;
import com.webill.core.model.TOrder;
import com.webill.core.model.UserContact;
import com.webill.core.service.ICarInfoService;
import com.webill.core.service.IOrderApplicantService;
import com.webill.core.service.IOrderInsurantService;
import com.webill.core.service.IOrderLogService;
import com.webill.core.service.IPremiumDetailService;
import com.webill.core.service.IPremiumOrderService;
import com.webill.core.service.IPremiumService;
import com.webill.core.service.IProductService;
import com.webill.core.service.ITOrderService;
import com.webill.core.service.IUserContactService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;
import com.webill.framework.service.mapper.EntityWrapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**   
 * @ClassName: OrderController   
 * @Description: 违章订单处理类  
 * @author: WangLongFei  
 * @date: 2017年5月31日 下午4:09:35      
 */
@Controller
@RequestMapping("/tOrder")
public class TOrderController extends BaseController {
    private static Log logger = LogFactory.getLog(TOrderController.class);

    @Autowired
    private ITOrderService tOrderService;
    
    @Autowired
    private IOrderInsurantService orderInsurantService;
    
    @Autowired
    private IOrderApplicantService orderApplicantService;
    
    @Autowired
    private IProductService productService;
    
    @Autowired
    private IOrderLogService orderLogService;
    
    
    @Autowired
    protected SystemProperty constPro;
    

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "system/tOrder/index";
    }

    /**   
     * @Title: orderList   
     * @Description: 后台订单列表      
     * @author: WangLongFei  
     * @date: 2017年7月19日 上午9:26:41   
     * @param po
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/list", method = { RequestMethod.POST,RequestMethod.GET},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String orderList(TOrder po) {
       Page page = this.getPage(Integer.MAX_VALUE);
       page =   tOrderService.getList(page, po);
       return JSONUtil.toJSONString(page);
    }
    
	/**
	 * @Title: orderDetail
	 * @Description: 订单详情
	 * @author: WangLongFei
	 * @date: 2018年1月4日 下午1:57:08
	 * @param id
	 * @param model
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/orderDetail/{id}", method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public String orderDetail(@PathVariable(value = "id") String id, Model model) {
		TOrder po = new TOrder();
		po = tOrderService.selectById(id);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order_id", id);
		List<OrderApplicant> applicants = orderApplicantService.selectByMap(map);
		if (applicants != null) {
			po.setApplicant(applicants.get(0));
		}

		// 封装产品信息
		Product product = productService.selectById(po.getProductId());
		if (StringUtil.isNotEmpty(product.getImgUrlShow())) {
			product.setImgUrlShow(constPro.PRODUCT_FILE_ACCESS_PATH + product.getImgUrlShow());
		}
		po.setProduct(product);

		map.put("order_id", id);

		// 封装操作信息，订单日志
		EntityWrapper<OrderLog> entityWrapper = new EntityWrapper<OrderLog>();
		entityWrapper.addFilter("order_id={0}", id);
		entityWrapper.orderBy("created_time", false);

		List<OrderLog> olList = orderLogService.selectList(entityWrapper);
		po.setOrderLogList(olList);
		model.addAttribute("po", po);

		List<OrderInsurant> insurantList = orderInsurantService.selectByMap(map);
		for (OrderInsurant insurant : insurantList) {
			String relationName = ApplicInsurRelation.getName(Integer.valueOf(insurant.getRelationId()));
			insurant.setRelationName(relationName);
			insurant.setCardName(CardType.getName(Integer.valueOf(insurant.getCardType())));
		}
		model.addAttribute("applicant", applicants.get(0));
		model.addAttribute("insurantList", insurantList);

		return "system/tOrder/orderDetail";
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
	@ApiOperation(value = "获取各状态订单数量")
	@RequestMapping(value = "/getNum", method = { RequestMethod.POST }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public Map<String,Object> getNum(TOrder po, Model model) {
		Map<String, Object> numMap = tOrderService.getNumByStatus();
		return numMap;
	}
	/** 
	 * @Title: update 
	 * @Description: 修改订单
	 * @author: WangLongFei
	 * @date: 2017年12月12日 下午5:22:56 
	 * @param po
	 * @return
	 * @return: JsonResult
	 */
   @ApiOperation(value = "修改订单")
   @RequestMapping(value = "/update", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
   @ResponseBody
   public JsonResult update(TOrder po){
	   boolean f = tOrderService.updateSelectiveById(po);
/*	   TOrder whereEntity = new TOrder();
	   TOrder entity = new TOrder();
	   whereEntity.setId(po.getId());
	   entity.settStatus(po.gettStatus());
	   boolean f = tOrderService.updateSelective(entity, whereEntity);*/
	   if(f){
		   return renderSuccess();
	   }else{
		   return renderError();
	   }
   }
   @RequestMapping(value = "/closeProdOrder", method = {RequestMethod.POST}, produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
   @ResponseBody
   public JsonResult closeProdOrder(TOrder po){
	   po.settStatus(Constant.ORDER_STATUS_ALREADY_CLOSE); //-1：已删除、0：默认状态、10：待付款、15：待发货、20：已出单、80：已关闭、90：已失效
	   boolean f = tOrderService.updateSelectiveById(po);
	   if (f) {
			return renderSuccess("关闭订单成功！", "200");
		}else {
			return renderError("关闭订单失败！", "500");
		}
   }
}
