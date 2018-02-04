package com.webill.core.service;

import com.webill.core.model.Customer;
import com.webill.core.model.dianhuabang.DHBGetLoginReq;
import com.webill.core.model.dianhuabang.DHBLoginReq;
import com.webill.core.model.juxinli.JXLCollectReq;
import com.webill.core.model.juxinli.JXLSubmitFormReq;
import com.webill.core.model.juxinli.Report;

/** 
 * @ClassName: IJxlDhbService 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月31日 下午6:59:33 
 */
public interface IJxlDhbService {

	/** 
	 * @Title: submitFormAndGetSid 
	 * @Description: 聚信立==>提交申请表单获取回执信息
	 * 				   电话邦==>获取登录方式（获取会话标识sid）
	 * @author ZhangYadong
	 * @date 2018年1月31日 下午7:13:25
	 * @param jxlReq
	 * @param dhbReq
	 * @param cusId
	 * @return
	 * @return Object
	 */
	Object submitFormAndGetSid(JXLSubmitFormReq jxlReq, DHBGetLoginReq dhbReq, Integer cusId);

	/** 
	 * @Title: jxlOrDhbCollect 
	 * @Description: 聚信立==>提交数据采集请求,根据返回的processCode，可能会请求多次
	 * 				 OR
	 * 				   电话邦==>登录（采集信息）
	 * @author ZhangYadong
	 * @date 2018年2月2日 上午9:19:17
	 * @param jxlReq
	 * @param dhbReq
	 * @param cus
	 * @return
	 * @return Object
	 */
	Object jxlOrDhbCollect(JXLCollectReq jxlReq, DHBLoginReq dhbReq, Customer cus);
	/** 
	 * @Title: jxlAndDhbcollect 
	 * @Description: 聚信立==>提交数据采集请求,根据返回的processCode，可能会请求多次
	 * 				 AND
	 * 				   电话邦==>登录（采集信息）
	 * @author ZhangYadong
	 * @date 2018年2月1日 上午11:43:46
	 * @param jxlReq
	 * @param dhbReq
	 * @param cus
	 * @return
	 * @return Object
	 */
	Object jxlAndDhbcollect(JXLCollectReq jxlReq, DHBLoginReq dhbReq, Customer cus);
	/** 
	 * @Title: getDhbToken 
	 * @Description: 获取电话邦token，用于之后的接口访问,由于token存在过期时间，所以后面判断返回code为4000的时候重新获取
	 * @author ZhangYadong
	 * @date 2018年1月31日 下午8:05:10
	 * @return
	 * @return String
	 */
	String getDhbToken();

	/** 
	 * @Title: getJxlToken 
	 * @Description: 聚信立==>获取access_token，如果为空，则发送请求到聚信立获取永久有效期的access_token并保存在accessToken变量中，
	 * 				   下次使用不需要再次请求
	 * @author ZhangYadong
	 * @date 2018年2月2日 上午10:21:19
	 * @return
	 * @return String
	 */
	String getJxlToken();

	/** 
	 * @Title: updateJxlReportStatus 
	 * @Description: 聚信立==>获取报告状态并更新数据库
	 * @author ZhangYadong
	 * @date 2018年2月2日 上午10:22:52
	 * @param report
	 * @return void
	 */
	void updateJxlReportStatus(Report report);

	/** 
	 * @Title: updateJxlReport 
	 * @Description: 更新聚信立报告数据
	 * @author ZhangYadong
	 * @date 2018年2月2日 上午10:22:58
	 * @param token
	 * @return
	 * @return String
	 */
	String updateJxlReport(String token);

	/** 
	 * @Title: updateDhbReport 
	 * @Description: 更新电话邦报告数据
	 * @author ZhangYadong
	 * @date 2018年2月2日 上午10:23:27
	 * @param sid
	 * @return
	 * @return String
	 */
	String updateDhbReport(String sid);

	/** 
	 * @Title: updateJxlDhbReport 
	 * @Description: 更新聚信立-电话邦报告数据
	 * @author ZhangYadong
	 * @date 2018年2月2日 上午10:23:46
	 * @return void
	 */
	void updateJxlDhbReport();

	/** 
	 * @Title: selectMdbReport 
	 * @Description: 根据reportKey获取mongodb最终报告数据
	 * @author ZhangYadong
	 * @date 2018年2月2日 上午10:17:25
	 * @param reportKey
	 * @return
	 * @return String
	 */
	String selectMdbReport(String reportKey);
}
