package com.webill.app.util;

public enum BankUtil {
	GS(1, "工商银行"),
	JS(2, "建设银行"),
	CX(3, "储蓄银行"),
	NY(4, "农业银行"),
	MS(5, "民生银行"),
	ZS(6, "招商银行"),
	XY(7, "兴业银行"),
	ZG(8, "中国银行"),
	ZX(9, "中信银行"),
	JT(10, "交通银行"),
	PA(11, "平安银行"),
	GD(12, "光大银行"),
	OTHER(13, "其他银行");

    

    private int code;
    private String name;

    private BankUtil(int code, String name) {
        this.code = code;
        this.name = name;
    }
    public int getCode() {
        return code;
    }
    public String getName() {
        return name;
    }

    public static String getName(int code){
        for(BankUtil ct: BankUtil.values()){
            if(code==ct.getCode()){
                return ct.getName();
            }
        }
        return OTHER.getName();
    }

    public static int getCode(String name){
        for(BankUtil ct: BankUtil.values()){
            if(name.equals(ct.getName())){
                return ct.getCode();
            }
        }
        return OTHER.getCode();
    }
}