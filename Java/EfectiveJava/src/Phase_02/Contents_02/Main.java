package Phase_02.Contents_02;

/* ----------------------------------------------

2장. 객체 생성과 파괴

(3) < private 생성자나 열거 타입으로 싱클턴임을 보증하라 >
(5) < 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라 >

 ---------------------------------------------- */

public class Main {

    public static void main(String[] args) {

        // 싱글턴 - 인스턴스를 오직 하나만 생성 가능한 클래스
        // 보통 생성자를 private 로 감춰드고, 유일한 인스턴스에 접근할 수 있는 수단으로 public static 멤버를 마련한다.

        // (1) public static final 방식 - 싱글턴임을 명백히 드러내며, 간결하다.
        Elvis.INSTANCE.leaveTheBuilding();

        // (2) 정적 팩터리 메서드 방식 - 인스턴스 생성 방식 변경 가능, 메서드를 제네릭로 만들 수 있음
        // 메서드 참조를 공급자로 사용이 가능하다. (Ex. Supplier<Elvis>)
        Elvis2.getInstance().leaveTheBuilding();

        // (3) 열거 타입 방식 - 가장 바람직
        // public 방식과 비슷하지만 더 간결하고, 노력없이 직렬화가 가능
        // 하지만 싱글턴이 Enum 이외의 클래스를 상속해야 한다면 사용 불가 (인터페이스 구현은 가능)
        Elvis3.INSTANCE.leaveTheBuilding();

        // 대부분의 클래스는 자원에 의존한다.
        // 사용하는 자원에 따라 동작이 달라지는 클래스는 정적 유틸리티 방식 또는 싱글턴 방식이 적합하지 않음
        // 때문에 인스턴스 생성시 필요한 자원을 넘겨주는 의존 객체 주입 방식은 유연성, 재사용성, 테스트 용이성을 높여준다.
        SpellCheckerExample spellCheckerExample = new SpellCheckerExample(Lexcion.KOREAN);
        SpellCheckerExample spellCheckerExample2 = new SpellCheckerExample(Lexcion.ENGLISH);
    }
}
