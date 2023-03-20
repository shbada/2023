package tobyspring.helloboot.step15_postProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import tobyspring.helloboot.step07_4_configannotation.MyAutoConfiguration;

import java.util.Map;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;
import static org.springframework.core.annotation.AnnotationUtils.getAnnotationAttributes;

@MyAutoConfiguration
public class PropertyPostProcessorConfig {
    @Bean
    BeanPostProcessor propertyPostProcessor(Environment env) {
        return new BeanPostProcessor() { // 모든 빈 등록이 끝날때마다 수행
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                // MyConfigurationProperties 어노테이션이 있는 빈일 경우에만 수행
                MyConfigurationProperties annotation = findAnnotation(bean.getClass(), MyConfigurationProperties.class);

                if (annotation == null) {
                    return bean;
                }

                Map<String, Object> attrs = getAnnotationAttributes(annotation);
                String prefix = (String) attrs.get("prefix");

//                return Binder.get(env).bindOrCreate("", bean.getClass());

                // prefix 추가
                return Binder.get(env).bindOrCreate(prefix, bean.getClass());
            }
        };
    }
}
