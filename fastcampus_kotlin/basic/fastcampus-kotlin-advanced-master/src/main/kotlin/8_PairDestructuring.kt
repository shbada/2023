// f((1, 3)) = 1 + 3 = 4
// f(1, 3) = 1 + 3 = 4

//data class Tuple(val a : Int, val b: Int)

fun plus(pair: Pair<Int, Int>) = pair.first + pair.second

fun main() {
    //println(plus(1,3))
    val plus = plus(Pair(1, 3))
    println(plus)

    val pair = Pair("A", 1) // 불변
    val newPair = pair.copy(first = "B") // 새로운 Pair을 생성
    println(newPair)

    val second = newPair.component2() // second 값 가져오기
    println(second)

    val list = newPair.toList()
    println(list)

    /* 3개 요소 (4개 이상부터는 지원하지 않음, Collection 사용하면 됨) */
    val triple = Triple("A","B","C")
    println(triple) // 출력

    triple.first
    triple.second
    val newTriple = triple.copy(third = "D") // third 값 변경한 새로운 Triple 생성

    println(newTriple)

    println(newTriple.component3())

    /* 구조분해 할당 */
    val (a: String, b: String, c: String) = newTriple
    println("$a, $b, $c")

    val list3: List<String> = newTriple.toList()
    val (a1, a2, a3) = list3
    println("$a1, $a2, $a3")

    list3.component1()
    list3.component2()
    list3.component3()
//    list3.component4()
//    list3.component5()

    val map = mutableMapOf(Pair("이상훈", "개발자"))
    for ( (key, value) in map ) {
        println("${key}의 직업은 $value")
    }










}