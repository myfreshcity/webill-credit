package com.webill.app.util;

import java.util.Random;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**   
 * @ClassName: NumberUtil   
 * @Description: 处理数字工具类  
 * @author: WangLongFei  
 * @date: 2017年7月27日 上午10:36:34      
 */
public class NumberUtil {
	private static final Logger logger = Logger.getLogger(NumberUtil.class);

	private NumberUtil() {

	}

	/**
	 * 验证是否为整数
	 * @param str 要验证的整数
	 * @return	true or false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	/**
	 * 验证是否为小数
	 * @param str 要验证的小数
	 * @return	true or false
	 */
	public static boolean isDecimal(String str) {
		Pattern pattern = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");
		return pattern.matcher(str).matches();
	}
	/**
	 * 验证是否为正整数
	 * @param str 要验证的正整数
	 * @return	true or false
	 */
	public static boolean isPosNumber(String str){
		boolean f;
		try {
			//把字符串强制转换为数字
			int num=Integer.parseInt(str);
			//如果是数字，返回True
			if(num>0){
				f=true;
			}else{
				f=false;
			}
		} catch (Exception e) {
			//如果抛出异常，返回False
			logger.error("验证内容不是正整数！", e);
			return false;
		}
		return f;
	}

	/**
	 * 验证是否为正整数
	 * @param str 要验证的正整数
	 * @return	true or false
	 */
	public static boolean isNumber(String str){
		boolean f;
		try {
			//把字符串强制转换为数字
			double num=Double.parseDouble(str);
			//如果是数字，返回True
			if(num>0){
				f=true;
			}else{
				f=false;
			}
		} catch (Exception e) {
			//如果抛出异常，返回False
			logger.error("验证内容不是正整数！", e);
			return false;
		}
		return f;
	}
	/**
	 * 格式化数字（四舍五入保留两位小数）
	 * @param num 需格式化的数字
	 * @return String
	 */
	public static String formatNumber(double num){
		String value = String.format("%.2f", num);
		return value;
	}

	/**
	 * 获取某个范围内的一个随机数
	 * @param min 范围最小值
	 * @param max 范围最大值
	 * @return	String
	 */
	public static String getRandom(int min, int max)
	{
		if(max <= 0){
			return Integer.toString(max);
		}
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return String.valueOf(s);
	}
}
