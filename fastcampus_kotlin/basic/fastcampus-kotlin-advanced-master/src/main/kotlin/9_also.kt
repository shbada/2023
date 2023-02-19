class User(val name: String, val password: String) {

    fun validate() {
        if (name.isNotEmpty() && password.isNotEmpty()) {
            println("검증 성공!")
        } else {
            println("검증 실패!")
        }
    }

    fun printName() = println(name)

}

fun main() {

    User(name = "tony", password = "1234").also {
        // it을 사용해서 간결하게 사용 가능
        it.validate()
        it.printName()
    }
}