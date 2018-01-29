package com.webill.app.util;

/** 
 * @ClassName: FinanceDhbBasicEnum 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月25日 下午3:11:59 
 */
public enum FinanceDhbEnum {
	// 聚信立-澳门电话通话情
	contact_macao("contact_macao"), 
	// 聚信立-110话通话情况
	contact_110("contact_110"), 
	// 聚信立-120话通话情况
	contact_120("contact_120"),
	// 聚信立-律师号码通话情况
	contact_lawyer("contact_lawyer"),
	// 聚信立-法院号码通话情况
	contact_court("contact_court"),
	// 聚信立-贷款类号码联系情况
	contact_loan("contact_loan"),
	// 聚信立-银行类号码联系情况
	contact_bank("contact_bank"),
	// 聚信立-信用卡类号码联系情况
	contact_credit_card("contact_credit_card");

    private String name;
    public String getName() {
    	return name;
    }

    private FinanceDhbEnum(String name) {
        this.name = name;
    }

    public static boolean isExist(String name){
    	boolean f = false;
        for(FinanceDhbEnum fe: FinanceDhbEnum.values()){
            if(fe.getName().equals(name)){
            	f = true;
            	break;
            }
        }
        return f;
    }

}
