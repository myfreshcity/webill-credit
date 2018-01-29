package com.webill.core.service;

import com.webill.core.model.juxinli.JXLCollectReq;
import com.webill.core.model.juxinli.JXLResetPasswordReq;
import com.webill.core.model.juxinli.JXLResetPasswordResp;
import com.webill.core.model.juxinli.JXLResp;
import com.webill.core.model.juxinli.JXLSubmitFormReq;
import com.webill.core.model.juxinli.JXLSubmitFormResp;
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
	JXLSubmitFormResp submitForm(JXLSubmitFormReq req, Integer cusId);

	/** 
	 * @Title: collect 
	 * @Description: 聚信立提交数据采集请求,根据返回的processCode，可能会请求多次
	 * @author ZhangYadong
	 * @date 2018年1月19日 上午10:34:26
	 * @param req
	 * @return
	 * @return JXLResp
	 */
	JXLResp collect(JXLCollectReq req);

	/** 
	 * @Title: getAccessToken 
	 * @Description: 获取access_token，如果为空，则发送请求到聚信立获取永久有效期的access_token并保存在accessToken变量中，
	 *				   下次使用不需要再次请求
	 * @author ZhangYadong
	 * @date 2018年1月21日 上午10:58:29
	 * @return
	 * @return String
	 */
	String getAccessToken();

	/** 
	 * @Title: getReport 
	 * @Description: 通过token获取报告数据	申请表单提交时获取的token
	 * @author ZhangYadong
	 * @date 2018年1月21日 上午10:59:07
	 * @param token
	 * @param name
	 * @param idCard
	 * @param mobile
	 * @return
	 * @return String
	 */
	String getReport(String token, String name, String idCard, String mobile);

	/** 
	 * @Title: getMobileRawData 
	 * @Description: 通过token获取运营商原始数据	申请表单提交时获取的token
	 * @author ZhangYadong
	 * @date 2018年1月21日 上午10:59:33
	 * @param token
	 * @return
	 * @return String
	 */
	String getMobileRawData(String token);

	/** 
	 * @Title: updateReportStatus 
	 * @Description: 获取报告状态并更新数据库
	 * @author ZhangYadong
	 * @date 2018年1月21日 上午10:59:50
	 * @param report
	 * @return void
	 */
	void updateReportStatus(Report report);

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
	 * @date 2018年1月24日 下午5:14:21
	 * @param resJson
	 * @return
	 * @return String
	 */
	String parseJXLReportData(String resJson);

	/** 
	 * @Title: parseDHBReportData 
	 * @Description: 解析电话邦报告数据
	 * @author ZhangYadong
	 * @date 2018年1月26日 下午6:29:18
	 * @param DHBReportJson
	 * @return
	 * @return String
	 */
	String parseDHBReportData(String DHBReportJson);

}
