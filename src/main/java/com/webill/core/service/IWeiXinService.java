package com.webill.core.service;

/** 
* @ClassName: WeiXinService 
* @Description: 
* @author ZhangYadong
* @date 2017年11月11日 上午10:00:06 
*/
public interface IWeiXinService {
	
	String urlRedirect(String uri, String url, String code);
}
