package me.whiteship.chapter02.item13.clone_use_constructor;

public class SubItem extends Item implements Cloneable {

    private String name;

    @Override
    public SubItem clone() {
        // 생성자로 생성한 Item을 리턴하는 clone() 메서드 결과를 SubItem 으로 변환을 못함
        // 상위타입(Item)을 하위타입(SubItem)으로 타입변환이 불가능

        // Item 클래스의 clone()을 다시 clone()을 사용하도록 수정했음
        // 이렇게되면 호출한 클래스의 타입으로 변환할 수 있음 
        return (SubItem) super.clone();
    }

    public static void main(String[] args) {
        SubItem item = new SubItem();
        SubItem clone = item.clone();

        // 만약 위에 clone() 메서드를 주석처리해서 오버라이드 안할경우
        // Object의 clone() 메서드 사용됨 - Item 클래스의 clone()을 또 호출하게됨
        SubItem clone2 = (SubItem) item.clone(); // 이 상황도 타입캐스팅 오류 발생함

        System.out.println(clone != item);
        System.out.println(clone.getClass() == item.getClass());
        System.out.println(clone.equals(item));
    }
}
