package com.webill.core.service;

import java.util.List;

import com.webill.core.model.juxinli.Report;

/**
 * @ClassName: IReportMongoDBService
 * @Description:
 * @author ZhangYadong
 * @date 2018年1月18日 下午3:11:14
 */
public interface IReportMongoDBService extends IBaseMongoDBService<Report> {

	/**
	 * @Title: updateReportByToken
	 * @Description: 根据Token更新报告
	 * @author ZhangYadong
	 * @date 2018年1月19日 下午4:59:32
	 * @param report
	 * @return void
	 */
	void updateReportByToken(Report report);
	
	/**
	 * @Title: updateReportBySid
	 * @Description: 根据Sid更新报告
	 * @author ZhangYadong
	 * @date 2018年2月1日 下午2:36:11
	 * @param report
	 * @return void
	 */
	void updateReportBySid(Report report);

	/**
	 * @Title: selectReportByStatus
	 * @Description: 查询聚信立和电话邦都采集成功的，最终采集状态为采集中的数据
	 * @author ZhangYadong
	 * @date 2018年2月1日 下午4:02:10
	 * @return
	 * @return List<Report>
	 */
	List<Report> selectReportByStatus();
	
	/**
	 * @Title: selectReportByToken
	 * @Description: 根据token查询报告
	 * @author ZhangYadong
	 * @date 2018年2月2日 上午10:02:20
	 * @param token
	 * @return
	 * @return Report
	 */
	Report selectReportByToken(String token);

	/**
	 * @Title: selectReportBySid
	 * @Description: 根据sid查询报告
	 * @author ZhangYadong
	 * @date 2018年2月2日 上午10:02:38
	 * @param sid
	 * @return
	 * @return Report
	 */
	Report selectReportBySid(String sid);

	/**
	 * @Title: selectReportByReportKey
	 * @Description: 根据reportKey查询报告
	 * @author ZhangYadong
	 * @date 2018年2月2日 上午10:02:42
	 * @param reportKey
	 * @return
	 * @return Report
	 */
	Report selectReportByReportKey(String reportKey);
	
	/**
	 * @Title: deleteExpire
	 * @Description: 每小时清除已过期（前一天到现在的）的未提交采集请求的数据
	 * @author ZhangYadong
	 * @date 2018年1月23日 下午2:34:13
	 * @return void
	 */
	void deleteExpire();

	/**
	 * @Title: selectTDReportByTdStatus
	 * @Description: 获取同盾采集中的数据
	 * @author: WangLongFei
	 * @date: 2018年2月6日 下午1:17:30
	 * @return
	 * @return: List<Report>
	 */
	List<Report> selectTDReportByTdStatus();
}
