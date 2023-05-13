package me.whiteship.chapter01.item08.cleaner;

import java.lang.ref.Cleaner;
import java.util.ArrayList;
import java.util.List;

public class CleanerIsNotGood {

    /**
     * Phantom Reference와 비슷
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Cleaner cleaner = Cleaner.create();

        List<Object> resourceToCleanUp = new ArrayList<>();
        BigObject bigObject = new BigObject(resourceToCleanUp);
        // cleaner에 등록한다.
        // 어떤 오브젝트가 gc가 될때 아래 task를 사용해서 객체를 해제하라. 라는 명시
        cleaner.register(bigObject, new BigObject.ResourceCleaner(resourceToCleanUp));

        bigObject = null; // 참조 해제
        System.gc(); // gc 발생
        Thread.sleep(3000L);
    }

}
