package com.concurrency.chapter02.예제2_02_동기화_구문없이_요청횟수_세기;

import java.math.BigInteger;
import javax.servlet.*;

import net.jcip.annotations.*;

/**
 * UnsafeCountingFactorizer
 * 스레드 안전하지 않은 코드
 */
@NotThreadSafe
public class UnsafeCountingFactorizer extends GenericServlet implements Servlet {
    /** 상태 추가 */
    private long count = 0;

    public long getCount() {
        return count;
    }

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        /* 값을 증가시키는 연산 - 실제로 단일 연산이 아니다.
           1) 현재 값을 가져와서
           2) 거기에 +1을 하고
           3) 새 값을 저장한다.
           위 3개 작업을 순차적으로 실행하는 한줄의 코드다.
         */
        /** 두 스레드가 접근시 스레드 안전하지 않으므로 데이터 오류가 발생할 수 있다.
         * 병렬 프로그래밍의 입장에서 타이밍이 안좋을때 결과가 잘못될 가능성은 중요한 개념이다. -> race condition (경쟁조건)
         * -> 경쟁조건은 여러 스레드를 교차해서 실행하는 상황에 따라 계산의 정확성이 달라질때 나타난다.
         *    타이밍이 딱 맞았을 때만 정답을 얻는 경우를 말한다. (잠재적으로 유효하지 않은 값을 볼 수가 있다.)
         * */
        ++count;
        encodeIntoResponse(resp, factors);
    }

    void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[] { i };
    }
}
