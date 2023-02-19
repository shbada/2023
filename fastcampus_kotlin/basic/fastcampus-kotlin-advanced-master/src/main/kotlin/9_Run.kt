class DatabaseClient {
    var url: String? = null
    var username: String? = null
    var password: String? = null

    // DB에 접속하고 Boolean 결과를 반환
    fun connect(): Boolean {
        println("DB 접속 중 ...")
        Thread.sleep(1000)
        println("DB 접속 완료")
        return true
    }
}

fun main() {

//    val config = DatabaseClient()
//    config.url = "localhost:3306"
//    config.username = "mysql"
//    config.password = "1234"
//    val connected = config.connect()

    // run 안에서 수신자 객체 참조는 this로 함 (생략도 가능)
    // 변수 중복 참조를 생략할 수 있다는 장점 (위에서 config.xx가 반복됨)
    val connected: Boolean = DatabaseClient().run {
        url = "localhost:3306"
        username = "mysql"
        this.password = "1234"
        connect() // 자동 return
    }
    println(connected)


   val result: Boolean =  with(DatabaseClient()) {
        url = "localhost:3306"
        username = "mysql"
        password = "1234"
        connect()
    }
    println(result)

}