package me.whiteship.chapter01.item06;

import java.util.regex.Pattern;

public class RegularExpression {

    private static final Pattern SPLIT_PATTERN = Pattern.compile(",");

    public static void main(String[] args) {
        long start = System.nanoTime();
        for (int j = 0; j < 10000; j++) {
            String name = "keesun,whiteship";
            name.split(","); // 문자열이 1개일때는 아래 코드와 성능이 비슷하다.
            name.split(";;;"); // 문자열이 여러개인 경우에는 Pattern Object를 만들고 체크하게된다.
//            SPLIT_PATTERN.split(name);
            // match, split, replaceAll, replaceFirst 는 첫번째 인자가 모두 Pattern 이므로 주의하자.
        }
        System.out.println(System.nanoTime() - start);
    }
}
