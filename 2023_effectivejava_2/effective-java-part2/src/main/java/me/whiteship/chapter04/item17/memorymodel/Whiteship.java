package me.whiteship.chapter04.item17.memorymodel;

public class Whiteship {

    private final int x;

    private final int y;

    public Whiteship() {
        this.x = 1;
        this.y = 2;
    }

    public static void main(String[] args) {
        // Object w = new Whiteship()
        // whiteship = w
        // w.x = 1
        // w.y = 2

        Whiteship whiteship = new Whiteship();
    }


}
