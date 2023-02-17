package com.concurrency.chapter04.예제4_06_스레드안전_추적_프로그램;

import java.util.*;
import java.util.concurrent.*;
import java.awt.Point;

import net.jcip.annotations.*;

/**
 * DelegatingVehicleTracker
 * <p/>
 * Delegating thread safety to a ConcurrentHashMap
 *
 * @author Brian Goetz and Tim Peierls
 *
 * 별다른 통기화를 하지 않는다.
 * 모든 동기화 작업은 ConcurrentHashMap 에서 담당하고, Map에 들어있는 모든 값은 불변 상태다.
 */
@ThreadSafe
public class DelegatingVehicleTracker {
    private final ConcurrentMap<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String, Point> points) {
        locations = new ConcurrentHashMap<String, Point>(points);
        unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    /**
     * 불변 객체인 Point 대신 불변 객체가 아닌 MutablePoint를 사용했다면
     * getLocations 메소드를 호출한 외부 프로그램에게 내부의 변경 가능한 상태를 그대로 공개하기 때문에,
     * 스레드 안정성이 깨질 수 있다.
     *
     * 언제든지 가장 최신의 차량 위치를 실시간으로 확인할 수 있는 동적인 데이터를 넘겨준다.
     * 다시 말하자면 스레드 A가 getLocations 메서드를 호출하여 값을 가져간 다음,
     * 스레드 B가 특정 차량의 위치를 변경하면 이전에 스레드 A가 받아갔떤 Map에서도 스레드 B가 새로 변경한 값을
     * 동일하게 사용이 가능하다.
     */
    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }

    public Point getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (locations.replace(id, new Point(x, y)) == null)
            throw new IllegalArgumentException("invalid vehicle name: " + id);
    }

    /**
     * 단순 복사본을 넘겨준다.
     * Map 내부에 들어있는 내용이 모두 불변이기 때문에 내부의 데이터가 아닌 구조만 복사돼야한다.
     * (HashMap을 결과로 만들어내는데, 일반 getLocations 메서드가 그 결과로 스레드 안전한 Map을 만들어내도록 설계하지 않았기 때문이다.)
     */
    // Alternate version of getLocations (Listing 4.8)
    public Map<String, Point> getLocationsAsStatic() {
        return Collections.unmodifiableMap(
                new HashMap<String, Point>(locations));
    }
}
