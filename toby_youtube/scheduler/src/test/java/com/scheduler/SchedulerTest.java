package com.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.scheduling.config.ScheduledTask;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class SchedulerTest {
    @SpyBean // 실제로 테스트 동작때 일어나는 일들을 저장하고 검증
    private ScheduledTasks scheduledTasks;

    @Test
    void reportCurrentTime() {
        // 최대 10초까지 기다리면서 verify 조건이 만족할때까지 기다린다.
        // 만족하면 성공, 아니면 실패
        await().atMost(10, SECONDS).untilAsserted(() -> {
            // 10초까지 기다리면서 최소 2번 호출되어야한다. (reportCurrentTime 메서드가)
            verify(scheduledTasks, atLeast(2)).reportCurrentTime();
        });
    }
}
