package me.whiteship.chapter01.item05.dependencyinjection;

import me.whiteship.chapter01.item05.Dictionary;

import java.util.List;
import java.util.function.Supplier;

public class SpellChecker {

    /**
     * Dictionary 가 인터페이스여야한다.
     */
    private final Dictionary dictionary;

    public SpellChecker(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Supplier 인터페이스를 사용할 수 있다.
     * 만약 Supplier<DefaultDictionary>로 되어있다면, 타입한정자 Supplier<? extends Dictionary> 로 해서 Dictionary 하위 타입을 받자
     * @param dictionarySupplier
     */
    public SpellChecker(Supplier<? extends Dictionary> dictionarySupplier) {
        // 아무것도 받는건 없고 리턴만 한다.
        this.dictionary = dictionarySupplier.get();
    }

    public boolean isValid(String word) {
        // TODO 여기 SpellChecker 코드
        return dictionary.contains(word);
    }

    public List<String> suggestions(String typo) {
        // TODO 여기 SpellChecker 코드
        return dictionary.closeWordsTo(typo);
    }
}
