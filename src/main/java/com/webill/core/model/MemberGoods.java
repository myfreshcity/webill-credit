package com.webill.core.model;

import java.io.Serializable;
import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

/**
 *
 * 会员商品表
 *
 */
@TableName("t_member_goods")
public class MemberGoods implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** id */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 会员等级：0-试用会员 1-银卡会员 2-金卡会员 */
	private String mLevel;

	/** 会员时长，以月为单位 */
	private Integer mLength;

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

	public String getMLevel() {
		return this.mLevel;
	}

	public void setMLevel(String mLevel) {
		this.mLevel = mLevel;
	}

	public Integer getMLength() {
		return this.mLength;
	}

	public void setMLength(Integer mLength) {
		this.mLength = mLength;
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
