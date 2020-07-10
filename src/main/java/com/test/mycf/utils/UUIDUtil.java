package com.test.mycf.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author ASUS
 * @create 2020/7/10 - 14:19
 */
@Component
public class UUIDUtil {

    public String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0,16);
    }

}
