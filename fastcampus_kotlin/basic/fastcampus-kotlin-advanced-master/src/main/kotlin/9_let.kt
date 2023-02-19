fun main() {

    val str: String? = "안녕"

    // str 이 null이 아닌 경우에 동작한다.
    val result: Int? = str?.let {
        println(it) // it = str

        val abc: String? = "abc"
        val def: String? = "def"
        if (!abc.isNullOrEmpty() && !def.isNullOrEmpty()) {
            println("abcdef가 null 아님")
        }

        // return 키워드 없이도 return 값으로 셋팅된다.
        1234
    }
    println(result)


//    val this: String? = null
//    val it : String? = null


    val hello = "hello"
    val hi = "hi"

    hello.let { a : String ->

        //println(a.length)

        hi.let{ b ->
            println(a.length)
            println(b.length)
        }
    }

}