package Phase_04.Contents_01;

/* ----------------------------------------------

4장. 클래스와 인터페이스

(15) < 클래스와 멤버의 접근 권한을 최소화하라 >
(16) < public 클래스에서는 public 필드가 아닌 점근자 메서드를 사용하라 >
(17) < 변경 가능성을 최소화하라 >

 ---------------------------------------------- */

import java.util.List;

import static Phase_04.Contents_01.InmmutableArray.*;

public class Main {

    public static void main(String[] args) {

        // 정보 은닉(캡슐화)의 장점을 활용하기 위해 '모든 클래스와 멤버의 접근성을 가능한 한 좁혀야 한다.'

        // A. 톱레벨 클래스와 인터페이스가 패키지 외부에서 쓸 이유가 없다면 public 보다는 package-private 으로 선언하자
        // - public 으로 선언하면 공개 API 가 되어 하위 호환을 위해 관리가 필요한 반면, package-private 는 내부 구현이 되어 언제든 수정 가능하다.

        // B. 그 외의 멤버는 모두 private 으로 만들고, 오직 같은 패키지의 다른 클래스에서 접근해야 하는 멤버만 package-private 으로 풀어주자

        // C. public 클래스의 인스턴스 필드는 되도록 public 이 아니어야 한다.
        // - public 으로 선언하면 그 필드와 관련된 모든 것은 불변식을 보장할 수 없게 된다.
        // - public 필드는 일반적으로 스레드 안전하지 않다.
        // - 단, 해당 클래스가 표현하는 추상 개념을 완성하는 데 꼭 필요한 구성요소로써의 상수라면 public static final 필드로 공개하자

        List<Thing> values1 = VALUES;
        Thing[] values2 = values();

        // 불변 클래스로부터 생성된 불변 인스턴스는 정보가 고정되어 객체가 파괴되는 순간까지 절대 변하지 않는다.
        // 불변 클래스는 가변 클래스보다 설계하고 구현하기 쉽고, 안전하다.

        // <불변 클래스 생성 규칙>
        // A. 객체의 상태를 변경하는 메서드를 제공하지 않는다.
        // B. 클래스를 확장할 수 없도록 한다.
        // C. 모든 필드를 final 로 선언한다.
        // D. 모든 필드를 private 으로 선언한다.
        // E. 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 한다.

        ComplexExample2 complex = ComplexExample2.valueOf(30, 20);
        System.out.println(complex);

        // 결론 - 클래스는 꼭 필요한 경우가 아니라면 불변이어야 한다.
        // 불변으로 만들 수 없는 클래스라도 변경할 수 있는 부분은 최소한으로 줄이자 (다른 합당한 이유가 없다면 모든 필드는 private final)
        // 확실한 이유가 없다면 생성자와 정적 팩터리 외에는 어떤 초기화 메서드도 public 으로 제공하면 안된다.
    }
}
