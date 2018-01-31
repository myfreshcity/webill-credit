package com.webill.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webill.core.model.InfoGoods;
import com.webill.core.service.IInfoGoodsService;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: InfoGoodsApiController 
 * @Description: 客户信息商品
 * @author ZhangYadong
 * @date 2018年1月30日 下午5:41:04 
 */
@Controller
@RequestMapping(value = "/api/infoGoods")
public class InfoGoodsApiController extends BaseController {
	@Autowired
	private IInfoGoodsService infoGoodsService;
	
	@ApiOperation(value = "获取客户信息套餐")
	@RequestMapping(value = "/getInfoGoods/{infoLevel}", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult getInfoGoods(@PathVariable Integer infoLevel){
		Map<String, Object> map = new HashMap<>();
		// 用户信息等级：0-基础版 1-标准版
		map.put("info_level", infoLevel);
		List<InfoGoods> igList = infoGoodsService.selectByMap(map);
		return renderSuccess(igList);
	}
}
