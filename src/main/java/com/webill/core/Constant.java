package com.webill.core;


/**
 * Created by david on 16/10/22.
 */
public interface Constant {
	/**  
	 * @fieldName: PUBLIC_ORDER_USERID  
	 * @fieldType: Integer  
	 * @author: WangLongFei  
	 * @date: 2017年7月13日 下午2:57:08   
	 * @Description: 共有订单userId=0  
	 */
	final public static Integer PUBLIC_ORDER_USERID = 0;
	
	/**  
	 * @fieldName: ORDER_NO_LENGTH  
	 * @fieldType: Integer  
	 * @author: WangLongFei  
	 * @date: 2017年9月7日 上午10:43:49   
	 * @Description: 订单号长度  
	 */
	final public static Integer ORDER_NO_LENGTH = 25;
	
    final public static Integer DEL_STATUS = -1;
    final public static Integer NORMAL_STATUS = 0;
    final public static Integer HISTORY_STATUS = 1;
    final public static Integer SHOW_STATUS = 2;

    final public static int STATUS_SUCCESS = 1;// 操作成功
    final public static int STATUS_FAIL = 0;// 操作失败
    
    //门店状态
    final public static Integer ORGANIZATION_DEL_STATUS = -1;
    final public static Integer ORGANIZATION_NORMAL_STATUS = 0;

    //标签种类类型
    final public static int BASIC_TAG_CAT_TYPE = 1;
    final public static int APPEARANCE_TAG_CAT_TYPE = 2;
    final public static int FUNCTION_TAG_CAT_TYPE = 3;

    final public static String CACHE_PHONE_TYPE = "type";
    final public static String CACHE_TAGS = "tags";

    final public static Integer ORG_TYPE_IN_T = 0; //门店
    final public static Integer ORG_TYPE_DIS_T = 1; //运营中心
    final public static Integer ORG_TYPE_OUT_T = 2; //渠道商

    //订单状态
    final public static Integer PHONE_GATHER_DONE = 1000; //已收机(未发货)
    final public static Integer PHONE_INVALID = 1100; //已失效
    final public static Integer PHONE_SEND_DONE = 2000; //已发货
    final public static Integer PHONE_ARRIVAL_DONE = 3000; //已到货

    final public static Integer PHONE_CHECK_DONE = 4000; //已验机
    final public static Integer PHONE_NEGO_DOING = 4100; //议价中
    final public static Integer PHONE_APPLY_WITHDRAW = 4110; //议价失败(待退货)

    final public static Integer PHONE_INBOUND_DONE = 5000; //已入库(回收成功)

    final public static Integer PHONE_COMPLETE_DONE = 9000; //正常结束。已完成。
    final public static Integer PHONE_CLOSE_DONE = 9100; //非正常结束。已关闭。
    final public static Integer PHONE_WITHDRAW_DONE = 9200; //退货结束(已退货)


    //更新订单状态错误码

    final public static String LOGIN_USER = "loginUser";

    final public static Integer LEVEL_CATEGORY_S = 0; //梯队
    final public static Integer LEVEL_CATEGORY_G = 1; //梯队组合
    
    //投保订单标识
    final public static Integer PREMIUM_NO_ORDER = 3;//无订单
    
    //----------------------------------------------------------添加车辆，标识-----------------------------begin------------
    /**  
     * @fieldName: CAR_ADD_SUCCESS  
     * @fieldType: String  
     * @author: WangLongFei  
     * @date: 2017年10月24日 下午1:43:57   
     * @Description: 车辆添加成功  
     */
    final public static String CAR_ADD_SUCCESS = "CAR_ADD_SUCCESS";

    /**  
     * @fieldName: CAR_ADD_FAIL  
     * @fieldType: String  
     * @author: WangLongFei  
     * @date: 2017年10月24日 下午1:44:10   
     * @Description: 车辆添加失败  
     */
    final public static String CAR_ADD_FAIL = "CAR_ADD_FAIL";
    
    /**  
     * @fieldName: CAR_ADD_EXIST  
     * @fieldType: String  
     * @author: WangLongFei  
     * @date: 2017年10月24日 下午1:44:17   
     * @Description: 车辆已存在,请勿重复添加  
     */
    final public static String CAR_ADD_EXIST = "CAR_ADD_EXIST";
    
