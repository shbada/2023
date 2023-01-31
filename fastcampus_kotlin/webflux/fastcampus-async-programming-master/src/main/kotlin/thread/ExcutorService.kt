package thread

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

fun main() {
    // 팩토리 메서드 : newFixedThreadPool (5: 스레드 유지 개수)
    // -> 항상 스레드풀 내의 스레드를 5개로 유지한다는 의미
    val pool: ExecutorService = Executors.newFixedThreadPool(5)
    try {
        for (i in 0..5) {
            pool.execute {
                println("current-thread-name : ${Thread.currentThread().name}")
            }
        }
    } finally {
        pool.shutdown()
    }
    println("current-thread-name : ${Thread.currentThread().name}")
}
