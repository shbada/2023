package com.concurrency.chapter03.예제3_01_변수를_공유하지만_동기화하지않음;

/**
 * 변수를 공유하지만 동기화하지 않은 예제
 * 이런 코드는 금물!
 *
 * 가시성
 * 만약 단일 스레드만 사용하는 환경이라면 특정 변수에 값을 지정하고 다음번에 해당 변수의 값을 다시 읽어오면,
 * 이전에 저장해뒀던 바로 그 값을 가져올 수 있다.
 * 특정 변수에 값을 저장하거나 읽어내는 코드가 여러 스레드에서 실행된다면, 정상적인 값을 읽지 못할 수가 있다.
 *
 * 메모리상의 공유된 변수를 여러 스레드에서 서로 사용할 수 있게 하려면 반드시 동기화 기능을 구현해야한다.
 *
 */
public class NoVisibility {
    /* 공유 변수 */
    private static boolean ready;
    private static int number;

    /**
     * 읽기 스레드
     */
    private static class ReaderThread extends Thread {
        public void run() {
            /*
            동기화하지 않은 문제 발생
            1) ready 변수의 값을 읽기 스레드에서 영영 읽지 못할수도 있다.
            2) 읽기 스레드가 메인 스레드에서 number 변수에 지정한 값보다 ready 변수의 값을 먼저 읽어가는 상황도 가능하다. (reordering; 재배치)
             */
            while (!ready) // main 스레드에서 ready를 true로 변경할때까지
                Thread.yield();

            System.out.println(number); // 42가 출력되겠지?
            /* 42가 출력될 수도 있고, 0을 출력할 수도 있고, 영원히 값을 출력하지 못하고 ready 변수가 true가 될때까지 기다릴 수도 있다. */
        }
    }

    /**
     * main 스레드
     * @param args
     */
    public static void main(String[] args) {
        new ReaderThread().start(); // 읽기 스레드 시작
        number = 42; // 변수에 42 값을 넣고,
        ready = true; // 변수에 true 설정
    }
}