    /**  
     * @fieldName: CAR_ADD_NOT_EXIST  
     * @fieldType: String  
     * @author: WangLongFei  
     * @date: 2017年10月24日 下午1:44:31   
     * @Description: 车辆不存在  
     */
    final public static String CAR_ADD_NOT_EXIST = "CAR_ADD_NOT_EXIST";
    
    /**  
     * @fieldName: CAR_ADD_OWNERNAME_NOT_SAME  
     * @fieldType: String  
     * @author: WangLongFei  
     * @date: 2017年10月24日 下午1:45:04   
     * @Description: 车主姓名不符 
     */
    final public static String CAR_ADD_OWNERNAME_NOT_SAME = "CAR_ADD_OWNERNAME_NOT_SAME";
    //----------------------------------------------------------添加车辆，标识-----------------------------end------------
    
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_USELESS  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月12日 上午11:41:00   
     * @Description: 已废弃
     */
    final public static Integer PREMIUM_ORDER_STATUS_USELESS = -1;//已废弃
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_CREATED  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月12日 上午11:41:43   
     * @Description: 已创建(草稿) 
     */
    final public static Integer PREMIUM_ORDER_STATUS_CREATED = 2000;//已创建(草稿)
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_NOPRICE  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月13日 上午10:06:51   
     * @Description: 无报价
     */
    final public static Integer PREMIUM_ORDER_STATUS_NOPRICE = 2100;//无报价
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_HAVEPRICE  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月12日 下午2:09:20   
     * @Description: 有报价  
     */
    final public static Integer PREMIUM_ORDER_STATUS_HAVEPRICE = 2200;//有报价
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_APPOINTMENT  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月12日 上午11:41:50   
     * @Description: 预约  
     */
    final public static Integer PREMIUM_ORDER_STATUS_APPOINTMENT = 3000;//预约
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_ALREADYPUSH  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月12日 上午11:41:56   
     * @Description: 已推送  
     */
    final public static Integer PREMIUM_ORDER_STATUS_ALREADYPUSH = 4000;//已推送
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_ALREADYSEE  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月12日 上午11:42:03   
     * @Description: 已查看  
     */
    final public static Integer PREMIUM_ORDER_STATUS_ALREADYSEE = 4100;//已查看
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_FINISHINFO  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月12日 上午11:42:13   
     * @Description: 完成投保信息
     */
    final public static Integer PREMIUM_ORDER_STATUS_FINISHINFO = 4200;//完成投保信息
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_ASKPRICEING  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月12日 上午11:42:22   
     * @Description: 询价中  
     */
    final public static Integer PREMIUM_ORDER_STATUS_ASKPRICEING = 5000;//询价中
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_ALREADYCONFIRM  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月13日 上午10:11:33   
     * @Description: 已确认  
     */
    final public static Integer PREMIUM_ORDER_STATUS_ALREADYCONFIRM = 4300;//已确认
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_PAYWAITTING  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月12日 下午2:22:08   
     * @Description: 待支付  
     */
    final public static Integer PREMIUM_ORDER_STATUS_PAYWAITTING = 4400;//待支付
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_PAYSUCCESS  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月12日 上午11:42:29   
     * @Description: 支付成功  
     */
    final public static Integer PREMIUM_ORDER_STATUS_PAYSUCCESS = 7000;//支付成功
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_PAYFAIL  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月12日 上午11:42:36   
     * @Description: 支付失败  
     */
    final public static Integer PREMIUM_ORDER_STATUS_PAYFAIL = 7100;//支付失败
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_OVERDUE  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月12日 上午11:42:49   
     * @Description: 已过期 
     */
    final public static Integer PREMIUM_ORDER_STATUS_OVERDUE = 8000;//已过期
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_COMPLETED  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月12日 上午11:42:42   
     * @Description: 已完成  
     */
    final public static Integer PREMIUM_ORDER_STATUS_COMPLETED = 9000;//已完成
    /**  
     * @fieldName: PREMIUM_ORDER_STATUS_DELETED  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月13日 上午10:16:03   
     * @Description: 已删除  
     */
    final public static Integer PREMIUM_ORDER_STATUS_DELETED = 9100;//已删除
    
