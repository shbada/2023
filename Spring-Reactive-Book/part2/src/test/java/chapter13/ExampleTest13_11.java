package chapter13;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

/**
 * StepVerifier Backpressure 테스트 예제
 */
public class ExampleTest13_11 {
    @Test
    public void generateNumberTest() {
        StepVerifier
                // 한번에 100개의 숫자 emit
                // 결과 fail -> OverflowException 발생
                .create(BackpressureTestExample.generateNumber(), 1L) // 데이터 요청 개수 1
                .thenConsumeWhile(num -> num >= 1)
                .verifyComplete();
    }
}
