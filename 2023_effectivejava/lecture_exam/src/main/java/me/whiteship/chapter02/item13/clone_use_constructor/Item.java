package me.whiteship.chapter02.item13.clone_use_constructor;

public class Item implements Cloneable {

    private String name;

    /**
     * 이렇게 구현하면 하위 클래스의 clone()이 깨질 수 있다. p78
     * @return
     */
//    @Override
//    public Item clone() {
//        // super.clone()을 쓰지 않고 생성자를 썼음
//        // 이렇게하면 규약이 깨짐
//        Item item = new Item();
//        item.name = this.name;
//        return item;
//    }

    /**
     * 올바르게 구현한 clone()
     * @return
     */
    @Override
    public Item clone() {
        Item result = null;

        try {
            // 여기선 Item 이라고 생각하지만, SubItem 에서 호출하면 SubItem 타입이다.
            result = (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }

        return result;
    }
}
