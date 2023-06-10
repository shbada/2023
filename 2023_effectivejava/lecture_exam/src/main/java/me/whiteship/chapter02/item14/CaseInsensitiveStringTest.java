package me.whiteship.chapter02.item14;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

// 코드 14-1 객체 참조 필드가 하나뿐인 비교자 (90쪽)
public final class CaseInsensitiveStringTest
        implements Comparable<CaseInsensitiveStringTest> {
    private final String s;

    public CaseInsensitiveStringTest(String s) {
        this.s = Objects.requireNonNull(s);
    }

    // 수정된 equals 메서드 (56쪽)
    @Override public boolean equals(Object o) {
        return o instanceof CaseInsensitiveStringTest &&
                ((CaseInsensitiveStringTest) o).s.equalsIgnoreCase(s);
    }

    @Override public int hashCode() {
        return s.hashCode();
    }

    @Override public String toString() {
        return s;
    }

    // 자바가 제공하는 비교자를 사용해 클래스를 비교한다.
    public int compareTo(CaseInsensitiveStringTest cis) {
        return String.CASE_INSENSITIVE_ORDER.compare(s, cis.s);
    }

    public static void main(String[] args) {
        Set<CaseInsensitiveStringTest> s = new TreeSet<>();
        s.add(new CaseInsensitiveStringTest("다라마"));
        s.add(new CaseInsensitiveStringTest("가나다"));
        s.add(new CaseInsensitiveStringTest("나다라"));
        System.out.println(s);
    }
}
