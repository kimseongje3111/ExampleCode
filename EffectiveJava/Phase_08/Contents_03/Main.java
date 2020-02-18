package Phase_08.Contents_03;

/* ----------------------------------------------

8장. 메서드

(54) < null 이 아닌, 빈 컬렉션이나 배열을 반환하라 >
(55) < 옵셔널 반환은 신중히 하라 >

 ---------------------------------------------- */

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        // 만일 컬렉션이나 배열 같은 컨테이너가 비었을 때 null 을 반환하는 메서드를 사용한다면 클라이언트에서는 아래와 같은 방어 코드를 넣어야 한다.
        List<Cheese> cheeses = CheeseExample.getCheeses();

        if (cheeses != null && cheeses.contains(Cheese.STILTON)) {
            System.out.println("Correct !");
        }

        // 그러므로 null 을 반환하지 않고 빈 컬렉션이나 배열을 반환하는 것이 바람직하다.
        // 빈 컨테이너를 생성하는 것이 성능 저하에 영향을 주지 않는다.
        // 만일 그러하더라도 불변 컬렉션 또는 배열을 반환함으로써 굳이 새로 할당하지 않고 반환할 수 있다.
        List<Cheese> cheeses2 = CheeseExample.getCheeses2();

        // 결과적으로 null 을 반환하는 API 는 사용하기 어렵고, 오류 처리 코드도 늘어나며, 그렇다고 성능이 향상되는 것이 아니다.
        // 따라서 null 이 아닌 빈 배열이나 빈 컬렉션을 반환하자

        // 자바 8 이전에는 어떤 메서드가 특정 조건에서 값을 반환할 수 없을 때 두 가지 방법으로 처리한다.
        // a. 예외 처리 (예외는 진짜 예외적인 상황에서만 사용해야 하며, 예외 생성 비용도 만만치 않음)
        // b. null 반환 (클라이언트는 별도의 null 처리 코드를 추가해야 함)
        OptionalExample.max(new ArrayList<Integer>());

        // 하지만 자바 8 이후부터 Optional<T> 를 사용할 수 있다. (불변 컬렉션)
        // Optional<T> 는 null 이 아닌 T 타입 참조를 하나 담거나 아무것도 담지 않을(비었다) 수 있다.
        // 보통의 경우 T 를 반환해야 하지만 특정 조건에서 아무것도 반환하지 않아야 한다면 T 대신 Optional<T> 를 반환하도록 하면 된다.
        // 옵셔널을 반환하는 메서드는 예외를 던지는 것보다 유연하고 사용하기 쉬우며, null 을 반환하는 것보다 오류 가능성이 적다.

        OptionalExample.max2(new ArrayList<Integer>());
        OptionalExample.max3(new ArrayList<Integer>());

        // 옵셔널을 사용하는 이유는 검사 예외가 비슷하다.
        // 즉, 반환값이 없을 수도 있음을 클라이언트에게 명확히 알려주는 역할을 한다.
        // 그러므로 예외와 비슷하게 옵셔널 반환시 클라이언트는 값을 받지 못할 경우의 행동을 선택해야 한다.

        // 예 1) 기본값 설정
        ArrayList<String> words = new ArrayList<>();
        String lastWordInLexicon = OptionalExample.max2(words).orElse("단어 없음");

        // 예 2) 원하는 예외를 던질 수 있음
        String lastWordInLexicon2 = OptionalExample.max2(words).orElseThrow(Exception::new);

        // 예 3) 항상 값이 채워져 있다고 가정
        String lastWordInLexicon3 = OptionalExample.max2(words).get();

        // 위 방법들을 포함하여 제공되는 메서드를 통해 다양하게 처리할 수 있다. (API 문서 참조)
        // 하지만 컬렉션, 스트림, 배열, 옵셔널 같은 컨테이너 타입은 옵셔널로 감싸면 안된다.
        // Optional<List<T>> 를 반환하기 보다 빈 List<T> 를 반환하는게 낫다. (아이템 54)
        // 또한 박싱된 기본 타입을 담는 옵셔널 전용 클래스가 존재한다. (ex. OptionalInt, OptionalLong ..)

        // T 대신 Optional<T> 를 선언하는 경우는 결과가 없을 수 있으며, 클라이언트가 이 상황을 특별하게 처리해야 한다면 Optional<T> 를 반환한다.
        // 하지만 Optional 생성에 비용이 추가되므로 성능과 같이 고려해야 한다.
        // 결과적으로 옵셔널 반환에는 성능 저하가 뒤따르기 때문에 성능에 민감한 메서드라면 null 이나 예외를 던지느 방식이 더 나을 수 있다.
        // 추가적으로 반환값 이외에 Optional 을 사용하는 경우는 드물다.
    }
}
