package com.webill.core.service.mapper;

import java.util.List;

import com.webill.core.model.Label;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * Label 表数据库控制层接口
 *
 */
public interface LabelMapper extends AutoMapper<Label> {
	/** 
	* @Title: getNavLabel 
	* @Description: 获取导航标签列表
	* @author ZhangYadong
	* @date 2017年12月12日 下午4:48:26
	* @return
	* @return List<Label>
	*/
	public List<Label> getNavLabel();
	/** 
	* @Title: labelListByGroupId 
	* @Description: 通过标签组ID获取相应标签列表 
	* @author ZhangYadong
	* @date 2017年12月21日 上午11:25:57
	* @param id
	* @return
	* @return List<Label>
	*/
	public List<Label> labelListByGroupId(Integer id);
}