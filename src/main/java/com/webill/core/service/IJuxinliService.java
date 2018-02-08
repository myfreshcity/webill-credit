package com.webill.core.service;

import com.webill.core.model.Customer;
import com.webill.core.model.juxinli.JXLCollectReq;
import com.webill.core.model.juxinli.JXLResetPasswordReq;
import com.webill.core.model.juxinli.JXLResetPasswordResp;
import com.webill.core.model.juxinli.JXLSubmitFormReq;
import com.webill.core.model.juxinli.Report;

/** 
 * @ClassName: IJuxinliService 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:16:07 
 */
public interface IJuxinliService{

	/** 
	 * @Title: submitForm 
	 * @Description: 聚信立提交申请表单获取回执信息
	 * @author ZhangYadong
	 * @date 2018年1月24日 下午2:58:36
	 * @param req
	 * @param cusId
	 * @return
	 * @return JXLSubmitFormResp
	 */
	Object submitForm(Integer cusId, String reportKey);

	/** 
	 * @Title: resetPassword 
	 * @Description: 提交重置密码请求
	 * @author ZhangYadong
	 * @date 2018年1月21日 上午11:00:01
	 * @param map
	 * @return
	 * @return JXLResetPasswordResp
	 */
	JXLResetPasswordResp resetPassword(JXLResetPasswordReq map);

	/** 
	 * @Title: resetPasswordResp 
	 * @Description: 获取重置密码响应信息
	 * @author ZhangYadong
	 * @date 2018年1月21日 上午11:00:12
	 * @param map
	 * @return
	 * @return JXLResetPasswordResp
	 */
	JXLResetPasswordResp resetPasswordResp(JXLResetPasswordReq map);

	/** 
	 * @Title: parseJXLReportData 
	 * @Description: 解析聚信立报告数据
	 * @author ZhangYadong
	 * @date 2018年2月5日 上午11:19:40
	 * @param resJson
	 * @param cusId
	 * @return
	 * @return String
	 */
	String parseJXLReportData(String resJson, Integer cusId);

	/** 
	 * @Title: parseDHBReportData 
	 * @Description: 解析电话邦报告数据
	 * @author ZhangYadong
	 * @date 2018年2月5日 上午11:19:50
	 * @param DHBReportJson
	 * @param cusId
	 * @return
	 * @return String
	 */
	String parseDHBReportData(String DHBReportJson, Integer cusId);

	Object jxlCollect(JXLCollectReq jxlReq, Customer cus, String reportKey);

	void updateJxlReportStatus(Report report);

	String updateJxlReport(String token);

}
