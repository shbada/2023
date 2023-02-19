fun main() {

    val str = "안녕하세요"

    //
    val length: Int = with(str) {
        length // return 생략 가능
    }
    println(length)
}