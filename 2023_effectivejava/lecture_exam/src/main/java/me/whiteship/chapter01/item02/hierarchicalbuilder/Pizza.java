package me.whiteship.chapter01.item02.hierarchicalbuilder;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

// 코드 2-4 계층적으로 설계된 클래스와 잘 어울리는 빌더 패턴 (19쪽)

// 참고: 여기서 사용한 '시뮬레이트한 셀프 타입(simulated self-type)' 관용구는
// 빌더뿐 아니라 임의의 유동적인 계층구조를 허용한다.

public abstract class Pizza {
    public enum Topping { HAM, MUSHROOM, ONION, PEPPER, SAUSAGE }
    final Set<Topping> toppings;

    /**
     * Builder 자신의 하위 클래스만 담도록한다.
     * 재귀적인 타입 제한
     * @param <T>
     */
    abstract static class  Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        /**
         * new NyPizza.Builder().build()
         * Builder 는 Pizza의 Builder 이므로 Pizza의 build()가 호출된다.
         * 그러므로 하위 타입을 리턴할 수 있도록 변경해야한다.
         */
//        public Builder<T> addTopping(Topping topping) {
//            toppings.add(Objects.requireNonNull(topping));
//            return this;
//        }

        /**
         * Builder의 하위타입을 리턴할 수 있게한다.
         * @param topping
         * @return
         */
        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        abstract Pizza build();

        // 하위 클래스는 이 메서드를 재정의(overriding)하여
        // "this"를 반환하도록 해야 한다.
        protected abstract T self();
    }
    
    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone(); // 아이템 50 참조
    }
}
