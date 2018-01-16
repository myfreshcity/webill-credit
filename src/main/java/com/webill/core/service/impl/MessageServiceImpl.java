package com.webill.core.service.impl;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.webill.app.SystemProperty;
import com.webill.app.util.MessageUtil;
import com.webill.app.util.WeixinSupport;
import com.webill.core.model.TemplateMsgResult;
import com.webill.core.model.WechatTemplateMsg;
import com.webill.core.service.IMessageService;
import com.webill.framework.common.JSONUtil;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;

import java.io.InputStream;
import java.io.OutputStream;



@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    WeixinSupport weixinSupport;
    
    @Autowired
    protected SystemProperty constPro;
	
    @Override
	public String sendMessage(String touser,String content) {
        //文本消息
    	StringBuffer sb = new StringBuffer();
    	sb.append( "{\"touser\":"+"\""+touser+"\""+",");
    	sb.append( "\"msgtype\":"+"\""+MessageUtil.RESP_MESSAGE_TYPE_TEXT+"\""+",");
    	sb.append( "\"text\":{"+"\"content\":\""+content+"\""+"}}");
    	sb.append("{\"touser\":\"OPENID\",\"template_id\":\"6HUuB7WK3Cgh2M2HsLqwvB2lrUVj-pI5LhNdnAGguZo\",\"url\":\"http://weixin.qq.com/download\",\"topcolor\":\"#FF0000\",\"data\":{\"User\":{\"value\":\"黄先生\",\"color\":\"#173177\"},\"Date\":{\"value\":\"06月07日 19时24分\",\"color\":\"#173177\"},\"CardNumber\":{\"value\":\"0426\",\"color\":\"#173177\"},\"Type\":{\"value\":\"消费\",\"color\":\"#173177\"},\"Money\":{\"value\":\"人民币260.00元\",\"color\":\"#173177\"},\"DeadTime\":{\"value\":\"06月07日19时24分\",\"color\":\"#173177\"},\"Left\":{\"value\":\"6504.09\",\"color\":\"#173177\"}}}");
    	String data = sb.toString();
    	
    	// 获取access_token
		String accessToken = weixinSupport.getAccessToken();
        System.out.println(data);
        
        //要调用的微信接口
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken;
        
        HttpClient httpclient = new HttpClient();
        PostMethod post = new PostMethod(url);
        String info = null;
        try {
	            RequestEntity entity = new StringRequestEntity(data, "text/plain","utf-8");
	            post.setRequestEntity(entity);
	            httpclient.executeMethod(post);
	            int code = post.getStatusCode();
	            if (code == HttpStatus.SC_OK)
	                info = new String(post.getResponseBodyAsString());  //接口返回的信息
        } catch (Exception ex) {
	            ex.printStackTrace();
        } finally {
	            post.releaseConnection();
        }
		return info;

	}
    
    @Override
	public TemplateMsgResult sendTemplateMessage(WechatTemplateMsg wtm) {
    	String wtmStr = JSONUtil.toJSONString(wtm);
    	// 获取access_token
		String accessToken = weixinSupport.getAccessToken();
        
        //要调用的微信模板接口
        String wxurl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken;
        
        HttpClient httpclient = new HttpClient();
        PostMethod post = new PostMethod(wxurl);
        String info = null;
        try {
	            RequestEntity entity = new StringRequestEntity(wtmStr, "text/plain","utf-8");
	            post.setRequestEntity(entity);
	            httpclient.executeMethod(post);
	            int code = post.getStatusCode();
	            if (code == HttpStatus.SC_OK)
	                info = new String(post.getResponseBodyAsString());  //接口返回的信息
        } catch (Exception ex) {
	            ex.printStackTrace();
        } finally {
	            post.releaseConnection();
        }
        TemplateMsgResult tmr = JSONObject.parseObject(info, TemplateMsgResult.class);
		return tmr;

	}


	/**   
	 * @Title: connectWeiXinInterface   
	 * @Description: 连接请求微信后台接口  
	 * @author: WangLongFei  
	 * @date: 2017年8月16日 下午6:42:43   
	 * @param action
	 * 			接口url
	 * @param json  
	 * 			请求接口传送的json字符串
	 * @return: void  
	 */
	public void connectWeiXinInterface(String action, String json) {
		URL url;
		try {
			url = new URL(action);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			OutputStream os = http.getOutputStream();
			os.write(json.getBytes("UTF-8"));// 传入参数
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String result = new String(jsonBytes, "UTF-8");
			System.out.println("请求返回结果:" + result);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
