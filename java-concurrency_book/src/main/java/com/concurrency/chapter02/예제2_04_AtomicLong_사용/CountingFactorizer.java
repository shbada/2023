package com.concurrency.chapter02.예제2_04_AtomicLong_사용;

import java.math.BigInteger;
import java.util.concurrent.atomic.*;
import javax.servlet.*;

import net.jcip.annotations.*;

/**
 * CountingFactorizer
 *
 * Servlet that counts requests using AtomicLong
 *
 * @author Brian Goetz and Tim Peierls
 *
 * atomic 블로그 포스팅 : https://devfunny.tistory.com/812
 */

@ThreadSafe
public class CountingFactorizer extends GenericServlet implements Servlet {
    /* count 에 접근하는 모든 동작이 단일 연산으로 처리된다.
        서블릿 상태가 카운터의 상태이고 카운터가 스레드에 안전하기 때문에 서블릿도 스레드에 안전하다.
     */
    private final AtomicLong count = new AtomicLong(0);

    public long getCount() { return count.get(); }

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);

        /* 카운터 증가시킴. 증가된 결과 값을 리턴함 */
        count.incrementAndGet();
        encodeIntoResponse(resp, factors);
    }

    void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {}
    BigInteger extractFromRequest(ServletRequest req) {return null; }
    BigInteger[] factor(BigInteger i) { return null; }
}