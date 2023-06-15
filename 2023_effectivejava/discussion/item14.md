## 아이템 14. Comparable을 구현할지 고려하라

### 1) 포스팅 정리
[아이템 14. Comparable을 구현할지 고려하라](https://devfunny.tistory.com/927)

### 2) Comparable vs Comparator
- Comparable, Comparator 둘다 모두 인터페이스다.


#### Comparable
- 자기 자신과 매개변수 객체를 비교 (자기 자신과 파라미터로 들어오는 객체를 비교)

```java
public interface Comparable<T> {
    public int compareTo(T o);
}
```

> 호출 예제
```java
class Student implements Comparable<Student> {
 
	int age;			// 나이
	int classNumber;	// 학급
	
	Student(int age, int classNumber) {
		this.age = age;
		this.classNumber = classNumber;
	}
	
	@Override
	public int compareTo(Student o) {
    
		// 자기자신의 age가 o의 age보다 크다면 양수
		if(this.age > o.age) {
			return 1;
		}
		// 자기 자신의 age와 o의 age가 같다면 0
		else if(this.age == o.age) {
			return 0;
		}
		// 자기 자신의 age가 o의 age보다 작다면 음수
		else {
			return -1;
		}
	}
}
```

```java
public class Test {
    public static void main(String[] args) {
        Studend a = new Student(17, 2);
        Studend b = new Student(18, 1);
        
        int isBig = a.compareTo(b); // 양수, 0, 음수
    }
}
```

#### Comparator
- 두 매개변수 객체를 비교 (자기 자신의 상태가 어떻던 상관없이 파라미터로 들어오는 두 객체를 비교)
- o1과 o2를 비교함에 있어 자기 자신은 두 객체 비교에 영향이 없다.

```java
@FunctionalInterface
public interface Comparator<T> {
    // ...
    
    int compare(T o1, T o2);

    // ...
}
```

> 호출 예제
```java
import java.util.Comparator;	// import 필요
class Student implements Comparator<Student> {
 
	int age;			// 나이
	int classNumber;	// 학급
	
	Student(int age, int classNumber) {
		this.age = age;
		this.classNumber = classNumber;
	}
	
	@Override
	public int compare(Student o1, Student o2) {
    
		// o1의 학급이 o2의 학급보다 크다면 양수
		if(o1.classNumber > o2.classNumber) {
			return 1;
		}
		// o1의 학급이 o2의 학급과 같다면 0
		else if(o1.classNumber == o2.classNumber) {
			return 0;
		}
		// o1의 학급이 o2의 학급보다 작다면 음수
		else {
			return -1;
		}
	}
}
```

```java
public class Test {
    public static void main(String[] args) {
        Studend a = new Student(17, 2);
        Studend b = new Student(18, 1);
        Studend b = new Student(15, 3);
        
        // b, c 객체를 비교한다. a 객체는 상관없음!
        int isBig = a.compareTo(b, c); // 양수, 0, 음수
        
        // a와 비교하고 싶다면?
        a.compare(a, b);
    }
}
```

> 의문 : 그럼 어떤 객체의 메서드를 호출해야하나?
```java
public class Test {
	public static void main(String[] args)  {
        Student a = new Student(17, 2);	// 17살 2반
        Student b = new Student(18, 1);	// 18살 1반
        Student c = new Student(15, 3);	// 15살 3반

        // a 객체?
        int isBig = a.compare(a, b);
        // a 객체?
        int isBig2 = a.compare(b, c);
        // b 객체?
        int isBig3 = b.compare(a, c);
	}
}
```

- 어떤 객체를 사용하느냐는 상관없다. 대신 여러 객체에 동일한 메서드가 있기 때문에 일관성이 떨어진다.

> 해결방안 : 익명객체 또는 람다 사용
```java
public class Test {
	public static void main(String[] args) {
//		Comparator<Student> comp1 = new Comparator<Student>() {
//			@Override
//			public int compare(Student o1, Student o2) {
//				return o1.classNumber - o2.classNumber;
//			}
//		};

        Comparator<Student> comp = (o1, o2) -> o1.classNumber - o2.classNumber;
        
//        int isBig = comp.compare(o1, o2);
	}
}
 
 
// 외부에서 익명 객체로 Comparator가 생성되기 때문에 클래스에서 Comparator을 구현 할 필요가 없어진다.
class Student {
 
	int age;			// 나이
	int classNumber;	// 학급
	
	Student(int age, int classNumber) {
		this.age = age;
		this.classNumber = classNumber;
	}
 
}
```

### 3) sort() 오류 상황
```java
public class ArraysTest {

    public static void main(String[] args) {

        MyInteger[] arr = new MyInteger[10];

        // 객체 배열 초기화 (랜덤 값으로)
        for(int i = 0; i < 10; i++) {
            arr[i] = new MyInteger((int)(Math.random() * 100));
        }

        // 정렬 이전
        System.out.print("정렬 전 : ");
        for(int i = 0; i < 10; i++) {
            System.out.print(arr[i].value + " ");
        }
        System.out.println();

        Arrays.sort(arr);

        // 정렬 이후
        System.out.print("정렬 후 : ");
        for(int i = 0; i < 10; i++) {
            System.out.print(arr[i].value + " ");
        }
        System.out.println();
    }

}

//class MyInteger implements Comparable<MyInteger> {
//    int value;
//
//    public MyInteger(int value) {
//        this.value = value;
//    }
//
//    @Override
//    public int compareTo(MyInteger o) {
//        return this.value - o.value;
//    }
//
//}

class MyInteger{
    int value;

    public MyInteger(int value) {
        this.value = value;
    }
}
```

> Comparable 구현
```java
public class ArraysTest {

    public static void main(String[] args) {

        MyInteger[] arr = new MyInteger[10];

        // 객체 배열 초기화 (랜덤 값으로)
        for(int i = 0; i < 10; i++) {
            arr[i] = new MyInteger((int)(Math.random() * 100));
        }

        // 정렬 이전
        System.out.print("정렬 전 : ");
        for(int i = 0; i < 10; i++) {
            System.out.print(arr[i].value + " ");
        }
        System.out.println();

        Arrays.sort(arr);

        // 정렬 이후
        System.out.print("정렬 후 : ");
        for(int i = 0; i < 10; i++) {
            System.out.print(arr[i].value + " ");
        }
        System.out.println();
    }

}

class MyInteger implements Comparable<MyInteger> {
    int value;

    public MyInteger(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(MyInteger o) {
        return this.value - o.value;
    }

}

//class MyInteger{
//    int value;
//
//    public MyInteger(int value) {
//        this.value = value;
//    }
//}
```

> 결과
```
정렬 전 : 32 32 24 95 82 98 82 9 16 15 
정렬 후 : 9 15 16 24 32 32 82 82 95 98 
```