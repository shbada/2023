package tobyspring.helloboot.step07_4_configannotation;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

//public class MyAutoConfigImportSelector_v3 implements DeferredImportSelector, BeanClassLoaderAware {
//    @Override
//    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//        ImportCandidates.load(MyAutoConfiguration.class, classLoader);
//    }
//
//    @Override
//    public void setBeanClassLoader(ClassLoader classLoader) {
//        // classLoader 주입
//    }
//}
