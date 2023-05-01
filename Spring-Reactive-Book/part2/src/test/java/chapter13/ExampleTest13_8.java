package chapter13;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * StepVerifier 활용 예제
 *  -검증에 소요되는 시간을 제한한다.
 */
public class ExampleTest13_8 {
    @Test
    public void getCOVID19CountTest() {
        StepVerifier
                .create(TimeBasedTestExample.getCOVID19Count(
                                // 1분뒤에 emit하도록 설정
                                Flux.interval(Duration.ofMinutes(1)).take(1)
                        )
                )
                .expectSubscription()
                .expectNextCount(11)
                .expectComplete()
                // 테스트 대상 메서드에 대한 기댒값을 평가하는데 걸리는 시간을 제한한다.
                // 3초 내에 기댓값의 평가가 끝나지 않으면 시간초과로 간주한다.
                .verify(Duration.ofSeconds(3));
    }
}
