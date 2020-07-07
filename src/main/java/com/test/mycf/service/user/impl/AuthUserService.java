package com.test.mycf.service.user.impl;

import com.test.mycf.mapper.user.IUserLoginMapper;
import com.test.mycf.pojo.user.AuthUser;
import com.test.mycf.service.user.IAuthUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ASUS
 * @create 2020/7/7 - 0:29
 */
@Service
public class AuthUserService implements IAuthUserService {

    @Resource
    private IUserLoginMapper iUserLoginMapper;

    @Override
    public AuthUser getAuthUserByAccount(String account) {
        AuthUser authUser = iUserLoginMapper.getAuthUserByAccount(account);
        if(authUser == null){
            return  null;
        }
        List<String> roles = Arrays.asList(authUser.getRole().split("-"));
        authUser.setRoles(roles);
        return authUser;
    }

}
