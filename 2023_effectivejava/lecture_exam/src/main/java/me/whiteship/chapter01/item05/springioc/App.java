package me.whiteship.chapter01.item05.springioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    /**
     * ApplicationContext, BeanFactory
     * @param args
     */
    public static void main(String[] args) {
        // Spring이 모르는 객체
//        SpellChecker spellChecker = new SpellChecker();

        // Spring이 관리하는 객체
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // 싱글톤
        SpellChecker spellChecker = applicationContext.getBean(SpellChecker.class);
        spellChecker.isValid("test");
    }
}
