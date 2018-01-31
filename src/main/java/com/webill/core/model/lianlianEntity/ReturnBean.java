package com.webill.core.model.lianlianEntity;
/** 
* @ClassName: ReturnBean 
* @Description: 返回参数实体类
* @author ZhangYadong
* @date 2017年11月7日 下午8:32:02 
*/
public class ReturnBean {
	
    private String ret_code;
    private String ret_msg;
	public String getRet_code() {
		return ret_code;
	}
	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}
	public String getRet_msg() {
		return ret_msg;
	}
	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}
	public ReturnBean(String ret_code, String ret_msg) {
		super();
		this.ret_code = ret_code;
		this.ret_msg = ret_msg;
	}
	public ReturnBean() {
		super();
	}
	
}
