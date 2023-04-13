package me.whiteship.chapter01.item03.field;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConcertTest {

    @Test
    void perform() {
        /* 가짜 Mock 객체 사용 */
        Concert concert = new Concert(new MockElvis());
        concert.perform();

        assertTrue(concert.isLightsOn());
        assertTrue(concert.isMainStateOpen());
    }

}