package me.whiteship.chapter01.item03.field;

import java.io.Serializable;

// 코드 3-1 public static final 필드 방식의 싱글턴 (23쪽)
public class Elvis implements IElvis, Serializable {

    /**
     * 싱글톤 오브젝트
     */
    public static final Elvis INSTANCE = new Elvis();
    private static boolean created;

    /**
     * 해당 클래스에서만 호출 가능
     */
    private Elvis() {
        // 생성이 된 경우, 예외처리
        // 코드 간결함이 깨진다.
        if (created) {
            throw new UnsupportedOperationException("can't be created by constructor.");
        }

        created = true;
    }

    public void leaveTheBuilding() {
        System.out.println("Whoa baby, I'm outta here!");
    }

    public void sing() {
        System.out.println("I'll have a blue~ Christmas without you~");
    }

    // 이 메서드는 보통 클래스 바깥(다른 클래스)에 작성해야 한다!
    public static void main(String[] args) {
        Elvis elvis = Elvis.INSTANCE;
        elvis.leaveTheBuilding();
    }

    /**
     * 역직렬화시 객체 생성때 호출된다.
     * 문법적으로 Override 는 아니지만, @Override 느낌이다.
     * @return
     */
    private Object readResolve() {
        return INSTANCE;
    }

}
