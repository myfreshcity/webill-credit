package com.webill.app.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.Constant;
import com.webill.core.ResponseInfo;
import com.webill.core.model.CarInfo;
import com.webill.core.model.IllegalDetail;
import com.webill.core.model.IllegalOrder;
import com.webill.core.model.UserContact;
import com.webill.core.service.ICarInfoService;
import com.webill.core.service.IIllegalDetailService;
import com.webill.core.service.IIllegalOrderService;
import com.webill.core.service.IUserContactService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.controller.BaseController;
import com.webill.framework.service.mapper.EntityWrapper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**   
 * @ClassName: IllegalOrderController   
 * @Description: 违章订单控制类  
 * @author: WangLongFei  
 * @date: 2017年5月31日 下午4:09:35      
 */
@Controller
@RequestMapping("/illegalOrder")
public class IllegalOrderController extends BaseController {
    private static Log logger = LogFactory.getLog(IllegalOrderController.class);

    @Autowired
    private IIllegalOrderService illegalOrderService;
    
    @Autowired
    private IIllegalDetailService illegalDetailService;
    
	@Autowired
	IUserContactService userContactService;
    
    @Autowired
    private ICarInfoService carInfoService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "system/illegalOrder/index";
    }

    /**   
     * @Title: confirmPay   
     * @Description: 确认代缴  
     * @author: WangLongFei  
     * @date: 2017年5月19日 下午1:53:28   
     * @param id
     * @return  
     * @return: String  
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping(value = "/confirmPay", method = { RequestMethod.POST },produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String confirmPay(CarInfo car,Model model,HttpServletRequest request) throws UnsupportedEncodingException {
    	String result = "";
    	ResponseInfo info = illegalOrderService.saveConfirmPay(car);
    	if(info.equals(ResponseInfo.SUCCESS)){
    		result = "system/illegalOrder/index";
    	}else{
    		result = this.orderDetail(car.getOrderId(), "0", model);
    	}
    	return result;
    }

    /**   
     * @Title: orderList   
     * @Description: 违章订单列表    
     * @author: WangLongFei  
     * @date: 2017年5月19日 下午3:29:53   
     * @param illo
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/list", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String orderList(IllegalOrder order) {
       logger.info("logging test....");
       Page page = this.getPage(Integer.MAX_VALUE);
       page =   illegalOrderService.getIllegalOrderList(page, order);
       return JSONUtil.toJSONString(page);
    }
    
    /**   
     * @Title: detaillist   
     * @Description: 违章详情列表
     * @author: WangLongFei  
     * @date: 2017年5月19日 下午3:28:09   
     * @param illo
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/orderDetail/{orderid}", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String orderDetail(@PathVariable String orderid,@PathVariable @RequestParam(required=false,defaultValue="0")String status,Model model) {
        Page page = this.getPage(Integer.MAX_VALUE);
        EntityWrapper<IllegalDetail> ew = new EntityWrapper<IllegalDetail>();
        if(null != orderid){
            ew.addFilter("order_id={0}", orderid);
            ew.addFilter("STATUS=1", status);
        }
        ew.orderBy("created_time desc");
        page = illegalDetailService.selectPage(page, ew);
        //获取违章订单信息---------order
        IllegalOrder order = illegalOrderService.selectById(orderid);
        //获取违章车辆信息 ---------ci
        CarInfo ci = carInfoService.selectById(order.getCarId());
        ci.setIllCount(page.getRecords().size());
        model.addAttribute("list", page.getRecords());
        model.addAttribute("orderId", orderid);
        model.addAttribute("status", order.getStatus());
        if(ci!=null){
        	model.addAttribute("ci", ci);
        }
        return "system/illegalOrder/detail-list";
    }
    
    /**   
     * @Title: getIllegalOrderListByUserId   
     * @Description: 用户个人中心违章订单列表  
     * @author: WangLongFei  
     * @date: 2017年6月15日 上午10:34:29   
     * @param userId
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/user/{userId}",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String getIllegalOrderListByUserId(@PathVariable String userId){
    	String jsonstr = "";
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("userId", userId);
    	List<IllegalOrder> iolist = illegalOrderService.getOnlyIllegalOrderByMap(map);
    	jsonstr = JSONArray.toJSONString(iolist);
    	return jsonstr;
    }
    
	/**   
	 * @Title: getIllegalOrderDetail   
	 * @Description: 违章订单信息  
	 * @author: WangLongFei  
	 * @date: 2017年6月15日 下午4:13:52   
	 * @param orderid
	 * @return  
	 * @return: String  
	 */
	@RequestMapping(value = "/order/{orderid}", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
	public String getIllegalOrderDetail(@PathVariable String orderid){
		String jsonstr ="";
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("id", orderid);
		//订单信息
		IllegalOrder order = illegalOrderService.getIllegalOrderInfo(map1);
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("order_id", orderid);
		//订单详情
		List<IllegalDetail> details = new ArrayList<IllegalDetail>();
		IllegalDetail dl = null;
		//获取违章详情id数组
		String[] detailIds = order.getDetailIds().split(",");
		for(String s:detailIds){
			dl = new IllegalDetail();
			dl = illegalDetailService.selectById(s);
			details.add(dl);
		}
		//获取默认联系人
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("user_id", order.getUserId());
		m.put("is_default", Constant.USER_CONTACT_IS_DEFAULT);
		List<UserContact> uclist = userContactService.selectByMap(m);
		if(order!=null){
			jsonstr = JSONObject.toJSONString(order);
			if(details.size()>0){
				jsonstr = JSONObject.toJSONString(order).replaceAll("}$", "");
				if(uclist.size()==1){
					jsonstr += ",\"uclist\":"+JSONArray.toJSONString(uclist);
				}
				jsonstr += ",\"detailList\":"+JSONArray.toJSONString(details);
				jsonstr += "}";
			}else{
				jsonstr = JSONObject.toJSONString(order).replaceAll("}$", "");
				if(uclist.size()==1){
					jsonstr += ",\"uclist\":"+JSONArray.toJSONString(uclist);
				}
				jsonstr += "}";
			}
		}
		return jsonstr;
	}

}
