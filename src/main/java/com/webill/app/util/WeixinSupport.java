package com.webill.app.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.webill.app.SystemProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

@Component
public class WeixinSupport {

    private static Logger logger = LoggerFactory.getLogger(WeixinSupport.class);

    @Autowired
    WeixinComponent weixinComponent;

    @Autowired
    protected SystemProperty constPro;

    /**
     * 获取一般access_token
     *
     * 注意：获取用户基本信息的access_token和这个不一样， 这个是公众号全局唯一的access_token
     *
     * @return
     */
    public String getAccessToken() {
        String weixinAppid = constPro.WEIXIN_APPID;
        String weixinSecret = constPro.WEIXIN_SECRET;
        String result = null;

        logger.info("**** WeixinSupport.getCommonAccessToken,1.0 begin...");
        // application中获取
        String access_token = weixinComponent.getToken();

        // 调用微信api获取
        if (StringUtil.isEmpty(access_token)) {
            logger.info("**** WeixinSupport.getCommonAccessToken,2.1 weixinComponent中未获取到，走微信api");
            try {
                URL url = new URL(
                        "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                                + weixinAppid + "&secret=" + weixinSecret);
                HttpURLConnection urlConn = (HttpURLConnection) url
                        .openConnection();
                urlConn.setConnectTimeout(1000 * 5);

                InputStream is = urlConn.getInputStream(); // 发起网络连接
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(is));
                StringBuffer strBuff = new StringBuffer();
                String line = "";
                while ((line = in.readLine()) != null) {
                    strBuff.append(line);
                }
                is.close();
                in.close();
                urlConn.disconnect();

                result = strBuff.toString();
                org.json.JSONObject resultJson = new org.json.JSONObject(result);

                access_token = resultJson.getString("access_token");
                if (StringUtil.isEmpty(access_token)) {
                    throw new Exception("get access_token error!");
                } else {
                    // 保存到component中
                    weixinComponent.setTokenAndTime(access_token, new Date());
                }

                logger.info("**** userWeixinServiceImpl.getCommonAccessToken,2.0 access_token->"
                        + access_token);

            } catch (Exception e) {
                logger.error("the response from weixin is:" + result);
                logger.error("try to get token failed:", e);
            }
        } else {
            logger.info("**** userWeixinServiceImpl.getCommonAccessToken,2.2 weixinComponent中获取到, access_token->"
                    + access_token);
        }
        return access_token;
    }

    public String getJsTicket() {
        logger.info("**** userWeixinServiceImpl.getJsTicket,1.0 begin...");
        // redis 中获取
        String ticket = weixinComponent.getJsticket();
        String ticketResult = null;

        if (StringUtil.isNotEmpty(ticket)) {
            logger.info("**** userWeixinServiceImpl.getJsTicket,2.1 weixinComponent获取，ticket－>"
                    + ticket);
            return ticket;
        }

        // 调用微信api
        String accessToken = getAccessToken();
        if (accessToken == null) {
            return null;
        }
        try {
            URL ticketUrl = new URL(
                    "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
                            + accessToken + "&type=jsapi");
            HttpURLConnection conn = (HttpURLConnection) ticketUrl
                    .openConnection();
            conn.setConnectTimeout(1000 * 5);
            InputStream is = conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            is.close();
            in.close();
            conn.disconnect();

            ticketResult = buffer.toString();
            JSONObject ticketJson = JSON.parseObject(ticketResult);
            ticket = ticketJson.getString("ticket");

            if (StringUtil.isNotEmpty(ticket)) {
                weixinComponent.setJsticketAndTime(ticket, new Date());

                logger.info("**** userWeixinServiceImpl.getJsTicket,2.1 weixinComponent未获取，走微信api获取，ticket－>"
                        + ticket);

                return ticket;
            }
        } catch (IOException e) {
            logger.error("the response from weixin is:" + ticketResult);
            logger.error("try to get js ticket failed:", e);
        }

        return null;

    }

    /**
     *
     * 获取下载图片信息（jpg）
     * @param mediaId
     *文件的id
     * @throws Exception
     */

    public String getPic(String savePath, String mediaId, String picName) {
        String result = null;
        InputStream inputStream = null;
        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        String url = null;

        try {
            String accessToken = getAccessToken();
            url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="
                    + accessToken + "&media_id=" + mediaId;

            URL urlGet = new URL(url);
            logger.debug(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            //http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            // 获取文件转化为byte流
            //String ContentDisposition = http.getHeaderField("Content-disposition");
            File file = new File(constPro.FILE_SAVE_PATH + savePath);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }

            //String fileExt = getFileexpandedName(http.getHeaderField("Content-Type"));
            String fileName = constPro.FILE_SAVE_PATH + savePath + "/" + picName;
            BufferedInputStream bis = new BufferedInputStream(http.getInputStream());
            FileOutputStream fos = new FileOutputStream(new File(fileName));
            byte[] buf = new byte[8096];
            int size = 0;
            while ((size = bis.read(buf)) != -1)
                fos.write(buf, 0, size);
            fos.close();
            bis.close();


            BASE64Encoder encoder = new BASE64Encoder();
            inputStream = new FileInputStream(fileName);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            result = encoder.encode(data);
            //logger.debug(result);
            inputStream.close();


        } catch (IOException e) {
            logger.error("download pic["+url+"] failed:", e);
        }
        return result;
    }

    private String getFileexpandedName(String contentType) {
        String fileEndWitsh = "";
        if ("image/jpeg".equals(contentType))
            fileEndWitsh = ".jpg";
        else if ("audio/mpeg".equals(contentType))
            fileEndWitsh = ".mp3";
        else if ("audio/amr".equals(contentType))
            fileEndWitsh = ".amr";
        else if ("video/mp4".equals(contentType))
            fileEndWitsh = ".mp4";
        else if ("video/mpeg4".equals(contentType))
            fileEndWitsh = ".mp4";
        return fileEndWitsh;
    }


}
