package com.test.mycf.controller.home;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ASUS
 * @create 2020/7/7 - 11:46
 */
@Controller
public class HomeController {

    @GetMapping("/toHome")
    public String index(){
        return "index";
    }

    @GetMapping("/xxx")
    @PreAuthorize("hasRole('ROLE_GENERAL')")
    public String index1(){
        return "general";
    }

    @GetMapping("/yyy")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String index2(){
        return "admin";
    }

    @GetMapping("/zzz")
    @PreAuthorize("hasRole('ROLE_SUPER')")
    public String index3(){
        return "super";
    }
}
