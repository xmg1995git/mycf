package com.test.mycf.service.user;

import com.test.mycf.pojo.user.UserDo;

/**
 * @author ASUS
 * @create 2020/7/3 - 20:05
 */
public interface IUserService {

    /**
     * 用户登录
     * @param user
     * @return
     */
    UserDo userLogin(UserDo user);

    /**
     * 保存登录时间
     * @param account
     * @return
     */
    Integer updateLandingTime(String account);
}
