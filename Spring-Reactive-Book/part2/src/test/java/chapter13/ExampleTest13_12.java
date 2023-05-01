package chapter13;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

/**
 * StepVerifier Backpressure 테스트 예제
 */
public class ExampleTest13_12 {
    @Test
    public void generateNumberTest() {
        StepVerifier
                .create(BackpressureTestExample.generateNumber(), 1L)
                .thenConsumeWhile(num -> num >= 1)
                .expectError()
                .verifyThenAssertThat() // 검증을 수행한 후, 추가적인 Assertion 가능
                .hasDroppedElements(); // Drop된 데이터가 있음을 Assertion

    }
}
