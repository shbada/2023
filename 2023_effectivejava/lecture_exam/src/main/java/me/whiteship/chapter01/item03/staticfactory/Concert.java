package me.whiteship.chapter01.item03.staticfactory;

import java.util.function.Supplier;

public class Concert {

    // 공급자 Supplier (FunctionalInterface)
    public void start(Supplier<Singer> singerSupplier) {
        Singer singer = singerSupplier.get();
        singer.sing();
    }

    public static void main(String[] args) {
        Concert concert = new Concert();
        // T get();
        // getInstance()를 전달가능 : 인스턴스를 리턴하기 때문에 인자로 전달 가능
        concert.start(Elvis::getInstance);
    }
}
