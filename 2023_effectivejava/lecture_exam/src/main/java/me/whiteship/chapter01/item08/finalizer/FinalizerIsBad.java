package me.whiteship.chapter01.item08.finalizer;

public class FinalizerIsBad {

    /**
     * finalize() 메서드 오버라이딩
     * Object.finalize()
     * @Deprecated(since="9") // 9버전 이후로 사용하지 말라고 명시됨
     * protected void finalize() throws Throwable { }
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.print("");
    }
}
