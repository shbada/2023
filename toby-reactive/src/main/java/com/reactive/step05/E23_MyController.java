package com.reactive.step05;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@Slf4j
public class E23_MyController {
    private final E26_MyService myService;

//    RestTemplate rt = new RestTemplate();
//    AsyncRestTemplate rt = new AsyncRestTemplate();
    // 논블로킹 : Netty를 사용해보자.
    AsyncRestTemplate rt = new AsyncRestTemplate(
            // http client 라이브러리를 Netty 사용
            // 네티 설정 추가 : 스레드 1개만 쓰도록 설정 (default : 프로세스 갯수(CPU 코어 갯수) * 2)
            // 병렬적 수행으로 변경
            new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));

//    @GetMapping("/rest")
//    public String rest(int idx) throws InterruptedException {
//        String res = rt.getForObject("http://localhost:8081/service?req={req}", String.class, "hello" + idx);
//        return "rest : " + idx;
//    }

    @GetMapping("/rest")
    public ListenableFuture<ResponseEntity<String>> rest(int idx) throws InterruptedException {
        // 언제가 될진 모르지만, 언젠가 결과가 담겨지면 그때 응답으로 처리해준다는 약속
        DeferredResult<String> dr = new DeferredResult<>();

        // getForObject : 블로킹 (결과가 오기 전까지는 대기 상태)
        // 이 스레드가 계속 놀고있기 때문에, 이 놀고있는 스레드를 추가로 오는 요청에 줄 방법이 없다.
        // -> API 호출 로직을 비동기로 바꿔야한다.
        // -> 이를 위해 스프링에서 제공하는 방법이 있다. AsyncRestTemplate
        ListenableFuture<ResponseEntity<String>> f1 =
                rt.getForEntity("http://localhost:8081/service?req={req}",
                        String.class,
                        "hello" + idx);
        // 바로 리턴해버리자.
        // 컨트롤러가 이 response를 리턴하면 2초 대기 없이 리턴하는데,
        // 이 콜백은 스프링이 알아서 해준다. 응답이 오면 이 컨트롤러의 리턴 값으로 처리한다.
        // 에러가 발생하면 에러 처리를 한다.

        // 결과를 가공하려면?
        f1.addCallback(s -> {
//            dr.setResult(s.getBody() + "/work");

            /** 두번째 서비스 호출 추가 - Netty 사용한 rt 객체를 위 첫번째 서비스 호출과 함께 사용하는 것
             * 2번 호출이므로 최소 4초가 걸리겠다.
             * 100개 요청된 경우 -> 4.5초 정도 걸린다.
             * HTTP Call 이 총 200번 호출된거다. 꽤 괜찮은 성능이다.
             * */
            ListenableFuture<ResponseEntity<String>> f2 =
                    rt.getForEntity("http://localhost:8081/service2?req={req}",
                            String.class,
                            "hello" + s.getBody());

            f2.addCallback(s2 -> {
                ListenableFuture<String> f3 = myService.work(s2.getBody());
                f3.addCallback(s3 -> {
                    dr.setResult(s3);
                }, e -> {
                    dr.setErrorResult(e.getMessage());
                });
            }, e -> {
                dr.setErrorResult(e.getMessage());
            });
        }, e -> {
            // 예외를 던지는건 콜백 방식에서 어느 StackTrace를 타고 실행되는지 몰라서, 정확하게 던질 수 없다.
            dr.setErrorResult(e.getMessage()); // 이렇게 하면 에러를 알아서 처리한다.
        });

        return f1;
    }
}
