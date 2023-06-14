package com.example.LeaderBoard.controller;

import com.example.LeaderBoard.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 리더보드(Leaderboard)
 * - 게임이나 경쟁에서 상위 참가자의 랭킹과 점수를 보여주는 기능
 * - 순위로 나타낼 수 있는 다양한 대상에 응용(최다 구매 상품, 리뷰 순위 등)
 * - 그룹 상위 랭킹 또는 특정 대상의 순위를 보여준다.
 *
 * 빠른 업데이트, 빠른 조회가 필요
 *
 * [Redis]
 * - 순위 데이터에 적합한 Sorted-Set의 자료구조를 사용하면 score를 통해 자동으로 정렬됨
 * - 빈번한 액세스에 유리한 In-memory DB의 속도
 */
@RestController
public class ApiController {

    @Autowired
    private RankingService rankingService;

    /**
     * 점수 생성/업데이트
     * @param userId
     * @param score
     * @return
     */
    @GetMapping("/setScore")
    public Boolean setScore(
        @RequestParam String userId,
        @RequestParam int score
    ){
        return rankingService.setUserScore(userId, score);
    }

    /**
     * 특정 대상 순위 조회
     * @param userId
     * @return
     */
    @GetMapping("/getRank")
    public Long getUserRank(
            @RequestParam String userId
    ) {
        return rankingService.getUserRanking(userId);
    }

    /**
     * 상위 랭크 조회
     * @return
     */
    @GetMapping("/getTopRanks")
    public List<String> getTopRanks() {
        return rankingService.getTopRank(3);
    }
}