    final public static Integer PREMIUM_ORDER_HAVE = 11;//有订单
    final public static Integer PREMIUM_ORDER_DURING_NOORDER_HAVEPRICE = 22;//保期内，无订单，有报价
    final public static Integer PREMIUM_ORDER_DURING_NOORDER_NOPRICE = 33;//保期内，无订单，无报价
    final public static Integer PREMIUM_ORDER_IS_OR_NOT_DURING = 44;//不确定是否保期内
    final public static Integer PREMIUM_ORDER_OUTOF_NOORDER_NOPRICE = 55;//保期外，无订单，无法报价
    
    
    /**  
     * @fieldName: PREMIUM_ORDER_DETAIL_HAVENO_PRICE  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月18日 上午11:21:03   
     * @Description: 投保方案价不完整  
     */
    final public static Integer PREMIUM_ORDER_DETAIL_HAVENO_PRICE = 2;
    
    final public static Integer PREMIUM_CHANGE_ORDER_COMPANYNAME = 1;//投保公司
    final public static Integer PREMIUM_CHANGE_ORDER_INSURER = 2;//投保人信息
    final public static Integer PREMIUM_CHANGE_ORDER_DETAIL = 3;//投保方案
    
    final public static Integer PREMIUM_ASK_PRICE_SUCCESS = 200;//询价成功
    final public static Integer PREMIUM_ASK_PRICE_FAILE = 500;//询价失败
    
    final public static Integer PREMIUM_DURING_INSURE = 1;//保期内
    final public static Integer PREMIUM_NOSURE_INSURE = 2;//不确定保期内外
    final public static Integer PREMIUM_OUTOF_INSURE = 3;//保期外
    
    final public static int CHANGE_USER_CONTACT_SUCCESS = 1;// 操作usercontact成功
    final public static int CHANGE_USER_CONTACT_FAILE = 0;// 操作usercontact失败
    /**  
     * @fieldName: USER_CONTACT_IS_DEFAULT  
     * @fieldType: int  
     * @author: WangLongFei  
     * @date: 2017年8月29日 上午10:08:48   
     * @Description: 默认  
     */
    final public static int USER_CONTACT_IS_DEFAULT = 1;// usercontact是默认
    /**  
     * @fieldName: USER_CONTACT_NOT_DEFAULT  
     * @fieldType: int  
     * @author: WangLongFei  
     * @date: 2017年8月29日 上午10:09:01   
     * @Description: 非默认  
     */
    final public static int USER_CONTACT_NOT_DEFAULT = 0;// 操作usercontact非默认
    /**  
     * @fieldName: USER_CONTACT_NORMAL_STATUS  
     * @fieldType: int  
     * @author: WangLongFei  
     * @date: 2017年8月29日 上午10:09:19   
     * @Description: 正常  
     */
    final public static int USER_CONTACT_NORMAL_STATUS = 0;// usercontact状态正常
    /**  
     * @fieldName: USER_CONTACT_DEL_STATUS  
     * @fieldType: int  
     * @author: WangLongFei  
     * @date: 2017年8月29日 上午10:09:32   
     * @Description: 作废  
     */
    final public static int USER_CONTACT_DEL_STATUS = -1;// usercontact状态作废
    
    /**  
     * @fieldName: PREMIUM_ORDER_PAY_FLAG  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月18日 下午2:22:26   
     * @Description: 获取订单方式，标识，支付情况时  
     */
    final public static Integer PREMIUM_ORDER_NOPAY_FLAG = 0;
    //---------------------------------------------------------------优惠券规则---------start---------------------------
    /**  
     * @fieldName: PROMO_RULER_STATUS_NEW  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月28日 上午11:33:42   
     * @Description: 优惠券规则状态：新建  
     */
    final public static Integer PROMO_RULER_STATUS_NEW = 0;
    /**  
     * @fieldName: PROMO_RULER_STATUS_ENABLE  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月28日 上午11:34:08   
     * @Description: 优惠券规则状态：启用
     */
    final public static Integer PROMO_RULER_STATUS_ENABLE = 1;
    /**  
     * @fieldName: PROMO_RULER_STATUS_DISABLED  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月28日 上午11:34:16   
     * @Description: 优惠券规则状态：禁用
     */
    final public static Integer PROMO_RULER_STATUS_DISABLED = 2;
    
