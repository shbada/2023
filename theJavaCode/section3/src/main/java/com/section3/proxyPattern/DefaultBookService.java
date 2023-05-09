package com.section3.proxyPattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Real Subject
 */
@Service
public class DefaultBookService implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Override
    public void rent(Book book) {
        System.out.println("real subject");
    }
}
