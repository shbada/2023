import java.io.File
import java.io.FileWriter

fun main() {
    FileWriter("test.txt")
        // close() 자동 호출
        .use {
            it.write("Hello Kotlin")
        }

}