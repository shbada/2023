## 아이템 17. 변경 가능성을 최소화하라.

### 1) 포스팅 정리
https://devfunny.tistory.com/543

### 2) 강의 포인트 정리
- 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 한다.
```
public final class Person {
    private final Address address;

    ...
}
```

- setXXX()로 값을 변경할 수 없도록 복사본을 제공한다.
```
public Address getAddress() {
    // 복사본을 만든다.
    Address copyOfAddress = new Address();
    copyOfAddress.setStreet(address.getStreet());
    copyOfAddress.setZipCode(address.getZipCode());
    copyOfAddress.setCity(address.getCity());
    return copyOfAddress;

    // 이렇게하면 set 메서드로 값 변경이 가능해져버린다. (address 내부 필드)
//        return address;
}
```

### CountDownLatch 예제
```
public static void main(String[] args) throws InterruptedException {
    int N = 10;
    CountDownLatch startSignal = new CountDownLatch(1);
    CountDownLatch doneSignal = new CountDownLatch(N);

    for (int i = 0; i < N; ++i) // create and start threads
        new Thread(new Worker(startSignal, doneSignal)).start();

    ready();            // don't let run yet
    startSignal.countDown();      // let all threads proceed
    doneSignal.await();           // wait for all to finish
    done();
}
```

