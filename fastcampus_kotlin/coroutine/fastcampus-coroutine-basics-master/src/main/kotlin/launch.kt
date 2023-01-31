import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

//fun main() = runBlocking<Unit> {
//    launch {
//        delay(500L)
//        println("World!")
//    }
//    println("Hello")
//}

fun main() = runBlocking<Unit> {

    // launch : 쓰레드 차단 없이
    val job1: Job = launch {
        val timeMillis = measureTimeMillis {
            delay(150) // 쓰레드 차단 없이 일시중단 (일시중단된 쓰레드는 코루틴 내에서 다른 일시중단된 함수를 실행함)
        }
        println("async task-1 $timeMillis ms")
    }
    job1.cancel() // cancel() 하면 실행이 취소되서 실행안된다.


    // 얘가 먼저 실행되겠다
    val job2: Job = launch(start = CoroutineStart.LAZY){
        val timeMillis = measureTimeMillis {
            delay(100)
        }
        println("async task-2 $timeMillis ms")
    }

    println("start task-2")

    // launch(start = CoroutineStart.LAZY) 처리 후 아래 코드 추가
    // -> 이 시점에 수행
    job2.start()

}