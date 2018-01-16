package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 保障项目表 
 *
 */
@TableName("order_ensure_project")
public class OrderEnsureProject implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** id */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 订单id对应order表的主键id */
	private Integer orderId;

	/** 保障项目名称 */
	private String projectName;

	/** 保额 */
	private Long sumInsured;

	/** 保额单位 1：万元 2：元/天 3：万元/年 */
	private String unitText;

	/** 保障开始时间 格式：yyyy-MM-dd */
	private Date startDate;

	/** 保障结束时间 格式：yyyy-MM-dd */
	private Date endDate;

	/** 保障内容说明 */
	private String insuredText;

	/** 高保额生效状态 0：未生效 1：已生效 */
	private Integer valid;

	/** 更新时间  */
	private Date updatedTime;

	/**  */
	private Date createdTime;


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getSumInsured() {
		return this.sumInsured;
	}

	public void setSumInsured(Long sumInsured) {
		this.sumInsured = sumInsured;
	}

	public String getUnitText() {
		return this.unitText;
	}

	public void setUnitText(String unitText) {
		this.unitText = unitText;
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

	public String getInsuredText() {
		return this.insuredText;
	}

	public void setInsuredText(String insuredText) {
		this.insuredText = insuredText;
	}

	public Integer getValid() {
		return this.valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
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

}
