package com.test.mycf.mapper.user;

import com.test.mycf.pojo.user.UserDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author ASUS
 * @create 2020/7/5 - 22:59
 */
public interface IUserLoginMapper {



    /**
     * 用户登录
     * @param user
     * @return
     */
    @Select("SELECT username,password,nickname,type,landingTime FROM cf_user_info WHERE account = #{account}")
    UserDo userLogin(UserDo user);
}
