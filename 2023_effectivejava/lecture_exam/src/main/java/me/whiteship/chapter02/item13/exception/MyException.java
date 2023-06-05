package me.whiteship.chapter02.item13.exception;

/**
 * RuntimeExcpetion, Error 상속 - UncheckedException
 * Exception 상속 - CheckedException
 *
 * CheckedException 보다 UncheckedException 을 선호함
 *
 * UncheckedException 선택 기준
 * - 에러 발생을 호출부에 알려야한다.
 *   이러한 상황을 잡아서 어떠한 행위를 해야한다. (복구 등)
 *
 * clone() 메서드에서 CloneNotSupportedException
 * - 클라이언트에서 할 수 있는 행위가 없는데 왜 CheckedExceptio 일까
 * - 이건 UncheckedException이였으면 더 좋았을거 같다라는 저자의 말이 있음
 */
public class MyException extends Exception {
}
