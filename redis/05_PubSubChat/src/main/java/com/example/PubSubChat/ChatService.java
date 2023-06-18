package com.example.PubSubChat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * Client to ChatServer
 * --> Client to Redis(채널 사용) : publish/subscribe 구조를 이용해 쉽게 구현 가능
 *
 *
 * 1) redis client
 * publish chat hi
 *
 * 2) springboot server
 * onMessage() 가 호출되어 메시지가 출력된다.
 *
 * -----
 *
 * 1) springboot server
 * redisTemplate.convertAndSend(chatRoomName, line); 로 메시지 전송
 *
 * 2) redis client
 * subscribe chat1
 * -> 위 1)번에서 입력한 문자열이 이제 보임
 */
@Service
public class ChatService implements MessageListener {

    /**
     * RedisConfig 에서 만든 빈 사용
     * redisContainer
     */
    @Autowired
    private RedisMessageListenerContainer container;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public void enterCharRoom(String chatRoomName) {
        // 구독 리스너 등록
        // 현재 클래스가 MessageListener를 구현중이므로 this
        container.addMessageListener(this, new ChannelTopic(chatRoomName));

        Scanner in = new Scanner(System.in);

        // 사용자 입력을 기다림
        while(in.hasNextLine()) {
            String line = in.nextLine();
            if(line.equals("q")) {
                System.out.println("Quit..");
                break;
            }

            // redis 전송
            // publish chat1 line
            redisTemplate.convertAndSend(chatRoomName, line);
        }

        // 종료
        container.removeMessageListener(this);
    }


    /**
     * 메시지가 도착할때마다 화면에 출력
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("Message: " + message.toString());
    }
}
