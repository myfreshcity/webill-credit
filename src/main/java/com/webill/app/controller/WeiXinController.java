package com.webill.app.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.webill.app.util.*;
import com.webill.core.Constant;
import com.webill.core.model.User;
import com.webill.core.service.*;
import com.webill.framework.controller.BaseController;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.Reactor;
import reactor.event.Event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by david on 16/10/28.
 */
@Controller
public class WeiXinController extends BaseController {

	private static String baseUrl = "http://weixin.xinfor.com";
	
    @Autowired
    @Qualifier("rootReactor")
    private Reactor r;
	
    @Autowired
    IUserService userService;

	@Autowired
	ICouponService couponService;
	
	@Autowired
	IMessageService messageService;
	
	@Autowired
	IUserCouponService userCouponService;
	

	@Autowired
    private WeixinSupport weixinSupport;



    /**
     * authorize的回调接口
     *
     * 获取并处理openid
     * @param request
     * @param code
     * @param response
     */
    @RequestMapping(value = "/wx/openid/{code}", method = { RequestMethod.GET, RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object openid(HttpServletRequest request, @PathVariable String code, HttpServletResponse response)
            throws Exception {

        logger.info("**** authorize的回调接口(/wx/openid): code-->" + code);
        logger.info("**** user信息在session中不存在");

        User weixin = null;
        if (StringUtils.isEmpty(code)) {
            logger.error("**** register error -- openid code empty");
        } else {
            weixin = processOauth(constPro.WEIXIN_APPID, constPro.WEIXIN_SECRET, code, true);
            if (weixin != null) {
				//设置token
            	Integer uid = weixin.getId();
            	String token = jwtUtil.generToken(String.valueOf(uid),"mmh","uid");
            	weixin.setJwtToken(token);
            	logger.info("**** weixin有值：openid-->" + weixin.getOpenId());

                return renderSuccess(weixin);
            }
        }
        return renderError("获取失败");
    }

    @RequestMapping(value = "/wx/wapHome")
    public String wapHome(HttpServletRequest request) {
        return "redirect:" + urlRedirect("/?target=home",request);
    }

    @RequestMapping(value = "/wx/shownCar/{shownCarId}")
    public String shownCar(@PathVariable String shownCarId,HttpServletRequest request) {
        return "redirect:" + urlRedirect("/shownCar/"+shownCarId,request);
    }
    
    @RequestMapping(value = "/wx/onlineInsurance/{orderId}")
    public String onlineInsurance(@PathVariable String orderId,HttpServletRequest request) {
        return "redirect:" + urlRedirect("/onlineInsurance/"+orderId,request);
    }
    
    @RequestMapping(value = "/wx/insureOrderDetail/{orderId}")
    public String insureOrderDetail(@PathVariable String orderId,HttpServletRequest request) {
    	return "redirect:" + urlRedirect("/insureOrderDetail/"+orderId,request);
    }
    
    @RequestMapping(value = "/wx/illegalOrderDetail/{orderId}")
    public String illegalOrderDetail(@PathVariable String orderId,HttpServletRequest request) {
    	return "redirect:" + urlRedirect("/illegalOrderDetail/"+orderId,request);
    }
    
    @RequestMapping(value = "/wx/illegalHome")
    public String illegalHome(HttpServletRequest request) {
        return "redirect:" + urlRedirect("/?target=illegalIndex",request);
    }

    @RequestMapping(value = "/wx/personalHome")
    public String personalHome(HttpServletRequest request) {
        return "redirect:" + urlRedirect("/?target=home",request);
    }

    @RequestMapping(value = "/wx/insuranceHome")
    public String insuranceHome(HttpServletRequest request) {
        return "redirect:" + urlRedirect("/?target=insuranceIndex",request);
    }


    @RequestMapping(value = "/wx/recommend/{uid}")
    public String recommend(@PathVariable String uid,HttpServletRequest request) {
        return "redirect:" + urlRedirect("/recommend/"+uid,request);
    }

    /**
     * 集中处理：获取用户的openid 或 userinfo
     *
     * @param code
     * @param isUserInfo
     * @return
     */
    private User processOauth(String weixin_appid,
                                         String weixin_secret, String code, boolean isUserInfo) {

        logger.info("**** UserWeixinServiceImpl.processOauth,1.0 begin: weixin_appid->"
                + weixin_appid
                + ",code->"
                + code
                + ",isUserInfo->" + isUserInfo);

        // 1. 构建HttpClient
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        try {
            // 2. 执行get请求(获取openid 和 access_token)
            CloseableHttpResponse httpResponseToken = null;
            try {
                // get token url
                StringBuffer getTokenUrl = new StringBuffer(
                        "https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                        .append(weixin_appid).append("&secret=")
                        .append(weixin_secret).append("&code=").append(code)
                        .append("&grant_type=authorization_code");
                HttpGet httpGetToken = new HttpGet(getTokenUrl.toString());
                httpResponseToken = closeableHttpClient.execute(httpGetToken);

                // 获取响应消息实体
                HttpEntity entity = httpResponseToken.getEntity();
                // 响应状态
                int httpStatusCode = httpResponseToken.getStatusLine()
                        .getStatusCode();
                if (httpStatusCode != 200) {
                    logger.error("register error -- when getting access_token, return http status "
                            + httpStatusCode);
                    return null;
                }

                // 判断响应实体是否为空
                if (entity != null) {
                    String accessTokenResult = null;
                    if (constPro.IS_PRODUCT) {
                        accessTokenResult = EntityUtils.toString(entity, "utf-8");
                    } else {
                        accessTokenResult = "{" +
                                "\"access_token\":\"ACCESS_TOKEN\"," +
                                "\"expires_in\":7200," +
                                "\"refresh_token\":\"REFRESH_TOKEN\"," +
                                "\"openid\":\"" + code + "\"," +
                                "\"scope\":\"SCOPE\"," +
                                "\"unionid\": \"o6_bmasdasdsad6_2sgVt7hMZOPfL\"}";
                    }

                    if (accessTokenResult.contains("access_token")) {
                        JSONObject accessTokenJson;
                        accessTokenJson = JSON.parseObject(accessTokenResult);
                        String openid = accessTokenJson.getString("openid");
                        String access_token = accessTokenJson.getString("access_token");

                        if (StringUtils.isEmpty(openid)) {
                            logger.error("register error -- get openid null!");
                            return null;
                        } else {

                            logger.info("**** UserWeixinServiceImpl.processOauth,2.1 获取openid和access_token结束: openid->"
                                    + openid + ",access_token->" + access_token);
                            User userWhere = new User();
                            // 需要获取userinfo
                            if (isUserInfo && constPro.IS_PRODUCT) {
                                logger.info("**** UserWeixinServiceImpl.processOauth,2.2 开始获取userinfo...");
                                HttpGet httpGetUserinfo = new HttpGet(
                                        "https://api.weixin.qq.com/sns/userinfo?access_token="
                                                + access_token + "&openid="
                                                + openid+"&lang=zh_CN");
                                CloseableHttpResponse httpResponseUserinfo = null;
                                httpResponseUserinfo = closeableHttpClient
                                        .execute(httpGetUserinfo);

                                // 获取响应消息实体
                                entity = httpResponseUserinfo.getEntity();
                                // 响应状态
                                httpStatusCode = httpResponseToken
                                        .getStatusLine().getStatusCode();
                                if (httpStatusCode != 200) {
                                    logger.error("register userinfo error -- when getting userinfo, return http status "
                                            + httpStatusCode);
                                    return null;
                                }
                                // 判断响应实体是否为空
                                if (entity != null) {
                                    String userinfoResult = EntityUtils.toString(entity, "utf-8");
                                    JSONObject userinfoJson = JSON.parseObject(userinfoResult);
                                    UserWeixinModel userWeixin = new UserWeixinModel();
                                    userWeixin.setOpenId(openid);
                                    
                                    String nickName = userinfoJson.getString("nickname");
                                    String headUrl = userinfoJson.getString("headimgurl");
                                    String sex = userinfoJson.getString("sex");
                                    String unionId = userinfoJson.getString("unionid");
                                    
                                    userWhere.setUnionId(unionId);
                                    userWhere.setSex(sex);
                                    userWhere.setHeadUrl(headUrl);
                                    userWhere.setWeixinNick(nickName);
                                    userWhere.setOpenId(openid);
                                    User user = userService.selectUserByWeixin(userWhere);
                                    
                                    User defaultUser = new User();
                                    defaultUser.setOpenId(openid);
                                    defaultUser.setId(user.getId());
                                    defaultUser.setSubscribeFlag(user.getSubscribeFlag());
                                    if(user!=null){
                                    	user.setWeixinNick(EmojiUtil.resolveToEmojiFromByte(user.getWeixinNick()));
//                                    	User defaultUser = new User();
//                                    	if(user.getLoginFlag().intValue()==Constant.LOGIN_STATUS_YES){
//                                    		//是自动登录，返回真数据
//                                    		userWeixin.setStaff(user);
//                                    		userWeixin.setSubscribe(user.getSubscribeFlag());
//                                    	}else{
//                                    		//非自动登录，返回假数据
//                                    		defaultUser.setId(-1);
//                                    		userWeixin.setStaff(defaultUser);
//                                    		userWeixin.setSubscribe(user.getSubscribeFlag());
//                                    	}
//                                    	
//                                    	userWeixin.setNickName(nickName);
//                                    	userWeixin.setHeadUrl(headUrl);
//                                    	userWeixin.setSex(sex);
//                                    	userWeixin.setCity(userinfoJson.getString("city"));
//                                    	userWeixin.setProvince(userinfoJson.getString("province"));
//                                    	userWeixin.setCountry(userinfoJson.getString("country"));
//                                    	userWeixin.setPrivilege(userinfoJson.getString("privilege"));
                                    	logger.info("**** UserWeixinServiceImpl.processOauth,2.3 获取userinfo结束: nickname->"
                                    			+ defaultUser.getWeixinNick());
                                    	return defaultUser;
                                    }else{
                                    	return null;
                                    }
                                }
                                httpResponseUserinfo.close();

                            } else { // 仅获取openid
                                logger.info("**** userWeixinServiceImpl: 仅获取openid");
                                User userWeixin = new User();
                                
                                userWhere.setUnionId("");
                                userWhere.setSex("1");
                                userWhere.setHeadUrl("http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ\n");
                                userWhere.setWeixinNick("测小花");
                                userWhere.setOpenId(openid);
                                
//                                userWeixin.setStaff(userService.selectUserByWeixin(userWhere));
                                userWeixin.setOpenId(openid);
                                return userWeixin;
                            }
                        }
                    } else {
                        logger.error("register error -- WeixinService access_token get error,code="
                                + code
                                + ",accessTokenResult="
                                + accessTokenResult);
                    }
                }

            } catch (Exception e) {
                logger.error("", e);
            } finally {
                if (httpResponseToken != null) {
                    try {
                        httpResponseToken.close();
                    } catch (IOException e) {
                        logger.error("", e);
                    }
                }
            }

        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (closeableHttpClient != null) {
                try {
                    closeableHttpClient.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }

        return null;
    }

    /**
     * 验证验证服务器地址的有效性
     *
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/wx/call", method = { RequestMethod.GET, RequestMethod.POST })
    public void call(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String method = request.getMethod().toLowerCase();		//传输方式
        PrintWriter out = response.getWriter();
        String signature = request.getParameter("signature");	// SHA1加密字符串
        String timestamp = request.getParameter("timestamp");	// 时间
        String nonce = request.getParameter("nonce");			// 随机数
        String echostr = request.getParameter("echostr");		// 随机字符串

        try {
            if ("get".equals(method)) { //验证开发者
                logger.info("微信验证开发者 - signature="+signature+",timestamp="+timestamp+",nonce="+nonce+",echostr="+echostr);
                if (echostr != null && echostr.trim().length() > 0) {
                    if (checkSignature(constPro.WEIXIN_TOKEN, signature, timestamp, nonce)) {
                        out.print(echostr);
                        logger.info("微信验证开发者 - OK");
                    } else {
                        out.print("");
                        logger.info("微信验证开发者 - NG");
                    }
                } else {
                    out.print("");
                    logger.info("微信接口 - 非法请求");
                }
            } else { // 处理消息
                if(checkSignature(constPro.WEIXIN_TOKEN, signature, timestamp, nonce)) {
                    InputStream is = request.getInputStream();
                    WeixinRequest wxRequest = new WeixinRequest();
                    wxRequest.parseXML(is);
                    logger.info("微信请求消息: open_id->"+wxRequest.getFromUserName()+", xml->"+wxRequest.getXml());
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("event", wxRequest.getEvent());
                    map.put("fromUserName", wxRequest.getFromUserName());
                    map.put("touser", wxRequest.getFromUserName());
                    
                    //用户关注，取消关注更新数据库关注状态
                    User userWhere = new User();
                    userWhere.setOpenId(wxRequest.getFromUserName());
                    if("subscribe".equals(wxRequest.getEvent())){
                    	//关注
                    	userWhere.setSubscribeFlag(1);
                    	r.notify("userSubscribeFlag.update", Event.wrap(userWhere));
                    	if(map!=null){
                    		//第一次关注成功，发放优惠券,
                    		//	发放成功，有微信消息提示
                    		logger.info("begin to send coupon...");
                    		r.notify("coupon.send", Event.wrap(map));
                    	}
                    }else if("unsubscribe".equals(wxRequest.getEvent())){
                    	//取消关注
                    	userWhere.setSubscribeFlag(0);
                    	r.notify("userSubscribeFlag.update", Event.wrap(userWhere));
                    }else{
                    	out.print("签名验证成功！");
                    }
                    
                } else {
                    out.print("签名验证失败！");
                }
            }
        } catch (Exception e) {
            logger.error("微信请求失败",e);
        } finally {
            out.flush();
            out.close();
        }
    }

    /**
     * 获取js配置信息
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value="/wx/jsconfig", method = {RequestMethod.GET},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object getJsConfig(String url){
        logger.info("get jsconfig for url:" + url);
        String nonceStr = "mmh" + VerifyCode.getVerifyCode(5);
        long timestamp = (new Date()).getTime();
        try {
        String ticket = weixinSupport.getJsTicket(); // 调用服务
        StringBuffer sign = new StringBuffer("jsapi_ticket=").append(ticket)
                .append("&noncestr=").append(nonceStr)
                .append("&timestamp=").append(timestamp)
                .append("&url=").append(url);
        logger.info("sign:" + sign.toString());
        MessageDigest messageDigest = null;
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(sign.toString().getBytes());
            byte[] signBytes = messageDigest.digest();
            Map result = new HashMap();
            result.put("appId", constPro.WEIXIN_APPID);
            result.put("timestamp", timestamp);
            result.put("nonceStr", nonceStr);
            result.put("signature", EncodeUtil.getByteToHex(signBytes));

            return renderSuccess(result);
        } catch (Exception e) {
            logger.error("request jsconfig url:"+url,e);
            return renderError("错误的车主姓名");
        }
    }


    /**
     * 验证微信请求签名
     * @param token
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private boolean checkSignature(String token, String signature, String timestamp, String nonce)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        boolean result = false;

        String[] a = { token, timestamp, nonce };
        Arrays.sort(a); // 数组排序
        String str = "";
        for (int i = 0; i < a.length; i++) {
            str += a[i];
        }

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(str.getBytes("UTF-8"));
        byte[] results = md.digest();
        StringBuffer sb = new StringBuffer();
        for(byte b:results){
            int i= b&0xff;
            if(i<0xf) {
                sb.append(0);
            }
            sb.append(Integer.toHexString(i));
        }
        String digest = sb.toString().toLowerCase();

        if (digest.equals(signature)) {
            result = true;
        }

        return result;
    }

	/**
	 * @Title: urlRedirect
	 * @Description: ☆☆☆☆☆—————————————— 第一步：用户同意授权，获取code 如果用户同意授权，页面将跳转至
	 *               redirect_uri/?code=CODE&state=STATE。 code说明 ：
	 *               code作为换取access_token的票据，每次用户授权带上的code将不一样，
	 *               code只能使用一次，5分钟未被使用自动过期。
	 * @author: WangLongFei
	 * @date: May 16, 2017 5:32:15 PM
	 * @param uri
	 * @return
	 * @return: String
	 */
	private String urlRedirect(String uri, HttpServletRequest request) {
		String client = this.isClient(request);
		logger.info("客户端类型=====>"+client);
		String newUri = null;
		if("weixin".equals(client)){
			if (constPro.IS_PRODUCT) {
				try {
					newUri = URLEncoder.encode(constPro.DOMAIN_URL + "/#" + uri, "UTF-8");
					newUri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + constPro.WEIXIN_APPID
							+ "&redirect_uri=" + newUri
							+ "&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect";
				} catch (UnsupportedEncodingException e) {
					logger.error("encode url fail:" + uri, e);
				}
			} else {
				String forwardUrl = request.getParameter("url");
				String code = request.getParameter("code");
				if(org.apache.commons.lang3.StringUtils.isBlank(forwardUrl)){
					forwardUrl = constPro.DOMAIN_URL;
				}
				if(org.apache.commons.lang3.StringUtils.isBlank(code)){
					code = "CODE";
				}
				newUri = forwardUrl + "/?code="+code+"&status=123#" + uri;
			}
		}else{
			newUri = constPro.DOMAIN_URL;
		}
		return newUri;
	}

	public String isClient(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent").toLowerCase();
		if (userAgent == null || userAgent.indexOf("micromessenger") == -1 ? false : true) { // 判断当前客户端是否为微信
			return "weixin";
		}else{ // 判断当前客户端是否为PC
			return "pc";
		}
	}
	 /**
	 * 微信网页授权获取用户基本信息，先获取 code，跳转 url 通过 code 获取 openId
	 * @param request
	 * @param response
	 * @return
	 */
     @RequestMapping("/wx/userAuth")
     public String userAuth(HttpServletRequest request, HttpServletResponse
             response) {
         try {
             //生成订单号
             String orderId = OrderUtils.genOrderNo("illegal",constPro.WEIXIN_APPID);
             //获取订单总金额
             String totalFee = request.getParameter("totalFee");

             //授权后要跳转的链接
             String backUri = baseUrl + "/wx/toPay";
             backUri = backUri + "?orderId=" + orderId + "&totalFee=" + totalFee;
             //URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
             backUri = URLEncoder.encode(backUri, "UTF-8");
             //scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
             //https://api.mch.weixin.qq.com/pay/unifiedorder
             String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                     "appid=" + constPro.WEIXIN_APPID +
                     "&redirect_uri=" + backUri +
                     "&response_type=code&scope=snsapi_userinfo&state=101#wechat_redirect";
             response.sendRedirect(url);
         } catch (IOException e) {
             e.printStackTrace();
         }
         return null;
     }

}
