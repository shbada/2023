package com.concurrency.chapter03.예제3_06_변수_외부공개;

/**
 * UnsafeStates
 * <p/>
 * Allowing internal mutable state to escape
 *
 * @author Brian Goetz and Tim Peierls
 *
 * 이런 코드는 금물!
 * 내부적으로 사용할 변수를 외부에 공개
 */
class UnsafeStates {
    private String[] states = new String[]{
            "AK", "AL" /*...*/
    };

    /**
     * 외부에 공개
     * private으로 지정된 배열에 들어있는 값을 공개한다.
     * private으로 선언된 states 변수는 private이 아닌 getStates 메서드를 통해 외부에 공개될 수 있다.
     * 객체가 공개되면 그 객체 내부의 private이 아닌 변수나 메서드를 통해 불러올 수 있는 모든 객체는 함께 공개된다.
     * @return
     */
    public String[] getStates() {
        // 호출하는 쪽에서 이 객체를 변경할 수 있기 때문에 권장하는 방법이 아니다.
        return states;
    }
}