    /**  
     * @fieldName: PROMO_RULER_LEAVEL_COMMON  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年7月31日 上午10:24:41   
     * @Description: 用户领用资格  
     */
    final public static Integer PROMO_RULER_LEAVEL_COMMON = 0;
    
  //---------------------------------------------------------------优惠券规则---------end--------------------------
    //状态：新建-100；提交审核-200；审核失败-300；审核通过未发放-400；发放中-500；停止发放-600；发放结束-700；作废-800
   //---------------------------------------------------------------优惠券---------end---------------------------
    /**  
     * @fieldName: COUPON_STATUS_NEW  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年8月2日 上午11:55:16   
     * @Description: 新建  
     */
    final public static Integer COUPON_STATUS_NEW = 100;
    /**  
     * @fieldName: COUPON_STATUS_COMIT_CHECK  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年8月2日 上午11:59:31   
     * @Description: 提交审核  
     */
    final public static Integer COUPON_STATUS_COMIT_CHECK = 200;
    /**  
     * @fieldName: COUPON_STATUS_CHECK_FAIL  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年8月2日 上午11:59:44   
     * @Description: 审核失败  
     */
    final public static Integer COUPON_STATUS_CHECK_FAIL = 300;
    /**  
     * @fieldName: COUPON_STATUS_CHECK_PASS_NO_SEND  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年8月2日 上午11:59:50   
     * @Description: 审核通过未发放  
     */
    final public static Integer COUPON_STATUS_CHECK_PASS_NO_SEND = 400;
    /**  
     * @fieldName: COUPON_STATUS_SENDING  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年8月2日 下午12:00:11   
     * @Description: 发放中  
     */
    final public static Integer COUPON_STATUS_SENDING = 500;
    /**  
     * @fieldName: COUPON_STATUS_STOP_SEND  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年8月2日 下午12:00:24   
     * @Description: 停止发放  
     */
    final public static Integer COUPON_STATUS_STOP_SEND = 600;
    /**  
     * @fieldName: COUPON_STATUS_SEND_END  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年8月2日 下午12:00:37   
     * @Description: 发放结束  
     */
    final public static Integer COUPON_STATUS_SEND_END = 700;
    /**  
     * @fieldName: COUPON_STATUS_DELETE  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年8月2日 下午12:00:41   
     * @Description: 作废  
     */
    final public static Integer COUPON_STATUS_DELETE = 800;
//    状态 0-新建 1-未使用 2-已使用 3-失效
    /**  
     * @fieldName: USER_COUPON_STATUS_NEW  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年8月2日 下午2:47:23   
     * @Description: 新建  
     */
    final public static Integer USER_COUPON_STATUS_NEW = 0;
    /**  
     * @fieldName: USER_COUPON_STATUS_UN_USE  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年8月2日 下午2:47:31   
     * @Description: 未使用  
     */
    final public static Integer USER_COUPON_STATUS_UN_USE = 1;
    /**  
     * @fieldName: USER_COUPON_STATUS_ALREADY_USE  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年8月2日 下午2:47:39   
     * @Description: 已使用  
     */
    final public static Integer USER_COUPON_STATUS_ALREADY_USE = 2;
    /**  
     * @fieldName: USER_COUPON_STATUS_DELETE  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年8月2日 下午2:47:48   
     * @Description: 失效  
     */
    final public static Integer USER_COUPON_STATUS_DELETE = 3;
    
    /**  
     * @fieldName: PREMIUM_ORDER_PAYTYPE_DEFAULT  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年9月30日 上午9:46:06   
     * @Description: 默认支付方式  
     * @date: 2017年10月16日 上午10:58:10   
     * @Description: 默认订单类型  
     */
    final public static Integer PREMIUM_ORDER_PAYTYPE_DEFAULT = 0;
    /**  
     * @fieldName: PREMIUM_ORDER_PAYTYPE_ONLINE  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年9月30日 上午9:44:41   
     * @Description: 线上支付  
     */
    final public static Integer PREMIUM_ORDER_PAYTYPE_ONLINE = 1;
    /**  
     * @fieldName: PREMIUM_ORDER_PAYTYPE_OFFLINE  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年9月30日 上午9:45:04   
     * @Description: 线下支付  
     */
    final public static Integer PREMIUM_ORDER_PAYTYPE_OFFLINE = 2;
    
