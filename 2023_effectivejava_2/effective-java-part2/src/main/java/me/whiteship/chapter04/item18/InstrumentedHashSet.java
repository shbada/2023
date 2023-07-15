package me.whiteship.chapter04.item18;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

// 코드 18-1 잘못된 예 - 상속을 잘못 사용했다! (114쪽)
public class InstrumentedHashSet<E> extends HashSet<E> {
    // 추가된 원소의 수
    private int addCount = 0;

    public InstrumentedHashSet() {
    }

    public InstrumentedHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        // 내부 구현에서 add()를 호출함
        // 만약 상위클래스의 내부 구현이 바뀐다면? 거기에 따------라서 하위클래스의 구현도 바뀌어야한다. (캡슐화가 제대로 안되어있어서)
        return super.addAll(c);
    }

    // 상위 클래스에 메서드가 신규로 추가되었다해서, 이걸 구현해야한다는걸 하위클래스가 감지하기 힘들다.

    public int getAddCount() {
        return addCount;
    }

    // 상위클래스의 접근지시자부터 더 넓은 접근지시자여야함
    // 상위클래스에서 아래 메서드명(getAddCount)으로 return type int로 생성하면 오류날 수 있음
//    private int getAddCount() {
//        return addCount;
//    }

    public static void main(String[] args) {
        InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
        s.addAll(List.of("틱", "탁탁", "펑"));
        System.out.println(s.getAddCount());
    }
}
