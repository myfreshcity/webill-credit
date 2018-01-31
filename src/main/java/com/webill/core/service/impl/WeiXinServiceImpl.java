package com.webill.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webill.app.SystemProperty;
import com.webill.core.service.IWeiXinService;

/** 
* @ClassName: WeiXinService 
* @Description: 
* @author ZhangYadong
* @date 2017年11月11日 上午10:00:06 
*/
@Service
public class WeiXinServiceImpl implements IWeiXinService{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected SystemProperty constPro; 
	 
	@Override
	public String urlRedirect(String uri, String url, String code) {
		String newUri = null;
		if (constPro.WEIXIN_ENV) {
			try {
				newUri = URLEncoder.encode(constPro.DOMAIN_URL + "/#" + uri, "UTF-8");
				newUri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + constPro.WEIXIN_APPID
						+ "&redirect_uri=" + newUri
						+ "&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect";
			} catch (UnsupportedEncodingException e) {
				logger.error("encode url fail:" + uri, e);
			}
		} else {
			String forwardUrl = url;
            if(org.apache.commons.lang3.StringUtils.isBlank(forwardUrl)){
                forwardUrl = constPro.DOMAIN_URL;
            }
            if(org.apache.commons.lang3.StringUtils.isBlank(code)){
                code = "CODE";
            }
			newUri = forwardUrl + "/?code="+code+"&status=123#" + uri;
		}
		return newUri;
	}
	
	
}
