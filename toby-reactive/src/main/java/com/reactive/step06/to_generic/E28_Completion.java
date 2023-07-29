package com.reactive.step06.to_generic;

import lombok.NoArgsConstructor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 */
@NoArgsConstructor
public class E28_Completion<S, T> {
    E28_Completion next; // 타입 파라미터를 못줬음. <T, ?>

    /**
     * lf : 수행 결과
     * @param lf
     * @return
     */
    public static <S, T> E28_Completion<S, T> from(ListenableFuture<T> lf) { // S: 넘어온 파라미터, T: 결과타입
        E28_Completion<S, T> c = new E28_Completion();

        lf.addCallback(s -> c.complete(s), e -> c.error(e));

        return c;
    }

    public void error(Throwable e) {
        if (next != null) {
            // 다음 호출할게 있으면
            next.error(e);
        }
    }

    private void complete(T s) { // 결과 자체 s / 내가 수행한것의 결과 타입 T
        if (next != null) { // 작업 완료 후, next 가 있으면 next에 작업 결과를 넘긴다.
            next.run(s);
        }
    }

    /**
     * 리팩토링 - 다형성 제공
     * @param value
     */
    public void run(S value) { // 앞의 Completion 에서 이걸 받는것이므로 받는쪽의 S
    }

    public void andAccept(Consumer<T> con) { // ResponseEntity<String> : T (받는쪽)
        E28_Completion<T, Void> c = new E29_AcceptCompletion(con);
        this.next = c;
    }

    public E28_Completion<T, T> andError(Consumer<Throwable> econ) { // 내가 넘겨주는걸 받는 Completion
        E28_Completion c = new E31_ErrorCompletion(econ);
        this.next = c;
        
        return c;
    }

    public <V> E28_Completion<T, V> andApply(Function<T, ListenableFuture<V>> fn) {
        E28_Completion<T, V> c = new E30_ApplyCompletion(fn);
        this.next = c;

        return c; // 체이닝이 가능한 구조가 되도록
    }
}
