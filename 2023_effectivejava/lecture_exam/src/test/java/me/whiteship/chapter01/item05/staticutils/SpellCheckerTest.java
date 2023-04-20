package me.whiteship.chapter01.item05.staticutils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpellCheckerTest {

    @Test
    void isValid() {
        // SpellChecker의 dictionary 를 매번 생성한다.
        assertTrue(SpellChecker.isValid("test"));
    }

}