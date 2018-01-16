package com.webill.core.service;

import java.util.List;

import com.webill.core.model.LabelGroup;
import com.webill.framework.service.ISuperService;

/**
 *
 * LabelGroup 数据服务层接口
 *
 */
public interface ILabelGroupService extends ISuperService<LabelGroup> {
	
	/** 
	* @Title: labelGroupList 
	* @Description: 获取标签组列表，不包括导航标签
	* @author ZhangYadong
	* @date 2017年12月21日 上午11:32:28
	* @return
	* @return List<LabelGroup>
	*/
	public List<LabelGroup> labelGroupList();

}