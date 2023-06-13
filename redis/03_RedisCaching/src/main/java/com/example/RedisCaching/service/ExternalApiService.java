package com.example.RedisCaching.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ExternalApiService {

    public String getUserName(String userId) {
        // 외부 서비스나 DB 호출
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }

        System.out.println("Getting user name from other service..");

        if(userId.equals("A")) {
            return "Adam";
        }
        if(userId.equals("B")) {
            return "Bob";
        }

        return "";
    }

    /**
     * Spring 캐싱 처리 - Redis 사용
     * (application.yml 아래 설정 추가로)
         spring:
         cache:
         type: redis # redis 사용을 알림
     * @param userId
     * @return
     */
    @Cacheable(cacheNames = "userAgeCache", key = "#userId")
    public int getUserAge(String userId) {
        try {
            Thread.sleep(500);
        } catch(InterruptedException ignored) {
        }

        // 캐싱 이후로는 안찍히겠지
        System.out.println("Getting user age from other service..");

        if (userId.equals("A")) {
            return 28;
        }

        if (userId.equals("B")) {
            return 32;
        }

        return 0;
    }
}
