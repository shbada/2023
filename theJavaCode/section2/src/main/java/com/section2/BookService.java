package com.section2;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class BookService {
//    private final BookRepository bookRepository;
    @Autowired
    BookRepository bookRepository;
}
