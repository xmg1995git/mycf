package com.test.mycf.controller.admin;

import com.test.mycf.common.SessionCommon;
import com.test.mycf.pojo.ResponseInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author ASUS
 * @create 2020/7/7 - 22:52
 */
@RestController
@RequestMapping("/admin")
public class HelloAdminController {

    @Resource
    private HttpSession httpSession;

    @Resource
    private RedisTemplate<Object,Object> redisTemplate;

    @GetMapping
    public ResponseInfo hello(){

        return new ResponseInfo().success("Hello, "+httpSession.getAttribute(SessionCommon.ACCOUNT)+"!");
    }
}
