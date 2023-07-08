package me.whiteship.chapter04.item19.impespec;

/**
 * Example class for java documentation for extendable class
 */
public class ExtendableClass {

    /**
     * This method can be overridden to print any message.
     *
     * @implSpec
     * Please use System.out.println().
     */
    protected void doSomething() {
        System.out.println("hello");
    }
}
