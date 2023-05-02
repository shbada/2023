package me.whiteship.chapter01.item07.optional;

import java.util.Optional;
import java.util.OptionalLong;

public class Channel {

    private int numOfSubscribers;

    /**
     * Optional 을 매개변수로 받거나 등의 다른 행위는 비추천
     * Optional은 반환타입 등에 사용
     * Optional로 Collection, Map 등을 감싸지마라.
     * @return
     */
    public Optional<MemberShip> defaultMemberShip() {
        if (this.numOfSubscribers < 2000) {
            return Optional.empty();
        } else {
            return Optional.of(new MemberShip());
        }
    }
}
