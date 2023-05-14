package me.whiteship.chapter01.item08.finalizer_attack;

import java.math.BigDecimal;

/**
 * 상속해서 하위클래스 생성
 */
public class BrokenAccount extends Account {

    public BrokenAccount(String accountId) {
        // 부모의 생성자 호출
        super(accountId);
    }

    /**
     * 재정의
     * Account 에서 final 추가한 오버라이딩 finalize() 메서드가 있다면 재정의 불가
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        // 원하는 금액을 원하는 사람한테 보냄
        this.transfer(BigDecimal.valueOf(100), "keesun");
    }
}

