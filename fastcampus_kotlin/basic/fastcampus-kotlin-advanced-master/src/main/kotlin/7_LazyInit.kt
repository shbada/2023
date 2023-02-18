class HelloBot {

    // val 불변
    // by lazy 사용 (멀티쓰레드 안전)
    // 기본 :  LazyThreadSafetyMode.SYNCHRONIZED
    // LazyThreadSafetyMode.NONE 등 상태값을 설정하여 쓰레드 안전성 무시 가능
    // 불변을 유지하면서 변수에 대한 초기화를 뒤로 미룰수 있다.
    val greeting: String by lazy(LazyThreadSafetyMode.PUBLICATION) {
        // 멀티쓰레드 환경에서도 동기화가 필요하지 않을때 : LazyThreadSafetyMode.PUBLICATION
        getHello()
    }

    fun sayHello() = println(greeting)
}

fun getHello() = "안녕하세요"

fun main() {
    val helloBot = HelloBot()
    // 초기화 이후에는 더이상 by lazy {}을 수행하지 않음

    // ...
    // ...
    for (i in 1..5) {
        Thread { // 쓰레드 생성하여 병렬로 수행해보자
            helloBot.sayHello()
        }.start()
    }

}
