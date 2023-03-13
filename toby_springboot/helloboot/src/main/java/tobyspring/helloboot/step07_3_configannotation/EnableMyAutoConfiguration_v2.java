package tobyspring.helloboot.step07_3_configannotation;

import org.springframework.context.annotation.Import;
import tobyspring.helloboot.step07_2_configannotation.Config;
import tobyspring.helloboot.step07_2_configannotation.autoconfig.DispatcherServletConfig;
import tobyspring.helloboot.step07_2_configannotation.autoconfig.TomcatWebServerConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MyAutoConfigImportSelector.class)
public @interface EnableMyAutoConfiguration_v2 {
}
