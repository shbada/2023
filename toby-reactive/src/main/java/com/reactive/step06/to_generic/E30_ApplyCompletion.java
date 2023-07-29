package com.reactive.step06.to_generic;

import org.springframework.util.concurrent.ListenableFuture;

import java.util.function.Function;

public class E30_ApplyCompletion<S, T> extends E28_Completion<S, T> {
    Function<S, ListenableFuture<T>> fn;
    public E30_ApplyCompletion(Function<S, ListenableFuture<T>> fn) {
        this.fn = fn;
    }

    @Override
    public void run(S value) {
        ListenableFuture<T> lf = fn.apply(value);
        lf.addCallback(s -> {}, e -> {});
    }
}
