## 아이템 25. 톱레벨 클래스는 한 파일에 하나만 담으라

### 1) 포스팅 정리
https://devfunny.tistory.com/562

### 컴파일 해보기
1) Dessert.java, Utensil.java 모두 존재
```
javac Main.java Dessert.java
```
- 컴파일 오류 발생
컴파일러는 가장 먼저 Main.java를 컴파일하고, 그 안에서 Utensil 참조가 먼저 나오므로, Utemsil.java 파일을 살펴 Utensil, Dessert 모두 찾아낼 것이다. 
그런 다음 Dessert.java 를 처리하려고 할때 같은 클래스의 정의가 있음을 알게된다.

- 컴파일 결과
```java
package me.whiteship.chapter04.item25;

public class Main {
    public Main() {
    }

    public static void main(String[] var0) {
        System.out.println("potpie");
    }
}
```

2) Main + Utensil
```
javac Main.java Utensil.java
```

- 결과 : pancake
```java
package me.whiteship.chapter04.item25;

public class Main {
    public Main() {
    }

    public static void main(String[] var0) {
        System.out.println("pancake");
    }
}
```

3) Dessert + Main
```
javac Dessert.java Main.java 
```

- 결과 : potpie
```java
package me.whiteship.chapter04.item25;

public class Main {
    public Main() {
    }

    public static void main(String[] var0) {
        System.out.println("potpie");
    }
}
```
컴파일러에 어느 소스파일을 먼저 건네느냐에 따라 동작이 달라지게된다.


