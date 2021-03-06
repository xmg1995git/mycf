package com.test.mycf.service.user.impl;

import com.test.mycf.mapper.user.IUserLoginMapper;
import com.test.mycf.pojo.user.UserDo;
import com.test.mycf.service.user.IUserLoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ASUS
 * @create 2020/7/3 - 20:05
 */
@Service
public class UserImpl implements IUserLoginService {

    @Resource
    private IUserLoginMapper iUserLoginMapper;

    @Override
    public UserDo userLogin(UserDo user) {
        return iUserLoginMapper.userLogin(user);
    }

    @Override
    public Integer userRegister(UserDo user) {
        return iUserLoginMapper.userRegister(user);
    }

    @Override
    public Integer updateLandingTime(String account) {
        return iUserLoginMapper.updateLandingTime(account);
    }
}
