package com.webill.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.webill.app.util.StringUtil;
import com.webill.core.model.User;
import com.webill.core.model.UserContact;
import com.webill.core.service.IUserContactService;
import com.webill.core.service.IUserService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;
@Controller
@RequestMapping("/userContact")
public class UserContactController extends BaseController {
    
    @Autowired
    private IUserContactService userContactService;
    @Autowired
    private IUserService userService;
    /**   
     * @Title: getDistributionInfo   
     * @Description: 获取配送接口  
     * @author: WangLongFei  
     * @date: 2017年6月3日 上午11:07:24   
     * @param userid
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/getUserContact/{userid}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String getUserContact(@PathVariable String userid){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("userId", userid);
    	List<UserContact> daList = userContactService.getUserContact(map);
    	String jsonstr = JSONUtil.toJSONString(daList);
    	return jsonstr;
    }
    /**   
     * @Title: updateUserContact   
     * @Description: 修改配送接口    
     * @author: WangLongFei  
     * @date: 2017年6月7日 下午3:35:57   
     * @param jsonstr
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/updateUserContact", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String updateUserContact(@RequestBody String jsonstr){
    	UserContact entity = JSONObject.parseObject(jsonstr, UserContact.class);
    	UserContact uc = userContactService.updateUserContact(entity);
    	return JSONObject.toJSONString(uc);
    }
    
    @RequestMapping(value = "/setDefault/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public boolean setDefault(@PathVariable String id){
    	boolean f = userContactService.updateDefault(id);
    	return f;
    }
    /**   
     * @Title: delUserContact   
     * @Description: 删除联系人（逻辑删除）  
     * @author: WangLongFei  
     * @date: 2017年6月28日 下午5:16:24   
     * @param id
     * @return  
     * @return: boolean  
     */
    @RequestMapping(value = "/delUserContact/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public boolean delUserContact(@PathVariable String id){
    	boolean f = userContactService.delUserContact(id);
    	return f;
    }
    /**   
     * @Title: updateDefault   
     * @Description: 设为默认联系人
     * @author: WangLongFei  
     * @date: 2017年6月28日 下午5:16:24   
     * @param id
     * @return  
     * @return: boolean  
     */
    @RequestMapping(value = "/updateDefault", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public JsonResult updateDefault(UserContact entity){
    	boolean f = userContactService.updateDefault(entity.getId().toString());
    	if(f){
    		return renderSuccess("设置成功！", "200");
    	}else{
    		return renderError("设置失败！", "500");
    	}
    }
    /**   
     * @Title: selectUserContact   
     * @Description: 根据id获取联系人信息 , 用于修改、添加配送信息页面数据显示
     * @author: WangLongFei  
     * @date: 2017年7月15日 下午1:53:00   
     * @param id
     * @return  
     * @return: UserContact  
     */
    @RequestMapping(value = "/selectUserContact/{id}/{flag}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String selectUserContact(@PathVariable String id,@PathVariable String flag,Model model){
    	UserContact uc = new UserContact(); 
    	Map<String,Object> map = new HashMap<String,Object>();
    	if("update".equals(flag)){
    		//id为配送id,获取修改配送信息所需数据
    		uc = userContactService.selectById(id);
    		map.put("id", uc.getUserId());
    	}else{
    		//id为用户id
    		map.put("id", id);
    		uc.setUserId(Integer.valueOf(id));
    	}
    	List<User> uList = userService.selectByMap(map);
    	if(uList.size()>0){
//    		uc.setWeixinNick(uList.get(0).getWeixinNick());
    	}
    	model.addAttribute("uc", uc);
    	return "system/userContact/edit";
    }
    
    
    /**   
     * @Title: addUserContact   
     * @Description: 添加配送信息——————后台  
     * @author: WangLongFei  
     * @date: 2017年7月14日 下午5:39:27   
     * @param jsonstr
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/addUserContact", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object addUserContact(UserContact uc){
    	UserContact entity = (UserContact) userContactService.saveContact(uc);
    	if(entity!=null){
    		return renderSuccess("添加配送信息成功！", "200",uc.getUserId());
    	}else{
    		return renderError("添加配送信息失败！", "500");
    	}
    }
    
    /**   
     * @Title: addUserContact   
     * @Description: 添加配送信息——————后台  
     * @author: WangLongFei  
     * @date: 2017年7月14日 下午5:39:27   
     * @param jsonstr
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/updateContact", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object updateContact(UserContact uc){
    	UserContact entity = (UserContact) userContactService.saveContact(uc);
    	if(entity!=null){
    		return renderSuccess("添加配送信息成功！", "200");
    	}else{
    		return renderError("添加配送信息失败！", "500");
    	}
    }
}
