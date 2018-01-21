package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 客户信息商品表
 *
 */
@TableName("t_info_goods")
public class InfoGoods implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** id */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 用户信息等级：1-标准版 2-高级版 */
	private Integer infoLevel;

	/** 信息次数 */
	private Integer times;

	/** 金额 */
	private Long price;

	/** 状态： -1-逻辑删除 0-正常数据 */
	private Integer tStatus;

	/** 更新时间 */
	private Date updatedTime;

	/** 创建时间 */
	private Date createdTime;


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInfoLevel() {
		return this.infoLevel;
	}

	public void setInfoLevel(Integer infoLevel) {
		this.infoLevel = infoLevel;
	}

	public Integer getTimes() {
		return this.times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Long getPrice() {
		return this.price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getTStatus() {
		return this.tStatus;
	}

	public void setTStatus(Integer tStatus) {
		this.tStatus = tStatus;
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
