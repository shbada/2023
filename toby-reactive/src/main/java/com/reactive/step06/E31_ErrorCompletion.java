package com.reactive.step06;

import org.springframework.http.ResponseEntity;

import java.util.function.Consumer;

public class E31_ErrorCompletion extends E28_Completion {
    Consumer<Throwable> econ;

    public E31_ErrorCompletion(Consumer<Throwable> econ) {
        this.econ = econ;
    }

    @Override
    public void run(ResponseEntity<String> value) {
        if (next != null) {
            next.run(value); // pass
        }
    }

    @Override
    public void error(Throwable e) {
        econ.accept(e);
    }
}
