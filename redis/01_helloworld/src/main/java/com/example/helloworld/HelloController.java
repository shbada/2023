package com.example.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    /**
     * RedisTemplate이라는 Redis 조작의 추상 레이어를 제공함
     */
    @Autowired
    StringRedisTemplate redisTemplate;

    // /hello => "hello world!"

    @GetMapping("/hello")
    public String hello() {
        return "hello world!";
    }

    // /setFruit?name=banana
    // /getFruit

    @GetMapping("/setFruit")
    public String setFruit(@RequestParam String name) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("fruit", name);

        return "saved.";
    }

    @GetMapping("/getFruit")
    public String getFruit() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String fruitName = ops.get("fruit");

        return fruitName;
    }
}
