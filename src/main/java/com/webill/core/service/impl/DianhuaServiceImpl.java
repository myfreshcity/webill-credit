package com.webill.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.webill.app.util.HttpUtils;
import com.webill.core.service.IDianhuaService;

/** 
 * @ClassName: DianhuaServiceImpl 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月22日 上午11:43:48 
 */
public class DianhuaServiceImpl implements IDianhuaService{

	/**
	 * 获取接口调用凭证
	 */
	/*public String getAccessToken() {
		if (accessToken != null) {
			return accessToken;
		}
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("org_name", constPro.JXL_ACCOUNT);
		reqMap.put("client_secret", constPro.JXL_SECRET);
		reqMap.put("hours", "per"); //永久性可用
		
		try {
			String resJson = HttpUtils.httpGetRequest(constPro.JXL_REQ_URL+"/api/v2/access_report_token", reqMap);
			if (resJson != null) {
				JSONObject json = JSONObject.parseObject(resJson);
				int code = json.getIntValue("code");
				if (code != 200) {
					throw new RuntimeException("获取access-token返回code:" + code);
				}
				accessToken = json.getString("access_token");
			} else {
				throw new RuntimeException("获取access-token请求聚信立失败!");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return accessToken;
	} */
}
