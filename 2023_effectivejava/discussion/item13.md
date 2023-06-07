## 아이템 13. clone 재정의는 주의해서 진행하라

### 1) 포스팅 정리
[아이템 13. clone 재정의는 주의해서 진행하라](https://devfunny.tistory.com/926)

### cloneable
##### Cloneable 구현하지 않은 상태

```java Target.java
@Getter
@Setter
public class Target {
    private String name;
    private String value;
}
```

> clone() 메서드 사용시 컴파일 오류 : `'clone()' has protected access in 'java.lang.Object`
```java Test.java
public class Test {
    public static void main(String[] args) {
        Target target = new Target();
        target.setName("test");
        target.setValue("vavavava");

        // 'clone()' has protected access in 'java.lang.Object
        target.clone();
    }
}
```

##### Cloneable 구현 CASE별 정리

```java Target.java
@Getter
@Setter
public class Target implements Cloneable {
    private String name;
    private String value;
}
```

> clone() 메서드 사용시 컴파일 오류 : `'clone()' has protected access in 'java.lang.Object'`
```java Test.java
public class Test {
    public static void main(String[] args) {
        Target target = new Target();
        target.setName("test");
        target.setValue("vavavava");

        // 'clone()' has protected access in 'java.lang.Object
        target.clone();
    }
}
```

결국 Cloneable 인터페이스는 비어있지만, 이 인터페이스를 구현해야 clone() 메서드 사용이 가능하다.
또한 Object 클래스의 clone() 메서드는 protected 이기 때문에 @Override clone() 구현해야 정상적으로 호출이 가능함을 알 수 있다.

```java Target.java
@Getter
@Setter
public class Target implements Cloneable {
    private String name;
    private String value;

    @Override
    public Target clone() {
        try {
            Target clone = (Target) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
```

만약 부모-자식 상속관계일 경우, protected 접근제한자인 Object 클래스의 clone() 호출이 가능하다.
하지만 Cloneable을 구현하지 않은 클래스의 경우 오류가 발생한다.

```java
public class Animal {
}
```

```java
public class Cat {
    public static void main(String[] args) {
        Cat cat = new Cat();

        try {
            cat.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
```

> 오류 발생!
`Exception in thread "main" java.lang.RuntimeException: java.lang.CloneNotSupportedException: me.whiteship.chapter02.item13.test.inheritance.Cat`

## 구현했던 Stack 클래스에서 주석을 다시보자
```
@Override public Stack clone() {
    try {
        Stack result = (Stack) super.clone();
        /*
        호출 안하면?
        다른 두 인스턴스 stack, copy -> 동일한 elements 를 참조하게된다.
        다른 두 인스턴스에서 동일한 배열을 쓰게됨

        elements.clone()을 해서 배열을 복사해야 복사본, 원본 인스턴스가 각각의 elements를 가지게된다.

        (얕은복사)
        stack -> elementsS[0, 1]
        copy -> elementsC[0, 1]

        // 배열만 새로 만들고. 안에 있는 인스턴스들은 동일하게 참조한다. (여전히 위험하긴한다.)
        elementsS[0] == elementsC[0]
         */
//           result.elements = elements.clone();

        return result;
    } catch (CloneNotSupportedException e) {
        throw new AssertionError();
    }
}
```

> elements[] 배열 안의 포함한 객체들에 clone()을 하지 않는 이유는?
그 객체들이 복제 가능(cloneable)한지 보장할 수 없기 때문이다.

```java
public class ArrayTest {
    public static void main(String[] args) {
        Object[] elements = new Object[3];
        elements[0] = new TargetChild(1);
        elements[1] = new TargetChild(2);
        elements[2] = new TargetChild(3);

        Object[] copy = elements.clone();

        System.out.println(copy[0] == elements[0]); // true
        System.out.println(copy[1] == elements[1]); // true
        System.out.println(copy[2] == elements[2]); // true

        TargetChild getValue = (TargetChild) copy[2];
        getValue.setNum(100);
        System.out.println(((TargetChild) copy[2]).getNum()); // 100
        System.out.println(((TargetChild) elements[2]).getNum()); // 100

        // 만약 배열 안의 객체도 clone() 했다면?
        for (Object object : elements) {
            // 구현이 되어있는지 어떻게알아?
            TargetChild targetChild = (TargetChild) object.clone();
        }
    }
}
```

