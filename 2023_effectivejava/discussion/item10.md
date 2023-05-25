## 아이템 10. equals는 일반 규약을 지켜 재정의하라

### 1) 포스팅 정리
[아이템 10. equals는 일반 규약을 지켜 재정의하라](https://devfunny.tistory.com/923)

### 2) String 클래스의 equals()
```
public boolean equals(Object anObject) {
    if (this == anObject) {
        return true;
    }
    
    if (anObject instanceof String) {
        String aString = (String)anObject;
        if (coder() == aString.coder()) {
            return isLatin1() ? StringLatin1.equals(value, aString.value)
                              : StringUTF16.equals(value, aString.value);
        }
    }
    return false;
}
```
공부한 내용으로 이해하자면, 아래와 같다.
1) 반사성 만족
```
if (this == anObject) {
    return true;
}
```

2) 본인과 다른 타입은 지원하지 말라
```
if (anObject instanceof String) {
    ...
}
```

## 3) StackOverflowError
StackOverflowError 는 응용 프로그램이 클래스 간에 순환 관계 를 갖도록 설계되었을 때도 발생할 수 있다.
이는 우리가 스프링에서 사용하는 생성자 주입이 필드 주입보다 더 좋은 이유 중에 하나였던,
순환참조를 방지할 수 있다. 라는 점도 StackOverflowError를 방지할 수 있다고 생각할 수 있겠다.


## 4) instanceof  vs getClass()
```
class Animal { }
class Dog extends Animal { }

public class Test {
    public static void main(String[] args) {
        Animal animal = new Dog();

        System.out.println(animal instanceof Animal); // 1) true
        System.out.println(animal instanceof Dog);    // 2) true

        System.out.println(animal.getClass()); // class Dog

        System.out.println(animal.getClass().equals(Animal.class)); // 3) false
        System.out.println(animal.getClass().equals(Dog.class)); // 4) true
    }
}
```

1) instanceof
   instanceof 연산자는 객체가 특정 클래스나 그 하위 클래스의 인스턴스인지를 확인하는 데 사용한다.

2) getClass()
   객체의 실제 런타임 클래스를 반환한다.

instanceof 를 사용한 1), 2)번의 결과는 animal이 Dog 타입이고, 이에 따라 Dog의 부모타입인 Animal 타입도 true라는 걸 알려준다.
getClass()는 실제 런타임 타입을 반환하므로 Animal 타입이 아닌 Dog 타입이 맞다.

## null instanceof Type
> A.equals(null), null.equals(A)
1) A.equals(null)
String 클래스의 equals() 메서드를 호출하므로 NPE(NullPointException) 발생 X
```
if (anObject instanceof String) {
    ...
}
```
그럼 위 코드를 봤을때 anObject 가 null일때 NPE 발생하지 않는다는 것이다.
```
public static void main(String[] args) {
    System.out.println(null instanceof String);
}
```
예제의 결과는 'false'다.

참고하자면, NPE는 일반적으로 null 참조에 대해 멤버나 메서드를 호출할 때 발생한다.
instanceof 연산자는 객체의 유형을 확인하기만 하고, 메서드를 호출하지 않기 때문에 NPE가 발생하지 않는다.

2) null.equals(A)
NPE 발생

지금까지 습관처럼 해왔던 A.equals("aa"); 가 아닌 "aa".equals(A); 를 권장했던 이유를 되새겨봤다.
