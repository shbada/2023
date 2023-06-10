package me.whiteship.chapter02.item14;

import java.math.BigDecimal;

public class Test {
    public static void main(String[] args) {
        // p89, compareTo가 0이라면 equals는 true여야 한다. (아닐 수도 있고..)
        BigDecimal oneZero = new BigDecimal("1.0");
        BigDecimal oneZeroZero = new BigDecimal("1.00");
        System.out.println(oneZero.compareTo(oneZeroZero)); // 0 / Tree, TreeMap
        System.out.println(oneZero.equals(oneZeroZero)); // false / 순서가 없는 콜렉션
    }
}
