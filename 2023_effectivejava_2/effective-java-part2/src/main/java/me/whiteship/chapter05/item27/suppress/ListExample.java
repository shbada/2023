package me.whiteship.chapter05.item27.suppress;

import java.util.Arrays;

public class ListExample {

    private int size;

    Object[] elements;

    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            /**
             * 이 애노테이션을 왜 여기서 선언했는지..
             */
            @SuppressWarnings("unchecked")
            T[] result = (T[]) Arrays.copyOf(elements, size, a.getClass());
            return result;
        }
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

}
