package me.whiteship.chapter01.item07.cache;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostRepositoryTest {

    @Test
    void cache() throws InterruptedException {
        PostRepository postRepository = new PostRepository();
        CacheKey key1 = new CacheKey(1);
        postRepository.getPostById(key1);

        // 비어있지 않겠지. 1이 있으니깐
        assertFalse(postRepository.getCache().isEmpty());

//        key1 = null;
        // TODO run gc
        System.out.println("run gc");
        System.gc();
        System.out.println("wait");
        Thread.sleep(3000L);

        // CacheKey key1 = new CacheKey(1);를 PostRepository 내부 메서드에 선언했을 경우
        // HashMap : gc() 호출했어도 캐시가 비어있지 않다.
        // WeakHashMap : cacheKey를 참조하는 곳이 없으므로 사라진다.
        // CacheKey key1 = new CacheKey(1);를 해당 테스트코드에서 수행 후 매개변수로 넘길경우,
        // 이 메서드가 끝날때까진 CacheKey가 유효하므로 weakReference 여도 참조해제되지 않음
        // ->  key1 = null; 를 해당 테스트 메서드 안에서 선언하면 됨
        assertTrue(postRepository.getCache().isEmpty());
    }

//    @keySetTest
//    void backgroundThread() throws InterruptedException {
//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//        PostRepository postRepository = new PostRepository();
//        CacheKey key1 = new CacheKey(1);
//        postRepository.getPostById(key1);
//
        // 주기적으로 가장 오래된 캐시를 제거해줌
        // 별도의 스레드가 3초마다 수행되면서 캐시를 정리해준다.
//        Runnable removeOldCache = () -> {
//            System.out.println("running removeOldCache task");
//            Map<CacheKey, Post> cache = postRepository.getCache();
//            Set<CacheKey> cacheKeys = cache.keySet();
//            Optional<CacheKey> key = cacheKeys.stream().min(Comparator.comparing(CacheKey::getCreated));
//            key.ifPresent((k) -> {
//                System.out.println("removing " + k);
//                cache.remove(k);
//            });
//        };
//
//        System.out.println("The time is : " + new Date());
//
//        executor.scheduleAtFixedRate(removeOldCache,
//                1, 3, TimeUnit.SECONDS);
//
//        Thread.sleep(20000L);
//
//        executor.shutdown();
//    }

}