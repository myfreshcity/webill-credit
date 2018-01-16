package com.webill.app.util;

import com.webill.core.Constant;

/**   
 * @ClassName: OrderUtils   
 * @Description: 订单工具类  
 * @author: WangLongFei  
 * @date: May 6, 2017 3:23:03 PM      
 */
public class OrderUtils {
	
	public static String genOrderNo(String flag,String orderId){
		StringBuffer orderNo = new StringBuffer("");
		orderNo.append("mmh");
		if(StringUtil.isNotEmpty(orderId)){
			if(Constant.ORDER_NO_LENGTH-3- orderId.length()>0){
				int num = Constant.ORDER_NO_LENGTH-3- orderId.length();
				for(int i=0;i<num;i++){
					orderNo.append("0");
				}
				orderNo.append(orderId);
			}else{
				orderNo.append(orderId);
			}
		}
		return orderNo.toString();
	}

}
