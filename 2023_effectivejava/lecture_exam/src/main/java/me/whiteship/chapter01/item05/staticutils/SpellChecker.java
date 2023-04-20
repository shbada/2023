package me.whiteship.chapter01.item05.staticutils;

import me.whiteship.chapter01.item05.DefaultDictionary;
import me.whiteship.chapter01.item05.Dictionary;

import java.util.List;

public class SpellChecker {

    /**
     * 다양한 사전이 있을 수 있는데, 어떤 사전으로 사용할지를 어떻게 할것인가?
     * 가지고있는 리소스에 따라 동작이 달라지는 경우 직접 이렇게 객체를 생성하지 말라.
     */
    private static final Dictionary dictionary = new DefaultDictionary(); // 자원을 직접 명시

    private SpellChecker() {}

    public static boolean isValid(String word) {
        // TODO 여기 SpellChecker 코드
        return dictionary.contains(word);
    }

    public static List<String> suggestions(String typo) {
        // TODO 여기 SpellChecker 코드
        return dictionary.closeWordsTo(typo);
    }
}
