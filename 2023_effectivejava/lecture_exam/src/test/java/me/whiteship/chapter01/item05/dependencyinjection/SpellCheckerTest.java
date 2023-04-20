package me.whiteship.chapter01.item05.dependencyinjection;

import me.whiteship.chapter01.item05.DefaultDictionary;
import me.whiteship.chapter01.item05.Dictionary;
import me.whiteship.chapter01.item05.MockDictionary;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

class SpellCheckerTest {

    @Test
    void isValid() {
        // 가짜 Dictionary 만들수 있다
        SpellChecker spellChecker = new SpellChecker(MockDictionary::new);
        spellChecker.isValid("test");

        // Supplier 사용하기
        Supplier<Dictionary> dictionarySupplier1 = () -> new DefaultDictionary();
        Supplier<Dictionary> dictionarySupplier2 = DefaultDictionary::new;
    }

}