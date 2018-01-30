package com.webill.core.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.util.EmojiUtil;
import com.webill.app.util.StringUtil;
import com.webill.core.Constant;
import com.webill.core.ResponseInfo;
import com.webill.core.model.RedisKeyDto;
import com.webill.core.model.User;
import com.webill.core.service.IUserService;
import com.webill.core.service.RedisService;
import com.webill.core.service.mapper.UserMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

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

   /* public User selectUserByWeixin(User userWhere){
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
    }*/

    /*public ResponseInfo addUser(User user) {
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
    }*/

	/*@Override
	public Page<User> getList(Page<User> page,User user) {
		List<User> uList = baseMapper.getList(page, user);
		page.setRecords(uList);
		return page;
	}*/

	/*@Override
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
	}*/

	@Override
	public User checkMobileIsExist(String mobileNo) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile_no", mobileNo);
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
		//获取系统发送的验证码
		RedisKeyDto redisWhere = new RedisKeyDto();
		redisWhere.setKeys(user.getMobileNo());
		RedisKeyDto redisKeyDto =  redisService.redisGet(redisWhere);
		if(redisKeyDto!=null){
			String verifyCode = redisKeyDto.getValues();
			if(user.getInCode().equals(verifyCode)){
				//获取手机号相关联的用户信息
				User  userCheck = this.checkMobileIsExist(user.getMobileNo());
				
				if(userCheck!=null){
					//手机号存在,换绑微信号
					//检测当前账号下是否有手机号
					//当前账号有手机号时
					//判断验证码是否正确
					if(user.getInCode().equals(verifyCode)){
						result = Constant.LOGIN_SUCCESS;
					}else{
						//验证码错误，请重试
						result = Constant.LOGIN_VERIFY_CODE_ERROR;
					}
				}else{
					result = Constant.LOGIN_FAIL;
					logger.debug("未找到该用户信息=====>"+user.getMobileNo());
				}
			}else{
				result = Constant.LOGIN_VERIFY_CODE_ERROR;
			}
		}else{
			result = Constant.LOGIN_VERIFY_CODE_INVALID;
		}
		return result;
	}

	@Override
	public Integer userLoginByPwd(User user) {
		 //根据手机号获取的用户信息
		 User mobUser = this.checkMobileIsExist(user.getMobileNo());
		 if(mobUser!=null){
			 if(user.getPassword().equals(mobUser.getPassword())){
				 return Constant.LOGIN_SUCCESS;
			 }else{
				 return Constant.LOGIN_PWD_ERROR;
			 }
		 }else{
			 return Constant.LOGIN_NOT_FOUNT_MOBILE;
		 }
	}

	@Transactional
	@Override
	public User saveRegister(User user) {
		//验证码正确-注册
		boolean f = false;
		f = this.insertSelective(user);
		if(f){
			User dbUser = new User();
			dbUser = this.selectById(user.getId());
			return dbUser;
		}else{
			return null;
		}
	}

}