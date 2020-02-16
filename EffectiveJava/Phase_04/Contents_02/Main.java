package Phase_04.Contents_02;

/* ----------------------------------------------

4장. 클래스와 인터페이스

(18) < 상속보다는 컴포지션을 사용하라 >
(19) < 상속을 고려해 설계하고 문서화하라. 그렇지 않았다면 상속을 금지하라 >

 ---------------------------------------------- */

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {

        // 구현 상속(클래스 확장)은 메서드 호출과 달리 캡슐화를 깨드린다.
        // 즉, 상위 클래스가 어떻게 구현되느냐에 따라 하위 클래스의 동작에 이상이 생길 수 있다.

        // 잘못된 상속의 예
        InstrumentedHashSet<String> set = new InstrumentedHashSet<>();
        set.addAll(Arrays.asList("A", "B", "C"));

        System.out.println(set.getAddCount());
        // 기대하는 출력 값은 3 이지만 6 이 출력된다.

        // 원인 : HashSet 의 addAll 메서드가 add 메서드를 사용해 구현되었기 때문
        // 이러한 '자기사용' 여부는 해당 클래스의 내부 구현 방식에 해당되며, 이것이 자바 전체적인 정책인지 알 수 없음
        // 때문에 이러한 가정(자기사용이 된다)을 통한 해결 방법은 해당되는 경우에만 적용될 수 밖에 없다.
        // 하위 클래스에서 메서드를 재정의하거나 새로운 메서드를 추가하는 것은 상위 클래스 변경에 영향을 받는다.

        // 이러한 문제를 해결하기 위해 컴포지션을 사용하자
        // 컴포지션 : 기존 클래스를 확장하는 대신 새로운 클래스를 만들고 private 필드로 기존 클래스의 인스턴스를 참조하게 하는 것 !

        // 새 클래스 인스턴스의 메서드들은 기존 클래스에 대응하는 메서드를 호출해 그 결과를 반환한다. (전달 메서드)
        // 그 결과, 새 클래스는 기존 클래스의 내부 구현 방식에 영향을 받지 않게 된다.
        Set<Instant> times = new InstrumentedHashSetByComposition<>(new TreeSet<>());
        Set<String> strings = new InstrumentedHashSetByComposition<>(new HashSet<>());

        // 이와 같이 InstrumentedHashSetByComposition 클래스는 다른 Set 인스턴스를 감싸고 있다는 뜻에서 '래퍼 클래스'라고 부른다.

        // 결론 - 상속은 강력하지만 캡슐화를 해친다.
        // 그러므로 상속은 상/하위 클래스가 순수한 is-a 관계일 때 사용하자 (상위 클래스가 확장을 고려해 설계되어야 한다)
        // 그렇지 않다면 컴포지션과 전달 클래스를 사용하자 (특히, 래퍼 클래스로 구현할 적당한 인터페이스가 있다면!)

        // 상속용 클래스를 설계하는 것은 여러 제약 사항이 존재한다.
        // 클래스 내부에서 스스로를 어떻게 사용하는지 모두 문서로 작성해야 하며, 해당 문서를 바탕으로 하위 클래스를 사용한다.
        // 그렇지 않으면 원하지 않은 오작동을 일으킬 수 있다.
        // 결과적으로 클래스를 확장해야할 명확한 의미가 없다면 상속을 금지하는 것이 현명하다.
    }
}
