package me.whiteship.chapter04.item17.memorymodel;

/**
 * JMM
 * final을 사용하면 안전하게 초기화할 수 있다.
 *
 * JMM
 * : 자바 메모리 모델은 JVM의 메모리 구조가 아니다.
 * : 적법한 프로그램을 실행 규칙
 * : 메모리 모델이 허용하는 범위내에서 프로그램을 어떻게 실행하든 구현체(JVM)의 자유다. (이 과정에서 실행 순서가 바뀔 수도 있다.)
 *
 */
public class Whiteship {

    private final int x;

    private final int y;

    public Whiteship() {
        this.x = 1;
        this.y = 2;
    }

    public static void main(String[] args) {
        // 메모리 모델이 허용하는 한해서 순서가 바뀔 수 있다.
        // 한 스레드 내에서 안전한지를 계산한다.
        // Object w = new Whiteship()
        // whiteship = w // 1)의 경우도 있고
        // w.x = 1
        // w.y = 2
        // whiteship = w // 2)의 경우도 있다.

        // 불완전한 초기화 (멀티스레드 환경에서)
        // // whiteship = w // x.y=2 셋팅 전에 이 인스턴스를 사용하는 스레드가 있을수도있다.

        // final 필드는 할당될때까지 기다린다.

        Whiteship whiteship = new Whiteship();
    }


}
