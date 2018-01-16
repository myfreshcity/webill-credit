package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * 用户表
 *
 */
@TableName("user")
public class User implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 微信ID */
	private String openId;

	/** 开放平台唯一ID */
	private String unionId;

	/** 微信昵称 */
	private String weixinNick;

	/** 微信头像地址 */
	private String headUrl;

	/** 用户登录名,登录用手机号 */
	private String username;

	/** 用户密码 */
	private String password;

	/** 必填  中文名 */
	private String cName;

	/** 拼音或英文名，境外旅游险必填 */
	private String eName;

	/** 必填  证件类型，证件类型：1：身份证、2:护照、3：出生证、4：驾照、5：港澳通行证、6：军官证、7：台胞证、8：警官证、9：港澳台回乡证10：组织机构代码证、11：营业执照、12：税务登记号、13：三证合一、14：统一信用代码证、99：其他、 */
	private String cardType;

	/** 必填  证件号 */
	private String cardCode;

	/** 必填  性别 0：女 1：男 */
	private String sex;

	/** 必填  出生日期 格式：yyyy-MM-dd */
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date birthday;

	/** 必填  手机号码 */
	private String mobile;

	/** 必填  邮箱 */
	private String email;

	/** 职业信息，职业id使用“-”拼接如：101414-101415-101416 */
	private String job;

	/** 国籍 */
	private String country;

	/** 居住省市，地区编码使用“-”拼接如：320000-320100-320104 */
	private String provCityId;

	/** 联系地址 */
	private String contactAddress;

	/** 联系地址邮编 */
	private String contactPost;

	/** 投保人类型 0：个人（默认） 1：公司 */
	private String applicantType;

	/** 证件有效期，格式yyyy-MM-dd */
	private Date cardPeriod;

	/** 婚姻状态 1：已婚 2：未婚 3：离婚 4：丧偶 5：其他 */
	private String marryState;

	/** 年收入 */
	private String yearlyIncome;

	/** 工作单位地址（办公地址） */
	private String officeAddress;

	/**   工作单位电话（办公电话） */
	private String tel;

	/** 工作单位名称 */
	private String workCompanyName;

	/** 工作单位邮箱 */
	private String workEmail;

	/** 办公地址邮编 */
	private String officePost;

	/** 是否有医保 0：否 1：是 */
	private String haveMedical;

	/** 身高 */
	private String height;

	/** 体重 */
	private String weight;

	/** 是否为企业员工 0:否，1:是 */
	private Integer isStaff;

	/** 推荐人ID，关联user表ID */
	private Integer recommendId;

	/** 状态 -1:删除，0:正常 */
	private Integer tStatus;

	/** 是否已关注：0-否；1-是 */
	private Integer subscribeFlag;

	/** 自动登录状态：0-否；1-是 */
	private Integer loginFlag;

	/**  */
	private Date createdTime;
	
	
	@TableField(exist = false)
	private String timeFrom;
	
	@TableField(exist = false)
	private String timeTo;
	
	@TableField(exist = false)
	private String recommendName;
	
	@ApiModelProperty(value = "用户输入的手机验证码", required = true)
	@TableField(exist = false)
	private String inCode;
	
	@TableField(exist = false)
	private String checkFlag;
	
	@TableField(exist = false)
	private String jwtToken;


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnionId() {
		return this.unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getWeixinNick() {
		return this.weixinNick;
	}

	public void setWeixinNick(String weixinNick) {
		this.weixinNick = weixinNick;
	}

	public String getHeadUrl() {
		return this.headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCName() {
		return this.cName;
	}

	public void setCName(String cName) {
		this.cName = cName;
	}

	public String getEName() {
		return this.eName;
	}

	public void setEName(String eName) {
		this.eName = eName;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardCode() {
		return this.cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvCityId() {
		return this.provCityId;
	}

	public void setProvCityId(String provCityId) {
		this.provCityId = provCityId;
	}

	public String getContactAddress() {
		return this.contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactPost() {
		return this.contactPost;
	}

	public void setContactPost(String contactPost) {
		this.contactPost = contactPost;
	}

	public String getApplicantType() {
		return this.applicantType;
	}

	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}

	public Date getCardPeriod() {
		return this.cardPeriod;
	}

	public void setCardPeriod(Date cardPeriod) {
		this.cardPeriod = cardPeriod;
	}

	public String getMarryState() {
		return this.marryState;
	}

	public void setMarryState(String marryState) {
		this.marryState = marryState;
	}

	public String getYearlyIncome() {
		return this.yearlyIncome;
	}

	public void setYearlyIncome(String yearlyIncome) {
		this.yearlyIncome = yearlyIncome;
	}

	public String getOfficeAddress() {
		return this.officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getWorkCompanyName() {
		return this.workCompanyName;
	}

	public void setWorkCompanyName(String workCompanyName) {
		this.workCompanyName = workCompanyName;
	}

	public String getWorkEmail() {
		return this.workEmail;
	}

	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}

	public String getOfficePost() {
		return this.officePost;
	}

	public void setOfficePost(String officePost) {
		this.officePost = officePost;
	}

	public String getHaveMedical() {
		return this.haveMedical;
	}

	public void setHaveMedical(String haveMedical) {
		this.haveMedical = haveMedical;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Integer getIsStaff() {
		return this.isStaff;
	}

	public void setIsStaff(Integer isStaff) {
		this.isStaff = isStaff;
	}

	public Integer getRecommendId() {
		return this.recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

	public Integer getSubscribeFlag() {
		return this.subscribeFlag;
	}

	public void setSubscribeFlag(Integer subscribeFlag) {
		this.subscribeFlag = subscribeFlag;
	}

	public Integer getLoginFlag() {
		return this.loginFlag;
	}

	public void setLoginFlag(Integer loginFlag) {
		this.loginFlag = loginFlag;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public Integer gettStatus() {
		return tStatus;
	}

	public void settStatus(Integer tStatus) {
		this.tStatus = tStatus;
	}

	public String getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public String getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	public String getRecommendName() {
		return recommendName;
	}

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}

	public String getInCode() {
		return inCode;
	}

	public void setInCode(String inCode) {
		this.inCode = inCode;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	
}
