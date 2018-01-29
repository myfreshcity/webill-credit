package com.webill.app.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webill.core.model.report.BlackInfo;
import com.webill.core.model.report.ContactRegion;
import com.webill.core.model.report.Cuishou;
import com.webill.core.model.report.CusBasicInfo;
import com.webill.core.model.report.FinancialCallInfo;
import com.webill.core.model.report.ReportContact;
import com.webill.core.model.report.TopContact;
import com.webill.core.model.report.TripInfo;

/** 
 * @ClassName: ReportParseUtil 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月25日 下午2:30:32 
 */
public class ReportParseUtil {
	
	/** 
	 * @Title: parseCusBasicInfo 
	 * @Description: 聚信立-解析客户基本信息
	 * @author ZhangYadong
	 * @date 2018年1月25日 下午2:36:54
	 * @param json
	 * @return
	 * @return CusBasicInfo
	 */
	public static CusBasicInfo parseCusBasicInfo(String json){
		JSONObject jxlReportObj = JSONObject.parseObject(json);
		// 客户基本信息
		CusBasicInfo cbi = new CusBasicInfo();
		JSONArray appCheckArr = jxlReportObj.getJSONArray("application_check");
		for (int i = 0; i < appCheckArr.size(); i++) {
			JSONObject appObj = appCheckArr.getJSONObject(i);
			String appPoint = appObj.getString("app_point");
			// 登记姓名
			if (appPoint.equals("user_name")) {
				cbi.setUser_name(appObj.getJSONObject("check_points").getString("key_value"));
			}
			// 年龄、性别、身份证号、户籍地址
			if (appPoint.equals("id_card")) {
				JSONObject checkPointsObj = appObj.getJSONObject("check_points");
				cbi.setAge(checkPointsObj.getIntValue("age"));
				cbi.setSex(checkPointsObj.getString("gender"));
				cbi.setId_no(checkPointsObj.getString("key_value"));
				cbi.setResidence_address(checkPointsObj.getString("province")+checkPointsObj.getString("city")+checkPointsObj.getString("region"));
			}
			// 移动运营商、实名认证、手机号、手机注册时间、姓名检查、身份证号检查
			if (appPoint.equals("cell_phone")) {
				JSONObject checkPointsObj = appObj.getJSONObject("check_points");
				cbi.setWebsite(checkPointsObj.getString("website"));
				cbi.setReliability(checkPointsObj.getString("reliability"));
				cbi.setMobile_no(checkPointsObj.getString("key_value"));
				cbi.setReg_time(checkPointsObj.getString("reg_time"));
				cbi.setCheck_name(checkPointsObj.getString("check_name"));
				cbi.setCheck_idcard(checkPointsObj.getString("check_idcard"));
			}
		}
		
		JSONArray behaviorCheckArr = jxlReportObj.getJSONArray("behavior_check");
		for (int i = 0; i < behaviorCheckArr.size(); i++) {
			JSONObject behaviorObj = behaviorCheckArr.getJSONObject(i);
			String checkPoint = behaviorObj.getString("check_point");
			if (checkPoint.equals("phone_silent")) {
				cbi.setPhone_silent_result(behaviorObj.getString("result"));
				cbi.setPhone_silent_evidence(behaviorObj.getString("evidence"));
			}
			if (checkPoint.equals("contact_each_other")) {
				cbi.setContact_each_other_evidence(behaviorObj.getString("evidence"));
			}
		}
		return cbi;
	}
	
