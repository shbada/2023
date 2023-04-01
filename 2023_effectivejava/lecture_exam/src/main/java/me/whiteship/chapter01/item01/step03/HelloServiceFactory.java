package me.whiteship.chapter01.item01.step03;

public class HelloServiceFactory {
    public static HelloService of(String lang) {
        if ("ko".equals(lang)) {
            return new KoreanHelloService();
        } else {
            return new EnglishHelloService();
        }
    }

    public static void main(String[] args) {
        HelloService ko = HelloServiceFactory.of("ko");
        HelloService eng = HelloServiceFactory.of("eng");
    }
}
