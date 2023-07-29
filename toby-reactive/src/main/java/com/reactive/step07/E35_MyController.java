package com.reactive.step07;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@Slf4j
public class E35_MyController {
    private final E36_MyService myService;

    AsyncRestTemplate rt = new AsyncRestTemplate(
            new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));

    @GetMapping("/step07/rest")
    public DeferredResult<String> rest(int idx) {
        DeferredResult<String> dr = new DeferredResult<>();

        /**
         * CompletableFuture 를 쓴다면
         * myService를 비동기적으로 할 필요가 없다.
         */
        toCompletableFuture(rt.getForEntity("http://localhost:8081/service?req={req}", String.class, "hello" + idx))
                .thenCompose(s -> toCompletableFuture(rt.getForEntity("http://localhost:8081/service2?req={req}", String.class, s.getBody())))
//                .thenCompose(s2 -> toCompletableFuture(myService.work(s2.getBody())))
                // 비동기가 아니므로 (ListenableFuture 리턴이 아니고 단순 String 리턴이므로 thenApply로 바꾸자.)
//                .thenApply(s2 -> myService.work(s2.getBody()))
                // 별도의 스레드에서 띄어지도록 한다.
                .thenApplyAsync(s2 -> myService.work(s2.getBody()))
                .thenAccept(s3 -> dr.setResult(s3))
                // 어디서든 예외가 발생하였다면
                // exceptionally는 결과를 처리하고 끝내는 Consumer 아닌 결과를 리턴해야하는 Function type으로 정의되야한다.
                // 그래서 리턴값이 없더라도 reutn null을 해줘야한다.
                .exceptionally(e -> {
                    dr.setErrorResult(e.getMessage());
                    return (Void) null;
                });

        return dr;
    }

    /**
     * lf의 콜백 방식으로 들어오는 결과를 CompletableFuture 로 변환
     * @param lf
     * @return
     * @param <T>
     */
    <T> CompletableFuture<T> toCompletableFuture(ListenableFuture<T> lf) {
        CompletableFuture<T> cf = new CompletableFuture<>();

        lf.addCallback(s -> cf.complete(s), e -> cf.completeExceptionally(e));
        return cf;
    }
}
