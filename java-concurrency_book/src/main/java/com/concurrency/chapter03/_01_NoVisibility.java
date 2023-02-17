package com.concurrency.chapter03;

public class _01_NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                System.out.println(ready);
                Thread.yield();
            }

            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();

        number = 42;

        Thread.sleep(2000);
        ready = true;
    }
}
