package chapter13;

import reactor.core.publisher.Mono;

public class PublisherProbeTestExample {
    /**
     * 평소에 주전력을 사용해서 작업을 진행하다가 주전력이 끊겼을 경우에만 예비 전력을 사용
     * @param main
     * @param standby
     * @return
     */
    public static Mono<String> processTask(Mono<String> main, Mono<String> standby) {
        return main
                .flatMap(massage -> Mono.just(massage)) // Mono.empty()를 리턴하기때문에
                // 최종적으로 Mono<String> standby가 동작한다.
                .switchIfEmpty(standby); // 예비전력을 사용하도록 하는 Operator (emit 없이 종료되는 경우, 대체 Publisher가 데이터를 emit한다.)
    }

    public static Mono<String> supplyMainPower() {
        return Mono.empty();
    }

    public static Mono supplyStandbyPower() {
        return Mono.just("# supply Standby Power");
    }
}
