package com.example.PubSubChat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Redis 연결 팩토리를 생성하고 반환
        return new LettuceConnectionFactory();
    }

    /**
     * subscribe 기능을 하여 listen 한다.
     * 수신된 메시지를 처리할 수 있음
     * @return
     */
    @Bean
    RedisMessageListenerContainer redisContainer() {
        // RedisMessageListenerContainer 객체를 생성
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        return container;
    }
}
