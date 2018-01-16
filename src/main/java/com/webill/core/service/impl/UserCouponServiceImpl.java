package com.webill.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webill.app.util.DateUtil;
import com.webill.core.Constant;
import com.webill.core.model.Coupon;
import com.webill.core.model.User;
import com.webill.core.model.UserCoupon;
import com.webill.core.service.ICouponService;
import com.webill.core.service.IUserCouponService;
import com.webill.core.service.IUserService;
import com.webill.core.service.mapper.UserCouponMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * UserCoupon 服务层接口实现类
 *
 */
@Service
public class UserCouponServiceImpl extends SuperServiceImpl<UserCouponMapper, UserCoupon>
		implements IUserCouponService {

	
	
	@Autowired
	ICouponService couponService;
	
	@Autowired
	IUserService userService;
	
	@Override
	public List<UserCoupon> getUsableCouponList(String orderNo) {
		String flag = orderNo.substring(0, 4);
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserCoupon> uclist = new ArrayList<UserCoupon>();
		if (flag.equals("1001")) {
			map.put("trade_no", orderNo);
		} else if(flag.equals("1002")) {
			map.put("order_no", orderNo);
		}else{
			uclist =null;
		}
		return uclist;
	}

	@Override
	public List<UserCoupon> getCouponListBy(Map<String, Object> map) {
		return baseMapper.getCouponListBy(map);
	}

	@Override
	public List<UserCoupon> getUserCouponList(Map<String, Object> map) {
		return baseMapper.getUserCouponList(map);
	}

	@Override
	public Integer getPeoPleNum(Map<String, Object> map) {
		return baseMapper.getPeoPleNum(map);
	}
	
	@Transactional
	@Override
	public boolean addUserCouponByEvent(Map<String, Object> map) {
		boolean f= false;
		if(map.get("event").equals("subscribe")){
        	Map<String,Object> mapby = new HashMap<String,Object>();
        	mapby.put("open_id", map.get("fromUserName"));
        	List<User> ulist = userService.selectByMap(mapby);
        	if(ulist.size()==1){
        		User u =ulist.get(0);
        		UserCoupon uc = new UserCoupon();
        		uc.setUserId(u.getId());
        		mapby.clear();
        		mapby.put("userId", u.getId());
        		Coupon cp = null;
        		/*获取可领取优惠券*/
        		List<Coupon> clist = couponService.getSendCouponList(mapby);
        		for(Coupon c:clist){
        			uc = new UserCoupon();
    				uc.setUserId(u.getId());
    				uc.setCouponId(c.getId());
    				uc.setStatus(Constant.USER_COUPON_STATUS_UN_USE);
    				if(c.getCpValidDay()!=null){
    					if(c.getCpValidDay().intValue()!=0){
    						uc.setEndTime(DateUtil.addDay(new Date(), c.getCpValidDay()));
    					}
    				}
    				if(c.getCpEndTime()!=null){
    					uc.setEndTime(c.getCpEndTime());
    				}
    				uc.setCreatedTime(new Date());
    				f = this.insertSelective(uc);
    				
    				//修改优惠券领用数量+1
    				if(f){
    					cp = new Coupon();
    					cp.setId(c.getId());
    					cp.setObtainAmt(c.getObtainAmt()+1);
    					f = couponService.updateSelectiveById(cp);
    				}
        		}
        	}
        }
		return f;
	}

}