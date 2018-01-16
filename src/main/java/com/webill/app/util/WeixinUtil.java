package com.webill.app.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by david on 16/10/28.
 */
public class WeixinUtil {

    public static Date getDateFromUnixTime(long timestamp) {
        return new Date(timestamp * 1000);
    }

    public static long getUnixTimeFromDate(Date date) {
        return date.getTime() / 1000;
    }

    public static boolean isWeixinAgent(HttpServletRequest request) {
        String agent = request.getHeader("user-agent");
        if (agent.toLowerCase().indexOf("micromessenger") == -1) {
            return false;
        }
        return true;
    }




}
