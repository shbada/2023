package com.section2.di;

import java.util.Arrays;

public class ContainerService {
    public static <T> T getObject(Class<T> classType) {
        T instance = createInstance(classType);
        Arrays.stream(classType.getDeclaredFields()).forEach(f -> {
            if (f.getAnnotation(Inject.class) != null) {
                Object fieldInstance = createInstance(f.getType());// bookRepository
                f.setAccessible(true);
                try {
                    f.set(instance, fieldInstance); // 채워준다.
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return instance;
    }

    private static <T> T createInstance(Class<T> classType) {
        try {
            return classType.getConstructor(null).newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
