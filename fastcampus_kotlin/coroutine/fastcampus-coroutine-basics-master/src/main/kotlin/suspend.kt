import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() {
    doSomething()
}

fun printHello() = println("hello")

// suspend 함수 : 일시중단이 가능한 함수
// 일반 함수가 suspend 함수를 바로 호출할 수 없다. suspend 함수가 일반 함수 호출은 가능하다.

// coroutineScope : 현재 쓰레드가 블로킹되지 않고 코루틴을 실행한다. (runBlocking과의 차이점)
suspend fun doSomething() = coroutineScope {

    launch {
        delay(200)
        println("world!")

    }

    launch {
        printHello()
    }

}


