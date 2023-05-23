package me.whiteship.chapter02.item10;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// 코드 10-1 잘못된 코드 - 대칭성 위배! (54-55쪽)
public final class CaseInsensitiveString {
    private final String s;

    public CaseInsensitiveString(String s) {
        this.s = Objects.requireNonNull(s);
    }

//     대칭성 위배!
    @Override public boolean equals(Object o) { // 권장하지 않는 코드
        if (o instanceof CaseInsensitiveString)
            return s.equalsIgnoreCase(
                    // o를 CaseInsensitiveString 로 캐스팅하고 그 안의 s와 비교
                    ((CaseInsensitiveString) o).s);
        if (o instanceof String)  // 한 방향으로만 작동한다!
            return s.equalsIgnoreCase((String) o);
        return false;
    }

    // 문제 시연 (55쪽)
    public static void main(String[] args) {
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        CaseInsensitiveString cis2 = new CaseInsensitiveString("polish");
        String polish = "polish";
        System.out.println(cis.equals(polish)); // true
        System.out.println(polish.equals(cis)); // false (String 클래스의 equals)
        System.out.println(cis2.equals(cis)); // true

        List<CaseInsensitiveString> list = new ArrayList<>();
        list.add(cis);

        System.out.println(list.contains(polish)); // false
    }

    // 수정한 equals 메서드 (56쪽)
//    @Override public boolean equals(Object o) {
//    // 다른 타입을 지원하지말라
//        return o instanceof CaseInsensitiveString &&
//                ((CaseInsensitiveString) o).s.equalsIgnoreCase(s);
//    }
}
