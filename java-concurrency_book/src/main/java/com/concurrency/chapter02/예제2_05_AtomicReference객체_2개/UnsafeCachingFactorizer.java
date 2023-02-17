package com.concurrency.chapter02.예제2_05_AtomicReference객체_2개;

import java.math.BigInteger;
import java.util.concurrent.atomic.*;
import javax.servlet.*;

import net.jcip.annotations.*;

/**
 * UnsafeCachingFactorizer
 * 이런 코드는 금물!
 * 단일 연산을 적절히 사용하지 못한 상태에서 결과 값을 캐시하려는 서블릿
 */

@NotThreadSafe
public class UnsafeCachingFactorizer extends GenericServlet implements Servlet {
    /* 변수 2개는 각각은 스레드에 안전하지만 클래스 자체는 틀린 결과를 낼 수 있다. */
    // 가장 마지막으로 인수분해하기 위해 입력된 숫자
    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<BigInteger>();
    // 위 입력 값을 인수분해한 결과값
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<BigInteger[]>();

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        /*
            불변조건 : 인수분해 결과를 곱한 값이 lastNumber에 캐시된 값과 같아야한다.
            - 여러개의 변수가 하나의 불변조건을 구성하고 있다면, 이 변수들은 서로 독립적이지 않다.
            즉, 한 변수의 값이 다른 변수에 들어갈 수 있는 값을 제한할 수 있다.
            따라서 변수 하나를 갱신할땐 다른 변수도 동일한 단일 연산 작업 내에서 함께 변경해야한다.
         */
        if (i.equals(lastNumber.get()))
            encodeIntoResponse(resp, lastFactors.get());
        else {
            BigInteger[] factors = factor(i);

            // 개별적인 각 set 메소드는 단일 연산으로 동작한다.
            // 단일 연산 참조 클래스를 쓰더라도 lastNumber, lastFactors라는 두개의 값을 동시에 갱신하지는 못한다.
            // 하나는 수정됐고, 하나는 수정되지 않은 그 시점에 여전이 취약점이 존재한다. 이 순간 다른 스레드가 값을 읽어갈 수 있다.
            // 상태를 일관성있게 유지하려면, 관련 있는 변수들을 하나의 단일 연산으로 갱신해야한다.
            // synchronized 구문을 사용해야한다.
            lastNumber.set(i);
            lastFactors.set(factors);

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
        return new BigInteger[]{i};
    }
}
