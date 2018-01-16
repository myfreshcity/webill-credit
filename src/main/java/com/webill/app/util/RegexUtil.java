package com.webill.app.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.InputMapUIResource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webill.core.model.HealthyQa;
import com.webill.core.model.HealthyQaAnswer;
import com.webill.core.model.HealthyQaHelp;
import com.webill.core.model.HealthyQaModule;
import com.webill.core.model.HealthyQaModuleHelp;
import com.webill.core.model.HealthyQaQuestion;
import com.webill.core.model.InsurePrice;
import com.webill.core.model.RestrictDictionary;
import com.webill.core.model.RestrictGene;
import com.webill.core.model.SubRestrictDictionary;
import com.webill.framework.common.JSONUtil;

/** 
* @ClassName: RegexUtil 
* @Description: 
* @author ZhangYadong
* @date 2017年11月30日 上午10:59:00 
*/
public class RegexUtil {

	/** 
	* @Title: matchImg 
	* @Description: 匹配标签中图片URl
	* @author ZhangYadong
	* @date 2017年11月30日 上午11:02:53
	* @param url
	* @return
	* @return String
	*/
	public static String matchImg(String url){
		Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
		Matcher m_src = p_src.matcher(url);
		String str_src = null;
		if (m_src.find()) {
			str_src = m_src.group(3);
		}
		return str_src;
	}
	
    /** 
    * @Title: getImgSrc 
    * @Description: 获取img标签中的src值
    * @author ZhangYadong
    * @date 2017年12月4日 下午1:22:14
    * @param content
    * @return
    * @return List<String>
    */
    public static List<String> getImgSrc(String content){
        List<String> list = new ArrayList<String>();
        //目前img标签标示有3种表达式
        //<img alt="" src="1.jpg"/>   <img alt="" src="1.jpg"></img>     <img alt="" src="1.jpg">
        //开始匹配content中的<img />标签
        Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
        Matcher m_img = p_img.matcher(content);
        boolean result_img = m_img.find();
        if (result_img) {
            while (result_img) {
                //获取到匹配的<img />标签中的内容
                String str_img = m_img.group(2);
                 
                //开始匹配<img />标签中的src
                Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
                Matcher m_src = p_src.matcher(str_img);
                if (m_src.find()) {
                    String str_src = m_src.group(3);
                    list.add(str_src);
                }
                //结束匹配<img />标签中的src
                 
                //匹配content中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
                result_img = m_img.find();
            }
        }
        return list;
    }
    
