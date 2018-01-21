package com.webill.core.service.impl;

import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.webill.app.util.EmptyUtil;
import com.webill.app.util.StringUtil;
import com.webill.core.model.UserInfo;
import com.webill.core.service.IUserMongoDBService;
import static org.springframework.data.mongodb.core.query.Criteria.where;

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