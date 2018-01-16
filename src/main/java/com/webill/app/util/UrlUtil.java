package com.webill.app.util;

import java.net.URL;

import org.apache.log4j.Logger;

/**   
 * @ClassName: UrlUtil   
 * @Description: url验证工具类  
 * @author: WangLongFei  
 * @date: 2017年9月21日 下午4:07:17      
 */
public class UrlUtil {
	private static final Logger logger = Logger.getLogger(UrlUtil.class);

	private UrlUtil() {

	}

	/**
	 * 是否是网址
	 *
	 * @param urlstr
	 *            要验证的网址
	 * @return true or false
	 */
	public static boolean isUrl(String urlstr) {
		boolean f;
		URL url;
		try {
			url = new URL(urlstr);
			url.openStream();
			f = true;
		} catch (Exception e) {
			logger.info("要验证的内容不是网址！", e);
			f = false;
		}
		return f;
	}
}
