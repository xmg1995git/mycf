package com.test.mycf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author ASUS
 * @create 2020/7/8 - 13:11
 */
@Configuration
public class ThreadPoolConfig {

    @Bean("executorService")
    public ExecutorService getThreadPool(){
        return new ThreadPoolExecutor(
                2 ,
                4,
                1L,
                 TimeUnit.MINUTES,
                 new LinkedBlockingQueue<Runnable>()
                );
    }
}
