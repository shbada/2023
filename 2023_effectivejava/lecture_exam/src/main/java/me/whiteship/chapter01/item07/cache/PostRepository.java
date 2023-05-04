package me.whiteship.chapter01.item07.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class PostRepository {

    private Map<CacheKey, Post> cache;

    public PostRepository() {
        /*
            WeakHashMap
            - weakReference를 key로 가진다.
            - Reference에는 soft, strong 등이 있다.
            - key가 더이상 참조가 안되면 그 key로 가지고있는 데이터를 map에서 삭제한다.
            (가비지 컬렉션의 대상이 된다.)
         */
        this.cache = new WeakHashMap<>();
    }

    /**
     * 조회할때마다 캐시에 쌓인다.
     * 지워주는 기능은 없다.
     * @param key
     * @return
     */
    public Post getPostById(CacheKey key) {
//        CacheKey key1 = new CacheKey(1);
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else {
            // TODO DB에서 읽어오거나 REST API를 통해 읽어올 수 있습니다.
            Post post = new Post();
            cache.put(key, post);
            return post;
        } // LRU (가장 최근에 사용된 캐시) 자료구조
    }

    /**
     * Integer로 받았다고 해보자.
     * WeakHashMap 을 쓸때 Custom한 레퍼런스 타입을 쓸때는 null로 참조를 해제해주거나의 등을 하면 gc의 대상이 된다.
     * 하지만 WeakHashMap 을 쓸때 Integer일때는 (Wrapper 타입, 원시타입 등)일때는 JVM 내에서 캐싱하기 때문에 gc의 대상이 되지않는다.
     * (원시타입은 어차피 map의 key가 될수 없음)
     * @param key
     * @return
     */
    public Post getPostById(Integer key) {
//        if (cache.containsKey(key)) {
//            return cache.get(key);
//        } else {
//            // TODO DB에서 읽어오거나 REST API를 통해 읽어올 수 있습니다.
//            Post post = new Post();
//            cache.put(key, post);
//            return post;
//        } // LRU (가장 최근에 사용된 캐시) 자료구조
        return new Post();
    }

    public Map<CacheKey, Post> getCache() {
        return cache;
    }
}
