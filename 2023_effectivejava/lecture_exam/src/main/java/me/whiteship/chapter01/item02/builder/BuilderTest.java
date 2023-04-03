package me.whiteship.chapter01.item02.builder;

public class BuilderTest {

    public static void main(String[] args) {
        // 필수 속성을 설정할 수 있다. (객체를 안전하게, 반드시 받아오게끔 강제할 수 있음)
        // 옵션 속성은 설정해도되고 안해도된다.
        // 반드시 빌더패턴을 사용하지 말고, immutable 하게 만들고 싶을때 사용하자.
        new NutritionFacts.Builder(10, 10)
                .calories(10)
                .build();
    }
}
