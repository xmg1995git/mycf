package com.test.mycf.controller.supper;

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
 * @create 2020/7/7 - 23:01
 */
@RestController
@RequestMapping("/super")
public class HelloSuperController {
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;
    @Resource
    private HttpSession httpSession;

    @GetMapping
    public ResponseInfo hello(){
        return new ResponseInfo().success("Hello "+httpSession.getAttribute(SessionCommon.ACCOUNT)+"!");
    }
}
