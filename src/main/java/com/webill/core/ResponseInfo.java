package com.webill.core;

public enum ResponseInfo {

    SUCCESS(0, "操作成功"),
    ACTION_FAIL(1000, "操作失败"),
    UNAUTHORZIED(1001, "未授权操作"),
    DATA_EXIST(1002, "数据已存在"),
    SERVER_ERROR(9999, "服务器异常"),
    USER_NOT_EXIST(1, "用户不存在"),
    PASSWORD_ERROR(2, "密码错误");


    private int code;
    private String msg;

    private ResponseInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

    public static String getResponseMsg(int code){
        for(ResponseInfo responseInfo: ResponseInfo.values()){
            if(code==responseInfo.getCode()){
                return responseInfo.getMsg();
            }
        }
        return SERVER_ERROR.getMsg();
    }

}