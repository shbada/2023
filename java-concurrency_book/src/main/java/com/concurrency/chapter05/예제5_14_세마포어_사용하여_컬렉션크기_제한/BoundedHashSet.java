package com.concurrency.chapter05.예제5_14_세마포어_사용하여_컬렉션크기_제한;

import java.util.*;
import java.util.concurrent.*;

/**
 카운팅 세마포어 (counting semaphore)
 = 특정 자원이나 특정 연산을 동시에 사용하거나 호출할 수 있는 스레드의 수를 제한하고자 할때 사용
 = 자원 풀(pool)이나 컬렉션의 크기에 제한을 두고자 할때 유용하다

 Semaphore 클래스
 - 가상의 퍼밋(permit)을 만들어, 내부 상태를 관리한다.
   semaphore를 생성할때 생성 메서드에 최초로 생성할 퍼밋의 수를 넘겨준다.
   외부 스레드는 퍼밋을 요청해 확보하거나, 이전제 확보한 퍼밋을 반납할 수 있다.

   acquired 메서드
 : 현재 사용할 수 있는 남은 퍼밋이 없는 경우, 남는 퍼밋이 생기거나 인터럽트가 걸리거나, 지정한 시간을 넘겨 타임아웃이 걸리기 전까지 대기한다.
   release 메서드
 : 확보했던 퍼밋을 다시 세모퍼어에게 반납하는 기능을 한다.

 세마포어는 데이터베이스 연결 풀과 같은 자원 풀에서 요긴하게 사용할 수 있다.
 1) 자원 풀을 만들때 모든 자원을 빌려주고 남아 있는 자원이 없을때 요청이 들어오는 경우에 단순하게 오류를 발생시키고 끝나버리는 정도의 풀은 쉽게 구현할 수 있다.
 -> 하지만 이 경우에 다른 스레드가 확보했던 객체를 반납받아 사용할 수 있을때까지 대기하도록 하는 방법이 더 낫다.
 -> 이럴때, 카운팅 메사포어를 만들면서 최초 퍼밋의 개수로 원하는 풀의 크기를 지정해보자.
    그리고 풀에서 자원을 할당받아 가려고 할때 먼저 acquired를 호출해 퍼밋을 확보하고, 다 사용한 자원을 반납하고 난 다음에는 release를 호출하여 반납하도록 한다.
 그러면 풀에 자원이 남아있지 않은 경우에 acquired 메서드가 대기 상태에 들어가기 때문에 객체가 반납될 때까지 자연스럽게 대기하게 된다.


 아래 예제와 같이, 세마포어를 사용하면 어떤 클래스라도 크기가 제한된 컬렉션 클래스로 활용할 수 있다.
 세마포어는 해당하는 컬렉션 클래스가 가질 수 있는 최대 크기에 해당하는 숫자로 초기화한다.
  add 메서드
 : 객체를 내부 데이터 구조에 추가하기 전에 acquired를 호출해 추가할 여유가 있는지 체크한다.
   만약 add 메서드가 내부 데이터 구조가 실제로 값을 추가하지 못했다면, 그 즉시 release 메서드를 호출해 세마포어에 퍼밋을 반납해야한다.

  remove 메서드
 : 객체를 삭제한 다음 퍼밋을 하나 반납해 남은 공간에 객체를 추가할 수 있도록 해준다.

 크기와 관련된 내용은 모두 BoundedHashSet에서 세마포어를 사용해 관리하기 때문에, 해당 클래스 내부에서 사용하는 Set은 크기가 제한되어 있다는 사실조차 알 필요가 없다.
 */

/**
 * BoundedHashSet
 * <p/>
 * Using Semaphore to bound a collection
 *
 * @author Brian Goetz and Tim Peierls
 */
public class BoundedHashSet <T> {
    private final Set<T> set;
    private final Semaphore sem;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        sem = new Semaphore(bound);
    }

    /**
     * 객체를 내부 데이터 구조에 추가하기 전에 acquired를 호출해 추가할 여유가 있는지 체크한다.
     * 만약 add 메서드가 내부 데이터 구조가 실제로 값을 추가하지 못했다면, 그 즉시 release 메서드를 호출해 세마포어에 퍼밋을 반납해야한다.
     */
    public boolean add(T o) throws InterruptedException {
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded)
                sem.release();
        }
    }

    /**
     * 객체를 삭제한 다음 퍼밋을 하나 반납해 남은 공간에 객체를 추가할 수 있도록 해준다.
     */
    public boolean remove(Object o) {
        boolean wasRemoved = set.remove(o);
        if (wasRemoved)
            sem.release();
        return wasRemoved;
    }
}
