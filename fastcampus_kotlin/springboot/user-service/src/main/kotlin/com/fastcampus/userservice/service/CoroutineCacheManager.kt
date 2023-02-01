package com.fastcampus.userservice.service

import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

/**
 * 캐시 매니저
 * 애플리케이션 자체 메모리에 올려보도록 하자
 */
@Component
class CoroutineCacheManager<T> {
    // ConcurrentHashMap
    private val localCache = ConcurrentHashMap<String, CacheWrapper<T>>()

    /**
     * 추가
     */
    suspend fun awaitPut(key: String, value: T, ttl: Duration) {
        localCache[key] = CacheWrapper(cached = value, Instant.now().plusMillis(ttl.toMillis()))
    }

    /**
     * 제거
     */
    suspend fun awaitEvict(key: String) {
        localCache.remove(key)
    }

    suspend fun awaitGetOrPut(
        key: String,
        ttl: Duration? = Duration.ofMinutes(5),
        supplier: suspend () -> T, // 람다를 받음 (suspend 함수)
    ): T {
        val now = Instant.now()
        val cacheWrapper = localCache[key]

        val cached = if (cacheWrapper == null) {
            // put
            CacheWrapper(cached = supplier(), ttl = now.plusMillis(ttl!!.toMillis())).also {
                localCache[key] = it
            }
        } else if (now.isAfter(cacheWrapper.ttl)) {
            // 캐시 ttl이 지난 경우
            localCache.remove(key)

            // put
            CacheWrapper(cached = supplier(), ttl = now.plusMillis(ttl!!.toMillis())).also {
                localCache[key] = it
            }
        } else {
            cacheWrapper
        }

        checkNotNull(cached.cached)
        return cached.cached
    }


    data class CacheWrapper<T>(val cached: T, val ttl: Instant)
}