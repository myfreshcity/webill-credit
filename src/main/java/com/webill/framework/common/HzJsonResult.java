package com.webill.framework.common;

public class HzJsonResult {
    /**
     * @fieldName: state
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月30日 下午4:41:11 
     * @Description: 接收状态 true：成功 false：失败
     */
    private boolean state;
    /**
     * @fieldName: failMsg
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月30日 下午4:40:58 
     * @Description: 失败原因
     */
    private String failMsg;
    
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public String getFailMsg() {
		return failMsg;
	}
	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}

}
