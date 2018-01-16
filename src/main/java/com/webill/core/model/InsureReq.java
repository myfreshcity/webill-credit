package com.webill.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.webill.framework.annotations.TableField;

/** 
 * @ClassName: InsureReq 
 * @Description: 承保所需参数
 * @author: WangLongFei
 * @date: 2017年12月1日 下午4:27:51  
 */
@JsonInclude(Include.NON_EMPTY)
public class InsureReq extends TOrder{
	private String partnerId;
	
	@JSONField(serialize = false)
	private String productId;
	
	private String transNo;
	
	private String caseCode;
	
	@JSONField(format="yyyy-MM-dd")
	private Date startDate;
	
	private OrderApplicant applicant;
	
	private List<OrderInsurant> insurants = new ArrayList<OrderInsurant>();
	
	private PriceArgs priceArgs;
	
	private OtherInfo otherInfo;
	
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public OrderApplicant getApplicant() {
		return applicant;
	}
	public void setApplicant(OrderApplicant applicant) {
		this.applicant = applicant;
	}
	public List<OrderInsurant> getInsurants() {
		return insurants;
	}
	public void setInsurants(List<OrderInsurant> insurants) {
		this.insurants = insurants;
	}
	public PriceArgs getPriceArgs() {
		return priceArgs;
	}
	public void setPriceArgs(PriceArgs priceArgs) {
		this.priceArgs = priceArgs;
	}
	public OtherInfo getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(OtherInfo otherInfo) {
		this.otherInfo = otherInfo;
	}

	

}