    /**  
     * @fieldName: PREMIUM_PRICE_BI_SUCCESS_CI_FAIL  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年10月19日 上午11:31:01   
     * @Description: 商业险报价成功，交强险报价失败  
     */
    final public static Integer PREMIUM_PRICE_BI_SUCCESS_CI_FAIL = 300;
    /**  
     * @fieldName: PREMIUM_PRICE_CI_SUCCESS_BI_FAIL  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年10月19日 上午11:31:14   
     * @Description: 交强险和车船税报价成功,商业险报价失败  
     */
    final public static Integer PREMIUM_PRICE_CI_SUCCESS_BI_FAIL = 400;
    /**  
     * @fieldName: PREMIUM_PRICE_ALL_SUCCESS  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年10月19日 上午11:31:46   
     * @Description: 全部报价成功  
     */
    final public static Integer PREMIUM_PRICE_ALL_SUCCESS = 200;
    /**  
     * @fieldName: PREMIUM_PRICE_ALL_FAIL  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年10月19日 上午11:31:57   
     * @Description: 全部报价失败  
     */
    final public static Integer PREMIUM_PRICE_ALL_FAIL = 500;
    /**  
     * @fieldName: PREMIUM_PRICE_NO_DETAIL  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年10月19日 上午11:32:02   
     * @Description: 投保方案为空  
     */
    final public static Integer PREMIUM_PRICE_NO_DETAIL = 600;
    
    /**  
     * @fieldName: LOGIN_SUCCESS  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年10月26日 下午4:46:40   
     * @Description: 登录成功  
     */
    final public static Integer LOGIN_SUCCESS = 200;
    
    /**  
     * @fieldName: LOGIN_NOT_FOUNT_MOBILE  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年10月26日 下午4:46:40   
     * @Description: 未找到该手机号  
     */
    final public static Integer LOGIN_NOT_FOUNT_MOBILE = 302;
    
    /**  
     * @fieldName: LOGIN_VERIFY_CODE_ERROR  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年10月26日 下午4:49:52   
     * @Description: 验证码错误  
     */
    final public static Integer LOGIN_VERIFY_CODE_ERROR = 300;
    
    /**  
     * @fieldName: LOGIN_PWD_ERROR  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年10月26日 下午4:49:52   
     * @Description: 密码错误  
     */
    final public static Integer LOGIN_PWD_ERROR = 301;
    
    /**  
     * @fieldName: LOGIN_VERIFY_CODE_INVALID  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年10月26日 下午4:49:52   
     * @Description: 验证码已失效
     */
    final public static Integer LOGIN_VERIFY_CODE_INVALID = 305;
    
    /**  
     * @fieldName: LOGIN_MOBILE_BEEN_USED  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年10月26日 下午5:28:53   
     * @Description: 手机号已经被关联过微信号    
     */
    final public static Integer LOGIN_MOBILE_BEEN_USED = 400;
    
    /**  
     * @fieldName: LOGIN_MOBILE_IS_NULL  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年10月26日 下午5:28:38   
     * @Description: 手机号为空    
     */
    final public static Integer LOGIN_MOBILE_IS_NULL = 303;
    
    /**  
     * @fieldName: LOGIN_FAIL  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年10月26日 下午4:52:54   
     * @Description: 登录失败  
     */
    final public static Integer LOGIN_FAIL = 500;
    /**  
     * @fieldName: LOGIN_STATUS_YES  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年10月28日 下午3:38:18   
     * @Description: 自动登录  
     */
    final public static Integer LOGIN_STATUS_YES = 1;
    /**  
     * @fieldName: LOGIN_STATUS_NO  
     * @fieldType: Integer  
     * @author: WangLongFei  
     * @date: 2017年10月28日 下午3:38:34   
     * @Description: 非自动登录  
     */
    final public static Integer LOGIN_STATUS_NO = 0;
    //===================================================用户登录==================BEGIN============================
    /**
     * @fieldName: SEND_MSG_CHECK_FLAG
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月22日 下午1:30:30 
     * @Description: 发送验证码的验证类型-注册
     */
    final public static String SEND_MSG_CHECK_FLAG_REGISTER = "register";
    
