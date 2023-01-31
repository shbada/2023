package future

import java.util.concurrent.Callable
import java.util.concurrent.Executors

fun sum(a: Int, b: Int) = a + b

fun main() {
    val pool = Executors.newSingleThreadExecutor()
    val future = pool.submit(Callable { // call 메서드 구현 (람다)
        sum(100, 200)
    })

    println("계산 시작")
    val futureResult = future.get() // 비동기 작업의 결과를 기다린다.
    println(futureResult)
    println("계산 종료")
}
