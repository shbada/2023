package com.concurrency.chapter02.예제2_01_상태없는_서블릿;

import java.math.BigInteger;
import javax.servlet.*;

import net.jcip.annotations.*;

/**
 * 상태없는 서블릿
 * StatelessFactorizer
 */
@ThreadSafe
public class StatelessFactorizer extends GenericServlet implements Servlet {

    public void service(ServletRequest req, ServletResponse resp) {
        /*
        다른 클래스의 참조가 없다. 선언한 변수가 없고, 다른 클래스의 변수를 참조하지도 않는다. 지역변수만 사용한다.
        StatelessFactorizer에 접근하는 특정 스레드는 같은 StatelessFactorizer에 접근하는 다른 스레드의 결과에 영향을 줄 수 없다.
        두 스레드가 상태를 공유하지 않기 때문에 서로 다른 인스턴스에 접근하는 것과 같다.
        그러므로 상태 없는 객체(StatelessFactorizer)는 항상 스레드 안전하다.
         */
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        encodeIntoResponse(resp, factors);
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
