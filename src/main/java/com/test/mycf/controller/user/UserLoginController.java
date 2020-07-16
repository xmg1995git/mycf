package com.test.mycf.controller.user;

import com.test.mycf.common.ConstantCommon;
import com.test.mycf.common.RedisCommon;
import com.test.mycf.common.SessionCommon;
import com.test.mycf.common.UploadCommon;
import com.test.mycf.exception.MyException;
import com.test.mycf.exception.ResultEnum;
import com.test.mycf.pojo.ResponseInfo;
import com.test.mycf.pojo.role.RoleDo;
import com.test.mycf.pojo.user.AuthUser;
import com.test.mycf.pojo.user.UserDo;
import com.test.mycf.service.role.IRoleService;
import com.test.mycf.service.user.IUserLoginService;
import com.test.mycf.utils.MD5Util;
import com.test.mycf.utils.RedisUtil;
import com.test.mycf.utils.UUIDUtil;
import com.test.mycf.utils.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.*;

import static com.test.mycf.common.ConstantCommon.B_1;

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
    @Resource
    private RedisUtil redisUtil;



    /**
     * 注册
     * @return
     */
    @PostMapping("/register")
    public ResponseInfo register(@RequestParam("file") MultipartFile file, @Valid UserDo user, BindingResult result) {
        String photo = "";
        try {
            if(result.hasErrors()){
                return new ResponseInfo().warning(ResultEnum.USER_PASSWORD_WARN.getMessage());
            }
            user.setAccount(user.getAccount().trim());
            photo = uploadUtil.upload(file, user.getAccount());
            user.setPhoto(photo);
            user.setPassword(md5Util.md5DigestAsHex(user.getPassword()));
            user.setId(uuidUtil.getUUID());
            user.setCreateTime(new Date());
            Integer count = userService.userRegister(user);
            if (count == B_1) { // success
                saveUserRole(user);
                return new ResponseInfo().success(ResultEnum.REGISTER_SUCCESS.getMessage());
            }else {
                // 删除图片
                delUserPhoto(photo);
            }
        } catch (Exception e) {
            if(ResultEnum.UPLOAD_FILE_NULL.getMessage().equals(e.getMessage())){
                return new ResponseInfo().warning(ResultEnum.UPLOAD_FILE_NULL.getMessage());
            }
            if (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
                return new ResponseInfo().success(user.getAccount() + ResultEnum.ACCOUNT_IS_EXIST.getMessage());
            }
        }
        return new ResponseInfo().warning(ResultEnum.NET_IS_WARNING.getMessage());
    }

    /**
     * 删除文件
     * @param name
     */
    private void delUserPhoto(String name){
        File file = new File(name);
        if(file.exists()){
            file.delete();
        }
        LOG.info(name + "--成功删除！");
    }


    /**
     * 保存用户角色
     * @param user
     */
    private void saveUserRole(UserDo user){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    RoleDo roleDo = new RoleDo(user.getAccount(), user.getAccount(), new Date());
                    roleService.saveDefaultUserRole(roleDo);
                } catch (Exception e) {
                    if (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
                        throw new MyException(ResultEnum.ACCOUNT_IS_EXIST);
                    }
                }
            }
        });
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
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        authorities.forEach(item ->{
            System.out.println(item.getAuthority());
        });
        System.out.println(B_1 == 1);
        int a = 1;
        System.out.println(a == 1);
        return userDetails;
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/{account}/{password}")
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

    @GetMapping("/")
    public String hello(){
        return "hello";
    }

    @GetMapping("/home")
    public ResponseInfo home(HttpSession session) {
        AuthUser authUser = (AuthUser) redisUtil.get((String) session.getAttribute(SessionCommon.ACCOUNT));
        String[] split = authUser.getRole().split(ConstantCommon.ROLE_JOINT);
        String role = split[split.length - B_1];
        return new ResponseInfo().success(role);
    }



}
