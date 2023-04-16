package me.whiteship.chapter01.item03.enumtype;

import java.lang.reflect.Constructor;

public class EnumElvisReflection {

    public static void main(String[] args) {
        try {
            /**
             * 오류발생
             * java.lang.NoSuchMethodException: me.whiteship.chapter01.item03.enumtype.Elvis.<init>()
             */
            Constructor<Elvis> declaredConstructor = Elvis.class.getDeclaredConstructor();
            System.out.println(declaredConstructor);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
