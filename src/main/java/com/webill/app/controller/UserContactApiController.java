package com.webill.app.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.webill.app.util.AddressUtil;
import com.webill.app.util.StringUtil;
import com.webill.core.model.CarCardApply;
import com.webill.core.model.RedisKeyDto;
import com.webill.core.model.UserContact;
import com.webill.core.service.IUserContactService;
import com.webill.core.service.RedisService;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "联系人API")
@Controller
@RequestMapping("/api/userContact")
public class UserContactApiController extends BaseController {
    
    @Autowired
    private IUserContactService userContactService;
    
	@Autowired
	private RedisService redisService;
    
    /**   
     * @Title: getList   
     * @Description: 获取联系人信息列表     
     * @author: WangLongFei  
     * @date: 2017年7月24日 下午4:53:59   
     * @param userId
     * @return  
     * @return: JsonResult  
     */
    @ApiOperation(value = "获取联系人信息列表")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "获取联系人信息列表成功！")})
    @RequestMapping(value = "/list/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult list(@ApiParam(required = true, value = "用户id") @PathVariable String userId){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("userId", userId);
    	List<UserContact> daList = userContactService.getUserContact(map);
    	return renderSuccess("获取联系人信息列表!", "200", daList);
    }

	/**   
	 * @Title: update   
	 * @Description: 修改联系人信息    
	 * @author: WangLongFei  
	 * @date: 2017年7月24日 下午4:55:25   
	 * @param entity
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "修改联系人信息")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "修改联系人信息成功！")})
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult update(@ApiParam(required = true, value = "联系人信息")@RequestBody String jsonStr){
		UserContact uContact = JSONObject.parseObject(jsonStr, UserContact.class);
    	UserContact uc = userContactService.updateUserContact(uContact);
    	return renderSuccess("修改联系人信息成功！", "200", uc);
    }
    
	/**   
	 * @Title: delete   
	 * @Description: 删除联系人信息（逻辑删除）    
	 * @author: WangLongFei  
	 * @date: 2017年7月24日 下午5:01:25   
	 * @param id
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "删除联系人信息")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "删除联系人信息成功！"),@ApiResponse(code = 500, message = "删除联系人信息失败！")})
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult delete(@ApiParam(required = true, value = "联系人信息id")@RequestBody String id){
    	boolean f = userContactService.delUserContact(id);
    	if(f){
    		return renderSuccess("删除联系人信息成功！", "200");
    	}else{
    		return renderError("删除联系人信息失败！", "500");
    	}
    }
	
	/**   
	 * @Title: get   
	 * @Description: 根据id获取联系人信息 
	 * @author: WangLongFei  
	 * @date: 2017年7月24日 下午5:04:21   
	 * @param id
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "根据id获取联系人信息")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "获取联系人信息成功！返回UserContact"),@ApiResponse(code = 500, message = "获取联系人信息失败！")})
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult get(@ApiParam(required = true, value = "联系人信息id") @PathVariable String id){
    	UserContact uc = userContactService.selectById(id);
    	if(uc!=null){
    		String[] addStr =new String[3];
    		if(StringUtil.isNotEmpty(uc.getProvince())&&StringUtil.isNotEmpty(uc.getCity())&&StringUtil.isNotEmpty(uc.getArea())){
    			addStr[0] =uc.getProvince();
    			addStr[1] =uc.getCity();
    			addStr[2] =uc.getArea();
    		}else{
    			addStr =null;
    		}
    		uc.setCityArray(addStr);
    		return renderSuccess("获取联系人信息成功！", "200", uc);
    	}else{
    		return renderError("获取联系人信息失败！", "500");
    	}
    }
    
	/**   
	 * @Title: add   
	 * @Description: 保存联系人 
	 * @author: WangLongFei  
	 * @date: 2017年7月24日 下午4:06:15   
	 * @param uc
	 * @return  
	 * @return: JsonResult  
	 */
	@ApiOperation(value = "添加联系人信息")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "添加成功！"),
			@ApiResponse(code = 500, message = "添加失败！")})
	@RequestMapping(value = "/add", method = { RequestMethod.POST },produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public JsonResult saveUserContact(@ApiParam(required = true, value = "联系人信息") @RequestBody String jsonStr) {
		UserContact uc = JSONObject.parseObject(jsonStr, UserContact.class);
		JsonResult result = null;
		//新增一条数据
		List<String> str = AddressUtil.splidAddress(uc.getCity());
 		uc.setProvince(str.get(0));
		uc.setCity(str.get(1));
		uc.setArea(str.get(2));
		uc.setIsDefault(0);
		uc.settStatus(0);
		uc.setCreatedTime(new Date());
		boolean f = userContactService.insertSelective(uc);
		if(f){
			result = renderSuccess("添加成功！", "200");
		}else{
			result = renderSuccess("添加失败！", "500");
		}
		return result;
	}
}
