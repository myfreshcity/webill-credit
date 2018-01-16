package com.webill.app.util;

/**
 * ***********************************************************************
 * <br>description : 字符串工具类
 * @date        2014-8-1 上午09:34:23
 * @version     1.0
 ************************************************************************
 */
public class StringUtil {
    /**
     * 将对象数据转换为String，并去除首尾空格
     * @param obj
     * @return
     */
    public static String trim(Object obj){
        if(null == obj)return "";
        else return obj.toString().trim();
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return null==str || "".equals(str.trim());
    }

    public static boolean isEmpty(Object obj){
        return null==obj || "".equals(obj);
    }

    /**
     * 判断字符串是否不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
