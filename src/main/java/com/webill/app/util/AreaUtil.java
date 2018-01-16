package com.webill.app.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.webill.app.SystemProperty;
import com.webill.core.model.Area;

/**   
 * @ClassName: AreaUtil   
 * @Description: 处理数字工具类  
 * @author: WangLongFei  
 * @date: 2017年7月27日 上午10:36:34      
 */
@Component
public class AreaUtil {
    @Autowired
    protected SystemProperty constPro;
    
	private AreaUtil() {

	}

	/** 
	 * @Title: getAreaName 
	 * @Description: 根据区域id获取区域name
	 * @author: WangLongFei
	 * @date: 2017年12月4日 上午10:29:12 
	 * @param areaId
	 * @return
	 * @return: String
	 */
	public  String getAreaName(String areaId) {
		String[] areaIds = areaId.split("-");
		List<Area> areaList = JSONArray.parseArray(constPro.AREA_JSON, Area.class);
		String result = null;
		if(areaId!=null){
			for (Area area : areaList) {
				if(areaId.equals(area.getAreaId())){
					result = area.getAreaName();
				}
				if(area.getArea()!=null){
					if(areaId.equals(area.getArea().getAreaId())){
						result = area.getArea().getAreaName();
					}
				}
				if(area.getAearList()!=null){
					for (Area aa : area.getAearList()) {
						if(areaId.equals(aa.getAreaId())){
							result = area.getAreaName()+aa.getAreaName();
						}
						for(Area a : aa.getAearList()){
							if(areaId.equals(a.getAreaId())){
								if(!aa.getAreaName().equals("市辖区")){
									result =area.getAreaName()+aa.getAreaName()+ a.getAreaName();
								}else{
									result =area.getAreaName()+ a.getAreaName();
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
	 * @Title: getAreaName 
	 * @Description: 根据区域id获取区域name
	 * @author: WangLongFei
	 * @date: 2017年12月4日 上午10:29:12 
	 * @param areaId
	 * @return
	 * @return: String
	 */
	public String getAreaId(String areaName) {
		String[] areaNames = areaName.split("-");
		List<Area> areaList = JSONArray.parseArray(constPro.AREA_JSON, Area.class);
		String result = null;
		int i = areaNames.length-1;
		if(areaNames[i]!=null){
			for (Area area : areaList) {
				if(areaNames[i].equals(area.getAreaName())){
					result = area.getAreaId();
				}
				if(area.getArea()!=null){
					if(areaNames[i].equals(area.getArea().getAreaName())){
						result = area.getArea().getAreaId();
					}
				}
				if(area.getAearList()!=null){
					for (Area aa : area.getAearList()) {
						if(areaNames[i].equals(aa.getAreaName())){
							result = area.getAreaId()+"-"+aa.getAreaId();
						}
						for(Area a : aa.getAearList()){
							if(areaNames[i].equals(a.getAreaName())){
								if(i==1){
									result =area.getAreaId()+ "-"+a.getAreaId();
								}else{
									result =area.getAreaId()+"-"+aa.getAreaId()+"-"+ a.getAreaId();
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
