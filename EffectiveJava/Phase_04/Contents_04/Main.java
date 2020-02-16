package Phase_04.Contents_04;

/* ----------------------------------------------

4장. 클래스와 인터페이스

(21) < 인터페이스는 구현하는 쪽을 생각해 설계하라 >
(22) < 인터페이스는 타입을 정의하는 용도로만 사용하라 >

 ---------------------------------------------- */

public class Main {

    public static void main(String[] args) {

        // 자바 8 부터 default 키워드를 통해 인터페이스에도 메서드를 추가할 수 있게 되었다.
        // 하지만 이전까지의 클래스들은 이러한 기능을 전제하지 않고 작성되었기 때문에 별도로 재정의하지 않으면 무작정 삽입되는 꼴이다.
        // 자바 8 에서는 핵심 컬렉션 인터페이스들에 다수의 default 메서드들이 추가되었는데, 주로 람다를 활용하기 위함이다.

        // 모든 상황에서 불변식을 해치지 않는 default 메서드를 작성하는 것은 어려운 일이다.
        // 결과적으로 기존 인터페이스를 구현하는 쪽에서는 새로운 default 메서드로 인해 런타임 오류가 발생할 수 있다.
        // 그러므로 기존 인터페이스에 default 메서드를 추가하는 일은 기존 구현체들과 충돌이 발생하는지에 대한 정보가 우선이다.

        // 인터페이스는 자신을 구현한 클래스의 인스턴스를 참조할 수 있는 타입 역할을 한다.
        // 즉, 클래스가 어떤 인터페이스를 구현한다는 것은 자신의 인스턴스로 무엇을 할 수 있는지를 말해주는 것이다.
        // 인터페이스는 오직 이 용도로 사용되어야 한다.

        double avogadrosNumber = PhysicalConstants2.AVOGADROS_NUMBER;
        double boltzmannConstant = PhysicalConstants2.BOLTZMANN_CONSTANT;
        double electronMass = PhysicalConstants2.ELECTRON_MASS;

    }
}

// 인터페이스를 잘못 활용한 예 - 상수 인터페이스
// 이 인터페이스를 구현한 클래스들은 이 상수들에 종속되어 버린다.
interface PhysicalConstants {
    static final double AVOGADROS_NUMBER = 6.022_140_857e23;
    static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
    static final double ELECTRON_MASS = 9.109_383_56e-31;
}

// 상수를 공개할 목적이라면 아래와 같은 상수 유틸리티 클래스를 제공하는 것이 맞다. (또는 열거 타입)
class PhysicalConstants2 {
    private PhysicalConstants2() {};        // 인스턴스화를 방지

    public static final double AVOGADROS_NUMBER = 6.022_140_857e23;
    public static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
    public static final double ELECTRON_MASS = 9.109_383_56e-31;
}

class ConstantExample implements PhysicalConstants {

    public void doSomething() {
        double avogadrosNumber = AVOGADROS_NUMBER;      // 이 상수는 외부 인터페이스가 아닌 내부 구현에 해당됨
    }

    // 따라서 상수 인터페이스를 구현하는 행위는 이 내부 구현을 클래스의 API 로 노출하는 행위이다.
}
