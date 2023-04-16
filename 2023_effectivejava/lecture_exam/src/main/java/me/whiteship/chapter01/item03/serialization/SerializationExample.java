package me.whiteship.chapter01.item03.serialization;

import java.io.*;
import java.time.LocalDate;

public class SerializationExample {

    private void serialize(Book book) {
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("book.obj"))) {
            out.writeObject(book);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Book deserialize() {
        try (ObjectInput in = new ObjectInputStream(new FileInputStream("book.obj"))) {
            return (Book) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
//        Book book = new Book("12345", "이팩티브 자바 완벽 공략", "백기선",
//                LocalDate.of(2022, 3, 21));
//        book.setNumberOfSold(200);

//        SerializationExample example = new SerializationExample();
//        example.serialize(book);
//        Book deserializedBook = example.deserialize();

//        System.out.println(book);
//        System.out.println(deserializedBook);

        // static 변수는 직렬화되지 않는다.
        staticSerializeTest();

        /**
         * private static final long serialVersionUID = 1L; 가 없을때
         * - private String name; 추가
         * - 직렬화할때 없었던 name 이 추가된 후, 역직렬화하면 오류가 발생한다.
         * - serialVersionUID 가 맞지 않다고 오류가 나온다.
         */
        // 1) 직렬화
        serialization1();

        // 2) 위 직렬화 수행후, name 변수 추가후 역직렬화 : 실패
        // 2) 위 직렬화 수행후, 클래스 변경 없이 역직렬화  : 성공 (serialVersionUID 가 동일하게 만들어짐)
        serialization2();

        /**
         * private static final long serialVersionUID = 1L; 가 있을때
         * - 직렬화 후 필드가 삭제되거나 추가되어도 역직렬화가 정상적으로 된다.
         */
        // 1) 직렬화
        serialization1();

        // 2) 위 직렬화 수행후, name 변수 추가후 역직렬화 : 성공
        // 2) 위 직렬화 수행후, 클래스 변경 없이 역직렬화  : 성공
        serialization2();
    }

    public static void staticSerializeTest() {
        Book book = new Book("12345", "이팩티브 자바 완벽 공략", "백기선",
                LocalDate.of(2022, 3, 21));
        book.setNumberOfSold(200);
        Book.staticName = "seohae"; // 직렬화 되지 않음

        SerializationExample example = new SerializationExample();
        example.serialize(book);
        Book.staticName = "update seohae";
        Book deserializedBook = example.deserialize();

        // 클래스에 할당되는 코드이므로 둘다 update seohae로 찍힌다.
        System.out.println(book);
        System.out.println(deserializedBook);
    }

    public static void serialization1() {
        Book book = new Book("12345", "이팩티브 자바 완벽 공략", "백기선",
                LocalDate.of(2022, 3, 21));
        book.setNumberOfSold(200);

        SerializationExample example = new SerializationExample();
        example.serialize(book); // 직렬화

        // 역직렬화 하지 않음
//        Book deserializedBook = example.deserialize();

        System.out.println(book);
//        System.out.println(deserializedBook);
    }

    public static void serialization2() {
//        Book book = new Book("12345", "이팩티브 자바 완벽 공략", "백기선",
//                LocalDate.of(2022, 3, 21));
//        book.setNumberOfSold(200);

        SerializationExample example = new SerializationExample();
//        example.serialize(book); // 직렬화

        Book deserializedBook = example.deserialize();

//        System.out.println(book);
        System.out.println(deserializedBook);
    }
}
