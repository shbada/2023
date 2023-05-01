package chapter13;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * StepVerifier 활용 예제
 *  - 지정된 대기 시간동안 이벤트가 없을을 확인한다.
 */
public class ExampleTest13_9 {
    @Test
    public void getVoteCountTest() {
        StepVerifier
                .withVirtualTime(() -> TimeBasedTestExample.getVoteCount(
                                Flux.interval(Duration.ofMinutes(1))
                        )
                )
                .expectSubscription()
                // 지정 시간 동안 어떤 signal 이벤트도 발생하지 않았음을 기대한다.
                // 순차적이므로 총 5분의 시간 소요 (5분 시간을 줄일 수 있도록 위에서 withVirtualTime()을 사용했다.
                // 파라미터 : 지정한 시간동안 어떤 이벤트도 발생하지 않을 것이라고 기대하고, 지정한 시간만큼 시간을 앞당긴다.
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNoEvent(Duration.ofMinutes(1))
                .expectNextCount(5)
                .expectComplete()
                .verify();
    }
}
