package com.java;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

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
class StudyTestAssum {
    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE31")
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
    @EnabledOnOs({OS.WINDOWS, OS.LINUX})
    void create_new_syudy_os1() {
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
    @EnabledOnOs({OS.LINUX})
    void create_new_syudy_again_os2() {
        String name = "JinSeok";

        assumingThat("JinSeok".equalsIgnoreCase(name), () -> {
            System.out.println("again JinSeok 이름이 맞습니다.@@@@@@@@");
            Study actual = new Study(10);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });

        assumingThat("test".equalsIgnoreCase(name), () -> {
            System.out.println("again test 이름이 맞습니다.@@@@@@@@");
            Study actual = new Study(10);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });
    }

    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE31")
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_11})
    void create_new_syudy_java() {
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
    @DisabledOnJre(JRE.JAVA_8)
    void create_new_syudy_again_java2() {
        String name = "JinSeok";

        assumingThat("JinSeok".equalsIgnoreCase(name), () -> {
            System.out.println("again JinSeok 이름이 맞습니다.@@@@@@@@");
            Study actual = new Study(10);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });

        assumingThat("test".equalsIgnoreCase(name), () -> {
            System.out.println("again test 이름이 맞습니다.@@@@@@@@");
            Study actual = new Study(10);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });

    }

    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE31")
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void create_new_syudy_again_environment() {
        String name = "JinSeok";

        assumingThat("JinSeok".equalsIgnoreCase(name), () -> {
            System.out.println("again JinSeok 이름이 맞습니다.@@@@@@@@");
            Study actual = new Study(10);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });

        assumingThat("test".equalsIgnoreCase(name), () -> {
            System.out.println("again test 이름이 맞습니다.@@@@@@@@");
            Study actual = new Study(10);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });

    }
}