package com.example.LeaderBoard;

import com.example.LeaderBoard.service.RankingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class SimpleTest {

    @Autowired
    private RankingService rankingService;

    @Test
    void getRanks() {
        // 여긴 좀 걸릴 수 있는데 이 다음부터는 제대로된 속도가 나옴 (테스트를 위한 의미없는 최초 호출 넣음)
        rankingService.getTopRank(1);

        // 1) Get user_100's rank
        // 2)보다 더 오래걸림 (둘다 빠르지만 2)는 끝에서 가져오면 되기 때문에 더 빠름)
        Instant before = Instant.now();
        Long userRank = rankingService.getUserRanking("user_100");
        Duration elapsed = Duration.between(before, Instant.now());

        System.out.println(String.format("Rank(%d) - Took %d ms", userRank, elapsed.getNano() / 1000000));

        // 2) Get top 10 user list
        before = Instant.now();
        List<String> topRankers = rankingService.getTopRank(10);
        elapsed = Duration.between(before, Instant.now());

        System.out.println(String.format("Range - Took %d ms", elapsed.getNano() / 1000000));
    }

    @Test
    void insertScore() {
        for(int i=0; i<1000000; i++) {
            int score = (int)(Math.random() * 1000000); // 0 ~ 999999
            String userId = "user_" + i;

            rankingService.setUserScore(userId, score);
        }
    }

    @Test
    void inMemorySortPerformance() {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0; i<1000000; i++) {
            int score = (int)(Math.random() * 1000000); // 0 ~ 999999
            list.add(score);
        }

        Instant before = Instant.now();
        Collections.sort(list); // nlogn
        Duration elapsed = Duration.between(before, Instant.now());

        // 나노초를 가져오므로 ms 로 변환
        System.out.println((elapsed.getNano() / 1000000) + " ms");
    }
}
