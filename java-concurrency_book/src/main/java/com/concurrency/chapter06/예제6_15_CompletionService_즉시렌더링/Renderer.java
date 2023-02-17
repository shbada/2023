package com.concurrency.chapter06.예제6_15_CompletionService_즉시렌더링;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.concurrency.chapter05.예제5_12_FutureTask_필요한데이터_미리_읽기.LaunderThrowable.launderThrowable;

/**
 * CompletionService 활용
 * 1) 전체 실행되는 시간을 줄일 수 있다.
 * 2) 응답 속도도 높일 수 있다.
 *
 * 각각의 이미지 파일을 다운로드 받는 작업을 생성하고, Executor를 활용해 다운로드 작업을 실행한다.
 * 이렇게하면 이전에 순서대로 다운로드하던 부분을 병렬화하는 것이고, 이미지 파일을 전부 다운로드 받는데 걸리는 전체 시간을 줄일 수 있다.
 * 다운로드 받은 이미지는 CompletionService를 통해 찾아가도록 하면,
 * 이미지 파일을 다운로드 받는 순간 해당하는 위치에 그림을 그려 넣을 수 있다.
 *
 * 여러개의 ExecutorCompletionService에서 동일한 Executor를 공유해 사용할 수 있다.
 * 따라서 실행을 맡은 Executor는 하나만 두고 동일한 작업을 처리하는 여러가지 ExecutorCompletionService를 생성해 사용하는 일도 충분히 가능하다.
 */

/**
 * Renderer
 * <p/>
 * Using CompletionService to render page elements as they become available
 *
 * @author Brian Goetz and Tim Peierls
 */
public abstract class Renderer {
    private final ExecutorService executor;

    Renderer(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) {
        final List<ImageInfo> info = scanForImageInfo(source);
        CompletionService<ImageData> completionService =
                new ExecutorCompletionService<ImageData>(executor);
        for (final ImageInfo imageInfo : info)
            /**
             * submit()
            */
            completionService.submit(new Callable<ImageData>() {
                public ImageData call() {
                    return imageInfo.downloadImage();
                }
            });

        renderText(source);

        try {
            for (int t = 0, n = info.size(); t < n; t++) {
                /**
                 * take()
                 */
                Future<ImageData> f = completionService.take();
                ImageData imageData = f.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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

