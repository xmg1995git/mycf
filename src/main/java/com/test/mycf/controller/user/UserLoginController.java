package com.test.mycf.controller.user;

import com.test.mycf.common.RedisCommon;
import com.test.mycf.pojo.ResponseInfo;
import com.test.mycf.pojo.user.UserDo;
import com.test.mycf.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCommand;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author ASUS
 * @create 2020/7/6 - 0:07
 */
@RestController
@RequestMapping("/user")
public class UserLoginController {

    @Autowired
    private IUserService userService;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @GetMapping("/n")
    public UserDo aa(){
        return null;
    }


    @GetMapping("/{account}/{password}")
    public ResponseInfo userLogin(@ModelAttribute UserDo user){
        String account, password;
        account = user.getAccount();
        password = user.getPassword();
        UserDo userDo = (UserDo)redisTemplate.opsForValue().get(account); //首先查看缓存
        if(userDo == null){ //redis缓存中没有
            System.out.println("数据库 查看...");
            userDo = userService.userLogin(user);
        }
        if(userDo == null){ //数据库中也不存在
            return null;
        }
        if(!userDo.getPassword().equalsIgnoreCase(password)){ //密码匹配失败
            return null;
        }
        redisTemplate.opsForValue().set(account,userDo, RedisCommon.Save_Time, TimeUnit.DAYS);
        return new ResponseInfo().success(userDo);
    }
}
