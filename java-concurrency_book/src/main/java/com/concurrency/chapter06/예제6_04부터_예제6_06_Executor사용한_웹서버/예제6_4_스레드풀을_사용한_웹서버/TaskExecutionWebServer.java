package com.concurrency.chapter06.예제6_04부터_예제6_06_Executor사용한_웹서버.예제6_4_스레드풀을_사용한_웹서버;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 요청처리 작업을 등록하는 작업과 실제로 처리 기능을 실행하는 부분이 Executor를 사이에 두고 분리되어 있고,
 Executor를 다른 방법으로 구현한 클래스를 사용하면 비슷한 기능에 다른 특성으로 동작하도록 손쉽게 변경할 수 있다.

 Executor을 사용하면 Executor의 설정을 변경하는 것만으로도 서버의 동작 특성을 쉽게 변경할 수 있다.
 Executro에 필요한 설정은 대부분 초기에 한번 지정하는 것이 보통이며, 처음 실행하는 시점에 설정 값을 지정하는 편이 좋다.
 하지만 Executor를 사용해 작업을 등록하는 코드는 전체 프로그램의 여기저기에 퍼져있는 경우가 많기 때문에 한눈에 보기 어렵다.

 작업을 등록하는 부분과 실행하는 부분을 서로 분리시켜두면, 특정 작업을 실행하고자 할때,
 실행 정책(execution policy)을 언제든지 쉽게 변경할 수 있다는 장점이 있다.
 실행 정책은 '무엇을, 어디에서, 언제, 어떻게' 실행하는지를 지정한다.
 - 작업을 어느 스레드에서 실행할 것인가?
 - 작업을 어떤 순서로 실행할 것인가?
 - 동시에 몇개의 작업을 병렬로 실행할 것인가?
 - 최대 몇개까지의 작업이 큐에서 실행을 대기할 수 있게할 것인가?
 - 시스템에 부하가 많이 걸려서 작업을 거절해야하는 경우, 어떤 작업을 희생양으로 삼아야 할것이며, 작업을 요청한 프로그램에 어떻게 알려야할 것인가?
 - 작업을 실행하기 직전이나 실행한 직후에 어떤 동작이 있어야 하는가?

 실행 정책은 일종의 자원 관리 도구라고도 할 수 있다.
 가장 최적화된 실행 정책을 찾으려면 하드웨어나 소프트웨어적인 자원을 얼마나 확보할 수 있는지 확인해야하고,
 더불어 애플리케이션의 성능과 반응 속도가 요구사항에 얼마만큼 명시되어 있는지도 알아야한다.
 병렬로 실행되는 스레드의 수를 제한한다면 아마도 애플리케이션에서 자원이 모자라는 상황에 다다르거나 제한된 자원을 서요 사용하기 위해 각 작업이 경쟁하느라
 애플리케이션의 성능이 떨어지는 일은 보기 어려울 것이다.
 실행 정책과 작업 등록 부분을 명확하게 분리시켜두면 애플리케이션을 실제 상황에 적용하려 할때 설치할 하드웨어와 기타 자원의 양에 따라 적절한 실행 정책을 임의로 지정할 수 있다.

 아래 예쩨에서는 제한된 개수의 스레드로 동작하는 Executor을 사용했다.
 처리할 작업을 execute 메서드로 등록해두면, Executor 내부의 큐에 쌓여있는 작업을 하나씩 뽑아내 처리하게 되어있다.
 (작업별로 스레드를 생성하는 전략(thread-per-task))
 */
/**
 * TaskExecutionWebServer
 * <p/>
 * Web server using a thread pool
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TaskExecutionWebServer {
    /** 100개의 고정된 스레드를 확보하는 풀 사용 */
    private static final int NTHREADS = 100;
    private static final Executor exec
            = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                public void run() {
                    handleRequest(connection);
                }
            };
            exec.execute(task);
        }
    }

    private static void handleRequest(Socket connection) {
        // request-handling logic here
    }
}

