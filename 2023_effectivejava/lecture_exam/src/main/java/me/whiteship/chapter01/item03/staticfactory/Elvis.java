package me.whiteship.chapter01.item03.staticfactory;

// 코드 3-2 정적 팩터리 방식의 싱글턴 (24쪽)
public class Elvis implements Singer {
    /* private */
    private static final Elvis INSTANCE = new Elvis();

    private Elvis() { }

    /**
     * 해당 메서드로 객체를 얻을 수 있다.
     * 만일 새로운 인스턴스를 리턴하고 싶다면 해당 메서드 로직만 변경하여 쉽게 적용한다. (클라이언트 변경 없음)
     * @return
     */
    public static Elvis getInstance() { return INSTANCE; }

    public void leaveTheBuilding() {
        System.out.println("Whoa baby, I'm outta here!");
    }

    // 이 메서드는 보통 클래스 바깥(다른 클래스)에 작성해야 한다!
    public static void main(String[] args) {
        Elvis elvis = Elvis.getInstance(); // 매번 동일한 객체를 얻는다.
        elvis.leaveTheBuilding();

        System.out.println(Elvis.getInstance());
        System.out.println(Elvis.getInstance());
    }

    @Override
    public void sing() {
        System.out.println("my way~~~");
    }
}
