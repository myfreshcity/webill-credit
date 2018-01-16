package com.webill.app.util;

/** 
 * @ClassName: ApplicInsurRelation 
 * @Description: 慧择——附录3（被保人与投保人关系）
 * @author: WangLongFei
 * @date: 2017年12月8日 上午9:41:54  
 */
public enum ApplicInsurRelation {
	SELF(1, "本人"),
	WIFE(2, "妻子"),
	HUSBAND(3, "丈夫"),
	SUN(4, "儿子"),
	DAUGHTER(5, "女儿"),
	FATHER(6, "父亲"),
	MOTHER(7, "母亲"),
	BROTHER(8, "兄弟"),
	SISTERS(9, "姐妹"),
	GRANDPARENT(10, "祖父/祖母/外祖父/外祖母"),
	GRANDCHILD(11, "孙子/孙女/外孙/外孙女"),
	UNCLE(12, "叔父/伯父/舅舅"),
	AUNT(13, "婶/姨/姑"),
	NEPHEW_NIECES(14, "侄子/侄女/外甥/外甥女"),
	COUSINS(15, "堂兄弟/堂姐妹/表兄弟/表姐妹"),
	FATHER_IN_LAW(16, "岳父"),
	MOTHER_IN_LAW(17, "岳母"),
	CO_WORKER(18, "同事"),
	FRIEND(19, "朋友"),
	EMPLOYER(20, "雇主"),
	EMPLOYEE(21, "雇员"),
	LEGAL_GUARDIAN(22, "法定监护人"),
	OTHER(23, "其他");
    

    private int code;
    private String name;

    private ApplicInsurRelation(int code, String name) {
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
        for(ApplicInsurRelation ct: ApplicInsurRelation.values()){
            if(code==ct.getCode()){
                return ct.getName();
            }
        }
        return OTHER.getName();
    }
    public static int getCode(String name){
        for(ApplicInsurRelation ct: ApplicInsurRelation.values()){
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