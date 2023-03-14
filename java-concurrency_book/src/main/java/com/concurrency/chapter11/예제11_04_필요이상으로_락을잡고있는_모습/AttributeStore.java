package com.concurrency.chapter11.예제11_04_필요이상으로_락을잡고있는_모습;

import java.util.*;
import java.util.regex.*;

import net.jcip.annotations.*;

/**
 * AttributeStore
 * <p/>
 * Holding a lock longer than necessary
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class AttributeStore {
    @GuardedBy("this") private final Map<String, String>
            attributes = new HashMap<String, String>();

    /**
     * 동기화가 필요한 부분은 한줄인데, 메서드 전체를 락으로 잡고있다.
     * @param name
     * @param regexp
     * @return
     */
    public synchronized boolean userLocationMatches(String name,
                                                    String regexp) {
        String key = "users." + name + ".location";
        /* 여기만 동기화가 필요 */
        String location = attributes.get(key);
        if (location == null)
            return false;
        else
            return Pattern.matches(regexp, location);
    }
}
