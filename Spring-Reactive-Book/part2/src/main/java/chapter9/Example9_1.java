package chapter9;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

/**
 * create() Operator를 사용하는 예제
 *  - 일반적으로 Publisher가 단일 쓰레드에서 데이터 생성한다.
 */
@Slf4j
public class Example9_1 {
    public static void main(String[] args) throws InterruptedException {
        int tasks = 6;
        Flux
            .create((FluxSink<String> sink) -> {
                IntStream
                        .range(1, tasks)
                        // create()가 처리해야할 작업의 개수만큼 doTask() 메서드를 호출하여 작업 처리
                        .forEach(n -> sink.next(doTask(n)));
            })
            // 모두 별도의 스레드로 실행 (결과적으로 main 스레드 제외한 3개의 스레드가 동시에 실행)
            .subscribeOn(Schedulers.boundedElastic()) // 작업을 처리하는 스레드 지정
            .doOnNext(n -> log.info("# create(): {}", n))
            .publishOn(Schedulers.parallel()) // 처리 결과를 가공하는 스레드
            .map(result -> result + " success!")
            .doOnNext(n -> log.info("# map(): {}", n))
            .publishOn(Schedulers.parallel()) // 가공된 결과를 Subscriber에게 전달하는 스레드
            .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }

    private static String doTask(int taskNumber) {
        // now tasking.
        // complete to task.
        return "task " + taskNumber + " result";
    }
}
