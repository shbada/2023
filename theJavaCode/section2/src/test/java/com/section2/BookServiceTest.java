package com.section2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Test
    public void di() {
        Assertions.assertNotNull(bookService);
        Assertions.assertNotNull(bookService.bookRepository);
    }
}