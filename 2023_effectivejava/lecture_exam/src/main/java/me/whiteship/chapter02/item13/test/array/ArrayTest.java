package me.whiteship.chapter02.item13.test.array;

public class ArrayTest {

    public static void main(String[] args) {
        Object[] elements = new Object[3];
        elements[0] = new TargetChild(1);
        elements[1] = new TargetChild(2);
        elements[2] = new TargetChild(3);

        Object[] copy = elements.clone();

        System.out.println(copy[0] == elements[0]); // true
        System.out.println(copy[1] == elements[1]); // true
        System.out.println(copy[2] == elements[2]); // true

        TargetChild getValue = (TargetChild) copy[2];
        getValue.setNum(100);
        System.out.println(((TargetChild) copy[2]).getNum()); // 100
        System.out.println(((TargetChild) elements[2]).getNum()); // 100

        // 만약 배열 안의 객체도 clone() 했다면?
//        for (Object object : elements) {
//            // 구현이 되어있는지 어떻게알아?
//            TargetChild targetChild = (TargetChild) object.clone();
//        }
    }
}
