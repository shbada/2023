package me.whiteship.chapter01.item03.field;

import java.io.*;

// 역직렬화로 여러 인스턴스 만들기
public class ElvisSerialization {

    public static void main(String[] args) {
        /*
         * 저장하는 코드
         */
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("elvis.obj"))) {
            out.writeObject(Elvis.INSTANCE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * 읽어오는 코드
         */
        try (ObjectInput in = new ObjectInputStream(new FileInputStream("elvis.obj"))) {
            Elvis elvis3 = (Elvis) in.readObject(); // readResolve() 호출

            // 새로운 인스턴스 생성 (false)
            System.out.println(elvis3 == Elvis.INSTANCE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
