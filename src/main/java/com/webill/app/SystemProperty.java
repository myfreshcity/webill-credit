package com.webill.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemProperty {

    @Value("${MMH_NET_URL}")
    public String MMH_NET_URL;

    @Value("${DOMAIN_STATIC_URL}")
    public String DOMAIN_STATIC_URL;

    @Value("${DOMAIN_URL}")
    public String DOMAIN_URL;

    @Value("${COOKIE_DOMAIN}")
    public String COOKIE_DOMAIN;

    @Value("${IS_PRODUCT}")
    public boolean IS_PRODUCT;

    @Value("${WEIXIN_APPID}")
    public String WEIXIN_APPID;

    @Value("${WEIXIN_SECRET}")
    public String WEIXIN_SECRET;

    @Value("${WEIXIN_TOKEN}")
    public String WEIXIN_TOKEN;

    @Value("${WEIXIN_AESKEY}")
    public String WEIXIN_AESKEY;

    @Value("${APK_URL}")
    public String APK_URL;

    @Value("${APK_V_NO}")
    public String APK_V_NO;

    @Value("${APK_MSG}")
    public String APK_MSG;

    @Value("${APK_IS_FORCE}")
    public String APK_IS_FORCE;

    @Value("${APK_MD5_CD}")
    public String APK_MD5_CD;
    
    @Value("${WEIXIN_SIGNTYPE}")
    public String WEIXIN_SIGNTYPE;
    
    @Value("${MCH_ID}")
    public String MCH_ID;
    
    @Value("${MCH_KEY}")
    public String MCH_KEY;
    
    @Value("${TRADE_TYPE}")
    public String TRADE_TYPE;
    
    @Value("${SERVER_FEE}")
    public String SERVER_FEE;
    
    @Value("${DEFAULT_ORDER_ID}")
    public String DEFAULT_ORDER_ID;
    
    @Value("${CITY}")
    public String CITY;
    
    @Value("${INSURER_COM}")
    public String INSURER_COM;

    @Value("${DISCOUNT_VALUE}")
    public Double DISCOUNT_VALUE;

    @Value("${FILE_SAVE_PATH}")
    public String FILE_SAVE_PATH;
    
    @Value("${DISCOUNT_MONEY}")
    public String DISCOUNT_MONEY;
    
    @Value("${MSG_URL}")
    public String MSG_URL;
    
    @Value("${BASE_WEIXIN_URL}")
    public String BASE_WEIXIN_URL;
    
    @Value("${CREATE_ORDER_URL}")
    public String CREATE_ORDER_URL;
    
    @Value("${DEFAULT_PREMIUM_YEAR_NUM}")
    public int DEFAULT_PREMIUM_YEAR_NUM;
    
    /**  
     * @fieldName: FILE_SHOW_URL  
     * @fieldType: String  
     * @author: WangLongFei  
     * @date: 2017年9月20日 下午5:32:30   
     * @Description: 页面文件展示路径
     */
    @Value("${FILE_SHOW_URL}")
    public String FILE_SHOW_URL;
    /**  
     * @fieldName: PRM_END_DAY  
     * @fieldType: String  
     * @author: WangLongFei  
     * @date: 2017年9月19日 下午3:13:28   
     * @Description: 车险到期天数  
     */
    @Value("${PRM_END_DAY}")
    public String PRM_END_DAY;
    
    /**  
     * @fieldName: PRM_EXPIRE_TEMPLATE_ID  
     * @fieldType: String  
     * @author: WangLongFei  
     * @date: 2017年9月20日 下午5:32:30   
     * @Description: 车险到期提醒模板id  
     */
    @Value("${PRM_EXPIRE_TEMPLATE_ID}")
    public String PRM_EXPIRE_TEMPLATE_ID;
    
    /**  
     * @fieldName: PRM_PRICE_TEMPLATE_ID  
     * @fieldName: PRM_EXPIRE_TEMPLATE_ID  
     * @fieldType: String  
     * @author: WangLongFei  
     * @date: 2017年9月20日 下午5:32:30   
     * @Description: 车险报价模板id  
     */
    @Value("${PRM_PRICE_TEMPLATE_ID}")
    public String PRM_PRICE_TEMPLATE_ID;
    
    /**  
     * @fieldName: PRM_ISSUE_TEMPLATE_ID  
     * @fieldName: PRM_EXPIRE_TEMPLATE_ID  
     * @fieldType: String  
     * @author: WangLongFei  
     * @date: 2017年9月20日 下午5:32:30   
     * @Description: 车险出单完成模板id  
     */
    @Value("${PRM_ISSUE_TEMPLATE_ID}")
    public String PRM_ISSUE_TEMPLATE_ID;
    
    /**  
     * @fieldName: ILLEGAL_PAY_TEMPLATE_ID  
     * @fieldName: PRM_EXPIRE_TEMPLATE_ID  
     * @fieldType: String  
     * @author: WangLongFei  
     * @date: 2017年9月20日 下午5:32:30   
     * @Description: 违章缴费完成模板id  
     */
    @Value("${ILLEGAL_PAY_TEMPLATE_ID}")
    public String ILLEGAL_PAY_TEMPLATE_ID;
    
    /**  
     * @fieldName: PRM_EXPIRE_TEMPLATE_ID  
     * @fieldType: String  
     * @author: WangLongFei  
     * @date: 2017年9月20日 下午5:32:30   
     * @Description: 再次推送车险到期，天数 
     */
    @Value("${PRM_PUSH_INTERVAL_DAY}")
    public String PRM_PUSH_INTERVAL_DAY;
    
    /**  
     * @author ZhangYadong
     * @date 2017年11月11日 下午2:48:49
     * @Description: 慧泽综合险
     */
    @Value("${HUIZE_PARTNERID}")
    public String HUIZE_PARTNERID;
    
    /**
     * @fieldName: HUIZE_KEY
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月30日 下午2:24:46 
     * @Description: 开发者账号(接口调试使用固定账号)
     */
    @Value("${HUIZE_KEY}")
    public String HUIZE_KEY;
    
    /**
     * @fieldName: HUIZE_TEST_KEY
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月30日 下午2:25:35 
     * @Description: 测试密钥 key
     */
    @Value("${HUIZE_TEST_KEY}")
    public String HUIZE_TEST_KEY;
    
    /**
     * @fieldName: HUIZE_TEST_REQUEST_URL
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月30日 下午2:25:37 
     * @Description: 测试环境请求URL
     */
    @Value("${HUIZE_TEST_REQUEST_URL}")
    public String HUIZE_TEST_REQUEST_URL;
    
    /**
     * @fieldName: PRODUCT_FILE_SAVE_PATH
     * @fieldType: String
     * @author ZhangYadong
     * @date 2017年12月26日 下午3:17:01
     * @Description: 商品图片上传路径
     */ 
    @Value("${PRODUCT_FILE_SAVE_PATH}")
    public String PRODUCT_FILE_SAVE_PATH;
    
    /**
     * @fieldName: CATEGORY_FILE_SAVE_PATH
     * @fieldType: String
     * @author ZhangYadong
     * @date 2017年12月26日 下午3:17:22
     * @Description: 商品分类图片上传路径
     */ 
    @Value("${CATEGORY_FILE_SAVE_PATH}")
    public String CATEGORY_FILE_SAVE_PATH;
    
    /**
     * @fieldName: PRODUCT_FILE_ACCESS_PATH
     * @fieldType: String
     * @author ZhangYadong
     * @date 2017年12月26日 下午4:16:33
     * @Description: 商品图片访问路径
     */ 
    @Value("${PRODUCT_FILE_ACCESS_PATH}")
    public String PRODUCT_FILE_ACCESS_PATH;
    
    /**
     * @fieldName: CATEGORY_FILE_ACCESS_PATH
     * @fieldType: String
     * @author ZhangYadong
     * @date 2017年12月26日 下午4:16:39
     * @Description: 商品分类图片访问路径
     */ 
    @Value("${CATEGORY_FILE_ACCESS_PATH}")
    public String CATEGORY_FILE_ACCESS_PATH;
    
    /**
     * @fieldName: AREA_JSON
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2018年1月4日 下午4:54:54 
     * @Description: 省市县三级联动
     */
    @Value("${AREA_JSON}")
    public String AREA_JSON;
    
    /**
     * @fieldType: String
     * @author ZhangYadong
     * @date 2018年1月18日 上午11:09:34
     * @Description: 聚信立配置
     */ 
    @Value("${JXL_ACCOUNT}")
    public String JXL_ACCOUNT;
    @Value("${JXL_SECRET}")
    public String JXL_SECRET;
    @Value("${JXL_REQ_URL}")
    public String JXL_REQ_URL;
}
