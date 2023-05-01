package chapter13;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

/**
 * StepVerifier 활용 예제
 * - 주어진 시간을 앞당겨서 테스트 한다.
 */
public class ExampleTest13_7 {
    @Test
    public void getCOVID19CountTest() {
        StepVerifier
                // 가상 스케줄러의 제어를 받도록 해준다.
                // 테스트 대상 메서드의 Sequence가 1시간 뒤에 실제로 동작하는지 확인한다.
                .withVirtualTime(() -> TimeBasedTestExample.getCOVID19Count(
                                Flux.interval(Duration.ofHours(1)).take(1)
                        )
                )
                .expectSubscription()
                // 시간을 1시간 앞당긴다.
                // then()으로 후속작업 수행
                // 실제로 Sequence가 동작하려면 1시간을 기다려야하지만 StepVerifier 체인들이 VirtualTimeScheduler의 제어를 받기 때문에 1시간을 기다리지 않아도 된다.
                .then(() -> VirtualTimeScheduler
                                    .get()
                                    .advanceTimeBy(Duration.ofHours(1)))
                .expectNextCount(11) // emit된 데이터가 11개임을 기대
                .expectComplete()
                .verify();

    }
}
