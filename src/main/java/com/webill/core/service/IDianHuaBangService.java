package com.webill.core.service;

import com.webill.core.model.Customer;
import com.webill.core.model.dianhuabang.DHBGetLoginReq;
import com.webill.core.model.dianhuabang.DHBLoginReq;

/** 
 * @ClassName: IDianHuaBangService 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:16:07 
 */
public interface IDianHuaBangService {

	Object dhbGetSid(DHBGetLoginReq dhbReq, Integer cusId, Integer temReportType);

	String getDhbToken();

	Object dhbCollect(DHBLoginReq dhbReq, Customer cus);

	String updateDhbReport(String sid);

	Object forgetPwd(String tel, String userName, String idCard);

	Object setServicePwd(String tid, String newPwd);

	Object forgotPwdSms(String tid, String smsCode);

	Object forgotPwdLogin(String tid, String loginCode);

	Object forgotPwdContact(String tid, String[] telList);

	Object refreshGraphic(String sid);

	Object dhbCollectSec(DHBLoginReq dhbReq, Customer cus);

	void updateJxlDhbReport();

	String selectMdbReport(String reportKey);
	
}
