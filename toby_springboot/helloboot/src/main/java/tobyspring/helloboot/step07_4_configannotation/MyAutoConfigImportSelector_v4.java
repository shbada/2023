package tobyspring.helloboot.step07_4_configannotation;

import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class MyAutoConfigImportSelector_v4 implements DeferredImportSelector {
    private final ClassLoader classLoader;

    public MyAutoConfigImportSelector_v4(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//        ImportCandidates candidates = ImportCandidates.load(MyAutoConfiguration.class, classLoader);
//        return StreamSupport.stream(candidates.spliterator(), false).toArray(String[]::new);

        List<String> autoConfigs = new ArrayList<>();
//        for (String candidate : ImportCandidates.load(MyAutoConfiguration.class, classLoader)) {
//            autoConfigs.add(candidate);
//        }
        /*
            load() : META_INF/spring/xxx 파일 경로로 읽어온다.
         */
        ImportCandidates.load(MyAutoConfiguration.class, classLoader).forEach(autoConfigs::add);

//        return autoConfigs.toArray(new String[0]); // 빈 array new String[0]
        return autoConfigs.stream().toArray(String[]::new);
    }
}
