package com.webill.core.model;

import java.util.Date;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableId;
import com.webill.framework.annotations.TableName;

import io.swagger.annotations.ApiModelProperty;

@TableName("shown_car")
public class ShownCar{
	
	/** 展示编号，通过UUID产生，保持唯一 */
	@ApiModelProperty(value = "展示编号，通过UUID产生，保持唯一", required = true)
	@TableId(type = IdType.UUID)
	private String id;
	
	/** 展示人user_id,对应user表id */
	@ApiModelProperty(value = "展示人user_id,对应user表id", required = true)
	private Integer userId;
	
	/** 展示的车辆id集合，对应car_info表id，用英文“,”隔开 */
	@ApiModelProperty(value = "展示的车辆id集合，对应car_info表id，用英文“,”隔开", required = true)
	private String carIds;
	
	/** 创建时间 */
	@ApiModelProperty(value = "创建时间", required = true)
	private Date createdTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCarIds() {
		return carIds;
	}

	public void setCarIds(String carIds) {
		this.carIds = carIds;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	
}
