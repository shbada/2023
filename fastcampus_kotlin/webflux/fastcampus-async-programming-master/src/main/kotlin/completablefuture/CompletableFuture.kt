package completablefuture

import future.sum
import java.util.concurrent.CompletableFuture

/**
 * Future 단점 보완
 */
fun main() {
    val completableFuture = CompletableFuture.supplyAsync { // supplyAsync 사용해서 비동기 작업을 수행
        Thread.sleep(2000) // 2초 sleep
        sum(100, 200) // 수행
    }

    println("계산 시작")

    // thenApplyAsync 사용하면 해당 함수가 논블로킹으로 동작하게되어, 아래 코드를 계속 수행
    //completableFuture.thenApplyAsync(::println) // 논블로킹으로 동작

    // 블로킹 
    val result = completableFuture.get() // 블로킹으로 동작
    println(result)

    while (!completableFuture.isCompletedExceptionally) {
        Thread.sleep(500)
        println("계산 결과를 집계 중입니다.")
    }
    println("계산 종료")
}
