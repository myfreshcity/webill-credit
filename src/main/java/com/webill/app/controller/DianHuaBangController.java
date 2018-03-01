package com.webill.app.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webill.app.util.PageUtil;
import com.webill.app.util.StringUtil;
import com.webill.core.model.dianhuabang.DHBCallbackResp;
import com.webill.core.model.dianhuabang.DHBCallsRecord;
import com.webill.core.model.juxinli.Report;
import com.webill.core.service.IDianHuaBangService;
import com.webill.core.service.IReportMongoDBService;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import io.swagger.annotations.ApiOperation;

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
	
	@ApiOperation(value = "电话邦报告回调")
	@Transactional
	@RequestMapping(value = "/notifyUrl", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	public void notifyUrl(@RequestBody String jsonStr, HttpServletResponse response) {
		logger.info("【电话邦】异步回调通知报告响应数据==>"+jsonStr);
		DHBCallbackResp callbackResp= null;
        try {
        	response.setCharacterEncoding("UTF-8");
        	callbackResp = DHBCallbackResp.fromJsonString(jsonStr);
        	if (callbackResp.getStatus() == 0) { //报告生成状态（成功 0，失败 2）
        		String sid = callbackResp.getSid();
        		//TODO 1：更新【电话邦】报告数据（1：更新报告生成时间 2：更新【电话邦】原始报告数据）
        		String udr = dianHuaBangService.updateDhbReport(sid);
        		//TODO 2：更新【电话邦】原始详单
        		String udocr = dianHuaBangService.updateDhbOrgCallsRecord(sid);
        		//TODO 3：更新【电话邦】报告状态：采集成功
        		if (udr != null && udocr != null) {
        			logger.info("【电话邦】更新报告数据（1：更新报告生成时间 2：更新【电话邦】原始报告数据）成功");
        			Report report = new Report();
        			report.setSid(sid);
        			report.setDhbStatus(1); //采集成功
        			reportMongoDBService.updateReportBySid(report);
        			
        			//TODO 4：响应字符串"ok"
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

	@ApiOperation(value = "分页获取电话详单数据")
	@RequestMapping(value = "/callsRecord", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult callsRecord(DHBCallsRecord dcr) {
		PageUtil page = null;
		try {
		    //查询出的list数据
			Report dbReport = reportMongoDBService.selectReportByReportKey(dcr.getReportKey());
			String dhbOrgCallsRecord = dbReport.getDhbOrgCallsRecord();
			if (StringUtil.isNotEmpty(dhbOrgCallsRecord)) {
				JSONObject jot = JSON.parseObject(dhbOrgCallsRecord);
				JSONArray callLogArr = jot.getJSONArray("call_log");
				if (jot.containsKey("call_log") && callLogArr != null && callLogArr.size() > 0) {
					page = dianHuaBangService.callsRecord(dcr, callLogArr);
				}else {
					return renderSuccess("手机原始详单中没有call_log节点！");
				}
			}else {
				return renderSuccess("没有查询到手机原始详单！");
			}
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return renderSuccess(page);
	}
	
}
