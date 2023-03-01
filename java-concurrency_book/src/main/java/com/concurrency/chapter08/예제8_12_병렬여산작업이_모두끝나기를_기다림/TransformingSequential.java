package com.concurrency.chapter08.예제8_12_병렬여산작업이_모두끝나기를_기다림;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * TransformingSequential
 * <p/>
 * Transforming sequential execution into parallel execution
 *
 * @author Brian Goetz and Tim Peierls
 */
public abstract class TransformingSequential {

    /**
     * 반복문의 각 차수에 해당하는 작업을 순차적 실행
     * @param elements
     */
    void processSequentially(List<Element> elements) {
        for (Element e : elements)
            process(e);
    }

    /**
     * 반복문의 각 차수에 해당하는 작업을 독립적으로 실행 (병렬 프로그램으로 쉽게 변경)
     * @param exec
     * @param elements
     */
    void processInParallel(Executor exec, List<Element> elements) {
        for (final Element e : elements)
            exec.execute(new Runnable() {
                public void run() {
                    process(e);
                }
            });
    }

    public abstract void process(Element e);

    /**
     * 반복문의 각 단계에서 실행되는 작업이 그 내부에서 재귀적으로 호출했던 작업의
     * 실행 결과를 사용할 필요가 없는 경우가 가장 간단하다.
     * @param nodes
     * @param results
     * @param <T>
     */
    public <T> void sequentialRecursive(List<Node<T>> nodes,
                                        Collection<T> results) {
        /*
           트리 구조를 대상으로 깊이 우선 탐색(DFS)을 실행하면서,
           각 노드에서 연산 작업을 처리하고 연산 결과를 컬렉션에 담도록 되어있다.
         */
        for (Node<T> n : nodes) {
            results.add(n.compute());
            sequentialRecursive(n.getChildren(), results);
        }
    }

    /**
     * sequentialRecursive()를 병렬화한 메서드
     * 깊이 우선 탐색을 진행하지만 각 노드를 방문할 때마다 필요한 결과를 계산하는 것이 아니라,
     * 노드별 값을 계산하는 작업을 생성해 Executro에 등록시킨다.
     * @param exec
     * @param nodes
     * @param results
     * @param <T>
     */
    public <T> void parallelRecursive(final Executor exec,
                                      List<Node<T>> nodes,
                                      final Collection<T> results) {
        for (final Node<T> n : nodes) {
            exec.execute(new Runnable() {
                public void run() {
                    results.add(n.compute());
                }
            });

            parallelRecursive(exec, n.getChildren(), results);
        }
    }

    /**
     * 병렬 연산 작업이 모두 끝나기를 기다리는 예제
     * @param nodes
     * @return
     * @param <T>
     * @throws InterruptedException
     */
    public <T> Collection<T> getParallelResults(List<Node<T>> nodes)
            throws InterruptedException {
        // parallelRecursive에서 사용할 전용 Executor를 하나 생성하고,
        ExecutorService exec = Executors.newCachedThreadPool();
        Queue<T> resultQueue = new ConcurrentLinkedQueue<T>();

        // parallelRecursive를 호출한다.
        parallelRecursive(exec, nodes, resultQueue);

        // shutdown(), awaitTermination() 메서드를 차례로 호출하여 모든 연산 작업이 마무리되기를 기다린다.
        exec.shutdown();
        exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

        return resultQueue;
    }

    interface Element {
    }

    interface Node <T> {
        T compute();

        List<Node<T>> getChildren();
    }
}
