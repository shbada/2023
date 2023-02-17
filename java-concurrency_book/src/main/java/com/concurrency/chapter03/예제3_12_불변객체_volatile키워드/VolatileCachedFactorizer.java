package com.concurrency.chapter03.예제3_12_불변객체_volatile키워드;

import java.math.BigInteger;
import javax.servlet.*;

import net.jcip.annotations.*;

/**
 * VolatileCachedFactorizer
 * <p/>
 * Caching the last result using a volatile reference to an immutable holder object
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class VolatileCachedFactorizer extends GenericServlet implements Servlet {
    /*
        OneValueCache 클래스를 사용하여 입력값과 결과를 캐시한다.
        volatile 키워드 선언하여 cache 변수를 선언했다.
        새로 생성한 OneValueCache 인스턴스를 설정하면 다른 스레드에서도 cache 변수에 설정된 새로운 값을 즉시 사용할 수 있다.
    */
    private volatile OneValueCache cache = new OneValueCache(null, null);

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = cache.getFactors(i);

        if (factors == null) {
            factors = factor(i);
            // 새로운 객체 생성 (불변객체)
            cache = new OneValueCache(i, factors);
        }

        encodeIntoResponse(resp, factors);
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[]{i};
    }
}
