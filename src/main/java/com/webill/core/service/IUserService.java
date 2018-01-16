package com.webill.core.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.ResponseInfo;
import com.webill.core.model.User;
import com.webill.framework.service.ISuperService;

/**
 *
 * User 表数据服务层接口
 *
 */
public interface IUserService extends ISuperService<User> {

    public User selectUserByWeixin(User userWhere);

    public ResponseInfo addUser(User user);
    
    /**   
     * @Title: getList   
     * @Description: 用户列表   
     * @author: WangLongFei  
     * @date: 2017年8月28日 下午2:37:56   
     * @param page
     * @param user
     * @return  
     * @return: Page<User>  
     */
    public Page<User> getList(Page<User> page,User user);
    
    /**   
     * @Title: saveRecommend   
     * @Description: 用户推荐  
     * @author: WangLongFei  
     * @date: 2017年9月11日 上午10:32:21   
     * @param user
     * @return  
     * @return: User  
     */
    public User saveRecommend(String userId);
    
    /**   
     * @Title: checkMobile   
     * @Description: 验证手机号唯一性，存在返回用户信息；不存在返回null  
     * @author: WangLongFei  
     * @date: 2017年10月26日 下午1:46:19   
     * @return  
     * @return: User  
     */
    public User checkMobileIsExist(String mobileNo);
    
    /**   
     * @Title: userLogin   
     * @Description: 用户手机验证登录
     * @author: WangLongFei  
     * @date: 2017年10月26日 下午4:19:58   
     * @return  
     * @return: Integer  
     */
    public Integer userLogin(User user);
    
    /** 
     * @Title: userLoginByPwd 
     * @Description: 用户密码登录
     * @author: WangLongFei
     * @date: 2017年11月22日 下午2:51:31 
     * @param user
     * @return
     * @return: Integer
     */
    public Integer userLoginByPwd(User user);
    /**   
     * @Title: userLogon   
     * @Description: 注销登录  
     * @author: WangLongFei  
     * @date: 2017年10月28日 下午6:15:39   
     * @param user
     * @return  
     * @return: boolean  
     */
    public boolean userLogon(User user);
    
    /**   
     * @Title: updateUserBind   
     * @Description: 用户换绑手机号  
     * @author: WangLongFei  
     * @date: 2017年10月28日 下午6:18:11   
     * @param mobUser
     * @param wxUser
     * @return  
     * @return: boolean  
     */
    public boolean updateUserBind(User mobUser,User wxUser);
    
    /** 
     * @Title: saveRegister 
     * @Description: 用户注册
     * @author: WangLongFei
     * @date: 2017年11月24日 下午4:13:08 
     * @param user
     * @return
     * @return: User
     */
    public User saveRegister(User user);
    
}