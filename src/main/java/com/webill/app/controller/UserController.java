package com.webill.app.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.Constant;
import com.webill.core.model.User;
import com.webill.core.model.UserContact;
import com.webill.core.service.IUserContactService;
import com.webill.core.service.IUserService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.controller.BaseController;
import com.webill.framework.service.mapper.EntityWrapper;

/**   
 * @ClassName: UserController   
 * @Description: 用户信息控制类  
 * @author: WangLongFei  
 * @date: 2017年11月6日 上午11:31:17      
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;
    
    @Autowired
    private IUserContactService usercontactService;
    
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "system/user/index";
    }

    /**   
     * @Title: userList   
     * @Description: 用户列表        
     * @author: WangLongFei  
     * @date: 2017年8月28日 上午9:55:07   
     * @param user
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/list", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String userList(User user) {
       Page page = this.getPage(Integer.MAX_VALUE);
//       page =   userService.getList(page,user);
       return JSONUtil.toJSONString(page);
    }
    
    /**   
     * @Title: detail   
     * @Description: 获取用户详情
     * @author: WangLongFei  
     * @date: 2017年8月28日 上午9:56:35   
     * @param id
     * @param model
     * @return  
     * @return: String  
     */
    /*@RequestMapping(value = "/detail/{id}", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String detail(@PathVariable @Param(value = "id") String id,Model model){
    	User user = userService.selectById(id);
    	User recommendUser = userService.selectById(user.getRecommendId());
    	if(recommendUser!=null){
    		user.setRecommendName(recommendUser.getWeixinNick());
    	}
    	model.addAttribute("user", user);
    	model.addAttribute("userId", id);
    	return "system/user/detail";
    }*/
    
    /**   
     * @Title: userContact   
     * @Description: 根据用户id获取------联系人列表 
     * @author: WangLongFei  
     * @date: 2017年8月28日 上午10:27:49   
     * @param userId
     * @param model
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/contactList/{userId}", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String contactList(@PathVariable String userId,Model model){
    	EntityWrapper<UserContact> entity = new  EntityWrapper<UserContact>();
    	entity.addFilter("user_id={0}", userId);
    	entity.addFilter("status={0}", Constant.NORMAL_STATUS);
    	entity.orderBy("created_time desc");
    	List<UserContact> ucList = usercontactService.selectList(entity);
    	User user = null;
    	for (UserContact uc : ucList) {
			user = new User();
			user = userService.selectById(uc.getUserId());
//			uc.setWeixinNick(user.getWeixinNick());
		}
    	model.addAttribute("ucList", ucList);
    	model.addAttribute("userId", userId);
    	return "system/user/contactList";
    }
    
    /**   
     * @Title: saveDetail   
     * @Description: 保存用户信息   
     * @author: WangLongFei  
     * @date: 2017年8月28日 上午10:59:06   
     * @param user
     * @return  
     * @return: Object  
     */
    @RequestMapping(value = "/saveDetail", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object saveDetail(User user){
    	boolean f = userService.updateSelectiveById(user);
    	if(f){
    		return renderSuccess("保存用户信息成功！", "200");
    	}else{
    		return renderError("保存用户信息失败！", "500");
    	}
    }
    
}
