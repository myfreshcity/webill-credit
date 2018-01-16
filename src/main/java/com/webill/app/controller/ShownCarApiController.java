package com.webill.app.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.webill.core.model.CarInfo;
import com.webill.core.model.ShownCar;
import com.webill.core.model.User;
import com.webill.core.model.UserContact;
import com.webill.core.service.ICarInfoService;
import com.webill.core.service.ICarUserRelService;
import com.webill.core.service.IMessageService;
import com.webill.core.service.IShownCarService;
import com.webill.core.service.IUserService;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Created by newcity on 2017/5/11.
 */
@Controller
@RequestMapping("/api/shownCar")
public class ShownCarApiController extends BaseController {
    private static Log logger = LogFactory.getLog(ShownCarApiController.class);

    @Autowired
    private ICarInfoService carInfoService;
    
    @Autowired
    private ICarUserRelService carUserRelService;
    
    @Autowired
    private IShownCarService shownCarService;

    @Autowired
    private IUserService userService;
    
    @Autowired
    private IMessageService messageService;
	/**   
	 * @Title: saveShownCar   
	 * @Description: 选择展示的车辆并保存 
	 * @author: WangLongFei  
	 * @date: 2017年9月8日 下午3:33:15   
	 * @param sc
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "选择展示的车辆并保存 ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "添加成功！返回id"), @ApiResponse(code = 500, message = "添加失败！") })
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public JsonResult saveShownCar(@ApiParam(required = true, value = "展示车辆id数组") @RequestBody String jsonStr) {
		JsonResult result = null;
		ShownCar sc = JSONObject.parseObject(jsonStr, ShownCar.class);
		String id = UUID.randomUUID().toString().replaceAll("\\-", "");
		sc.setId(id);
		sc.setCreatedTime(new Date());
		boolean f = shownCarService.insertSelective(sc);
		if (f) {
			result = renderSuccess("分享成功！", "200",id);
		} else {
			result = renderError("分享失败！", "500");
		}
		return result;
	}
	
	@ApiOperation(value = "根据id和推荐人获取展示的车辆 ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "获取成功！返回id"), @ApiResponse(code = 500, message = "获取失败！"),@ApiResponse(code = 300, message = "推荐失败！") })
	@RequestMapping(value = "/getList/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public JsonResult getList(@ApiParam(required = true, value = "展示车辆数据的id") @PathVariable String id) {
		JsonResult result = null;
		//获取车辆展示数据，通过推荐用户id,完成用户推荐业务
		ShownCar sc = shownCarService.selectById(id);
		User ruser = userService.saveRecommend(sc.getUserId().toString());
		
		//获取展示车辆
		if(ruser!=null){
			List<CarInfo> ciList = shownCarService.getList(id);
			if (ciList.size()>0) {
				result = renderSuccess("获取成功！", "200",ciList);
			} else {
				result = renderError("获取失败！", "500");
			}
		}else{
			result = renderError("推荐失败！", "300");
		}
		return result;
	}
}
