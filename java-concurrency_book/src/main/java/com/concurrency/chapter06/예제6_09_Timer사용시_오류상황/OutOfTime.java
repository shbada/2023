package com.concurrency.chapter06.예제6_09_Timer사용시_오류상황;

import java.util.*;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 [지연 작업, 주기적 작업]
 Timer 클래스
 - 특정 시간 이후에 원하는 작업을 실행하는 지연 작업이나 주기적인 작업을 실행할 수 있다.

 (Timer 클래스 대안)
 > ScheduledThreadPoolExecutor
 - ScheduledThreadPoolExecutor를 생성하려면 직접 ScheduledThreadPoolExecutor 클래스의 생성 메서드를 호출하거나,
   newScheduledThreadPool 팩토리 메서드를 사용해 생성하는 방법이 있다.

 Timer 클래스는 등록된 작업을 실행시키는 스레드를 하나만 생성해 사용한다.
 만약 Timer에 등록된 특정 작업이 너무 오래 실행된다면 등록된 다른 TimerTask 작업이 예정된 시각에 실행되지 못할 가능성이 높다.
 ScheduledThreadPoolExecutor를 사용하면 지연 작업과 주기적 작업마다 여러개의 스레드를 할당해 작업을 실행하느라
 각자의 실행 예정 시각을 벗어나는 일이 없도록 조절해준다.

 Timer 클래스는 TimerTask가 동작하던 도중에 예쌍치못한 Exception을 던져버리는 경우에 예측하지 못한 상태로 넘어갈 수 있다.
 Timer 클래스는 오류가 발생해 스레드가 종료된 상황에서도 자동으로 새로운 스레드를 생성해주지 않는다.
 이런 상황에 다다르면 해당 Timer에 등록되어있떤 모든 작업이 취소된 상황이라고 간주해야하며,
 그동안 등록됐던 TimerTask는 전혀 실행되지 않고 물론 새로운 작업을 등록할 수도 없다.

 [DelayQueue]
 큐 내부에 여러개의 Delayed 객체로 작업을 관리하며,
 각각의 Delayed 객체는 저마다의 시각을 갖고있다.
 DelayQueue를 사용하면 Delayed 내부의 시각이 만료된 객체만 take 메서드로 가져갈 수 있다.
 DelayQueue에서 뽑아내는 객체는 객체마다 지정되어 있던 시각 순서로 정렬되어 뽑아진다.
 */

/**
 * OutOfTime
 * <p/>
 * Class illustrating confusing Timer behavior
 *
 * @author Brian Goetz and Tim Peierls
 */

public class OutOfTime {
    /**
     * Timer 클래스가 내부적으로 어떻게 꼬이는지 보여주는 예제
     * 6초동안 실행하다가 종료될 것이라고 예상하겠지만,
     * 실제로는 1초만 실행되다가 "Timer already cancelled" 라는 메시지의 IllegalStateException을 띄우면서 바로 종료된다.
     *
     * ScheduledThreadPoolExecutor는 이와 같은 오류를 안정적으로 처리해주기 때문에 Timer을 사용할 필요는 없다.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        SECONDS.sleep(1);
        timer.schedule(new ThrowTask(), 1);
        SECONDS.sleep(5);
    }

    static class ThrowTask extends TimerTask {
        public void run() {
            throw new RuntimeException();
        }
    }
}
