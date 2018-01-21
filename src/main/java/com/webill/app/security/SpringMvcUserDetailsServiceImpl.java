package com.webill.app.security;

import com.webill.core.model.User;
import com.webill.core.service.IUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

public class SpringMvcUserDetailsServiceImpl implements UserDetailsService {


    private static Logger logger = LoggerFactory.getLogger(SpringMvcUserDetailsServiceImpl.class);
    @Autowired
    IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        // 读取用户
        User u = new User();
//        u.setUsername(uid);
        User userEntity = userService.selectOne(u);
        // 读取权限
        Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

        // 这里需要从数据库里读取所有的权限点
        Map urMap = new HashMap<>();
        urMap.put("user_id",uid);
        // 根据用户读到所有的角色
        // List<Integer> roles = userRoleService.selectByMap(urMap).stream().map(b -> b.getRoleId()).collect(Collectors.toList());
        // 根据角色读取所有的resource_id
        // resourceRoleService.selectBatchIds()


        // for (Integer ae : aes) {
        //    auths.add(new SimpleGrantedAuthority("ROLE_"+ae.toString()));
        // }

        auths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(userEntity.getId().toString(), userEntity.getPassword(), true, true, true, true, auths);
    }

}
