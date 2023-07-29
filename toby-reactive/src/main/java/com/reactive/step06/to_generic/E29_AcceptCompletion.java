package com.reactive.step06.to_generic;

import java.util.function.Consumer;

public class E29_AcceptCompletion<S> extends E28_Completion<S, Void> {
    Consumer<S> con;

    public E29_AcceptCompletion(Consumer<S> con) {
        this.con = con;
    }

    @Override
    public void run(S value) {
        con.accept(value); // 앞의 결과를 받는다.
    }
}
