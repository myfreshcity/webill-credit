package com.webill.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/** 
* @ClassName: LianLianUtil 
* @Description: 连连支付签名工具类
* @author ZhangYadong
* @date 2017年10月30日 上午11:23:36 
*/
public class LianLianUtil {
	
    /** 
    * @Title: genSign 
    * @Description: 
    * @author ZhangYadong
    * @date 2017年11月2日 下午2:53:58
    * @param reqObj
    * @param signKey (md5-key 或者 商户private-key)
    * @return
    * @return String
    */
    public static String genSign(JSONObject reqObj, String signKey) {
        String sign_type=reqObj.getString("sign_type");
    	
        // 生成待签名串
        String sign_src = genSignData(reqObj);
        System.out.println("待签名串:"+sign_src);

        if("MD5".equals(sign_type)){
            sign_src += "&key=" + signKey;
            return signMD5(sign_src);
        }
        if("RSA".equals(sign_type)){
            return getSignRSA(sign_src, signKey);
        }
        return null;
    }
    
    /**
     * Md5签名生成
     * @param reqObj
     * @return
     */
    private static String signMD5(String signSrc) {
        try {
        	return MD5Util.MD5Encode(signSrc, "utf-8");
            /*return Md5Algorithm.getInstance().md5Digest(
                    signSrc.getBytes("utf-8"));*/
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * RSA签名生成
     * @param reqObj
     * @return
     */
    private static String getSignRSA(String sign_src, String prikeyvalue) {
        return RSAUtil.sign(prikeyvalue, sign_src);
    }

    /**
     * 生成待签名串
     * @param paramMap
     * @return
     */
    public static String genSignData(JSONObject jsonObject) {
        StringBuffer content = new StringBuffer();

        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<String>(jsonObject.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            // sign 和ip_client 不参与签名
            if ("sign".equals(key)) {
                continue;
            }
            String value = (String) jsonObject.getString(key);
            // 空串不参与签名
            if (null==value||"".equals(value)) {
                continue;
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);

        }
        String signSrc = content.toString();
        if (signSrc.startsWith("&")) {
            signSrc = signSrc.replaceFirst("&", "");
        }
        return signSrc;
    }
    
    /**
     * 读取request流
     * @param req
     * @return
     */
    public static String readReqStr(HttpServletRequest request) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {

            }
        }
        return sb.toString();
    }
    
    /**
     * str空判断
     * @param str
     * @return
     */
    public static boolean isnull(String str) {
        if (null == str || str.equalsIgnoreCase("null") || str.equals("")){
            return true;
        } else{
        	return false;
        }
    }
    
    /**
     * 获取当前时间str，格式yyyyMMddHHmmss
     * @return
     */
    public static String getCurrentDateTimeStr() {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String timeString = dataFormat.format(date);
        return timeString;
    }
    
    /**
     * 
     * 功能描述：获取真实的IP地址
     * @param request
     * @return
     * @author guoyx
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        if (!isnull(ip) && ip.contains(",")){
            String[] ips = ip.split(",");
            ip = ips[ips.length - 1];
        }
        //转换IP 格式
        if(!isnull(ip)){
            ip=ip.replace(".", "_");
        }
        return ip;
    }
    
    /**
     * 签名验证
     * @param reqStr
     */
    public static boolean checkSign(String reqStr, String rsa_public, String md5_key) {
        JSONObject reqObj = JSON.parseObject(reqStr);
        if (reqObj == null){
            return false;
        }
        String sign_type = reqObj.getString("sign_type");
        if (SignTypeEnum.MD5.getCode().equals(sign_type)){
            return checkSignMD5(reqObj, md5_key);
        } else{
            return checkSignRSA(reqObj, rsa_public);
        }
    }
    
    /**
     * MD5签名验证
     * @param signSrc
     * @param sign
     * @return
     */
    private static boolean checkSignMD5(JSONObject reqObj, String md5_key) {
        System.out.println("进入商户[" + reqObj.getString("oid_partner")
                + "]MD5签名验证");
        if (reqObj == null){
            return false;
        }
        String sign = reqObj.getString("sign");
        // 生成待签名串
        String sign_src = genSignData(reqObj);
        System.out.println("商户[" + reqObj.getString("oid_partner") + "]待签名原串"
                + sign_src);
        System.out.println("商户[" + reqObj.getString("oid_partner") + "]签名串"
                + sign);
        sign_src += "&key=" + md5_key;
        try{
        	if (sign.equals(MD5Util.MD5Encode(sign_src, "utf-8"))){
                System.out.println("商户[" + reqObj.getString("oid_partner")
                        + "]MD5签名验证通过");
                return true;
            } else{
                System.out.println("商户[" + reqObj.getString("oid_partner")
                        + "]MD5签名验证未通过");
                return false;
            }
        } catch (Exception e){
            System.out.println("商户[" + reqObj.getString("oid_partner")
                    + "]MD5签名验证异常" + e.getMessage());
            return false;
        }
    }
    
    /**
     * RSA签名验证
     * @param reqObj
     * @return
     */
    public static boolean checkSignRSA(JSONObject reqObj, String rsa_public) {
        System.out.println("进入商户[" + reqObj.getString("oid_partner")
                + "]RSA签名验证");
        if (reqObj == null){
            return false;
        }
        String sign = reqObj.getString("sign");
        // 生成待签名串
        String sign_src = genSignData(reqObj);
        System.out.println("商户[" + reqObj.getString("oid_partner") + "]待签名原串"
                + sign_src);
        System.out.println("商户[" + reqObj.getString("oid_partner") + "]签名串"
                + sign);
        try{
            if (TraderRSAUtil.checksign(rsa_public, sign_src, sign)){
                System.out.println("商户[" + reqObj.getString("oid_partner")
                        + "]RSA签名验证通过");
                return true;
            } else{
                System.out.println("商户[" + reqObj.getString("oid_partner")
                        + "]RSA签名验证未通过");
                return false;
            }
        } catch (Exception e){
            System.out.println("商户[" + reqObj.getString("oid_partner")
                    + "]RSA签名验证异常" + e.getMessage());
            return false;
        }
    }
    
}
