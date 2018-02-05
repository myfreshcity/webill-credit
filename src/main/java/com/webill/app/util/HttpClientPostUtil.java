package com.webill.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author zhangjia
 * @createDate 2016年11月19日 上午11:49:21
 * @className HttpClientPostUtil
 * @classDescribe 利用HttpClient进行post请求的工具类
 */
public class HttpClientPostUtil {
	
	public static String doPost(String url, Map<String, String> map) {
		String charset = "utf-8";  
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		// 同盾
		String partnerCode = "pdwealth";// 第三方用户唯一凭证
		String partnerKey = "be315f6b1c3a4ae2b76b39a2c9a384f1";// 秘钥
		String appName = "pdwealth_web";// 应用编号
		 String msg = " ";
		String subUrl = "https://api.tongdun.cn/preloan/apply/v5";// sumbit接口
		String qryUrl = "https://api.tongdun.cn/preloan/report/v6";// query接口
		String chkUrl = "https://api.tongdun.cn/identification/realname/v1";// 实名验证
		 
		// 白骑士
		String getToken = "https://credit.baiqishi.com/clweb/api/common/gettoken";// 获取token
		String getReport = "https://credit.baiqishi.com/clweb/api/common/getreport";// 查询报告原数据
		String getOriginal = "https://credit.baiqishi.com/clweb/api/common/getoriginal";// 查询所有原始数据
		String emialUrl = "https://credit.baiqishi.com/clweb/api/emailbill/getoriginal";// 查询邮箱账单
		String mobileLogin = "https://credit.baiqishi.com/clweb/api/mno/login";// 运营商一次登录
		String mobileLoginTwice = "https://credit.baiqishi.com/clweb/api/mno/verifyauthsms";// 运营商二次鉴权登录
		String partnerId = "pdwealth";// 商户编号
		String verifyKey = "a540a1fff7d94418bcf9144368408b5b";// 认证密匙
		String appId = "test";// 应用编号
		 
		// 运营商一次登录
		 Map<String, String> map = new HashMap<String, String>();
		// sub接口map
		 map.put("reqId", "");
		 map.put("partnerId", partnerId);
//		 map.put("verifyKey", verifyKey);
		map.put("name", "张佳");// 用户姓名
		map.put("certNo", "430921199307137418");// 用户身份证
		map.put("mobile", "13062610559");// 用户手机号
		map.put("pwd", "524129");// 服务密码
		map.put("smsCode", "");// 登录短信验证码
		 String resultSub = doPost(mobileLogin, map);
		// 运营商二次鉴权登录
		 Map<String, String> map2 = new HashMap<String, String>();
		map2.put("reqId", "");// 任务id
		map2.put("smsCode", "");// 手机动态验证码
		 
		// qry接口map
		String param = "partner_code" + "=" + partnerCode + "&partner_key" + "=" + partnerKey + "&app_name" + "="
				+ appName + "&report_id" + "=" + "ER2016121411534913107228";
		String resultQry = HttpClientGetUtil.sendGet(qryUrl, param);
		System.out.println(resultSub);
		System.out.println(resultQry);
	}
}