package com.test.mycf.pojo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ASUS
 * @create 2020/7/7 - 0:24
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthUser implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 用户账号 */
    private String account;
    /** 账号密码 */
    private String password;
    /** 角色集合 */
    private List<String> roles;

    private String role;

    private UserDo userDo;



}
