package com.test.mycf.controller.general;

import com.test.mycf.pojo.ResponseInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ASUS
 * @create 2020/7/7 - 22:56
 */
@RestController
@RequestMapping("/general")
public class HelloGeneralController {
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;

    @GetMapping
    public ResponseInfo hello(){
        return new ResponseInfo().success("Hello General!");
    }
}
