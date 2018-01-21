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

}
