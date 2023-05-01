package chapter13;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class BackpressureTestExample {
    public static Flux<Integer> generateNumber() {
        return Flux
                .create(emitter -> {
                    for (int i = 1; i <= 100; i++) {
                        emitter.next(i);
                    }
                    emitter.complete();
                }, FluxSink.OverflowStrategy.ERROR); // 오버플로가 발생하면 OverflowException 발생
    }
}
