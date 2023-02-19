fun main() {

    // return 타입이 Context 객체에 대한 타입 그대로 (DatabaseClient)
    DatabaseClient().apply {
        url = "localhost:3306"
        username = "mysql"
        this.password = "1234"
    }.connect()
        .run { println(this) } // this : connect() 함수의 반환결과


}