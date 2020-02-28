package Phase_05.Contents_05;

/* ----------------------------------------------

5장. 제네릭

(31) < 제네릭과 가변인수를 함께 쓸 때는 신중하라 >

 ---------------------------------------------- */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {

        // 가변인수 메서드를 호출하면 이 가변인수를 담기 위한 배열이 자동으로 생성된다.
        // 하지만 이 배열이 내부에 감춰지지 않고 클라이언트에 노출하는 문제가 생겼다.
        // 그 결과, 만일 varargs 매개변수에 제네릭이나 매개변수화 타입이 포함되면 알기 어려운 컴파일 경고가 발생한다.

        // 매개변수화 타입의 변수가 타입이 다른 객체를 참조하게 되면 힙 오염이 발생한다.
        // 이렇게 다른 타입 객체를 참조하는 상황에서는 컴파일러가 자동으로 생성한 형변환이 실패할 가능성이 있다.
        // 이것은 제네릭 타입의 타입 안정성이 보장되지 못함을 알 수 있다.
       // dangerous(new ArrayList<>(Arrays.asList("a", "b")));

        // 프로그래머가 직접 제네릭 배열을 생성하는 것은 금지되지만 제네릭 varargs 매개변수를 받는 메서드 작성이 허용되는 이유는
        // 실무에서 굉장히 유용하게 쓰이기 때문이다.
        // 만일 이 제네릭 가변인수 메서드가 타입 안전을 보장한다면 @SafeVarargs 애너테이션을 붙히면 된다.

        // 제네릭 가변인수 메서드가 타입 안전하다는 것을 확신할 수 있는 요소들이 있다.
        // 우선 메서드가 이 가변인수 배열에 아무것도 저장하지 않고, 밖으로 참조를 노출하지 않으면 된다.
        // 즉, 클라이언트로부터 순수하게 인자만 전달하는 목적으로 쓰인다면(원래 varargs 의 목적) 타입 안전하다.

        String[] strings = pickTwo("좋은", "빠른", "저렴한");      // ClassCastException
        // 아무 문제없어 보이는 이 호출이 형변환 오류를 발생시키는 이유는 pickTwo 반환값을 strings 에 저장할 때 자동으로 발생하는 형변환 코드에 있다.
        // toArray 메서드가 돌려준 배열(가변인수)은 Object[] 인데, String[] 으로 형변환을 시도했기 때문이다.
        // 결과적으로 이는 제네릭 varargs 매개변수 배열에 다른 메서드가 접근하도록 허용하면 안전하지 않음을 보여준다.
        // 단, 이미 @SafeVarargs 애노테이트 된 다른 가변인수 배열에 전달하거나 varargs 를 받지않는 일반 메서드에 넘기는 것은 안전하다.

        // 제네릭이나 매개변수화 타입의 varargs 매개변수를 받는 모든 메서드는 @SafeVarargs 애너테이션을 모두 쓰자
        // 이 말은 타입 안전함을 보장하지 못한다면 가변인수와 제네릭을 혼용해서 쓰지 말라는 것과 같다.
        // 제네릭 varargs 메서드를 다시 정리하면
        // a. varargs 매개변수 배열에 아무것도 저장하지 않는다.
        // b. 해당 배열 또는 복제본을 신뢰할 수 없는 메서드에 노출하지 않는다.
        // c. 재정의할 수 없는 메서드이어야 한다.
        List<String> flatten = flatten(new ArrayList<>(Arrays.asList("a", "b")));
    }

    // 제네릭과 varargs 를 혼용하여 타입 안정성이 깨짐
    // 이처럼 제네릭 varargs 배열 매개변수에 값을 저장하는 것은 안전하지 않다.
    private static void dangerous(List<String>... stringLists) {
        List<Integer> integerList = Arrays.asList(42);
        Object[] objects = stringLists;
        objects[0] = integerList;           // 힙 오염(다른 타입의 객체 참조)
        String s = stringLists[0].get(0);   // ClassCastException
    }

    // 이 메서드는 제네릭 가변인수 배열의 참조가 그대로 노출된다.
    private static <T> T[] toArray(T... args) {
        return args;
    }

    private static <T> T[] pickTwo(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0:
                // toArray 호출시 만드는 varargs 매개변수 배열의 타입은 Object (어떤 타입이라도 담을 수 있는 구체적인 타입이기 때문)
                return toArray(a, b);
            case 1:
                return toArray(a, c);
            case 2:
                return toArray(b, c);
        }
        throw new AssertionError();
    }

    // 이 메서드는 제네릭 varargs 매개변수를 안전하게 사용한다.
    // 저장을 하지도 않으며, 참조를 노출하지도 않는다.
    @SafeVarargs
    private static <T> List<T> flatten(List<? extends T>... lists) {
        List<T> result = new ArrayList<>();

        for (List<? extends T> list : lists) result.addAll(list);

        return result;
    }
}
