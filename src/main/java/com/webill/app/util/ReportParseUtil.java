package com.webill.app.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webill.core.model.Customer;
import com.webill.core.model.report.BlackInfo;
import com.webill.core.model.report.ContactRegion;
import com.webill.core.model.report.Cuishou;
import com.webill.core.model.report.CusBasicInfo;
import com.webill.core.model.report.FinancialCallInfo;
import com.webill.core.model.report.ReportContact;
import com.webill.core.model.report.TopContact;
import com.webill.core.model.report.TripInfo;
import com.webill.core.service.ICustomerService;

/** 
 * @ClassName: ReportParseUtil 
 * @Description: 
 * @author ZhangYadong
 * @date 2018年1月25日 下午2:30:32 
 */
@Component
public class ReportParseUtil {
	@Autowired
	private ICustomerService customerService;
	
	/** 
	 * @Title: parseCusBasicInfo 
	 * @Description: 聚信立-解析客户基本信息
	 * @author ZhangYadong
	 * @date 2018年1月25日 下午2:36:54
	 * @param json
	 * @return
	 * @return CusBasicInfo
	 */
	public CusBasicInfo parseCusBasicInfo(String json, Integer cusId){
		Customer cus = customerService.selectById(cusId);
		
		JSONObject reportObj = JSONObject.parseObject(json);
		// 客户基本信息
		CusBasicInfo cbi = new CusBasicInfo();
		JSONArray appCheckArr = reportObj.getJSONArray("application_check");
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
				String idNo = checkPointsObj.getString("key_value"); 
				cbi.setId_no(idNo);
				//cbi.setResidence_address(checkPointsObj.getString("province")+checkPointsObj.getString("city")+checkPointsObj.getString("region"));
				// 查询性别、年龄、户籍地址
				cbi.setSex(IDCardUtil.parseGender(idNo));
				cbi.setAge(IDCardUtil.parseAge(idNo));
				cbi.setResidence_address(IDCardUtil.parseAddress(idNo));
				// 法院黑名单检查
				Boolean court_black = checkPointsObj.getJSONObject("court_blacklist").getBoolean("arised"); //arised：是否出现	
				if (court_black) {
					cbi.setCheck_court_black(0); // 0-黑名单 1-非黑名单
				}else {
					cbi.setCheck_court_black(1); // 0-黑名单 1-非黑名单
				}
			}
			//工作地址
			if (cus.getWorkAddr() != null && cus.getWorkAddrDetail() != null) {
				cbi.setWork_address(cus.getWorkAddr()+cus.getWorkAddrDetail());
			}
			// 家庭住址
			if (appPoint.equals("home_addr")) {
				cbi.setHome_address(appObj.getJSONObject("check_points").getString("key_value"));
				cbi.setHome_addr_check(appObj.getJSONObject("check_points").getString("check_addr"));
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
		
		JSONArray behaviorCheckArr = reportObj.getJSONArray("behavior_check");
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
	 * @Description: 聚信立-解析紧急联系人信息（至少6个月，时间肯定大于6个月）
	 * @author ZhangYadong
	 * @date 2018年1月26日 上午10:46:49
	 * @param json
	 * @return
	 * @return ReportContact
	 */
	public List<ReportContact> parseReportContact(String json){
		JSONObject reportObj = JSONObject.parseObject(json);
		List<ReportContact> rcList = new ArrayList<>();
		
		JSONArray appCheckArr = reportObj.getJSONArray("application_check");
		for (int i = 0; i < appCheckArr.size(); i++) {
			
			JSONObject appObj = appCheckArr.getJSONObject(i);
			String appPoint = appObj.getString("app_point");
			// 登记姓名
			if (appPoint.equals("contact")) {
				ReportContact rc = new ReportContact();
				rc.setContact_name(appObj.getJSONObject("check_points").getString("contact_name"));
				String xiaohao = appObj.getJSONObject("check_points").getString("check_xiaohao");
				if (xiaohao.contains("非临时小号")) {
					rc.setCheck_xiaohao(1); // 0-临时小号 1-非临时小号
				}else {
					rc.setCheck_xiaohao(0);
				}
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
	public ReportContact regexMatchContact(String str){
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
	public CusBasicInfo parseDHBCusBasicInfo(String json, Integer cusId){
		Customer cus = customerService.selectById(cusId);
		
		JSONObject reportObj = JSONObject.parseObject(json);
		// 客户基本信息
		CusBasicInfo cbi = new CusBasicInfo();
		JSONObject userInfoObj = reportObj.getJSONObject("user_info");
		JSONObject telInfoObj = reportObj.getJSONObject("tel_info");
		JSONObject callsOverview = reportObj.getJSONObject("calls_overview");
		
		// 登记姓名
		cbi.setUser_name(userInfoObj.getString("user_name"));
		// （年龄、性别）身份证号、（户籍地址）
		String idNo = userInfoObj.getString("user_idcard");
		cbi.setId_no(idNo);
		// 查询性别、年龄、户籍地址
		cbi.setSex(IDCardUtil.parseGender(idNo));
		cbi.setAge(IDCardUtil.parseAge(idNo));
		cbi.setResidence_address(IDCardUtil.parseAddress(idNo));
		
		//工作地址
		if (cus.getWorkAddr() != null && cus.getWorkAddrDetail() != null) {
			cbi.setWork_address(cus.getWorkAddr()+cus.getWorkAddrDetail());
		}
		// 居住地址
		cbi.setHome_address(userInfoObj.getString("user_address"));
		// 移动运营商、（实名认证）手机号、手机注册时间、姓名检查、身份证号检查
		// fancha_telloc:手机号归属地
		String fanchaTelloc = reportObj.getJSONObject("tel_info").getString("fancha_telloc");
		// telecom：所属运营商，
		String telecom = reportObj.getJSONObject("call_behavior").getJSONObject("number_used").getString("telecom");
		cbi.setWebsite(fanchaTelloc+telecom);
		cbi.setMobile_no(telInfoObj.getString("tel"));
		String open_date = telInfoObj.getString("open_date");
		if (!"运营商未透露".equals(open_date)) {
			cbi.setReg_time(DateUtil.timeStampToDat(Long.parseLong(open_date)));
		}
		// 与登记姓名一致性：0 不适用,1是,2 否，3 模糊匹配一致
		String checkName = userInfoObj.getString("conclusion_of_user_name_check");
		String cnFlag = "";
		if (checkName != null) {
			if (Integer.parseInt(checkName) == 0) {
				cnFlag = "不适用";
			}else if (Integer.parseInt(checkName) == 1) {
				cnFlag = "匹配一致";
			}else if (Integer.parseInt(checkName) == 2) {
				cnFlag = "不匹配";
			}else{
				cnFlag = "模糊匹配一致";
			}
		}
		cbi.setCheck_name("与登记姓名" + cnFlag);
		// 与登记身份证一致性 ：0 不适用,1 是,2 否，3 模糊匹配一致
		String checkIdcard = userInfoObj.getString("conclusion_of_user_idcard_check");
		String cidFlag = "";
		if (checkIdcard != null) {
			if (Integer.parseInt(checkIdcard) == 0) {
				cidFlag = "不适用";
			}else if (Integer.parseInt(checkIdcard) == 1) {
				cidFlag = "匹配一致";
			}else if (Integer.parseInt(checkIdcard) == 2) {
				cidFlag = "不匹配";
			}else{
				cidFlag = "模糊匹配一致";
			}
		}
		cbi.setCheck_idcard("与登记身份证" + cidFlag);
		// 与登记地址一致性 ：0 不适用,1 是,2 否，3 模糊匹配一致
		String checkAddress = userInfoObj.getString("conclusion_of_user_address_check");
		String caFlag = "";
		if (checkAddress != null) {
			if (Integer.parseInt(checkAddress) == 0) {
				caFlag = "不适用";
			}else if (Integer.parseInt(checkAddress) == 1) {
				caFlag = "匹配一致";
			}else if (Integer.parseInt(checkAddress) == 2) {
				caFlag = "不匹配";
			}else{
				caFlag = "模糊匹配一致";
			}
		}
		cbi.setCheck_address("与登记地址一致性"+caFlag);
		
		// 手机静默最长时间（小时）==>单次静默持续最长的时间
		int btcm = callsOverview.getIntValue("blank_times_count_max");
		String btcmStr = DateUtil.hoursToDay(btcm);
		cbi.setPhone_silent_result("手机静默最长时间："+btcmStr);
		// 手机静默时间段==>爬取周期内手机静默的时间段
		JSONArray blankTimesArr = callsOverview.getJSONArray("blank_times_list");
		if (!blankTimesArr.isEmpty()) {
			JSONArray btArr = blankTimesArr.getJSONArray(0);
			if (!btArr.isEmpty() && btArr.size() == 3) {
				String startDate = DateUtil.timeStampToDat(Long.parseLong(btArr.get(0).toString()));
				String endDate = DateUtil.timeStampToDat(Long.parseLong(btArr.get(1).toString()));
				String period = btArr.get(2).toString();
				cbi.setPhone_silent_evidence("手机静默时间段："+startDate+"至"+endDate+"，共"+period+"小时");
			}
		}
		cbi.setContact_each_other_evidence("互通号码数量/占比:"+callsOverview.getString("both_side_calls_count")+"个,占总联系人的"+callsOverview.getString("both_side_calls_percent")+"%");
		
		return cbi;
	}
	
	/** 
	 * @Title: parseReportContact 
	 * @Description: 电话邦-解析紧急联系人信息（6个月）
	 * @author ZhangYadong
	 * @date 2018年1月26日 上午10:46:49
	 * @param json
	 * @return
	 * @return ReportContact
	 */
	public List<ReportContact> parseDHBReportContact(String json){
		JSONObject reportObj = JSONObject.parseObject(json);
		List<ReportContact> rcList = new ArrayList<>();
		
		JSONArray mcArr = reportObj.getJSONArray("mergency_contact");
		for (int i = 0; i < mcArr.size(); i++) {
			JSONObject mvObj = mcArr.getJSONObject(i);
			
			ReportContact rc = new ReportContact();
			rc.setContact_name(mvObj.getString("contact_name"));
			// 如果这个号码没有标记，金融标签，黄页数据就会输出非临时小号
			JSONArray tfArr = mvObj.getJSONArray("tags_financial");
			String tl = mvObj.getString("tags_label");
			String typ = mvObj.getString("tags_yellow_page");
			if (tfArr.isEmpty() && StringUtil.isEmpty(tl) && StringUtil.isEmpty(typ)) {
				rc.setCheck_xiaohao(1); // 0-临时小号 1-非临时小号
			}else {
				rc.setCheck_xiaohao(0);
			}
			
			rc.setRelationship(mvObj.getString("contact_relationship"));
			rc.setMobile_no(mvObj.getString("format_tel"));
			//rc.setCall_day(rcRegex.getCall_day());
			rc.setCall_time_rank(mvObj.getString("call_length_index"));
			rc.setCall_cnt(mvObj.getString("call_times"));
			float callLen = Float.parseFloat(mvObj.getString("call_length"))/60;
			DecimalFormat df = new DecimalFormat(".00");
			rc.setCall_len(df.format(callLen));

			rcList.add(rc);
		}
		
		return rcList;
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
	public BlackInfo parseBlackInfo(String json){
		JSONObject reportObj = JSONObject.parseObject(json);
		// 用户黑名单信息
		BlackInfo blackInfo = new BlackInfo();
		JSONObject userInfoCheckObj = reportObj.getJSONObject("user_info_check");
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
	public List<FinancialCallInfo> parseFinancialCallInfo(String json){
		JSONObject reportObj = JSONObject.parseObject(json);

		List<FinancialCallInfo> fciList = new ArrayList<>();
		JSONArray behaviorCheckArr = reportObj.getJSONArray("behavior_check");
		for (int i = 0; i < behaviorCheckArr.size(); i++) {
			FinancialCallInfo fci = new FinancialCallInfo();
			JSONObject checkPointObj = behaviorCheckArr.getJSONObject(i);
			String checkPoint = checkPointObj.getString("check_point");
			// 澳门电话通话情、110话通话情况、120话通话情况、律师号码通话情况、法院号码通话情况、贷款类号码联系情况、银行类号码联系情况、信用卡类号码联系情况
			if (FinanceJxlEnum.isExist(checkPoint)) {
				fci.setCheck_point_cn(checkPointObj.getString("check_point_cn"));
				fci.setResult(checkPointObj.getString("result"));
				List<String> evidList = new ArrayList<>();
				evidList.add(checkPointObj.getString("evidence"));
				fci.setEvidence(evidList);
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
	public List<FinancialCallInfo> parseDHBFinBasCallInfo(String json){
		JSONObject dhbReportObj = JSONObject.parseObject(json);
		
		List<FinancialCallInfo> fciList = new ArrayList<>();
		JSONArray csbtfArr = dhbReportObj.getJSONArray("calls_sa_by_tags_financial");
		
		for (int i = 0; i < csbtfArr.size(); i++) {
			FinancialCallInfo fci = new FinancialCallInfo();
			JSONObject csbtfObj = csbtfArr.getJSONObject(i);
			String tagsName = csbtfObj.getString("tags_name");
			// 金融基础版
			String dbhFinance[] = {"赌博","110","120","律师","公检法","贷款","银行","信用卡热线"};
			List<String> dbhList = Arrays.asList(dbhFinance);
			if (dbhList.contains(tagsName)) {
				fci.setCheck_point_cn(csbtfObj.getString("tags_name"));
				// 最近一次通话记录
				String clt = csbtfObj.getString("contact_last_time");
				if (StringUtil.isNotEmpty(clt)) {
					fci.setResult("最近一次通话记录："+clt);
				}else {
					fci.setResult(clt);
				}
				
				List<String> cdList = new ArrayList<>();
				JSONArray cdArr = csbtfObj.getJSONArray("contact_detail");
				if (cdArr.size() > 0) {
					for (int j = 0; j < cdArr.size(); j++) {
						JSONObject cdObj = cdArr.getJSONObject(j);
						String typ = cdObj.getString("tags_yellow_page");
						int coutt = cdObj.getIntValue("call_out_times");
						int coutl = cdObj.getIntValue("call_out_length");
						int cint = cdObj.getIntValue("call_in_times");
						int cinl = cdObj.getIntValue("call_in_length");
						String cdStr = "["+typ+"]"+"主叫"+coutt+"次共"+DateUtil.secondToMinute(coutl)+"，被叫"+cint+"次共"+DateUtil.secondToMinute(cinl);
						cdList.add(cdStr);
					}
				}
				fci.setEvidence(cdList);
				
				fciList.add(fci);
			}
		}
		
		return fciList;
	}
	
	/** 
	 * @Title: parseDHBFinancialCallInfo 
	 * @Description: 电话邦-解析金融类标准版通话信息
	 * @author ZhangYadong
	 * @date 2018年1月29日 上午11:43:53
	 * @param json
	 * @return
	 * @return List<FinancialCallInfo>
	 */
	public List<FinancialCallInfo> parseDHBFinAdvCallInfo(String json){
		JSONObject dhbReportObj = JSONObject.parseObject(json);
		
		List<FinancialCallInfo> fciList = new ArrayList<>();
		JSONArray csbtfArr = dhbReportObj.getJSONArray("calls_sa_by_tags_financial");
		
		for (int i = 0; i < csbtfArr.size(); i++) {
			FinancialCallInfo fci = new FinancialCallInfo();
			JSONObject csbtfObj = csbtfArr.getJSONObject(i);
			String tagsName = csbtfObj.getString("tags_name");
			// 金融标准版
			String dbhFinance[] = {"互联网金融","典当","保险","理财","小号","证券"};
			List<String> dbhList = Arrays.asList(dbhFinance);
			if (dbhList.contains(tagsName)) {
				fci.setCheck_point_cn(csbtfObj.getString("tags_name"));
				// 最近一次通话记录
				String clt = csbtfObj.getString("contact_last_time");
				if (StringUtil.isNotEmpty(clt)) {
					fci.setResult("最近一次通话记录："+clt);
				}else {
					fci.setResult(clt);
				}
				
				List<String> cdList = new ArrayList<>();
				JSONArray cdArr = csbtfObj.getJSONArray("contact_detail");
				if (cdArr.size() > 0) {
					for (int j = 0; j < cdArr.size(); j++) {
						JSONObject cdObj = cdArr.getJSONObject(j);
						String typ = cdObj.getString("tags_yellow_page");
						int coutt = cdObj.getIntValue("call_out_times");
						int coutl = cdObj.getIntValue("call_out_length");
						int cint = cdObj.getIntValue("call_in_times");
						int cinl = cdObj.getIntValue("call_in_length");
						String cdStr = "["+typ+"]"+"主叫"+coutt+"次共"+DateUtil.secondToMinute(coutl)+"，被叫"+cint+"次共"+DateUtil.secondToMinute(cinl);
						cdList.add(cdStr);
					}
				}
				fci.setEvidence(cdList);

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
	public List<ContactRegion> parseContactRegion(String json){
		JSONObject reportObj = JSONObject.parseObject(json);
		List<ContactRegion> crList = new ArrayList<>();
		
		JSONArray contactRegionArr = reportObj.getJSONArray("contact_region");
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
	public List<ContactRegion> parseDHBContactRegion(String json){
		JSONObject reportObj = JSONObject.parseObject(json);
		List<ContactRegion> crList = new ArrayList<>();
		
		JSONArray contactRegionArr = reportObj.getJSONArray("calls_sa_by_region");
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
	public List<TopContact> parseDateTopContact(String json){
		JSONObject reportObj = JSONObject.parseObject(json);
		List<TopContact> tcList = new ArrayList<>();
		List<TopContact> urgentList = new ArrayList<>();
		
		// 联系人按照通话时长排序
		List<JSONObject> contactArrList = JSONObject.parseArray(reportObj.getString("contact_list"), JSONObject.class);
		Collections.sort(contactArrList, new Comparator<JSONObject>() {
			public int compare(JSONObject jo1, JSONObject jo2) {
				// 通话时长
				Integer callLen1 = jo1.getIntValue("call_len");
				Integer callLen2 = jo2.getIntValue("call_len");
				// 通话次数
				Integer callCnt1 = jo1.getIntValue("call_cnt");
				Integer callCnt2 = jo1.getIntValue("call_cnt");

				if (callLen1 != callLen2) {
					return callLen2.compareTo(callLen1);
					//return callLen1 < callLen2 ? 1 : -1;
					//return callLen2 > callLen1 ? 1 : -1;
				} else {
					return callCnt2.compareTo(callCnt1);
					//return callCnt1 < callCnt2 ? 1 : -1;
				}
			}

		});
		
		// 取前10条数据
		if (contactArrList != null && contactArrList.size() > 0) {
			if (contactArrList != null && contactArrList.size() > 0) {
				int size = contactArrList.size() > 10 ? 10 : contactArrList.size();
				for (int i = 0; i < size; i++) {
					JSONObject jo = contactArrList.get(i);
					TopContact tc = new TopContact();
					tc.setFormat_tel(jo.getString("phone_num"));
					tc.setTags_label(jo.getString("contact_name"));
					tc.setCall_times(jo.getString("call_cnt"));
					tc.setCall_length(jo.getString("call_len"));
					tcList.add(tc);
				}
			}
		}
		
		//TODO 解析紧急联系人信息
		JSONArray appCheckArr = reportObj.getJSONArray("application_check");
		for (int i = 0; i < appCheckArr.size(); i++) {
			JSONObject appObj = appCheckArr.getJSONObject(i);
			String appPoint = appObj.getString("app_point");
			TopContact tc = new TopContact();
			if (appPoint.equals("contact")) {
				String mobile = appObj.getJSONObject("check_points").getString("key_value");
				tc.setFormat_tel(mobile);
				String tagsLabel = appObj.getJSONObject("check_points").getString("relationship"); 
				tc.setTags_label(tagsLabel);
				String check_mobile = appObj.getJSONObject("check_points").getString("check_mobile");
				if (check_mobile != null) {
					ReportContact rcRegex = regexMatchContact(check_mobile);
					tc.setCall_times(rcRegex.getCall_cnt());
					tc.setCall_length(rcRegex.getCall_len());
				}
				urgentList.add(tc);
			}
		}
		
		if (tcList != null && tcList.size() > 0) {
			// 修改标签
			for (int i = 0; i < tcList.size(); i++) {
				for (int j = 0; j < urgentList.size(); j++) {
					if (tcList.get(i).getFormat_tel().equals(urgentList.get(j).getFormat_tel())) {
						tcList.get(i).setTags_label(urgentList.get(j).getTags_label());
					}
				}
			}
			// 添加紧急联系人
			for (TopContact topCon: urgentList) {
				boolean f = parseIsExistCon(tcList, topCon.getFormat_tel());
				if (!f) {
					tcList.add(topCon);
				}
			}
		}else {
			tcList = urgentList;
		}
		
		return tcList;
	}
	
	/** 
	 * @Title: parseIsExistCon 
	 * @Description: 解析是否存在紧急联系人
	 * @author ZhangYadong
	 * @date 2018年2月9日 上午10:06:37
	 * @param tcList
	 * @param mobile
	 * @return
	 * @return boolean
	 */
	private boolean parseIsExistCon(List<TopContact> tcList, String mobile){
		boolean f = false;
		if (tcList != null && tcList.size() > 0) {
			for (int i = 0; i < tcList.size(); i++) {
				if (mobile.equals(tcList.get(i).getFormat_tel())) {
					f = true;
				}
			}
		}
		return f;
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
	public List<TopContact> parseDHBDateTopContact(String json){
		JSONObject reportObj = JSONObject.parseObject(json);
		List<TopContact> tcList = new ArrayList<>();
		List<TopContact> urgentList = new ArrayList<>();
		
		JSONArray callsSaByLengthArr = reportObj.getJSONArray("calls_sa_by_length");
		for (int i = 0; i < callsSaByLengthArr.size(); i++) {
			TopContact tc = new TopContact();
			JSONObject callsSaByLengthObj = callsSaByLengthArr.getJSONObject(i);
			tc.setFormat_tel(callsSaByLengthObj.getString("format_tel"));
			tc.setTags_label(callsSaByLengthObj.getString("tags_label"));
			tc.setCall_times(callsSaByLengthObj.getString("call_times"));
			// 秒转分钟，保留2位小数
			float callLen = Float.parseFloat(callsSaByLengthObj.getString("call_length"))/60;
			DecimalFormat df = new DecimalFormat(".00");
			tc.setCall_length(df.format(callLen));
			tcList.add(tc);
		}
		
		// 解析紧急联系人信息
		JSONArray mcArr = reportObj.getJSONArray("mergency_contact");
		for (int i = 0; i < mcArr.size(); i++) {
			TopContact tc = new TopContact();
			JSONObject mvObj = mcArr.getJSONObject(i);
			String mobile = mvObj.getString("format_tel");
			tc.setFormat_tel(mobile);
			String tagsLabel = mvObj.getString("contact_relationship");
			tc.setTags_label(tagsLabel);
			tc.setCall_times(mvObj.getString("call_times"));
			
			float callLen = Float.parseFloat(mvObj.getString("call_length"))/60;
			DecimalFormat df = new DecimalFormat(".00");
			tc.setCall_length(df.format(callLen));

			urgentList.add(tc);
		}
		
		if (tcList != null && tcList.size() > 0) {
			// 修改标签
			for (int i = 0; i < tcList.size(); i++) {
				for (int j = 0; j < urgentList.size(); j++) {
					if (tcList.get(i).getFormat_tel().equals(urgentList.get(j).getFormat_tel())) {
						tcList.get(i).setTags_label(urgentList.get(j).getTags_label());
					}
				}
			}
			// 添加紧急联系人
			for (TopContact topCon: urgentList) {
				boolean f = parseIsExistCon(tcList, topCon.getFormat_tel());
				if (!f) {
					tcList.add(topCon);
				}
			}
		}else {
			tcList = urgentList;
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
	public List<TopContact> parseTimesTopContact(String json){
		JSONObject reportObj = JSONObject.parseObject(json);
		List<TopContact> tcList = new ArrayList<>();
		List<TopContact> urgentList = new ArrayList<>();
		
		// 联系人按照通话次数排序
		List<JSONObject> contactArrList = JSONObject.parseArray(reportObj.getString("contact_list"), JSONObject.class);
		Collections.sort(contactArrList, new Comparator<JSONObject>() {
			public int compare(JSONObject jo1, JSONObject jo2) {
				// 通话次数
				Integer callCnt1 = jo1.getIntValue("call_cnt");
				Integer callCnt2 = jo1.getIntValue("call_cnt");
				// 通话时长
				Integer callLen1 = jo1.getIntValue("call_len");
				Integer callLen2 = jo2.getIntValue("call_len");

				if (callCnt1 != callCnt2) {
					return callCnt2.compareTo(callCnt1);
					//return callCnt1 < callCnt2 ? 1 : -1;
				} else {
					return callLen2.compareTo(callLen1);
					//return callLen1 < callLen2 ? 1 : -1;
				}
			}

		});
		
		// 取前10条数据
		if (contactArrList != null && contactArrList.size() > 0) {
			int size = contactArrList.size() > 10 ? 10 : contactArrList.size();
			for (int i = 0; i < size; i++) {
				JSONObject jo = contactArrList.get(i);
				TopContact tc = new TopContact();
				tc.setFormat_tel(jo.getString("phone_num"));
				tc.setTags_label(jo.getString("contact_name"));
				tc.setCall_times(jo.getString("call_cnt"));
				tc.setCall_length(jo.getString("call_len"));
				tcList.add(tc);
			}
		}
		//TODO 解析紧急联系人信息
		JSONArray appCheckArr = reportObj.getJSONArray("application_check");
		for (int i = 0; i < appCheckArr.size(); i++) {
			JSONObject appObj = appCheckArr.getJSONObject(i);
			String appPoint = appObj.getString("app_point");
			TopContact tc = new TopContact();
			if (appPoint.equals("contact")) {
				String mobile = appObj.getJSONObject("check_points").getString("key_value");
				tc.setFormat_tel(mobile);
				String tagsLabel = appObj.getJSONObject("check_points").getString("relationship");
				tc.setTags_label(tagsLabel);
				String check_mobile = appObj.getJSONObject("check_points").getString("check_mobile");
				if (check_mobile != null) {
					ReportContact rcRegex = regexMatchContact(check_mobile);
					tc.setCall_times(rcRegex.getCall_cnt());
					tc.setCall_length(rcRegex.getCall_len());
				}
				
				urgentList.add(tc);
			}
		}
		
		if (tcList != null && tcList.size() > 0) {
			// 修改标签
			for (int i = 0; i < tcList.size(); i++) {
				for (int j = 0; j < urgentList.size(); j++) {
					if (tcList.get(i).getFormat_tel().equals(urgentList.get(j).getFormat_tel())) {
						tcList.get(i).setTags_label(urgentList.get(j).getTags_label());
					}
				}
			}
			// 添加紧急联系人
			for (TopContact topCon: urgentList) {
				boolean f = parseIsExistCon(tcList, topCon.getFormat_tel());
				if (!f) {
					tcList.add(topCon);
				}
			}
		}else {
			tcList = urgentList;
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
	public List<TopContact> parseDHBTimesTopContact(String json){
		JSONObject reportObj = JSONObject.parseObject(json);
		List<TopContact> tcList = new ArrayList<>();
		List<TopContact> urgentList = new ArrayList<>();
		
		JSONArray callsSaByLengthArr = reportObj.getJSONArray("calls_sa_by_times");

		for (int i = 0; i < callsSaByLengthArr.size(); i++) {
			TopContact tc = new TopContact();
			JSONObject callsSaByLengthObj = callsSaByLengthArr.getJSONObject(i);
			tc.setFormat_tel(callsSaByLengthObj.getString("format_tel"));
			tc.setTags_label(callsSaByLengthObj.getString("tags_label"));
			tc.setCall_times(callsSaByLengthObj.getString("call_times"));
			// 秒转分钟，保留2位小数
			float callLen = Float.parseFloat(callsSaByLengthObj.getString("call_length"))/60;
			DecimalFormat df = new DecimalFormat(".00");
			tc.setCall_length(df.format(callLen));
			tcList.add(tc);
		}
		
		// 解析紧急联系人信息
		JSONArray mcArr = reportObj.getJSONArray("mergency_contact");
		for (int i = 0; i < mcArr.size(); i++) {
			TopContact tc = new TopContact();
			JSONObject mvObj = mcArr.getJSONObject(i);
			String mobile = mvObj.getString("format_tel");
			tc.setFormat_tel(mobile);
			String tagsLabel = mvObj.getString("contact_relationship");
			tc.setTags_label(tagsLabel);
			tc.setCall_times(mvObj.getString("call_times"));
			
			float callLen = Float.parseFloat(mvObj.getString("call_length"))/60;
			DecimalFormat df = new DecimalFormat(".00");
			tc.setCall_length(df.format(callLen));
			
			urgentList.add(tc);
		}
		
		if (tcList != null && tcList.size() > 0) {
			// 修改标签
			for (int i = 0; i < tcList.size(); i++) {
				for (int j = 0; j < urgentList.size(); j++) {
					if (tcList.get(i).getFormat_tel().equals(urgentList.get(j).getFormat_tel())) {
						tcList.get(i).setTags_label(urgentList.get(j).getTags_label());
					}
				}
			}
			// 添加紧急联系人
			for (TopContact topCon: urgentList) {
				boolean f = parseIsExistCon(tcList, topCon.getFormat_tel());
				if (!f) {
					tcList.add(topCon);
				}
			}
		}else {
			tcList = urgentList;
		}
		return tcList;
	}
	
	/** 
	 * @Title: parseAllContact 
	 * @Description: 聚信立-解析所有联系人数据
	 * @author ZhangYadong
	 * @date 2018年1月29日 下午3:26:32
	 * @param json
	 * @return
	 * @return List<TopContact>
	 */
	public List<TopContact> parseAllContact(String json){
		JSONObject reportObj = JSONObject.parseObject(json);
		List<TopContact> tcList = new ArrayList<>();
		
		JSONArray contactArr = reportObj.getJSONArray("contact_list");
		for (int i = 0; i < contactArr.size(); i++) {
			TopContact tc = new TopContact();
			JSONObject contactObj = contactArr.getJSONObject(i);
			tc.setFormat_tel(contactObj.getString("phone_num"));
			tc.setTags_label(contactObj.getString("contact_name"));
			tc.setCall_times(contactObj.getString("call_cnt"));
			tc.setCall_length(contactObj.getString("call_len"));
			tc.setFancha_telloc(contactObj.getString("phone_num_loc"));
			tc.setCall_out_times(contactObj.getInteger("call_out_cnt"));
			tc.setCall_in_times(contactObj.getInteger("call_in_cnt"));
			tcList.add(tc);
		}
		return tcList;
	}
	
	/** 
	 * @Title: parseDHBAllContact 
	 * @Description: 电话邦-解析所有联系人数据 
	 * @author ZhangYadong
	 * @date 2018年1月29日 下午3:28:45
	 * @param json
	 * @return
	 * @return List<TopContact>
	 */
	public List<TopContact> parseDHBAllContact(String json){
		JSONObject reportObj = JSONObject.parseObject(json);
		List<TopContact> tcList = new ArrayList<>();
		
		JSONArray contactArr = reportObj.getJSONArray("call_log_group_by_tel");
		for (int i = 0; i < contactArr.size(); i++) {
			TopContact tc = new TopContact();
			JSONObject contactObj = contactArr.getJSONObject(i);
			tc.setFormat_tel(contactObj.getString("format_tel"));
			tc.setTags_label(contactObj.getString("tags_label"));
			tc.setCall_times(contactObj.getString("call_times"));
			tc.setCall_length(contactObj.getString("call_length"));
			tc.setFancha_telloc(contactObj.getString("fancha_telloc"));
			tc.setCall_out_times(contactObj.getInteger("call_out_times"));
			tc.setCall_in_times(contactObj.getInteger("call_in_times"));
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
	public List<TripInfo> parseTripInfo(String json){
		JSONObject reportObj = JSONObject.parseObject(json);
		List<TripInfo> tfList = new ArrayList<>();
		JSONArray tripInfoArr = reportObj.getJSONArray("trip_info");
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
	public Cuishou parseDHBCuishou(String json){
		JSONObject reportObj = JSONObject.parseObject(json);
		Cuishou cs = null;
		JSONObject crdObj = reportObj.getJSONObject("cuishou_risk_detection");
		if (crdObj.containsKey("cuishou") && crdObj.containsKey("cuishou_degree")) {
			JSONObject cuishou = crdObj.getJSONObject("cuishou");
			JSONObject cuishou_degree = crdObj.getJSONObject("cuishou_degree");
			cs = parseCuishouJson(cuishou, cuishou_degree);
		}else {
			cs = parseCuishouIsEmptyJson();
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
	public Cuishou parseDHBYisiCuishou(String json){
		JSONObject reportObj = JSONObject.parseObject(json);
		Cuishou cs = new Cuishou();
		
		JSONObject crdObj = reportObj.getJSONObject("cuishou_risk_detection");

		if (crdObj.containsKey("yisicuishou") && crdObj.containsKey("yisicuishou_degree")) {
			JSONObject yisicuishou = crdObj.getJSONObject("yisicuishou");
			JSONObject yisicuishou_degree = crdObj.getJSONObject("yisicuishou_degree");
			cs = parseCuishouJson(yisicuishou, yisicuishou_degree);
		}else {
			cs = parseCuishouIsEmptyJson();
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
	private Cuishou parseCuishouJson(JSONObject cuishou, JSONObject cuishou_degree) {
		Cuishou cs = new Cuishou();
		if (cuishou.getInteger("nums_tel") != null) {
			cs.setNums_tel(cuishou.getInteger("nums_tel"));
		}else {
			cs.setNums_tel(0);
		}
		if (cuishou_degree.getInteger("nums_tel") != null) {
			cs.setNums_tel_degree(cuishou_degree.getInteger("nums_tel"));
		}else {
			cs.setNums_tel_degree(0);
		}
		if (cuishou.getString("call_times") != null) {
			cs.setCall_times(cuishou.getString("call_times"));
		}else {
			cs.setCall_times("0");
		}
		if (cuishou.getInteger("call_in_times") != null) {
			cs.setCall_in_times(cuishou.getInteger("call_in_times"));
		}else {
			cs.setCall_in_times(0);
		}
		if (cuishou_degree.getInteger("call_in_times") != null) {
			cs.setCall_in_times_degree(cuishou_degree.getInteger("call_in_times"));
		}else {
			cs.setCall_in_times_degree(0);
		}
		if (cuishou.getInteger("call_in_length") != null) {
			cs.setCall_in_length(cuishou.getInteger("call_in_length"));
		}else {
			cs.setCall_in_length(0);
		}
		if (cuishou_degree.getInteger("call_in_length") != null) {
			cs.setCall_in_length_degree(cuishou_degree.getInteger("call_in_length"));
		}else {
			cs.setCall_in_length_degree(0);
		}
		if (cuishou.getInteger("call_in_less_15") != null) {
			cs.setCall_in_less_15(cuishou.getInteger("call_in_less_15"));
		}else {
			cs.setCall_in_less_15(0);
		}
		if (cuishou_degree.getInteger("call_in_less_15") != null) {
			cs.setCall_in_less_15_degree(cuishou_degree.getInteger("call_in_less_15"));
		}else {
			cs.setCall_in_less_15_degree(0);
		}
		if (cuishou.getInteger("most_times_by_tel") != null) {
			cs.setMost_times_by_tel(cuishou.getInteger("most_times_by_tel"));
		}else {
			cs.setMost_times_by_tel(0);
		}
		if (cuishou_degree.getInteger("most_times_by_tel") != null) {
			cs.setMost_times_by_tel_degree(cuishou_degree.getInteger("most_times_by_tel"));
		}else {
			cs.setMost_times_by_tel_degree(0);
		}
		if (cuishou.getInteger("up_2_times_by_tel") != null) {
			cs.setUp_2_times_by_tel(cuishou.getInteger("up_2_times_by_tel"));
		}else {
			cs.setUp_2_times_by_tel(0);
		}
		if (cuishou_degree.getInteger("up_2_times_by_tel") != null) {
			cs.setUp_2_times_by_tel_degree(cuishou_degree.getInteger("up_2_times_by_tel"));
		}else {
			cs.setUp_2_times_by_tel_degree(0);
		}
		if (cuishou.getInteger("call_out_times") != null) {
			cs.setCall_out_times(cuishou.getInteger("call_out_times"));
		}else {
			cs.setCall_out_times(0);
		}
		if (cuishou_degree.getInteger("call_out_times") != null) {
			cs.setCall_out_times_degree(cuishou_degree.getInteger("call_out_times"));
		}else {
			cs.setCall_out_times_degree(0);
		}
		if (cuishou.getInteger("call_out_length") != null) {
			cs.setCall_out_length(cuishou.getInteger("call_out_length"));
		}else {
			cs.setCall_out_length(0);
		}
		if (cuishou_degree.getInteger("call_out_length") != null) {
			cs.setCall_out_length_degree(cuishou_degree.getInteger("call_out_length"));
		}else {
			cs.setCall_out_length_degree(0);
		}
		if (cuishou.getInteger("7day_times") != null) {
			cs.setDay7_times(cuishou.getInteger("7day_times"));
		}else {
			cs.setDay7_times(0);
		}
		if (cuishou_degree.getInteger("7day_times") != null) {
			cs.setDay7_times_degree(cuishou_degree.getInteger("7day_times"));
		}else {
			cs.setDay7_times_degree(0);
		}
		if (cuishou.getInteger("30day_times") != null) {
			cs.setDay30_times(cuishou.getInteger("30day_times"));
		}else {
			cs.setDay30_times(0);
		}
		if (cuishou_degree.getInteger("30day_times") != null) {
			cs.setDay30_times_degree(cuishou_degree.getInteger("30day_times"));
		}else {
			cs.setDay30_times_degree(0);
		}
		if (cuishou.getInteger("60day_times") != null) {
			cs.setDay60_times(cuishou.getInteger("60day_times"));
		}else {
			cs.setDay60_times(0);
		}
		if (cuishou_degree.getInteger("60day_times") != null) {
			cs.setDay60_times_degree(cuishou_degree.getInteger("60day_times"));
		}else {
			cs.setDay60_times_degree(0);
		}
		if (cuishou.getInteger("90day_times") != null) {
			cs.setDay90_times(cuishou.getInteger("90day_times"));
		}else {
			cs.setDay90_times(0);
		}
		if (cuishou_degree.getInteger("90day_times") != null) {
			cs.setDay90_times_degree(cuishou_degree.getInteger("90day_times"));
		}else {
			cs.setDay90_times_degree(0);
		}
		if (cuishou.getInteger("120day_times") != null) {
			cs.setDay120_times(cuishou.getInteger("120day_times"));
		}else {
			cs.setDay120_times(0);
		}
		if (cuishou_degree.getInteger("120day_times") != null) {
			cs.setDay120_times_degree(cuishou_degree.getInteger("120day_times"));
		}else {
			cs.setDay120_times_degree(0);
		}
		return cs;
	}
	
	/** 
	 * @Title: parseCuishouIsEmptyJson 
	 * @Description: 解析催收数据为null的json数据
	 * @author ZhangYadong
	 * @date 2018年1月29日 上午11:16:28
	 * @param cuishou
	 * @param cuishou_degree
	 * @return
	 * @return Cuishou
	 */
	private Cuishou parseCuishouIsEmptyJson() {
		Cuishou cs = new Cuishou();
		cs.setNums_tel(0);
		cs.setNums_tel_degree(0);
		cs.setCall_times("0");
		cs.setCall_in_times(0);
		cs.setCall_in_times_degree(0);
		cs.setCall_in_length(0);
		cs.setCall_in_length_degree(0);
		cs.setCall_in_less_15(0);
		cs.setCall_in_less_15_degree(0);
		cs.setMost_times_by_tel(0);
		cs.setMost_times_by_tel_degree(0);
		cs.setUp_2_times_by_tel(0);
		cs.setUp_2_times_by_tel_degree(0);
		cs.setCall_out_times(0);
		cs.setCall_out_times_degree(0);
		cs.setCall_out_length(0);
		cs.setCall_out_length_degree(0);
		cs.setDay7_times(0);
		cs.setDay7_times_degree(0);
		cs.setDay30_times(0);
		cs.setDay30_times_degree(0);
		cs.setDay60_times(0);
		cs.setDay60_times_degree(0);
		cs.setDay90_times(0);
		cs.setDay90_times_degree(0);
		cs.setDay120_times(0);
		cs.setDay120_times_degree(0);
		return cs;
	}

}
