package com.concurrency.chapter06.예제6_13_Future_이미지_다운로드작업_기다림;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.concurrency.chapter05.예제5_12_FutureTask_필요한데이터_미리_읽기.LaunderThrowable.launderThrowable;

/**
 * Future은 특정 작업이 정상적으로 완료됐는지, 아니면 취소됐는지 등에 대한 정보를 확인할 수 있도록 만들어진 클래스다.
 * Future가 동작하는 사이클에서 염두해야할 점은, 한번 지나간 상태는 되돌릴수 없다. 완료된 작업은 완료 상태에 영원히 머무른다.
 *
 * Future의 get()
 * - 작업이 진행되는 상태에 따라 다른 유형으로 동작한다.
 *   작업이 완료 상태라면 get 메서드를 호출했을때 즉시 결과값을 리턴하거나 Exception을 발생시킨다.
 *   반면, 아직 작업을 시작하지 않았거나 작업이 실행되고 있는 상태라면, 작업이 완료될때까지 대기한다.
 *   작업이 모두 끝난 상태에 Exception이 발생했었다면 get 메서드는 원래 발생했던 Exception을 ExcecutionException이라는 예외 클래스에 담아 던진다.
 *   작업이 중간에 취소됬다면 cancelationException이 발생한다.
 *   get 메서드에서 ExecutionException이 발생한 경우 원래 발생했던 오류는 ExecutionException의 getCause 메서드로 확인할 수 있다.
 *
 *  [아래 예제]
 *  1) 프로그램 내부에서 진행되는 작업을 둘로 나눈다.
 *  - 텍스트를 그려넣는 작업은 CPU를 많이 사용하고, 이미지를 다운로드 받는 작업은 I/O 부분을 많이 사용한다.
 *  - 작업을 이와 같이 둘로 나누면 단일 CPU를 사용하는 시스템에서도 성능을 향상시킬 수 있다.
 *
 *  Future.get 메서드를 감싸고있는 오류 처리 구문에서는 발생할 수 있는 두가지 가능성에 모두 대응할 수 있어야한다.
 *  1) Exception이 발생하는 경우
 *  2) 결과값을 얻기 전에 get 메서드를 호출해 대기하던 메인 스레드가 인터럽트되는 경우
 *
 *  아래 예제에서는 이미지 파일을 다운로드 하면서, 그와 동시에 텍스트 본문을 이미지로 그려넣는다.
 *
 *  [주의점]
 *  특정 스레드에 일정한 유형의 작업을 모두 맡겨버리는 정책은 그다지 확장성이 좋지 않다.
 *  만약 주방에 일할 사람 여럿이 추가로 투입되면 작업 방법을 재구성하지 않는 한 모든 사람이 최대한 바쁘게 효과적으로 일하도록 하기가 힘들다.
 *  전체적인 성능을 떨어뜨릴 수도 있다.
 *  다양한 종류의 작업을 여럿 작업 스레드에서 나눠 처리하도록 할때는 나눠진 작업이 일정한 크기를 유지하지 못할수도 있다는 단점이 있다.
 *  결과적으로 여러개의 작업 스레드가 하나의 자원을 나눠 실행시킬때는 항상 작업 스레드간에 필요한 내용을 조율하는데 일부 자원을 소모하게된다.
 *  따라서 작업을 잘개 쪼개는 의미를 찾으려면 병렬로 처리해서 얻을 수 있는 성능상의 이득이 이와 같은 부하를 훨씬 넘어서야한다.
 */

/**
 * FutureRenderer
 * <p/>
 * Waiting for image download with \Future
 *
 * @author Brian Goetz and Tim Peierls
 */
public abstract class FutureRenderer {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    void renderPage(CharSequence source) {
        final List<ImageInfo> imageInfos = scanForImageInfo(source);
        /**
         * 이미지 다운로드 작업
         */
        Callable<List<ImageData>> task =
                new Callable<List<ImageData>>() {
                    public List<ImageData> call() {
                        List<ImageData> result = new ArrayList<ImageData>();
                        for (ImageInfo imageInfo : imageInfos)
                            result.add(imageInfo.downloadImage());
                        return result;
                    }
                };

        Future<List<ImageData>> future = executor.submit(task);

        /**
         * 텍스트 그려넣는 작업
         */
        renderText(source);

        try {
            /**
             * 이미지 다운로드 작업을 기다림
             * -> 이미지 다운로드보다 텍스트 그려넣는 작업이 훨씬 더 빨리 끝난다면, 여기서 기다려야하기 때문에
             * 순차적인 실행보다 그다지 빠르지는 않을 수 있다. 프로그램의 구조만 복잡해진다.
             */
            List<ImageData> imageData = future.get();
            for (ImageData data : imageData)
                renderImage(data);
        } catch (InterruptedException e) {
            // Re-assert the thread's interrupted status
            Thread.currentThread().interrupt();
            // We don't need the result, so cancel the task too
            future.cancel(true);
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }

    interface ImageData {
    }

    interface ImageInfo {
        ImageData downloadImage();
    }

    abstract void renderText(CharSequence s);

    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    abstract void renderImage(ImageData i);
}

