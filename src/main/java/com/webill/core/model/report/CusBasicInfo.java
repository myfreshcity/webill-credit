package com.webill.core.model.report;
/** 
 * @ClassName: Cus_Basic_Info 
 * @Description: 客户基本信息
 * @author ZhangYadong
 * @date 2018年1月24日 下午5:47:27 
 */
public class CusBasicInfo {
	/**
	 * 聚信立-对应application_check
	 */
	private String user_name; //登记姓名
	private Integer age; //年龄
	private String sex; //性别
	private String id_no; //身份证号
	private String residence_address; //户籍地址
	private String home_address; //居住地址（家庭地址）
	private String home_addr_check; //家庭地址检查
	private String work_address; //工作地址（暂无）
	private String work_addr_check; //工作地址检查
	private String website; //移动运营商
	private String reliability; //实名认证
	private String mobile_no; //手机号
	private String reg_time; //手机注册时间
	private String check_name; //姓名检查
	private String check_idcard; //身份证号检查
	private String check_address; //与登记地址检查
	/**
	 * 聚信立-对应behavior_check.(check_point="phone_silent")
	 */
	private String phone_silent_result; //手机静默结果
	private String phone_silent_evidence; //手机静默证据
	/**
	 * 聚信立-对应behavior_check.(check_point="contact_each_other")
	 */
	private String contact_each_other_evidence; //互通电话证据
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getResidence_address() {
		return residence_address;
	}
	public void setResidence_address(String residence_address) {
		this.residence_address = residence_address;
	}
	public String getHome_address() {
		return home_address;
	}
	public void setHome_address(String home_address) {
		this.home_address = home_address;
	}
	public String getWork_address() {
		return work_address;
	}
	public void setWork_address(String work_address) {
		this.work_address = work_address;
	}
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getReliability() {
		return reliability;
	}
	public void setReliability(String reliability) {
		this.reliability = reliability;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getReg_time() {
		return reg_time;
	}
	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}
	public String getCheck_name() {
		return check_name;
	}
	public void setCheck_name(String check_name) {
		this.check_name = check_name;
	}
	public String getCheck_idcard() {
		return check_idcard;
	}
	public void setCheck_idcard(String check_idcard) {
		this.check_idcard = check_idcard;
	}
	public String getPhone_silent_result() {
		return phone_silent_result;
	}
	public void setPhone_silent_result(String phone_silent_result) {
		this.phone_silent_result = phone_silent_result;
	}
	public String getPhone_silent_evidence() {
		return phone_silent_evidence;
	}
	public void setPhone_silent_evidence(String phone_silent_evidence) {
		this.phone_silent_evidence = phone_silent_evidence;
	}
	public String getContact_each_other_evidence() {
		return contact_each_other_evidence;
	}
	public void setContact_each_other_evidence(String contact_each_other_evidence) {
		this.contact_each_other_evidence = contact_each_other_evidence;
	}
	public String getCheck_address() {
		return check_address;
	}
	public void setCheck_address(String check_address) {
		this.check_address = check_address;
	}
	public String getHome_addr_check() {
		return home_addr_check;
	}
	public void setHome_addr_check(String home_addr_check) {
		this.home_addr_check = home_addr_check;
	}
	public String getWork_addr_check() {
		return work_addr_check;
	}
	public void setWork_addr_check(String work_addr_check) {
		this.work_addr_check = work_addr_check;
	}
	
}
