sealed class Developer {

    abstract val name: String
    abstract fun code(language: String)

}

data class BackendDeveloper(override val name: String) : Developer() {

    override fun code(language: String) {
        println("저는 백엔드 개발자입니다 ${language}를 사용합니다")
    }
}

data class FrontendDeveloper(override val name: String) : Developer() {

    override fun code(language: String) {
        println("저는 프론트엔드 개발자입니다 ${language}를 사용합니다")
    }
}

object OtherDeveloper : Developer() {

    override val name: String = "익명"

    override fun code(language: String) {
        TODO("Not yet implemented")
    }

}

data class AndroidDeveloper(override val name: String) : Developer() {

    override fun code(language: String) {
        println("저는 안드로이드 개발자입니다 ${language}를 사용합니다")
    }
}


data class IosDeveloper(override val name: String) : Developer() {

    override fun code(language: String) {
        println("저는 Ios 개발자입니다 ${language}를 사용합니다")
    }
}

object DeveloperPool {
    val pool = mutableMapOf<String, Developer>()

    // 컴파일러는 Developer 구현 클래스가 무엇인지를 모름
    // else가 없으면 when절에 컴파일 오류남
    // Developer 를 sealed Class로 정의하면 else문 생략가능
    // 같은 패키지/하위 모듈에 있는 경우에만 sealed class의 하위클래스가 될 수 있다
    fun add(developer: Developer) = when(developer) {
        is BackendDeveloper -> pool[developer.name] = developer
        is FrontendDeveloper -> pool[developer.name] = developer
        is AndroidDeveloper ->  pool[developer.name] = developer
        is IosDeveloper ->  pool[developer.name] = developer
        is OtherDeveloper -> println("지원하지않는 개발자종류입니다")
    }

    fun get(name: String) = pool[name]
}

fun main() {
    val backendDeveloper = BackendDeveloper(name="토니")
    DeveloperPool.add(backendDeveloper)

    val frontendDeveloper = FrontendDeveloper(name="카즈야")
    DeveloperPool.add(frontendDeveloper)

    val androidDeveloper = AndroidDeveloper(name="안드로")
    DeveloperPool.add(androidDeveloper)

    println(DeveloperPool.get("토니"))
    println(DeveloperPool.get("카즈야"))
    println(DeveloperPool.get("안드로"))


}


