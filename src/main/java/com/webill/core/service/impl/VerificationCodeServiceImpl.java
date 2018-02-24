package com.webill.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.webill.app.SystemProperty;
import com.webill.app.util.HttpUtils;
import com.webill.app.util.VerificationCodeUtil;
import com.webill.core.Constant;
import com.webill.core.model.RedisKeyDto;
import com.webill.core.service.IVerificationCodeService;
import com.webill.core.service.RedisService;

@Service
public class VerificationCodeServiceImpl implements IVerificationCodeService{

	private static Log logger = LogFactory.getLog(VerificationCodeServiceImpl.class);
	
    @Autowired
    protected SystemProperty constPro;
    
	@Autowired
	private RedisService redisService;
	
	@Override
	public Integer sendVerficationCode(String mobile) {
		String verifyCode = VerificationCodeUtil.generateTextCode(VerificationCodeUtil.TYPE_NUM_ONLY, 6, null);
		logger.info("**** mobile and verificationCode :"+mobile+"-------->"+verifyCode);
		String url = constPro.MMH_NET_URL + "/sendSms";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("phoneNo", mobile);
		params.put("content", "您的验证码是"+verifyCode+"【葆鼎网络】");
		String resultStr ="";
		Integer returnFlag = -1;
		try {
			resultStr = HttpUtils.httpPostRequest(url, params);
			if(resultStr.contains("\"msg\": \"ok\"")){
				JSONObject resultarr = JSONObject.parseObject(resultStr);
				String msg = resultarr.getString("msg");
				if(msg.equals("ok")){
					RedisKeyDto redisKeyDto = new RedisKeyDto();
					redisKeyDto.setKeys(mobile);
					redisKeyDto.setValues(verifyCode);
					redisService.addRedisData(redisKeyDto, 60);
					returnFlag = Constant.STATUS_SUCCESS;
				}else{
					returnFlag = Constant.STATUS_FAIL;
				}
			}else if(resultStr.contains("ValueError: No JSON object could be decoded")){
				returnFlag = Constant.STATUS_FAIL;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("**** sendVerificationCode resultStr:"+resultStr,e);
		}
		return returnFlag;
	}

}
