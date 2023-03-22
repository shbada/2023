package com.concurrency.chapter13.예제13_01_Lock_인터페이스;

import java.util.concurrent.locks.Lock;

/**
 * Lock 인터페이스는 여러 가지 락 관련 기능에 대한 추상 메서드를 정의하고 있다.
 * Lock 인터페이스는 암묵적인 락과 달리 조건 없는 락, 폴링 락, 타임아웃이 있는 락, 락 확보 대기 상태에 인터럽트를 걸 수 있는 방법 등이 포함되어있다.
 * 락을 확보하고 해제하는 모든 작업이 명시적이다.
 *
 * [ReentrantLock 클래스]
 * Lock 인터페이스를 구현하며, synchronized 구문과 동일한 메모리 가시성과 상호 배제 기능을 제공한다.
 * ReentrantLock 확보한다는 것은 synchronized 블록에 진입하는 것과 동일한 효과를 갖고있고,
 * ReentrantLock 해제한다는 것은 synchronizd 블록에서 빠져나가는 것과 동일한 효과를 가진다.
 * ReentrantLock 역시 syncrhonized 키워드와 동일하게 재진입이 가능하도록 허용하고 있다.
 * 락을 제대로 확보하기 어려운 시점에 synchronized 블록을 사용할때보다 훨씬 능동적으로 대처할 수 있다.
 */
public class LockTest {
//    Lock
}
