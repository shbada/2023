package com.reactive.step06.to_generic;

import com.reactive.step05.E26_MyService;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequiredArgsConstructor
@Slf4j
public class E27_MyGController {
    private final E26_MyService myService;

    AsyncRestTemplate rt = new AsyncRestTemplate(
            new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));

    @GetMapping("/gen/rest")
    public DeferredResult<String> rest(int idx) throws InterruptedException {
        DeferredResult<String> dr = new DeferredResult<>();

        E28_Completion.from(rt.getForEntity("http://localhost:8081/service?req={req}", String.class, "hello" + idx))
                      // 결과를 받고 순차적 진행
                      .andApply(s -> rt.getForEntity("http://localhost:8081/service2?req={req}", String.class, "hello" + s.getBody()))
                      .andApply(s -> myService.work(s.getBody()))
                      // andError 을 만나는 순간에 에러 체크
                      .andError(e -> dr.setErrorResult(e)) // 위 작업중에 어디서 에러가 나던, 이 람다식이 실행하게 하고싶다. 에러가 안나면 패스하고싶다. 뒤에는 무시하고 종료한다.
                      .andAccept(s -> dr.setResult(s)) // 앞에서 일어난 비동기 작업의 결과를 담는다.
                      ;

        return dr;
    }
}
