package com.webill.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 订单/投保单表
 *
 */
@TableName("t_order")
@JsonInclude(Include.NON_EMPTY)
public class TOrder implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** id */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 产品id对应product表的主键id */
	private String productId;

	/** 对应user表的主键id */
	@JSONField(serialize=false)
	private Integer userId;
	
	/** 对应other_info表的主键id */
	private Integer otherInfoId;

	/** 订单号，齐欣的交易流水号 */
	private String transNo;

	/** 投保单号 */
	private String inOrderNo;

	/** 总份数 */
	private Integer totalNum;

	/** 起保日期 格式：yyyy-MM-dd */
	@JSONField(format="yyyy-MM-dd") 
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date startDate;

	/** 终保日期 格式：yyyy-MM-dd */
	@JSONField(format="yyyy-MM-dd")  
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date endDate;

	/** 保障期限 */
	private String deadline;

	/** 保障期限说明 */
	private String deadlineText;

	/** 出单状态 0：未出单 1：已出单 2：延时出单 3：取消出单 */
	private Integer issueStatus;

	/** 生效（退保）状态 0：未生效 1：已生效 2：退保中 3：已退保 */
	private Integer effectiveStatus;

	/** 支付状态 0：未支付 1：已支付 2：不能支付 3：扣款中 4：扣款失败 5：扣款成功 */
	private Integer payStatus;
	
	/** 保单总保费（单位：分） */
	private Long payAmount;

	/** 产品二级分类名称 */
	private String categoryName;

	/** 投保时间 格式：yyyy-MM-dd HH:mm:ss */
	private Date insureTime;

	/** 成功支付完成时间 */
	private Date payTime;

	/** 出单时间,保险公司承保时间 */
	private Date orderIssueTime;

	/** 生成保单时间 */
	private Date insureIssueTime;

	/** 保单下载地址 */
	private String insureDownUrl;
	
	/** 订单状态：-1：已删除、0：默认状态、10：待付款、15：待发货、20：已出单、30：已完成、40：已关闭、90：已失效 */
	private Integer tStatus;
	
	/** 客户端类型 0：默认 1：PC，2：H5 */
	private Integer clientType;
	
	/** 支付类型 0：默认 1：支付宝 3：银联 21:微信 */
	private Integer gateWay;
	
	/** 备注 */
	private String remark;
	
	/** 更新时间 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedTime;

	/**  */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdTime;

	/** 保险公司保单号 */
	@TableField(exist=false)
	private String policyNum;
	
	/** 证件名称 */
	@TableField(exist=false)
	private String cardName;
	
	@TableField(exist=false)
	private String cName;
	
	@TableField(exist=false)
	@JSONField(serialize=false)
	private Integer autoPay;
	
	@TableField(exist=false)
	private String mobile;
	
	/** 产品名字 */
	@TableField(exist=false)
	private String prodName;
	
	/** 产品描述 */
	@TableField(exist=false)
	private String description;
	
	/** 产品CODE */
	@TableField(exist=false)
	private String caseCode;
	
	/** 图片路径*/
	@TableField(exist=false)
	private String imgUrlShow;

	@TableField(exist=false)
	@JSONField(serialize=false)  
	private OrderApplicant applicant;
	
	@TableField(exist=false)
	@JSONField(serialize=false)  
	private List<OrderInsurant> insurantList = new ArrayList<OrderInsurant>();
	
	@TableField(exist=false)
	private Product product;
	
	@TableField(exist=false)
	@JSONField(serialize=false)
	private List<ProductProtectItem> itemList = new ArrayList<ProductProtectItem>();
	
	@TableField(exist=false)
	@JSONField(serialize=false)
	private List<OrderEnsureProject> projectList = new ArrayList<OrderEnsureProject>();
	
	@TableField(exist=false)
	@JSONField(serialize=false)  
	private List<OrderLog> orderLogList = new ArrayList<OrderLog>();
	
	
	public Integer getAutoPay() {
		return autoPay;
	}

	public void setAutoPay(Integer autoPay) {
		this.autoPay = autoPay;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public List<ProductProtectItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<ProductProtectItem> itemList) {
		this.itemList = itemList;
	}

	public OrderApplicant getApplicant() {
		return applicant;
	}

	public void setApplicant(OrderApplicant applicant) {
		this.applicant = applicant;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public List<OrderLog> getOrderLogList() {
		return orderLogList;
	}

	public void setOrderLogList(List<OrderLog> orderLogList) {
		this.orderLogList = orderLogList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<OrderInsurant> getInsurantList() {
		return insurantList;
	}

	public void setInsurantList(List<OrderInsurant> insurantList) {
		this.insurantList = insurantList;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public Integer getGateWay() {
		return gateWay;
	}

	public void setGateWay(Integer gateWay) {
		this.gateWay = gateWay;
	}

	public String getPolicyNum() {
		return policyNum;
	}

	public void setPolicyNum(String policyNum) {
		this.policyNum = policyNum;
	}

	public Integer gettStatus() {
		return tStatus;
	}

	public void settStatus(Integer tStatus) {
		this.tStatus = tStatus;
	}

	public Integer getOtherInfoId() {
		return otherInfoId;
	}

	public void setOtherInfoId(Integer otherInfoId) {
		this.otherInfoId = otherInfoId;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTransNo() {
		return this.transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getInOrderNo() {
		return this.inOrderNo;
	}

	public void setInOrderNo(String inOrderNo) {
		this.inOrderNo = inOrderNo;
	}

	public Integer getTotalNum() {
		return this.totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDeadline() {
		return this.deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getDeadlineText() {
		return this.deadlineText;
	}

	public void setDeadlineText(String deadlineText) {
		this.deadlineText = deadlineText;
	}

	public Integer getIssueStatus() {
		return this.issueStatus;
	}

	public void setIssueStatus(Integer issueStatus) {
		this.issueStatus = issueStatus;
	}

	public Integer getEffectiveStatus() {
		return this.effectiveStatus;
	}

	public void setEffectiveStatus(Integer effectiveStatus) {
		this.effectiveStatus = effectiveStatus;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}


	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Date getInsureTime() {
		return this.insureTime;
	}

	public void setInsureTime(Date insureTime) {
		this.insureTime = insureTime;
	}

	
	public String getCardName() {
		return this.cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}


	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getOrderIssueTime() {
		return this.orderIssueTime;
	}

	public void setOrderIssueTime(Date orderIssueTime) {
		this.orderIssueTime = orderIssueTime;
	}

	public Date getInsureIssueTime() {
		return this.insureIssueTime;
	}

	public void setInsureIssueTime(Date insureIssueTime) {
		this.insureIssueTime = insureIssueTime;
	}

	public String getInsureDownUrl() {
		return this.insureDownUrl;
	}

	public void setInsureDownUrl(String insureDownUrl) {
		this.insureDownUrl = insureDownUrl;
	}

	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getImgUrlShow() {
		return imgUrlShow;
	}

	public void setImgUrlShow(String imgUrlShow) {
		this.imgUrlShow = imgUrlShow;
	}

	public List<OrderEnsureProject> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<OrderEnsureProject> projectList) {
		this.projectList = projectList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
