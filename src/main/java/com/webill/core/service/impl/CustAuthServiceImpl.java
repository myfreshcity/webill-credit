package com.webill.core.service.impl;

import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.webill.core.model.CarCardApply;
import com.webill.core.service.ICustAuthService;
import com.webill.core.service.mapper.CarCardApplyMapper;
import com.webill.framework.service.impl.SuperServiceImpl;


public class CustAuthServiceImpl extends SuperServiceImpl<CarCardApplyMapper, CarCardApply> implements ICustAuthService {

	@Override
	public void fourElementAuth(String realName, String idCard, String mobile, String bankCard) {
		JSONObject telecom = null;
		
		//key为唯一标识
		String key = "28f4e9231f5fb951cf971edc070293b1";
		
		String apiAddress = "http://v.apistore.cn/api/bank/v4?" + "key=" + key 
				+ "&bankcard=" + bankCard + "&cardNo=" + idCard 
				+ "&realName="	+ realName + "&Mobile=" + mobile + "&information=1";
		
		String response = new RestTemplate().getForObject(apiAddress, String.class);
		
		telecom = JSONObject.parseObject(response);
		String errorCode = telecom.getString("error_code");
		if("0".equals(errorCode)){
			//TODO 认证成功，把相关数据插入数据库
		} else{
			//TODO 认证失败，把错误信息返回
		}
	}

}
