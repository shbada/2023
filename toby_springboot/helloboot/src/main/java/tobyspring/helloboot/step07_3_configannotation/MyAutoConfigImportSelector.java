package tobyspring.helloboot.step07_3_configannotation;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyAutoConfigImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {
                "tobyspring/helloboot/step07_2_configannotation/autoconfig/DispatcherServletConfig.java",
                "tobyspring/helloboot/step07_2_configannotation/autoconfig/TomcatWebServerConfig.java"
        };
    }
}
