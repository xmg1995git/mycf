package com.test.mycf.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * @author ASUS
 * @create 2020/7/7 - 10:17
 */
@Component
public class MD5Util {

    /**
     * 密码加密
     * @param o
     * @return
     */
    public String md5DigestAsHex(Object o){
        return DigestUtils.md5DigestAsHex(o.toString().getBytes());
    }
}
