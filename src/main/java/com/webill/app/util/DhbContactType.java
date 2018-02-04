package com.webill.app.util;

/** 
 * @ClassName: DhbContactType 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月25日 下午3:11:59 
 */
public enum DhbContactType {
	//"0":配偶，"1":父母，"2":兄弟姐妹,"3":子女,"4":同事,"5": 同学,"6": 朋友
	pl(0, "配偶"), 
	fm(1, "父母"), 
	xdjm(2, "兄弟姐妹"),
	zn(3, "子女"),
	ts(4, "同事"),
	tx(5, "同学"),
	py(6, "朋友");

	private Integer key;
    private String value;
    
    public Integer getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}

	
    private DhbContactType(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

    public static String retValue(Integer key){
    	String relationShip = null;
        for(DhbContactType dct: DhbContactType.values()){
            if(dct.getKey().equals(key)){
            	relationShip = dct.getValue();
            }
        }
        return relationShip;
    }
    
}
