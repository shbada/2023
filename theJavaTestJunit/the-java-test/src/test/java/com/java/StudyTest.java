package com.java;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Springboot 2.2 부터 spring-boot-starter 에 junit5 의존성이 적용되어있다.
 */
// _를 공백으로 변경해준다.
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // 모든 테스트에 적용
class StudyTest {
    // junit5 부터는 public 생략 가능
    @Test
    @DisplayName("스터디 만들기") // 테스트 이름 지정
//    @Disabled //  전체 테스트 실행시 제외된다.
    void create() {
        Study study = new Study(-30);
        assertNotNull(study);
        // String vs 람다 또는 익명클래스
        // assertEquals(.., .., "스터디를 처음 만들면 "  + StudyStatus.DRACFT + " 상태다.") // String
        // 위 문자열 연산을 람다는 최대한 하지 않는다. 필요한 시점까지 미뤄서, 그 시점에 한다.
        // 비용이 걱정되는 연산일 경우 람다를 쓰는게 더 유리하다.
        /*
		assertEquals(StudyStatus.DRAFT, study.getStatus(), new Supplier<String>() {
			@Override
			public String get() {
				return "스터디를 처음 만들면 상태값이 DRAFT여야 한다.";
			}
		});
		*/
        assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");
    }

    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE31")
    void create_new_syudy() {
        Study study = new Study(-30);

        // 묶을 수 있다.
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                        () -> "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
                () -> assertTrue(study.getLimit() > 0,
                        () -> "스터디를 최대 참석 가능인원은 0보다 커야 한다."));
    }

    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE31")
    void create_new_syudy_exception() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Study(-10));

        String message = exception.getMessage();
        assertEquals("limit는 0보다 커야 한다.", message);
    }

    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE31")
    void create_new_syudy_timeout() {
        // 성공 예시
        // 10초 안에 끝나야한다.
        // assertTimeout(Duration.ofSeconds(10), () -> new Study(10));

        // 실패 예시
        assertTimeout(Duration.ofMillis(100), () -> {
            // 별도 쓰레드에서 실행하기 때문에 스레드로컬(ThreadLocal) 을 사용하는 코드가 있으면,
            // 예상치 못한 결과가 나올 수 있다.
            // 쓰레드로컬은 다른 쓰레드와 공유가 안된다. 이런 경우에는 스프링의 트랜잭션 설정이 제대로 적용 안될 수 있다.
            // 롤백이 안되고 DB에 반영이 될 수가 있다.
            new Study(10);
            Thread.sleep(300); // sleep 을 걸어서 오류나게끔 한다.
        });
    }

    // private 안되고, 리턴타입이 있으면 안된다.
    @BeforeAll // 모든 테스트 수행 전 수행
    static void beforeAll() {
        System.out.println("before all");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    // 각 테스트별 수행 전 수행
    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }
}