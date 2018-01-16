package com.webill.app.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.webill.core.Constant;
import com.webill.core.model.IllegalDetail;
import com.webill.core.model.IllegalOrder;
import com.webill.core.model.UserContact;
import com.webill.core.service.IIllegalDetailService;
import com.webill.core.service.IIllegalOrderService;
import com.webill.core.service.IUserContactService;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

/**   
 * @ClassName: IllegalApiController   
 * @Description: 违章订单控制类  
 * @author: WangLongFei  
 * @date: 2017年5月31日 下午4:09:35      
 */
@Api("违章订单API")
@Controller
@RequestMapping("/api/illegal")
public class IllegalApiController extends BaseController {
    private static Log logger = LogFactory.getLog(IllegalApiController.class);

    @Autowired
    private IIllegalOrderService illegalOrderService;
    
    @Autowired
    private IIllegalDetailService illegalDetailService;
    
	@Autowired
	IUserContactService userContactService;
    
    /**   
     * @Title: list   
     * @Description: 用户个人中心违章订单列表  
     * @author: WangLongFei  
     * @date: 2017年6月15日 上午10:34:29   
     * @param userId
     * @return  
     * @return: String  
     */
	@ApiOperation(value = "根据用户id获取违章订单列表 ")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取获取违章订单列表成功！返回IllegalOrderList"),@ApiResponse(code = 500, message = "获取获取违章订单列表失败！")})
    @RequestMapping(value = "/list/{userId}",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult list(@ApiParam(required = true, value = "用户id") @PathVariable String userId){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("userId", userId);
    	List<IllegalOrder> iolist = illegalOrderService.getOnlyIllegalOrderByMap(map);
    	return renderSuccess("获取获取违章订单列表成功！", "200", iolist);
    }
	
	/**   
	 * @Title: delete   
	 * @Description: 删除违章订单  
	 * @author: WangLongFei  
	 * @date: 2017年7月27日 上午10:25:25   
	 * @param orderId
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "删除违章订单 ")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "删除订单成功！"),@ApiResponse(code = 500, message = "删除订单失败！")})
    @RequestMapping(value = "/delete",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult delete(@ApiParam(required = true, value = "订单id") @RequestBody String orderId){
		IllegalOrder order = new IllegalOrder();
		order.setId(Integer.valueOf(orderId));
		order.setStatus(Constant.DEL_STATUS);
    	boolean f = illegalOrderService.updateSelectiveById(order);
    	if(f){
    		return renderSuccess("删除订单成功！", "200");
    	}else{
    		return renderError("删除订单失败！", "500");
    	}
    }
    
	/**   
	 * @Title: get   
	 * @Description: 违章订单信息    
	 * @author: WangLongFei  
	 * @date: 2017年7月27日 上午10:25:14   
	 * @param orderid
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "违章订单信息")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取违章订单信息成功！"),@ApiResponse(code = 500, message = "获取违章订单信息失败！")})
	@RequestMapping(value = "/order/{orderId}", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
	public JsonResult get(@ApiParam(required = true, value = "订单id") @PathVariable String orderId){
		String jsonstr ="";
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("id", orderId);
		//订单信息
		IllegalOrder order = illegalOrderService.getIllegalOrderInfo(map1);
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("order_id", orderId);
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
			JSONObject jo = JSONObject.parseObject(jsonstr);
			return renderSuccess("获取违章订单信息成功", "200", jo);
		}else{
			return renderError("获取违章订单信息失败！", "500");
		}
	}

}
