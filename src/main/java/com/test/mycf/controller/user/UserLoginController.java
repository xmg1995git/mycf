package com.test.mycf.controller.user;

import com.test.mycf.pojo.user.UserDo;
import com.test.mycf.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ASUS
 * @create 2020/7/6 - 0:07
 */
@RestController
public class UserLoginController {

    @Autowired
    private IUserService userService;

    @GetMapping("/1/{name}")
    public String hello(@PathVariable String name){
        return "hello, welcome to here!"+name;
    }



    @GetMapping("/{account}/{password}")
    public UserDo userLogin(@ModelAttribute UserDo user){
//        System.out.println("hi,"+user.getUsername());
        System.out.println(user.getAccount());
        System.out.println("int0 ...");
        UserDo userDo = new UserDo();
        userDo.setAccount("aaa");
        UserDo b = userService.userLogin(userDo);
        System.out.println(b);
        return b;
    }
}
