package me.whiteship.chapter01.item03.field;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

// 생성자로 여러 인스턴스 만들기
public class ElvisReflection {

    public static void main(String[] args) {
        try {
            /*
                getConstructor() : public 생성자
                getDeclaredConstructor : 선언되어있는 생성자
             */
            Constructor<Elvis> defaultConstructor = Elvis.class.getDeclaredConstructor();

            /* private 에 접근 가능 */
            defaultConstructor.setAccessible(true);

            Elvis elvis1 = defaultConstructor.newInstance();
            Elvis elvis2 = defaultConstructor.newInstance();

            Elvis.INSTANCE.sing();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
