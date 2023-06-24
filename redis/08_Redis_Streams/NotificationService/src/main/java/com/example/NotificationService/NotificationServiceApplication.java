package com.example.NotificationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Redis Streams
 * - append-only log를 구현한 자료구조
 * - 하나의 key로 식별되는 하나의 stream에 엔트리가 계속 추가되는 구조
 * - 하나의 엔트리는 entryId + (key-value 리스트)로 구성
 * - 추가된 데이터는 사용자가 삭제하지 않는 한 지워지지 않음
 *
 * 활용
 * - 센서 모니터링(지속적으로 변하는 데이터인 시간 별 날씨 수집 등)
 * - 유저별 알림 데이터 저장
 * - 이벤트 저장소
 *
 * 명령어
 */
@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

}
