package com.webill.app.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.webill.core.model.Customer;
import com.webill.core.model.User;
import com.webill.core.model.dianhuabang.DHBCallbackResp;
import com.webill.core.model.juxinli.Report;
import com.webill.core.service.ICustomerService;
import com.webill.core.service.IDianHuaBangService;
import com.webill.core.service.IReportMongoDBService;
import com.webill.core.service.IUserService;
import com.webill.framework.controller.BaseController;

/** 
 * @ClassName: DianHuaBangController 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月30日 上午10:30:18 
 */
@Controller
@RequestMapping(value = "/api/dhbReport")
public class DianHuaBangController  extends BaseController{
	@Autowired
	private IDianHuaBangService dianHuaBangService;
	@Autowired
	private IReportMongoDBService reportMongoDBService;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IUserService userService;
	
	@Transactional
	@RequestMapping(value = "/notifyUrl", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	public void notifyUrl(@RequestBody String jsonStr, HttpServletResponse response) {
		logger.info("电话邦异步通知报告响应数据==>"+jsonStr);
		DHBCallbackResp callbackResp= null;
        try {
        	response.setCharacterEncoding("UTF-8");
        	callbackResp = DHBCallbackResp.fromJsonString(jsonStr);
        	if (callbackResp.getStatus() == 0) { //报告生成状态（成功 0，失败 2）
        		//TODO 更新电话邦报告状态：采集成功
        		String sid = callbackResp.getSid();
        		//TODO 更新聚信立报告数据
        		String udr = dianHuaBangService.updateDhbReport(sid);
        		if (udr != null) {
        			Report report = new Report();
        			report.setSid(sid);
        			report.setDhbStatus(1); //采集成功
        			reportMongoDBService.updateReportBySid(report);
        			
        			// 根据sid获取mongodb报告
        			Report mdbReport = reportMongoDBService.selectReportBySid(sid);
        			Customer cus = customerService.selectById(Integer.parseInt(mdbReport.getCusId()));
        			//TODO 更新最新报告key编号等信息入库 
        			if (cus.getTemReportType() == 0) { //信息报告类型：0-基础 1-标准 
        				cus.setId(Integer.parseInt(mdbReport.getCusId()));
        				cus.setRefreshTimes(cus.getRefreshTimes() + 1);
        				cus.setLatestReportKey(mdbReport.getReportKey());
        				cus.setLatestReportType(cus.getTemReportType()); //信息报告类型：0-基础 1-标准
        				cus.setLatestReportStatus(1); //采集成功
        				customerService.updateSelectiveById(cus);
        				
        				// 更新user用户查询次数
        				User user = userService.selectById(cus.getUserId());
        				user.setId(cus.getUserId());
        				if (cus.getTemReportType() == 0) {//信息报告类型：0-基础 1-标准
        					user.setStandardTimes(user.getStandardTimes() - 1);
        				}else {
        					user.setAdvancedTimes(user.getAdvancedTimes() - 1);
        				}
        				userService.updateSelectiveById(user);
        			}
        			
        			//TODO 响应字符串"ok"
        			String ok = "ok";
        			response.getWriter().write(JSON.toJSONString(ok));
        			response.getWriter().flush();
        			return;
				}
        	}
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
