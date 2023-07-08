package me.whiteship.chapter05.item29.object;

import me.whiteship.chapter05.item29.EmptyStackException;

import java.util.Arrays;
import java.util.List;

// Object를 이용한 제네릭 스택 (170-174쪽)
public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null; // 다 쓴 참조 해제
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size + 1);
    }

    // 코드 29-5 제네릭 Stack을 사용하는 맛보기 프로그램 (174쪽)
    public static void main(String[] args) {
        Stack stack = new Stack();
        for (String arg : List.of("a", "b", "c"))
            stack.push(arg);
        while (!stack.isEmpty())
            System.out.println(((String)stack.pop()).toUpperCase());
    }
}
