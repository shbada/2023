class `7_LateInit` {
    // 가변 프로퍼티에 대한 지연 초기화
    // nullable이 아님에도 초기호 안했어도 컴파일 오류가 발생하지 않는다.
    lateinit var text: String // var : 가변

    val textInitialized: Boolean
        // isInitialized 는 클래스 내부에서만 사용 가능하다. (Main 등에서 사용 불가능)
        get() = this::text.isInitialized // 초기화 여부

    fun printText() {
        println(text)
    }
}

fun a (str:String, block: (String) -> Unit) {
    block(str)
}
fun main() {

    "".let {  }
    a("") {
        it.length
    }
    val test = `7_LateInit`()

    if (!test.textInitialized) {
        test.text = "하이요"
    }
    test.printText() // 초기화 전에 출력 요청하면 오류 발생

}