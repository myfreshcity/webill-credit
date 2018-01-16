package com.webill.app.controller;

import com.webill.core.model.UserCoupon;
import com.webill.core.service.IUserCouponService;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.Reactor;

/**   
 * @ClassName: InsuranceApiController   
 * @Description: 保单接口  
 * @author: WangLongFei  
 * @date: 2017年6月3日 上午10:54:25      
 */
@Api(value = "优惠券API")
@Controller
@RequestMapping("/api/coupon")
public class CouponApiController extends BaseController {

    private static Log logger = LogFactory.getLog(CouponApiController.class);
    
    
    @Autowired
    private IUserCouponService userCouponService;
    

    @Autowired
    @Qualifier("rootReactor")
    private Reactor r;

    
	/**   
	 * @Title: receiveCoupon   
	 * @Description: 领取优惠券  
	 * @author: WangLongFei  
	 * @date: 2017年7月25日 下午3:38:49   
	 * @param userId
	 * @param isActive
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "领取优惠券")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "领取优惠券成功!"),@ApiResponse(code = 500, message = "领取优惠券失败！")})
    @RequestMapping(value = "/receiveCoupon",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult receiveCoupon(@ApiParam(required = true, value = "userId") @RequestBody String userId,
    		@ApiParam(required = true, value = "优惠券id") @RequestBody Integer couponId){
		
		
		boolean f = false;
    	if(f){
    		logger.info("领取优惠券成功！");
    		return renderSuccess("领取成功！","200");
    	}else{
    		logger.info("领取优惠券失败！");
    		return renderSuccess("领取失败！","500");
    	}
    }
	
	/**   
	 * @Title: usableList   
	 * @Description: 获取优惠券(区分可用、不可用优惠券)  
	 * @author: WangLongFei  
	 * @date: 2017年7月25日 下午3:38:49   
	 * @param userId
	 * @param isActive
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "获取优惠券：0-不可用 1-可用")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取优惠券成功!")})
    @RequestMapping(value = "/usableList",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult usableList(@ApiParam(required = true, value = "订单编号") String orderNo){
		List<UserCoupon> uclist = userCouponService.getUsableCouponList(orderNo);
    	return renderSuccess("获取优惠券成功!","200",uclist);
    }
	
	/**   
	 * @Title: list   
	 * @Description: 获取用户所有优惠券  
	 * @author: WangLongFei  
	 * @date: 2017年7月25日 下午3:38:49   
	 * @param userId
	 * @param isActive
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "获取用户所有优惠券：状态 0-新建 1-未使用 2-已使用 3-失效")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取用户所有优惠券成功!"),@ApiResponse(code = 500, message = "获取用户所有优惠券失败！")})
	@RequestMapping(value = "/list",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult list(@ApiParam(required = true, value = "用户id") String userId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		List<UserCoupon> uclist = userCouponService.getUserCouponList(map);
		return renderSuccess("获取优惠券列表成功！","200",uclist);
	}

}
