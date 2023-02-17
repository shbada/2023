package com.concurrency.chapter02.예제2_06_synchronized_성능저하;

import java.math.BigInteger;
import javax.servlet.*;

import net.jcip.annotations.*;

/**
 * SynchronizedFactorizer
 *
 * Servlet that caches last result, but with unnacceptably poor concurrency
 *
 * @author Brian Goetz and Tim Peierls
 *
 * 이런 코드는 금물!
 * 마지막 결과를 캐시하지만 성능이 현저하게 떨어지는 서블릿
 */

@ThreadSafe
public class SynchronizedFactorizer extends GenericServlet implements Servlet {
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;

    /**
     * synchronized 키워드 추가 - 한번에 한 스레드만 실행할 수 있게되었다.
     * 스레드는 안전하다.
     * 하지만 방법이 너무 극단적이라 인수분해 서블릿을 여러 클라이언트가 동시에 사용할 수 없고,
     * 이때문에 응답성이 엄청나게 떨어진다.
     * @param req
     * @param resp
     */
    public synchronized void service(ServletRequest req,
                                     ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber))
            encodeIntoResponse(resp, lastFactors);
        else {
            BigInteger[] factors = factor(i);
            lastNumber = i;
            lastFactors = factors;
            encodeIntoResponse(resp, factors);
        }
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[] { i };
    }
}
