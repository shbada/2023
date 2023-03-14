package tobyspring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

public class ConditionalTest {
    @Test
    void conditional() {
        // true
        ApplicationContextRunner runner = new ApplicationContextRunner();
        runner.withUserConfiguration(Config1.class)
                .run(context -> { // 빈이 포함되어있는가?
                    Assertions.assertThat(context).hasSingleBean(MyBean.class);
                });

//        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
//        ac.register(Config1.class);
//        ac.register();
//
//        MyBean bean = ac.getBean(MyBean.class);


        // false
//        ApplicationContextRunner runner2 = new ApplicationContextRunner();
//        runner2.withUserConfiguration(Config2.class)
//                .run(context -> { // 빈이 포함되어있는가?
//                    Assertions.assertThat(context).hasSingleBean(MyBean.class);
//                });

        new ApplicationContextRunner().withUserConfiguration(Config2.class)
                .run(context -> { // 빈이 포함되어있는가?
                    Assertions.assertThat(context).hasSingleBean(MyBean.class);
                    Assertions.assertThat(context).hasSingleBean(Config1.class);
                });
//        AnnotationConfigWebApplicationContext ac2 = new AnnotationConfigWebApplicationContext();
//        ac2.register(Config2.class);
//        ac2.register();
//
//        MyBean bean2 = ac2.getBean(MyBean.class);
    }

    // 1. 어노테이션
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(TrueCondition.class)
    @interface  TrueConditional {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(FalseCondition.class)
    @interface  FalseConditional {}

    // 2. element 지정 방식
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(BooleanCondition.class)
    @interface  BooleanConditional {
        boolean value();
    }

    @Configuration
//    @Conditional(TrueCondition.class)
//    @TrueConditional
    @BooleanConditional(true)
    static class Config1 {
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }

    @Configuration
//    @Conditional(FalseCondition.class)
    @FalseConditional
    static class Config2 {
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }

    static class MyBean {}

    static class TrueCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return true;
        }
    }

    static class FalseCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return false;
        }
    }

    static class BooleanCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Map<String, Object> map = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
            return (Boolean) map.get("value"); // 속성 가져오기
        }
    }
}
