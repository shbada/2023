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
public class ConcurrentExample {
    public static void main(String[] args) throws InterruptedException {
        int N = 10;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(N);

        for (int i = 0; i < N; ++i) // create and start threads
            new Thread(new Worker(startSignal, doneSignal)).start();

        ready();            // don't let run yet
        // 모든 스레드가 작업을 시작
        startSignal.countDown();      // let all threads proceed
        // 모든 스레드가 작업을 완료할 때까지 대기
        doneSignal.await();           // wait for all to finish
        done();
    }

    private static void ready() {
        System.out.println("준비~~~");
    }

    private static void done() {
        System.out.println("끝!");
    }

    private static class Worker implements Runnable {

        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        public Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        public void run() {
            try {
                // 스레드를 시작하기 전에 대기
                startSignal.await();
                doWork();
                // 해당 스레드의 작업이 완료되었음을 알림
                doneSignal.countDown();
            } catch (InterruptedException ex) {} // return;
        }

        void doWork() {
            System.out.println("working thread: " + Thread.currentThread().getName());
        }
    }
}
```

> 실행결과
```
준비~~~
working thread: Thread-4
working thread: Thread-3
working thread: Thread-2
working thread: Thread-8
working thread: Thread-6
working thread: Thread-7
working thread: Thread-1
working thread: Thread-5
working thread: Thread-0
working thread: Thread-9
끝!
```

- CountDownLatch 를 사용하지 않았다면?
```
public class ConcurrentExample2 {
    public static void main(String[] args) throws InterruptedException {
        int N = 10;

        for (int i = 0; i < N; ++i) // create and start threads
            new Thread(new Worker()).start();

        ready();            // don't let run yet
        done();
    }

    private static void ready() {
        System.out.println("준비~~~");
    }

    private static void done() {
        System.out.println("끝!");
    }

    private static class Worker implements Runnable {

        public Worker() {
        }

        public void run() {
            doWork();
        }

        void doWork() {
            System.out.println("working thread: " + Thread.currentThread().getName());
        }
    }
}
```

> 실행결과
```
working thread: Thread-2
working thread: Thread-6
working thread: Thread-4
working thread: Thread-7
working thread: Thread-3
working thread: Thread-0
working thread: Thread-5
working thread: Thread-1
working thread: Thread-8
준비~~~
끝!
working thread: Thread-9
```

위 실행결과는 실행할때마다 다르게 나온다.