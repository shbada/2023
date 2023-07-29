package com.reactive.step07;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class E32_CompletableFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Future f;
//        f.get(); // 비동기 작업을 대기하거나 등
//        CompletableFuture cf; // 이 오브젝트를 가지고 비동기작업을 직접 완료하겠다?

        // 작업이 완료된 상태로 CompletableFuture를 생성
        CompletableFuture<Integer> f = CompletableFuture.completedFuture(1);
        System.out.println(f.get()); // 1

        CompletableFuture<Integer> f2 = new CompletableFuture<>();
//        System.out.println(f2.get()); // 무한 대기

        f2.complete(2); // 완료 처리
        System.out.println(f2.get()); // 2

        // 예외 명시적
        // 예외 정보를 f2가 담고있을 뿐, 별도로 에러가 발생하진 않는다.
        f2.completeExceptionally(new RuntimeException());
        System.out.println(f2.get()); // 블로킹 - get()을 하는 순간, 예외가 발생되는것

    }
}