	/** 
	 * @Title: parseReportContact 
	 * @Description: 聚信立-解析联系人信息
	 * @author ZhangYadong
	 * @date 2018年1月26日 上午10:46:49
	 * @param json
	 * @return
	 * @return ReportContact
	 */
	public static List<ReportContact> parseReportContact(String json){
		JSONObject jxlReportObj = JSONObject.parseObject(json);
		List<ReportContact> rcList = new ArrayList<>();
		
		JSONArray appCheckArr = jxlReportObj.getJSONArray("application_check");
		for (int i = 0; i < appCheckArr.size(); i++) {
			
			JSONObject appObj = appCheckArr.getJSONObject(i);
			String appPoint = appObj.getString("app_point");
			// 登记姓名
			if (appPoint.equals("contact")) {
				ReportContact rc = new ReportContact();
				rc.setContact_name(appObj.getJSONObject("check_points").getString("contact_name"));
				rc.setCheck_xiaohao(appObj.getJSONObject("check_points").getString("check_xiaohao"));
				rc.setRelationship(appObj.getJSONObject("check_points").getString("relationship"));
				rc.setMobile_no(appObj.getJSONObject("check_points").getString("key_value"));
				
				String check_mobile = appObj.getJSONObject("check_points").getString("check_mobile");
				if (check_mobile != null) {
					ReportContact rcRegex = regexMatchContact(check_mobile);
					rc.setCall_day(rcRegex.getCall_day());
					rc.setCall_time_rank(rcRegex.getCall_time_rank());
					rc.setCall_cnt(rcRegex.getCall_cnt());
					rc.setCall_len(rcRegex.getCall_len());
				}
				rcList.add(rc);
			}
		}
		
		return rcList;
	}
	
	/** 
	 * @Title: regexMatchContact 
	 * @Description: 正则匹配联系人（联系天数、联系时长排名、联系次数、联系时长）
	 * @author ZhangYadong
	 * @date 2018年1月26日 上午11:16:20
	 * @param str
	 * @return
	 * @return ReportContact
	 */
	public static ReportContact regexMatchContact(String str){
		String strArr[] = {"\\[(.*?)\\]天", "联系\\[(.*?)\\]次", "次\\[(.*?)\\]分钟", "第\\[(.*?)\\]位"};
		ReportContact rc = new ReportContact();
		String rcArr[] = new String[4];
		for (int i = 0; i < 4; i++) {
			Pattern p_src = Pattern.compile(strArr[i]);
			Matcher m_src = p_src.matcher(str);
			String str_src = null;
			if (m_src.find()) {
				str_src = m_src.group(1);
			}
			rcArr[i] = str_src;
		}
		rc.setCall_day(rcArr[0]);
		rc.setCall_time_rank(rcArr[1]);
		rc.setCall_cnt(rcArr[2]);
		rc.setCall_len(rcArr[3]);
		return rc;
	}
	
	/** 
	 * @Title: parseDHBCusBasicInfo 
	 * @Description: 电话邦-解析客户基本信息
	 * @author ZhangYadong
	 * @date 2018年1月25日 下午2:36:54
	 * @param json
	 * @return
	 * @return CusBasicInfo
	 */
	public static CusBasicInfo parseDHBCusBasicInfo(String json){
		JSONObject jxlReportObj = JSONObject.parseObject(json);
		// 客户基本信息
		CusBasicInfo cbi = new CusBasicInfo();
		JSONArray appCheckArr = jxlReportObj.getJSONArray("application_check");
		for (int i = 0; i < appCheckArr.size(); i++) {
			JSONObject appObj = appCheckArr.getJSONObject(i);
			String appPoint = appObj.getString("app_point");
			// 登记姓名
			if (appPoint.equals("user_name")) {
				cbi.setUser_name(appObj.getJSONObject("check_points").getString("key_value"));
			}
			// 年龄、性别、身份证号、户籍地址
			if (appPoint.equals("id_card")) {
				JSONObject checkPointsObj = appObj.getJSONObject("check_points");
				cbi.setAge(checkPointsObj.getIntValue("age"));
				cbi.setSex(checkPointsObj.getString("gender"));
				cbi.setId_no(checkPointsObj.getString("key_value"));
				cbi.setResidence_address(checkPointsObj.getString("province")+checkPointsObj.getString("city")+checkPointsObj.getString("region"));
			}
			// 移动运营商、实名认证、手机号、手机注册时间、姓名检查、身份证号检查
			if (appPoint.equals("cell_phone")) {
				JSONObject checkPointsObj = appObj.getJSONObject("check_points");
				cbi.setWebsite(checkPointsObj.getString("website"));
				cbi.setReliability(checkPointsObj.getString("reliability"));
				cbi.setMobile_no(checkPointsObj.getString("key_value"));
				cbi.setReg_time(checkPointsObj.getString("reg_time"));
				cbi.setCheck_name(checkPointsObj.getString("check_name"));
				cbi.setCheck_idcard(checkPointsObj.getString("check_idcard"));
			}
		}
		
		JSONArray behaviorCheckArr = jxlReportObj.getJSONArray("behavior_check");
		for (int i = 0; i < behaviorCheckArr.size(); i++) {
			JSONObject behaviorObj = behaviorCheckArr.getJSONObject(i);
			String checkPoint = behaviorObj.getString("check_point");
			if (checkPoint.equals("phone_silent")) {
				cbi.setPhone_silent_result(behaviorObj.getString("result"));
				cbi.setPhone_silent_evidence(behaviorObj.getString("evidence"));
			}
			if (checkPoint.equals("contact_each_other")) {
				cbi.setContact_each_other_evidence(behaviorObj.getString("evidence"));
			}
		}
		return cbi;
	}
	
