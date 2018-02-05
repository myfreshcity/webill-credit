/**
 * @Copyright Atos Origin
 */
package com.webill.core.service.impl;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.util.WebUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

import rx.exceptions.Exceptions;



/**
 * For ApplicationContext,ServletContext wrapping.
 * Be initialized during context loading.
 * 
 * @author <a href="mailto:wangtao.pkuss@gmail.com">Wang Tao</a>
 * @since Nov 23, 2009
 */
public final class WorkbenchContext implements ApplicationContextAware,ServletContextAware{
	private static ApplicationContext applicationContext;
	private static ServletContext	servletContext;
	private static String dateFormat = "yyyy-MM-dd";
	private static String dateTimeFormat="yyyy-MM-dd HH:mm:ss";
//	private static String resourcePath = "resource/report/";
	private static String resourcePath = "WEB-INF/classes";

	private static String workbenchPath = "d:/workbench";
	public void setWorkbenchPath(String workbenchPath1) {
		WorkbenchContext.workbenchPath = workbenchPath1;
	}
	private WorkbenchContext() {}
	public static  DataSource getDataSource() {
	
		
		return (DataSource) applicationContext.getBean("dataSource");
	}
	
	public static boolean containsBean(String beanName){
		return applicationContext.containsBean(beanName);
	}
	
	public static boolean containsBean(Class clazz){
		return containsBean(mapBeanName(clazz));
	}
	
	public static  Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
	
	public static  Object getAutowiredBean(Class beanClass) {
		return applicationContext.getAutowireCapableBeanFactory().getBean(beanClass);
	}
	
	static String mapBeanName(Class clazz){
		String name = clazz.getName();
		int pos = name.lastIndexOf('.');
		if(pos > 0)
			name = name.substring(pos+1); 
		return name.substring(0,1).toLowerCase()+name.substring(1);		
	}
	/**
	 * Simple get bean by uncapitalized class name
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public static <T>  T getBean(Class<T> clazz) {
		return (T) applicationContext.getBean(StringUtils.uncapitalize(clazz.getSimpleName()));
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		WorkbenchContext.applicationContext=applicationContext;
	}

	public static ApplicationContext getApplicationContext(){
		return WorkbenchContext.applicationContext;
	}
	
	public void setServletContext(ServletContext arg0) {
		WorkbenchContext.servletContext=arg0;
	}
	private static Logger logger= LoggerFactory.getLogger(WorkbenchContext.class);


	public static String getResourcePath() throws Throwable {
		try {
			if(servletContext==null) {
				return ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
			}else {
				return WebUtils.getRealPath(servletContext, resourcePath);
			}
		} catch (Exception e) {
			logger.error("",e);
			throw Exceptions.getFinalCause(e);
		}
	}
	
	public static String getResourcePath(String subdirectory) throws Throwable {
		if (subdirectory == null)
			return getResourcePath();
		return pathWrap(getResourcePath(), subdirectory);
	}

	public static String getWorkbenchPath() throws Throwable {
		try {
			return workbenchPath;
		} catch (Exception e) {
			throw Exceptions.getFinalCause(e);
		}
	}

	public static String getWorkbenchPath(String subdirectory) throws Throwable {
		if (subdirectory == null)
			return getWorkbenchPath();
		return pathWrap(getWorkbenchPath(), subdirectory);
	}
 

	private static String pathWrap(String path, String subPath) {
		String tempPath = path + "/" + subPath;
		
		// 报表模板可能用到'\'路径符号，加入以下代码对原路径进行替换
		int i = 0;
		i = tempPath.length();
		while (true) {
			tempPath = tempPath.replaceAll("\\\\", "/");
			if (i == tempPath.length())
				break;
			else
				i = tempPath.length();
		}
		i = tempPath.length();
		while (true) {
			tempPath = tempPath.replaceAll("//", "/");
			if (i == tempPath.length())
				break;
			else
				i = tempPath.length();
		}
		
		return tempPath;
	}
	 
	public static String getDateFormat() {
		return dateFormat;
	}
	public static String getDateTimeFormat() {
		return dateTimeFormat;
	}
	 
	public static void setDateFormat(String dateFormat) {
		WorkbenchContext.dateFormat = dateFormat;
	}
	public static void setDateTimeFormat(String dateTimeFormat) {
		WorkbenchContext.dateTimeFormat = dateTimeFormat;
	}
	
	public static String findI18nText(String key) {
		return LocalizedTextUtil.findDefaultText(key, getLocale());
	}
	public static String findI18nErrorCode(String errorCode) {
		return LocalizedTextUtil.findDefaultText(errorCode, getLocale());
	}
	
	public static Locale getLocale() {
//		return Locale.SIMPLIFIED_CHINESE;	//modify by jayce 20121121
		if( ActionContext.getContext()==null) {
			return Locale.getDefault();
		}else {
			return ActionContext.getContext().getLocale();
		}
	}
	
	
	
}
