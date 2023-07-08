package me.whiteship.chapter04.item15.class_and_interfaces.item;

import me.whiteship.chapter04.item15.class_and_interfaces.member.MemberService;

public class ItemService {

    private MemberService memberService;

    boolean onSale;

    protected int saleRate;

    // 바깥에서 값을 임의대로 바꿀 수 있다.
    // 그럼 아래 배열 필드를 사용하는 여러곳에서 오류가 발생할 수 있다.
    public static final String[] NAMES = new String[10];

    public ItemService(MemberService memberService) {
        if (memberService == null) {
            throw new IllegalArgumentException("MemberService should not be null.");
        }

        this.memberService = memberService;
    }

    MemberService getMemberService() {
        return memberService;
    }
}
