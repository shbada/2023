package com.reactive.step06;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.function.Function;

public class E30_ApplyCompletion extends E28_Completion {
    Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn;
    public E30_ApplyCompletion(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn) {
        this.fn = fn;
    }

    @Override
    public void run(ResponseEntity<String> value) {
        ListenableFuture<ResponseEntity<String>> lf = fn.apply(value);
        lf.addCallback(s -> {}, e -> {});
    }
}
