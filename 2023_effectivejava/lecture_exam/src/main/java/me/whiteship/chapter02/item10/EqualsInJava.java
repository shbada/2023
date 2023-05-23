package me.whiteship.chapter02.item10;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;

public class EqualsInJava extends Object {

    public static void main(String[] args) throws MalformedURLException {
        long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        Date date = new Date(time);

        // 대칭성 위배! P60
        // Date 입장에서는 true, TimeStamp 입장에서는 false
        System.out.println(date.equals(timestamp)); // true (time만 체크)
        System.out.println(timestamp.equals(date)); //  false (Timestamp 에 별도 추가된 필드까지 체크)

        // 일관성 위배 가능성 있음. P61
        URL google1 = new URL("https", "about.google", "/products/");
        URL google2 = new URL("https", "about.google", "/products/");
        System.out.println(google1.equals(google2)); // true

        // 최종적으로 도메인의 ip를 꺼내서 비교한다.
        // ip가 다르면 다를 수도 있다. (virtual hosting 의 경우 다를 수 있음)
        URL url1 = new URL("http://example.com");
        URL url2 = new URL("http://192.0.2.1");

        System.out.println(url1.equals(url2));  // false
    }
}
