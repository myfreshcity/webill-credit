package com.webill.core.model;  
  

/**   
 * @ClassName: TemplateMsgResult   
 * @Description: 模板消息 返回的结果   
 * @author: WangLongFei  
 * @date: 2017年9月20日 上午11:00:00      
 */
public class TemplateMsgResult extends ResultState {
	
    private static final long serialVersionUID = 1L;  
  
    private String msgid; // 消息id(发送模板消息)  
  
    public String getMsgid() {  
        return msgid;  
    }  
  
    public void setMsgid(String msgid) {  
        this.msgid = msgid;  
    }  
} 
