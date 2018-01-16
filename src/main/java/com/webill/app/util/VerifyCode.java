package com.webill.app.util;

import java.util.Random;

public class VerifyCode {

    private static VerifyCode instance = null;

    private static char code[] = { '0', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g'
            , 'h', 'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D'
            , 'E', 'F', 'G', 'H', 'I', 'G', 'M', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };


    static {
        instance = new VerifyCode();
    }

    public static VerifyCode getInstance() {
        return instance;
    }

    public static String getVerifyCode() {

        Random ran = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < 20; i++) {
            sb.append(ran.nextInt(10));
        }
        return sb.toString();

    }

    /**
     *
     * 获取长度是 length 的 [0-9][a-z][A-Z]的字符串
     *
     * @param length
     *
     * @return
     */

    public static String getVerifyCode(final int length) {

        Random ran = new Random();
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < length; i++) {
            buf.append(code[ran.nextInt(code.length)]);
        }

        return buf.toString();
    }
    public static void main(String args[]){
        System.out.println(getVerifyCode(20));
    }
}

