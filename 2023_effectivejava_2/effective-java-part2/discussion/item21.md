## 아이템 21. 인터페이스는 구현하는 쪽을 생각해 설계하라

### 1) 포스팅 정리
https://devfunny.tistory.com/556

### foreach문의 iterator 사용
for-each문은 Iterable 인터페이스의 iterable() 메서드가 필요하다.
따라서 Iterable 인터페이스를 구현한 객체만이 for-each문을 사용할 수 있다.

```
public interface List<E> extends Collection<E> {
```
List.java가 Collection을 상속한다.

```
public interface Collection<E> extends Iterable<E> {
```
Collection.java가 Iterable을 상속한다.
따라서 List도 for-each문을 사용 가능한 것이다.

> 배열의 경우?
```
public void example(String[] words) {
    for (String word : words) {
        ...
    }
}
```

위 코드를 보면 for-each문이 Array Type일 경우 일반 for문으로 변환하여 수행해줘서, 별도 오류 없이 정상 수행된다.

- 위 코드의 디컴파일 모습
```
public void example(String[] words) {
    String[] value1 = words;
    int value2 = words.length;

    for(int value3 = 0; value3 < value2; ++value3) {
        String word = value1[value3];
        System.out.println(word);
    }
}
```

> Iterable 인터페이스의 default 메서드로 forEach() 메서드가 제공되고있다.
```
public void example(List<String> words) {
    words.forEach(word -> System.out.println(word));
}
```

## 새로 겪은 사실
- 인터페이스
```
public interface MarkerInterface {
    default void hello() {
        System.out.println("hello interface");
    }

}
```

- 클래스
```
public class SuperClass {
    private void hello() {
        System.out.println("hello class");
    }
}
```

- main 메서드
```
public class SubClass extends SuperClass implements MarkerInterface {
    public static void main(String[] args) {
        SubClass subClass = new SubClass();
        subClass.hello();
    }
}
```

> 상황
1. 인터페이스의 default hello()가 있다.
2. 부모클래스의 private hello()가 있다.
3. main 메서드에서 호출되는 hello()는 인터페이스보다 클래스의 메서드를 우선적으로 호출한다.
4. private 메서드를 호출했으므로 서버오류가 발생한다.

- 문제점 : 컴파일 오류로 왜 안잡힐까?
5. 결국 인터페이스의 hello()는 존재하기 때문에 컴파일 오류로 잡히지 못한다.
   실제로 호출하는건 인터페이스가 아닌 클래스의 hello()이므로 버그성 오류로 볼 수 있다.

