package com.webill.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.SystemProperty;
import com.webill.app.util.AreaChildrenUtil;
import com.webill.app.util.DhbContactType;
import com.webill.core.model.CusContact;
import com.webill.core.model.Customer;
import com.webill.core.model.User;
import com.webill.core.model.dianhuabang.DHBGetLoginReq;
import com.webill.core.model.dianhuabang.DHBUserContact;
import com.webill.core.model.juxinli.JXLContact;
import com.webill.core.model.juxinli.JXLSubmitFormReq;
import com.webill.core.service.ICusContactService;
import com.webill.core.service.ICustomerService;
import com.webill.core.service.IUserService;
import com.webill.core.service.mapper.CustomerMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * Customer 服务层接口实现类
 *
 */
@Service
public class CustomerServiceImpl extends SuperServiceImpl<CustomerMapper, Customer> implements ICustomerService {
	@Autowired
	private ICusContactService cusContactService;
	@Autowired
	private IUserService userService;
	@Autowired
    private SystemProperty constPro;
	@Autowired
	private AreaChildrenUtil areaChildrenUtil;
	
	@Override
	public Page<Customer> getCusList(Page<Customer> page, Customer cus){
		List<Customer> cusList = baseMapper.getCusList(page, cus);
		for (Customer cust : cusList) {
			if (cust.getLatestReportTime() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cust.setLatestReportTimeStr(sdf.format(cust.getLatestReportTime()));
			}
		}
		page.setRecords(cusList);
		return page;
	}
	
	@Override
	public Customer getCusByBasicInfo(Customer cus){
		Customer cusDetail = null;
		// 获取客户基本信息
		Map<String, Object> cusMap = new HashMap<>();
		cusMap.put("user_id", cus.getUserId());
		cusMap.put("real_name", cus.getRealName());
		cusMap.put("id_no", cus.getIdNo());
		cusMap.put("mobile_no", cus.getMobileNo());
		List<Customer> cusList = this.selectByMap(cusMap);
		if (cusList != null && cusList.size() > 0) {
			cusDetail = cusList.get(0);
		}

		if (cusDetail != null) {
			Customer cust = this.addSelectTimes(cusDetail);
			cusDetail.setStandardTimes(cust.getStandardTimes());
			cusDetail.setAdvancedTimes(cust.getAdvancedTimes());
			cusDetail.setContacts(cust.getContacts());
			cusDetail.setAreaJson(cust.getAreaJson());
		}
		return cusDetail;
	}
	
	@Override
	public Customer addSelectTimes(Customer cus){
		// 获取用户信息可用次数
		User user = userService.selectById(cus.getUserId());
		cus.setStandardTimes(user.getStandardTimes());
		cus.setAdvancedTimes(user.getAdvancedTimes());
		
		// 获取客户联系人信息
		Map<String, Object> conMap = new HashMap<>();
		conMap.put("cus_id", cus.getId());
		List<CusContact> cusConList = cusContactService.selectByMap(conMap);
		if (cusConList != null && cusConList.size() > 0) {
			cus.setContacts(cusConList);
		}
		cus.setAreaJson(constPro.AREA_JSON);
		return cus;
	}
	
	@Override
	public Customer getCusByUserIdCusId(Customer cus){
		// 获取客户基本信息
		Customer cusDetail = this.selectById(cus.getId());
		
		if (cusDetail != null) {
			// 获取用户信息可用次数
			User user = userService.selectById(cus.getUserId());
			cusDetail.setStandardTimes(user.getStandardTimes());
			cusDetail.setAdvancedTimes(user.getAdvancedTimes());
			
			// 获取客户联系人信息
			Map<String, Object> conMap = new HashMap<>();
			conMap.put("cus_id", cusDetail.getId());
			List<CusContact> cusConList = cusContactService.selectByMap(conMap);
			if (cusConList != null && cusConList.size() > 0) {
				cusDetail.setContacts(cusConList);
			}
		}
		return cusDetail;
	}
	
	@Override
	public boolean updateCus(Customer cus){
		cus.setHomeAddr(areaChildrenUtil.getLable(cus.getHomeAddrCode()));
		cus.setWorkAddr(areaChildrenUtil.getLable(cus.getWorkAddrCode()));
		boolean f = false;
		// 更新客户联系人信息
		if (cus.getContacts() != null) {
			// 1：先删除客户联系人（批量删除）
			cusContactService.delCusContact(cus.getId());
			List<CusContact> contacts = cus.getContacts();
			// 2：填写新的客户联系人（批量添加）
			for (CusContact cusCon : contacts) {
				cusCon.setCusId(cus.getId());
			}
			cusContactService.insertBatch(contacts);
		}
		// 更新客户基本信息
		f = this.updateSelectiveById(cus);
		return f;
	}
	
