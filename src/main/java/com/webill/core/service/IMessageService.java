package com.webill.core.service;


import com.webill.core.model.TemplateMsgResult;
import com.webill.core.model.WechatTemplateMsg;

public interface IMessageService {
	
	/**   
	 * @Title: sendMessage   
	 * @Description: 微信公共账号发送给账号  
	 * @author: WangLongFei  
	 * @date: 2017年8月16日 下午6:52:51   
	 * @param content
	 *            文本内容
	 * 
	 * @param toUser
	 *            微信用户
	 * @return  
	 * @return: String  
	 */
	String sendMessage(String touser,String content);
	
	/**   
	 * @Title: sendTemplateMessage   
	 * @Description: 微信公共账号通过模板消息推送给用户    
	 * @author: WangLongFei  
	 * @date: 2017年9月19日 下午3:31:07   
	 * @param map
	 * @return  
	 * @return: TemplateMsgResult  
	 */
	TemplateMsgResult sendTemplateMessage(WechatTemplateMsg wtm);
	
}
