package com.webill.app.com.webill.app.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxPayConfiguration {
    @Value("${WEIXIN_APPID}")
    private String appId;

    @Value("${MCH_ID}")
    private String mchId;

    @Value("${MCH_KEY}")
    private String mchKey;

    @Value("${WEIXIN_SUB_APPID}")
    private String subAppId;

    @Value("${PAY_SUB_MCH_ID}")
    private String subMchId;

    @Value("${PAY_KEY_PATH}")
    private String keyPath;

    @Bean
    public WxPayConfig payConfig() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(this.appId);
        payConfig.setMchId(this.mchId);
        payConfig.setMchKey(this.mchKey);
        payConfig.setSubAppId(this.subAppId);
        payConfig.setSubMchId(this.subMchId);
        payConfig.setKeyPath(this.keyPath);

        return payConfig;
    }

    @Bean
    public WxPayService payService() {
        WxPayService payService = new WxPayServiceImpl();
        payService.setConfig(payConfig());
        return payService;
    }
}
