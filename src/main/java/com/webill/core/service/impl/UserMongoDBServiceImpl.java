package com.webill.core.service.impl;

import org.springframework.stereotype.Repository;

import com.webill.core.model.UserInfo;
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

}