	/** 
	 * @Title: parseBlackInfo 
	 * @Description: 聚信立-解析用户黑名单信息
	 * @author ZhangYadong
	 * @date 2018年1月25日 下午2:50:32
	 * @param json
	 * @return
	 * @return BlackInfo
	 */
	public static BlackInfo parseBlackInfo(String json){
		JSONObject jxlReportObj = JSONObject.parseObject(json);
		// 用户黑名单信息
		BlackInfo blackInfo = new BlackInfo();
		JSONObject userInfoCheckObj = jxlReportObj.getJSONObject("user_info_check");
		JSONObject checkBlackInfoObj = userInfoCheckObj.getJSONObject("check_black_info");
		
		blackInfo.setPhone_gray_score(checkBlackInfoObj.getIntValue("phone_gray_score"));
		blackInfo.setContacts_class1_blacklist_cnt(checkBlackInfoObj.getIntValue("contacts_class1_blacklist_cnt"));
		blackInfo.setContacts_class2_blacklist_cnt(checkBlackInfoObj.getIntValue("contacts_class2_blacklist_cnt"));
		blackInfo.setContacts_class1_cnt(checkBlackInfoObj.getIntValue("contacts_class1_cnt"));
		blackInfo.setContacts_router_cnt(checkBlackInfoObj.getIntValue("contacts_router_cnt"));
		blackInfo.setContacts_router_ratio(checkBlackInfoObj.getIntValue("contacts_router_ratio"));
		
		return blackInfo;
	}

	/** 
	 * @Title: parseFinancialCallInfo 
	 * @Description: 聚信立-解析金融类通话信息
	 * @author ZhangYadong
	 * @date 2018年1月25日 下午2:55:42
	 * @param json
	 * @return
	 * @return List<FinancialCallInfo>
	 */
	public static List<FinancialCallInfo> parseFinancialCallInfo(String json){
		JSONObject jxlReportObj = JSONObject.parseObject(json);

		List<FinancialCallInfo> fciList = new ArrayList<>();
		JSONArray behaviorCheckArr = jxlReportObj.getJSONArray("behavior_check");
		for (int i = 0; i < behaviorCheckArr.size(); i++) {
			FinancialCallInfo fci = new FinancialCallInfo();
			JSONObject checkPointObj = behaviorCheckArr.getJSONObject(i);
			String checkPoint = checkPointObj.getString("check_point");
			// 澳门电话通话情、110话通话情况、120话通话情况、律师号码通话情况、法院号码通话情况、贷款类号码联系情况、银行类号码联系情况、信用卡类号码联系情况
			if (FinanceJxlEnum.isExist(checkPoint)) {
				fci.setCheck_point_cn(checkPointObj.getString("check_point_cn"));
				fci.setResult(checkPointObj.getString("result"));
				fci.setEvidence(checkPointObj.getString("evidence"));
				fciList.add(fci);
			}
		}
		
		return fciList;
	}
	
