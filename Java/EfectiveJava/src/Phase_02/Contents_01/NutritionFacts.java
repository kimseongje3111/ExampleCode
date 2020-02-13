package Phase_02.Contents_01;

// 점층적 생성자 패턴 - 확장이 어렵다.

public class NutritionFacts {
    private final int servingSize;      // 1회 내용량 (필수)
    private final int servings;         // 총 n회 제공량 (필수)
    private final int calories;         // 1회 제공량당 칼로리 (선택)
    private final int fat;              // 지방 (선택)
    private final int sodium;           // 나트륨 (선택)
    private final int carbohydrate;     // 탄수화물 (선택)

    public NutritionFacts(int servingSize, int servings) {
        this(servingSize, servings, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories) {
        this(servingSize, servings, calories, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat) {
        this(servingSize, servings, calories, fat, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium) {
        this(servingSize, servings, calories, fat, sodium, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}
