package me.whiteship.chapter01.item08.outerclass;

import java.lang.reflect.Field;

public class OuterClass {

    private void hi() {

    }

    // 무조건 static으로 해야한다. 만약 그렇지 않다면?의 예제임
    // 정적이 아닌 중첩클래스
    class InnerClass {

        public void hello() {
            // OuterClass에 대한 참조를 가지고있음
            OuterClass.this.hi(); // OuterClass.hi();
        }

    }

    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();
        // outerClass 인스턴스로 생성할 수 있다. 아주 종속적!
        InnerClass innerClass = outerClass.new InnerClass();

        System.out.println(innerClass);

        outerClass.printFiled();
    }

    private void printFiled() {
        Field[] declaredFields = InnerClass.class.getDeclaredFields();
        for(Field field : declaredFields) {
            System.out.println("field type:" + field.getType());
            System.out.println("field name:" + field.getName());
        }
    }
}
