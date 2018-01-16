package com.webill.core.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.util.EmojiUtil;
import com.webill.app.util.MD5;
import com.webill.app.util.StringUtil;
import com.webill.core.Constant;
import com.webill.core.ResponseInfo;
import com.webill.core.model.RedisKeyDto;
import com.webill.core.model.User;
import com.webill.core.service.IUserService;
import com.webill.core.service.RedisService;
import com.webill.core.service.mapper.UserMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements IUserService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
	private RedisService redisService;

    public User selectUserByWeixin(User userWhere){
    	String weixinId = userWhere.getOpenId();
    	String nickName = userWhere.getWeixinNick();
    	String headUrl = userWhere.getHeadUrl();
    	String sex = userWhere.getSex();
    	String unionId = userWhere.getUnionId();
    	
        User quser = new User();
        quser.settStatus(Constant.NORMAL_STATUS);
        quser.setLoginFlag(Constant.LOGIN_STATUS_YES);
        quser.setOpenId(weixinId);
        User user = this.selectOne(quser);
        
        logger.debug("get User by openId:"+weixinId);
        if(null == user) {
        	quser.setLoginFlag(Constant.LOGIN_STATUS_NO);
        	user = this.selectOne(quser);
        	if(null == user){
        		user = new User();
        		user.settStatus(Constant.NORMAL_STATUS);
        		Date now = new Date();
        		user.setCreatedTime(now);
        		user.setLoginFlag(Constant.LOGIN_STATUS_NO);
        	}
        }
        user.setOpenId(weixinId);
        String weixinBNick = EmojiUtil.resolveToByteFromEmoji(nickName);
        user.setWeixinNick(weixinBNick);
        user.setHeadUrl(headUrl);
        user.setSex(sex);
        user.setUnionId(unionId);
        user.setSubscribeFlag(1);
        
        user.setLoginFlag(0);

        
        this.insertOrUpdateSelective(user);
        
        return user;
    }

    public ResponseInfo addUser(User user) {
        User quser = this.selectOne(user);
        if (quser == null) {
            Date now = new Date();
            user.setCreatedTime(now);
            //user.setUpdatedTime(now);
            boolean flag = this.insert(user);
            if (flag) {
                return ResponseInfo.SUCCESS;
            } else {
                return ResponseInfo.ACTION_FAIL;
            }
        } else {
            return ResponseInfo.DATA_EXIST;
        }
    }

	@Override
	public Page<User> getList(Page<User> page,User user) {
		List<User> uList = baseMapper.getList(page, user);
		page.setRecords(uList);
		return page;
	}

	@Override
	public User saveRecommend(String userId) {
		User dUser = this.selectById(userId);
		//如果以前有推荐人或者用户创建时间和当前时间相差超过2小时，不更新
		long t1 = dUser.getCreatedTime().getTime();
		long t2 = new Date().getTime();
		long offset = (t2 - t1) / (1000);

		if (null != dUser.getRecommendId() || offset > 2 * 60 * 60 * 60) {
			return dUser;
		}
        boolean flag = this.insertOrUpdateSelective(dUser);
        if (flag) {
        	return dUser;
        } else {
            return null;
        }
	}

	@Override
	public User checkMobileIsExist(String mobileNo) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile", mobileNo);
		List<User> uList = this.selectByMap(map);
		if(uList.size()>0){
			logger.info("已存在该手机号========"+mobileNo);
			return uList.get(0);
		}else{
			logger.info("未查到该手机号========"+mobileNo);
			return null;
		}
	}

	@Override
	public Integer userLogin(User user) {
		Integer result = null;
		boolean f = false;
		//获取系统发送的验证码
		RedisKeyDto redisWhere = new RedisKeyDto();
		redisWhere.setKeys(user.getMobile());
		RedisKeyDto redisKeyDto =  redisService.redisGet(redisWhere);
		if(redisKeyDto!=null){
			String verifyCode = redisKeyDto.getValues();
			
			//获取手机号相关联的用户信息
			User  userCheck = this.checkMobileIsExist(user.getMobile());
			
			//根据微信获取的用户信息
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("open_id", user.getOpenId());
			List<User> uList = this.selectByMap(map);
			if(uList!=null){
				if(uList.size()==1){
					//获取当前登录用户的信息
					User dbUser = uList.get(0);
					if(userCheck!=null){
						//手机号存在,换绑微信号
						//检测当前账号下是否有手机号
						//当前账号有手机号时
						//判断验证码是否正确
						if(user.getInCode().equals(verifyCode)){
							//给新手机号关联的数据更新微信信息
							f = this.updateUserBind(userCheck, dbUser);
							if(f){
								//登录成功
								result = Constant.LOGIN_SUCCESS;
							}else{
								//绑定手机号失败，请重试
								result = Constant.LOGIN_FAIL;
							}
						}else{
							//验证码错误，请重试
							result = Constant.LOGIN_VERIFY_CODE_ERROR;
						}
					}
				}else{
					result = Constant.LOGIN_FAIL;
					logger.debug("该用户信息不唯一=====>"+user.getOpenId());
				}
			}else{
				result = Constant.LOGIN_FAIL;
				logger.debug("未找到该用户信息=====>"+user.getOpenId());
			}
		}else{
			result = Constant.LOGIN_VERIFY_CODE_INVALID;
		}
		return result;
	}

	@Transactional
	@Override
	public boolean updateUserBind(User mobUser,User wxUser) {
		boolean f = false;
		//给新手机号关联的数据更新微信信息
		mobUser.setOpenId(wxUser.getOpenId());
		mobUser.setHeadUrl(wxUser.getHeadUrl());
		mobUser.setWeixinNick(wxUser.getWeixinNick());
		mobUser.setSex(wxUser.getSex());
		mobUser.setLoginFlag(Constant.LOGIN_STATUS_YES);
		mobUser.setUnionId(wxUser.getUnionId());
		mobUser.settStatus(Constant.NORMAL_STATUS);
		f = this.updateSelectiveById(mobUser);
		
		if(!mobUser.getMobile().equals(wxUser.getMobile())){
			//如果原来数据没有手机号则删除原来数据，有手机号则制空原来用户的微信信息
			if(StringUtil.isNotEmpty(wxUser.getMobile())){
				//有手机号则制空原来用户的微信信息
				logger.info("*********clear user userId="+wxUser.getId()+"old weixin info:openId="+wxUser.getOpenId()+",HeadUrl="+wxUser.getHeadUrl()+",WeiXinNick="+wxUser.getWeixinNick()+",sex="+wxUser.getSex());
				wxUser.setLoginFlag(Constant.LOGIN_STATUS_NO);
				f = this.updateSelectiveById(wxUser); 
			}else{
				//没有手机号则删除原来数据
				f = this.deleteById(wxUser);
			}
		}
		
		return f;
	}

	@Override
	public boolean userLogon(User user) {
		user.setLoginFlag(Constant.LOGIN_STATUS_NO);
		boolean f = this.updateSelectiveById(user);
		return f;
	}

	@Override
	public Integer userLoginByPwd(User user) {
		 //根据手机号获取的用户信息
		 User mobUser = this.checkMobileIsExist(user.getMobile());
		 
		 if(StringUtil.isNotEmpty(user.getOpenId())){
			 //根据微信获取的用户信息
			 Map<String,Object> map = new HashMap<String,Object>();
			 map.put("open_id", user.getOpenId());
			 List<User> uList = this.selectByMap(map);
			 if(uList!=null){
				 if(uList.size()==1){
					 User wxUser = uList.get(0);
					 
					 if(mobUser!=null){
						 if(user.getPassword().equals(mobUser.getPassword())){
							 boolean f = this.updateUserBind(mobUser, wxUser);
							 if(f){
								 return Constant.LOGIN_SUCCESS;
							 }else{
								 return Constant.LOGIN_FAIL;
							 }
						 }else{
							 return Constant.LOGIN_PWD_ERROR;
						 }
					 }else{
						 return Constant.LOGIN_NOT_FOUNT_MOBILE;
					 }
				 }else{
					 return Constant.LOGIN_FAIL;
				 }
			 }else{
				 return Constant.LOGIN_FAIL;
			 }
		 }else{
			 if(mobUser!=null){
				 if(user.getPassword().equals(mobUser.getPassword())){
					 return Constant.LOGIN_SUCCESS;
				 }else{
					 return Constant.LOGIN_PWD_ERROR;
				 }
			 }else{
				 return Constant.LOGIN_FAIL;
			 }
		 }
	}

	@Transactional
	@Override
	public User saveRegister(User user) {
		//验证码正确-注册
		boolean f = false;
		if(StringUtil.isNotEmpty(user.getOpenId())){
			//微信端注册
			//获取当前账号信息
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("open_id", user.getOpenId());
			List<User> uList = this.selectByMap(map); 
			if(uList!= null){
				if(uList.size()>0){
					User dbUser = uList.get(0);
					if(uList.size()==1){
						//第一次注册,关联手机号，设置为自动登录状态
						user.setId(dbUser.getId());
						user.setLoginFlag(Constant.LOGIN_STATUS_YES);
						f = this.updateSelectiveById(user);
					}else{
						//第二次或者以后注册
						//把此前所有用户设置为非登录状态
						for (User u : uList) {
							u.setLoginFlag(Constant.LOGIN_STATUS_NO);
							f = this.updateSelectiveById(u);
							if(!f){
								break;
							}
						}
						if(f){
							//插入一个自动登录的新用户数据
							user.setLoginFlag(Constant.LOGIN_STATUS_YES);
							f = this.insertSelective(user);
						}
					}
					
				}
			}
		}else{
			//网页端注册
			f = this.insertSelective(user);
		}
		if(f){
			User dbUser = new User();
			dbUser = this.selectById(user.getId());
			return dbUser;
		}else{
			return null;
		}
	}

}