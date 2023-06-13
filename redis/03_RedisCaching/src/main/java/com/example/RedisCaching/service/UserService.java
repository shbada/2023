package com.example.RedisCaching.service;

import com.example.RedisCaching.dto.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private ExternalApiService externalApiService;

    @Autowired
    StringRedisTemplate redisTemplate;

    public UserProfile getUserProfile(String userId) {

        String userName = null;

        /**
         * Redis 캐싱
         */
        // ValueOperations 객체 얻기
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        // key 값으로 value 얻기
        String cachedName = ops.get("nameKey:" + userId);

        if (cachedName != null) {
            userName = cachedName;
        } else {
            // 만약 Object라면 어떤 형식으로 넣을지 생각해줘야한다. (json 등)
            // Spring Cache를 쓸때가 좀더 나은 방식일 수 있음
            userName = externalApiService.getUserName(userId);
            ops.set("nameKey:" + userId, userName, 5, TimeUnit.SECONDS);
        }

        /**
         * Spring 캐싱
         */
        //String userName = externalApiService.getUserName(userId);
        int userAge = externalApiService.getUserAge(userId);

        return new UserProfile(userName, userAge);
    }
}
