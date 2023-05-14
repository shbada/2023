package me.whiteship.chapter01.item08.finalizer_attack;

import java.math.BigDecimal;

public class Account {

    private String accountId;

    public Account(String accountId) {
        this.accountId = accountId;

        // 방지
        if (accountId.equals("푸틴")) {
            throw new IllegalArgumentException("푸틴은 계정을 막습니다.");
        }
    }

    public void transfer(BigDecimal amount, String to) {
        System.out.printf("transfer %f from %s to %s\n", amount, accountId, to);
    }

    /**
     * 해결방안) 아무것도 하지 않는 finalize()를 선언해라 (fianl을 붙혀라!)
     * @throws Throwable
     */
//    @Override
//    protected final void finalize() throws Throwable {
//    }
}
