package com.test.mycf;

import com.test.mycf.mapper.user.IUserLoginMapper;
import com.test.mycf.pojo.user.UserDo;
import com.test.mycf.service.user.impl.UserImpl;
import com.test.mycf.utils.UUIDUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.*;

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

    @Resource
    private IUserLoginMapper iUserLoginMapper;

    @Resource
    private UUIDUtil uuidUtil;


    @Test
    public void testUUID() {
        for (int i = 0; i < 8; i++) {
            System.out.println(uuidUtil.getUUID());
        }
    }
    @Test
    public void testMybatis() {
        System.out.println(iUserLoginMapper.getAuthUserByAccount("admin"));
        UserDo u = new UserDo();
        u.setAccount("admin");
        System.out.println(iUserLoginMapper.userLogin(u));
    }

    @Test
    public void testexecutorService() throws ExecutionException, InterruptedException {
        Future<Object> submit1 = executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return "hello mycf!";
            }
        });
        System.out.println(submit1.get());
        Future<Integer> submit = executorService.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(2000);
                    System.out.println(i);
                }
            }
        }, 100);
        for (int i = 0; i < 9; i++) {
            System.out.println(Thread.currentThread().getName());
        }
        System.out.println(submit.get());

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
