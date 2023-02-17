package com.concurrency.chapter03.예제3_12_불변객체_volatile키워드;

import java.math.BigInteger;
import java.util.*;

import net.jcip.annotations.*;

/**
 * blog posting : https://devfunny.tistory.com/841
 *
 * OneValueCache
 * <p/>
 * Immutable holder for caching a number and its factors
 *
 * @author Brian Goetz and Tim Peierls
 */
@Immutable
public class OneValueCache {
    /* 불변 */
    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    public OneValueCache(BigInteger i, BigInteger[] factors) {
        lastNumber = i;
        lastFactors = Arrays.copyOf(factors, factors.length);
    }

    public BigInteger[] getFactors(BigInteger i) {
        if (lastNumber == null || !lastNumber.equals(i))
            return null;
        else
            // 새로운 불변 객체 생성
            return Arrays.copyOf(lastFactors, lastFactors.length);
    }
}
