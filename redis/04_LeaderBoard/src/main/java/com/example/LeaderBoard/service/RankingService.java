package com.example.LeaderBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * ZSet
 * score 기준
 */
@Service
public class RankingService {

    private static final String LEADERBOARD_KEY = "leaderBoard";

    @Autowired
    StringRedisTemplate redisTemplate;


    public boolean setUserScore(String userId, int score) {
        // RedisTemplate을 사용하여 ZSet(정렬된 집합)에 대한 ZSetOperations 인스턴스를 얻는다.
        ZSetOperations zSetOps = redisTemplate.opsForZSet();

        // set
        // ZSet에 데이터를 추가
        zSetOps.add(LEADERBOARD_KEY, userId, score);

        return true;
    }

    public Long getUserRanking(String userId) {
        ZSetOperations zSetOps = redisTemplate.opsForZSet();

        // 특정 멤버(userId)의 역순 순위를 조회
        Long rank = zSetOps.reverseRank(LEADERBOARD_KEY, userId);

        return rank;
    }

    public List<String> getTopRank(int limit) {
        ZSetOperations zSetOps = redisTemplate.opsForZSet();

        // 내림차순
        // ZSet에서 0부터 limit - 1까지의 범위 내의 멤버를 내림차순으로 조회
        Set<String> rangeSet = zSetOps.reverseRange(LEADERBOARD_KEY, 0, limit - 1);

        // set to arrayList
        return new ArrayList<>(rangeSet);
    }
}
