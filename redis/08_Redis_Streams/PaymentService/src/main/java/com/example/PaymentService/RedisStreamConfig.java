package com.example.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;

@Configuration
public class RedisStreamConfig {

    @Autowired
    private OrderEventStreamListener orderEventStreamListener;

    /**
     * order-events 스트림 구독 준비
     * payment-service-gorup 컨슈머 그룹이 생성되어있지 않으면 application 시작시 오류날 수도 있음
     * 컨슈머 그룹을 미리 생성해주자
     * XGROUP CREATE order-events payment-service-group $ MKSTREAM
     * (이후로 생기는걸 읽겠다 $)
     * - MKSTREAM : 스트림이 없다면 스트림을 생성하겠다는 의미
     * @param factory
     * @return
     */
    @Bean
    public Subscription subscription(RedisConnectionFactory factory) {
        // ListenerOption 객체 생성
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .build();

        // ListenerContainer 생성
        StreamMessageListenerContainer listenerContainer = StreamMessageListenerContainer.create(factory, options);

        // receiveAutoAck() : 메시지를 받아갈때 처리한것으로 표시하겠다는 의미
        // 하지않으면 같은 컨슈머 그룹에 다른 소비자에게 갈수도 있음
        // payment-service-group 컨슈머의 이름 instance-1
        // order-event 를 읽어옴
        // 아까 생성한 OrderEventStreamListener를 매개변수에 포함
        Subscription subscription = listenerContainer.receiveAutoAck(Consumer.from("payment-service-group", "instance-1"),
                StreamOffset.create("sorder-event", ReadOffset.lastConsumed()), orderEventStreamListener);

        // start()
        listenerContainer.start();
        return subscription;
    }
}
