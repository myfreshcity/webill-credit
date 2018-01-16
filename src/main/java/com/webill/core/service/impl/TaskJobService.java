package com.webill.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.webill.app.SystemProperty;
import com.webill.core.Constant;
import com.webill.core.model.Coupon;
import com.webill.core.service.ICouponService;

import reactor.core.Reactor;

/**
 * 定时任务 对应：spring-task.xml
 * Created by laimi on 2017/8/1.
 */
@Service
public class TaskJobService {
	
	private static Log logger = LogFactory.getLog(TaskJobService.class);
	
    @Autowired
    @Qualifier("rootReactor")
    private Reactor r;
    
	@Autowired
	private ICouponService	couponService;
	
    @Autowired
    protected SystemProperty constPro; 
    
	
    /**   
     * @Title: sendCoupon   
     * @Description: 定时发放优惠券  
     * @author: WangLongFei  
     * @date: 2017年8月2日 下午1:41:54     
     * @return: void  
     */
    
    public void sendCoupon(){
    	Coupon cp = new Coupon();
    	cp.setStatus(Constant.COUPON_STATUS_CHECK_PASS_NO_SEND);
    	List<Coupon> clist = couponService.getWaitingCouponList(cp);
    	boolean f = false;
    	for(Coupon c:clist){
    		f = couponService.addSendCoupon(c.getId(), c.getSendTarget());
    		if(!f){
    			break;
    		}
    	}
    	if(f){
    		logger.info("定时发放优惠券成功！");
    	}
    }
    
}
