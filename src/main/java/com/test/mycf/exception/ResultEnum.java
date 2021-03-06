package com.test.mycf.exception;

import lombok.Getter;
import lombok.ToString;

/**
 * @author ASUS
 * @create 2020/7/11 - 10:28
 */
@ToString
@Getter
public enum  ResultEnum {

    /**
     * 用户名密码必填！
     */
    USER_PASSWORD_WARN(10004,"用户名密码必填！！！"),

    /**
     * 注册成功！
     */
    REGISTER_SUCCESS(10003,"注册成功！"),

    /**
     * 请选择上传文件！
     */
    UPLOAD_FILE_NULL(10002,"请选择上传文件！"),
    /**
     * 账号已存在！
     */
    ACCOUNT_IS_EXIST(10001,"账号已存在！"),
    /**
     * 网络故障，请稍后...！
     */
    NET_IS_WARNING(000000,"网络故障，请稍后...！");




    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
