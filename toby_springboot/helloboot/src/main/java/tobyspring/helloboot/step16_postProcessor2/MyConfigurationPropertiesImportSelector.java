package tobyspring.helloboot.step16_postProcessor2;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

public class MyConfigurationPropertiesImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        MultiValueMap<String, Object> attr = importingClassMetadata.getAllAnnotationAttributes(EnableMyConfigurationProperties.class.getName());
//        attr.get("value"); // List
        Class propertyClass = (Class) attr.getFirst("value");

        return new String[] { propertyClass.getName() };
    }
}
