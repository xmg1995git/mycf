package com.test.mycf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author ASUS
 * @create 2020/7/6 - 23:57
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsServiceImpl;

    /**
     * 用户认证配置
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * 指定用户认证时，默认从哪里获取认证用户信息
         */
        auth.userDetailsService(userDetailsServiceImpl);
    }

    /**
     * Http安全配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 表单登录：使用默认的表单登录页面和登录端点/login进行登录
         * 退出登录：使用默认的退出登录端点/logout退出登录
         * 记住我：使用默认的“记住我”功能，把记住用户已登录的Token保存在内存里，记住30分钟
         * 权限：除了/toHome和/toUser之外的其它请求都要求用户已登录
         * 注意：Controller中也对URL配置了权限，如果WebSecurityConfig中和Controller中都对某文化URL配置了权限，则取较小的权限
         */
        http
            .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                .failureUrl("/fail")
                .defaultSuccessUrl("/index", true)
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .rememberMe()
                .tokenValiditySeconds(1800)
                .and()
            .authorizeRequests()
                .antMatchers("/toHome", "/", "/webjars/echarts/4.7.0/echarts.min.js")
                .permitAll()
                .antMatchers("/general/**").hasAnyRole("GENERAL","ADMIN","SUPER")
                .antMatchers("/admin/**").hasAnyRole("ADMIN","SUPER")
                .antMatchers("/super/**").hasRole("SUPER")
                .anyRequest()
                .authenticated()
                .and()
            .csrf().disable(); //取消csrf防护
    }

    /**
     * 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        /**
         * BCryptPasswordEncoder：相同的密码明文每次生成的密文都不同，安全性更高
         */
        return new MD5PasswordEncoderConfig();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/img/**")
            .antMatchers("/webjars/**");

    }




}
