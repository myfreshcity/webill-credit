package com.webill.app.util;

public enum CardType {
    ID_CARD(1, "身份证"),
    PASSPORT(2, "护照"),
    BIRTH_CERTIFICATE(3, "出生证"),
    DRIVING_LICENSE(4, "驾照"),
    HONG_KONG_AND_MACAO_PASS(5, "港澳通行证"),
    CERTIFICATE_OF_OFFICERS(6, "军官证"),
    MTP(7, "台胞证"),
    POLICE_OFFICER_CARD(8, "警官证"),
    RETURN_HOME_CERTIFICATE(9, "港澳台回乡证"),
    ORGANIZATION_CODE_CERTIFICATE(10, "组织机构代码证"),
    BUSINESS_LICENSE(11, "营业执照"),
    TAX_REGISTRATION_NUMBER(12, "税务登记号"),
    THREE_IN_ONE(13, "三证合一"),
    UNIFIED_CREDIT_CODE_CERTIFICATE(14, "统一信用代码证"),
	OTHER(99, "其他");
    

    private int code;
    private String name;

    private CardType(int code, String name) {
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
        for(CardType ct: CardType.values()){
            if(code==ct.getCode()){
                return ct.getName();
            }
        }
        return OTHER.getName();
    }
    public static int getCode(String name){
        for(CardType ct: CardType.values()){
            if(name.equals(ct.getName())){
                return ct.getCode();
            }
        }
        return OTHER.getCode();
    }
    
}