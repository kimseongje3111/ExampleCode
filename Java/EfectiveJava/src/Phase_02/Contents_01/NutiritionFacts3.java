package Phase_02.Contents_01;

// 빌더 패턴 - 점층적 생성자 패턴과 자바빈즈 패턴의 장점만 합침

public class NutiritionFacts3 {
    private final int servingSize;      // 1회 내용량 (필수)
    private final int servings;         // 총 n회 제공량 (필수)
    private final int calories;         // 1회 제공량당 칼로리 (선택)
    private final int fat;              // 지방 (선택)
    private final int sodium;           // 나트륨 (선택)
    private final int carbohydrate;     // 탄수화물 (선택)

    // 보통 생성할 클래스 안에 정적 멤버 클래스로 만든다.
    public static class Builder {
        // 필수 매개변수
        private final int servingSize;
        private final int servings;

        // 선택 매개변수 (초기화)
        private int calories;
        private int fat;
        private int sodium;
        private int carbohydrate;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val) {
            calories = val;
            return this;
        }

        public Builder fat(int val) {
            fat = val;
            return this;
        }

        public Builder sodium(int val) {
            sodium = val;
            return this;
        }

        public Builder carbohydrate(int val) {
            carbohydrate = val;
            return this;
        }

        public NutiritionFacts3 build() {
            return new NutiritionFacts3(this);
        }
    }

    private NutiritionFacts3(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }
}