	/** 
	 * @Title: parseDHBFinancialCallInfo 
	 * @Description: 电话邦-解析金融类基础版通话信息
	 * @author ZhangYadong
	 * @date 2018年1月29日 上午11:43:53
	 * @param json
	 * @return
	 * @return List<FinancialCallInfo>
	 */
	public static List<FinancialCallInfo> parseDHBFinBasCallInfo(String json){
		JSONObject dhbReportObj = JSONObject.parseObject(json);
		
		List<FinancialCallInfo> fciList = new ArrayList<>();
		JSONArray csbtfArr = dhbReportObj.getJSONArray("calls_sa_by_tags_financial");
		
		for (int i = 0; i < csbtfArr.size(); i++) {
			FinancialCallInfo fci = new FinancialCallInfo();
			JSONObject csbtfObj = csbtfArr.getJSONObject(i);
			String tagsName = csbtfObj.getString("tags_name");
			// 金融高级版
			String dbhFinance[] = {"赌博","110","120","律师","公检法","贷款","银行","信用卡热线"};
			List<String> dbhList = Arrays.asList(dbhFinance);
			if (dbhList.contains(tagsName)) {
				fci.setCheck_point_cn(csbtfObj.getString("tags_name"));
				fci.setResult(csbtfObj.getString("contact_last_time"));
				fci.setEvidence(csbtfObj.getString("contact_detail"));
				fciList.add(fci);
			}
		}
		
		return fciList;
	}
	
	/** 
	 * @Title: parseDHBFinancialCallInfo 
	 * @Description: 电话邦-解析金融类高级版通话信息
	 * @author ZhangYadong
	 * @date 2018年1月29日 上午11:43:53
	 * @param json
	 * @return
	 * @return List<FinancialCallInfo>
	 */
	public static List<FinancialCallInfo> parseDHBFinAdvCallInfo(String json){
		JSONObject dhbReportObj = JSONObject.parseObject(json);
		
		List<FinancialCallInfo> fciList = new ArrayList<>();
		JSONArray csbtfArr = dhbReportObj.getJSONArray("calls_sa_by_tags_financial");
		
		for (int i = 0; i < csbtfArr.size(); i++) {
			FinancialCallInfo fci = new FinancialCallInfo();
			JSONObject csbtfObj = csbtfArr.getJSONObject(i);
			String tagsName = csbtfObj.getString("tags_name");
			// 金融高级版
			String dbhFinance[] = {"互联网金融","典当","保险","理财","小号","证券"};
			List<String> dbhList = Arrays.asList(dbhFinance);
			if (dbhList.contains(tagsName)) {
				fci.setCheck_point_cn(csbtfObj.getString("tags_name"));
				fci.setResult(csbtfObj.getString("contact_last_time"));
				fci.setEvidence(csbtfObj.getString("contact_detail"));
				fciList.add(fci);
			}
		}
		
		return fciList;
	}
	
	/** 
	 * @Title: parseContactRegion 
	 * @Description: 聚信立-解析联系人区域信息
	 * @author ZhangYadong
	 * @date 2018年1月25日 下午3:38:47
	 * @param json
	 * @return
	 * @return List<ContactRegion>
	 */
	public static List<ContactRegion> parseContactRegion(String json){
		JSONObject jxlReportObj = JSONObject.parseObject(json);
		List<ContactRegion> crList = new ArrayList<>();
		
		JSONArray contactRegionArr = jxlReportObj.getJSONArray("contact_region");
		for (int i = 0; i < contactRegionArr.size(); i++) {
			ContactRegion cr = new ContactRegion();
			JSONObject contactRegionObj = contactRegionArr.getJSONObject(i);
			cr.setRegion_loc(contactRegionObj.getString("region_loc"));
			cr.setRegion_uniq_num_cnt(contactRegionObj.getString("region_uniq_num_cnt"));
			cr.setRegion_call_in_cnt(contactRegionObj.getString("region_call_in_cnt"));
			cr.setRegion_call_out_cnt(contactRegionObj.getString("region_call_out_cnt"));
			crList.add(cr);
		}
		return crList;
	}
	
