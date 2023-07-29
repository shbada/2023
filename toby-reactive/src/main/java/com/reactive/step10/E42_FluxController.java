package com.reactive.step10;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Spring5 사용
 * Netty
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class E42_FluxController {
    @GetMapping("/event/{id}")
    Mono<Event> event(@PathVariable long id) {
       return Mono.just(new Event(id, "event" + id)).log();
    }

    @GetMapping("/event/list/{id}")
    Mono<List<Event>> hello(@PathVariable long id) {
        // Mono 안에 리스트를 담는다면?
        List<Event> list = Arrays.asList(new Event(1L, "event1"), new Event(2L, "event2"));
        return Mono.just(list).log(); // 하나의 데이터 (하나하나의 엔티티 레벨에서 뭔가 수행하는것은 안되고, 통째로 수행해야한다)
    }

    /**
     * produces = MediaType.TEXT_EVENT_STREAM_VALUE
     * 리스트일때 각 엔티티별로 이벤트성으로 결과를 받을 수 있다.
     *
     * @return
     */
    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE) // 어떤 MediaType으로 리턴될것인가?
    Flux<Event> events() {
        // Mono와 다르게 여러개를 넣을 수 있다.
        return Flux.just(new Event(1L, "event1"), new Event(2L, "event2")).log();
    }

    @GetMapping(value = "/events/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE) // 어떤 MediaType으로 리턴될것인가?
    Flux<Event> eventStream() {
        return Flux.fromStream(Stream.generate(() -> new Event(System.currentTimeMillis(), "value")))
                .delayElements(Duration.ofSeconds(1)) // 1초 delay (1초마다 10개씩, 10초(블로킹: 이메서드 전체 말고, 이 delay 처리하는 백그라운드 스레드) : 백그라운드 스레드로 수행, 처음 수행된 스레드가 아닌 delay 처리하는 스레드가 10초동안 물고있다)
                .take(10).log(); // 10개 request가 오면 cancel() 날린다. 더이상 하지 않겠다.
    }

    @GetMapping(value = "/events/sink", produces = MediaType.TEXT_EVENT_STREAM_VALUE) // 어떤 MediaType으로 리턴될것인가?
    Flux<Event> eventSink() {
        return Flux
                .<Event>generate(sink -> sink.next(new Event(System.currentTimeMillis(), "value"))) // sink : 데이터를 흘려서 보내는 역할
                .delayElements(Duration.ofSeconds(1)) // 1초 delay (1초마다 10개씩, 10초(블로킹: 이메서드 전체 말고, 이 delay 처리하는 백그라운드 스레드) : 백그라운드 스레드로 수행, 처음 수행된 스레드가 아닌 delay 처리하는 스레드가 10초동안 물고있다)
                .take(10).log(); // 10개 request가 오면 cancel() 날린다. 더이상 하지 않겠다.
    }

    @GetMapping(value = "/events/sink/generate", produces = MediaType.TEXT_EVENT_STREAM_VALUE) // 어떤 MediaType으로 리턴될것인가?
    Flux<Event> eventGenerateSink() {
        Flux<Event> es = Flux
                .<Event, Long>generate(() -> 1L, (id, sink) -> {
                    sink.next(new Event(id, "value" + id));
                    return id + 1; // 바뀐값
                })
//                .delayElements(Duration.ofSeconds(1)) // 1초 delay (1초마다 10개씩, 10초(블로킹: 이메서드 전체 말고, 이 delay 처리하는 백그라운드 스레드) : 백그라운드 스레드로 수행, 처음 수행된 스레드가 아닌 delay 처리하는 스레드가 10초동안 물고있다)
                .take(10);// 10개 request가 오면 cancel() 날린다. 더이상 하지 않겠다.

        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1)); // 0, 1, 2, 3, 4 등

        return Flux.zip(es, interval).map(tu -> tu.getT1()).log();
    }
}
