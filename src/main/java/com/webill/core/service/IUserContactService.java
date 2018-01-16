package com.webill.core.service;

import java.util.List;
import java.util.Map;

import com.webill.core.model.UserContact;
import com.webill.framework.service.ISuperService;

/**
 *
 * UserContact 数据服务层接口
 *
 */
public interface IUserContactService extends ISuperService<UserContact> {

	/**   
	 * @Title: checkMobileIsExist   
	 * @Description: 验证手机号是否存在  
	 * @author: WangLongFei  
	 * @date: 2017年6月22日 下午3:48:38   
	 * @param mobile
	 * @return  
	 * @return: boolean  true 手机号存在
	 * 					 false 手机号不存在
	 */
	boolean checkMobileIsExist(String mobile);
	
	/**   
	 * @Title: saveContact   
	 * @Description: 保存联系人，更新违章的联系人contactId   
	 * @author: WangLongFei  
	 * @date: 2017年6月29日 下午3:39:54   
	 * @param uc
	 * @return  
	 * @return: UserContact  
	 */
	Object saveContact(UserContact uc);
	/**   
	 * @Title: saveContact   
	 * @Description: 保存联系人手机号，旧数据复制到新记录，作废旧数据  
	 * @author: WangLongFei  
	 * @date: 2017年6月22日 下午6:23:10   
	 * @return  
	 * @return: boolean  
	 */
	UserContact saveMobile(UserContact uc);
	
	/**   
	 * @Title: setOrCancleDefault   
	 * @Description: 默认联系人切换  
	 * @author: WangLongFei  
	 * @date: 2017年6月23日 上午11:19:41   
	 * @param id
	 * @return  
	 * @return: boolean
	 */
	boolean updateDefault(String id);
	/**   
	 * @Title: setOrCancleDefault   
	 * @Description: 默认联系人切换  
	 * @author: WangLongFei  
	 * @date: 2017年6月23日 上午11:19:41   
	 * @param id
	 * @return  
	 * @return: boolean
	 */
	boolean updateNoDefault(String id);
	
	/**   
	 * @Title: delUserContact   
	 * @Description: 删除联系人（逻辑删除）  
	 * @author: WangLongFei  
	 * @date: 2017年6月28日 下午4:54:57   
	 * @param id
	 * @return  
	 * @return: boolean  
	 */
	boolean delUserContact(String id);
	
	/**   
	 * @Title: updateContact   
	 * @Description: 编辑联系人信息  
	 * @author: WangLongFei  
	 * @date: 2017年6月28日 下午7:14:19   
	 * @param jsonstr
	 * @return  
	 * @return: boolean  
	 */
	UserContact updateUserContact(UserContact entity);
	
	/**   
	 * @Title: getUserContact   
	 * @Description: 获取联系人列表  
	 * @author: WangLongFei  
	 * @date: 2017年6月29日 上午9:38:48   
	 * @param map
	 * @return  
	 * @return: List<UserContact>  
	 */
	List<UserContact> getUserContact(Map<String,Object> map);
	
}