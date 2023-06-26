package com.example.RedisCaching.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;

@Configuration
public class RedisCacheConfig {
    /**
     * userAgeCache 캐시의 만료시간 지정하기
     * 빈으로 직접 구현해야한다.
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // Redis 캐시에 대한 기본 구성을 생성하는 메서드입니다.
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                // 이 메서드는 null 값을 캐시하지 않도록 설정
                .disableCachingNullValues()
                // 캐시 항목의 TTL(Time-To-Live)을 10초로 설정
                .entryTtl(Duration.ofSeconds(10))
                // 캐시 키의 접두사 계산을 위한 전략 (simple() : 클래스의 간단한 이름을 캐시 키의 접두사로 사용)
                .computePrefixWith(CacheKeyPrefix.simple())
                // 직렬화기(serializer)를 설정
                .serializeKeysWith(
                        // 캐시 키를 문자열로 변환하여 Redis에 저장
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                );

        // Redis 캐시 구성을 위한 구성 맵을 생성
        HashMap<String, RedisCacheConfiguration> configMap = new HashMap<>();

        // "userAgeCache"라는 특정 캐시에 대한 TTL(Time-To-Live)이 5초인 RedisCacheConfiguration을 생성하고, 구성 맵에 추가
        configMap.put("userAgeCache", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(5)));  // 특정 캐시에 대한 TTL

        return RedisCacheManager
                .RedisCacheManagerBuilder
                // connectionFactory를 사용하여 Redis 연결을 설정
                .fromConnectionFactory(connectionFactory)
                // 기본적으로 캐시에 적용되는 RedisCacheConfiguration을 설정
                .cacheDefaults(configuration) // configuration
                // 구성 맵을 사용하여 각 캐시에 대한 RedisCacheConfiguration을 지정
                .withInitialCacheConfigurations(configMap)
                .build();
    }
}
