package com.concurrency.chapter04.예제4_02_한정기법_스레드안정성확보;

import java.util.*;

import net.jcip.annotations.*;

/**
 * PersonSet
 * <p/>
 * Using confinement to ensure thread safety
 *
 * @author Brian Goetz and Tim Peierls
 */

/**
 * 내부에 정의되어있는 변수가 스레드 안전한 객체가 아니더라도, 여러가지 한정기법과 락을 활용하여
 * 전체적으로 해당 클래스를 스레드에 안전하게 구현할 수 있다.
 */
@ThreadSafe
public class PersonSet {
    /* HashSet 자체는 스레드 안전한 객체가 아니다. private 이므로 외부에 유출되지 않는다. */
    /*
    Person 객체를 사용했다.
    Person 객체가 갖고있는 데이터가 변경될 수 있는 정보라면 PersonSet에서 Person 객체를 사용하고자할때
    적절한 동기화 방법을 적용해야한다.
    가장 좋은 방법은 Person 객체 자체에서 스레드 안정성을 확보하는 방법이다.
    다른 방법은 Person 객체를 사용할때마다 여러가지 동기화 기법을 사용하는 방법인데, 권장하지는 않는다.

    인스턴스 한정 기법은 클래스를 구현할때 스레드 안정성을 확보할 수 있는 가장 쉬운 방법이다.

     */
    @GuardedBy("this") private final Set<Person> mySet = new HashSet<Person>();

    /**
     * mySet 변수를 사용할 수 있는 방법은 addPerson, containsPerson 메서드를 호출하는 방법 뿐이다.
     * 두 메서드 모두 synchronized 키워드를 통해 PersonSet 객체에 락이 걸려있다.
     * 내부에서 사용하는 모든 상태 정보가 락으로 막혀 있기 때문에 PersonSet 객체는 스레드 안정성을 확보했다.
     * @param p
     */
    public synchronized void addPerson(Person p) {
        mySet.add(p);
    }

    public synchronized boolean containsPerson(Person p) {
        return mySet.contains(p);
    }

    interface Person {
    }
}
