package com.concurrency.chapter04.예제4_16_재구성기법_추가기능;

import java.util.*;

import net.jcip.annotations.*;

/**
 * ImprovedList
 *
 * Implementing put-if-absent using composition
 *
 * @author Brian Goetz and Tim Peierls
 *
 * 재구성 기법으로 putIfAbsent 메서드 구현!
 *
 * 재구성(composition)
 * List 클래스 인스턴스가 갖고있는 기능을 불러와 사용하고, 그에 덧붙여 putIfAbsent 메서드를 구현하고있다.
 * ImprovedList 생성 메서드에 한번 넘겨준 내용은 ImproveList를 통해서만 접근할 수 있고,
 * 원래 클래스를 직접 호출하여 사용하는 일은 없어야한다.
 *
 * ImprovedList 클래스는 그 자체를 락으로 사용하여 그 안에 포함되어있는 List와는 다른 수준에서
 * 락을 활용하고있다.
 * 이런 경우에는 ImprovedList 클래스를 락으로 사용해 동기화하기 때문에,
 * 내부의 List 클래스가 스레드 안전한지 아닌지는 중요하지 않고 신경 쓸 필요도 없다.
 * 이런 방법으로 동기화 기법을 한단계 더 사용한다면 전체적인 성능 측면에서는 약간 부정적인 영향이 있을수도 있지만,
 * ImprovedList 에서 사용한 동기화 기법은 이전에 사용했던 클라이언트 측 락 등의 방법보다 훨씬 안전하다.
 * ImprovedList 클래스에 들어있는 List 클래스가 외부로 공개되지 않는 한 스레드 안정성을 확보할 수 있다.
 */
@ThreadSafe
public class ImprovedList<T> implements List<T> {
    private final List<T> list; // 불변

    /**
     * PRE: list argument is thread-safe.
     */
    public ImprovedList(List<T> list) { this.list = list; }

    /**
     * 메서드 추가
     * @param x
     * @return
     */
    public synchronized boolean putIfAbsent(T x) {
        boolean contains = list.contains(x);
        if (contains)
            list.add(x);
        return !contains;
    }

    // Plain vanilla delegation for List methods.
    // Mutative methods must be synchronized to ensure atomicity of putIfAbsent.

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean contains(Object o) {
        return list.contains(o);
    }

    public Iterator<T> iterator() {
        return list.iterator();
    }

    public Object[] toArray() {
        return list.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    public synchronized boolean add(T e) {
        return list.add(e);
    }

    public synchronized boolean remove(Object o) {
        return list.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    public synchronized boolean addAll(Collection<? extends T> c) {
        return list.addAll(c);
    }

    public synchronized boolean addAll(int index, Collection<? extends T> c) {
        return list.addAll(index, c);
    }

    public synchronized boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    public synchronized boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    public boolean equals(Object o) {
        return list.equals(o);
    }

    public int hashCode() {
        return list.hashCode();
    }

    public T get(int index) {
        return list.get(index);
    }

    public T set(int index, T element) {
        return list.set(index, element);
    }

    public void add(int index, T element) {
        list.add(index, element);
    }

    public T remove(int index) {
        return list.remove(index);
    }

    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    public synchronized void clear() { list.clear(); }
}
