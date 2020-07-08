package com.test.mycf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author ASUS
 * @create 2020/7/8 - 12:52
 */
@Configuration
@EnableAsync
public class TaskPoolConfig {

//    @Bean("taskExecutor")
//    public Executor taskExecutro(){
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(10);
//        taskExecutor.setMaxPoolSize(50);
//        taskExecutor.setQueueCapacity(200);
//        taskExecutor.setKeepAliveSeconds(60);
//        taskExecutor.setThreadNamePrefix("taskExecutor--");
//        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
//        taskExecutor.setAwaitTerminationSeconds(60);
//        return taskExecutor;
//    }
}
