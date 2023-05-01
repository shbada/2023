package chapter13;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.util.Arrays;
import java.util.List;

/**
 * 오동작 하는 TestPublisher 예제
 * - 리액티브 스트림즈 사양 위반 여부를 사전에 체크하지 않았다는 의미다.
 */
public class ExampleTest13_19 {
    @Test
    public void divideByTwoTest() {
        // onNext signal을 전송하기 전에 Validation 과정을 거쳐 전송할 데이터가 null이면 NPE 오류 발생
        TestPublisher<Integer> source = TestPublisher.create();

        // 데이터의 값이 null이라도 정상 동작하는 TestPublisher를 생성한다.
        // onNext signal을 전송하는 과정에서 NPE 발생
//        TestPublisher<Integer> source =
//                TestPublisher.createNoncompliant(TestPublisher.Violation.ALLOW_NULL);

        StepVerifier
                .create(GeneralTestExample.divideByTwo(source.flux()))
                .expectSubscription()
                .then(() -> {
                    getDataSource().stream()
                            .forEach(data -> source.next(data));
                    source.complete();
                })
                .expectNext(1, 2, 3, 4, 5)
                .expectComplete()
                .verify();
    }

    private static List<Integer> getDataSource() {
        return Arrays.asList(2, 4, 6, 8, null);
    }
}