	@Override
	public JXLSubmitFormReq getJXLSubmitFormReq(Integer cusId){
		Customer cusNew = this.selectById(cusId);
		// 根据cusId获取联系人列表
		Map<String, Object> map = new HashMap<>();
		map.put("cus_id", cusId);
		List<CusContact> cusConList = cusContactService.selectByMap(map);
		
		JXLSubmitFormReq req = new JXLSubmitFormReq();
		// 解析客戶对应联系人==>提交表单到聚信立联系人
		List<JXLContact> jxlContacts = new ArrayList<>();
		
		if (cusConList != null && cusConList.size() > 0) {
			for (CusContact cusCon : cusConList) {
				JXLContact jxlCon = new JXLContact();
				jxlCon.setContactTel(cusCon.getMobileNo());
				jxlCon.setContactName(cusCon.getName());
				jxlCon.setContactType(cusCon.getContactType().toString());
				jxlContacts.add(jxlCon);
			}
			req.setContacts(jxlContacts);
		}
		
		req.setName(cusNew.getRealName());
		req.setIdCardNum(cusNew.getIdNo());
		req.setMobileNo(cusNew.getMobileNo());
		req.setHomeAddress(cusNew.getHomeAddr() + cusNew.getHomeAddrDetail());
		req.setWorkAddress(cusNew.getWorkAddr() + cusNew.getWorkAddrDetail());
		req.setUid(cusNew.getUserId().toString());
		return req;
	}
	
	@Override
	public JXLSubmitFormReq cusToJXLSubmitFormReq(Customer cus){
		JXLSubmitFormReq req = new JXLSubmitFormReq();
		// 解析客戶对应联系人==>提交表单到聚信立联系人
		List<JXLContact> jxlContacts = new ArrayList<>();
		List<CusContact> cusConList = cus.getContacts();
		if (cusConList != null && cusConList.size() > 0) {
			for (CusContact cusCon : cusConList) {
				JXLContact jxlCon = new JXLContact();
				jxlCon.setContactTel(cusCon.getMobileNo());
				jxlCon.setContactName(cusCon.getName());
				jxlCon.setContactType(cusCon.getContactType().toString());
				jxlContacts.add(jxlCon);
			}
			req.setContacts(jxlContacts);
		}
		
		Customer cusNew = this.selectById(cus.getId());
		req.setName(cusNew.getRealName());
		req.setIdCardNum(cusNew.getIdNo());
		req.setMobileNo(cusNew.getMobileNo());
		req.setHomeAddress(cusNew.getHomeAddr() + cusNew.getHomeAddrDetail());
		req.setWorkAddress(cusNew.getWorkAddr() + cusNew.getWorkAddrDetail());
		req.setUid(cusNew.getUserId().toString());
		return req;
	}
	
	@Override
	public DHBGetLoginReq cusToDHBGetLoginReq(Customer cus){
		DHBGetLoginReq req = new DHBGetLoginReq();
		// 解析客戶对应联系人==>电话邦联系人
		List<DHBUserContact> dhbContacts = new ArrayList<>();
		List<CusContact> cusConList = cus.getContacts();
		if (cusConList != null && cusConList.size() > 0) {
			for (CusContact cusCon : cusConList) {
				DHBUserContact dhbCon = new DHBUserContact();
				dhbCon.setContactName(cusCon.getName());
				//dhbCon.setContactPrionity(1);
				//"0":配偶，"1":父母，"2":兄弟姐妹,"3":子女,"4":同事,"5": 同学,"6": 朋友
				dhbCon.setContactRelationShip(DhbContactType.retValue(cusCon.getContactType()));
				dhbCon.setContactTel(cusCon.getMobileNo());
				dhbContacts.add(dhbCon);
			}
			req.setContacts(dhbContacts);
		}
		
		Customer cusNew = this.selectById(cus.getId());
		req.setUserName(cusNew.getRealName());
		req.setUserIdcard(cusNew.getIdNo());
//		req.setUserProvince("");
//		req.setUserCity("");
		req.setUserAddress(cusNew.getHomeAddr() + cusNew.getHomeAddrDetail());
		req.setUid(cusNew.getUserId().toString());
		req.setTel(cusNew.getMobileNo());
		
		return req;
	}
}