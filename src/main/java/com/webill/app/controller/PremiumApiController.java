package com.webill.app.controller;




import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.util.PinYinUtil;
import com.webill.app.util.StringUtil;
import com.webill.core.Constant;
import com.webill.core.ResponseInfo;
import com.webill.core.model.AskPrice;
import com.webill.core.model.CarInfo;
import com.webill.core.model.PremiumDetail;
import com.webill.core.model.PremiumOrder;
import com.webill.core.service.ICarInfoService;
import com.webill.core.service.IPremiumOrderService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;
import com.webill.framework.service.mapper.EntityWrapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.Reactor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**   
 * @ClassName: InsuranceApiController   
 * @Description: 保单接口  
 * @author: WangLongFei  
 * @date: 2017年6月3日 上午10:54:25      
 */
@Api(value = "投保API")
@Controller
@RequestMapping("/api/premium")
public class PremiumApiController extends BaseController {

    private static Log logger = LogFactory.getLog(PremiumApiController.class);
    
    @Autowired
    private IPremiumOrderService premiumOrderService;
    
    @Autowired
    private ICarInfoService carInfoService;

    @Autowired
    @Qualifier("rootReactor")
    private Reactor r;

    
    /**   
     * @Title: createPremiumOrderBySystem   
     * @Description: 根据车主姓名和车牌号获取去年用户保单信息,并生成新订单;
     * 					如果没有历史保单信息，生成默认订单;  
     * @author: WangLongFei  
     * @date: 2017年7月24日 下午2:48:10   
     * @param carno
     * @param username  
     * @return: void  
     */
    @ApiOperation(value = "系统根据历史订单或默认订单创建订单")
	@RequestMapping(value = "/createPremiumOrderBySystem/{licenseNo}/{carOwner}", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public void newPoByHistoryOrDefault(@PathVariable String carno,@PathVariable String username) {
    	ResponseInfo f = premiumOrderService.checkCarNo(carno, username);
    	if(ResponseInfo.SUCCESS.equals(f)){
//    		premiumOrderService.newPoByHistoryOrDefault(carno,userId);
    	}else{
    		logger.info("车主姓名:"+username+"和车牌号:"+carno+"不匹配");
    	}
    }
    
	/**   
	 * @Title: getPremiumOrder   
	 * @Description: 获取投保订单  
	 * @author: WangLongFei  
	 * @date: 2017年7月24日 下午3:03:38   
	 * @param orderId
	 * @param userId
	 * @param flag
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "获取订单")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取订单成功！")})
    @RequestMapping(value = "/getPremiumOrder",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult getPremiumOrder(String orderId,String userId,@RequestParam(required = false,defaultValue="0")Integer flag){
		PremiumOrder order = premiumOrderService.saveLookPremiumOrder(orderId, null, userId);
		if(order!=null){
			return renderSuccess(order);
		}else{
			String result = premiumOrderService.getPremiumOrder(orderId,null,userId);
			JSONObject jo = JSONObject.parseObject(result);
			return renderSuccess(jo);
		}
    }

	/**   
	 * @Title: updateOrderCanPay
	 * @Description: 判断订单状态是否可支付，返回订单状态：
	 *						1、状态为4100（已查看）时，投保信息不完善，不可支付，不做任何操作
	 *						2、状态为4200（完成投保信息）时，修改订单状态为4400（待支付） 
	 * @author: WangLongFei  
	 * @date: 2017年7月24日 下午3:03:38   
	 * @param orderId
	 * @param userId
	 * @param flag
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "修改订单，确定是否可支付,并返回订单状态 ：1、状态为4100（已查看）时，投保信息不完善，不可支付，不做任何操作；2、状态为4200（完成投保信息）时，修改订单状态为4400（待支付）  ")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "修改成功！"),@ApiResponse(code = 500, message = "修改失败！")})
    @RequestMapping(value = "/updateOrderCanPay",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult updateOrderCanPay(@ApiParam(required = true, value = "订单id") @RequestBody PremiumOrder po){
    	Integer status = premiumOrderService.updateOrderCanPay(po.getId().toString());
    	if(status.intValue()==Constant.PREMIUM_ORDER_STATUS_ALREADYSEE){
    		return renderError("修改失败！","500",status);
    	}else{
    		return renderSuccess("修改成功！","200",status);
    	}
    }
	
	/**   
	 * @Title: getEveryOnePrice   
	 * @Description: 询价接口  
	 * @author: WangLongFei  
	 * @date: 2017年7月24日 下午3:04:00   
     * @param ciInsurerCom 保险公司
     * @param licenseNo 车牌号
     * @param engineNo  发动机号
     * @param carVin  车架号
     * @param registerDate  初登日期
     * @param moldName  品牌型号
     * @param detailList  询价方案
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "询价接口")
	@ApiResponses(value = {})
	@RequestMapping(value = "/askprice", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
    public String getEveryOnePrice(AskPrice ap) {
    	AskPrice result = premiumOrderService.askPremiumPrice(ap.getCiInsurerCom(),ap.getCarNo(),ap.getDetailList());
        return JSONUtil.toJSONString(result);
    }
    
	/**   
	 * @Title: updatePremiumOrder   
	 * @Description: 修改投保订单     
	 * @author: WangLongFei  
	 * @date: 2017年7月24日 下午3:04:14   
	 * @param po
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "修改订单")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "修改订单成功！"),
			@ApiResponse(code = 500, message = "修改订单失败")})
	@RequestMapping(value = "/updatePremiumOrder", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    public JsonResult updatePremiumOrder(@RequestBody PremiumOrder po){
		
    	String token = request.getHeader("token");
    	String userId = jwtUtil.getUserId(token);
		
    	Map<String,Object> map = new HashMap<String,Object>();
		map.put("contentFlag", po.getContentFlag());
		map.put("carNo", po.getLicenseNo());
		map.put("city", constPro.CITY);
		map.put("userId", userId);
		map.put("orderId", po.getId());
		
		//更改保险公司
		if(po.getCiInsurerCom()!=null){
			map.put("ciInsurerCom", po.getCiInsurerCom());
		}
		//更改投保方案
		if(po.getPdlist().size()>0){
			map.put("PremiumDetail", po.getPdlist());
		}
		//更改投保人信息
		if(po.getPersonMsg()!=null){
			map.put("personMsg", po.getPersonMsg());
		}
		boolean f = premiumOrderService.updatePremiumOrderInfo(map); 
		if(f){
			return renderSuccess("修改订单成功！", "200");
		}else{
			return renderError("修改订单失败！", "500");
		}
    }
    
	/**   
	 * @Title: createPremiumOrder   
	 * @Description: 生成预约订单---在线投保
	 * @author: WangLongFei  
	 * @date: 2017年7月24日 下午3:07:50   
	 * @param jsonstr
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "生成预约订单")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "生成预约订单成功！"),@ApiResponse(code = 500, message = "生成预约订单失败")})
    @RequestMapping(value = "/createPremiumOrder", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult createPremiumOrder(@ApiParam(required = true, value = "订单信息")@RequestBody String jsonStr){
		PremiumOrder po = JSONObject.parseObject(jsonStr, PremiumOrder.class);
		po.setCiInsurerCom(premiumOrderService.getBaoXianStr(po.getCiInsurerCom()));
    	po.setBiInsurerCom(po.getCiInsurerCom());
    	List<PremiumDetail> pdlist = po.getPdlist();
    	String result = premiumOrderService.savePremiumOrder(po, pdlist);
    	if("".equals(result)||result==null){
    		return renderError("生成预约订单失败！", "500");
    	}else{
    		return renderSuccess("生成预约订单成功！", "200", result);
    	}
    }

	/**   
	 * @Title: list   
	 * @Description: 用户个人中心投保订单列表  
	 * @author: WangLongFei  
	 * @date: 2017年7月24日 下午3:08:44   
	 * @param userId
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "投保订单列表")
	@ApiResponses(value = {})
    @RequestMapping(value = "/list/{userId}",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult getPremiumOrderListBy(@PathVariable String userId){
    	EntityWrapper<PremiumOrder> ew = new EntityWrapper<PremiumOrder>();
    	if(userId!=null){
    		ew.like("user_id", userId);
    	}
    	//订单列表排出【废弃订单】和【已删除订单】
    	Integer[] statusArray ={Constant.PREMIUM_ORDER_STATUS_DELETED,Constant.PREMIUM_ORDER_STATUS_USELESS};
    	ew.notIn("status", statusArray);
        ew.orderBy("created_time");
    	Page<PremiumOrder> page = this.getPage(Integer.MAX_VALUE);
    	page = premiumOrderService.selectPage(page, ew);
    	List<PremiumOrder> polist = page.getRecords();
    	for(PremiumOrder po:polist){
    		po.setCiInsurerCom(PinYinUtil.getPingYin(po.getCiInsurerCom()));
    		po.setBiInsurerCom(po.getCiInsurerCom());
    	}
    	return renderSuccess(polist);
    }
    
	/**   
	 * @Title: delete   
	 * @Description: 删除投保订单——————个人中心订单列表    
	 * @author: WangLongFei  
	 * @date: 2017年7月24日 下午3:09:04   
	 * @param orderId
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "删除投保订单")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "删除投保订单成功！"),@ApiResponse(code = 500, message = "删除投保订单失败")})
    @RequestMapping(value = "/delete", method = { RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult delPremiumOrder(@RequestBody String orderId){
    	boolean f = premiumOrderService.deletePremiumOrder(orderId);
    	if(f){
    		return renderSuccess("删除成功！","200");
    	}else{
    		return renderError("删除失败", "500");
    	}
    }
    
	/**   
	 * @Title: doOrderOnLine   
	 * @Description: 在线投保    
	 * @author: WangLongFei  
	 * @date: 2017年7月24日 下午3:09:16   
     * @param licenseNo
     * 				车牌号
     * @param userId
     * 				用户ID
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "在线投保")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功！"),@ApiResponse(code = 500, message = "操作失败！")})
    @RequestMapping(value = "/doOrderOnLine",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult doOrderOnLine(@RequestBody PremiumOrder order){
		String licenseNo = order.getLicenseNo();
		String userId = order.getUserId().toString();
		String insurerCom = order.getInsurerCom();
		
		JsonResult result = null;
		String orderStr = "";
		Integer f = 0;
		//选择车辆进入
		//查询订单
		//查询私有订单
		String jsonstr = premiumOrderService.getPremiumOrder(null,licenseNo, userId);
		PremiumOrder po = JSONObject.parseObject(jsonstr, PremiumOrder.class);
		if(po.getIsSuccess().equals(Constant.PREMIUM_NO_ORDER.toString())){
			//2、无订单
			//①判断是否在保期内
			f = premiumOrderService.isDuringPremiumTime(licenseNo);
			//保期内无订单，根据默认或者历史订单创建订单
			ResponseInfo info = premiumOrderService.savePoByHistoryOrDefault(licenseNo,userId,insurerCom);
			if(info.equals(ResponseInfo.SUCCESS)){
				//保期内，无订单，有报价
				orderStr = premiumOrderService.getPremiumOrder(null,licenseNo, userId);
			}else{
				//保期内，无订单，无报价s
				orderStr = premiumOrderService.getPremiumOrder(null,licenseNo, userId);
			}
			JSONObject jo = JSONObject.parseObject(orderStr);
			result = renderSuccess("获取订单成功！","200",jo);
		}else{
			//1、有订单，返回订单
			po = JSONObject.parseObject(jsonstr, PremiumOrder.class);
			boolean updateFlag = false;
			if(StringUtil.isNotEmpty(insurerCom)){
				updateFlag = premiumOrderService.updateInsurecom(insurerCom, po.getId().toString());
				if(updateFlag){
					jsonstr = premiumOrderService.getPremiumOrder(po.getId().toString(),null,null);
					po = JSONObject.parseObject(jsonstr, PremiumOrder.class);
				}
			}
			if(po.getUserId()==0){
				//如果是公有订单，没有获取到私有订单,判断共有订单是否询价成功
				if(Integer.valueOf(po.getIsSuccess())!=Constant.PREMIUM_ASK_PRICE_FAILE.intValue()){
					//如果询价成功创建一个以查看的的私有订单，并返回
					String childOrderId = premiumOrderService.saveCopyOrder(po.getId().toString(), userId);
					orderStr = premiumOrderService.getPremiumOrder(childOrderId,null, null);
				}else{
					//如果询价失败返回公有订单，进入预约
					orderStr = premiumOrderService.getPremiumOrder(null,licenseNo, userId);
				}
				JSONObject jo = JSONObject.parseObject(orderStr);
				result = renderSuccess("获取订单成功！","200",jo);
			}else{
				result = renderSuccess("获取订单成功！","200",po);
			}
		}
		return result;
    }
}
