package com.webill.app.security;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AccessDecisionManagerImpl implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) throws AccessDeniedException,
            InsufficientAuthenticationException {
        // TODO Auto-generated method stub
        if (null == attributes)
            return;
        for (ConfigAttribute attribute : attributes) {
            String needRole = ((SecurityConfig) attribute).getAttribute();
            // authority为用户所被赋予的权限, needRole 为访问相应的资源应该具有的权限。
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (needRole.trim().equals(grantedAuthority.getAuthority().trim()))
                    return;
            }
        }
        throw new AccessDeniedException("权限不足!");
        //return;
    }

    @Override
    public boolean supports(ConfigAttribute arg0) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        // TODO Auto-generated method stub
        return true;
    }

}
