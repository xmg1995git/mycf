package com.test.mycf.pojo.user;

import lombok.Data;

import java.util.List;

/**
 * @author ASUS
 * @create 2020/7/7 - 0:24
 */
@Data
public class AuthUser {

    /** 用户ID */
    private Long id;
    /** 用户账号 */
    private String username;
    /** 账号密码 */
    private String password;
    /** 角色集合 */
    private List<String> roles;


}
