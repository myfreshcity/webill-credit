package com.webill.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webill.app.util.AddressUtil;
import com.webill.app.util.StringUtil;
import com.webill.core.Constant;
import com.webill.core.model.CarCardApply;
import com.webill.core.model.UserContact;
import com.webill.core.service.ICarCardApplyService;
import com.webill.core.service.IUserContactService;
import com.webill.core.service.mapper.UserContactMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * UserContact 服务层接口实现类
 *
 */
@Service
public class UserContactServiceImpl extends SuperServiceImpl<UserContactMapper, UserContact> implements IUserContactService {
	    
	
    @Autowired
    private ICarCardApplyService carCardApplyService;
    @Autowired
    private UserContactMapper userContactMapper;
    
	@Override
	public boolean checkMobileIsExist(String mobile) {
		boolean f = false;
		logger.info("**** checkMobileIsExist mobile:" + mobile);
		int mobileTotal = userContactMapper.checkMobileIsExist(mobile);
		logger.info("**** checkMobileIsExist uclist totalCount:" + mobileTotal);
		if (mobileTotal>0) {
			f = true;
			logger.info("**** checkMobileIsExist mobile:" + mobile+" is exist");
		}else{
			f = false;
			logger.info("**** checkMobileIsExist mobile:" + mobile+" is not exist");
		}
		return f;
	}

	@Transactional
	@Override
	public Object saveContact(UserContact uc) {
		boolean f = false;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_id", uc.getUserId());
		map.put("status", Constant.USER_CONTACT_NORMAL_STATUS);
		List<UserContact> uclist = this.selectByMap(map);
		if(uclist.size()>0){
			uc.setIsDefault(Constant.USER_CONTACT_NOT_DEFAULT);
		}else{
			uc.setIsDefault(Constant.USER_CONTACT_IS_DEFAULT);
		}
		uc.settStatus(Constant.USER_CONTACT_NORMAL_STATUS);
		uc.setCreatedTime(new Date());
		List<String> addStr = AddressUtil.splidAddress(uc.getCity());
		if(addStr.size()==3){
			uc.setProvince(addStr.get(0));
			uc.setCity(addStr.get(1));
			uc.setArea(addStr.get(2));
		}
		f = this.insertOrUpdateSelective(uc);
		
		
		if(StringUtil.isNotEmpty(uc.getCarId())){
			//*************************************************************保存*******信用卡申请记录*****************************
			UserContact ucwhere = new UserContact();
			ucwhere = this.selectById(uc.getId());
			CarCardApply ccr = new CarCardApply();
			ccr.setApplicantAddress(ucwhere.getProvince()+ucwhere.getCity()+ucwhere.getArea()+ucwhere.getAddress());
			ccr.setApplicantMobile(ucwhere.getMobile());
			ccr.setApplicantName(ucwhere.getContactName());
			ccr.setCarId(Integer.valueOf(uc.getCarId()));
			ccr.setCreatedTime(new Date());
			ccr.setLicenseNo(uc.getLicenseNo());
			ccr.setUcId(ucwhere.getId());
			ccr.setUserId(ucwhere.getUserId());
			f = carCardApplyService.insertSelective(ccr);
			if(f){
				return ccr;
			}else{
				return null;
			}
			//*************************************************************保存*******信用卡申请记录*****************************
		}else{
			return uc;
		}
		
	}
	@Transactional
	@Override
	public UserContact saveMobile(UserContact uc) {
		boolean f = false;
		UserContact ucold = this.selectById(uc.getId());
		UserContact ucnew  = new UserContact();
		String[] ignoreProperties = {"id","mobile","createdTime"};
		BeanUtils.copyProperties(ucold, ucnew, ignoreProperties);
		ucnew.setMobile(uc.getMobile());
		ucnew.setCreatedTime(new Date());
		//保存更换的手机号
		f = this.insertSelective(ucnew);
		//作废原来的数据
		f = this.delUserContact(uc.getId().toString());
		
		if(f){
			return ucnew;
		}else{
			return null;
		}
	}

