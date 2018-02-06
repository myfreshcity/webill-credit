/**
 * 
 */
package com.webill.core.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.webill.framework.annotations.FieldAnnotation;

/**
 * @createDate 2016年12月5日 上午11:10:02
 * @className AddressDetectTab
 * @classDescribe 归属地详情
 */
@Entity
@Table(name = "ADDRESS_DETECT_TAB", uniqueConstraints = {})
public class AddressDetectTab {
	private Long id;
	private String reportId;
	private String idCardAddress;
	private String mobileAddress;
	private String trueIpAddress;
	private String wifiAddress;
	private String cellAddress;
	private String bankCardAddress;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "report_id", length = 24)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	@Column(name = "id_card_address", length = 128)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getIdCardAddress() {
		return idCardAddress;
	}
	public void setIdCardAddress(String idCardAddress) {
		this.idCardAddress = idCardAddress;
	}
	@Column(name = "mobile_address", length = 128)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getMobileAddress() {
		return mobileAddress;
	}
	public void setMobileAddress(String mobileAddress) {
		this.mobileAddress = mobileAddress;
	}
	@Column(name = "true_ip_address", length = 128)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getTrueIpAddress() {
		return trueIpAddress;
	}
	public void setTrueIpAddress(String trueIpAddress) {
		this.trueIpAddress = trueIpAddress;
	}
	@Column(name = "wifi_address", length = 128)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getWifiAddress() {
		return wifiAddress;
	}
	public void setWifiAddress(String wifiAddress) {
		this.wifiAddress = wifiAddress;
	}
	@Column(name = "cell_address", length = 128)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getCellAddress() {
		return cellAddress;
	}
	public void setCellAddress(String cellAddress) {
		this.cellAddress = cellAddress;
	}
	@Column(name = "bank_card_address", length = 128)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getBankCardAddress() {
		return bankCardAddress;
	}
	public void setBankCardAddress(String bankCardAddress) {
		this.bankCardAddress = bankCardAddress;
	}
}
