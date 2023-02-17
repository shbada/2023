package com.concurrency.chapter06.예제6_14_ExecutorCompletionService의_QueueingFuture;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;

/**
 * 처리해야할 작업을 갖고있고, 이 작업을 모두 Executor에 등록해 싫랭시킨 다음,
 * 각 작업에서 결과가 나오는 즉시, 그 값을 가져다 사용하고자 한다면, 등록한 각 작업별로 Future 객체를 정리해두고,
 * 타임아웃에 0을 지정해 get 메서드를 호출하면서 결과가 나왔는지를 폴링(polling)해 결과를 찾아올 수 있다.
 * 이는 깔끔한 방법은 아니다.
 * 다행이 이런 작업을 위해 미리 만들어져있는 방법이 있다. -> 완료 서비스(completion service)
 *
 * [CompletionService]
 * - Executor 기능과 BlockingQueue의 기능을 하나로 모은 인터페이스
 * - 필요한 Callable 작업을 등록하여 실행시킬 수 있고, take, poll과 같은 큐 메서드를 사용하여 작업이 완료되는 순간,
 * 완료된 작업의 Future 인스턴스를 받아올 수 있다.
 * CompletionService를 구현한 클래스로는 ExecutorCompletionService가 있다. (등록된 작업은 Executor를 통해 실행한다.)
 *
 * [ExecutorCompletionService]
 * - 생성 메서드에서 완료된 결과 값을 쌓아둘 BlockingQueue를 생성한다.
 * - FutureTask에는 done 메서드가 있는데, FutureTask의 작업이 모두 완료되면 done 메서드가 한번씩 호출된다.
 * - 작업을 처음 등록하면 먼저 FutureTask를 상속받은 QueueingFuture라는 클래스로 변환하는데,
 *   QueueingFuture의 done 메서드에서는 결과를 BlockingQueue에 추가하도록 되어있다.
 *   task, poll 메서드를 호출하면 그대로 BlockingQueue의 해당 메서드로 넘겨 처리한다.
 */
public class CompletionServiceExam {
    public static void main(String[] args) {
//        CompletionService
//        ExecutorCompletionService
    }
}
