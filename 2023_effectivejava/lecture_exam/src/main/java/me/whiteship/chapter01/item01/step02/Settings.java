package me.whiteship.chapter01.item01.step02;

import me.whiteship.chapter01.item01.step04_enum.Difficulty;

import java.util.ArrayList;
import java.util.List;

/*
javadoc
mvc javadoc:javadoc
-> target/site/apidocs/index.html
 */
/**
 * 이 클래스의 인스턴스는 #getInstance()를 통해 사용한다.
 * @see #getInstance()
 */
public class Settings {

    private boolean useAutoSteering;

    private boolean useABS;

    private Difficulty difficulty;

    /**
     * public 기본 생성자는 생성자를 선언하지 않으면 기본으로 존재한다.
     * 그러므로 new Settings() 사용하여 어디서든 인스턴스를 생성할 수 있다.
     *
     * [통제해야 한다면?]
     * 1) 생성자를 private 로 변경하여 외부에서 호출할 수 없도록 한다.
     * -> 상속 불가능
     */
    private Settings() {}

    /**
     * 미리 만들어놓는다.
     * 정적 팩토리 메서드로 외부에서 사용할 수 있다.
     */
    private static final Settings SETTINGS = new Settings();

    /**
     * 정적 팩토리 사용
     * newInstance 명칭으로 사용
     *
     * [플라이웨이트 패턴]과 비슷하다.
     * 미리 자주 사용하는 객체들을 만들어놓고, 필요한 객체르 꺼내다 쓴다.
     * @return
     */
    public static Settings getInstance() {
        return SETTINGS;
    }

    public static void main(String[] args) {
        Boolean.valueOf(false);

        // 생성자, 정적페토리 메서드 모두 제공할 수도 있다.
        List<String> list = new ArrayList<>();
        List.of("a", "B");
    }

}
