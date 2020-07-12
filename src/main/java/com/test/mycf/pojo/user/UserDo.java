package com.test.mycf.pojo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ASUS
 * @create 2020/7/3 - 20:11
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserDo implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键
    private String id;

    // 姓名
    private String username;

    // 账号
    @NotEmpty (message = "账号不能为空")
    private String account;

    // 昵称
    private String nickname;

    // 性别
    private String gender;

    // 手机号
    private String phone;

    // 密码
    @NotEmpty(message = "密码不能为空")
    private String password;

    // 头像
    private String photo;

    // 状态 0:无效
    private String status;

    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    // 修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    // 登录时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date landingTime;




}
