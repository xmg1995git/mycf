package com.test.mycf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@MapperScan("com.test.mycf.mapper")
public class MycfApplication {

    public static void main(String[] args) {
        SpringApplication.run(MycfApplication.class, args);
    }

}
