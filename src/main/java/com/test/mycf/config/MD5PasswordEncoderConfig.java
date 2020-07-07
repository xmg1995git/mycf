package com.test.mycf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ASUS
 * @create 2020/7/7 - 7:47
 */
public class MD5PasswordEncoderConfig implements PasswordEncoder {



    /**
     * 对明文密码加密的方法
     * @param charSequence
     * @return
     */
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    /**
     * 匹配密码的方法，第一个参数是要匹配的密码，第二个参数是加密后的encode密文
     * @param charSequence
     * @param s
     * @return
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {

        return charSequence.equals(s);
    }
}
