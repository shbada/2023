package com.concurrency.chapter06.예제6_10_페이지내용_순차적_렌더링;

import java.util.*;

/**
 * 순차적 페이지 렌더링
 *
 * 순차적으로 처리하므로 사용자 입장에서 화면이 전부 표시될때까지 한참 기다려야한다.
 *
 * 이미지를 다운로드 받는 방법은 I/O 작업이며,
 * 요청한 데이터를 전송받을때까지 대기하는 시간이 상당히 많이 걸리지만 대기하는 시간동안 실제로 CPU가 하는 일은 별로 없다.
 * 따라서 이와같은 순차적인 방법은 CPU의 능력을 제대로 활용하지 못하는 경우가 많으며,
 * 사용자는 똑같은 내용을 보기위해 불필요하게 많은 시간을 기다려야한다.
 * 여기에서 처리해야할 큰 작업을 작은 단위의 작업으로 쪼개서 동시에 실행할 수 있도록 한다면 CPU도 훨씬 잘 활용할 수 있고,
 * 처리속도와 응답속도도 개선할 수 있다.
 */

/**
 * SingleThreadRendere
 * <p/>
 * Rendering page elements sequentially
 *
 * @author Brian Goetz and Tim Peierls
 */
public abstract class SingleThreadRenderer {
    /**
     * 순차적!
     * @param source
     */
    void renderPage(CharSequence source) {
        renderText(source);
        List<ImageData> imageData = new ArrayList<ImageData>();
        for (ImageInfo imageInfo : scanForImageInfo(source))
            imageData.add(imageInfo.downloadImage());
        for (ImageData data : imageData)
            renderImage(data);
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

