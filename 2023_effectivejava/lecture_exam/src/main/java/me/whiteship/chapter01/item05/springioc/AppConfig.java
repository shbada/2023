package me.whiteship.chapter01.item05.springioc;

import me.whiteship.chapter01.item05.Dictionary;
import me.whiteship.chapter01.item05.dependencyinjection.SpellChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration // 스프링이 제공해주는 어노테이션 (자바설정 파일임을 명시)
// AppConfig 이 클래스가 위치한 곳에 있는 패키지부터 컴포넌트를 찾겠다는 의미
@ComponentScan(basePackageClasses = AppConfig.class)
public class AppConfig {
    // @ComponentScan(basePackageClasses = AppConfig.class) 추가로 주석처리
//    @Bean
//    public SpellChecker spellChecker(Dictionary dictionary) {
//        return new SpellChecker(dictionary);
//    }
//
//    @Bean
//    public Dictionary dictionary() {
//        return new SpringDictionary();
//    }
}
