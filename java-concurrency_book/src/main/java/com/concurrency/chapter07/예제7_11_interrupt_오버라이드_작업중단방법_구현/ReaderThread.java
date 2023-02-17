package com.concurrency.chapter07.예제7_11_interrupt_오버라이드_작업중단방법_구현;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * [ReaderThread]
 * 표준적이지 않은 방법으로 작업을 중단하는 기능을 속으로 감춰버리는 방법을 소개
 * - ReaderThread는 소켓 하나를 연결해서 사용하는데 소켓으로 들어오는 내용을 동기적으로 읽어들이고,
 *   읽은 내용을 모두 ProcessBuffer 메서드에 넘긴다.
 * - ReaderThread 클래스는 사용자가 접속해 연결되어있는 소켓을 닫아버리거나 프로그램을 종료시킬 수 있도록,
 *   interrupt 메서드를 오버라이드해 인터럽트를 요청하는 표준적인 방법과 함께 추가적으로 열려있는 소켓을 닫는다.
 * - ReaderThread 클래스에 인터럽트를 걸었을때 read 메서드에서 대기중인 상태이거나 기타 인터럽트에 응답할 수 있는,
 *   블로킹 메서드에 멈춰있을 때에도 작업을 중단시킬 수 있다.
 *
 * JAVA6 버전의 ThreadPoolExecutor 클래스에 newTaskFor 메서드 추가
 * - ExecutorServce 클래스에 Callable 인스턴스를 등록할때 submit 메서드를 호출하면 그 결과로 해당하는 작업을 취소시킬 수 있는 Future 객체를 받아온다.
 * - newTaskFor 메서드 역시 등록된 작업을 나타내는 Future 객체를 리턴해주는데, 이전과는 다른 RunnableFuture 객체를 리턴한다.
 * - RunnableFuture 인터페이스는 Future와 Runnable 인터페이스를 모두 상속받으며,
 *   FutureTask는 자바 5에서 Future를 구현했었지만 자바 6에서는 RunnableFuture를 구현한다.
 *
 * Future.cancel() 메서드를 오버라이드하면 작업 중단 과정을 원하는대로 변경할 수 있다.
 */

/**
 * ReaderThread
 * <p/>
 * Encapsulating nonstandard cancellation in a Thread by overriding interrupt
 *
 * @author Brian Goetz and Tim Peierls
 */
public class ReaderThread extends Thread {
    private static final int BUFSZ = 512;
    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    /**
     * interrupt 오버라이드
     */
    @Override
    public void interrupt() {
        try {
            socket.close();
        } catch (IOException ignored) {
        } finally {
            /** interrupt() */
            super.interrupt();
        }
    }

    public void run() {
        try {
            byte[] buf = new byte[BUFSZ];
            while (true) {
                int count = in.read(buf);
                if (count < 0)
                    break;
                else if (count > 0)
                    processBuffer(buf, count);
            }
        } catch (IOException e) { /* Allow thread to exit */
        }
    }

    public void processBuffer(byte[] buf, int count) {
    }
}

