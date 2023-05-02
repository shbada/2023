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

    public Map<CacheKey, Post> getCache() {
        return cache;
    }
}
