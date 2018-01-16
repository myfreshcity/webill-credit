package com.webill.core.service;  
  
public interface IVerificationCodeService {
	
	/** 
	 * @Title: sendVerficationCode 
	 * @Description: 发送验证码  
	 * @author: WangLongFei
	 * @date: 2017年11月22日 下午1:53:55 
	 * @param mobile
	 * @return
	 * @return: Integer
	 */
	Integer sendVerficationCode(String mobile);
}
