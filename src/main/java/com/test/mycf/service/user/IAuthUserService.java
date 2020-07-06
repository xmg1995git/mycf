package com.test.mycf.service.user;

import com.test.mycf.pojo.user.AuthUser;

/**
 * @author ASUS
 * @create 2020/7/7 - 0:23
 */
public interface IAuthUserService {

    /**
     * 通过用户账号获取认证用户信息
     */
    AuthUser getAuthUserByAccount(String account);
}
