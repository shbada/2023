package com.section3.proxyPattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/*
Proxy
 */
@Service
public class BookServiceProxy implements BookService {
    @Autowired // 실제 객체를 참조한다.
    @Qualifier("defaultBookService")
    BookService bookService;

    @Override
    public void rent(Book book) {
        System.out.println("proxy");
        bookService.rent(book); // 실제 객체 호출
    }
}
