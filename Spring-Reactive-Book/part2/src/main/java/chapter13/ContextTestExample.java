package chapter13;

import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

public class ContextTestExample {
    public static Mono<String> getSecretMessage(Mono<String> keySource) {
        return keySource
                .zipWith(Mono.deferContextual(ctx ->
                                               Mono.just((String)ctx.get("secretKey"))))
                // 파라미터로 입력받은 keySource와 Context에 저장된 secret key 값을 비교해서 일치하면, Context에 저장된 Mono<String> secretMessage를 리턴해준다.
                .filter(tp ->
                            tp.getT1().equals(
                                   new String(Base64Utils.decodeFromString(tp.getT2())))
                )
                .transformDeferredContextual(
                        (mono, ctx) -> mono.map(notUse -> ctx.get("secretMessage"))
                );
    }
}
