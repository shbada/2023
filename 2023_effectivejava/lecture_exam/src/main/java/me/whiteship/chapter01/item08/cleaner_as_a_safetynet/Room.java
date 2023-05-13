package me.whiteship.chapter01.item08.cleaner_as_a_safetynet;

import java.lang.ref.Cleaner;

// 코드 8-1 cleaner를 안전망으로 활용하는 AutoCloseable 클래스 (44쪽)
public class Room implements AutoCloseable {
    private static final Cleaner cleaner = Cleaner.create();

    // Room 객체가 gc 대상이 되고 해제할때 수행됨
    // 청소가 필요한 자원. 절대 Room을 참조해서는 안 된다!
    private static class State implements Runnable {
        int numJunkPiles; // Number of junk piles in this room

        State(int numJunkPiles) {
            this.numJunkPiles = numJunkPiles;
        }

        // close 메서드나 cleaner가 호출한다.
        @Override public void run() {
            System.out.println("Cleaning room");
            numJunkPiles = 0; // cleaning 작업이라고 생각하자.
        }
    }

    // 방의 상태. cleanable과 공유한다.
    private final State state;

    // cleanable 객체. 수거 대상이 되면 방을 청소한다.
    private final Cleaner.Cleanable cleanable;

    public Room(int numJunkPiles) {
        state = new State(numJunkPiles);
        // 클리너 등록해놓기
        // gc 대상이 되면 state 작업이 수행되도록 한다.
        cleanable = cleaner.register(this, state);
    }

    // try~with~resources 사용시 호출될 예정
    @Override public void close() {
        cleanable.clean();
    }
}
