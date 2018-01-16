package com.webill.core.service.mapper;

import java.util.List;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.webill.core.model.User;
import com.webill.framework.service.mapper.AutoMapper;

/**
 *
 * User 表数据库控制层接口
 *
 */
public interface UserMapper extends AutoMapper<User> {

    /**   
     * @Title: getList   
     * @Description: 用户列表   
     * @author: WangLongFei  
     * @date: 2017年8月28日 上午9:50:02   
     * @param user
     * @return  
     * @return: List<User>  
     */
    public List<User> getList(Pagination page,User user);
}