package com.concurrency.chapter03.예제3_15_객체_올바르게_공개하지않으면_문제발생;

/**
 * Holder
 * <p/>
 * Class at risk of failure if not properly published
 *
 * @author Brian Goetz and Tim Peierls
 *
 * Holder 객체를 다른 스레드가 사용할 수 있도록 코드를 작성하면서 적절한 동기화 방법을 적용하지 않았으므로,
 * Holder 클래스는 올바르게 공개되지 않았다.
 *
 * 1) Holder 변수에 스테일 상태가 발생할 수 있다. Holder 변수에 값을 지정한 이후에도 null 값이 지정되어 있거나
 * 예전에 사용하던 참조가 들어가 있을 수도 있다.
 * 2) 다른 스레드는 모두 holder 변수에서 정상적인 참조 값을 가져갈 수 있지만 Holder 클래스의 입장에서는 스테일 상태에 빠질 수 있다.
 * 특정 스레드에서 변수 값을 처음 읽을때는 스테일 값을 읽어가고 그 다음에 사용할때는 정상적인 값을 가져갈수도 있기 때문에,
 * assertSanity 메소드를 실행하면 AssertionError가 발생하기도 한다.
 *
 * [객체를 안전하게 공개하는 방법]
 * 1) 객체에 대한 참조를 static 메서드에서 초기화시킨다.
 * 2) 객체에 대한 참조를 volatile 변수 또는 AtomicReference 클래스에 보관한다.
 * 3) 객체에 대한 참조를 올바르게 생성된 클래스 내부에 final 변수에 보관한다.
 * 4) 락을 사용해 올바르게 막혀있는 변수에 객체에 대한 참조를 보관한다.
 *
 * static 초기화 방법은 JVM에서 클래스를 초기화하는 시점에 모두 진행된다.
 * JVM 내부에 동기화가 맞춰져있기 때문에 아래 코드는 스레드 안전하게 공개된다.
 * public static Holder holder = new Holder(42);
 */
public class Holder {
    private int n;

    public Holder(int n) {
        this.n = n;
    }

    public void assertSanity() {
        if (n != n)
            throw new AssertionError("This statement is false.");
    }
}

