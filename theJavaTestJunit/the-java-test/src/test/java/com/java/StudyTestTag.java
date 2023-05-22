package com.java;

import com.java.annotations.SlowTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/**
 * Springboot 2.2 부터 spring-boot-starter 에 junit5 의존성이 적용되어있다.
 */
// _를 공백으로 변경해준다.
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // 모든 테스트에 적용
class StudyTestTag {
    // junit5 부터는 public 생략 가능
    @Test
    @DisplayName("스터디 만들기") // 테스트 이름 지정
    @Tag("fast") // configuration 또는 pom.xml에 특정 태그가 달려있는 메서드만 수행되도록 지정 가능
//    @Disabled //  전체 테스트 실행시 제외된다.
    void create() {
        Study study = new Study(-30);
        assertNotNull(study);

        assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");
    }

//    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE31")
    @SlowTest
    void create_new_syudy() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);

        // local인 경우에만 그 다음 테스트 코드를 실행한다.
        assumeTrue("LOCAL".equalsIgnoreCase(test_env)); // 환경변수가 local인지?

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
    void create_new_syudy_assertThat() {
        String name = "JinSeok";

        assumingThat("JinSeok".equalsIgnoreCase(name), () -> {
            System.out.println("JinSeok 이름이 맞습니다.@@@@@@@@");
            Study actual = new Study(10);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });

        assumingThat("test".equalsIgnoreCase(name), () -> {
            System.out.println("test 이름이 맞습니다.@@@@@@@@");
            Study actual = new Study(10);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });

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