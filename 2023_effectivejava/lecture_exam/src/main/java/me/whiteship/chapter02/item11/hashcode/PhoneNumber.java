package me.whiteship.chapter02.item11.hashcode;

import me.whiteship.chapter02.item10.Point;

import java.util.*;

// equals를 재정의하면 hashCode로 재정의해야 함을 보여준다. (70-71쪽)
public final class PhoneNumber {
    private final short areaCode, prefix, lineNum;

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "area code");
        this.prefix   = rangeCheck(prefix,   999, "prefix");
        this.lineNum  = rangeCheck(lineNum, 9999, "line num");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    @Override public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PhoneNumber))
            return false;
        PhoneNumber pn = (PhoneNumber)o;
        return pn.lineNum == lineNum && pn.prefix == prefix
                && pn.areaCode == areaCode;
    }

//    @Override
//    public int hashCode() {
//        return 42;
//    }

    // hashCode 없이는 제대로 동작하지 않는다. 다음 셋 중 하나를 활성화하자.

    // 코드 11-2 전형적인 hashCode 메서드 (70쪽)
    /*
    왜 31이냐?
    1) 홀수를 써야한다. 짝수를 쓰면 뒤에 0이 채워지는 문제
    2) 해시충돌이 어떤 숫자를 썼을때 적게 나는가?의 연구 결과 31
     */
//    @Override public int hashCode() {
//        int result = Short.hashCode(areaCode); // 1
//        result = 31 * result + Short.hashCode(prefix); // 2
//        result = 31 * result + Short.hashCode(lineNum); // 3
//        return result;
//    }

    // 코드 11-3 한 줄짜리 hashCode 메서드 - 성능이 살짝 아쉽다. (71쪽)
//    @Override public int hashCode() {
//        return Objects.hash(lineNum, prefix, areaCode);
//    }

    // 해시코드를 지연 초기화하는 hashCode 메서드 - 스레드 안정성까지 고려해야 한다. (71쪽)
    private volatile int hashCode; // 자동으로 0으로 초기화된다.
    /*
    volatile : CPU의 캐시에 데이터를 저장하는데, 캐시 데이터를 읽어올때 유효하지 않을수도 있는데,
               volatile을 사용하면 이 데이터를 메인메모리에 저장해서 메인메모리에서 읽어온다.
               그래서 다른 쓰레드가 가장 최근에 업데이트한 값을 가져온다.
     */

    /**
     * hashCode() 최초 호출됬을때 계산
     * 쓰레드 안정성을 고려해야한다.
     * @return
     */
    @Override
    public int hashCode() {
        if (this.hashCode != 0) { // 값이 있으면 계산X
            return hashCode;
        }

        synchronized (this) { // lock
            // T1 쓰레드만 수행
            // 이후 T2 쓰레드 수행되도 result는 0 이 아니므로 if문 수행X -> hashCode 변수에 volatile 추가
            int result = hashCode;
            if (result == 0) {
                result = Short.hashCode(areaCode);
                result = 31 * result + Short.hashCode(prefix);
                result = 31 * result + Short.hashCode(lineNum);
                this.hashCode = result;
            }
            return result;
        }
    }

    public static void main(String[] args) {
        Map<PhoneNumber, String> m = new HashMap<>();
        m.put(new PhoneNumber(707, 867, 5309), "제니");
        System.out.println(m.get(new PhoneNumber(707, 867, 5309)));
    }
}
