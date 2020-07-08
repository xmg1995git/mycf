package com.test.mycf;

import com.test.mycf.pojo.user.UserDo;
import com.test.mycf.service.user.IUserService;
import com.test.mycf.service.user.impl.UserImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

@SpringBootTest
@RunWith(SpringRunner.class)
class MycfApplicationTests {

    @Autowired
    DataSource data;

    @Resource
    UserImpl iUserService;

    @Resource
    RedisTemplate<Object, Object> redisTemplate;

    @Resource
    Executor taskExecutor;

    @Resource
    ExecutorService executorService;

    @Test
    public void testexecutorService(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"executorService");
            }
        });

    }

    @Test
    public void testTaskExecutor(){
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });

    }



    @Test
    public void testConnection() throws SQLException {
        System.out.println(data.getClass());
        System.out.println(data.getConnection());
    }

    @Test
    public void testlogin() {
        UserDo userDo = new UserDo();
        userDo.setAccount("aaa");
        UserDo b = iUserService.userLogin(userDo);
        System.out.println(b);
    }

    @Test
    public void testRedis(){
//        System.out.println(redisTemplate.getClass());
        redisTemplate.opsForValue().set("e","hello redis!");
        Object oo = redisTemplate.opsForValue().get("d");
//        System.out.println("----->"+o);
        System.out.println("----->"+oo);
    }

}
