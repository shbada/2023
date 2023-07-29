package com.reactive.step06.to_generic;

import java.util.function.Consumer;

public class E31_ErrorCompletion<T> extends E28_Completion<T, T> {
    Consumer<Throwable> econ;

    public E31_ErrorCompletion(Consumer<Throwable> econ) {
        this.econ = econ;
    }

    @Override
    public void run(T value) {
        if (next != null) {
            next.run(value); // pass
        }
    }

    @Override
    public void error(Throwable e) {
        econ.accept(e);
    }
}
