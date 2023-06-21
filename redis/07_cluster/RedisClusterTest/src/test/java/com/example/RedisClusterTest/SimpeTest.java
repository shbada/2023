package com.example.RedisClusterTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SimpleTest {

    @Autowired
    RedisTemplate redisTemplate;

    String dummyValue = "banana";

    /**
     * key에 따라 노드가 다르게 할당되므로 7002 포트에 접속했을때 있는 값도 있을거고, 없는 값도 있을거임
     *
     * master
     * 7002
     * 7000
     * 7001
     *
     * slave
     * 7003
     * 7005
     *
     * 7002 죽여보자.
     * 7002 : master,fail
     * master
     * 7000
     * 7004 (승격)
     * 7001
     *
     * slave
     * 7003
     * 7005
     */
    @Test
    void setValues() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        for(int i = 0; i < 1000; i++) {
            String key = String.format("name:%d", i);   // name:1
            ops.set(key, dummyValue);
        }
    }

    @Test
    void getValues() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        for(int i = 0; i < 1000; i++) {
            String key = String.format("name:%d", i);   // name:1
            String value = ops.get(key);

            assertEquals(value, dummyValue);
        }
    }
}
