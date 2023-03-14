package tobyspring.helloboot.step09_2_classutils;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;
import tobyspring.helloboot.step09_2_classutils.ConditionalMyOnClass;

import java.util.Map;

public class MyOnClassCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(ConditionalMyOnClass.class.getName());
        String value = (String) annotationAttributes.get("value");

        // 위 해당하는 이름에 존재한다면 true, 아니면 false
        return ClassUtils.isPresent(value, context.getClassLoader());
    }
}
