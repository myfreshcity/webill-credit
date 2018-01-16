package com.webill.app.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Formatter;

/**
 * 编码工具
 */
public class EncodeUtil {
    protected EncodeUtil() { }

    public static String encodeURLComponent(String raw) {
        try {
            return URLEncoder.encode(raw, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return raw;
        }
    }
    public static String decodeURLComponent(String raw) {
        try {
            return URLDecoder.decode(raw, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return raw;
        }
    }
    /**
     * 二进制转十六进制
     */
    public static String getByteToHex(final byte[] hash){
        Formatter formatter = new Formatter();
        for(byte b: hash){
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
