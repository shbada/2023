package me.whiteship.chapter01.item08.cleaner;

import java.util.List;

public class BigObject {

    private List<Object> resource; // 정리해줘야하는 객체

    public BigObject(List<Object> resource) {
        this.resource = resource;
    }

    // 정리를 실행 (별도의 Runnable Task로 만든다.)
    // inner class : 반드시 static
    // 위 BigObject를 참조하면 안된다.
    public static class ResourceCleaner implements Runnable {

        private List<Object> resourceToClean;

        public ResourceCleaner(List<Object> resourceToClean) {
            this.resourceToClean = resourceToClean;
        }

        @Override
        public void run() {
            resourceToClean = null;
            System.out.println("cleaned up.");
        }
    }
}
