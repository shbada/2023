package chapter13;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.test.publisher.PublisherProbe;

/**
 * PublisherProbe 예제
 */
public class ExampleTest13_21 {
    @Test
    public void publisherProbeTest() {
        // 테스트 대상 Publisher를 PublisherProbe.of() 메서드로 래핑한다.
        PublisherProbe<String> probe =
                PublisherProbe.of(PublisherProbeTestExample.supplyStandbyPower());

        StepVerifier
                .create(PublisherProbeTestExample
                        .processTask(
                                PublisherProbeTestExample.supplyMainPower(),
                                probe.mono()) // probe.mono()에서 리턴된 Mono 객체
                )
                .expectNextCount(1)
                .verifyComplete();

        probe.assertWasSubscribed(); // 기대하는 Publisher가 구독을 했는가?
        probe.assertWasRequested(); // 기대하는 Publisher가 요청을 했는가?
        probe.assertWasNotCancelled(); // 기대하는 Publisher가 중간에 취소하지 않았는가?
    }
}
