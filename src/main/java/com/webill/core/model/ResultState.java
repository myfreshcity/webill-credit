package com.webill.core.model;  
  
import java.io.Serializable;  

/**   
 * @ClassName: ResultState   
 * @Description: 微信API返回状态   
 * @author: WangLongFei  
 * @date: 2017年9月20日 上午10:59:43      
 */
public class ResultState implements Serializable {  
  
	private static final long serialVersionUID = 1L;
	
    private int errcode; // 状态  
      
    private String errmsg; //信息  
  
    public int getErrcode() {  
        return errcode;  
    }  
  
    public void setErrcode(int errcode) {  
        this.errcode = errcode;  
    }  
  
    public String getErrmsg() {  
        return errmsg;  
    }  
  
    public void setErrmsg(String errmsg) {  
        this.errmsg = errmsg;  
    }  
}  
