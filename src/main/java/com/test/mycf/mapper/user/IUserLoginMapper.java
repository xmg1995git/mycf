package com.test.mycf.mapper.user;

import com.test.mycf.pojo.user.AuthUser;
import com.test.mycf.pojo.user.UserDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author ASUS
 * @create 2020/7/5 - 22:59
 */
public interface IUserLoginMapper {

    /**
     * 通过用户账号获取认证用户信息
     */
    AuthUser getAuthUserByAccount(String account);

    /**
     * 用户登录
     * @param user
     * @return
     */
    UserDo userLogin(UserDo user);

    /**
     * 用户注册
     * @param user
     * @return
     */
    Integer userRegister(UserDo user);

    @Update("UPDATE  cf_user_info SET landingTime=now() WHERE account=#{account}")
    Integer updateLandingTime(String account);
}
