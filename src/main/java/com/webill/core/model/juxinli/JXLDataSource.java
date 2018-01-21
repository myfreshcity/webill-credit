package com.webill.core.model.juxinli;

import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: JXLDataSource
 * @Description:
 * @author ZhangYadong
 * @date 2018年1月18日 上午11:47:52
 */
public class JXLDataSource {

	private String id = null;
	
	private String website = null; //网站英文名称
	
	private String name = null;
	
	private String  category = null;
	
	private String categoryName = null;
	
	private Date createDate = null;
	
	private Date updateDate = null;
	
	private int offlineTimes;
	
	private int status;
	
	private int websiteCode;
	
	private int resetPasswordMethod;
	
	private int smsRequired;
	
	private int requiredCaptchaUserIdentified;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getOfflineTimes() {
		return offlineTimes;
	}

	public void setOfflineTimes(int offlineTimes) {
		this.offlineTimes = offlineTimes;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getWebsiteCode() {
		return websiteCode;
	}

	public void setWebsiteCode(int websiteCode) {
		this.websiteCode = websiteCode;
	}

	public int getResetPasswordMethod() {
		return resetPasswordMethod;
	}

	public void setResetPasswordMethod(int resetPasswordMethod) {
		this.resetPasswordMethod = resetPasswordMethod;
	}

	public int getSmsRequired() {
		return smsRequired;
	}

	public void setSmsRequired(int smsRequired) {
		this.smsRequired = smsRequired;
	}

	public int getRequiredCaptchaUserIdentified() {
		return requiredCaptchaUserIdentified;
	}

	public void setRequiredCaptchaUserIdentified(int requiredCaptchaUserIdentified) {
		this.requiredCaptchaUserIdentified = requiredCaptchaUserIdentified;
	}
	
	
	public JSONObject toJSONObject(){
		
		JSONObject json = new JSONObject();
		
		json.put("id", id);
		json.put("website", website);
		json.put("name", name);
		json.put("category", category);
		json.put("category_name", categoryName);
		json.put("status", status);
		json.put("reset_pwd_method", resetPasswordMethod);
		json.put("sms_required", smsRequired);
		json.put("offline_times", offlineTimes);
		json.put("website_code", websiteCode);
		json.put("required_captcha_user_identified", requiredCaptchaUserIdentified);

		JSONObject createDateJson = new JSONObject();
		
		Calendar createDateCalendar = Calendar.getInstance();
		
		createDateCalendar.setTime(createDate);
		
		createDateJson.put("year", createDateCalendar.get(Calendar.YEAR));
		createDateJson.put("month", createDateCalendar.get(Calendar.MONTH));
		createDateJson.put("dayOfMonth", createDateCalendar.get(Calendar.DAY_OF_MONTH));
		createDateJson.put("hourOfDay", createDateCalendar.get(Calendar.HOUR_OF_DAY));
		createDateJson.put("minute", createDateCalendar.get(Calendar.MINUTE));
		createDateJson.put("second", createDateCalendar.get(Calendar.SECOND));

		json.put("create_time", createDateJson);
		
		JSONObject updateDateJson = new JSONObject();
		
		Calendar updateDateCalendar = Calendar.getInstance();
		
		updateDateCalendar.setTime(updateDate);
		
		updateDateJson.put("year", updateDateCalendar.get(Calendar.YEAR));
		updateDateJson.put("month", updateDateCalendar.get(Calendar.MONTH));
		updateDateJson.put("dayOfMonth", updateDateCalendar.get(Calendar.DAY_OF_MONTH));
		updateDateJson.put("hourOfDay", updateDateCalendar.get(Calendar.HOUR_OF_DAY));
		updateDateJson.put("minute", updateDateCalendar.get(Calendar.MINUTE));
		updateDateJson.put("second", updateDateCalendar.get(Calendar.SECOND));
		
		json.put("update_time", updateDateJson);
		
		
		return json;
		
	}
	
	
	public static JXLDataSource fromJson(JSONObject json){
		JXLDataSource dataSource = new JXLDataSource();
		
		dataSource.setId(json.getString("id"));
		
		dataSource.setWebsite(json.getString("website"));
		
		dataSource.setName(json.getString("name"));
		
		dataSource.setCategory(json.getString("category"));
		
		dataSource.setCategoryName(json.getString("category_name"));
		
		dataSource.setOfflineTimes(json.getIntValue("offline_times"));
		
		dataSource.setStatus(json.getIntValue("status"));
		
		dataSource.setWebsiteCode(json.getIntValue("website_code"));
		
		dataSource.setResetPasswordMethod(json.getIntValue("reset_pwd_method"));
		
		dataSource.setSmsRequired(json.getIntValue("sms_required"));
		
		dataSource.setRequiredCaptchaUserIdentified(json.getIntValue("required_captcha_user_identified"));
		
		JSONObject createDateJson = json.getJSONObject("create_time");
		Calendar createDate = Calendar.getInstance();
		createDate.set(Calendar.YEAR, createDateJson.getIntValue("year"));
		createDate.set(Calendar.MONTH, createDateJson.getIntValue("month"));
		createDate.set(Calendar.DAY_OF_MONTH, createDateJson.getIntValue("dayOfMonth"));
		createDate.set(Calendar.HOUR_OF_DAY, createDateJson.getIntValue("hourOfDay"));
		createDate.set(Calendar.MINUTE, createDateJson.getIntValue("minute"));
		createDate.set(Calendar.SECOND, createDateJson.getIntValue("second"));
		dataSource.setCreateDate(createDate.getTime());
		
		
		JSONObject updateDateJson = json.getJSONObject("update_time");
		Calendar updateDate = Calendar.getInstance();
		updateDate.set(Calendar.YEAR, updateDateJson.getIntValue("year"));
		updateDate.set(Calendar.MONTH, updateDateJson.getIntValue("month"));
		updateDate.set(Calendar.DAY_OF_MONTH, updateDateJson.getIntValue("dayOfMonth"));
		updateDate.set(Calendar.HOUR_OF_DAY, updateDateJson.getIntValue("hourOfDay"));
		updateDate.set(Calendar.MINUTE, updateDateJson.getIntValue("minute"));
		updateDate.set(Calendar.SECOND, updateDateJson.getIntValue("second"));
		dataSource.setUpdateDate(updateDate.getTime());
		return dataSource;
	}
	
}