	/** 
	 * @Title: parseDHBContactRegion 
	 * @Description: 电话邦-解析联系人区域信息
	 * @author ZhangYadong
	 * @date 2018年1月29日 上午9:27:27
	 * @param json
	 * @return
	 * @return List<ContactRegion>
	 */
	public static List<ContactRegion> parseDHBContactRegion(String json){
		JSONObject jxlReportObj = JSONObject.parseObject(json);
		List<ContactRegion> crList = new ArrayList<>();
		
		JSONArray contactRegionArr = jxlReportObj.getJSONArray("calls_sa_by_region");
		for (int i = 0; i < contactRegionArr.size(); i++) {
			ContactRegion cr = new ContactRegion();
			JSONObject contactRegionObj = contactRegionArr.getJSONObject(i);
			cr.setRegion_loc(contactRegionObj.getString("region"));
			cr.setRegion_uniq_num_cnt(contactRegionObj.getString("total_format_tel"));
			cr.setRegion_call_in_cnt(contactRegionObj.getString("call_in_times"));
			cr.setRegion_call_out_cnt(contactRegionObj.getString("call_out_times"));
			crList.add(cr);
		}
		return crList;
	}
	
	/** 
	 * @Title: parseDateTopContact 
	 * @Description: 聚信立-解析长时间联系人（Top10）
	 * @author ZhangYadong
	 * @date 2018年1月25日 下午3:43:25
	 * @param json
	 * @return
	 * @return List<TopContact>
	 */
	public static List<TopContact> parseDateTopContact(String json){
		JSONObject jxlReportObj = JSONObject.parseObject(json);
		List<TopContact> tcList = new ArrayList<>();
		
		// 联系人按照通话时长排序
		List<JSONObject> contactArrList = JSONObject.parseArray(jxlReportObj.getString("contact_list"), JSONObject.class);
		Collections.sort(contactArrList, new Comparator<JSONObject>() {
			public int compare(JSONObject jo1, JSONObject jo2) {
				// 通话时长
				int callLen1 = jo1.getIntValue("call_len");
				int callLen2 = jo2.getIntValue("call_len");
				// 通话次数
				int callCnt1 = jo1.getIntValue("call_cnt");
				int callCnt2 = jo1.getIntValue("call_cnt");

				if (callLen1 != callLen2) {
					return callLen1 < callLen2 ? 1 : -1;
				} else {
					return callCnt1 < callCnt2 ? 1 : -1;
				}
			}

		});
		
		// 取前10条数据
		for (int i = 0; i < 10; i++) {
			JSONObject jo = contactArrList.get(i);
			TopContact tc = new TopContact();
			tc.setFormat_tel(jo.getString("phone_num"));
			tc.setTags_label(jo.getString("contact_name"));
			tc.setCall_times(jo.getString("call_cnt"));
			tc.setCall_length(jo.getString("call_len"));
			tcList.add(tc);
		}
		return tcList;
	}
	
	/** 
	 * @Title: parseDHBDateTopContact 
	 * @Description: 电话邦-解析长时间联系人（Top10）
	 * @author ZhangYadong
	 * @date 2018年1月29日 上午9:28:32
	 * @param json
	 * @return
	 * @return List<TopContact>
	 */
	public static List<TopContact> parseDHBDateTopContact(String json){
		JSONObject jxlReportObj = JSONObject.parseObject(json);
		List<TopContact> tcList = new ArrayList<>();
		
		JSONArray callsSaByLengthArr = jxlReportObj.getJSONArray("calls_sa_by_length");
		for (int i = 0; i < callsSaByLengthArr.size(); i++) {
			TopContact tc = new TopContact();
			JSONObject callsSaByLengthObj = callsSaByLengthArr.getJSONObject(i);
			tc.setFormat_tel(callsSaByLengthObj.getString("format_tel"));
			tc.setTags_label(callsSaByLengthObj.getString("tags_label"));
			tc.setCall_times(callsSaByLengthObj.getString("call_times"));
			tc.setCall_length(callsSaByLengthObj.getString("call_length"));
			tcList.add(tc);
		}
		return tcList;
	}
	
