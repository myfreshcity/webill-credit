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
import com.webill.core.model.juxinli.Report;
import com.webill.core.service.ICouponService;
import com.webill.core.service.IJxlDhbService;
import com.webill.core.service.IReportMongoDBService;
import com.webill.core.service.ITongDunService;

import reactor.core.Reactor;

/**
 * 定时任务 对应：spring-task.xml Created by laimi on 2017/8/1.
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
    @Autowired
    private IJxlDhbService jxlDhbService;
	@Autowired
	private ITongDunService tongDunService;
	@Autowired
	private IReportMongoDBService reportMongoDBService;
    
	
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
    
    /**
	 * @Title: updateReport
	 * @Description: 每3分钟更新聚信立和电话邦报告
	 * @author ZhangYadong
	 * @date 2018年2月1日 下午3:36:57
	 * @return void
	 */
    public void updateJxlDhbReport(){
    	jxlDhbService.updateJxlDhbReport();
    }
    
	public void updateTdReport() {
		List<Report> report = reportMongoDBService.selectTDReportByTdStatus();
		for (Report r : report) {
			tongDunService.updateQueryByReportId(r.getSid(), r.getReportKey());
		}
	}
}
