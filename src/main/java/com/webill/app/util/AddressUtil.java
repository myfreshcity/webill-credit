package com.webill.app.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**   
 * @ClassName: NumberUtil   
 * @Description: 处理数字工具类  
 * @author: WangLongFei  
 * @date: 2017年7月27日 上午10:36:34      
 */
public class AddressUtil {

	private AddressUtil() {

	}

	/**   
	 * @Title: splidAddress   
	 * @Description: 处理地址数组为list  
	 * @author: WangLongFei  
	 * @date: 2017年8月10日 上午11:10:34   
	 * @param addressStr
	 * @return  
	 * @return: List<String>  
	 */
	public static List<String> splidAddress(String addressStr) {
		List<String> slist = new ArrayList<String>();
		if(!"".equals(addressStr)&&addressStr!=null){
			addressStr = addressStr.replaceAll("\"]$", "");
			addressStr = addressStr.replace("[\"", "");
			String[] address = addressStr.split("\",\"");
			Collections.addAll(slist, address);
		}
		return slist;
	}
	
}
