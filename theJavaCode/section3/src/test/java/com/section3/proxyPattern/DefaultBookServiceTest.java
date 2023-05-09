package com.section3.proxyPattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DefaultBookServiceTest {
    @Autowired
    @Qualifier("bookServiceProxy")
    BookService bookService;

    @Test
    public void di() {
        Book book = new Book();
        book.setB("spring");
        bookService.rent(book);
    }
}