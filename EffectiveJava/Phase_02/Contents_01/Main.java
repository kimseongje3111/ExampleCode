package Phase_02.Contents_01;

/* ----------------------------------------------

2장. 객체 생성과 파괴

(1) < 생성자 대신 정적 팩터리 메서드를 고려하라 >
(2) < 생성자에 매개변수가 많다면 빌더를 고려하라 >

 ---------------------------------------------- */

import static Phase_02.Contents_01.NyPizza.SIZE;
import static Phase_02.Contents_01.Pizza.Topping;

public class Main {

    public static void main(String[] args) {

        // (A) 점층적 생성자 패턴 - 매개변수가 많아지면 코드 작성 및 읽기가 어렵다.
        NutritionFacts cocaCola = new NutritionFacts(240, 8, 100, 0, 35, 27);

        // (B) 자바빈즈 패턴 - 객체를 만들기 위해 여러 메서드를 호출해야 하며, 완전히 객체가 생성되기 전까지 일관성이 무너진 상태이다.
        // 일관성 문제 때문에 클래스를 불변으로 만들 수 없다. (스레드 안정성 조치 필요)
        NutritionFacts2 cocaCola2 = new NutritionFacts2();
        cocaCola2.setServingSize(240);
        cocaCola2.setServings(0);
        cocaCola2.setCalories(100);
        cocaCola2.setFat(0);
        cocaCola2.setSodium(35);
        cocaCola2.setCarbohydrate(27);

        // (C) 빌더 패턴 - 쓰기 및 일기가 쉽다.
        // 계층적으로 설계된 클래스와 함께 쓰기 좋다.
        NutiritionFacts3 cocaCola3 = new NutiritionFacts3.Builder(240, 8)
                .calories(100)
                .sodium(35)
                .carbohydrate(27)
                .build();

        // 빌더 패턴 예시 (피자 - 뉴욕 피자, 칼초네 피자)
        // 빌더 하나로 여러 객체를 순회하면서 만들 수 있고, 정말 유연하다.
        // 단점은 빌더 객체 생성 비용 (비중이 크진 않음), 매개변수가 적어도 4개 이상은 되어야 효용 가치가 있을 것
        Pizza nyPizza = new NyPizza.NyBuilder(SIZE.SMALL)
                .addTopping(Topping.HAM)
                .addTopping(Topping.ONION)
                .build();

        Pizza calPizza = new Calzone.CalBuilder()
                .addTopping(Topping.MUSHROOM)
                .addTopping(Topping.PEPPER)
                .addTopping(Topping.SAUSAGE)
                .addSauce()
                .build();

        // 결론 - 생성자나 정적 팩터리 메서드가 처리해야 할 매개변수가 많다면 빌더 패던을 사용하자 (선택 매개변수가 많을수록 좋음)
        // 점층적 생성자 패턴보다 간결하고, 자바빈즈 패턴보다 안전하다.
    }
}
