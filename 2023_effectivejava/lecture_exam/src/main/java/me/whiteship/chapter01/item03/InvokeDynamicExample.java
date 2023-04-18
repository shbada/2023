package me.whiteship.chapter01.item03;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class InvokeDynamicExample {
    public static void main(String [] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        // String.format(String format, Object... args)
        MethodType type = MethodType.methodType(String.class, String.class, Object[].class);
        MethodHandle mh = lookup.findStatic(String.class, "format", type);

        // String.format("Hello, %s!", "World");
        String s = (String) mh.invokeExact("Hello, %s!", new Object[]{"World"});
        System.out.println(s); // Hello, World!
    }
}
