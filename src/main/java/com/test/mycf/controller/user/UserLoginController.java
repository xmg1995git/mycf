package com.test.mycf.controller.user;

import com.test.mycf.common.RedisCommon;
import com.test.mycf.common.SessionCommon;
import com.test.mycf.common.UploadCommon;
import com.test.mycf.exception.MyException;
import com.test.mycf.exception.ResultEnum;
import com.test.mycf.pojo.ResponseInfo;
import com.test.mycf.pojo.role.RoleDo;
import com.test.mycf.pojo.user.UserDo;
import com.test.mycf.service.role.IRoleService;
import com.test.mycf.service.user.IUserLoginService;
import com.test.mycf.utils.MD5Util;
import com.test.mycf.utils.UUIDUtil;
import com.test.mycf.utils.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
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
    private IUserLoginService userService;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Resource
    private IRoleService roleService;
    @Resource
    private ExecutorService executorService;
    @Resource
    private MD5Util md5Util;
    @Resource
    private UUIDUtil uuidUtil;
    @Resource
    private UploadUtil uploadUtil;

    @GetMapping("/")
    public String hello(){
        return "hello";
    }


    /**
     * 注册
     * @return
     */
    @PostMapping("/register")
    public ResponseInfo register(@RequestParam("file") MultipartFile file, UserDo user,
                                  HttpServletRequest request) {
        user.setAccount(user.getAccount().trim());
        try {
            String filePath = uploadUtil.upload(file, user.getAccount());
            user.setPhoto(filePath);
        }catch (MyException e){
            return new ResponseInfo().warning(e.getMessage());
        }
        user.setPassword(md5Util.md5DigestAsHex(user.getPassword()));
        user.setId(uuidUtil.getUUID());
        user.setCreateTime(new Date());
        Integer count = null;
        try {
           count = userService.userRegister(user);
        }catch (Exception e) {
            if (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
                return new ResponseInfo().success(user.getAccount() + ResultEnum.ACCOUNT_IS_EXIST.getMessage());
            }
        }
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    RoleDo roleDo = new RoleDo(user.getAccount(), user.getAccount(), new Date());
                    roleService.saveDefaultUserRole(roleDo);
                }catch (Exception e){
                    if(e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException){
                        throw new MyException(ResultEnum.ACCOUNT_IS_EXIST);
                    }
                }
            }
        });
        if(count == 1){ // success
            return new ResponseInfo().success(ResultEnum.REGISTER_SUCCESS.getMessage());
        }else{
            return new ResponseInfo().warning(ResultEnum.NET_IS_WARNING.getMessage());
        }

    }

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
        password = md5Util.md5DigestAsHex(password);
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
