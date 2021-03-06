package com.test.mycf.service.user.impl;

import com.test.mycf.common.RedisCommon;
import com.test.mycf.common.SessionCommon;
import com.test.mycf.pojo.user.AuthUser;
import com.test.mycf.service.user.IAuthUserService;
import com.test.mycf.service.user.IUserLoginService;
import com.test.mycf.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author ASUS
 * @create 2020/7/7 - 0:10
 */
@Component("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Resource
    private IAuthUserService authUserServiceImpl;
    @Resource
    private IUserLoginService userService;
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;
    @Resource
    private HttpSession httpSession;
    @Resource
    private ExecutorService executorService;
    @Resource
    private RedisUtil redisUtil;


    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        if(StringUtils.isEmpty(account)) {
            throw new UsernameNotFoundException("UserDetailsService没有接收到用户账号");
        } else {
            /**
             * 根据用户名查找用户信息
             */
            AuthUser authUser = (AuthUser)redisUtil.get(account);
            if (authUser == null){
                authUser = authUserServiceImpl.getAuthUserByAccount(account);
            }
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
            if(httpSession.getAttribute(SessionCommon.ACCOUNT) == null){
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        userService.updateLandingTime(account);
                    }
                });
                httpSession.setAttribute(SessionCommon.ACCOUNT,account);
                httpSession.setAttribute(SessionCommon.PHOTO,authUser.getUserDo().getPhoto());
                redisUtil.set(account, authUser, RedisCommon.SAVE_TIME);
            }
            return new User(authUser.getAccount(), authUser.getPassword(), grantedAuthorities);
        }

    }
}
