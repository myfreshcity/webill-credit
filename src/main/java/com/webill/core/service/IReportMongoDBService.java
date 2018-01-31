package com.webill.core.service;

import com.webill.core.model.juxinli.Report;

/** 
 * @ClassName: IReportMongoDBService 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月18日 下午3:11:14 
 */
public interface IReportMongoDBService extends IBaseMongoDBService<Report> {

	/** 
	 * @Title: updateReportByToke 
	 * @Description: 根据Token更新报告
	 * @author ZhangYadong
	 * @date 2018年1月19日 下午4:59:32
	 * @param report
	 * @return void
	 */
	void updateReportByToke(Report report);

	/** 
	 * @Title: deleteExpire 
	 * @Description: 每小时清除已过期（前一天到现在的）的未提交采集请求的数据 
	 * @author ZhangYadong
	 * @date 2018年1月23日 下午2:34:13
	 * @return void
	 */
	void deleteExpire();

}
