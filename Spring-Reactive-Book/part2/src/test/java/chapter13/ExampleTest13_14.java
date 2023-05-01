package chapter13;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * StepVerifier Context 테스트 예제
 */
public class ExampleTest13_14 {
    @Test
    public void getSecretMessageTest() {
        Mono<String> source = Mono.just("hello");

        StepVerifier
                .create(
                    ContextTestExample
                        .getSecretMessage(source)
                        .contextWrite(context ->
                                        context.put("secretMessage", "Hello, Reactor"))
                        .contextWrite(context -> context.put("secretKey", "aGVsbG8="))
                )
                .expectSubscription() // 구독 발생 기대
                .expectAccessibleContext() // Context 전파 기대
                .hasKey("secretKey") // Context에 key 포함 기대
                .hasKey("secretMessage") // Context에 key 포함 기대
                .then() // Sequence 다음 Signal 이벤트의 기댓값을 평가할 수 있도록한다.
                .expectNext("Hello, Reactor") // emit 문자열 기대
                .expectComplete() // onComplete Signal 전송 기대
                .verify();
    }
}
