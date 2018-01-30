package com.webill.core.service;

import com.webill.core.model.dianhuabang.DHBGetLoginReq;
import com.webill.core.model.dianhuabang.DHBGetLoginResp;
import com.webill.core.model.dianhuabang.DHBLoginReq;

/** 
 * @ClassName: IDianHuaBangService 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:16:07 
 */
public interface IDianHuaBangService {

	/** 
	 * @Title: getToken 
	 * @Description: 获取token，用于之后的接口访问,由于token存在过期时间，所以后面判断返回code为4000的时候重新获取，
	 * 				 token有效期为 7200 秒（2小时）
	 * @author ZhangYadong
	 * @date 2018年1月30日 上午10:17:12
	 * @return
	 * @return String
	 */
	String getToken();

	/** 
	 * @Title: getSid 
	 * @Description: 获取登录方式（获取会话标识sid）
	 * @author ZhangYadong
	 * @date 2018年1月30日 下午2:17:35
	 * @param req
	 * @param cusId
	 * @return
	 * @return DHBGetLoginRes
	 */
	DHBGetLoginResp getSid(DHBGetLoginReq req, Integer cusId);

	/** 
	 * @Title: login 
	 * @Description: 登录（采集信息）
	 * @author ZhangYadong
	 * @date 2018年1月30日 上午10:23:40
	 * @param token
	 * @param loginReq
	 * @return
	 * @return String
	 */
	String login(String token, DHBLoginReq loginReq);

	/** 
	 * @Title: loginSec 
	 * @Description: 登录后二次验证
	 * @author ZhangYadong
	 * @date 2018年1月30日 上午10:24:29
	 * @param token
	 * @param sid
	 * @param smsCode
	 * @return
	 * @return String
	 */
	String loginSec(String token, String sid, String smsCode);

	/** 
	 * @Title: getReport 
	 * @Description: 拉取详单报告
	 * @author ZhangYadong
	 * @date 2018年1月30日 上午10:25:14
	 * @param token
	 * @param sid
	 * @return
	 * @return String
	 */
	String getReport(String token, String sid);

	/** 
	 * @Title: forgotPwd 
	 * @Description: 忘记服务密码
	 * @author ZhangYadong
	 * @date 2018年1月30日 上午10:25:38
	 * @param token
	 * @param tel
	 * @param userName
	 * @param idCard
	 * @return
	 * @return String
	 */
	String forgotPwd(String token, String tel, String userName, String idCard);

	/** 
	 * @Title: forgotPwdSec 
	 * @Description: 忘记服务密码短信校验
	 * @author ZhangYadong
	 * @date 2018年1月30日 上午10:26:23
	 * @param token
	 * @param tid
	 * @param smsCode
	 * @return
	 * @return String
	 */
	String forgotPwdSec(String token, String tid, String smsCode);

	/** 
	 * @Title: forgotPwdContact 
	 * @Description: 忘记密码联系人通话记录校验
	 * @author ZhangYadong
	 * @date 2018年1月30日 上午10:26:46
	 * @param tid
	 * @param telList
	 * @return
	 * @return String
	 */
	String forgotPwdContact(String tid, String[] telList);

	/** 
	 * @Title: setServicePwd 
	 * @Description: 设置新的服务密码
	 * @author ZhangYadong
	 * @date 2018年1月30日 上午10:27:10
	 * @param tid
	 * @param newPwd
	 * @return
	 * @return String
	 */
	String setServicePwd(String tid, String newPwd);

	/** 
	 * @Title: forgotPwdLogin 
	 * @Description: 忘记密码登录校验
	 * @author ZhangYadong
	 * @date 2018年1月30日 上午10:27:36
	 * @param tid
	 * @param loginCode
	 * @return
	 * @return String
	 */
	String forgotPwdLogin(String tid, String loginCode);
	
}
