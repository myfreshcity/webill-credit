package com.webill.app.util;
/** 
* @ClassName: CategoryEnums 
* @Description: 
* @author ZhangYadong
* @date 2017年12月1日 下午1:41:25 
*/
public enum CategoryEnum {
	YWX(2000, "意外保险"),
	RSY(2018, "人身意外保险"),
	JTY(2016, "交通意外保险"),
	HKY(2017, "航空意外保险"),
	
	LYX(2001, "旅游保险"),
	SQX(2019, "申根签证保险"),
	JWL(2020, "境外旅行保险"),
	GLB(2021, "国内旅游保险"),
	CLX(2022, "出国留学保险"),
	HWY(2041, "户外运动保险"),
	
	JCX(2008, "家财保险"),
	ZJX(2034, "自住型家财险"),
	CZJ(2035, "出租型家财险"),
	CJX(2036, "承租型家财险"),
	WZJ(2037, "网店专用型家财险"),
	
	JKX(2002, "健康保险"),
	ZDX(2027, "重大疾病保险"),
	ZYX(2028, "住院医疗保险"),
	HLX(2025, "护理保险"),
	JBX(2026, "疾病保险"),
	
	RSX(2006, "人寿保险"),
	DQX(2029, "定期寿险"),
	ZSX(2030, "终身寿险"),
	LQX(2032, "两全保险");
	
	public final Integer code;
	public final String value;
	
	public static String getValue(int code) {
		for (CategoryEnum ce : CategoryEnum.values()) {
			if (ce.getCode() == code) {
				return ce.getValue();
			}
		}
		return null;
	}

	public Integer getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	private CategoryEnum(Integer code, String value) {
		this.code = code;
		this.value = value;
	}

}
