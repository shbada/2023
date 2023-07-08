package me.whiteship.chapter05.item33.type_token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Favorites {

    private Map<Class<?>, Object> map = new HashMap<>();

    public <T> void put(Class<T> clazz, T value) {
        this.map.put(Objects.requireNonNull(clazz), clazz.cast(value));
    }

    public <T> T get(Class<T> clazz) {
        return clazz.cast(this.map.get(clazz));
    }

    public static void main(String[] args) {
        Favorites favorites = new Favorites();
        favorites.put(String.class, "keesun");
        favorites.put(Integer.class, 2);

//        favorites.put(List<Integer>.class, List.of(1, 2, 3));
//        favorites.put(List<String>.class, List.of("a", "b", "c"));

//        List list = favorites.get(List.class);
//        list.forEach(System.out::println);
    }

}
