package com.reactive.step06;

import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 */
@NoArgsConstructor
public class E28_Completion {
    E28_Completion next; // 다음 수행될 작업

    /**
     * E28_Completion 객체 생성
     * 비동기 작업에 대한 E28_Completion 객체
     * @param lf
     * @return
     */
    public static E28_Completion from(ListenableFuture<ResponseEntity<String>> lf) {
        E28_Completion c = new E28_Completion();

        // 콜백이 수행되는 순간 처리될 메서드 호출
        lf.addCallback(s -> c.complete(s), e -> c.error(e));

        return c;
    }

    public void error(Throwable e) {
        if (next != null) {
            // 다음 호출할게 있으면
            next.error(e);
        }
    }

    /**
     * 이게 호출됬다는거는 비동기 작업의 addCallback의 complete()가 호출되었다는 의미
     * @param s
     */
    private void complete(ResponseEntity<String> s) {
        if (next != null) { // 작업 완료 후, next 가 있으면 next에 작업 결과를 넘긴다.
            next.run(s); // 두번째 작업에 첫번째 작업이 결과를 넘겨준다.
        }
    }

    /**
     * 리팩토링 - 다형성 제공
     * @param value
     */
    public void run(ResponseEntity<String> value) {
    }

    public void andAccept(Consumer<ResponseEntity<String>> con) {
        E28_Completion c = new E29_AcceptCompletion(con);
        this.next = c;
    }

    public E28_Completion andError(Consumer<Throwable> econ) {
        E28_Completion c = new E31_ErrorCompletion(econ);
        this.next = c;
        
        return c;
    }

    /**
     *
     * @param fn
     * @return
     */
    public E28_Completion andApply(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn) {
        E28_Completion c = new E30_ApplyCompletion(fn);
        this.next = c;

        return c; // 체이닝이 가능한 구조가 되도록
    }
}
