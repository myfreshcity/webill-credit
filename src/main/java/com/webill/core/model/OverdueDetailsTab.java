/**
 * 
 */
package com.webill.core.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.webill.framework.annotations.FieldAnnotation;


/**
 * @author zhangjia
 * @createDate 2016年12月5日 上午10:47:31
 * @className OverdueDetailsTab
 * @classDescribe 逾期详情
 */
@Entity
@Table(name = "OVERDUE_DETAILS_TAB", uniqueConstraints = {})
public class OverdueDetailsTab {
	private Long id;
	private Integer itemId;
	private BigDecimal overdueAmount = BigDecimal.ZERO.setScale(2);
	private Integer overdueCount;
	private Integer overdueDay;
	
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
	@Column(name = "overdue_amount", precision = 20)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public BigDecimal getOverdueAmount() {
		return overdueAmount;
	}
	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
	}
	@Column(name = "overdue_count", length = 4)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public Integer getOverdueCount() {
		return overdueCount;
	}
	public void setOverdueCount(Integer overdueCount) {
		this.overdueCount = overdueCount;
	}
	@Column(name = "overdue_day", length = 4)
	@FieldAnnotation(insertable = false, updatable = false, listable = false, viewable = true, filterable = false, orderValue = 0)
	public Integer getOverdueDay() {
		return overdueDay;
	}
	public void setOverdueDay(Integer overdueDay) {
		this.overdueDay = overdueDay;
	}
	
}
