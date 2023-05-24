package me.whiteship.chapter02.item10;

class Animal { }
class Dog extends Animal { }

public class Test {
    public static void main(String[] args) {
        Animal animal = new Dog();

        System.out.println(animal instanceof Animal); // true
        System.out.println(animal instanceof Dog);    // true

        System.out.println(animal.getClass()); // class Dog

        System.out.println(animal.getClass().equals(Animal.class)); // false
        System.out.println(animal.getClass().equals(Dog.class)); // true
    }
}
