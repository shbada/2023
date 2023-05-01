package chapter13;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

/**
 * 정상동작 하는 TestPublisher 예제
 */
public class ExampleTest13_18 {
    @Test
    public void divideByTwoTest() {
        /*
            TestPublisher을 사용하면, 개발자가 직접 프로그래밍 방식으로 Signal을 발생시키면서 원하는 상황을 미세하게 재연하며 테스트를 진행할 수 있다.
            - 복잡한 로직이 포함된 대상 메서드를 테스트하거나 조건에 따라서 Signal을 변경해야 되는 등의 특정 상황을 테스트하기가 용이하다.
         */
        TestPublisher<Integer> source = TestPublisher.create(); // TestPublisher을 생성

        StepVerifier
                .create(GeneralTestExample.divideByTwo(source.flux())) // 파라미터로 Flux를 전달하기 위해 flux() 메서드를 이용하여 Flux로 변환
                .expectSubscription()
                .then(() -> source.emit(2, 4, 6, 8, 10)) // emit 메서드를 사용해서 테스트에 필요한 데이터 emit
                .expectNext(1, 2, 3, 4)
                .expectError()
                .verify();
    }
}
