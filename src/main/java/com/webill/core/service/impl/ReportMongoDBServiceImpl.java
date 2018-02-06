package com.webill.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.webill.app.util.DateUtil;
import com.webill.app.util.EmptyUtil;
import com.webill.app.util.StringUtil;
import com.webill.core.model.juxinli.Report;
import com.webill.core.service.IReportMongoDBService;

/**
 * @ClassName: ReportMongoDBServiceImpl
 * @Description:
 * @author ZhangYadong
 * @date 2018年1月18日 下午3:14:19
 */
@Service
public class ReportMongoDBServiceImpl extends BaseMongoDBImpl<Report> implements IReportMongoDBService {

	@Override
	protected Class<Report> getEntityClass() {
		return Report.class;
	}
	
	@Override
	public void updateReportByToken(Report report) {
		// 反向解析对象
		Map<String, Object> map = null;
		try {
			map = parseEntity(report);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 生成参数
		Update update = new Update();
		if (EmptyUtil.isNotEmpty(map)) {
			for (String key : map.keySet()) {
				if (key.indexOf("{") != -1) {
					continue;
				} else if (!StringUtil.isEmpty(map.get(key))) {
					update.set(key, map.get(key));
				}
			}
		}
		mgt.updateFirst(new Query(Criteria.where("token").is(report.getToken())), update, getEntityClass());  
	}
	
	@Override
	public void updateReportBySid(Report report) {
		// 反向解析对象
		Map<String, Object> map = null;
		try {
			map = parseEntity(report);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 生成参数
		Update update = new Update();
		if (EmptyUtil.isNotEmpty(map)) {
			for (String key : map.keySet()) {
				if (key.indexOf("{") != -1) {
					continue;
				} else if (!StringUtil.isEmpty(map.get(key))) {
					update.set(key, map.get(key));
				}
			}
		}
		mgt.updateFirst(new Query(Criteria.where("sid").is(report.getSid())), update, getEntityClass());  
	}
	
	/**
	 * 查询聚信立和电话邦都采集成功的，最终采集状态为采集中的数据
	 */
	@Override
	public List<Report> selectReportByStatus() {
		List<Report> reports = mgt.find(new Query(Criteria.where("jxlStatus").is(1).and("dhbStatus").is(1).and("status").is(0)), getEntityClass());
		return reports;
	}
	
	/**
	 * @Title: selectTDReportByTdStatus
	 * @Description: 查询同盾状态为采集中的数据
	 * @author: WangLongFei
	 * @date: 2018年2月6日 下午1:13:01
	 * @return
	 * @return: List<Report>
	 */
	@Override
	public List<Report> selectTDReportByTdStatus() {
		List<Report> reports = mgt.find(new Query(Criteria.where("tdStatus").is(0)), getEntityClass());
		return reports;
	}

	@Override
	public Report selectReportByToken(String token){
		Report mdbReport = null;
		List<Report> reports = this.findByProp("token", token);
		if (reports != null && reports.size() > 0) {
			mdbReport = reports.get(0);
		}
		return mdbReport;
	}
	
	@Override
	public Report selectReportBySid(String sid){
		Report mdbReport = null;
		List<Report> reports = this.findByProp("sid", sid);
		if (reports != null && reports.size() > 0) {
			mdbReport = reports.get(0);
		}
		return mdbReport;
	}
	
	@Override
	public Report selectReportByReportKey(String reportKey){
		Report mdbReport = null;
		List<Report> reports = this.findByProp("reportKey", reportKey);
		if (reports != null && reports.size() > 0) {
			mdbReport = reports.get(0);
		}
		return mdbReport;
	}
	
	/**
	 * 每小时清除已过期（前一天到现在的）的未提交采集请求的数据
	 */
	@Override
	public void deleteExpire(){
		try {
			SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			Date date = new Date();
			Query query = new Query();
			Criteria criteria = Criteria.where("applyDate").gte(format.parse(DateUtil.getYesterdayDate(date))).lt(date).and("status").is("-1");
			query.addCriteria(criteria);
			mgt.remove(query, Report.class, "coll_report");
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
}
