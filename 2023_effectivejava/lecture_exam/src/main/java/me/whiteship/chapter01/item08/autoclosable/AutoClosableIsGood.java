package me.whiteship.chapter01.item08.autoclosable;

import java.io.*;

/**
 * 권장방법
 */
/*
AutoCloseable 인터페이스

AutoCloseable의 하위 인터페이스: Closeable

차이가 뭘까?
Closeable은 IOException을 던진다.
file, db, socket 등 io와 관련이 있다면 Closeable를 구현하느것이 좋은 방법이다.
 */
public class AutoClosableIsGood implements Closeable {

    private BufferedReader reader;

    public AutoClosableIsGood(String path) {
        try {
            this.reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(path);
        }
    }

    /**
     * 이렇게 던지면 클라이언트에서 예외를 처리하라고 전가한다.
     */
//    @Override
//    public void close() throws IOException {
//        reader.close();
//    }

    /**
     * 아래 메서드를 구현해야한다.
     * close를 알아서 여기서 처리한다.
     *
     * 멱등성: 여러번 호출하더라도 같은 결과를 내보내야한다.
     */
    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            // RuntimeException 계열로 변환함
            // 구체적인 RuntimeException 이면 더 좋음
            // 해당 스레드는 종료됨
            throw new RuntimeException(e);
        }
    }
}
