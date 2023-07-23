package me.whiteship.chapter04.item17.part3;

import java.math.BigInteger;

public class BigIntegerUtils {

    public static BigInteger safeInstance(BigInteger val) {
        // 실제 타입이 BigInteger인지를 체크
        // 혹시나 아니라면 새로운 인스턴스를 생성하겠다 (방어적 복사)
        return val.getClass() == BigInteger.class ? val : new BigInteger(val.toByteArray());
    }
}
