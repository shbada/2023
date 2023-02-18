/**
 * 문자열 첫번째 원소 리턴
 */
fun String.first() : Char {
    return this[0]
}

fun String.addFirst(char: Char) : String {
    // this : 수신자 객체
    return char + this.substring(0)
}

class MyExample {
    fun printMessage() = println("클래스 출력")
}

// MyExample의 확장함수 생성
// printMessage 멤버함수와 이름을 동일하게 했을때 멤버함수가 우선적으로 수행된다.
// 확장함수의 멤버함수와 동일한 시그니처는 멤버함수가 실행됨
fun MyExample.printMessage() = println("확장 출력")

// 시그니처가 다르면 확장함수 실행이 잘 됨
fun MyExample.printMessage(message:String) = println(message)

// MyExample 이 null일 가능성이 존재
// null인 경우와 아닌 경우 분기처리
fun MyExample?.printNullOrNotNull() {
    if (this == null) println("널인 경우에만 출력")
    else println("널이 아닌 경우에만 출력")
}

fun main() {
    var myExample: MyExample? = null
    // 함수에서 null 체크를 하고있다는걸 컴파일러가 알고있어서 오류가 안난다.
    myExample.printNullOrNotNull()

    myExample = MyExample()
    myExample.printNullOrNotNull()

    //MyExample().printMessage("확장 출력")

//    println("ABCD".first())
//
//    println("ABCD".addFirst('Z'))
}