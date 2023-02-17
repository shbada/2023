package com.concurrency.chapter04.예제4_12_내부상태_안전하게공개;

import java.util.*;
import java.util.concurrent.*;

import com.concurrency.chapter04.예제4_11_값변경가능_스레드안전.SafePoint;
import net.jcip.annotations.*;

/**
 * PublishingVehicleTracker
 * <p/>
 * Vehicle tracker that safely publishes underlying state
 *
 * @author Brian Goetz and Tim Peierls
 *
 * 내부 상태를 안전하게 공개하는 차량 추적 프로그램
 *
 *
 */
@ThreadSafe
public class PublishingVehicleTracker {
    private final Map<String, SafePoint> locations;
    private final Map<String, SafePoint> unmodifiableMap;

    /**
     * 스레드 안정성을 ConcurrentHashMap 클래스에게 위임해서 전체적으로 스레드 안정성을 확보
     * 이번에는 Map 내부에 들어있는 값도 변경 불가능한 클래스 대신 스레드 안전하고 변경 가능한 SafePoint 클래스를 사용한다.
     * 외부에서 호출하는 프로그램은 차량을 추가하거나 삭제할 수는 없지만 Map 클래스를 가져가서,
     * 내부에 들어있는 SafePoint의 값을 수정하면 차량의 위치를 변경할 수는 있다.
     * 이와 같이 Map이 동적으로 연동된다는 특성은 고객이 요구하는 요구 사항에 따라 장점일수도 단점일수도 있다.
     *
     * 이 클래스도 스레드 안정성은 확보하지만, 만약 차량의 제약사항을 추가해야 한다면 스레드 안정성을 해칠 수 있다.
     * 외부 프로그램이 차량의 위치를 변경하고자 할때 변경 값을 반영하지 못하도록 거부하거나,
     * 변경 사항을 반영하도록 선택할 수 있어야한다면 이는 아직 충분하지않다.
     * @param locations
     */
    public PublishingVehicleTracker(Map<String, SafePoint> locations) {
        this.locations = new ConcurrentHashMap<String, SafePoint>(locations);
        this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
    }

    public Map<String, SafePoint> getLocations() {
        return unmodifiableMap;
    }

    public SafePoint getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (!locations.containsKey(id)) {
            throw new IllegalArgumentException("invalid vehicle name: " + id);
        }

        locations.get(id).set(x, y);
    }
}
