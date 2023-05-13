package me.whiteship.chapter01.item08.outerclass;

import java.lang.reflect.Field;

public class LambdaExample {

    private int value = 10;

    /*
        람다 안에서 바깥 클래스를 참조하면(value랑 같이),
        람다 안에 LambdaExample 참조가 들어가게된다.

        (참고) value가 static이였다면 참조하지 않았을것임
     */
    private Runnable instanceLambda = () -> {
        System.out.println(value);
    };

    public static void main(String[] args) {
        LambdaExample example = new LambdaExample();
        Field[] declaredFields = example.instanceLambda.getClass().getDeclaredFields();

        // LambdaExample 를 참조하게됨을 확인할 수 있다.
        for (Field field : declaredFields) {
            System.out.println("field type: " + field.getType());
            System.out.println("field name: " + field.getName());
        }
    }

}
