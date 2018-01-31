package com.webill.core.service;

import com.webill.core.model.EmailBean;

/** 
* @ClassName: ISendEmailService 
* @Description: 
* @author ZhangYadong
* @date 2017年11月24日 下午3:34:37 
*/
public interface ISendEmailService {

	/** 
	 * @Title: sendEmail 
	 * @Description: 发送邮件
	 * @author ZhangYadong
	 * @date 2018年1月31日 下午4:14:53
	 * @param eb
	 * @return void
	 */
	void sendEmail(EmailBean eb);
	
}
