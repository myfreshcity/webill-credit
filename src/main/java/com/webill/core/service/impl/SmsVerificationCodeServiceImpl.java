package com.webill.core.service.impl;

import java.util.Random;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.webill.core.model.RedisKeyDto;
import com.webill.core.service.RedisService;
import com.webill.core.service.SmsVerificationCodeService;

public class SmsVerificationCodeServiceImpl implements SmsVerificationCodeService {

    //短信验证码过期时间
    private  static int  EXPIRATIONTIME=3000;
    @Resource
    private RedisService redisService;

    @Transactional
    @Override
    public String sendMessage(String phoneName) {
        String rusult = null;
        // 成为开发者，创建应用后系统自动生成，带星号的地方可以通过阿里大于平台申请
        String code = getRandNum(6);
        RedisKeyDto redisKeyDto=new RedisKeyDto();
        redisKeyDto.setKeys(phoneName);
        redisKeyDto.setValues(code);
        redisService.addRedisData(redisKeyDto,EXPIRATIONTIME);
        return rusult;
    }

    @Override
    public boolean checkIsCorrectCode(String phone, String code) {
        RedisKeyDto redisKeyDto=new RedisKeyDto();
        redisKeyDto.setKeys(phone);
        RedisKeyDto result=redisService.redisGet(redisKeyDto);
        if (result!=null&&result.getValues().equals(String.valueOf(code)))
            return true;
        return false;
    }
    /***
     * 获取6位验证码
     * @param charCount
     * @return
     */
    public String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }

    public int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }

    public RedisService getRedisService() {
        return redisService;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}