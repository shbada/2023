package com.section4.annotation;

import org.springframework.javapoet.ClassName;
import org.springframework.javapoet.JavaFile;
import org.springframework.javapoet.MethodSpec;
import org.springframework.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

/*
[META-INF.services 대체]
auto-service dependency 추가 (google)
아래 어노테이션 추가
클래스 컴파일 시점에 META-INF.services 를 자동으로 만들어준다.
컴파일 하고 만들어주기 때문에 컴파일을 먼저 하고 META-INF.services를 설정하여 오류 발생 방지한다.
 */
//@AutoService(Processor.class)
public class MagicMojaProcessor extends AbstractProcessor {
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Magic.class.getName());
    }

    /**
     * 여러 round 에 걸쳐서 처리한다.
     * 각 round 마다 특정한 어노테이션들이 이 프로세서가 처리한 (어노테이션을 달고있는) 클래스를 찾으면 처리
     *
     * true를 리턴하면 이 어노테이션을 처리한거다.
     * @param annotations the annotation interfaces requested to be processed
     * @param roundEnv  environment for information about the current and prior round
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // Magic 을 가진 모든 element
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Magic.class);

        // 인터페이스만 붙이길 원함
        for (Element element : elementsAnnotatedWith) {
            if (element.getKind() != ElementKind.INTERFACE) {
                // 인터페이스가 아니라면 에러
                // 컴파일때 에러로 알 수 있다.
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Magic annotation can not be used on " + element.getSimpleName());
            } else {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing " + element.getSimpleName());
            }
        }

        TypeElement typeElement = (TypeElement) elementsAnnotatedWith;
        ClassName className = ClassName.get(typeElement);

        /* 메서드 설정 변경 */
        MethodSpec pollOut = MethodSpec.methodBuilder("pollOut")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement("return $S", "Rabbit!")
                .build();

        /* 클래스 이름 MagicMoja(simpleName) 지정 */
        // new MajicMoja(); 이 코드에서 어노테이션 프로세싱을 통해 만들어낼 클래스
        TypeSpec magicMoja = TypeSpec.classBuilder("MagicMoja")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(className) // 슈퍼타입 지정
                .addMethod(pollOut)
                .build();

        Filer filer = processingEnv.getFiler();

        try {
            JavaFile.builder(className.packageName(), magicMoja)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "FATAL ERROR " + e);
        }

        return true;
    }
}