	@Transactional
	@Override
	public boolean updateDefault(String id) {
		boolean f = false;
    	UserContact uc = this.selectById(id);
    	Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_id", uc.getUserId());
		map.put("status", Constant.NORMAL_STATUS);
		List<UserContact> uclist = this.selectByMap(map);
		UserContact user = null;
		UserContact whereEntity = null;
		if(uclist.size()>0){
			for(UserContact c:uclist){
				if(c.getIsDefault()!=null){
					if(c.getIsDefault()==Constant.USER_CONTACT_IS_DEFAULT){
						whereEntity = new UserContact();
						whereEntity.setId(c.getId());
						user = new UserContact();
						user.setIsDefault(Constant.USER_CONTACT_NOT_DEFAULT);//改为非默认
						f = this.updateSelective(user, whereEntity);
					}
				}
			}
			
		}
    	UserContact where = new UserContact();
    	where.setId(Integer.valueOf(id));
    	//把当前数据设置为默认
    	where.setIsDefault(Constant.USER_CONTACT_IS_DEFAULT);
		f = this.updateSelectiveById(where);
		return f;
	}
	@Transactional
	@Override
	public boolean updateNoDefault(String id) {
		boolean f = false;
    	UserContact a = new UserContact();
    	UserContact wherea = new UserContact();
    	wherea.setId(Integer.valueOf(id));
    	//把当前数据设置为默认
    	a.setIsDefault(Constant.USER_CONTACT_NOT_DEFAULT);
		f = this.updateSelective(a, wherea);
		return f;
	}
	
	@Override
	public boolean delUserContact(String id){
		boolean f = false;
		UserContact entity = new UserContact();
		UserContact whereEntity = new UserContact();
		entity.settStatus(Integer.valueOf(Constant.USER_CONTACT_DEL_STATUS));
		whereEntity.setId(Integer.valueOf(id));
		f = this.updateSelective(entity, whereEntity);
		return f;
	}
	@Transactional
	@Override
	public UserContact updateUserContact(UserContact entity){
		if(entity.getCity().equals("[\"\"]")){
			entity.setCity("");
		}
    	boolean f = false;
    	UserContact uc = new UserContact();
    	
    	//获取历史联系人信息
    	UserContact entityOld = this.selectById(entity.getId());
    	if(!entity.equals(entityOld)){
    		//作废原来数据
    		UserContact oldWhere = new UserContact();
    		oldWhere.settStatus(Constant.USER_CONTACT_DEL_STATUS);
    		oldWhere.setId(entity.getId());
    		f = this.updateSelectiveById(oldWhere);
    		
    		//新增一条数据
    		List<String> str = AddressUtil.splidAddress(entity.getCity());
    		UserContact entityNew = new UserContact();
    		
    		entityNew.setContactName(entity.getContactName());
    		entityNew.setUserId(entityOld.getUserId());
    		entityNew.setAddress(entity.getAddress());
    		entityNew.setProvince(str.get(0));
    		entityNew.setCity(str.get(1));
    		entityNew.setArea(str.get(2));
    		entityNew.setEmail(entity.getEmail());
    		entityNew.settStatus(Constant.NORMAL_STATUS);
    		entityNew.setMobile(entity.getMobile());
    		
    		f = this.insertSelective(entityNew);
//    		if(f){
//    			if(!entity.getIsDefault().equals(entityOld.getIsDefault())){
//    				if(entity.getIsDefault()==1){
//    					//设为默认
//    					f = this.updateDefault(entityNew.getId().toString());
//    				}else if(entity.getIsDefault()==0){
//    					//取消默认
//    					f = this.updateNoDefault(entityNew.getId().toString());
//    				}
//    			}
//    		}
    	}
    	
    	if(f){
    		uc.setIsSuccess(Constant.STATUS_SUCCESS);
    	}else{
    		uc.setIsSuccess(Constant.STATUS_FAIL);
    	}
		return uc;
	}
	@Override
	public List<UserContact> getUserContact(Map<String,Object> map){
		List<UserContact> uclist = new ArrayList<UserContact>();
		uclist = baseMapper.getUserContact(map); 
		if(uclist.size()>0){
			for(UserContact uc:uclist){
	    		String[] addStr =new String[3];
	    		if(StringUtil.isNotEmpty(uc.getProvince())&&StringUtil.isNotEmpty(uc.getCity())&&StringUtil.isNotEmpty(uc.getArea())){
	    			addStr[0] =uc.getProvince();
	    			addStr[1] =uc.getCity();
	    			addStr[2] =uc.getArea();
	    		}else{
	    			addStr =null;
	    		}
	    		uc.setCityArray(addStr);
			}
			
		}
		return uclist;
	}

}