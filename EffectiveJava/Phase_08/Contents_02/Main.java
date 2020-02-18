package Phase_08.Contents_02;

/* ----------------------------------------------

8장. 메서드

(52) < 다중정의는 신중히 사용하라 >
(53) < 가변인수는 신중히 사용하라 >

 ---------------------------------------------- */

import java.util.*;

public class Main {

    public static void main(String[] args) {

        // 다중정의한 메서드는 정적으로 선택되고, 재정의한 메서드는 동적으로 선택된다.
        // 다시 말해 메서드를 재정의 했다면 객체의 런타임 타입이 메서드 호출의 기준이 되며, 다중정의된 메서드는 컴파일 타입으로 결정된다.

        Collection<?>[] collections = {
                new HashSet<>(),
                new ArrayList<>(),
                new HashMap<String, String>().values()
        };

        for (Collection<?> c : collections) {
            System.out.println(CollectionClassifier.classify(c));
            // 다중정의된 세 classify 중 매개변수가 Collection<?> 인 메서드만 3번 호출
            // 타입이 컴파일 시점에 결정되기 때문

            // 이 문제는 단순히 한개의 메서드에서 instanceof 를 사용함으로 해결할 수 있다.
            System.out.println(CollectionClassifier.realClassify(c));
        }

        List<Wine> wineList = Arrays.asList(new Wine(), new SparklingWine(), new Champagne());

        for (Wine wine : wineList) {
            System.out.println(wine.name());
            // 런타임에 결정된 객체의 타입에 따라 각각 재정의된 메서드 호출
        }

        // 결과적으로 다중정의가 혼동을 일이키는 상황을 피해야 한다.
        // a. 매개변수 수가 같다면 다중정의는 피하자
        // b. 가변인수를 사용하는 메서드는 다중정의를 해서는 안된다. (예외가 있음)
        // c. 다중정의 대신 메서드 이름을 다르게 지어주는 방법을 쓰자
        // d. 이름을 다르게 해줄 수 없는 생성자는 정적 팩터리를 이용하면 된다.

        // 가변인수 메서드는 명시한 타입의 인수를 0개 이상 받을 수 있다.
        // 인수 개수가 일정하지 않은 메서드를 정의해야 한다면 가변인수가 반드시 필요하다.
        min(new int[5]);
        min(new int[6]);

        // 또한 앞서 가변인수 메서드의 다중정의에 예외적인 상황이 존재한다.
        /*
            void foo()
            void foo(int a1)
            void foo(int a1, int a2)
            void foo(int a1, int a2, int a3)
            void foo(int a1, int a2, int a3, int... rest)

            만일 해당 foo 메서드의 호출 비율의 95% 가 인수 3개 이하로 사용한다고 가정 했을 때
            위와 같이 다중정의를 이용하면 나머지 5% 만이 배열을 생성한다.
            따라서 어떠한 상황에서는 이 방법이 성능을 향상시킬 수 있는 수단이 된다.
         */

        // 결과적으로 메서드를 정의할 떄 필수 매개변수는 앞에 두고, 가변인수는 성능 문제까지 고려하자
        min2(1, new int[4]);
        min2(1, new int[5]);
    }

    // 이 방법의 문제는 예외가 런타임이 발생한다는 것이다.
    private static int min(int... args) {
        if (args.length == 0) throw new IllegalArgumentException("인수가 1개 이상 필요합니다.");

        int min = args[0];

        for (int i = 1; i < args.length; i++) {
            min = Math.min(min, args[i]);
        }

        return min;
    }

    // 이 방법을 사용하면 args 의 명시적 유효성 검사를 생략하면서 조건을 만족한다.
    private static int min2(int firstArg, int... remainingArgs) {
        int min = firstArg;

        for (int remainingArg : remainingArgs) {
            min = Math.min(min, remainingArg);
        }

        return min;
    }
}
