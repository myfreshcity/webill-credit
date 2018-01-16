package com.webill.app.util;

/** 
 * @ClassName: ApplicInsurRelation 
 * @Description: 慧择——附录3（被保人与投保人关系）
 * @author: WangLongFei
 * @date: 2017年12月8日 上午9:41:54  
 */
public enum MarryStateUtil {
	MARRIED(1, "本人"),
	UNMARRIED(2, "妻子"),
	DIVORCED(3, "丈夫"),
	WIDOWED(4, "儿子"),
	OTHER(5, "女儿");

    private int code;
    private String name;

    private MarryStateUtil(int code, String name) {
        this.code = code;
        this.name = name;
    }
    public int getCode() {
        return code;
    }
    public String getName() {
        return name;
    }

    public String getName(int code){
        for(MarryStateUtil ct: MarryStateUtil.values()){
            if(code==ct.getCode()){
                return ct.getName();
            }
        }
        return OTHER.getName();
    }
    public static int getCode(String name){
        for(MarryStateUtil ct: MarryStateUtil.values()){
        	if(ct.getName().contains("/")){
        		String[] regexs =ct.getName().split("/");
        		for(String reg:regexs){
        			if(name.matches(reg)){
        				return ct.getCode();
        			}
        		}
        	}else{
        		if(name.matches(ct.getName())){
        			return ct.getCode();
    			}
        	}
        }
        return OTHER.getCode();
    }
    
}