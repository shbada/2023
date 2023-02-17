package com.concurrency.chapter04.예제4_06_스레드안전_추적_프로그램;

import net.jcip.annotations.*;

/**
 * Point
 * <p/>
 * Immutable Point class used by DelegatingVehicleTracker
 *
 * @author Brian Goetz and Tim Peierls
 *
 * 불변 클래스
 * -> 스레드 안전하다.
 * 불변의 값은 얼마든지 마음대로 안전하게 공하고 외부에 공개할 수 있으므로, 위치를 알려달라는 외부 프로그램에게 객체 인스턴스를
 * 복사해줄 필요가 없다.
 */
@Immutable
public class Point {
    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

