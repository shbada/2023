## 아이템 7. 다 쓴 객체 참조를 해제하라

### 1) 포스팅 정리
[아이템 7. 다 쓴 객체 참조를 해제하라](https://devfunny.tistory.com/919)

### Stack 메모리 누수 해결
- 참조해제
> Java 에서 제공해주는 java.util.Stack 클래스
```java
public class Stack<E> extends Vector<E> {

    public Stack() {
    }

    public E push(E item) {
        addElement(item);

        return item;
    }
    
    public synchronized E pop() {
        E       obj;
        int     len = size();

        obj = peek();
        removeElementAt(len - 1);

        return obj;
    }

    // ...

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = 1224463164541339165L;
}
```

위 pop() 의 Vector.removeElementAt()를 보자.
```
/**
 * The number of valid components in this {@code Vector} object.
 * Components {@code elementData[0]} through
 * {@code elementData[elementCount-1]} are the actual items.
 *
 * @serial
 */
protected int elementCount;
    
// ...

public synchronized void removeElementAt(int index) {
    if (index >= elementCount) {
        throw new ArrayIndexOutOfBoundsException(index + " >= " +
                                                 elementCount);
    }
    else if (index < 0) {
        throw new ArrayIndexOutOfBoundsException(index);
    }
    int j = elementCount - index - 1;
    if (j > 0) {
        System.arraycopy(elementData, index + 1, elementData, index, j);
    }
    modCount++;
    elementCount--;
    elementData[elementCount] = null; /* to let gc do its work */
}
```

여기서도 책에 나온 예제처럼 null 처리로 참조 해제를 수행한다.
```
elementData[elementCount] = null;
```