	/** 
	 * @Title: parseTimesTopContact 
	 * @Description: 聚信立-解析高频联系人（Top10）
	 * @author ZhangYadong
	 * @date 2018年1月25日 下午3:43:25
	 * @param json
	 * @return
	 * @return List<TopContact>
	 */
	public static List<TopContact> parseTimesTopContact(String json){
		JSONObject jxlReportObj = JSONObject.parseObject(json);
		List<TopContact> tcList = new ArrayList<>();
		
		// 联系人按照通话次数排序
		List<JSONObject> contactArrList = JSONObject.parseArray(jxlReportObj.getString("contact_list"), JSONObject.class);
		Collections.sort(contactArrList, new Comparator<JSONObject>() {
			public int compare(JSONObject jo1, JSONObject jo2) {
				// 通话次数
				int callCnt1 = jo1.getIntValue("call_cnt");
				int callCnt2 = jo1.getIntValue("call_cnt");
				// 通话时长
				int callLen1 = jo1.getIntValue("call_len");
				int callLen2 = jo2.getIntValue("call_len");

				if (callCnt1 != callCnt2) {
					return callCnt1 < callCnt2 ? 1 : -1;
				} else {
					return callLen1 < callLen2 ? 1 : -1;
				}
			}

		});
		
		// 取前10条数据
		for (int i = 0; i < 10; i++) {
			JSONObject jo = contactArrList.get(i);
			TopContact tc = new TopContact();
			tc.setFormat_tel(jo.getString("phone_num"));
			tc.setTags_label(jo.getString("contact_name"));
			tc.setCall_times(jo.getString("call_cnt"));
			tc.setCall_length(jo.getString("call_len"));
			tcList.add(tc);
		}
		return tcList;
	}
	
	/** 
	 * @Title: parseDHBTimesTopContact 
	 * @Description: 电话邦-解析高频联系人（Top10）
	 * @author ZhangYadong
	 * @date 2018年1月29日 上午9:38:10
	 * @param json
	 * @return
	 * @return List<TopContact>
	 */
	public static List<TopContact> parseDHBTimesTopContact(String json){
		JSONObject jxlReportObj = JSONObject.parseObject(json);
		List<TopContact> tcList = new ArrayList<>();
		
		JSONArray callsSaByLengthArr = jxlReportObj.getJSONArray("calls_sa_by_times");
		for (int i = 0; i < callsSaByLengthArr.size(); i++) {
			TopContact tc = new TopContact();
			JSONObject callsSaByLengthObj = callsSaByLengthArr.getJSONObject(i);
			tc.setFormat_tel(callsSaByLengthObj.getString("format_tel"));
			tc.setTags_label(callsSaByLengthObj.getString("tags_label"));
			tc.setCall_times(callsSaByLengthObj.getString("call_times"));
			tc.setCall_length(callsSaByLengthObj.getString("call_length"));
			tcList.add(tc);
		}
		return tcList;
	}
	
	/** 
	 * @Title: parseTripInfo 
	 * @Description: 聚信立-解析出行数据
	 * @author ZhangYadong
	 * @date 2018年1月25日 下午5:08:00
	 * @param json
	 * @return
	 * @return List<TripInfo>
	 */
	public static List<TripInfo> parseTripInfo(String json){
		JSONObject jxlReportObj = JSONObject.parseObject(json);
		List<TripInfo> tfList = new ArrayList<>();
		JSONArray tripInfoArr = jxlReportObj.getJSONArray("trip_info");
		for (int i = 0; i < tripInfoArr.size(); i++) {
			TripInfo tf = new TripInfo();
			JSONObject tripInfoObj = tripInfoArr.getJSONObject(i);
			tf.setTrip_leave(tripInfoObj.getString("trip_leave"));
			tf.setTrip_dest(tripInfoObj.getString("trip_dest"));
			tf.setTrip_type(tripInfoObj.getString("trip_type"));
			tf.setTrip_start_time(tripInfoObj.getString("trip_start_time"));
			tf.setTrip_end_time(tripInfoObj.getString("trip_end_time"));
			tfList.add(tf);
		}
		return tfList;
	}
	
	/** 
	 * @Title: parseDHBCuishou 
	 * @Description: 电话邦-解析催收信息数据节点
	 * @author ZhangYadong
	 * @date 2018年1月29日 上午11:17:06
	 * @param json
	 * @return
	 * @return Cuishou
	 */
	public static Cuishou parseDHBCuishou(String json){
		JSONObject jxlReportObj = JSONObject.parseObject(json);
		Cuishou cs = null;
		JSONObject crdObj = jxlReportObj.getJSONObject("cuishou_risk_detection");

		JSONObject cuishou = crdObj.getJSONObject("cuishou");
		JSONObject cuishou_degree = crdObj.getJSONObject("cuishou_degree");
		if (!StringUtil.isEmpty(cuishou) && !StringUtil.isEmpty(cuishou_degree)) {
			 cs = parseCuishouJson(cuishou, cuishou_degree);
		}
		
		return cs;
	}

