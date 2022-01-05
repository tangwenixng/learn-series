package com.twx.learn.sentinel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author tangwx@soyuan.com.cn
 * @date 2020/6/17 12:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTest {
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void valOps() {
        String key = "service_test_limit_Count";
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        ValueOperations<String, String> valOps = redisTemplate.opsForValue();
        valOps.set(key,"500");
//        Long decrement = valOps.decrement(key);
//        System.out.println(decrement);
        System.out.println(valOps.get(key));
    }
}
