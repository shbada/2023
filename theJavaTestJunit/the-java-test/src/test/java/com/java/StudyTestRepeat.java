package com.java;

import com.java.annotations.SlowTest;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

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
class StudyTestRepeat {
    // junit5 부터는 public 생략 가능
//    @RepeatedTest(10) // 10번 반복
    @DisplayName("스터디 만들기")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetition") // 10번 반복
    void repeatTest(RepetitionInfo repetitionInfo) { // 인자를 받을 수 있다.
        System.out.println("test");
        // 몇번째 반복?
        System.out.println(repetitionInfo.getCurrentRepetition());
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message = {0}") // 다른 값으로 테스트를 반복하고 싶을때
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요."}) // 4번 호출
    void parameterizedTest(String message) {
        System.out.println(message);
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message = {0}") // 다른 값으로 테스트를 반복하고 싶을때
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요."}) // 4번 호출
    @EmptySource // 빈 문자열을 테스트 추가
    @NullSource // null 테스트 추가
//    @NullAndEmptySource // 위 Empty, Null을 합친 어노테이션
    void parameterizedTest2(String message) {
        System.out.println(message);
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message = {0}") // 다른 값으로 테스트를 반복하고 싶을때
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTest2(Integer message) {
        System.out.println(message);
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message = {0}") // 다른 값으로 테스트를 반복하고 싶을때
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTest2(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message = {0}") // 다른 값으로 테스트를 반복하고 싶을때
    @CsvSource({"10, 'java'", "20, '스프링'"})
    void parameterizedTest2(Integer limit, String name) {
        Study study = new Study(limit, name);
        System.out.println(study);
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message = {0}") // 다른 값으로 테스트를 반복하고 싶을때
    @CsvSource({"10, 'java'", "20, '스프링'"})
    void parameterizedTest3(ArgumentsAccessor argumentsAccessor) { // 2개 이상의 아규먼트
        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message = {0}") // 다른 값으로 테스트를 반복하고 싶을때
    @CsvSource({"10, 'java'", "20, '스프링'"})
    void parameterizedTest3(@AggregateWith(StudyAggregator.class) Study study) { // 2개 이상의 아규먼트
        System.out.println(study);
    }

    /**
     * inner static 또는 public 인 클래스만 사용 가능
     */
    static class StudyAggregator implements ArgumentsAggregator {

        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }
    /**
     * 하나의 아규먼트에 관한것
     */
    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }
}