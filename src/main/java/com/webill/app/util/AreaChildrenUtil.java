package com.webill.app.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.webill.app.SystemProperty;
import com.webill.core.model.AreaChildren;

/**   
 * @ClassName: AreaChildrenUtil   
 * @Description: 处理数字工具类  
 * @author: WangLongFei  
 * @date: 2017年7月27日 上午10:36:34      
 */
@Component
public class AreaChildrenUtil {
    @Autowired
    protected SystemProperty constPro;
    
	private AreaChildrenUtil() {

	}

	/** 
	 * @Title: getAreaName 
	 * @Description: 根据区域id获取区域name
	 * @author: WangLongFei
	 * @date: 2017年12月4日 上午10:29:12 
	 * @param value
	 * @return
	 * @return: String
	 */
	public  String getLable(String value) {
		String[] values = value.split("-");
		List<AreaChildren> children = JSONArray.parseArray(constPro.AREA_JSON, AreaChildren.class);
		String result = null;
		int i = values.length-1;
		if(values[i]!=null){
			for (AreaChildren area : children) {
				if(values[i].equals(area.getValue())){
					result = area.getLabel();
				}
				if(area.getArea()!=null){
					if(value.equals(area.getArea().getValue())){
						result = area.getArea().getLabel();
					}
				}
				if(area.getChildren()!=null){
					for (AreaChildren aa : area.getChildren()) {
						if(values[i].equals(aa.getValue())){
							result = area.getLabel()+aa.getLabel();
						}
						for(AreaChildren a : aa.getChildren()){
							if(values[i].equals(a.getValue())){
								if(!aa.getLabel().equals("市辖区")){
									result =area.getLabel()+aa.getLabel()+ a.getLabel();
								}else{
									result =area.getLabel()+ a.getLabel();
								}
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	/** 
	 * @Title: getValue 
	 * @Description: 根据区域name获取区域id
	 * @author: WangLongFei
	 * @date: 2017年12月4日 上午10:29:12 
	 * @param lable
	 * @return
	 * @return: String
	 */
	public String getValue(String lable) {
		String[] lables = lable.split("-");
		List<AreaChildren> children = JSONArray.parseArray(constPro.AREA_JSON, AreaChildren.class);
		String result = null;
		int i = lables.length-1;
		if(lables[i]!=null){
			for (AreaChildren area : children) {
				if(lables[i].equals(area.getLabel())){
					result = area.getValue();
				}
				if(area.getArea()!=null){
					if(lables[i].equals(area.getArea().getLabel())){
						result = area.getArea().getValue();
					}
				}
				if(area.getChildren()!=null){
					for (AreaChildren aa : area.getChildren()) {
						if(lables[i].equals(aa.getLabel())){
							result = area.getValue()+"-"+aa.getValue();
						}
						for(AreaChildren a : aa.getChildren()){
							if(lables[i].equals(a.getLabel())){
								if(i==1){
									result =area.getValue()+ "-"+a.getValue();
								}else{
									result =area.getValue()+"-"+aa.getValue()+"-"+ a.getValue();
								}
							}
						}
					}
				}
			}
		}
		return result;
	}
}
