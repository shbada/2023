package me.whiteship.chapter02.item13.test;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Target implements Cloneable {
    private String name;
    private String value;

    @Override
    public Target clone() {
        try {
            Target clone = (Target) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
