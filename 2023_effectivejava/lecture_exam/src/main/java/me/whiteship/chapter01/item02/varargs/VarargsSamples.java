package me.whiteship.chapter01.item02.varargs;

import java.util.Arrays;

public class VarargsSamples {

    /**
     * 가변인수는 파라미터 여러개일때 항상 뒤쪽에 1개만 놓을 수 있다.
     * @param numbers
     */
    public void printNumbers(int... numbers) {
        System.out.println(numbers.getClass().getCanonicalName()); // int[]
        System.out.println(numbers.getClass().getComponentType()); // int
        Arrays.stream(numbers).forEach(System.out::println);
    }

    /**
     * Heap Pollution 힙 오염
     * @param args
     */
    public static void main(String[] args) {
        VarargsSamples samples = new VarargsSamples();
        samples.printNumbers(1, 20, 20, 39, 59);
    }
}
