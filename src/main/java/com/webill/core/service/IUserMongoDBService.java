package com.webill.core.service;

import com.webill.core.model.UserInfo;

/**
 * 子类Dao接口
 * <p>
 * ClassName: IUserDao
 * </p>
 * <p>
 * Description:当基本接口方法不再满足需求时可在子接口中定义
 * </p>
 * <p>
 * Copyright: (c)2017 Jastar·Wang,All rights reserved.
 * </p>
 * 
 * @author Jastar·Wang
 * @date 2017年4月12日
 */
public interface IUserMongoDBService extends IBaseMongoDBService<UserInfo> {

	void updateField(UserInfo entity);

}