package com.webill.core.model;  
  
/**   
 * @ClassName: TextMessage   
 * @Description: 文本消息消息体 
 * @author: WangLongFei  
 * @date: 2017年8月15日 下午3:11:07      
 */
public class TextMessage extends BaseMessage {  
    // 回复的消息内容   
    private String Content;  

    public String getContent() {  
        return Content;  
    }  

    public void setContent(String content) {  
        Content = content;  
    }  
}
