package com.test.mycf.mapper.role;

import com.test.mycf.pojo.role.RoleDo;
import org.apache.ibatis.annotations.Insert;

/**
 * @author ASUS
 * @create 2020/7/10 - 23:19
 */
public interface IRoleMapper {

    @Insert("INSERT INTO cf_user_role (account,role,createAccount,status,createTime) VALUE (#{account},'ROLE_GENERAL',#{createAccount},'1',#{createTime})")
    Integer saveDefaultUserRole(RoleDo role);
}
