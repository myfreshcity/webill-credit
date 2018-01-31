package com.webill.app.util;

import java.util.Random;

/**
 * @ClassName: TransNoUtil
 * @Description:
 * @author ZhangYadong
 * @date 2017年10月31日 上午9:48:29
 */
public class TransNoUtil {

	/**
	 * @Title: genTransNo
	 * @Description: 微账房-认证支付请求交易流水号
	 * @author ZhangYadong
	 * @date 2017年11月4日 上午10:39:01
	 * @return
	 * @return String
	 */
	public static String genRzTransNo() {// 22位
		// 取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		// 加上5位随机数
		Random random = new Random();
		int end5 = random.nextInt(99999);
		// 如果不足5位前面补0
		String str = "RZZF" + millis + String.format("%05d", end5);
		return str;
	}

	/** 
	* @Title: genTransNo 
	* @Description: 齐欣-请求流水号（每次接口请求流水号不能重复）
	* @author ZhangYadong
	* @date 2017年11月4日 上午10:39:01
	* @return
	* @return String
	*/
	public static String genTransNo(){//25位 baoding
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//加上5位随机数
		Random random = new Random();
		int end5 = random.nextInt(99999);
		//如果不足5位前面补0
		String str = "baoding" + millis + String.format("%05d", end5);
		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(genRzTransNo()); 
	}
}
