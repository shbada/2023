package me.whiteship.chapter01.item03.enumtype;

import me.whiteship.chapter01.item03.field.Elvis;

import java.io.*;

public class EnumElvisSerialization {

    public static void main(String[] args) {
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("elvis.obj"))) {
            out.writeObject(Elvis.INSTANCE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInput in = new ObjectInputStream(new FileInputStream("elvis.obj"))) {
            Elvis elvis = (Elvis) in.readObject();

            // readResolve() 선언 없이도 기존 인스턴스와 동일하게 얻을 수 있다.
            // true
            System.out.println(elvis == Elvis.INSTANCE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
