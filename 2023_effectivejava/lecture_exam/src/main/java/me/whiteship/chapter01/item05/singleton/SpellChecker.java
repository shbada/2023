package me.whiteship.chapter01.item05.singleton;

import me.whiteship.chapter01.item05.DefaultDictionary;
import me.whiteship.chapter01.item05.Dictionary;

import java.util.List;

/**
 * 싱글톤 작성 방법도 있는데, 이것도 문제는 마찬가지다.
 * 본인이 사용하는 리소스를 안에 직접 정의해버리므로 테스트하기 어려워진다.
 */
public class SpellChecker {

    private final Dictionary dictionary = new DefaultDictionary();

    private SpellChecker() {}

    public static final SpellChecker INSTANCE = new SpellChecker();

    public boolean isValid(String word) {
        // TODO 여기 SpellChecker 코드
        return dictionary.contains(word);
    }

    public List<String> suggestions(String typo) {
        // TODO 여기 SpellChecker 코드
        return dictionary.closeWordsTo(typo);
    }
}
