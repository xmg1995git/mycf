package com.test.mycf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.test.mycf.mapper")
public class MycfApplication {

    public static void main(String[] args) {
        SpringApplication.run(MycfApplication.class, args);
    }

}
