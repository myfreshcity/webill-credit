package com.webill.core.service;

import com.webill.core.model.juxinli.JXLCollectReq;
import com.webill.core.model.juxinli.JXLResp;
import com.webill.core.model.juxinli.JXLSubmitFormReq;
import com.webill.core.model.juxinli.JXLSubmitFormResp;

/** 
 * @ClassName: IJuxinliService 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:16:07 
 */
public interface IJuxinliService{

	/** 
	 * @Title: submitForm 
	 * @Description: 聚信立提交申请表单获取回执信息
	 * @author ZhangYadong
	 * @date 2018年1月19日 上午10:34:23
	 * @param req
	 * @return
	 * @return JXLSubmitFormResp
	 */
	JXLSubmitFormResp submitForm(JXLSubmitFormReq req);

	/** 
	 * @Title: collect 
	 * @Description: 聚信立提交数据采集请求,根据返回的processCode，可能会请求多次
	 * @author ZhangYadong
	 * @date 2018年1月19日 上午10:34:26
	 * @param req
	 * @return
	 * @return JXLResp
	 */
	JXLResp collect(JXLCollectReq req);

}
