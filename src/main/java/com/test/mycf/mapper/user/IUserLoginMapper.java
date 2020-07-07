package com.test.mycf.mapper.user;

import com.test.mycf.pojo.user.AuthUser;
import com.test.mycf.pojo.user.UserDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author ASUS
 * @create 2020/7/5 - 22:59
 */
public interface IUserLoginMapper {


    /**
     * 通过用户账号获取认证用户信息
     */
    @Select("SELECT u.id, u.account, password, photo, role, nickname, landingTime " +
            "FROM " +
            "cf_user_info u JOIN cf_user_role r ON u.account = r.account " +
            "WHERE " +
            "r.STATUS = '1' AND u.STATUS = '1' AND u.account = #{account}"
            )
    AuthUser getAuthUserByAccount(String account);

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Select("SELECT username,password,nickname,photo,landingTime FROM cf_user_info WHERE account = #{account}")
    UserDo userLogin(UserDo user);
}