	/** 
	 * @Title: parseDHBYisiCuishou 
	 * @Description: 电话邦-疑似催收信息数据节点
	 * @author ZhangYadong
	 * @date 2018年1月29日 上午11:17:21
	 * @param json
	 * @return
	 * @return Cuishou
	 */
	public static Cuishou parseDHBYisiCuishou(String json){
		JSONObject jxlReportObj = JSONObject.parseObject(json);
		Cuishou cs = new Cuishou();
		
		JSONObject crdObj = jxlReportObj.getJSONObject("cuishou_risk_detection");
		
		JSONObject yisicuishou = crdObj.getJSONObject("yisicuishou");
		JSONObject yisicuishou_degree = crdObj.getJSONObject("yisicuishou_degree");
		if (!StringUtil.isEmpty(yisicuishou) && !StringUtil.isEmpty(yisicuishou_degree)) {
			cs = parseCuishouJson(yisicuishou, yisicuishou_degree);
		}
		
		return cs;
	}
	
	/** 
	 * @Title: parseCuishouJson 
	 * @Description: 解析催收返回json数据
	 * @author ZhangYadong
	 * @date 2018年1月29日 上午11:16:28
	 * @param cuishou
	 * @param cuishou_degree
	 * @return
	 * @return Cuishou
	 */
	private static Cuishou parseCuishouJson(JSONObject cuishou, JSONObject cuishou_degree) {
		Cuishou cs = new Cuishou();
		cs.setNums_tel(cuishou.getInteger("nums_tel"));
		cs.setNums_tel_degree(cuishou_degree.getInteger("nums_tel"));
		cs.setCall_times(cuishou.getString("call_times"));
		cs.setCall_in_times(cuishou.getInteger("call_in_times"));
		cs.setCall_in_times_degree(cuishou_degree.getInteger("call_in_times"));
		cs.setCall_in_length(cuishou.getInteger("call_in_length"));
		cs.setCall_in_length_degree(cuishou_degree.getInteger("call_in_length"));
		cs.setCall_in_less_15(cuishou.getInteger("call_in_less_15"));
		cs.setCall_in_less_15_degree(cuishou_degree.getInteger("call_in_less_15"));
		cs.setMost_times_by_tel(cuishou.getInteger("most_times_by_tel"));
		cs.setMost_times_by_tel_degree(cuishou_degree.getInteger("most_times_by_tel"));
		cs.setUp_2_times_by_tel(cuishou.getInteger("up_2_times_by_tel"));
		cs.setUp_2_times_by_tel_degree(cuishou_degree.getInteger("up_2_times_by_tel"));
		cs.setCall_out_times(cuishou.getInteger("call_out_times"));
		cs.setCall_out_times_degree(cuishou_degree.getInteger("call_out_times"));
		cs.setCall_out_length(cuishou.getInteger("call_out_length"));
		cs.setCall_out_length_degree(cuishou_degree.getInteger("call_out_length"));
		cs.setDay7_times(cuishou.getInteger("7day_times"));
		cs.setDay7_times_degree(cuishou_degree.getInteger("7day_times"));
		cs.setDay30_times(cuishou.getInteger("30day_times"));
		cs.setDay30_times_degree(cuishou_degree.getInteger("30day_times"));
		cs.setDay60_times(cuishou.getInteger("60day_times"));
		cs.setDay60_times_degree(cuishou_degree.getInteger("60day_times"));
		cs.setDay90_times(cuishou.getInteger("90day_times"));
		cs.setDay90_times_degree(cuishou_degree.getInteger("90day_times"));
		cs.setDay120_times(cuishou.getInteger("120day_times"));
		cs.setDay120_times_degree(cuishou_degree.getInteger("120day_times"));
		return cs;
	}
	
}
