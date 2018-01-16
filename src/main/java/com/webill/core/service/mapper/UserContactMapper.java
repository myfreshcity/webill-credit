package com.webill.core.service.mapper;

import java.util.List;
import java.util.Map;

import com.webill.core.model.UserContact;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * UserContact 表数据库控制层接口
 *
 */
public interface UserContactMapper extends AutoMapper<UserContact> {

	/**   
	 * @Title: checkMobileIsExist   
	 * @Description: 判断手机号是否重复  
	 * @author: WangLongFei  
	 * @date: 2017年6月28日 下午3:37:39   
	 * @param mobile
	 * @return  
	 * @return: int  
	 */
	int checkMobileIsExist(String mobile);
	
	/**   
	 * @Title: getUserContact   
	 * @Description: 获取联系人列表  
	 * @author: WangLongFei  
	 * @date: 2017年6月29日 上午9:36:37   
	 * @param map
	 * @return  
	 * @return: UserContact  
	 */
	List<UserContact> getUserContact(Map<String,Object> map);
}