package com.webill.app.util;


public class EnumUtil {
	
	/**   
	 * @Title: getStatusStr   
	 * @Description: 获取优惠券状态  
	 * @author: WangLongFei  
	 * @date: 2017年8月1日 上午11:51:02   
	 * @param status
	 * @return  
	 * @return: String  
	 */
	public  static String getStatusStr(Integer status) {
		String result ="";
		for(CouponStatus s:CouponStatus.values()){
			if(status.equals(s.getValue())){
				result = s.getDescription();
			}
		}
		return result;
	}
    
}
