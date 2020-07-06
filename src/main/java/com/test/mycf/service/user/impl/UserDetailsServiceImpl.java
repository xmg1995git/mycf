package com.test.mycf.service.user.impl;

import com.test.mycf.pojo.user.AuthUser;
import com.test.mycf.service.user.IAuthUserService;
import com.test.mycf.service.user.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ASUS
 * @create 2020/7/7 - 0:10
 */
@Component("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IAuthUserService authUserServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        if(StringUtils.isEmpty(account)) {
            throw new UsernameNotFoundException("UserDetailsService没有接收到用户账号");
        } else {
            /**
             * 根据用户名查找用户信息
             */
            AuthUser authUser = authUserServiceImpl.getAuthUserByAccount(account);
            if(authUser == null) {
                throw new UsernameNotFoundException(String.format("用户'%s'不存在", account));
            }
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (String role : authUser.getRoles()) {
                //封装用户信息和角色信息到SecurityContextHolder全局缓存中
                grantedAuthorities.add(new SimpleGrantedAuthority(role));
            }
            /**
             * 创建一个用于认证的用户对象并返回，包括：用户名，密码，角色
             */
            return new User(authUser.getUsername(), authUser.getPassword(), grantedAuthorities);
        }

    }
}
