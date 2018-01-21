package com.webill.core.model.juxinli;

import java.io.Serializable;

/**
 * @ClassName: JXLResetPasswordRespData
 * @Description:
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:55:35
 */
public class JXLResetPasswordRespData implements Serializable {
	private static final long serialVersionUID = -208976264407617042L;
	private String type; // CONTROL 控制类型的响应结果
	private String process_code; // 如下说明
	private boolean finish; // 所有的采集流程是否结束
	private String content; // 提示内容
	private String nextDatasource; // 下一个需要采集的数据源
	private JXLReqMsgTpl req_msg_tpl;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProcess_code() {
		return process_code;
	}

	public void setProcess_code(String process_code) {
		this.process_code = process_code;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public String getNextDatasource() {
		return nextDatasource;
	}

	public void setNextDatasource(String nextDatasource) {
		this.nextDatasource = nextDatasource;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public JXLReqMsgTpl getReq_msg_tpl() {
		return req_msg_tpl;
	}

	public void setReq_msg_tpl(JXLReqMsgTpl req_msg_tpl) {
		this.req_msg_tpl = req_msg_tpl;
	}
}
