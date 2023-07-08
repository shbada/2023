package me.whiteship.chapter04.item15.class_and_interfaces.item;

import me.whiteship.chapter04.item15.class_and_interfaces.member.MemberService;

public class ItemService {

    private MemberService memberService;

    boolean onSale;

    protected int saleRate;

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