    /**
     * @fieldName: SEND_MSG_CHECK_FLAG_LOGIN
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月22日 下午1:34:15 
     * @Description: 发送验证码的验证类型-登录
     */
    final public static String SEND_MSG_CHECK_FLAG_LOGIN = "login";
    
    /**
     * @fieldName: SEND_MSG_CHECK_FLAG_CHECKCARD
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年12月25日 上午11:06:20 
     * @Description: 发送验证码的验证类型-身份验证
     */
    final public static String SEND_MSG_CHECK_FLAG_CHECKCARD = "checkCard";
    
    /**
     * @fieldName: SEND_MSG_CHECK_FLAG_UPDATEMOB
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年12月25日 上午11:05:00 
     * @Description: 发送验证码的验证类型-修改手机号
     */
    final public static String SEND_MSG_CHECK_FLAG_UPDATEMOB = "updateMob";
    /**
     * @fieldName: SEND_MSG_CHECK_FLAG_UPDATEPWD
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月22日 下午1:34:01 
     * @Description: 发送验证码的验证类型-修改密码
     */
    final public static String SEND_MSG_CHECK_FLAG_UPDATEPWD = "updatePwd";
    //===================================================用户登录==================END============================
    
    //===================================================订单==================BEGIN============================
    
    /**
     * @fieldName: APPLICANT_TYPE_PERSONAL
     * @fieldType: Integer
     * @author: WangLongFei
     * @date: 2018年1月4日 上午9:48:54 
     * @Description: 投保人类型-个人
     */
    final public static Integer APPLICANT_TYPE_PERSONAL = 0;
    /**
     * @fieldName: APPLICANT_TYPE_COMPANY
     * @fieldType: Integer
     * @author: WangLongFei
     * @date: 2018年1月4日 上午9:48:29 
     * @Description: 投保人类型-公司
     */
    final public static Integer APPLICANT_TYPE_COMPANY = 1;
    /**
     * @fieldName: ORDER_STATUS_WAIT_PAY
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月22日 下午1:34:01 
     * @Description: 订单状态-待付款
     */
    final public static Integer ORDER_STATUS_WAIT_PAY = 10;
    /**
     * @fieldName: ORDER_STATUS_WAIT_PAY
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月22日 下午1:34:01 
     * @Description: 订单状态-已支付
     */
    final public static Integer ORDER_STATUS_ALREADY_PAY = 20;
    /**
     * @fieldName: ORDER_STATUS_WAIT_PAY
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月22日 下午1:34:01 
     * @Description: 订单状态-出单中（已承保）
     */
    final public static Integer ORDER_STATUS_ISSUING = 30;
    /**
     * @fieldName: ORDER_STATUS_WAIT_PAY
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月22日 下午1:34:01 
     * @Description: 订单状态-已出单
     */
    final public static Integer ORDER_STATUS_ALREADY_ISSUE = 40;
    /**
     * @fieldName: ORDER_STATUS_WAIT_PAY
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月22日 下午1:34:01 
     * @Description: 订单状态-退保中
     */
    final public static Integer ORDER_STATUS_SURRENDING = 50;
    /**
     * @fieldName: ORDER_STATUS_WAIT_PAY
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月22日 下午1:34:01 
     * @Description: 订单状态-已退保
     */
    final public static Integer ORDER_STATUS_ALREADY_SURRENDER = 60;
    /**
     * @fieldName: ORDER_STATUS_WAIT_PAY
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月22日 下午1:34:01 
     * @Description: 订单状态-已关闭
     */
    final public static Integer ORDER_STATUS_ALREADY_CLOSE = 80;
    /**
     * @fieldName: ORDER_STATUS_WAIT_PAY
     * @fieldType: String
     * @author: WangLongFei
     * @date: 2017年11月22日 下午1:34:01 
     * @Description: 订单状态-已失效
     */
    final public static Integer ORDER_STATUS_ALREADY_INVALID = 90;
    //===================================================订单==================END============================
}
