import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking


fun main() = runBlocking<Unit> {
    val flow = simple()
    // collect 가 있어야 flow 코드가 동작한다.
    flow.collect { value -> println(value) }
}

// = flux
fun simple(): Flow<Int> = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}