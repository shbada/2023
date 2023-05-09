package com.section3;

import com.section3.proxyPattern.BookRepository;
import com.section3.proxyPattern.BookService;
import com.section3.proxyPattern.DefaultBookService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static org.mockito.Mockito.mock;

public class DynamicProxyTest {
    // proxy 인스턴스 만들기
    // java의 dynamicProxy는 class 기반을 못만들고 반드시 인터페이스여야한다.
    // -> new Class[]{BookService.class} 여기가 인터페이스여야됨
    BookService bookService = (BookService) java.lang.reflect.Proxy.newProxyInstance(
            BookService.class.getClassLoader(),
            new Class[]{BookService.class},
            new InvocationHandler() {
                BookService bookService = new DefaultBookService(); // real subject

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("aaaa");

                    if (method.getName().equals("rent")) {
                        System.out.println("rent call");
                    }

                    // real subject, argument
                    // 실제 호출
                    Object invoke = method.invoke(bookService, args);
                    System.out.println("bbbb");
                    return invoke;
                }
            }
    );

    @Test
    public void di() {
        // 가짜객체를 만듬
        // BookRepository 타입의 프록시 생성
        BookRepository bookRepository = mock(BookRepository.class);
    }
}
