package com.fastcampus.issueservice.domain.enums

enum class IssueType {
    // 버그, 업무
    BUG, TASK;

    companion object {
        /*
            operator : 연산자
            operator invoke 함수를 정의했으므로, 생성자를 사용하듯이 invoke 함수 사용이 가능하다.
            IssueType("BUG") = IssueType.invoke("BUG")
         */
        operator fun invoke(type:String) = valueOf(type.uppercase())

    }
}
