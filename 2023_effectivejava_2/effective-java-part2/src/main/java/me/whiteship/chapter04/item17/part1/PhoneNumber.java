package me.whiteship.chapter04.item17.part1;

/**
 * 상속불가
 * final class
 * private 생성자만 가지고있게
 */
public final class PhoneNumber {

    /**
     * 불면클래스로 개발하는것이 훨씬 코딩하는데 쉽고 안전하다.
     *
     * final로 하지않으면 실수로라도 set메서드나 별도 메서드에서 값을 바꾸는 경우가 생길 수 있다.
     */
    private final short areaCode, prefix, lineNum;

    public PhoneNumber(short areaCode, short prefix, short lineNum) {
        this.areaCode = areaCode;
        this.prefix = prefix;
        this.lineNum = lineNum;
    }

    public short getAreaCode() {
        return areaCode;
    }

    public short getPrefix() {
        return prefix;
    }

    public short getLineNum() {
        return lineNum;
    }


}
