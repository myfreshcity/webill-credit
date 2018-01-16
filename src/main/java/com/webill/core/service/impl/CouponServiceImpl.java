package com.webill.core.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.util.DateStyle;
import com.webill.app.util.DateUtil;
import com.webill.app.util.EnumUtil;
import com.webill.app.util.NumberUtil;
import com.webill.core.Constant;
import com.webill.core.model.Coupon;
import com.webill.core.model.User;
import com.webill.core.model.UserCoupon;
import com.webill.core.service.ICouponService;
import com.webill.core.service.IUserCouponService;
import com.webill.core.service.IUserService;
import com.webill.core.service.mapper.CouponMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * CouponNew 服务层接口实现类
 *
 */
@Service
public class CouponServiceImpl extends SuperServiceImpl<CouponMapper, Coupon> implements ICouponService {
    @Autowired
    private IUserCouponService userCouponService;
    
	@Autowired
	private IUserService userService;
	

	@Override
	public Page<Coupon> getList(Page<Coupon> page,Coupon c) {
		List<Coupon> clist = baseMapper.getList(c);
		for(Coupon cn:clist){
			cn.setStatusStr(EnumUtil.getStatusStr(cn.getStatus()));

			Map<String,Object> map = new HashMap<String,Object>();
			map.put("couponId", cn.getId());
			Integer peoPleNum = userCouponService.getPeoPleNum(map);
			cn.setSendPeopleNum(peoPleNum);
			if(cn.getSendScope().intValue()==0){
				cn.setUseScopeStr("无限制");
			}else if(cn.getSendScope().intValue()==1){
				cn.setUseScopeStr("代缴");
			}else{
				cn.setUseScopeStr("投保");
			}
			cn.setAmtLimitStr(cn.getAmtLimitStr()+"元");
			
			
        	if(cn.getSaleResult().intValue()==1){
        		BigDecimal   b   =   new   BigDecimal(Double.valueOf(cn.getSaleAmtStr())*10);  
        		double   prd   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).doubleValue();  
        		String saleAmtStr =String.valueOf(prd).replaceAll(".0$", "");
        		
        		cn.setSaleAmtStr(saleAmtStr+"折");
        	}else if(cn.getSaleResult().intValue()==2){
        		cn.setSaleAmtStr(cn.getSaleAmtStr()+"元");
        	}else{
        		cn.setSaleAmtStr(cn.getSaleAmtStr()+"元");
        	}
        	
//			cn.setSaleAmtStr(cn.getSaleAmtStr()+"元");
			if(cn.getCpValidDay()!=null){
				if(cn.getCpValidDay().intValue()!=0){
					cn.setValidTimeStr(String.valueOf(cn.getCpValidDay()));
				}
			}
			if(cn.getCpEndTime()!=null){
				cn.setValidTimeStr(String.valueOf(DateUtil.calculateIntervalDays(new Date(), cn.getCpEndTime())));
			}
			if(cn.getSendWay().intValue()==0){
				cn.setSendWayStr("系统发放");
			}else{
				cn.setSendWayStr("手动发放");
			}
		}
		page.setRecords(clist);
		return page;
	}

	@Transactional
	public boolean addSendCoupon(Integer couponId, String sendTarget) {
		boolean f = false;
		Map<String,Object> map = new HashMap<String,Object>();
		Coupon c = this.selectById(couponId);
		UserCoupon uc = null;
		
		//领取的优惠券数量
		int countCoupon = 0;
		
		if ("all".equals(sendTarget)) {
			//发放给所有用户
			map.put("status", Constant.NORMAL_STATUS);
			List<User> ulist = userService.selectByMap(map);
			for (User user : ulist) {
				uc = new UserCoupon();
				uc.setCouponId(couponId);
				uc.setUserId(user.getId());
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
				f = userCouponService.insertSelective(uc);
				if(!f){
					break;
				}else{
					countCoupon++;
				}
			}
		}else if(NumberUtil.isInteger(sendTarget)){
			//发放给车险到期天数sendTarget之内的用户
			map.clear();
			Date endTime = new Date();
			endTime = DateUtil.addDay(c.getSendStartTime(),Integer.valueOf(sendTarget));
			map.put("endTime",DateUtil.DateToString(endTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
			map.put("startTime", DateUtil.DateToString(c.getSendStartTime(), DateStyle.YYYY_MM_DD_HH_MM_SS));
//			List<CarInfo> cilist = carInfoService.getDuringCarList(map);
//			for (CarInfo carInfo : cilist) {
//				map.clear();
//				map.put("car_id", carInfo.getId());
//				List<CarUserRel> curlist = carUserRelService.selectByMap(map);
//				for (CarUserRel cur : curlist) {
//					uc = new UserCoupon();
//					uc.setUserId(cur.getUserId());
//					uc.setCouponId(couponId);
//					uc.setStatus(Constant.USER_COUPON_STATUS_UN_USE);
//					if(c.getCpValidDay()!=null){
//						if(c.getCpValidDay().intValue()!=0){
//							uc.setEndTime(DateUtil.addDay(new Date(), c.getCpValidDay()));
//						}
//					}
//					if(c.getCpEndTime()!=null){
//						uc.setEndTime(c.getCpEndTime());
//					}
//					uc.setCreatedTime(new Date());
//					f = userCouponService.insertSelective(uc);
//					if(!f){
//						break;
//					}else{
//						countCoupon++;
//					}
//				}
//			}
		}else{
			//发放给该车牌号下的用户
			map.clear();
			map.put("license_no", sendTarget);
//			List<CarUserRel> curlist = carUserRelService.selectByMap(map);
//			for (CarUserRel cur : curlist) {
//				uc = new UserCoupon();
//				uc.setUserId(cur.getUserId());
//				uc.setCouponId(couponId);
//				uc.setStatus(Constant.USER_COUPON_STATUS_UN_USE);
//				if(c.getCpValidDay()!=null){
//					if(c.getCpValidDay().intValue()!=0){
//						uc.setEndTime(DateUtil.addDay(new Date(), c.getCpValidDay()));
//					}
//				}
//				if(c.getCpEndTime()!=null){
//					uc.setEndTime(c.getCpEndTime());
//				}
//				uc.setCreatedTime(new Date());
//				f = userCouponService.insertSelective(uc);
//				if(!f){
//					break;
//				}else{
//					countCoupon++;
//				}
//			}
			
		}
		if(f){
			c.setObtainAmt(countCoupon);
			c.setPlanAmt(countCoupon);
			c.setStatus(Constant.COUPON_STATUS_SEND_END);
			f = this.updateSelectiveById(c);
		}
		return f;
	}

	@Override
	public boolean addCouponByCareFor() {
		return false;
	}

	@Override
	public List<Coupon> getWaitingCouponList(Coupon c) {
		return baseMapper.getWaitingCouponList(c);
	}

	@Override
	public List<Coupon> getSendCouponList(Map<String, Object> map) {
		return baseMapper.getSendCouponList(map);
	}

	
}