package com.concurrency.chapter04.예제4_04_모니터기반_차량추적_프로그램;

import java.util.*;

import net.jcip.annotations.*;

/**
 * MonitorVehicleTracker
 * <p/>
 * Monitor-based vehicle tracker implementation
 *
 * @author Brian Goetz and Tim Peierls
 *
 * synchronized 동기화를 수행한다.
 */
@ThreadSafe
public class MonitorVehicleTracker {
    @GuardedBy("this") private final Map<String, MutablePoint> locations;

    /**
     * 생성자 : 복사본 제공
     * @param locations
     */
    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint loc = locations.get(id);
        return loc == null ? null : new MutablePoint(loc);
    }

    public synchronized void setLocation(String id, int x, int y) {
        MutablePoint loc = locations.get(id);
        if (loc == null)
            throw new IllegalArgumentException("No such ID: " + id);
        loc.x = x;
        loc.y = y;
    }

    private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
        Map<String, MutablePoint> result = new HashMap<String, MutablePoint>();

        for (String id : m.keySet())
            result.put(id, new MutablePoint(m.get(id)));

        return Collections.unmodifiableMap(result);
    }
}
