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
 * @author zhangjia
 * @createDate 2016年12月5日 上午10:37:37
 * @className ItemDetailTab
 * @classDescribe 检查项详情
 */
@Entity
@Table(name = "ITEM_DETAIL_TAB", uniqueConstraints = {})
public class ItemDetailTab {
	private Long id;
	private Integer itemId;
	private Integer discreditTimes;
	private Integer platformCount;
	private String fraudType;
	private String type;
	
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
	@Column(name = "item_id", length = 4)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	@Column(name = "discredit_times", length = 4)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public Integer getDiscreditTimes() {
		return discreditTimes;
	}
	public void setDiscreditTimes(Integer discreditTimes) {
		this.discreditTimes = discreditTimes;
	}
	@Column(name = "platform_count", length = 4)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public Integer getPlatformCount() {
		return platformCount;
	}
	public void setPlatformCount(Integer platformCount) {
		this.platformCount = platformCount;
	}
	@Column(name = "fraud_type", length = 50)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getFraudType() {
		return fraudType;
	}
	public void setFraudType(String fraud_type) {
		this.fraudType = fraud_type;
	}
	@Column(name = "type", length = 20)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
