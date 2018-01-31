package com.webill.core.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.webill.app.util.DateUtil;
import com.webill.app.util.EmptyUtil;
import com.webill.app.util.StringUtil;
import com.webill.core.model.UserInfo;
import com.webill.core.model.juxinli.Report;
import com.webill.core.service.IUserMongoDBService;

/**
 * 用户接口实现类
 * <p>
 * ClassName: UserDaoImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: (c)2017 Jastar·Wang,All rights reserved.
 * </p>
 * 
 * @author Jastar·Wang
 * @date 2017年4月12日
 */
@Repository
public class UserMongoDBServiceImpl extends BaseMongoDBImpl<UserInfo> implements IUserMongoDBService {

	@Override
	protected Class<UserInfo> getEntityClass() {
		return UserInfo.class;
	}
	
	@Override
	public List<UserInfo> selectAllTimeData(){
		
		try {
			
			SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			Date date = new Date();
			Query query = new Query();
			Criteria criteria = Criteria.where("applyDate").gte(format.parse("2018-01-19")).lt("2018-01-20").and("status").is("-1");
			query.addCriteria(criteria);
			mgt.remove(query, Report.class, "coll_report");
			
			
			/*SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			String starttime="2018-01-23 11:16:15";
			String endtime="2018-01-23 11:17:06";
			Date date = new Date();
			
			Query query = new Query();
			Criteria criteria = Criteria.where("birth").gte(format.parse(DateUtil.getYesterdayDate(date))).lt(date);
			query.addCriteria(criteria);
			long count = mgt.count(query, UserInfo.class, "coll_user");
			System.out.println(count);*/
			
			List<UserInfo> users = mgt.find(query, UserInfo.class, "coll_user");
			mgt.remove(query, UserInfo.class, "coll_user");
			return users;
			
			/*SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			Date date = new Date();
			Query query = new Query();
			Criteria criteria = Criteria.where("status").is("-1");
			query.addCriteria(criteria);
			mgt.remove(query, Report.class, "coll_report");*/
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void updateField(UserInfo entity) {

		// 反向解析对象
		Map<String, Object> map = null;
		try {
			map = parseEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ID字段
		String idName = null;
		Object idValue = null;
		
		// 生成参数
		Update update = new Update();
		/*if (entity.getName() != null) {
			update.set("name", entity.getName());
		}
		if (entity.getAge() != null) {
			update.set("myage", entity.getAge());
		}
		if (entity.getBirth() != null) {
			update.set("birth", entity.getBirth());
		}*/
		
		if (EmptyUtil.isNotEmpty(map)) {
			for (String key : map.keySet()) {
				if (key.indexOf("{") != -1) {
					// 设置ID
					idName = key.substring(key.indexOf("{") + 1, key.indexOf("}"));
					idValue = map.get(key);
				} else if (!StringUtil.isEmpty(map.get(key))) {
					update.set(key, map.get(key));
				}
			}
		}
		/*update.set("name", value)
		Update.update("name", "zyd");*/
		
		mgt.updateFirst(new Query(Criteria.where("myage").is(29)), update, getEntityClass());  
//		mgt.updateFirst(new Query().addCriteria(where(idName).is(idValue)), update, getEntityClass());
	}

}