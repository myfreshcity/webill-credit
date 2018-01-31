package com.webill.core.model.lianlianEntity;

import java.io.Serializable;

/** 
* @ClassName: ConfirmPayResBean 
* @Description: 
* @author ZhangYadong
* @date 2017年11月17日 上午9:42:31 
*/
public class ConfirmPayResBean extends BaseResBean implements Serializable{
	private static final long serialVersionUID = 1L;

	/** 连连银通付款流水号 . */
	public String oid_paybill;

	/** 商户付款流水号 . */
	private String no_order;

	public String getNo_order() {
		return no_order;
	}

	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}

	public String getOid_partner() {
		return oid_partner;
	}

	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}

	public String getOid_paybill() {
		return oid_paybill;
	}

	public void setOid_paybill(String oid_paybill) {
		this.oid_paybill = oid_paybill;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConfirmPaymentResponseBean [oid_paybill=");
		builder.append(oid_paybill);
		builder.append(", no_order=");
		builder.append(no_order);
		builder.append(", ret_code=");
		builder.append(ret_code);
		builder.append(", ret_msg=");
		builder.append(ret_msg);
		builder.append(", sign_type=");
		builder.append(sign_type);
		builder.append(", sign=");
		builder.append(sign);
		builder.append(", oid_partner=");
		builder.append(oid_partner);
		builder.append("]");
		return builder.toString();
	}
}