    public static String retImgSrc(String content){
    	String Src = "";
    	//目前img标签标示有3种表达式
    	//<img alt="" src="1.jpg"/>   <img alt="" src="1.jpg"></img>     <img alt="" src="1.jpg">
    	//开始匹配content中的<img />标签
    	Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
    	Matcher m_img = p_img.matcher(content);
    	boolean result_img = m_img.find();
    	if (result_img) {
    		while (result_img) {
    			//获取到匹配的<img />标签中的内容
    			String str_img = m_img.group(2);
    			
    			//开始匹配<img />标签中的src
    			Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
    			Matcher m_src = p_src.matcher(str_img);
    			if (m_src.find()) {
    				String str_src = m_src.group(3);
    				Src = Src + str_src + ",";
    			}
    			//结束匹配<img />标签中的src
    			
    			//匹配content中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
    			result_img = m_img.find();
    		}
    	}
    	Src = Src.substring(0, Src.length()-1);
    	return Src;
    }
    
    
	/** 
	* @Title: getInsuAge 
	* @Description: 根据试算因子获取承保年龄
	* @author ZhangYadong
	* @date 2017年12月4日 下午2:53:07
	* @param restrictGenes
	* @return
	* @return String
	* @throws Exception 
	*/
	public static String insurantDate(String restrictGenes){
		String insurantDate = null;
		try {
			List<RestrictGene> reList = JSONUtil.toObjectList(restrictGenes, RestrictGene.class);
			for (RestrictGene rg: reList) {
				if ("insurantDate".equals(rg.getKey())) {
					List<RestrictDictionary> rdList = rg.getValues();
					if (rdList.size() == 1) {
						RestrictDictionary rd = rdList.get(0);
						String value = rd.getValue();
						String unit = rd.getUnit();
						if (StringUtil.isNotEmpty(unit)) {
							insurantDate = value + unit;
						}else {
							insurantDate = value;
						}
					}else {
						int maxIndex = 0, minIndex = 0;
						for (int i = 1; i < rdList.size(); i++) {
							RestrictDictionary rd1 = rdList.get(i);
							RestrictDictionary rd2 = rdList.get(maxIndex);
							RestrictDictionary rd3 = rdList.get(minIndex);
							if (rd1.getMax() > rd2.getMax()) {
								maxIndex = i;//将最大元素的索引赋值给maxIndex 
							}
							if (rd1.getMin() < rd3.getMin()) {
								minIndex = i;//将最小元素的索引赋值给minIndex
							}
						}
						RestrictDictionary maxRd = rdList.get(maxIndex); //min最小值对象
						RestrictDictionary minRd = rdList.get(minIndex); //max最大值对象
						String minUnit = minRd.getUnit();
						String maxUnit = maxRd.getUnit();
						if (minUnit == null) {
							minUnit = "";
						}
						if (maxUnit == null) {
							maxUnit = "";
						}
						if (minRd.getMin() == minRd.getMax() && maxRd.getMin() == maxRd.getMax()) {
							insurantDate = minRd.getValue()+minUnit+"-"+maxRd.getValue()+maxUnit+"可选";
						}else {
							insurantDate = minRd.getMin()+minUnit+"-"+maxRd.getMax()+maxUnit+"可选";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return insurantDate;

	}
	
	/** 
	* @Title: insurantDateLimit 
	* @Description: 根据试算因子获取保障期限
	* @author ZhangYadong
	* @date 2017年12月4日 下午4:45:54
	* @param restrictGenes
	* @return
	* @return String
	*/
	public static String insurantDateLimit(String restrictGenes) {
		String insurantDate = null;
		try {
			List<RestrictGene> reList = JSONUtil.toObjectList(restrictGenes, RestrictGene.class);
			for (RestrictGene rg: reList) {
				if ("insurantDateLimit".equals(rg.getKey())) {
					List<RestrictDictionary> rdList = rg.getValues();
					if (rdList.size() == 1) {
						RestrictDictionary rd = rdList.get(0);
						String value = rd.getValue();
						String unit = rd.getUnit();
						if (StringUtil.isNotEmpty(unit)) {
							insurantDate = value + unit;
						}else {
							insurantDate = value;
						}
					}else {
						int maxIndex = 0, minIndex = 0;
						for (int i = 1; i < rdList.size(); i++) {
							RestrictDictionary rd1 = rdList.get(i);
							RestrictDictionary rd2 = rdList.get(maxIndex);
							RestrictDictionary rd3 = rdList.get(minIndex);
							if (rd1.getMax() > rd2.getMax()) {
								maxIndex = i;//将最大元素的索引赋值给maxIndex 
							}
							if (rd1.getMin() < rd3.getMin()) {
								minIndex = i;//将最小元素的索引赋值给minIndex
							}
						}
						RestrictDictionary maxRd = rdList.get(maxIndex); //min最小值对象
						RestrictDictionary minRd = rdList.get(minIndex); //max最大值对象
						String minUnit = minRd.getUnit();
						String maxUnit = maxRd.getUnit();
						if (minUnit == null) {
							minUnit = "";
						}
						if (maxUnit == null) {
							maxUnit = "";
						}
						if (minRd.getMin() == minRd.getMax() && maxRd.getMin() == maxRd.getMax()) {
							insurantDate = minRd.getValue()+minUnit+"-"+maxRd.getValue()+maxUnit+"可选";
						}else {
							insurantDate = minRd.getMin()+minUnit+"-"+maxRd.getMax()+maxUnit+"可选";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return insurantDate;
	}
	
	/** 
	* @Title: delTextTag 
	* @Description: 
	* @author ZhangYadong
	* @date 2017年12月6日 上午11:27:47
	* @param htmlStr
	* @return
	* @return String
	*/
	public static String delTextTag(String htmlStr) {
		htmlStr = htmlStr.replaceAll("<span[^>]*?>", "");//过滤span标签
		htmlStr = htmlStr.replaceAll("<\\/span>", "");
		
        Pattern p_strongl = Pattern.compile("<strong[^>]*?>");
        Matcher m_strongl = p_strongl.matcher(htmlStr);
        htmlStr = m_strongl.replaceAll(""); //过滤strong标签
        Pattern p_strongr = Pattern.compile("<\\/strong>");
        Matcher m_strongr = p_strongr.matcher(htmlStr);
        htmlStr = m_strongr.replaceAll(""); //过滤strong标签
        
        return htmlStr;
	}
	
	
	public static String getMinInsurDate(String restrictGenes) {
		List<RestrictGene> rgList = JSONUtil.toObjectList(restrictGenes, RestrictGene.class);
		String minInsurDate = null;
		for (RestrictGene rg : rgList) {
			if ("insurantDate".equals(rg.getKey())) {
				List<RestrictDictionary> vaList = rg.getValues();
				int min = vaList.get(0).getMin();
				String minUnit = vaList.get(0).getUnit();
				for (int i = 0; i < vaList.size(); i++) {
					if (min > vaList.get(i).getMin()) {
						min = vaList.get(i).getMin();
						SubRestrictDictionary sd = vaList.get(i).getSubDictionary();
						if (sd != null) {
							minUnit = sd.getUnit();
							min = Math.abs(sd.getMax());
						}
					}
				}
				if (StringUtil.isNotEmpty(minUnit)) {
					minInsurDate = min+minUnit;
				} else{
					minInsurDate = min+"";
				}
					
			}
		}
		return minInsurDate;
	}
	
	public static String getMaxInsurDate(String restrictGenes) {
		List<RestrictGene> rgList = JSONUtil.toObjectList(restrictGenes, RestrictGene.class);
		String maxInsurDate = null;
		for (RestrictGene rg : rgList) {
			if ("insurantDate".equals(rg.getKey())) {
				List<RestrictDictionary> vaList = rg.getValues();
				int max = vaList.get(0).getMax();
				String maxUnit = vaList.get(0).getUnit();
				for (int i = 0; i < vaList.size(); i++) {
					if (max < vaList.get(i).getMin()) {
						max = vaList.get(i).getMin();
					}
				}
				if (StringUtil.isNotEmpty(maxUnit)) {
					maxInsurDate = max+maxUnit;
				} else{
					maxInsurDate = max+"";
				}
			}
		}
		return maxInsurDate;
	}
	
	/** 
	* @Title: parseInsurePrice 
	* @Description: 解析试算因子
	* @author ZhangYadong
	* @date 2017年12月8日 下午1:20:36
	* @param restrictGenes
	* @return
	* @return List<RestrictGene>
	*/
	public static List<RestrictGene> parseRestrictGenes(String restrictGenes) {
		List<RestrictGene> rgList = JSONUtil.toObjectList(restrictGenes, RestrictGene.class);
		if (rgList != null && rgList.size() > 0) {
			for (RestrictGene re : rgList) {
				// 解析基本保额
				if ("基本保额".equals(re.getName()) || "主险基本保额".equals(re.getName())) {
					List<InsurePrice> inpr = new LinkedList<>();
					List<RestrictDictionary> valist = re.getValues();
					int min = valist.get(0).getMin();
					int max = valist.get(0).getMax();
					String unit = valist.get(0).getUnit();
					if (min == max) {
						InsurePrice ipr = new InsurePrice();
						if (StringUtil.isNotEmpty(unit)) {
							ipr.setValue(min + unit);
							inpr.add(ipr);
						}else {
							ipr.setValue(String.valueOf(min));
							inpr.add(ipr);
						}
					}else {
						int step = valist.get(0).getStep();
						int size = (max-min)/step;
						InsurePrice ip = new InsurePrice();
						if(StringUtil.isNotEmpty(unit)) {
							ip.setValue(min + unit);
							inpr.add(ip);
							for (int i = 0; i < size; i++) {
								InsurePrice ip2 = new InsurePrice();
								ip2.setValue((min + (step*(i+1))) + unit);
								inpr.add(ip2);
							}
						}else {
							ip.setValue(String.valueOf(min));
							inpr.add(ip);
							for (int i = 0; i < size; i++) {
								InsurePrice ip2 = new InsurePrice();
								ip2.setValue(String.valueOf((min + (step*(i+1)))));
								inpr.add(ip2);
							}
						}
					}
					re.setInsurPrice(inpr);
				}
				
				// 解析投保地区
				if ("city".equals(re.getKey())) {
					String cityName = null, proName = null;
					// 解析市级名称
					String defCityVa = re.getDefaultValue();
					List<RestrictDictionary> cvaList = re.getValues();
					for (RestrictDictionary crd : cvaList) {
						if (defCityVa.equals(crd.getValue())) {
							cityName = crd.getName();
						}
					}
					// 解析省级名称
					List<RestrictGene> srList = re.getSubRestrictGenes();
					for (RestrictGene rg : srList) {
						String defProVa = rg.getDefaultValue();
						if ("province".equals(rg.getKey())) {
							List<RestrictDictionary> pvalues = rg.getValues();
							for (RestrictDictionary prd : pvalues) {
								if (defProVa.equals(prd.getValue())) {
									proName = prd.getName();
								}
							}
						}
					}
					re.setProvinceName(proName);
					re.setCityName(cityName);
				}
				
				// 解析缴费年限
				if ("insureAgeLimit".equals(re.getKey())) {
					List<InsurePrice> ipList = new LinkedList<>();
					List<RestrictDictionary> valist = re.getValues();
					for (RestrictDictionary rd : valist) {
						if (rd.getMin() == rd.getMax()) {
							InsurePrice ip = new InsurePrice();
							if (StringUtil.isNotEmpty(rd.getUnit())) {
								ip.setValue(rd.getValue() + rd.getUnit());
								ipList.add(ip);
							}else {
								ip.setValue(rd.getValue());
								ipList.add(ip);
							}
						}else {
							int size = (rd.getMax()-rd.getMin())/rd.getStep();
							if (StringUtil.isNotEmpty(rd.getUnit())) {
								for (int i = 0; i <= size; i++) {
									InsurePrice ip2 = new InsurePrice();
									ip2.setValue(rd.getMin() + rd.getStep()*i + rd.getUnit());
									ipList.add(ip2);
								}
							}else {
								for (int i = 0; i <= size; i++) {
									InsurePrice ip2 = new InsurePrice();
									ip2.setValue(rd.getMin() + rd.getStep()*i + "");
									ipList.add(ip2);
								}
							}
						}
					}
					re.setInsurPrice(ipList);
				}
				
			}
		}
		return rgList;
	}
	
	/** 
	 * @Title: parseInsureDeclare 
	 * @Description: 解析投保声明
	 * @author ZhangYadong
	 * @date 2017年12月22日 下午5:39:25
	 * @param insureDeclare
	 * @param attachmentUrl
	 * @return
	 * @return String
	 */
	public static String parseInsureDeclare(String insureDeclare, String attachmentUrl) {
		String attUrl = insureDeclare.replaceAll("#ProductClauseUrl#", attachmentUrl);
		return attUrl;
	}
	
	/** 
	 * @Title: parseHealthState 
	 * @Description: 解析健康告知的提交数据
	 * @author ZhangYadong
	 * @date 2017年12月25日 下午2:11:47
	 * @param qaAnswerHelp
	 * @return
	 * @throws Exception
	 * @return String
	 */
	public static String parseHealthState(String qaAnswerHelp) throws Exception {
		HealthyQaHelp hqHelp = JSONUtil.toObject(qaAnswerHelp, HealthyQaHelp.class);
		
		List<HealthyQaModuleHelp> hqhList1 = hqHelp.getHealthyQaModules();
		HealthyQa healthyQa = new HealthyQa();
		healthyQa.setHealthyId(hqHelp.getHealthyId());
		
		Map<String, List<HealthyQaModuleHelp>> map1 = new HashMap<>();
		Map<Integer, List<HealthyQaModuleHelp>> map2 = new HashMap<>();
		//解析moduleId，分成不同的key
		for (HealthyQaModuleHelp hqh : hqhList1) {
			 String key = hqh.getModuleId();
			 if (map1.containsKey(key)) {
				 List<HealthyQaModuleHelp> value = map1.get(key);
				 value.add(hqh);
			 }else {
				 List<HealthyQaModuleHelp> tmpValue = new ArrayList<>();
				 tmpValue.add(hqh);
				 map1.put(key, tmpValue);
			 }
		}
		
		List<HealthyQaModule> modulesListHelp = new ArrayList<>();
		List<HealthyQaQuestion> questionListHelp = new ArrayList<>();
		//解析questionId，分成不同的key
		for (String k : map1.keySet()) {
			//设置数据
			HealthyQaModule moduleHelp = new HealthyQaModule();
			moduleHelp.setModuleId(k);
			//解析数据
			List<HealthyQaModuleHelp> hqhList2 = map1.get(k);
			for (HealthyQaModuleHelp hqh : hqhList2) {
				int key = hqh.getQuestionId();
				if (map2.containsKey(key)) {
					List<HealthyQaModuleHelp> value = map2.get(key);
					value.add(hqh);
				}else {
					List<HealthyQaModuleHelp> tmpValue = new ArrayList<>();
					tmpValue.add(hqh);
					map2.put(key, tmpValue);
				}
			} 
			
			//遍历questionId
			for (Integer k2 : map2.keySet()) {
				//设置数据
				HealthyQaQuestion questionHelp = new HealthyQaQuestion();
				questionHelp.setQuestionId(k2);
				//解析数据
				List<HealthyQaModuleHelp> hqhList3 = map2.get(k2);
				
				List<HealthyQaAnswer> answerListHelp = new ArrayList<>();
				for (HealthyQaModuleHelp hqh : hqhList3) {
					//设置数据
					questionHelp.setParentId(hqh.getParentId());
					questionHelp.setQuestionSort(hqh.getQuestionSort());
					
					HealthyQaAnswer answerHelp = new HealthyQaAnswer();
					answerHelp.setAnswerId(hqh.getAnswerId());
					answerHelp.setAnswerValue(hqh.getAnswerValue());
					answerHelp.setKeyCode(hqh.getKeyCode());
					answerListHelp.add(answerHelp);
				}
				questionHelp.setHealthyQaAnswers(answerListHelp);
				questionListHelp.add(questionHelp);
			}
			moduleHelp.setHealthyQaQuestions(questionListHelp);
			modulesListHelp.add(moduleHelp);
			//清空当前modules对应的所有questionId
			map2.clear();
		}
		healthyQa.setHealthyQaModules(modulesListHelp);
		String qaAnswer = JSONUtil.toJSONString(healthyQa);
		return qaAnswer;
	}
}
