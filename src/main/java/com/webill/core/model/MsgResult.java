package com.webill.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 询价返回对象
 *
 */
public class MsgResult{

	private int isSuccess;
	
	private String ciInsurerCom;
	
	private String carNo;
	
	private List<PremiumDetail> detailList = new ArrayList<PremiumDetail>();

	
	public String getCiInsurerCom() {
		return ciInsurerCom;
	}

	public void setCiInsurerCom(String ciInsurerCom) {
		this.ciInsurerCom = ciInsurerCom;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public int getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}

	public List<PremiumDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PremiumDetail> detailList) {
		this.detailList = detailList;
	}




}
