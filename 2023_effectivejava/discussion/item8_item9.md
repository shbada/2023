## 아이템 8. finalizer와 cleaner 사용을 피하라.

### 1) 포스팅 정리
[아이템 8. finalizer와 cleaner 사용을 피하라.](https://devfunny.tistory.com/921)

## 아이템 9. try-finally 보다 try-with-resouces를 사용하라.

### 1) 포스팅 정리
[아이템 9. try-finally 보다 try-with-resouces를 사용하라.](https://devfunny.tistory.com/922)

### BufferReader 클래스 예시
BufferedReader 클래스는 AutoCloseable를 구현하고 있기 때문에 try~with~resources문을 사용할 수 있다.
```
public class BufferedReader extends Reader {
...
public abstract class Reader implements Readable, Closeable {
...
public interface Closeable extends AutoCloseable {
```