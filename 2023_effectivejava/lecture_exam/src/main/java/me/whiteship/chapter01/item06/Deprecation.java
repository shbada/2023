package me.whiteship.chapter01.item06;

public class Deprecation {

    /**
     * forRemoval = true : 삭제될 예정임을 명시
     * since = "1.2" : 버전값 넣기
     * @deprecated in favor of
     * {@link #Deprecation(String)} // # : 해시태그 (참조)
     */
    @Deprecated(forRemoval = true, since = "1.2")
    public Deprecation() {
    }

    private String name;

    public Deprecation(String name) {
        this.name = name;
    }
}
