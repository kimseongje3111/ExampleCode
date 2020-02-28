package Phase_06.Contents_02;

/* ----------------------------------------------

6장. 열거 타입과 애너테이션

(36) < 비트 필드 대신 EnumSet을 사용하라 >
(37) < ordinal 인덱싱 대신 EnumMap을 사용하라 >

 ---------------------------------------------- */

import java.util.*;

public class Main {

    private static final int STYLE_BOLD = 1 << 0;
    private static final int STYLE_ITALIC = 1 << 1;
    private static final int STYLE_UNDERLINE = 1 << 2;

    public enum Style {BOLD, ITALIC, UNDERLINE, STRIKETHROUGH}

    public static void main(String[] args) {

        // 열거한 값들이 주로 집합으로 사용될 경우, 예전에는 비트 필드를 사용하였다.
        // 각 상수에 서로 다른 2의 거듭제곱 값을 할당하는 방법으로 OR 연산을 사용해 여러 상수를 집합으로 모을 수 있었다.
        int constSet = STYLE_BOLD | STYLE_ITALIC | STYLE_UNDERLINE;

        // 하지만 비트 필드는 정수 열거 상수의 단점과 함께 비트 필드 값이 그대로 노출된다는 단점이 존재한다.
        // 또한 최대 몇 비트가 필요한지 미리 예측하여 적절한 타입을 선택해야 한다.

        // 이러한 단점을 모두 해결해주는 것이 'EnumSet' 이다.
        // EnumSet 은 열거 타입 상수의 값으로 구성된 집합을 효과적으로 표현해준다.
        // Set 인터페이스를 완벽히 구현하며, 타입 안전하고, 다른 Set 구현체와 함께 사용할 수 있다.
        // 만일 원소가 총 64개 이하라면, 즉 대부분의 경우에 EnumSet 전체를 long 변수 하나로 표현할 수 있는 성능을 가진다.
        applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC, Style.UNDERLINE));

        // 열거 타입 클래스의 ordinal 값을 배열의 인덱스로 사용할 경우
        Plant[] garden = new Plant[10];
        Set<Plant>[] plantsByLifeCycle = (Set<Plant>[]) new Set[Plant.LifeCycle.values().length];

        for (int i = 0; i < plantsByLifeCycle.length; i++) plantsByLifeCycle[i] = new HashSet<>();
        for (Plant p : garden) plantsByLifeCycle[p.lifeCycle.ordinal()].add(p);
        // 배열은 제네릭과 호환되지 않기 때문에 비검사 형변환을 수행해야 하고, 깔끔히 컴파일되지 못한다.
        // 또한 배열은 각 인덱스의 의미를 모를뿐만아니라 정확한 정수값을 사용한다는 것을 직접 보증해야 한다. (인덱스에 대한 레이블이 필요)
        // 정수는 열거 타입과 달리 타입 안전하지 않기 때문이다.
        // 그렇지 못한 경우 동작은 되지만 잘못된 값을 가지고 작업을 수행할 위험이 생긴다.

        // 'EnumMap'은 아주 좋은 해결책이다.
        // 위에서 배열은 실질적으로 열거 타입 상수를 값으로 매핑하는 일을 수행한다.
        // 그러므로 열거 타입을 키로 사용하는 EnumMap 을 사용함으로써 더 짧고, 안전하며, 성능까지 보장할 수 있다.
        Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle2 = new EnumMap<>(Plant.LifeCycle.class);     // Plant.LifeCycle.class -> 키 (한정적 타입 토큰)

        for (Plant.LifeCycle lc : Plant.LifeCycle.values()) plantsByLifeCycle2.put(lc, new HashSet<>());
        for (Plant p : garden) plantsByLifeCycle2.get(p.lifeCycle).add(p);

        // 배열의 인덱스를 얻기 위해 ordinal 을 쓰는 것은 위험하기 때문에 EnumMap 을 활용하자
        // 다치원의 관계는 중첩하여 사용하면 된다.
        // 명확하고, 안전하고, 유지보수하기 좋다.
        System.out.println(Phase.Transition.from(Phase.SOLID, Phase.GAS));
    }

    private static void applyStyles(Set<Style> styles) {
        // 스타일 추가
    }
}
