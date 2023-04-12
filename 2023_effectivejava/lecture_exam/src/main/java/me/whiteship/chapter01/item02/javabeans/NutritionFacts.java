package me.whiteship.chapter01.item02.javabeans;

/**
 * 자바빈(JavaBean)
 * - java.beans 패키지 안에 있는 모든것
 * - 규약을 따라야함 (아규먼트 없는 기본 생성자, getter/setter 메서드 규약, Serializable 인터페이스 규약)
 */
// 코드 2-2 자바빈즈 패턴 - 일관성이 깨지고, 불변으로 만들 수 없다. (16쪽)
public class NutritionFacts {
    // 필드 (기본값이 있다면) 기본값으로 초기화된다.
    private int servingSize  = -1; // 필수; 기본값 없음
    private int servings     = -1; // 필수; 기본값 없음
    private int calories     = 0;
    private int fat          = 0;
    private int sodium       = 0;
    private int carbohydrate = 0;
    private boolean healthy;

    public NutritionFacts() { }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public static void main(String[] args) {
        /* 필수값 셋팅은 안된채로 사용하게 될 수도 있다. - 불안정한 상태 */
        NutritionFacts cocaCola = new NutritionFacts();

        // immutable : set()을 제공하지 않고, 생성자로만 셋팅되도록한다.
        // set()을 제공해야할때는 이를 immutable 객체로 만들기 어렵다.

        // 필수인 데이터를 set() 하기 전에 cocaCola 객체를 사용할 위험이 있음
        cocaCola.setServingSize(240);
        cocaCola.setServings(8);

        cocaCola.setCalories(100);
        cocaCola.setSodium(35);
        cocaCola.setCarbohydrate(27);
    }
}
