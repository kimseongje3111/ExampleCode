package Phase_02.Contents_01;

// 자바빈즈 패턴 - 일관성이 꺠지고, 불변으로 만들 수 없다.

public class NutritionFacts2 {
    private int servingSize = -1;       // 1회 내용량 (필수)
    private int servings = -1;          // 총 n회 제공량 (필수)
    private int calories = 0;           // 1회 제공량당 칼로리 (선택)
    private int fat = 0;                // 지방 (선택)
    private int sodium = 0;             // 나트륨 (선택)
    private int carbohydrate = 0;       // 탄수화물 (선택)

    public NutritionFacts2() {
    }

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
}
