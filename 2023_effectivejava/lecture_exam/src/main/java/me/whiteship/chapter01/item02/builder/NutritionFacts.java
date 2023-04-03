package me.whiteship.chapter01.item02.builder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

// 코드 2-3 빌더 패턴 - 점층적 생성자 패턴과 자바빈즈 패턴의 장점만 취했다. (17~18쪽)
// @Builder // lombok annotation 사용 가능
// 위 어노테이션이 있으면, 컴파일 시점에 이 클래스 코드를 lombok이 조작한다.
// 롬복 사용의 단점 : 필수 파라미터를 지정해줄 수 없다. -> 이 경우에는 직접 작성하자.
//@AllArgsConstructor(access = AccessLevel.PRIVATE) // 내부에서 빌더에서만 사용가능함
public class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static void main(String[] args) {
        NutritionFacts cocaCola = new Builder(240, 8)
                .calories(100)
                .sodium(35)
                .carbohydrate(27).build();
    }

    public static class Builder {
        // 필수 매개변수
        private final int servingSize;
        private final int servings;

        // 선택 매개변수 - 기본값으로 초기화한다.
        private int calories      = 0;
        private int fat           = 0;
        private int sodium        = 0;
        private int carbohydrate  = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings    = servings;
        }

        /* Builder 타입을 리턴 */
        public Builder calories(int val)
        { calories = val;      return this; }

        public Builder fat(int val)
        { fat = val;           return this; }

        public Builder sodium(int val)
        { sodium = val;        return this; }

        public Builder carbohydrate(int val)
        { carbohydrate = val;  return this; }

        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

    private NutritionFacts(Builder builder) {
        servingSize  = builder.servingSize;
        servings     = builder.servings;
        calories     = builder.calories;
        fat          = builder.fat;
        sodium       = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }
}
