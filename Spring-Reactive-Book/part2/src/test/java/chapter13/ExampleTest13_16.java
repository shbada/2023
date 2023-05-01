package chapter13;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * StepVerifier Record 테스트 예제
 */
public class ExampleTest13_16 {
    @Test
    public void getCountryTest() {
        StepVerifier
                .create(RecordTestExample.getCapitalizedCountry(
                        Flux.just("korea", "england", "canada", "india")))
                .expectSubscription()
                // 파라미터로 전달한 Java의 컬렉션에 emit된 데이터를 추가하는 세션 시작
                .recordWith(ArrayList::new)
                .thenConsumeWhile(country -> !country.isEmpty()) // 파라미터로 전달한 Predicate와 일치하는 데이터는 다음 단계에서 소비 가능
                .consumeRecordedWith(countries -> { // 컬렉션에 기록된 데이터를 소비
                    assertThat(
                            countries
                                    .stream()
                                    .allMatch(country ->
                                            Character.isUpperCase(country.charAt(0))),
                            is(true)
                    );
                })
                .expectComplete()
                .verify();
    }
}
