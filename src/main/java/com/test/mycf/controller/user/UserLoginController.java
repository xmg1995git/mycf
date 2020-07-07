package com.test.mycf.controller.user;

import com.test.mycf.common.RedisCommon;
import com.test.mycf.pojo.ResponseInfo;
import com.test.mycf.pojo.user.UserDo;
import com.test.mycf.service.user.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.concurrent.TimeUnit;

/**
 * @author ASUS
 * @create 2020/7/6 - 0:07
 *
 * 登录、注册接口
 */
@RestController
@RequestMapping("/user")
public class UserLoginController {
    private static final Logger LOG = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;


    /**
     * 登录
     * @param user
     * @return
     */
    @GetMapping("/{account}/{password}")
    public ResponseInfo userLogin(@ModelAttribute UserDo user){
        String account, password;
        account = user.getAccount();
        password = user.getPassword();
        UserDo userDo = (UserDo)redisTemplate.opsForValue().get(account);
        if(userDo == null){
            userDo = userService.userLogin(user);
        }
        if(userDo == null){ //数据库中不存在
            return new ResponseInfo().warning("用户名不存在!");
        }
        if(!userDo.getPassword().equalsIgnoreCase(password)){ //密码匹配失败
            return new ResponseInfo().warning("密码匹配失败!");
        }
        redisTemplate.opsForValue().set(account,userDo, RedisCommon.SAVE_TIME, TimeUnit.DAYS);
        return new ResponseInfo().success(userDo);
    }

    /**
     * 获取登录后的Principal（需要登录）
     */
    @GetMapping("/getPrincipal")
    public Object getPrincipal(@AuthenticationPrincipal Principal principal){
        return principal;
    }

    /**
     * 获取登录后的UserDetails（需要登录）
     */
    @GetMapping("/getUserDetails")
    public Object getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }


}
