package com.reactive.step08;

import com.reactive.step07.E36_MyService;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * Spring5 사용
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class E37_MyController {
    private final E39_MyService myService;
    static final String URL1 = "http://localhost:8081/step08/service?req={req}";
    static final String URL2 = "http://localhost:8081/step08/service2?req={req}";

    WebClient client = WebClient.create();

    /**
     * Mono : Callable, DeferredResult 와 같이 스프링 웹플럭스 등에서는 Mono, Flux 등을 사용한다.
     * 파라미터로 한번에 던지고, 리턴값을 한번에 결과에 담고.
     *
     * 비동기 논블로킹 관점
     * exchange() : 스레드 블로킹 없이, 바로 리턴
     * asyncRestTemplate + DeferredResult 와 동일하다고 보면된다.
     * @param idx
     * @return
     */
    @GetMapping("/step08/rest")
    public Mono<String> rest(int idx) {
        // Mono 리턴
        // Mono 를 컨테이너라고 생각하자. List<String> 에서 List와 같은..
//        String s = "Hello";
//        Mono<String> m = Mono.just("Hello"); // 많은 기능을 제공

        // 정의하는 것만으로는 호출을 하지 않는다.
        Mono<ClientResponse> res = client.get().uri(URL1, idx).exchange();
//        res.subscribe(); // subscribe()를 해준 순간 위가 호출된다.
        // 호출은 Spring WebFlux Framwork가 대신 해준다.
        // Mono 객체를 리턴하는 순간
//        return res; // res 안의 ClientResponse 의 String value 를 꺼내야한다.

        // Mono<ClientResponse> 는 Mono로 감싸져있다.
//        ClientResponse cr = null;
//        Mono<String> body  = cr.bodyToMono(String.class);

        // String 을 감싼 Mono 타입
        // clientResponse -> clientResponse.bodyToMono(String.class) : Mono<String>
        // map -> Mono<Mono<String>>
//        Mono<Mono<String>> body = res.map(clientResponse -> clientResponse.bodyToMono(String.class));
        // flat 해주자.
        Mono<String> body = res.flatMap(clientResponse -> clientResponse.bodyToMono(String.class));

        // 한줄로도 가능
//        client.get().uri(URL1, idx).exchange().flatMap(clientResponse -> clientResponse.bodyToMono(String.class));

//        return Mono.just("Hello");
        return body; // API를 한번 호출해서 body를 String 으로 꺼내서 Mono에 담아서 리턴하는 리액티브 코드
    }

    @GetMapping("/step08/rest/chain")
    public Mono<String> restChain(int idx) {
        // 한줄로도 가능
        Mono<String> body = client.get().uri(URL1, idx).exchange()
                .flatMap(c -> c.bodyToMono(String.class))
                .doOnNext(c -> log.info(c))
                .flatMap(res1 -> client.get().uri(URL2, res1).exchange())
                .flatMap(c -> c.bodyToMono(String.class)) // Mono<String>
                .doOnNext(c -> log.info(c))
                // 동기 수행하면 work 스레드를 계속 묶고있기 때문에 처리 속도가 느려진다.
//                .map((String res2) -> myService.work(res2)) // Mono<String> res2 : String, work() 리턴 : String
//                CompletableFuture<String> -> Mono<String> 바꾸는 작업을 해야한다.
                .flatMap(res2 -> Mono.fromCompletionStage(myService.work(res2)))
                ;

        return body;
    }
}
