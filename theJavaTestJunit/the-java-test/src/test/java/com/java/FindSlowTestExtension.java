package com.java;

import com.java.annotations.SlowTest;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;

public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private final long THRESHOLD;

    public FindSlowTestExtension(long THRESHOLD) {
        this.THRESHOLD = THRESHOLD;
    }



    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        String className = context.getRequiredTestClass().getName();
        String methodName = context.getRequiredTestMethod().getName();
        ExtensionContext.Store store = context.getStore(ExtensionContext.Namespace.create(className, methodName));
        store.put("START_TIME", System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method requiredTestMethod = context.getRequiredTestMethod();
        SlowTest annotation = requiredTestMethod.getAnnotation(SlowTest.class);

        String className = context.getRequiredTestClass().getName();
        String methodName = context.getRequiredTestMethod().getName();
        ExtensionContext.Store store = context.getStore(ExtensionContext.Namespace.create(className, methodName));
        long start_time = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - start_time;

        // 테스트가 1초이상 걸릴 경우 아래 메시지가 출력된다.
        if (duration > THRESHOLD && annotation == null) { // +어노테이션이 없는 경우
            System.out.printf("Please consider mark method [%s] with @SlowTest \n", methodName);
        }
    }
}
