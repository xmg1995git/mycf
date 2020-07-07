package com.test.mycf.utils;

import org.springframework.util.DigestUtils;

/**
 * @author ASUS
 * @create 2020/7/7 - 10:17
 */
public class MD5Util {

    /**
     * 密码加密
     * @param o
     * @return
     */
    public static String md5DigestAsHex(Object o){
        return DigestUtils.md5DigestAsHex(o.toString().getBytes());
    }
}
