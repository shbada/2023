package com.reactive.step06;

import org.springframework.http.ResponseEntity;

import java.util.function.Consumer;

public class E29_AcceptCompletion extends E28_Completion {
    Consumer<ResponseEntity<String>> con;

    public E29_AcceptCompletion(Consumer<ResponseEntity<String>> con) {
        this.con = con;
    }

    @Override
    public void run(ResponseEntity<String> value) {
        con.accept(value); // 앞의 결과를 받는다.
    }
}
