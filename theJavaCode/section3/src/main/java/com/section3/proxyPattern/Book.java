package com.section3.proxyPattern;

public class Book {
    public static String A = "A";

    public void setB(String b) {
        B = b;
    }

    private String B = "B";

    public Book() {
    }

    public Book(String b) {
        B = b;
    }

    public void c() {
        System.out.println("C");
    }

    public int sum(int left, int right) {
        return left + right;
    }
}
