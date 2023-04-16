package me.whiteship.chapter01.item01.step07_reflection;

import me.whiteship.chapter01.item01.step03.HelloService;
//import me.whiteship.hello.ChineseHelloService;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.ServiceLoader;

public class HelloServiceFactory {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> aClass = Class.forName("me.whiteship.hello.ChineseHelloService");
        Constructor<?> constructor = aClass.getConstructor();
        HelloService helloService = (HelloService) constructor.newInstance();
        System.out.println(helloService.hello());
    }

}
