import kotlinx.coroutines.*

fun sum(a: Int, b: Int) = a + b

fun main() = runBlocking<Unit> {

    // Deferred<Int> 타입을 반환한다.
    // Deferred<Int> 내부에 결과를 받을 수 있는 await() 제공
    val result1: Deferred<Int> = async {
        delay(100)
        sum(1, 3)
    }

    // 비동기 실행 결과 받기
    println("result1 : ${result1.await()}")

    val result2: Deferred<Int> = async {
        println("@")
        delay(100)
        sum(2, 5)
    }

    println("result2 : ${result2.await()}")


}
