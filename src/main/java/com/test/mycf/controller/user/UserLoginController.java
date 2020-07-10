package com.test.mycf.controller.user;

import com.test.mycf.common.RedisCommon;
import com.test.mycf.common.SessionCommon;
import com.test.mycf.common.UploadCommon;
import com.test.mycf.pojo.ResponseInfo;
import com.test.mycf.pojo.user.UserDo;
import com.test.mycf.service.user.IUserLoginService;
import com.test.mycf.utils.MD5Util;
import com.test.mycf.utils.UUIDUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
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
    private MD5Util md5Util;
    @Resource
    private UUIDUtil uuidUtil;

    @GetMapping("/")
    public String hello(){
        return "hello";
    }


    /**
     * 注册
     * @return
     */
    @PostMapping("/register")
    public Integer us(@RequestParam("file") MultipartFile file, UserDo user, HttpServletRequest request) throws IOException {
        // 判断文件是否为空，空则返回失败页面
        if (file.isEmpty()) {
            user.setPhoto("");
        }else{
            // 获取原文件名
            String fileName = file.getOriginalFilename();
            String[] split = fileName.split("\\.");
            fileName = new StringBuilder()
                    .append(user.getAccount())
                    .append(System.currentTimeMillis() % 100)
                    .append(".")
                    .append(split[split.length - 1]).toString();
            // 创建文件实例
            File filePath = new File(UploadCommon.PATH+fileName);
            // 写入文件
            file.transferTo(filePath);
            user.setPhoto(fileName);
        }
        user.setPassword(md5Util.md5DigestAsHex(user.getPassword()));
        user.setId(uuidUtil.getUUID());
        user.setCreateTime(new Date());
        System.out.println(user);
        return userService.userRegister(user);
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
