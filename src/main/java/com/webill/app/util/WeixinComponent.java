package com.webill.app.util;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class WeixinComponent {

    private String token;
    private Date token_time;
    private String jsticket;
    private Date jsticket_time;

    /**
     * 返回有效token
     *
     * @return
     */
    public String getToken() {
        if(token == null || token_time == null
                || diffAndCompareTime(new Date(), token_time, 7100)){
            token = null;
            token_time = null;
        }
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setTokenAndTime(String token, Date time){
        this.token = token;
        this.token_time = time;
    }
    public Date getToken_time() {
        return token_time;
    }
    public void setToken_time(Date token_time) {
        this.token_time = token_time;
    }
    public String getJsticket() {
        if(jsticket == null || jsticket == null
                || diffAndCompareTime(new Date(), jsticket_time, 7100)){
            jsticket = null;
            jsticket_time = null;
        }
        return jsticket;
    }
    public void setJsticketAndTime(String jsticket, Date time){
        this.jsticket = jsticket;
        this.jsticket_time = time;
    }
    public void setJsticket(String jsticket) {
        this.jsticket = jsticket;
    }
    public Date getJsticket_time() {
        return jsticket_time;
    }
    public void setJsticket_time(Date jsticket_time) {
        this.jsticket_time = jsticket_time;
    }

    /**
     * d1和d2的时间差 与 compare 比较
     * @param d1
     * @param d2
     * @param compare
     * @return
     */
    private boolean diffAndCompareTime(Date d1, Date d2, long compare){
        long diff = d1.getTime() - d2.getTime();
        if(diff > compare*1000){
            return true;
        } else {
            return false;
        }
    }
}
