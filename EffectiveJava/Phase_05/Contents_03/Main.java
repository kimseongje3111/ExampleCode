package Phase_05.Contents_03;

/* ----------------------------------------------

5장. 제네릭

(29) < 이왕이면 제네릭 타입으로 만들라 >

 ---------------------------------------------- */

import java.util.*;

public class Main {

    public static void main(String[] args) {

        // 클래스와 마찬가지로, 메서드 또한 제네릭으로 만들 수 있다.
        // 제네릭 메서드 또한 타입 안전하게 만드는 것이 핵심이다.

        // 제네릭 메서드 구현
        Set<String> guys = new HashSet<>(Arrays.asList("톰", "딕", "해리"));
        Set<String> stooges = new HashSet<>(Arrays.asList("래리", "모에", "컬리"));

        Set<String> union = union(guys, stooges);

        // 불변 객체를 여러 타입으로 활용해야 하는 경우
        // 제네릭은 런타임에 타입 정보가 소거 되기 때문에 하나의 객체를 여러 타입으로 매개변수화 할 수 있다.
        // 하지만 이렇게 할 경우 요청 타입 매개변수에 맞게 매번 해당 객체의 타입을 변경해주는 정적 팩터리가 필요하다.
        // 이 패턴을 '제네릭 싱글턴 팩터리'라고 한다.
        String[] strings = {"삼베", "대마", "나일론"};
        UnaryOperatorExample<String> sameString = UnaryOperatorClass.identityFunction();

        for (String s : strings) System.out.println(sameString.apply(s));

        Number[] numbers = {1, 2.0, 3L};
        UnaryOperatorExample<Number> sameNumber = UnaryOperatorClass.identityFunction();

        for (Number n : numbers) System.out.println(sameNumber.apply(n));

        // 자기 자신이 들어간 표현식을 사용하여 타입 매개변수의 허용 범위를 한정할 수도 있다.
        // 이것을 '재귀적 타입 한정'이라고 하며, 주로 Comparable 인터페이스와 함께 쓰인다.
        Optional<Integer> max = max(new ArrayList<>(Arrays.asList(1, 2, 3)));

        // 결과적으로 타입과 마찬가지로, 형변환을 해줘야 하는 기존 메서드는 제네릭하게 만드는 것이 좋다.
    }

    // 아래 메서드 선언의 세 집합(입력 2개, 반환 1개)의 원소 타입을 타입 매개변수로 명시하고, 메서드 안에서도 이 타입 매개변수를 사용
    // (타입 매개변수들을 선언하는)타입 매개변수 목록은 접근 제한자와 반환 타입 사이에 선언
    private static <E> Set<E> union(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);

        return result;
    }

    // 재귀적 타입 한정을 사용한 최대값 구하기 예제 (Optional 사용)
    // <E extends Comparable<E>> : 모든 타입 E 는 자신과 비교할 수 있다 (상호 비교 가능하다)
    private static <E extends Comparable<E>> Optional<E> max(Collection<E> c) {
        if (!c.isEmpty()) {
            E result = null;

            for (E e : c) {
                if (result == null || e.compareTo(result) > 0) {
                    result = Objects.requireNonNull(e);
                }
            }

            return Optional.of(result);

        } else {
            return Optional.empty();
        }

    }
}
